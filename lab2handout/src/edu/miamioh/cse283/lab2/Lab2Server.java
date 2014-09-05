package edu.miamioh.cse283.lab2;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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

			// 5 bytes for the header information
			byte[] b = new byte[5];
			DatagramPacket p = new DatagramPacket(b, b.length);

			// receive a datagram packet that tells the server how many packets to send, their size in bytes, and their rate:
			s.receive(p);
			ByteArrayInputStream bis = new ByteArrayInputStream(b);
			DataInputStream dis = new DataInputStream(bis);
			InetAddress ip = p.getAddress();
			
			byte rate = dis.readByte();
			short size = dis.readShort();
			short numPackets = dis.readShort();

			System.out.println("Rate: " + rate);
			System.out.println("Size: " + size);
			System.out.println("Num Packets: " + numPackets);

			int i = 0;
			byte[] sending = new byte[size];
			
			for(short j = 0; j < size; j++){
				sending[j] = (byte) 1;
			}
			

			DatagramPacket toSend;

			while (true) {
				// for each packet you're supposed to send:

				toSend = new DatagramPacket(sending, sending.length, ip, PORT);

				// - assemble the packet

				// - wait the right amount of time to hit the requested sending rate
				// see: Object.wait(long millis) and the concurrency lesson listed in the lab description
				synchronized (o) {
					o.wait(rate);
					i++;
					s.send(toSend);
				}
				
				// - send the packet
				// end loop
				if (numPackets == 0)
					break;
				else {
					System.out.println(numPackets);
					numPackets--;
				}
			}
		} catch (SocketException | InterruptedException | IllegalMonitorStateException | NullPointerException ex) { // this will not compile until you start filling in the socket code
			System.out.println("Could not open socket (is the server already running?).");
			ex.printStackTrace();
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}
}
