package edu.miamioh.cse283.wumpus.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CaveSystemServer {

	/** Inner class to handle multiple threads */
	public class ServerThread implements Runnable {
		protected Socket client;

		public ServerThread(Socket client) {
			this.client = client;
		}

		@Override
		public void run() {
			try {
				// next addr == localhost
				// next port == portbase + 1
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				
				// Send the info back to the Client
				out.println("localhost");
				out.println("" + 1235);
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
			(new Thread(new ServerThread(client))).start();
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		System.out.println("Starting CaveSystemServer");
		System.out.println("  CSS: args[0] = " + args[0]);
		CaveSystemServer s = new CaveSystemServer();
		s.run(Integer.parseInt(args[0]));
	}
}
