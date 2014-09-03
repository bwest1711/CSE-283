package edu.miamioh.cse283.lab2;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Template server for CSE283 Lab2, FS2014.
 * 
 * This server should respond to a client with a sequence of packets sent at a rate and size determined by the client.
 * 
 * @author dk
 */
public class Lab2Server {
	public static final int PORT = 4242;

	public static void main(String[] args) throws IOException {
		DatagramSocket s = null;

		try {
			// construct a datagram socket listening on port PORT:
			s = new DatagramSocket(PORT);

			// for convenience, the server should tell us what addresses it's listening on;
			// see DatagramSocket.getLocalSocketAddress() and InetAddress.getLocalHost().

			System.out.println("Lab2Server listening on: " + s.getLocalSocketAddress());

			// you will probably want to output something like:
			// "Lab2Server listening on: <ip address>:<port>"

			while (true) {
				// 5 bytes for the header information
				byte[] b = new byte[5];
				DatagramPacket p = new DatagramPacket(b, b.length);
				// receive a datagram packet that tells the server how many packets to send, their size in bytes, and their rate:
				ByteArrayInputStream bis = new ByteArrayInputStream(b);

				byte rate = (byte) bis.read();
				short size = (short) bis.read(b, 1, 2);
				short numPackets = (short) bis.read(b, 3, 2);
				
				// for each packet you're supposed to send:

				// - assemble the packet

				// - wait the right amount of time to hit the requested sending rate
				// see: Object.wait(long millis) and the concurrency lesson listed in the lab description

				// - send the packet
				// end loop
				break;
			}
		} catch (SocketException ex) { // this will not compile until you start filling in the socket code
			System.out.println("Could not open socket (is the server already running?).");
			ex.printStackTrace();
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}
}
