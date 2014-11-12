package edu.miamioh.cse283.wumpus.net;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import edu.miamioh.cse283.wumpus.Room;
import edu.miamioh.cse283.wumpus.net.proxy.CaveSystemServerProxy;
import edu.miamioh.cse283.wumpus.net.proxy.ClientProxy;

public class CaveServer {

	/** Port base for this cave server. */
	protected int portBase;

	/** Socket for accepting connections from players. */
	protected ServerSocket clientSocket;

	/** Proxy to the CaveSystemServer. */
	protected CaveSystemServerProxy caveSystem;

	private final String startMessage = "   _____  _                 _                   _                    _            _                    _ \n"
			+ "  |_   _|| |_   __ _  _ _  | |__  __ _  ___  __| |  _  _  ___  _  _ ( )_ _  ___  | |_   ___  _ _  ___ | |\n"
			+ "    | |  | ' \\ / _` || ' \\ | / / / _` |/ _ \\/ _` | | || |/ _ \\| || ||/| '_|/ -_) | ' \\ / -_)| '_|/ -_)|_|\n"
			+ "    |_|  |_||_|\\__,_||_||_||_\\_\\ \\__, |\\___/\\__,_|  \\_, |\\___/ \\_,_|  |_|  \\___| |_||_|\\___||_|  \\___|(_)\n"
			+ "                                 |___/              |__/                                                 \n\n"
			+ " SHOOT - shoots an arrow\n" + "PICKUP - picks up whatever gold/arrows are on the ground\n"
			+ " CLIMB - climbs the ladder to exit the cave\n" + "  MOVE - followed by a number to move to a different room\n";

	/** Rooms in this CaveServer. */
	protected ArrayList<Room> rooms;

	/** Constructor. */
	public CaveServer(CaveSystemServerProxy caveSystem, int portBase) {
		this.caveSystem = caveSystem;
		this.portBase = portBase;
		rooms = new ArrayList<Room>();
		for (int i = 0; i < 20; ++i) {
			rooms.add(new Room());
		}
	}

	/** Returns the port number to use for accepting client connections. */
	public int getClientPort() {
		return portBase;
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
				client.message("Welcome!");

				// put the player in a room (any room is fine)

				// now, in a loop while the player is alive:
				// -- send the player their "senses":
				client.senses("You are in an empty room.");
				// -- and retrieve their action:
				String action = client.getAction();
				// -- and perform the action

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
		// first, we need our proxy object to the CaveSystemServer:
		CaveSystemServerProxy caveSystem = new CaveSystemServerProxy(new Socket(InetAddress.getByName(args[0]), Integer.parseInt(args[1]) + 1));

		// now construct this cave server, and run it:
		CaveServer cs = new CaveServer(caveSystem, Integer.parseInt(args[2]));
		cs.run();
	}
}
