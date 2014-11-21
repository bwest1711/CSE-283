package edu.miamioh.cse283.echo;

import java.io.*;
import java.net.*;

public class TestProtocolClient {
	
	//! Proxy class for the TestProtocolServer.
	public class TestProtocolServerProxy {
		protected Socket s; //!< Socket connecting this client to the server.
		protected BufferedReader in; //!< Input reader.
		protected PrintWriter out; //!< Output writer.
		
		//! Constructor.
		public TestProtocolServerProxy(Socket s) throws Exception {
			this.s = s;
			try {
				this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				this.out = new PrintWriter(s.getOutputStream(), true);
			} catch(IOException ex) {
				try { s.close(); } catch(Exception ex2) { }
				throw ex;
			}
		}
		
		//! Close this connection to the server.
		public void close() {
			try { s.close(); } catch(Exception ex) { }
		}
		
		//! Prints a line to the server.
		public void println(String msg) throws IOException {
			out.println(msg);
		}

		//! Returns the next line that can be read.
		public String nextLine() throws IOException {
			return in.readLine().trim();
		}

		//! Returns true if there is data waiting to be read.
		public boolean ready() throws IOException {
			return in.ready();
		}
	}
	
	//! Run this client.
	public void run() {
		TestProtocolServerProxy server=null;
		try {
			Socket s = new Socket(InetAddress.getByName("localhost"), 3000);
			server = new TestProtocolServerProxy(s);
			InputScanner input = new InputScanner();

			while (true) {
				while (!server.ready() && !input.ready()) {
					try { Thread.sleep(50);	} catch (InterruptedException ex) {	}
				}

				if(input.ready()) {
					server.println(input.nextLine());
				}
					
				if(server.ready()) {
					System.out.println(server.nextLine());
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			server.close();
		}
	}
	

	/** Main method (takes no command line parameters). */
	public static void main(String[] args) throws Exception {
		TestProtocolClient c = new TestProtocolClient();
		c.run();
	}		
}
