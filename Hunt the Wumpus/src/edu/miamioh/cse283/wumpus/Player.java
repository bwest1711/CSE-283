package edu.miamioh.cse283.wumpus;

import java.util.Scanner;

public class Player implements Moveable {
	private final Scanner in = new Scanner(System.in);

	public String getInput() {
		return in.nextLine();
	}
	
	public boolean hasInput(){
		return in.hasNext();
	}

}
