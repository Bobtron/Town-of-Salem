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
	
	@OnOpen
	public void open(Session session) {
		System.out.println("Connection made!");
		thisID = currentID++;
		Server.open(thisID, session);
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		//System.out.println(message);
		Server.onMessage(thisID, message);
	}
	
	@OnClose
	public void close(Session session) {
		System.out.println("Disconnecting!");
		Server.close(thisID);
	}
	
	@OnError
	public void error(Throwable error) {
		System.out.println("Error!");
	}
}
