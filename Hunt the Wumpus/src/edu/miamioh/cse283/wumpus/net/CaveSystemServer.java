package edu.miamioh.cse283.wumpus.net;

import java.net.InetAddress;

public class CaveSystemServer extends CaveServer {
	
	/**
	 * NETWORKING CODE:
	 * 
	 * recv connections <-- client
	 * handoff connections --> servers
	 * 
	 * manage new cave servers connecting
	 */
	
	/**
	 * Hands off the client to a new (different) server.
	 * 
	 * @param addr is the address for the server.
	 * @param port is the port number for the server.
	 */
	public void handoff(InetAddress addr, int port){
		
	}
}
