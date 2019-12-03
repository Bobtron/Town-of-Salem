package ServerSocket;

import java.io.IOException;

import javax.websocket.Session;

public class ServerThread extends Thread {
	
	private Session session;
	
	public ServerThread(Session session) {
		this.session = session;
	}
	
	public void onMessage(String message) {
		this.start();
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				session.getBasicRemote().sendText("testing");
				this.sleep(1000);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
