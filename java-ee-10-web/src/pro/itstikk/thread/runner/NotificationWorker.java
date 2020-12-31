package pro.itstikk.thread.runner;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.Session;


import pro.itstikk.listener.NotificationListener;

public class NotificationWorker implements Runnable {
	private static Logger logger = Logger.getLogger(NotificationWorker.class.getName());
	// Get the shared queue
	private static BlockingQueue<String> queue = NotificationListener.getQueue();
	private Session session;
	
	public NotificationWorker(final Session session) {
		this.session = session;
	}
	
	@Override
	public void run() {
		// start notification listener
		logger.log(Level.ALL,"Notification listener is running ......!");
		
		// Loop forever pulling messages off the queue
		while (true) {
			try {
				// queue blocks until something is placed on it
				String msg = queue.take();

				// Do something with the event
				logger.log(Level.ALL,"PostgreSQL Notification :"+msg);
				// Send message through Websocket to client
				session.getAsyncRemote().sendText(msg);

			} catch (InterruptedException e) {
				logger.log(Level.ALL, "Error occured while processing message"+e.getMessage());
				e.printStackTrace();
			}
		}
	}

}
