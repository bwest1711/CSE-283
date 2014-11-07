package edu.miamioh.cse283.wumpus.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CaveSystemServer {

	/** Inner class to handle multiple threads */
	public class ServerThread implements Runnable{
		protected Socket client;
		
		public ServerThread(Socket client) {
			this.client = client;
		}
		
		@Override
		public void run() {
			
		}
	}
	
	protected ServerSocket clientSocket;

	/**
	 * Run from a non static context.
	 * 
	 * @param portBase
	 * @throws IOException temporary until we change to try-catch
	 */
	public void run(int portBase) throws IOException {
		clientSocket = new ServerSocket(portBase);
		
		while(true){
			Socket client = clientSocket.accept();
			
			// Create a new thread and pass our client
			(new Thread(new ServerThread(client))).start();
			
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		CaveSystemServer s = new CaveSystemServer();
		s.run(Integer.parseInt(args[0]));
	}
}
