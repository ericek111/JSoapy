package eu.lixko.jsoapy.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.swing.JPanel;

import eu.lixko.jsoapy.FFT;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class WaterfallPanel extends JPanel implements ComponentListener {
    private static final long serialVersionUID = 7474511002119759534L;
    
	private BufferedImage waterfallImage; // BufferedImage to hold the waterfall data
    private final int WATERFALL_RESOLUTION = 1000000;
    private int[] palette = new int[WATERFALL_RESOLUTION];
    
    private float waterfallMin = 0.0f;
    private float waterfallMax = 1.0f;
    private float fftData[][];
    private int fftSizes[];
    private int currentLine = 0;
    private Thread zoomWorker;
    protected final AtomicBoolean zoomWorkAvailable = new AtomicBoolean(false);
    protected final AtomicBoolean shuttingDown = new AtomicBoolean(false);
    private ReentrantReadWriteLock resizingLock = new ReentrantReadWriteLock();
    
    protected double offsetFactor = 0.0;
    protected double viewBandwidth = 0.0; // actual bandwidth displayed
	protected double viewZoom; // linearized representation of the current zoom (e. g. percentage on a slider)
	private double zoomFactor;
    
    public WaterfallPanel(int width, int height) {
    	this.addComponentListener(this);
    	
        this.waterfallImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Set the preferred size of the panel
        setPreferredSize(new Dimension(width, height));

        // Enable double buffering
        setDoubleBuffered(false);
        
        fftData = new float[height][4096];
        fftSizes = new int[fftData.length];
        
        this.updatePalette(new int[] { // classic
    		0x000020,
    		0x000030,
    		0x000050,
    		0x000091,
    		0x1E90FF,
    		0xFFFFFF,
    		0xFFFF00,
    		0xFE6D16,
    		0xFE6D16,
    		0xFF0000,
    		0xFF0000,
    		0xC60000,
    		0x9F0000,
    		0x750000,
    		0x4A0000
        });
        
        zoomWorker = new Thread(() -> {
        	while (!shuttingDown.get()) {
	        	synchronized (zoomWorkAvailable) {
		        	try {
		        		zoomWorkAvailable.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	        	}
	        	while (zoomWorkAvailable.get()) {
    				zoomWorkAvailable.set(false);
    				
    				resizingLock.readLock().lock();
    				try {
    					doFullUpdateWork();    					
    				} finally {
    					resizingLock.readLock().unlock();
    				}    				
    			}
	        }
        });
        zoomWorker.start();
    }

    public void addLine(FFT fftGen) {
    	resizingLock.readLock().lock();
		try {
	    	WritableRaster raster = waterfallImage.getRaster();
	    	DataBuffer rasterBuf = raster.getDataBuffer();
	    	int[] imageData = ((DataBufferInt) rasterBuf).getData();
	
	    	// raster.setPixels(0, 0, raster.getWidth(), raster.getHeight() - 1, raster.getPixels(0, 1, raster.getWidth(), raster.getHeight() - 1, (int[]) null));
	    	
	    	if (false) {
	    		// scroll upwards: 
	        	System.arraycopy(imageData, raster.getWidth(), imageData, 0, raster.getWidth() * (raster.getHeight() - 1));
	    	} else {
	    		// scroll downwards
	        	System.arraycopy(imageData, 0, imageData, raster.getWidth(), raster.getWidth() * (raster.getHeight() - 1));
	    	}
	    	
	    	final int fftSize = (int) fftGen.getSize();
	    	currentLine %= this.fftData.length;
	    	
	    	// saving memory at the cost of extra allocations when changing the FFT size often.
	    	if (this.fftData[currentLine].length != fftSize) {
	    		this.fftData[currentLine] = new float[fftSize];
	    	}
	    	
	    	MemorySegment.copy(fftGen.fft_out, ValueLayout.JAVA_FLOAT, 0, this.fftData[currentLine], fftSize / 2, fftSize / 2);
	    	MemorySegment.copy(fftGen.fft_out, ValueLayout.JAVA_FLOAT, fftSize * Float.BYTES / 2, this.fftData[currentLine], 0, fftSize / 2);
	    	fftSizes[currentLine] = fftSize;
	    	drawFftLine(imageData, currentLine, 0);
	    	
	    	currentLine = (currentLine + 1) % this.fftData.length;
	    	
	    	repaint();
		} finally {
			resizingLock.readLock().unlock();
		}
	}
    
    private void drawFftLine(int imageData[], int fftIdx, int yPos) {
    	float dataRange = waterfallMax - waterfallMin;
    	    	
    	int waterfallWidth = waterfallImage.getWidth();
    	int yIdx = waterfallImage.getWidth() * yPos;
    	if (waterfallWidth == 0)
    		return;
    	
    	float[] fftLineBuf = this.fftData[fftIdx];
    	int fftSize = fftSizes[fftIdx]; // fftLineBuf.length;
    	
    	int drawDataSize = (int) (fftSize * zoomFactor);
    	int drawDataStart = (int) (((double) fftSize / 2.0) * (offsetFactor + 1) - (drawDataSize / 2));
    	
    	final VectorSpecies<Float> species = FloatVector.SPECIES_PREFERRED;
    	
    	double searchCursor = (double) drawDataStart;
    	double searchStep = ((double) drawDataSize / waterfallWidth);
    	
    	for (int px = 0; px < waterfallWidth; px++) {
    		float maxVal = -Float.MAX_VALUE;
    		int searchStart = (int) (searchCursor);
    		searchCursor += searchStep;
    		int searchEnd = Math.min(fftSize - 1, (int) Math.ceil(searchCursor));
    		
			// int simdEnd = species.loopBound(searchEnd); // this would need aligned searchStart
			int simdDiff = searchEnd - searchStart;
			int simdCount = simdDiff - simdDiff % species.length();
			int simdEnd = searchStart + simdCount;
			
			// FloatVector vr = FloatVector.broadcast(species, maxVal);
			if (simdCount > 0 && true) {
				FloatVector vr = FloatVector.fromArray(species, fftLineBuf, searchStart);
				searchStart += species.length();
				for (; searchStart < simdEnd; searchStart += species.length()) {
					var vi = FloatVector.fromArray(species, fftLineBuf, searchStart);
	    			vr = vr.max(vi);
				}
				maxVal = vr.reduceLanes(VectorOperators.MAX);
			}
			
			for (; searchStart < searchEnd; searchStart++) {
    			float curVal = fftLineBuf[searchStart];
    			if (curVal > maxVal) {
    				maxVal = curVal;
    			}
    		}
			
       		float ampl = maxVal;
			float pixel = (Math.clamp(ampl, waterfallMin, waterfallMax) - waterfallMin) / dataRange;

			int paletteIdx = Math.clamp((int) (pixel * palette.length), 0, palette.length - 1);
			imageData[yIdx + px] = /* px > waterfallWidth - 20 ? 0x00FF00 : */ palette[paletteIdx];
    	}
    }
    
    public void doFullUpdate() {
    	zoomWorkAvailable.set(true);
    	synchronized (zoomWorkAvailable) {
    		zoomWorkAvailable.notify();
		}
    }
    
    private void doFullUpdateWork() {
    	WritableRaster raster = waterfallImage.getRaster();
    	DataBuffer rasterBuf = raster.getDataBuffer();
    	int[] imageData = ((DataBufferInt) rasterBuf).getData();
    	
    	long t1 = System.nanoTime();
    	for (int i = this.fftData.length - 1; i > 0; i--) {
    		if (zoomWorkAvailable.get()) {
    			// System.out.println("Bailing");
    			return;
    		}
    		drawFftLine(imageData, (currentLine + i) % this.fftData.length, raster.getHeight() - 1 - i);    		
    	}
    	long took = System.nanoTime() - t1;
    	// System.out.println("Full update took: " + (took / 1000000) + " ms");
    	//repaint();
    }
    
    public void setViewOffset(double offset) {
    	this.offsetFactor = offset;
    	if (this.offsetFactor + this.zoomFactor > 1.0) {
    		this.offsetFactor = 1.0 - this.zoomFactor;
    	}
    	
    	if (this.offsetFactor - this.zoomFactor < -1.0) {
    		this.offsetFactor = -1.0 + this.zoomFactor;
    	}
    	
    	// this.offsetFactor = Math.clamp(this.offsetFactor, -this.zoomFactor, this.zoomFactor);
    	
    	doFullUpdate();
    	repaint();
    }
    
    public double getViewOffset() {
    	return this.offsetFactor;
    }
    
    public void setZoomLevel(double zoomLevel) {
    	zoomLevel = Math.clamp(zoomLevel, 0.0, 1.0);

        double factor = zoomLevel * zoomLevel * zoomLevel;
        
        // Clip offset
        // this.offsetFactor = Math.clamp(this.offsetFactor, factor / 2.0, 1.0 - factor / 2.0);
        
        this.zoomFactor = factor;
        this.setViewOffset(this.offsetFactor);

        doFullUpdate();
        
        repaint();
    }

    public void setMinValue(float minValue) {
        this.waterfallMin = Math.min(minValue, this.waterfallMax);
        doFullUpdate();
    }

    public void setMaxValue(float maxValue) {
        this.waterfallMax = Math.max(maxValue, this.waterfallMin);
        doFullUpdate();
        repaint();
    }
    
    public float getMinValue() {
    	return this.waterfallMin;
    }
    
    public float getMaxValue() {
    	return this.waterfallMax;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the buffered image
        g.drawImage(waterfallImage, 0, 0, null);
    }
    
    public void updatePalette(int[] colors) {
    	float[][] frac = new float[colors.length][3];
    	for (int i = 0; i < colors.length; i++) {
    		frac[i][0] = (colors[i] & 0xFF);
    		frac[i][1] = ((colors[i] >> 8) & 0xFF);
    		frac[i][2] = ((colors[i] >> 16) & 0xFF);
    	}
    	
    	// System.out.println(" ==== ");
    	for (int i = 0; i < palette.length; i++) {
    		
    		int lowerId = (int) Math.floor(((float) i / palette.length) * colors.length);
    		int upperId = (int) Math.ceil(((float) i / palette.length) * colors.length);
    		lowerId = Math.clamp(lowerId, 0, colors.length - 1);
    		upperId = Math.clamp(upperId, 0, colors.length - 1);
    		float ratio = (((float) i / palette.length) * colors.length) - lowerId;
            float r = (frac[lowerId][0] * (1.0f - ratio)) + (frac[upperId][0] * ratio);
            float g = (frac[lowerId][1] * (1.0f - ratio)) + (frac[upperId][1] * ratio);
            float b = (frac[lowerId][2] * (1.0f - ratio)) + (frac[upperId][2] * ratio);
            palette[i] = ((int) b << 16) | ((int) g << 8) | ((int) r);
            if (i % 10000 == 0) {
            	// System.out.println(i + ": lowerId: " + lowerId + " upperId: " + upperId + " ratio: " + ratio + " / " + r + " / " + g + " / " + b);
            }
    	}
    }

	@Override
	public void componentResized(ComponentEvent e) {
		float[][] newData = Arrays.copyOf(this.fftData, this.getHeight());
		for (int i = this.fftData.length; i < newData.length; i++) { // fill the new empty slots with float buffers
			newData[i] = new float[fftSizes[currentLine]]; // the current line seems like a good starting point for the buffer size
		}
		
		resizingLock.writeLock().lock();
		try {
			// TODO: We should shuffle the data in the buffers around according to currentLine, else there's initially a moving gap in the waterfall.
			this.waterfallImage = new BufferedImage(this.getWidth(), this.getHeight(), this.waterfallImage.getType());
			this.fftData = newData;
			this.fftSizes = Arrays.copyOf(this.fftSizes, this.getHeight());
			currentLine = Math.min(currentLine, this.fftData.length);
		} finally {
			resizingLock.writeLock().unlock();
		}
		
		doFullUpdate();
	}

	@Override
	public void componentMoved(ComponentEvent e) {		
	}

	@Override
	public void componentShown(ComponentEvent e) {		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		
	}

 
}
