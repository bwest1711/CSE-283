package edu.miamioh.cse283.lab2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Template client for CSE283 Lab2, FS2014.
 * 
 * This client should read the following from the command line: 
 * 1) the remote address for the server 
 * 2) the number of packets that should be requested from the server 
 * 3) the size of those packets 
 * 4) the sending rate of those packets
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
		
		String address = args[0];
		String numPackets = args[1];
		String size = args[2];
		String rate = args[3];

		// Construct a socket to use for communication (see: DatagramSocket):
		DatagramSocket s = null;
		try {
			s = new DatagramSocket(PORT);

			// assemble the first packet to communicate the packet stream parameters to the server:

			byte[] b = new byte[5];
			b[0] = (byte) 250;
			
			
			DatagramPacket p = new DatagramPacket(b, b.length);
			// send it:

			// receive a bunch of packets from the server:

			// calculate bytes/second (see System.currentTimeMillis() or System.nanoTime())
			double throughput = 0.0;
			System.out.println("Measured throughput is: " + throughput + " bytes/second");

			// calculate packet loss:
			double packetLoss = 0.0;
			System.out.println("Packet loss averages: " + packetLoss + "packets/second");

		} finally {
			// close the socket:
			if (s != null) {
				s.close();
			}
		}
	}
}
