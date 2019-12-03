package ServerSocket;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
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
	int numMafia;
	Random random;
	
	ArrayList<User> everyone;
	ArrayList<User> alive;
	ArrayList<User> dead;
	ArrayList<User> mafiaPeople;
	ArrayList<User> townsPeople;
	
	
	public GameThread(String gameName, int totalUsers) {
		
		this.started = false;
		this.gameOver = false;
		
		this.gameName = gameName;
		this.totalPlayers = totalUsers;
		this.usersLeft = totalUsers;
		this.numMafia = totalUsers / 4;
		
		random = new Random();
		
		this.everyone = new ArrayList<User>();
		this.alive = new ArrayList<User>();
		this.dead = new ArrayList<User>();
		this.mafiaPeople = new ArrayList<User>();
		this.townsPeople = new ArrayList<User>();
				
		this.start();
	}
	
	public boolean hasSpace() {
		return !started && totalPlayers > everyone.size();
	}

	public String getGameName() {
		return gameName;
	}
	
	public synchronized void addUser(User user) {
		everyone.add(user);
		townsPeople.add(user);
		alive.add(user);
		user.setIndex(everyone.size() - 1);
	}
	
	public void sendChatAll(String remainder, User user, String org) {
		broadcastAll("CHAT|ALL|" + remainder);
	}

	public void sendChatMafia(String remainder, User user, String org) {
		broadcastTo("CHAT|MAFIA|" + remainder, mafiaPeople);
	}
	
	public void voteAll(String remainder, User user, String org) {
		String[] args = remainder.split("|");
		
	}

	public void voteMafia(String remainder, User user, String org) {
		String[] args = remainder.split("|");
		
	}

	@Override
	public void run() {
		while(everyone.size() < totalPlayers) {
			Thread.yield();
		}
		Thread.yield();
		
		int numVoted = 0;
		started = true;
		
		broadcastAll("STATUS|GAME_START");
		
		for(int i = 0; i < numMafia; i++) {
			int removeThis = random.nextInt(townsPeople.size());
			User user = townsPeople.remove(removeThis);
			mafiaPeople.add(user);
		}
		
		broadcastTo("CLASS|MAFIA", mafiaPeople);
		broadcastTo("CLASS|TOWNS", townsPeople);
		String players = "PLAYER_NAMES";
		for(int i = 0; i < everyone.size(); i++) {
			players += "|" + everyone.get(i).getUsername();
		}
		broadcastAll(players);
		
		while(true) {
			broadcastTo("STATUS|GAME_NIGHT", alive);
			
			broadcastTo("VOTE|MAFIA", mafiaPeople);
			
			while(numVoted < mafiaPeople.size()) {
				Thread.yield();
			}
			
			//TODO analyze the vote
			numVoted = 0;
			
			broadcastTo("STATUS|GAME_DAY", alive);
			
			broadcastTo("VOTE|ALL", alive);
			
			while(numVoted < alive.size()) {
				Thread.yield();
			}
			
			//TODO analyze the vote
			numVoted = 0;
			
			break;
		}
		
		broadcastAll("STATUS|GAME_END");
	}
	
	public void broadcastAll(String message) {
		System.out.println(message);
		try {
			for(int i = 0; i < everyone.size(); i++) {
				Session s = everyone.get(i).getS();
				s.getBasicRemote().sendText(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastTo(String message, int target) {
		System.out.println(message);
		try {
			Session s = everyone.get(target).getS();
			s.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastTo(String message, User user) {
		System.out.println(message);
		try {
			Session s = user.getS();
			s.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastTo(String message, ArrayList<User> arr) {
		System.out.println(message);
		try {
			for(int i = 0; i < arr.size(); i++) {
				Session s = arr.get(i).getS();
				s.getBasicRemote().sendText(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}