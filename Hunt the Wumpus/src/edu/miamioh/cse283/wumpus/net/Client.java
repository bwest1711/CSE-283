package edu.miamioh.cse283.wumpus.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

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
	/** Scanner for player input */
	protected Scanner scanner = new Scanner(System.in);

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
		System.out.println("Connecting to: " + server.getInetAddress().getHostAddress());
		System.out.println("      On Port: " + server.getPort());
		getStreams(server);

		// IP and Port from CSS
		InetAddress caveAddr = InetAddress.getByName(in.readLine());
		int cavePort = Integer.parseInt(in.readLine());

		// Close connection to CSS
		System.out.println("Disconnecting: " + server.getInetAddress().getHostAddress());
		System.out.println("    From Port: " + server.getPort());
		server.close();

		

		// Start connecting to the Cave Server
		cave = new Socket(caveAddr, cavePort);
		System.out.println("Connecting to: " + cave.getInetAddress().getHostAddress());
		System.out.println("      On Port: " + cave.getPort());
		getStreams(cave);

		player = new Player();

		String input, output;

		// Handle input/output from the user/server
		// Server currently leads with single message that tells how many lines are coming
		while ((input = in.readLine()) != null) {
			int num = Integer.parseInt(input); // How many lines

			// Read in from the server the specified number of lines
			for (int i = 0; i < num; i++) {
				input = in.readLine();
				System.out.println("[Server] - " + input);
			}

			System.out.print("[Client] - ");
			if ((output = scanner.nextLine()) != null) {
				out.println(output);
			}
		}
	}

	/**
	 * Gets the input and output streams for the given socket. Assumes the connection to the socket has been connected
	 * 
	 * @param s
	 *            the socket to get the streams for
	 * @throws IOException
	 *             if the socket is not connected, there will not be and input or output stream
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
		System.out.println("  Client:      args[0] = " + args[0]);
		System.out.println("  Client:      args[1] = " + args[1]);
		Client c = new Client();
		c.run(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));
	}
}
