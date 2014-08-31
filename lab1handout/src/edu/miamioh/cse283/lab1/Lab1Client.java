package edu.miamioh.cse283.lab1;

import java.io.*;
import java.net.*;

/**
 * Template client for CSE283 Lab1, FS2014.
 * 
 * This client should read a remote address and one integer from the command
 * line, and send a single datagram packet containing that integer to the
 * remote address on port 4242.  The client should then check for a response
 * from the server, which will also be a single integer in a datagram packet.
 * Both integers should be echoed to the console.
 * 
 * @author dk
 */
public class Lab1Client {
	/** Buffer size for packets received from the server. */
	public static final int BUFFER_SIZE=256;
	
	/** Port on which the server will be listening. */
	public static final int PORT=4242;

	/**
	 * Runs the Lab1Client.
	 * 
	 * @param args is an array containing each of the command-line arguments.
	 * @throws IOException if there is a networking error.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("Usage: java Lab1Client <inet address> <integer>");
			return;
		}

		// Construct a socket to use for communication (see: DatagramSocket):

		try {
			// pack the integer into a byte array (see: ByteArrayOutputStream,
			// DataOutputStream):
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			int t = Integer.parseInt(args[1]); // get from args[1]
			bout.write(t);

			// build a DatagramPacket containing integer t:

			InetAddress ia = InetAddress.getByName(args[0]);
			
			byte[] b = new byte[8];

			DatagramPacket dp = new DatagramPacket(bout.toByteArray(), bout.toByteArray().length);
			DatagramSocket ds = new DatagramSocket(PORT, ia);
			ds.send(dp);

			// send the packet to address args[0] on port PORT (see:
			// InetAddress):

			// echo it to the console (don't change this):
			System.out.print("sent: " + t);

			// receive a response (see: DataInputStream, ByteArrayInputStream):
			DatagramPacket dp2 = new DatagramPacket(b, b.length, ia, PORT);
			ds.receive(dp2);
			ByteArrayInputStream bs = new ByteArrayInputStream(dp2.getData());
			int i = bs.read();
	
			// echo to console (don't change this either):
			int r = i; // get from packet
			System.out.println("; received: " + r);

			ds.close();
		} finally {

		}
	}
}
