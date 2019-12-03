package ServerSocket;


import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/ws")
public class ServerSocket {
	private static int currentID = 1;
	private int thisID;
	private Server server;
	
	@OnOpen
	public void open(Session session) {
		if(server == null) {
			server = new Server();
		}
		
		System.out.println("Connection made!");
		thisID = currentID++;
		server.open(thisID, session);
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		server.onMessage(thisID, message);
		//server.onMessage(thisID, "ID: " + thisID);
	}
	
	@OnClose
	public void close(Session session) {
		System.out.println("Disconnecting!");
		server.close(thisID);
	}
	
	@OnError
	public void error(Throwable error) {
		System.out.println("Error!");
	}
}
