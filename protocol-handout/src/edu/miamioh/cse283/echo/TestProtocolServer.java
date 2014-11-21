package edu.miamioh.cse283.echo;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//! Example server to use for testing a line-based protocol.
public class TestProtocolServer {

	//! ServerThread -- One instance of this class per client.
	public class ServerThread implements Runnable {
		protected Socket s; //!< Socket, connected to the client.
		protected BufferedReader in; //!< Input reader.
		protected PrintWriter out; //!< Output writer.

		//! Constructor.
		public ServerThread(Socket s) throws IOException {
			this.s = s;
			try {
				this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				this.out = new PrintWriter(s.getOutputStream(), true);
			} catch(IOException ex) {
				try { s.close(); } catch(Exception ex2) { }
				throw ex;
			}
		}

		//! Run method for this thread.
		public void run() {
			try {
				while(true) {
					String line = in.readLine().trim().toLowerCase();
					out.println(line);
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			} finally {
				try { 
					s.close();
				} catch(Exception ex) {
				}
			}
		}
	}

	/** Run this server. */
	public void run() {
		ServerSocket srv=null;
		try {
			srv = new ServerSocket(3000);
			while(true) {
				Socket client = srv.accept();
				try {
					(new Thread(new ServerThread(client))).start();
				} catch(IOException ex) {
				}
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		} finally {
			if(srv != null) {
				try { 
					srv.close();
				} catch(IOException ex) {
				}
			}
			System.exit(1);
		}
	}

	/** Main method (takes no command line parameters). */
	public static void main(String[] args) throws Exception {
		TestProtocolServer s = new TestProtocolServer();
		s.run();
	}
}
