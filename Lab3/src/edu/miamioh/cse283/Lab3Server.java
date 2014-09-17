package edu.miamioh.cse283;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Lab3Server {
	public static void main(String[] args) {
		try (ServerSocket serverSocket = new ServerSocket(10780);
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {

			String inputLine, outputLine, math;

			// Initiate conversation with client
			AMPProtocol amp = new AMPProtocol();
			outputLine = amp.processInput("GET WORK");
			out.println(outputLine);

			while ((inputLine = in.readLine()) != null) {
				outputLine = amp.processInput(inputLine);
				out.println(outputLine);
				if (outputLine.equals("AMP WORK")) {
					math = AMPWork();
					out.println(math);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}

	private static String AMPWork() {
		Random random = new Random();
		int first, second, i;
		String opp = "";
		first = random.nextInt();
		second = random.nextInt();
		i = random.nextInt(4);

		switch (i) {
		case 0:
			opp = " + ";
			break;
		case 1:
			opp = " - ";
			break;
		case 2:
			opp = " / ";
			break;
		case 3:
			opp = " * ";
			break;
		}

		return first + opp + second;
	}
}
