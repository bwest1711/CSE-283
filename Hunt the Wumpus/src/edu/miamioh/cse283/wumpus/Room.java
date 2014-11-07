package edu.miamioh.cse283.wumpus;

public class Room {
	private boolean hasGold, hasWumpus, hasPlayer;
	private final boolean hasBats, hasPit;

	public Room(boolean hasBats, boolean hasPit) {
		this.hasBats = hasBats;
		this.hasPit = hasPit;
	}

	public boolean hasGold() {
		return hasGold;
	}

	public void setHasGold(boolean hasGold) {
		this.hasGold = hasGold;
	}

	public boolean hasWumpus() {
		return hasWumpus;
	}

	public void setHasWumpus(boolean hasWumpus) {
		this.hasWumpus = hasWumpus;
	}

	public boolean hasPlayer() {
		return hasPlayer;
	}

	public void setHasPlayer(boolean hasPlayer) {
		this.hasPlayer = hasPlayer;
	}

	public boolean hasBats() {
		return hasBats;
	}

	public boolean hasPit() {
		return hasPit;
	}
}
