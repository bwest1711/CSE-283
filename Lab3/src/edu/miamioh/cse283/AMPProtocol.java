package edu.miamioh.cse283;

public class AMPProtocol {

	public String processInput(String input) {
		String toReturn = "";
		switch (input) {
		case "":
			toReturn = "This is a test";
			break;
		case "GET WORK":
			toReturn = "Get Work Method";
			break;
		case "PUT ANSWER":
			toReturn = "Put Answer Method";
			break;
		case "AMP WORK":
			toReturn = "Amp Work Method";
			break;
		case "AMP NONE":
			toReturn = "Amp None Method";
			break;
		case "AMP OK":
			toReturn = "Amp Ok Method";
			break;
		case "AMP ERROR":
			toReturn = "Amp Error Method";
			break;
		}

		return toReturn;
	}
}
