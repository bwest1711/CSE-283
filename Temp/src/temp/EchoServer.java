package temp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	public static void main(String[] args) throws Exception {
		ServerSocket s = new ServerSocket(Integer.parseInt(args[0]));
		while (true) {
			Socket client = s.accept();

			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			System.out.println(in.readLine());
		}
	}
}
