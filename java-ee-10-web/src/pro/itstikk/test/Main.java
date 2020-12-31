package pro.itstikk.test;

import java.util.concurrent.BlockingQueue;

import pro.itstikk.listener.NotificationListener;

public class Main {

	public static void main(String[] args) {
		NotificationListener ln = new NotificationListener();
		// Get the shared queue
		BlockingQueue<String> queue = ln.getQueue();

		// Loop forever pulling messages off the queue
		while (true) {
			try {
				// queue blocks until something is placed on it
				String msg = queue.take();

				// Do something with the event
				System.out.println(msg);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
