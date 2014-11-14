package edu.miamioh.cse283.wumpus.net;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import edu.miamioh.cse283.wumpus.Room;
import edu.miamioh.cse283.wumpus.net.proxy.CaveSystemServerProxy;
import edu.miamioh.cse283.wumpus.net.proxy.ClientProxy;

/**
 * The CaveServer class takes the following command-line parameters:
 * 
 * <Hostname of CaveSystemServer> <port number of CaveSystemServer> <port number of this CaveServer>
 * 
 * E.g., "localhost 1234 2000"
 */
public class CaveServer {

	/** Port base for this cave server. */
	protected int portBase;

	private final String startMessage = "   _____  _                 _                   _                    _            _                    _ \n"
			+ "  |_   _|| |_   __ _  _ _  | |__  __ _  ___  __| |  _  _  ___  _  _ ( )_ _  ___  | |_   ___  _ _  ___ | |\n"
			+ "    | |  | ' \\ / _` || ' \\ | / / / _` |/ _ \\/ _` | | || |/ _ \\| || ||/| '_|/ -_) | ' \\ / -_)| '_|/ -_)|_|\n"
			+ "    |_|  |_||_|\\__,_||_||_||_\\_\\ \\__, |\\___/\\__,_|  \\_, |\\___/ \\_,_|  |_|  \\___| |_||_|\\___||_|  \\___|(_)\n"
			+ "                                 |___/              |__/                                                 \n\n" + " SHOOT - shoots an arrow\n"
			+ "PICKUP - picks up whatever gold/arrows are on the ground\n" + " CLIMB - climbs the ladder to exit the cave\n"
			+ "  MOVE - followed by a number to move to a different room\n";

	/** Socket for accepting connections from players. */
	protected ServerSocket clientSocket;

	/** Proxy to the CaveSystemServer. */
	protected CaveSystemServerProxy caveSystem;

	/** Rooms in this CaveServer. */
	protected ArrayList<Room> rooms;

	/** Simple room layout */
	private String[][] room = { { "1", "2", "6", "5", "GOLD" }, { "2", "3", "8", "1", "NONE" }, { "3", "4", "10", "2", "NONE" }, { "4", "5", "12", "3", "NONE" },
			{ "5", "1", "14", "4", "GOLD" }, { "6", "7", "1", "15", "BATS" }, { "7", "8", "17", "6", "NONE" }, { "8", "9", "2", "7", "NONE" }, { "9", "10", "18", "8", "NONE" },
			{ "10", "11", "3", "9", "NONE" }, { "11", "12", "19", "10", "NONE" }, { "12", "13", "4", "11", "BATS" }, { "13", "14", "20", "12", "NONE" },
			{ "14", "15", "5", "13", "NONE" }, { "15", "6", "16", "14", "NONE" }, { "16", "17", "15", "20", "NONE" }, { "17", "18", "7", "16", "GOLD" },
			{ "18", "19", "9", "17", "NONE" }, { "19", "20", "11", "18", "NONE" }, { "20", "16", "13", "19", "PIT" } };

	/** Constructor. */
	public CaveServer(CaveSystemServerProxy caveSystem, int portBase) {
		this.caveSystem = caveSystem;
		this.portBase = portBase;
		rooms = new ArrayList<Room>();
		for (int i = 0; i < 20; ++i) {
			rooms.add(new Room(room[i][0], room[i][1], room[i][2], room[i][3], room[i][4]));
		}
	}

	/** Returns the port number to use for accepting client connections. */
	public int getClientPort() {
		return portBase;
	}

	public String sense(int room) {
		int[] adjRooms = rooms.get(room).getNeighbors();

		String toReturn = "You sense ";

		for (int i : adjRooms) {
			switch(rooms.get(i).sense()) {
			case "NONE":
				continue;
			case "GOLD":
				toReturn += "a shimmer of gold ";
				break;
			case "PIT":
				toReturn += "the whistle of a pit ";
				break;
			case "BATS":
				toReturn += "the flapping of bats ";
			}
		}
		
		return toReturn;
	}

	/** This is the thread that handles a single client connection. */
	public class ClientThread implements Runnable {
		/** This is our "client" (actually, a proxy to the network-connected client). */
		protected ClientProxy client;

		/** Constructor. */
		public ClientThread(ClientProxy client) {
			this.client = client;
		}

		/**
		 * Play the game with this client.
		 */
		public void run() {
			try {
				Random r = new Random();
				int roomNum = r.nextInt(20);

				client.message("Welcome!");

				// put the player in a room (any room is fine)
				// now, in a loop while the player is alive:
				while (true) {
					// -- send the player their "senses":
					client.message(rooms.get(roomNum).getPath());

					client.senses(sense(roomNum));
					// -- and retrieve their action:
					String action = client.getAction();
					System.out.println(action);
					action = action.toUpperCase();

					if (action.startsWith("MOVE")) {
						String num = action.substring(5);
						roomNum = Integer.parseInt(num.trim());
					}

					// -- and perform the action
				}

			} catch (Exception ex) {
				// If an exception is thrown, we can't fix it here -- Crash.
				ex.printStackTrace();
				System.exit(1);
			}
		}
	}

	/** Runs the CaveSystemServer. */
	public void run() {
		try {
			clientSocket = new ServerSocket(getClientPort());
			caveSystem.register(clientSocket);

			while (true) {
				// and now loop forever, accepting client connections:
				while (true) {
					ClientProxy client = new ClientProxy(clientSocket.accept());
					(new Thread(new ClientThread(client))).start();
				}
			}
		} catch (Exception ex) {
			// If an exception is thrown, we can't fix it here -- Crash.
			ex.printStackTrace();
			System.exit(1);
		}
	}

	/** Main method (run the CaveServer). */
	public static void main(String[] args) throws Exception {
		InetAddress addr = InetAddress.getByName(args[0]);
		int cssPortBase = Integer.parseInt(args[1]);
		int cavePortBase = Integer.parseInt(args[2]);

		// first, we need our proxy object to the CaveSystemServer:
		CaveSystemServerProxy caveSystem = new CaveSystemServerProxy(new Socket(addr, cssPortBase + 1));

		// now construct this cave server, and run it:
		CaveServer cs = new CaveServer(caveSystem, cavePortBase);
		cs.run();
	}
}
