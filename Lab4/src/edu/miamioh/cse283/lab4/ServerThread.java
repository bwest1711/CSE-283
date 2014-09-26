package edu.miamioh.cse283.lab4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread implements Runnable {

	public static final String GET_WORK = "GET WORK";
	public static final String PUT_ANSWER = "PUT ANSWER";
	public static final String AMP_WORK = "AMP WORK";
	public static final String AMP_NONE = "AMP NONE";
	public static final String AMP_OK = "AMP OK";
	public static final String AMP_ERROR = "AMP ERROR";
	public static final String GET_STATUS = "GET STATUS";
	public static final String AMP_STATUS = "AMP STATUS";
	public static final String END_SESSION = "END SESSION";

	private int correct = 0;
	private Socket client;
	private int nwork;

	ServerThread(Socket c, int n) {
		client = c;
		nwork = n;
	}

	@Override
	public void run() {
		try {
			// loop "forever":
			while (true) {
				// and build the reader and writer:
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

				String line;
				Double expectedAns = 0.0;
				int count = 0;

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
						}
					} else if (line.startsWith(PUT_ANSWER)) { // client is sending an answer:
						// read the client's answer (use "in"):
						String answer = in.readLine();
						System.out.println("  ANSWER: " + answer);
						// if the client's answer matches our expected answer:
						if (expectedAns == Double.parseDouble(answer)) {
							System.out.println("  CORRECT");
							increment();
						} else {
							System.out.println("  INCORRECT");
						}
						System.out.println("  RESPONSE: OK");

						// respond with OK (use "out"):
						out.println(AMP_OK);
					} else if (line.startsWith(GET_STATUS)) {
						out.println(AMP_STATUS);
						String status = "  THREADS: ";
						status += Thread.activeCount();
						status += ", CORRECT: ";
						status += getCorrect();
						status += ", INCORRECT: ";
						status += nwork - getCorrect();
						out.println(status);
					} else if (line.startsWith(END_SESSION)) {
						continue;
						// client.close();
					} else {
						// garbled input from the client; respond with AMP_ERROR (use "out"):
						out.println(AMP_ERROR);
						System.out.println("  RESPONSE: ERROR");
					}
				}

				System.out.println("---- END: " + getCorrect() + " OF " + nwork + " CORRECT RESPONSES ----");
				break;
			}
		} catch (IOException ex) {
			System.out.println("EXCEPTION: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public synchronized void increment() {
		correct++;
	}

	public synchronized int getCorrect() {
		return this.correct;
	}

	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(Integer.parseInt(args[0]));

			while (true) {
				Socket client = server.accept();
				(new Thread(new ServerThread(client, Integer.parseInt(args[1])))).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}