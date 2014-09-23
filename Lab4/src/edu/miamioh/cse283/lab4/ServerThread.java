package edu.miamioh.cse283.lab4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import edu.miamioh.cse283.lab3.MathProblem;

public class ServerThread implements Runnable {
	
	public static final String GET_WORK = "GET WORK";
	public static final String PUT_ANSWER = "PUT ANSWER";
	public static final String AMP_WORK = "AMP WORK";
	public static final String AMP_NONE = "AMP NONE";
	public static final String AMP_OK = "AMP OK";
	public static final String AMP_ERROR = "AMP ERROR";
	
	Socket client;

	public ServerThread(Socket c) {
		client = c;
	}

	@Override
	public void run() {
		try {
			while (true) {
				// and build the reader and writer:
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

				String line;
				Double expectedAns = 0.0;
				int count = 0;
				int correct = 0;

				// loop while we are able to read lines from the client
				// (this is a safeguard against a client that terminates early):
				while ((line = in.readLine()) != null) {
					System.out.println("CLIENT REQUEST: " + line);

					// if the client is requesting work:
					if (line.startsWith(GET_WORK)) {
						// if we have more work to do:
						if (count < nwork) {
							// generate a math problem, and calculate the expected answer
							// (see MathProblem.generate and MathProblem.solve, in this package)
							String amp = MathProblem.generate();
							expectedAns = MathProblem.solve(amp);

							// send the work response to the client (use "out", don't forget the header):
							out.println(AMP_WORK);
							out.println(amp);
							System.out.println("  RESPONSE: " + amp);
							++count;
						} else { // all done; tell the client we're out of work:
							System.out.println("  RESPONSE: NONE");
							out.println(AMP_NONE);
							break;
						}
					} else if (line.startsWith(PUT_ANSWER)) { // client is sending an answer:
						// read the client's answer (use "in"):
						String answer = in.readLine();
						System.out.println("  ANSWER: " + answer);
						// if the client's answer matches our expected answer:
						if (expectedAns == Double.parseDouble(answer)) {
							System.out.println("  CORRECT");
							++correct;
						} else {
							System.out.println("  INCORRECT");
						}
						System.out.println("  RESPONSE: OK");

						// respond with OK (use "out"):
						out.println(AMP_OK);
					} else {
						// garbled input from the client; respond with AMP_ERROR (use "out"):
						out.println(AMP_ERROR);
						System.out.println("  RESPONSE: ERROR");
					}
				}

				client.close();
				System.out.println("---- END: " + correct + " OF " + nwork + " CORRECT RESPONSES ----");
				break;
			}
		} catch (SocketException ex) {
			// only get here if something went wrong
			System.out.println("EXCEPTION: " + ex.getMessage());
		} finally {
			if (server != null) {
				server.close();
			}
			if (client != null) {
				client.close();
			}
		}
	}

	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(10780);

			while (true) {
				Socket client = server.accept();
				(new Thread(new ServerThread(client))).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
