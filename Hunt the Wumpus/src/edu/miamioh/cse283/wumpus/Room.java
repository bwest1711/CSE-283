package edu.miamioh.cse283.wumpus;

import java.util.ArrayList;

import edu.miamioh.cse283.wumpus.net.proxy.ClientProxy;

public class Room {
	public ArrayList<ClientProxy> players;
	private final int roomNum, roomOne, roomTwo, roomThree;
	private final String danger;

	public Room(String roomNum, String roomOne, String roomTwo, String roomThree, String danger) {
		players = new ArrayList<ClientProxy>();

		this.roomNum = Integer.parseInt(roomNum) - 1;
		this.roomOne = Integer.parseInt(roomOne) - 1;
		this.roomTwo = Integer.parseInt(roomTwo) - 1;
		this.roomThree = Integer.parseInt(roomThree) - 1;
		this.danger = danger;
	}

	public String getPath() {
		return "You are in room " + roomNum + " there are paths to rooms " + roomOne + ", " + roomTwo + ", and " + roomThree;
	}
	
	public int[] getNeighbors() {
		int [] toReturn = {roomOne, roomTwo, roomThree};
		return toReturn;
	}

	public String sense() {
		return danger;
	}
}
