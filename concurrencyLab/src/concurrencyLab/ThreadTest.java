package concurrencyLab;

import java.util.ArrayList;

public class ThreadTest {

	public class SmallThread implements Runnable {
		int index;
		ThreadTest server;

		public SmallThread(int i, ThreadTest server) {
			index = i;
			this.server = server;
		}

		@Override
		public void run() {
			synchronized (server) {
				int c = server.getCounter();
				System.out.println("Hello world " + index);
				System.out.println("  counter value: " + c);
				server.putCounter(c + 1);
			}
		}
	}

	protected int counter;

	public void putCounter(int c) {
		counter = c;
	}

	public int getCounter() {
		return counter;
	}

	public void serve(String[] args) {
		ArrayList<Thread> list = new ArrayList<Thread>();
		for (int i = 0; i < 100; i++) {
			SmallThread t = new SmallThread(i, this);
			Thread thread = new Thread(t);
			list.add(thread);
			thread.start();
		}

		for (Thread t : list) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		ThreadTest test = new ThreadTest();
		test.serve(args);
	}
}
