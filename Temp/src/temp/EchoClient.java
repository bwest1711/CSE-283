package temp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class EchoClient {
	public static void main(String[] args) throws Exception {
		Socket s = new Socket(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		PrintWriter out = new PrintWriter(s.getOutputStream(), true);

		out.println("hello world");
		System.out.println(in.readLine());
	}
}
