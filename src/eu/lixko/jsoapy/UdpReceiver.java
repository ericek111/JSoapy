package eu.lixko.jsoapy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class UdpReceiver {
	public static void main(String[] args) throws IOException {
		ByteBuffer rawBuf = ByteBuffer.allocate(16384 * Float.BYTES * 2);
		
		
		DatagramSocket socket = new DatagramSocket(6501);
		long t1 = System.nanoTime();
		long totalFetched = 0;
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
			System.out.println("Received: " + packet.getLength() + " / " + rawBuf);
		}
		
	}
}
