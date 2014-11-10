package edu.miamioh.cse283.wumpus.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import edu.miamioh.cse283.wumpus.Room;

public class CaveServer {
	/** Inner class to handle multiple threads */
	public class CaveThread implements Runnable {

		/** The list of rooms */
		protected ArrayList<Room> rooms;

		/** Connection to the player */
		protected Socket client;

		/** The message that is displayed when a player enters the cave */
		private final String startMessage = "   _____  _                 _                   _                    _            _                    _ \n"
				+ "  |_   _|| |_   __ _  _ _  | |__  __ _  ___  __| |  _  _  ___  _  _ ( )_ _  ___  | |_   ___  _ _  ___ | |\n"
				+ "    | |  | ' \\ / _` || ' \\ | / / / _` |/ _ \\/ _` | | || |/ _ \\| || ||/| '_|/ -_) | ' \\ / -_)| '_|/ -_)|_|\n"
				+ "    |_|  |_||_|\\__,_||_||_||_\\_\\ \\__, |\\___/\\__,_|  \\_, |\\___/ \\_,_|  |_|  \\___| |_||_|\\___||_|  \\___|(_)\n"
				+ "                                 |___/              |__/                                                 \n\n" + " SHOOT - shoots an arrow\n"
				+ "PICKUP - picks up whatever gold/arrows are on the ground\n" + " CLIMB - climbs the ladder to exit the cave\n"
				+ "  MOVE - followed by a number to move to a different room\n";

		/**
		 * Creates a new instance of the cave system.
		 * 
		 * @param client
		 *            the socket connected to the client
		 */
		public CaveThread(Socket client) {
			this.client = client;
			init();
		}

		private void init() {
			rooms = new ArrayList<Room>();
			for(int i = 0; i < 20; i++){
				Room temp = new Room(i, false, false);
				rooms.add(temp);
			}
		}

		/**
		 * Determine how many lines a string contains.
		 * 
		 * @param message
		 * @return number of lines
		 */
		private int getNumLines(String message) {
			char[] c = message.toCharArray();
			int toReturn = 1;

			for (char letter : c) {
				if (letter == '\n')
					toReturn++;
			}

			return toReturn;
		}

		@Override
		public void run() {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);

				out.println(getNumLines(startMessage));
				out.println(startMessage);

				String input;

				// Loop for input from user
				while ((input = in.readLine()) != null) {
					System.out.println("[Client] " + input);
					out.println(1);
					switch (input.toUpperCase().trim()) {
					case "SHOOT":
						out.println("You shot me!"); // This can become Player.shoot(direction)
						break;
					case "PICKUP":
						out.println("You found gold!"); // Player.pickup();
						break;
					case "MOVE":
						out.println("You moved somewhere!"); // Player.move(direction)
						break;
					case "CLIMB":
						out.println("You climbed stuff!"); // Player.climb()
						break;
					default:
						out.println("Not a valid command!");
						break;
					}
				}

			} catch (IOException e) {
				System.err.println(e.getMessage());
				//e.printStackTrace();
			}
		}
	}

	/** Listening Socket for new client connections */
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
		System.out.println("  CaveServer:  args[0] = " + args[0]);
		CaveServer s = new CaveServer();
		s.run(Integer.parseInt(args[0]));
	}
}
