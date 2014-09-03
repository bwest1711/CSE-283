package edu.miamioh.cse283.lab2;

import java.io.*;
import java.net.*;
import java.util.*;

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
			

			// you will probably want to output something like:
			// "Lab2Server listening on: <ip address>:<port>"

			while (true) {
				// receive a datagram packet that tells the server how many packets to send, their size in bytes, and their rate:

				// for each packet you're supposed to send:

				// - assemble the packet

				// - wait the right amount of time to hit the requested sending rate
				// see: Object.wait(long millis) and the concurrency lesson listed in the lab description

				// - send the packet
				// end loop
			}
		} catch (SocketException ex) { // this will not compile until you start filling in the socket code
			System.out.println("Could not open socket (is the server already running?).");
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}
}
