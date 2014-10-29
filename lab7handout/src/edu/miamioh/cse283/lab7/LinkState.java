package edu.miamioh.cse283.lab7;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * Software network template for CSE283 Lab 7, FS2014.
 * 
 * @author dk
 */
public class LinkState {

	/**
	 * The link state matrix of distances.
	 * 
	 * M[i][j] is the cost of the link from node i to node j.
	 */
	protected double[][] D;

	/** Constructor. */
	public LinkState(int n) {
		D = new double[n][];
		for (int i = 0; i < n; ++i) {
			D[i] = new double[n];
			Arrays.fill(D[i], Double.POSITIVE_INFINITY);
			D[i][i] = 0;
		}
	}

	/**
	 * Adds a bidirectional link with the specified cost.
	 */
	public void link(int i, int j, double w) {
		D[i][j] = w;
		D[j][i] = w;
	}

	/**
	 * Runs Dijkstra's algorithm on the distance matrix M.
	 */
	public void calculate_shortest_paths() {

		for (int u = 0; u < D.length; u++) {
			// u is our source

			// Subset of nodes: if a node v is in Nprime
			// We know that the least cost path from our u -> v is known
			TreeSet<Integer> Nprime = new TreeSet<Integer>();

			Nprime.add(u);
			// already have direct connection costs

			while (Nprime.size() < D.length) {
				// Find w not in Nprime such that D(w) is a min
				Double dw = Double.POSITIVE_INFINITY;
				int w = -1;

				for (int i = 0; i < D[u].length; i++) {
					if (!Nprime.contains(i) && D[u][i] < dw) {
						dw = D[u][i];

						w = i;
					}
				}

				// When we get to here we know:
				// w is the next node being added to Nprime
				Nprime.add(w);

				// when the network is disconnected, w will be -1

				// updating D(v) for each neighbor v of w not in N'0
				for (int v = 0; v < D.length; v++) {
					if (!Nprime.contains(v)) {
						D[u][v] = Math.min(D[u][v], D[u][w] + D[w][v]);
					}
				}
			}
		}
	}

	/**
	 * Prints out the distance matrix M.
	 */
	public void print() {
		for (int i = 0; i < D.length; ++i) {
			for (int j = 0; j < D.length; ++j) {
				if (D[i][j] == Double.POSITIVE_INFINITY) {
					System.out.print("*    ");
				} else {
					System.out.printf("%-4.1f ", D[i][j]);
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Main method.
	 */
	public static void main(String[] args) {
		LinkState ls = new LinkState(6);
		
		ls.link(0, 1, 2.0);
		ls.link(0, 2, 1.0);
		ls.link(0, 3, 5.0);
		
		ls.link(1, 2, 2.0);
		ls.link(1, 3, 3.0);
		
		ls.link(2, 3, 3.0);
		ls.link(2, 4, 1.0);
		
		ls.link(3, 4, 1.0);
		ls.link(3, 5, 5.0);
		
		ls.link(4, 5, 2.0);
		
		System.out.println("Initial cost matrix:");
		ls.print();

		System.out.println("Shortest paths:");
		ls.calculate_shortest_paths();
		ls.print();
	}
}
