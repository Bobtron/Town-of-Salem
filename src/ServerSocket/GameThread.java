package ServerSocket;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.websocket.Session;


public class GameThread extends Thread {
	
	String gameName;
	int totalPlayers;
	int usersLeft;
	boolean started;
	boolean gameOver;
	
	ArrayList<User> users;
	ArrayList<Boolean> dead;
	ArrayList<Integer> mafiaIndex;
	ArrayList<Integer> townsPeopleIndex;
	
	
	public GameThread(String gameName, int totalUsers) {
		
		this.started = false;
		this.gameOver = false;
		
		this.gameName = gameName;
		this.totalPlayers = totalUsers;
		this.usersLeft = totalUsers;
		
		this.users = new ArrayList<User>();
		this.dead = new ArrayList<Boolean>();
		this.mafiaIndex = new ArrayList<Integer>();
		this.townsPeopleIndex = new ArrayList<Integer>();
				
		this.start();
	}
	
	public boolean hasSpace() {
		return !started && totalPlayers > users.size();
	}

	public String getGameName() {
		return gameName;
	}
	
	public synchronized void addUser(User user) {
		users.add(user);
		dead.add(false);
		
		if(users.size() == totalPlayers) {
			broadcastAll("All users have joined.");
		}else {
			broadcastAll("Waiting for " + (totalPlayers - users.size()) + " other user to join...");
		}
		
	}
	
	public void broadcastAll(String message) {
		try {
			for(int i = 0; i < users.size(); i++) {
				Session s = users.get(i).getS();
				s.getBasicRemote().sendText(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastAll(String message, int exclude) {
		try {
			for(int i = 0; i < users.size(); i++) {
				if(i != exclude) {
					Session s = users.get(i).getS();
					s.getBasicRemote().sendText(message);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastTo(String message, int target) {
		try {
			Session s = users.get(target).getS();
			s.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastTo(String message, ArrayList<Integer> arr) {
		try {
			for(int i = 0; i < arr.size(); i++) {
				Session s = users.get(arr.get(i)).getS();
				s.getBasicRemote().sendText(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized boolean getGameOver() {
		return gameOver;
	}

	@Override
	public void run() {
		
	}
}