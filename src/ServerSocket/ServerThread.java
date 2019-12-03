package ServerSocket;

import java.io.IOException;

import javax.websocket.Session;

public class ServerThread extends Thread {
	
	private Session session;
	
	public ServerThread(Session session) {
		this.session = session;
	}
	
	public void onMessage(String message) {
		sendText(message);
	}
	
	public void sendText(String message) {
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(true) {
			
		}
	}
	
}
