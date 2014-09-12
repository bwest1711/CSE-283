package edu.miamioh.cse283;

public class AMPProtocol {

	public String processInput(String input) {
		String toReturn = "";
		switch (input) {
		case "":
			toReturn = "This is a test";
			break;
		case "GET WORK":
			toReturn = "This is a test";
			break;
		case "PUT ANSWER":
			toReturn = "This is a test";
			break;
		case "AMP WORK":
			toReturn = "This is a test";
			break;
		case "AMP NONE":
			toReturn = "This is a test";
			break;
		case "AMP OK":
			toReturn = "This is a test";
			break;
		case "AMP ERROR":
			toReturn = "This is a test";
			break;
		}

		return toReturn;
	}
}
