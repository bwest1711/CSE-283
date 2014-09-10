package edu.miamioh.cse283.lab2;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Template client for CSE283 Lab2, FS2014.
 * 
 * This client should read the following from the command line: 1) the remote address for the server 2) the number of packets that should be requested from the server 3) the size
 * of those packets 4) the sending rate of those packets
 * 
 * @author dk
 */
public class Lab2Client {
	/** Port on which the server will be listening. */
	public static final int PORT = 4242;

	/**
	 * Runs the Lab2Client.
	 * 
	 * @param args
	 *            is an array containing each of the command-line arguments.
	 * @throws IOException
	 *             if there is a networking error.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 4) {
			System.out.println("Usage: java Lab1Client <inet address> <number> <size in bytes> <rate>");
			return;
		}

		// Read the parameters from the command line
		String _address = args[0];
		String _rate = args[1];
		String _size = args[2];
		String _numPackets = args[3];

		// Display the input 
		System.out.println("Rate: " + _rate + " ms");
		System.out.println("Size: " + _size + " bytes");
		System.out.println("Number of Packets: " + _numPackets);

		// Construct a socket to use for communication (see: DatagramSocket):
		DatagramSocket socket = null;
		short receivedPackets = 0;
		try {
			socket = new DatagramSocket();

			// assemble the first packet to communicate the packet stream parameters to the server:
			byte rate = Byte.parseByte(_rate);
			short size = Short.parseShort(_size);
			short numPackets = Short.parseShort(_numPackets);

			socket.setSoTimeout((int) 10 * rate);

			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DataOutputStream dout = new DataOutputStream(bout);

			dout.write(rate);
			dout.writeShort(size);
			dout.writeShort(numPackets);
			dout.flush();

			byte[] b = new byte[5];
			b = bout.toByteArray();

			InetAddress ip = InetAddress.getByName(_address);

			DatagramPacket sendPacket = new DatagramPacket(b, b.length, ip, PORT);

			socket.send(sendPacket);

			// receive a bunch of packets from the server:
			long startTime = System.currentTimeMillis();

			while (true) {
				byte[] receivedByteArray = new byte[size];
				DatagramPacket received = new DatagramPacket(receivedByteArray, receivedByteArray.length);
				try {
					socket.receive(received);
					Object o = new Object();

					synchronized (o) {
						o.wait(rate);
						receivedPackets++;
					}

					if (receivedPackets == numPackets) {
						System.out.println("Received all packets.");
						break;
					}
				} catch (Exception e) {
					System.out.println("Only received: " + receivedPackets);
					break;
				}
			}
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;

			long totalBytes = receivedPackets * size;
			int lostPackets = numPackets - receivedPackets;

			double throughput = (double) totalBytes / totalTime;
			double packetLoss = (double) lostPackets / totalTime;

			System.out.println("Total time taken: " + totalTime + " ms");
			System.out.println("Measured throughput is: " + throughput + " bytes/second");
			System.out.println("Packet loss averages: " + packetLoss + " packets/second");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				socket.close();
			}
		}
	}
}
