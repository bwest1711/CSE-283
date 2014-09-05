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

		String address = args[0];
		String rate = args[1];
		String size = args[2];
		String numPackets = args[3];

		System.out.println(rate);
		System.out.println(size);
		System.out.println(numPackets);

		// Construct a socket to use for communication (see: DatagramSocket):
		DatagramSocket s = null;
		try {
			s = new DatagramSocket();

			// assemble the first packet to communicate the packet stream parameters to the server:
			byte[] b = new byte[5];
			byte r = Byte.parseByte(rate);
			short sz = Short.parseShort(size);
			short n = Short.parseShort(numPackets);

			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DataOutputStream dout = new DataOutputStream(bout);
			dout.write(r);
			dout.writeShort(sz);
			dout.writeShort(n);
			dout.flush();

			b = bout.toByteArray();

			InetAddress ip = InetAddress.getByName(address);

			DatagramPacket p = new DatagramPacket(b, b.length, ip, PORT);
			// send it:

			s.send(p);

			// receive a bunch of packets from the server:
			while (true) {
				byte[] b2 = new byte[sz];
				DatagramPacket received = new DatagramPacket(b2, b2.length, ip, PORT);
				s.receive(received);
				System.out.println("done");
				Object o = new Object();

				synchronized (o) {
					o.wait(r);
					n--;
				}

				if (n == 0)
					break;

			}

			// calculate bytes/second (see System.currentTimeMillis() or System.nanoTime())
			double throughput = 0.0;
			System.out.println("Measured throughput is: " + throughput + " bytes/second");

			// calculate packet loss:
			double packetLoss = 0.0;
			System.out.println("Packet loss averages: " + packetLoss + "packets/second");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close the socket:
			if (s != null) {
				s.close();
			}
		}
	}
}
