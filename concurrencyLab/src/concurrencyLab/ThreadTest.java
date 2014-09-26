package concurrencyLab;

public class ThreadTest {

	public class SmallThread implements Runnable {
		public SmallThread() {

		}

		@Override
		public void run() {

		}
	}

	public void serve(String[] args) {
		System.out.println("Hello world");
		SmallThread t = new SmallThread();
		
	}

	public static void main(String[] args) {
		ThreadTest test = new ThreadTest();
		test.serve(args);
	}
}
