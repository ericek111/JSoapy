package eu.lixko.jsoapy;

import java.io.IOException;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

import eu.lixko.jsoapy.fft.LiquidDsp;

public class LiquidTest {
	
	public static void main(String[] args) {
		int num_channels = 100;
		float As = 80;     // stop-band attenuation
		int m = 16;// filter delay
		int h_len = 2*num_channels*m + 1;
		Object channelLock = new Object();
		AtomicInteger channelN = new AtomicInteger(99);
		// float[] h = new float[h_len];
		
		// Arena.global().
		// var h = java.nio.ByteBuffer.allocateDirect(h_len * 4).asFloatBuffer().array();
		/*var mem = Arena.global().allocate(ValueLayout.JAVA_FLOAT, h_len);
		LiquidDsp.liquid_firdes_kaiser(mem, 0.5f / num_channels, As, 0.0f);
		for (int i = 0; i < mem.byteSize() / Float.BYTES; i++) {
			System.out.println(mem.getAtIndex(ValueLayout.JAVA_FLOAT, i));
		} */
		
		Thread.ofVirtual().start(() -> {
			while(true) {
				try {
					int c = System.in.read();
					if (c == 97) { // a
						synchronized (channelLock) {
							channelN.set(Math.max(0, channelN.get() - 1));
						}
						System.out.println("Channel: " + channelN.get());
					} else if (c == 113) { // q
						synchronized (channelLock) {
							channelN.set(Math.min(num_channels - 1, channelN.get() + 1));
						}
						System.out.println("Channel: " + channelN.get());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		// var qa = LiquidDsp.firpfbch2_crcf_create_kaiser(0, num_channels, m, As);
		var qa = LiquidDsp.firpfbch_crcf_create_kaiser(0, num_channels, m, As);
		
		// var qs = LiquidDsp.firpfbch2_crcf_create_kaiser(1, num_channels, m, As);
		var in_buf = Arena.global().allocate(ValueLayout.JAVA_FLOAT, num_channels * 2);
		var tmp_buf = Arena.global().allocate(ValueLayout.JAVA_FLOAT, num_channels * 2);
		var chann_buf = Arena.global().allocate(ValueLayout.JAVA_FLOAT, num_channels * 2 * 16);
		
		
		ByteBuffer rawBuf = ByteBuffer.allocate(65536 * Float.BYTES * 2);
		// FloatBuffer fftOut = FloatBuffer.allocate((int) fftGen.getNativeBufferSize() / Float.BYTES);
		
		try {
    		DatagramSocket socket = new DatagramSocket(6501);
    		var sendAddr = InetAddress.getByName("127.0.0.1");
    		DatagramSocket sendSocket = new DatagramSocket();
    		long t1 = System.nanoTime();
    		long totalFetched = 0;
    		long fftInPos = 0; // bytes
    		long bufOutPos = 0;
    		
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
    			
    			    			
    			while (rawBuf.hasRemaining()) {

    			
        			long shouldCopy = Math.max(0, Math.min(in_buf.byteSize() - fftInPos, rawBuf.limit() - rawBuf.position()));
        			MemorySegment.copy(rawBuf.array(), rawBuf.position(), in_buf, ValueLayout.JAVA_BYTE, fftInPos, (int) shouldCopy);
        			fftInPos += shouldCopy;
        			rawBuf.position((int) (rawBuf.position() + shouldCopy));
        			// System.out.println(shouldCopy + " / " + rawBuf.position() + " pos: " + fftInPos + " max: " + fftGen.getNativeBufferSize());
        			
        			if (fftInPos >= in_buf.byteSize()) { // we have filled the input buffer            				
        				fftInPos = 0;
            			LiquidDsp.firpfbch_crcf_analyzer_execute(qa,
        					in_buf,
        					tmp_buf
        				);
            			
            			// chann_buf.asSlice(bufOutPos, num_channels * Float.BYTES * 2)
            			
            			final long sampleSize = Float.BYTES * 2;
            			synchronized (channelLock) {
            				MemorySegment.copy(tmp_buf, channelN.get() * sampleSize, chann_buf, bufOutPos, sampleSize);
            			}
            			bufOutPos += sampleSize;
            			
            			if (bufOutPos >= chann_buf.byteSize()) { // we have filled the output buffer
            				DatagramPacket outPacket = new DatagramPacket(chann_buf.toArray(ValueLayout.JAVA_BYTE), (int) chann_buf.byteSize());
            				outPacket.setAddress(sendAddr);
            				outPacket.setPort(6502);
            				sendSocket.send(outPacket);

            				bufOutPos = 0;
            			}
        			}
    			}
    		}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		
		// LiquidDsp.firpfbch2_crcf_print(qa);
		
	}
}
