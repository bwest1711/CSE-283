package edu.miamioh.cse283;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Lab3Client {
	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		String _address = args[0];
		String _port = args[1];
		int port = Integer.parseInt(_port);

		try (Socket s = new Socket(_address, port);
				PrintWriter out = new PrintWriter(s.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));) {
			String fromServer, fromUser;
			while ((fromServer = in.readLine()) != null) {
				System.out.println("Server: " + fromServer);
				if (fromServer.equals("Bye."))
					break;

				fromUser = stdin.nextLine();
				if (fromUser != null) {
					System.out.println("Client: " + fromUser);
					out.println(fromUser);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
