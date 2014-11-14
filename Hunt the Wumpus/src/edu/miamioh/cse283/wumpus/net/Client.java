package edu.miamioh.cse283.wumpus.net;

import java.net.InetAddress;
import java.net.Socket;

import edu.miamioh.cse283.wumpus.Player;
import edu.miamioh.cse283.wumpus.net.proxy.CaveProxy;

/**
 * Client (player) for the Hunt the Wumpus game.
 * 
 * The Client class takes the following command-line parameters:
 * 
 * <Hostname of CaveSystemServer> <port number of CaveSystemServer>
 * 
 * E.g., "localhost 1234"
 *
 */
public class Client {

	/** Proxy object that connects the client to its current cave. */
	protected CaveProxy cave;
	private Player player;

	/** Constructor. */
	public Client(CaveProxy cave) {
		this.cave = cave;
		this.player = new Player();
	}

	/** Returns true if the player is still alive. */
	public synchronized boolean isAlive() {
		return true;
	}

	/**
	 * Plays the game.
	 * 
	 * @param args
	 *            holds address and port number for the CaveSystemServer this client will connect to.
	 */
	public void run() {
		try {
			// all clients initially experience a handoff:
			cave = cave.handoff();
			System.out.println(cave.getMessage());
			// now start the sense and respond loop:
			while (isAlive()) {
				// get an action from the player, and
				// send it to the cave server.
				System.out.println(cave.getSenses());
				if (player.hasInput())
					cave.sendAction(player.getInput());

			}

		} catch (Exception ex) {
			// If an exception is thrown, we can't fix it here -- Crash.
			ex.printStackTrace();
			System.exit(1);
		}
	}

	public Player getPlayer() {
		return player;
	}

	/**
	 * Main method for clients.
	 * 
	 * @param args
	 *            contains the hostname and port number of the server that this client should connect to.
	 */
	public static void main(String[] args) throws Exception {
		InetAddress addr = InetAddress.getByName(args[0]);
		int cavePortBase = Integer.parseInt(args[1]);

		CaveProxy cave = new CaveProxy(new Socket(addr, cavePortBase));
		Client c = new Client(cave);
		c.run();
	}
}
