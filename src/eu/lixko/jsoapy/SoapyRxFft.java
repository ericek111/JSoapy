package eu.lixko.jsoapy;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;

import eu.lixko.jsoapy.gui.WaterfallPanel;
import eu.lixko.jsoapy.soapy.Converters;
import eu.lixko.jsoapy.soapy.Converters_h;
import eu.lixko.jsoapy.soapy.Errors_h;
import eu.lixko.jsoapy.soapy.SoapySDRConverterFunction;
import eu.lixko.jsoapy.soapy.SoapySDRDevice;
import eu.lixko.jsoapy.soapy.SoapySDRDeviceDirection;
import eu.lixko.jsoapy.soapy.SoapySDRKwargs;
import eu.lixko.jsoapy.soapy.SoapySDRStream;
import eu.lixko.jsoapy.soapy.StreamFormat;
import eu.lixko.jsoapy.util.NativeUtils;


public class SoapyRxFft extends JFrame {
	private static final long serialVersionUID = -1170329866799567839L;

	public final static int MAX_FFT_SIZE = 65536 * 2 * 2 * 2; // * 2 * 2;
	
	WaterfallPanel waterfall;
	
	public SoapyRxFft(FFT fftGen) {
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
		NativeUtils.loadLibrary();
		
		/* try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
		} */
		
		FFT fftGen = new FFT(MAX_FFT_SIZE);
		SoapyRxFft frame = new SoapyRxFft(fftGen);
        frame.setVisible(true);
        
    	Thread.ofVirtual().start(() -> {
    		// FloatBuffer fftOut = FloatBuffer.allocate((int) fftGen.getNativeBufferSize() / Float.BYTES);
    		
    		SoapySDRDevice device = SoapySDRDevice.makeStrArgs("");
    		System.out.println(device.listSampleRates(SoapySDRDeviceDirection.RX, 0));
    		device.setSampleRate(SoapySDRDeviceDirection.RX, 0, 8_000_000.0);
    		device.setFrequency(SoapySDRDeviceDirection.RX, 0, 100_000_000.0);
			System.out.println(device.getSampleRate(SoapySDRDeviceDirection.RX, 0));
			SoapySDRStream stream = device.setupStream(SoapySDRDeviceDirection.RX, StreamFormat.CF32, null, null);
			System.out.println(stream);
			System.out.println("Block size: " + stream.getBlockSize());
			stream.activateStream();
			
			MemorySegment convFunction = Converters_h.SoapySDRConverter_getFunction(stream.getNativeFormat(0).format().addr(), StreamFormat.CF32.addr());
			
			
			
			long t1 = System.nanoTime();
			long totalFetched = 0;
			long fftInPos = 0; // bytes
			long totalBatches = 0;

			while (true) {
				// int readElems = stream.readStream(100000);
				var readBuf = stream.acquireReadBuffer(100000);
				int readElems = (int) readBuf.getNumElems();
				
				if (readElems < 0) {
					readBuf.release();
					System.out.println("Error reading: " + Errors_h.fromCode(readElems).name());
					continue;
				}
				
				MemorySegment inSamples = readBuf.getBuffer(0);
				// MemorySegment inSamples = stream.getData(0);
				ByteBuffer rawBuf = inSamples.asByteBuffer();
				
				totalFetched += inSamples.byteSize();
				totalBatches++;
				
				// System.out.println(readElems + " / " + inSamples.byteSize() + " / " + totalFetched + " @ " + inSamples.address());
				
				
				if (System.nanoTime() - t1 > 1000000000) {
					t1 = System.nanoTime();
					System.out.println("Rate: " + totalFetched + " B / s in " + totalBatches + " packets");
					totalFetched = 0;
					totalBatches = 0;
				}
				
				int fftSize = (int) fftGen.getSize();
				while (rawBuf.hasRemaining()) {
					long shouldCopy = Math.max(0, Math.min(fftSize * Float.BYTES * 2 - fftInPos, rawBuf.limit() - rawBuf.position()));
					// System.out.println(inSamples.get(ValueLayout.JAVA_SHORT, 0));
					// Converters.convert(stream.getNativeFormat(0).format(), StreamFormat.CF32, MemorySegment.ofAddress(inSamples.address() + rawBuf.position()), fftGen.fft_in.asSlice(fftInPos), (int) (shouldCopy / StreamFormat.CF32.byteSize()), 1.0);
					// SoapySDRConverterFunction.invoke(convFunction, MemorySegment.ofAddress(inSamples.address() + rawBuf.position()), fftGen.fft_in.asSlice(fftInPos), (int) (shouldCopy / StreamFormat.CF32.byteSize()), 1.0);
					SoapySDRConverterFunction.invokeLongs(convFunction, inSamples.address() + rawBuf.position(), fftGen.fft_in.address() + fftInPos, (int) (shouldCopy / StreamFormat.CF32.byteSize()), 1.0);
					// MemorySegment.copy(inSamples, (long) rawBuf.position(), fftGen.fft_in, fftInPos, (int) shouldCopy);
					fftInPos += shouldCopy;
					rawBuf.position((int) (rawBuf.position() + shouldCopy));
					// System.out.println(shouldCopy + " / " + rawBuf.position() + " pos: " + fftInPos + " max: " + fftGen.getNativeBufferSize());

					if (fftInPos >= fftSize * Float.BYTES * 2) { // we have filled the input buffer            				
						fftInPos = 0;
						fftGen.execute();
						
						frame.getWaterfall().addLine(fftGen);
					}
				}
				readBuf.release();
			}

    	});

    }
}

