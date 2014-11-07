package edu.miamioh.cse283.wumpus.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

	/** Socket for communication */
	protected Socket server;
	protected Socket cave;
	
	protected BufferedReader in;
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
		
		InetAddress caveAddr = InetAddress.getByName(in.readLine());
		int cavePort = Integer.parseInt(in.readLine());
		
		System.out.println("Got shit from the CaveSystemServer");

		server.close();

		cave = new Socket(caveAddr, cavePort);
		getStreams(cave);
		
		System.out.println(in.readLine());

	}

	/**
	 * Gets the input and output streams for the given socket.
	 * 
	 * @param s the socket to get the streams for
	 * @throws IOException 
	 */
	private void getStreams(Socket s) throws IOException {
		if(in != null){
			in.close();
		}
		
		if(out != null){
			out.close();
		}
		
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream());
	}

	/**
	 * Connect this client to the server.
	 * 
	 * @param addr
	 *            is the address for the server.
	 * @param port
	 *            is the port number for the server.
	 */
	public void connect(InetAddress addr, int port) {

	}

	/**
	 * Hands off the client to a new (different) server.
	 * 
	 * @param addr
	 *            is the address for the server.
	 * @param port
	 *            is the port number for the server.
	 */
	public void handoff(InetAddress addr, int port) {

	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		Client c = new Client();
		c.run(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));
	}
}
