package edu.miamioh.cse283;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Lab3Server {
	public static void main(String[] args) {
		try (
				ServerSocket serverSocket = new ServerSocket(10780);
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				) {

			String inputLine, outputLine, work;

			// Initiate conversation with client
			AMPProtocol amp = new AMPProtocol();

			while ((inputLine = in.readLine()) != null) {
				outputLine = amp.processInput(inputLine);
				out.println(outputLine);
				if (outputLine.equals("AMP WORK")) {
					work = MathProblem.generate();
					out.println(work);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}
}
