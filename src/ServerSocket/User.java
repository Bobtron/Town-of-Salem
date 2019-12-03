package ServerSocket;

import java.net.Socket;

import javax.websocket.Session;

public class User {
	String username;
	Session s;
	int index;
	
	
	public User(String username, Session s) {
		super();
		this.username = username;
		this.s = s;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Session getS() {
		return s;
	}
	public void setS(Session s) {
		this.s = s;
	}
}
