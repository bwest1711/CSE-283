package edu.miamioh.cse283.lab2;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
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

	public static void main(String[] args) throws IOException, InterruptedException {
		DatagramSocket s = null;
		Object o = new Object();

		try {
			// construct a datagram socket listening on port PORT:
			s = new DatagramSocket(PORT);

			// for convenience, the server should tell us what addresses it's listening on;
			// see DatagramSocket.getLocalSocketAddress() and InetAddress.getLocalHost().

			System.out.println("Lab2Server listening on: " + s.getLocalSocketAddress());

			// you will probably want to output something like:
			// "Lab2Server listening on: <ip address>:<port>"
			boolean isDone = false;
			while (!isDone) {
				// 5 bytes for the header information
				byte[] b = new byte[5];
				DatagramPacket p = new DatagramPacket(b, b.length);
				// receive a datagram packet that tells the server how many packets to send, their size in bytes, and their rate:
				s.receive(p);
				ByteArrayInputStream bis = new ByteArrayInputStream(b);
				DataInputStream dis = new DataInputStream(bis);

				byte rate = dis.readByte();
				short size = dis.readShort();
				short numPackets = dis.readShort();
				
				System.out.println("Rate: " + rate);
				System.out.println("Size: " + size);
				System.out.println("Num Packets: " + numPackets);
				// for each packet you're supposed to send:

				// - assemble the packet

				// - wait the right amount of time to hit the requested sending rate
				// see: Object.wait(long millis) and the concurrency lesson listed in the lab description
				int i = 0;
				synchronized(o){
					o.wait(rate);
					System.out.println(i);
					i++;
				}
					
				// - send the packet
				// end loop
				if(numPackets == 0)
					isDone = true;
				else
					numPackets--;
			}
		} catch (SocketException | InterruptedException | IllegalMonitorStateException ex) { // this will not compile until you start filling in the socket code
			System.out.println("Could not open socket (is the server already running?).");
			ex.printStackTrace();
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}
}
