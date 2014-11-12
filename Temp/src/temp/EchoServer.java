package temp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	public static class ClientProxy {
		Socket s;
		BufferedReader in;
		PrintWriter out;

		public ClientProxy(Socket s) throws Exception {
			this.s = s;
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(s.getOutputStream(), true);
		}

		public void echo() throws Exception {
			String message = in.readLine();
			System.out.println(message);
			out.println(message);
		}
	}

	public static void main(String[] args) throws Exception {
		ServerSocket s = new ServerSocket(Integer.parseInt(args[0]));
		while (true) {
			ClientProxy cp = new ClientProxy(s.accept());

			cp.echo();
		}
	}
}
