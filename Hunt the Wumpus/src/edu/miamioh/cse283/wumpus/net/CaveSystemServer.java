package edu.miamioh.cse283.wumpus.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CaveSystemServer {

	protected ServerSocket clientSocket;

	public void run(int portBase) throws IOException {
		clientSocket = new ServerSocket(portBase);
		
		while(true){
			Socket client = clientSocket.accept();
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		CaveSystemServer s = new CaveSystemServer();
		s.run(Integer.parseInt(args[0]));
	}
}
