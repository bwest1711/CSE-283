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
		private final String startMessage = "   _____  _                 _                   _                    _            _                    _ \n"
				+ "  |_   _|| |_   __ _  _ _  | |__  __ _  ___  __| |  _  _  ___  _  _ ( )_ _  ___  | |_   ___  _ _  ___ | |\n"
				+ "    | |  | ' \\ / _` || ' \\ | / / / _` |/ _ \\/ _` | | || |/ _ \\| || ||/| '_|/ -_) | ' \\ / -_)| '_|/ -_)|_|\n"
				+ "    |_|  |_||_|\\__,_||_||_||_\\_\\ \\__, |\\___/\\__,_|  \\_, |\\___/ \\_,_|  |_|  \\___| |_||_|\\___||_|  \\___|(_)\n"
				+ "                                 |___/              |__/                                                 \n";

		public CaveThread(Socket client) {
			this.client = client;
		}

		@Override
		public void run() {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);

				out.println(startMessage);

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
		// Listen on the base port
		clientSocket = new ServerSocket(portBase);

		while (true) {
			// Always wait for something to try to connect
			Socket client = clientSocket.accept();

			// Create a new thread and pass our client
			(new Thread(new CaveThread(client))).start();

		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		System.out.println("Starting CaveServer");
		System.out.println("  CaveServer: args[0] = " + args[0]);
		CaveServer s = new CaveServer();
		s.run(Integer.parseInt(args[0]));
	}
}
