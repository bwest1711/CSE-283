package edu.miamioh.cse283.wumpus;

import java.util.ArrayList;

import edu.miamioh.cse283.wumpus.net.proxy.ClientProxy;

public class Room {
	public ArrayList<ClientProxy> players;

	public Room() {
		players = new ArrayList<ClientProxy>();
	}
}
