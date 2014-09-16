package edu.miamioh.cse283;

import java.util.Random;

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
			toReturn = AMPWork();
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
	
	private String AMPWork(){
		int firstNum, secondNum, i, ans;
		String opp = "";
		Random random = new Random(System.currentTimeMillis());
		firstNum = random.nextInt(500);
		secondNum = random.nextInt(500);
		i = random.nextInt(4);
		
		switch(i) {
		case 0:
			opp = "+";
			ans = firstNum + secondNum;
			break;
		case 1:
			opp = "-";
			ans = firstNum - secondNum;
			break;
		case 2:
			opp = "*";
			ans = firstNum * secondNum;
			break;
		case 3:
			opp = "/";
			ans = firstNum / secondNum;
			break;
		}
		
		return firstNum + " " + opp + " " + secondNum;
	}
}
