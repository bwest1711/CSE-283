package edu.miamioh.cse283.wumpus.net;

import java.net.InetAddress;

public class CaveServer extends Server{
	/**
	 * Networking code
	 * 
	 * send description --> client
	 * recv actions from clients
	 */
	
	/**
	 * Sends a description to the client.
	 * 
	 * @param desc the description being sent
	 * @param addr the address of the client.
	 * @param port the port of the client.
	 */
	public void sendDescription(String desc, InetAddress addr, int port){
		
	}
	
	/**
	 * Receives actions from the server
	 * 
	 * @param action the action to be performed.
	 */
	public void getAction(String action){
		
	}
}
