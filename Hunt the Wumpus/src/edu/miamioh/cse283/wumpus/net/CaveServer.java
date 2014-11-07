package edu.miamioh.cse283.wumpus.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CaveServer {
	/** Inner class to handle multiple threads */
	public class CaveThread implements Runnable {
		protected Socket client;

		public CaveThread(Socket client) {
			this.client = client;
		}

		@Override
		public void run() {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				out.println("Thank god you're here!");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected ServerSocket clientSocket;

	/**
	 * Run from a non static context.
	 * 
	 * @param portBase
	 * @throws IOException
	 *             temporary until we change to try-catch
	 */
	public void run(int portBase) throws IOException {
		clientSocket = new ServerSocket(portBase);

		while (true) {
			Socket client = clientSocket.accept();

			// Create a new thread and pass our client
			(new Thread(new CaveThread(client))).start();

		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		CaveServer s = new CaveServer();
		s.run(Integer.parseInt(args[0]));
	}
}
