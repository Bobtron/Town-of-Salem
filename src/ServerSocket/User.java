package ServerSocket;

import java.net.Socket;

import javax.websocket.Session;

public class User {
	String username;
	Session s;
	int index;
	String imageURL;
	
	
	public User(String username, Session s, String imageURL) {
		super();
		this.username = username;
		this.s = s;
		this.imageURL = imageURL;
	}
	
	
	
	public String getImageURL() {
		return imageURL;
	}



	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
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
