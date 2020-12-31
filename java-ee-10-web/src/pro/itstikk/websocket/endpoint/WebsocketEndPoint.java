package pro.itstikk.websocket.endpoint;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import pro.itstikk.thread.runner.NotificationWorker;

@ServerEndpoint(value="/endpoint")
public class WebsocketEndPoint {
	private static Logger logger = Logger.getLogger(WebsocketEndPoint.class.getName());
	
	@OnOpen
    public void onOpen(Session session) {
		logger.log(Level.INFO,"onOpen::" + session.getId());
		String info = "Hello Client " + session.getId() + "!";
		logger.log(Level.INFO,info);
		
		// start notification listener
		
		NotificationWorker runnable = new NotificationWorker(session);
		Thread thread = new Thread(runnable, "Notification Thread");
		thread.start();
		logger.log(Level.INFO, "Listener for the notification started.");
		
		
    }
	
    @OnClose
    public void onClose(Session session) {
    	String str = "onClose::" +  session.getId();
        logger.log(Level.INFO, str);
    }
    
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
		String str = " Server Site Message= " + message;
		logger.log(Level.INFO,str);
		session.getBasicRemote().sendText("Hello Client ... !");
    }
    
    @OnError
    public void onError(Throwable t) {
        logger.log(Level.ALL,"onError:: occured"+t.getMessage());
    }
    
}
