package edu.miamioh.cse283.wumpus.net;

import java.net.InetAddress;

public class Client {
	/**
	 * Plays the game
	 * 
	 * @param args - Holds the address and port number for 
	 * the CaveSystemServer this client will connect to
	 */
	public void run(String[] args){
		
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
	
	public static void main(String[] args) {
		Client c = new Client();
		c.run(args);
	}
}
