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
 * This client should read the following from the command line: 1) the remote address for the server 2) the number of packets that should be requested
 * from the server 3) the size of those packets 4) the sending rate of those packets
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
		String _delay = args[1];
		String _numPackets = args[2];
		String _size = args[3];

		// Display the input
		System.out.println("Rate: " + _delay + " ms");
		System.out.println("Size: " + _size + " bytes");
		System.out.println("Number of Packets: " + _numPackets);

		DatagramSocket socket = null;

		// Keeps track of the number of packets actually received
		short receivedPackets = 0;
		try {
			socket = new DatagramSocket();

			// assemble the first packet to communicate the packet stream parameters to the server:
			byte delay = Byte.parseByte(_delay);
			short size = Short.parseShort(_size);
			short numPackets = Short.parseShort(_numPackets);

			// Tell the socket how long to wait before timing out
			socket.setSoTimeout((int) 1000 * delay + 5000);

			// Create and add header information
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DataOutputStream dout = new DataOutputStream(bout);

			dout.write(delay);
			dout.writeShort(numPackets);
			dout.writeShort(size);
			dout.flush();

			byte[] b = new byte[5];
			b = bout.toByteArray();

			InetAddress ip = InetAddress.getByName(_address);

			DatagramPacket sendPacket = new DatagramPacket(b, b.length, ip, PORT);

			socket.send(sendPacket);

			// Keep track of how long it takes for packets to arrive
			boolean isAllPackets;
			long endTime;
			long startTime = System.currentTimeMillis();

			while (true) {
				// A byte array of specified size (from args)
				byte[] receivedByteArray = new byte[size];

				// The receiving packet
				DatagramPacket received = new DatagramPacket(receivedByteArray, receivedByteArray.length);

				/*
				 * If the socket times out it will throw an exception. We want to handle this by assuming this means no more packets are coming and
				 * there are lost packets.
				 */
				try {
					// Attempt to receive the packet
					socket.receive(received);
					endTime = System.currentTimeMillis();
					receivedPackets++;

					// Determine if we are done receiving packets
					if (receivedPackets == numPackets) {
						System.out.println("Received all packets.");
						isAllPackets = true;
						break;
					}
				} catch (Exception e) {
					endTime = System.currentTimeMillis();

					System.out.println("Only received: " + receivedPackets);
					isAllPackets = false;
					break;
				}
			}

			// Account for server timeout
			if (!isAllPackets) {
				endTime -= (delay * 1000) + 5000;
			}

			long totalTime = (endTime - startTime);

			long totalBytes = receivedPackets * size;
			int lostPackets = numPackets - receivedPackets;

			double throughput = (double) totalBytes / totalTime;
			double packetLoss = (double) lostPackets / totalTime;

			throughput *= 1000;
			packetLoss *= 1000;

			System.out.println("Total time taken: " + totalTime + " ms");
			System.out.println("Measured throughput is: " + throughput + " bytes/second");
			System.out.println("Packet loss averages: " + packetLoss + " packets/second");

		} catch (Exception e) {
			System.out.println("Something failed");
			e.printStackTrace();
		} finally {
			if (socket != null) {
				socket.close();
			}
		}
	}
}
