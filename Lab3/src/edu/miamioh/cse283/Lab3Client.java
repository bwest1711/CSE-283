package edu.miamioh.cse283;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Lab3Client {
	public static void main(String[] args) {
		String _address = args[0];
		String _port = args[0];
		int port = Integer.parseInt(_port);

		try (
				Socket s = new Socket(_address, port);
				PrintWriter out = new PrintWriter(s.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));) {

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
