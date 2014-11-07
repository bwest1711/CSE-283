package edu.miamioh.cse283.wumpus;

import java.util.Scanner;

public class Player implements Moveable {
	private Scanner in;
	
	public Player(){
		in = new Scanner(System.in);
	}
	
	public String getInput(){
		return in.nextLine();
	}
}
