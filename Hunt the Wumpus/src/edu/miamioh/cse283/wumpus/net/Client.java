package edu.miamioh.cse283.wumpus.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
	
	protected Socket server;
	
	/**
	 * Runs the game
	 * @param addr the ip address of the server
	 * @param port the port number of the server
	 * @throws IOException 
	 */
	public void run(InetAddress addr, int port) throws IOException{
		server = new Socket(addr, port);
		System.out.println("Something happened.");		
	}
	
	/**
	 * Connect this client to the server.
	 * 
	 * @param addr is the address for the server.
	 * @param port is the port number for the server.
	 */
	public void connect(InetAddress addr, int port){
		
	}

	/**
	 * Hands off the client to a new (different) server.
	 * 
	 * @param addr is the address for the server.
	 * @param port is the port number for the server.
	 */
	public void handoff(InetAddress addr, int port){
		
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		Client c = new Client();
		c.run(InetAddress.getByName(args[0]),
				Integer.parseInt(args[1]));
	}
}
