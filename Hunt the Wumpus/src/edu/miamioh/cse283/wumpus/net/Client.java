package edu.miamioh.cse283.wumpus.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import edu.miamioh.cse283.wumpus.Player;

public class Client {

	/** Socket for communication */
	protected Socket server;
	/** Current cave connected to Client */
	protected Socket cave;

	/** The player */
	protected Player player;

	/** Input stream */
	protected BufferedReader in;
	/** Output stream */
	protected PrintWriter out;

	/**
	 * Runs the game
	 * 
	 * @param addr
	 *            the ip address of the server
	 * @param port
	 *            the port number of the server
	 * @throws IOException
	 */
	public void run(InetAddress addr, int port) throws IOException {
		server = new Socket(addr, port);
		getStreams(server);

		// IP and Port from CSS
		InetAddress caveAddr = InetAddress.getByName(in.readLine());
		int cavePort = Integer.parseInt(in.readLine());

		System.out.println("Connecting to: " + caveAddr);
		System.out.println("      On Port: " + cavePort);

		// Close connection to CSS
		server.close();

		cave = new Socket(caveAddr, cavePort);
		getStreams(cave);

		String input;

		while ((input = in.readLine()) != null) {
			System.out.println(input);
		}
	}

	/**
	 * Gets the input and output streams for the given socket.
	 * 
	 * @param s
	 *            the socket to get the streams for
	 * @throws IOException
	 */
	private void getStreams(Socket s) throws IOException {
		// Make sure to close old streams
		if (in != null) {
			in.close();
		}

		if (out != null) {
			out.close();
		}

		// Get new streams based on the socket.
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		System.out.println("Starting Client");
		System.out.println("  Client: args[0] = " + args[0]);
		System.out.println("  Client: args[1] = " + args[1]);
		Client c = new Client();
		c.run(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));
	}
}
