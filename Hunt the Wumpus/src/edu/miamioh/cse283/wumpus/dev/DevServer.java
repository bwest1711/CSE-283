package edu.miamioh.cse283.wumpus.dev;

import edu.miamioh.cse283.wumpus.net.CaveServer;
import edu.miamioh.cse283.wumpus.net.CaveSystemServer;
import edu.miamioh.cse283.wumpus.net.Client;

public class DevServer {
	public static void main(String[] args) throws Exception {
		Object lock = new Object();
		synchronized (lock) {
			new Thread(new CaveSystemServerDevel()).start();
			lock.wait(500);
			new Thread(new CaveServerDevel()).start();
			lock.wait(500);
			new Thread(new ClientDevel()).start();
		}
	}

	public static class CaveSystemServerDevel implements Runnable {
		public void run() {
			String[] caveSystemServerArgs = { "1234" };
			try {
				CaveSystemServer.main(caveSystemServerArgs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static class CaveServerDevel implements Runnable {
		public void run() {
			String[] caveServerArgs = { "localhost", "1234", "2000" };
			try {
				CaveServer.main(caveServerArgs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static class ClientDevel implements Runnable {
		public void run() {
			String[] clientArgs = { "localhost", "1234" };
			try {
				Client.main(clientArgs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
