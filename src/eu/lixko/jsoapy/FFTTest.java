package eu.lixko.jsoapy;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.synth.SynthOptionPaneUI;

import eu.lixko.jsoapy.gui.WaterfallPanel;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;


public class FFTTest extends JFrame {
	public final static int MAX_FFT_SIZE = 65536 * 2 * 2 * 2; // * 2 * 2;
	
	WaterfallPanel waterfall;
	
	public FFTTest(FFT fftGen) {
		setTitle("FFT Waterfall");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        waterfall = new WaterfallPanel(1800, 960);
        add(waterfall, BorderLayout.CENTER);
        
        JLabel sizeLabel = new JLabel("Size: ");
        ArrayList<Integer> spinnerSizes = new ArrayList<>();
        for (int i = 4096; i <= MAX_FFT_SIZE; i *= 2) {
        	spinnerSizes.add(i);
        }
        var sizeSpinnerModel = new SpinnerListModel(spinnerSizes);
        
        JSpinner sizeSelector = new JSpinner(sizeSpinnerModel);
        sizeSelector.setValue(MAX_FFT_SIZE);
        JSlider offsetSlider = new JSlider(JSlider.HORIZONTAL, -1000, 1000, 0);
        JLabel offsetLabel = new JLabel("Offset: ");
        JSlider zoomSlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 1000);
        JLabel zoomLabel = new JLabel("Zoom: ");
        JSlider minSlider = new JSlider(JSlider.HORIZONTAL, -160, -10, -90);
        JLabel minLabel = new JLabel("Min: ");
        JSlider maxSlider = new JSlider(JSlider.HORIZONTAL, -150, 0, -60);
        JLabel maxLabel = new JLabel("Max: ");
        waterfall.setMinValue(minSlider.getValue());
        waterfall.setMaxValue(maxSlider.getValue());
        waterfall.setZoomLevel(1.0);
        
        sizeSelector.addChangeListener((ChangeEvent e) -> {
        	fftGen.setFftSize((Integer) sizeSelector.getValue());
        });
        
        offsetSlider.addChangeListener((ChangeEvent e) -> {
            waterfall.setViewOffset(offsetSlider.getValue() / 1000d);
            offsetSlider.setValue((int) (waterfall.getViewOffset() * 1000d));
        });
        
        zoomSlider.addChangeListener((ChangeEvent e) -> {
            waterfall.setZoomLevel(zoomSlider.getValue() / 1000d);
            offsetSlider.setValue((int) (waterfall.getViewOffset() * 1000d));
        });

        minSlider.addChangeListener((ChangeEvent e) -> {
            waterfall.setMinValue(minSlider.getValue());
            maxSlider.setValue((int) (waterfall.getMaxValue()));
            minSlider.setValue((int) (waterfall.getMinValue()));
            maxLabel.setText("Max: " + maxSlider.getValue());
            minLabel.setText("Min: " + minSlider.getValue());
        });

        maxSlider.addChangeListener((ChangeEvent e) -> {
            waterfall.setMaxValue(maxSlider.getValue());
            maxSlider.setValue((int) (waterfall.getMaxValue()));
            minSlider.setValue((int) (waterfall.getMinValue()));
            maxLabel.setText("Max: " + maxSlider.getValue());
            minLabel.setText("Min: " + minSlider.getValue());
        });
        
        JPanel fftControls = new JPanel();
        fftControls.setLayout(new GridLayout(5, 2));
        fftControls.add(sizeLabel);
        fftControls.add(sizeSelector);
        
        fftControls.add(offsetLabel);
        fftControls.add(offsetSlider);
        fftControls.add(zoomLabel);
        fftControls.add(zoomSlider);
        fftControls.add(minLabel);
        fftControls.add(minSlider);
        fftControls.add(maxLabel);
        fftControls.add(maxSlider);
        

        add(fftControls, BorderLayout.SOUTH);
        
        
        pack();
	}
	
	public WaterfallPanel getWaterfall() {
		return this.waterfall;
	}
	
	public static void main(String[] args) {
		/* try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
		} */
		
		FFT fftGen = new FFT(MAX_FFT_SIZE);
		FFTTest frame = new FFTTest(fftGen);
        frame.setVisible(true);
        
        

    	Thread.ofVirtual().start(() -> {
    		
    		
    		ByteBuffer rawBuf = ByteBuffer.allocate((int) fftGen.getSize() * Float.BYTES * 2);
    		// FloatBuffer fftOut = FloatBuffer.allocate((int) fftGen.getNativeBufferSize() / Float.BYTES);
    		
    		try {
        		DatagramSocket socket = new DatagramSocket(6502);
        		long t1 = System.nanoTime();
        		long totalFetched = 0;
        		long fftInPos = 0; // bytes
        		
        		while (true) {
        			DatagramPacket packet = new DatagramPacket(rawBuf.array(), rawBuf.capacity());
        			socket.receive(packet);
        			
        			totalFetched += packet.getLength();
        			if (System.nanoTime() - t1 > 1000000000) {
        				t1 = System.nanoTime();
        				System.out.println("Speed: " + totalFetched + " B / s");
        				totalFetched = 0;
        			}
        			rawBuf.limit(packet.getLength());
        			rawBuf.position(0);
        			
        			int fftSize = (int) fftGen.getSize();
        			
        			while (rawBuf.hasRemaining()) {
            			long shouldCopy = Math.max(0, Math.min(fftSize * Float.BYTES * 2 - fftInPos, rawBuf.limit() - rawBuf.position()));
            			MemorySegment.copy(rawBuf.array(), rawBuf.position(), fftGen.fft_in, ValueLayout.JAVA_BYTE, fftInPos, (int) shouldCopy);
            			fftInPos += shouldCopy;
            			rawBuf.position((int) (rawBuf.position() + shouldCopy));
            			// System.out.println(shouldCopy + " / " + rawBuf.position() + " pos: " + fftInPos + " max: " + fftGen.getNativeBufferSize());

            			if (fftInPos >= fftSize * Float.BYTES * 2) { // we have filled the input buffer            				
            				fftInPos = 0;
            				fftGen.execute();
            				
            				frame.getWaterfall().addLine(fftGen);
            			}
        			}
        		}
    		} catch (IOException ex) {
    			ex.printStackTrace();
    		}

    	});

    }
}

