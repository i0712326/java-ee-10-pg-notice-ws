package pro.itstikk.listener;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.impossibl.postgres.api.jdbc.PGConnection;
import com.impossibl.postgres.api.jdbc.PGNotificationListener;
import com.impossibl.postgres.jdbc.PGDataSource;

public class NotificationListener {
	private static Logger logger = Logger.getLogger(NotificationListener.class.getName());
	
	// Create the queue that will be shared by the producer and consumer
	private static BlockingQueue<String> queue = new ArrayBlockingQueue<String>(100);
	
	// Get database info from environment variables
	private static final String DBHost 		= "192.168.230.8";
	private static final String DBName 		= "sammy";
	private static final String DBUserName 	= "sammy";
	private static final String DBPassword 	= "qf48d8uv";
	private static final int 	DBPort 		= 5432;
	
	// Database connection
	// private static PGDataSource dataSource;
	// private static PGConnection connection;
	
	static {

		try {
			
			// Create a data source for logging into the db
			PGDataSource dataSource = new PGDataSource();
			dataSource.setHost(DBHost);
			dataSource.setPort(DBPort);
			dataSource.setDatabase(DBName);
			dataSource.setUser(DBUserName);
			dataSource.setPassword(DBPassword);

			// Log into the db
			PGConnection connection = (PGConnection) dataSource.getConnection();
			
			logger.log(Level.ALL, "Connection to PostgreSQL Successful !");
			System.out.println("Connection to PostgreSQL Successful !");
			// add the callback listener created earlier to the connection
			connection.addNotificationListener(new PGNotificationListener() {
				@Override
				public void notification(int processId, String channelName, String payload) {
					// Add event and payload to the queue
					queue.add("/channels/" + channelName + " " + payload);
				}
			});
			logger.log(Level.ALL, "Attached Listener to the Connection Successful !");
			System.out.println("Attached Listener to the Connection Successful !");
			// Tell PostgreSQL to send NOTIFY q_event to our connection and listener
			Statement statement = connection.createStatement();
			statement.execute("LISTEN queue_event");
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public NotificationListener() {
		super();
	}
	
	/**
	 * @return shared queue
	 */
	public static BlockingQueue<String> getQueue() {
		return queue;
	}
}
