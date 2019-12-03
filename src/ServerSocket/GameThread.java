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
	
	ArrayList<Integer> votes;
	ArrayList<String> candidates;
	int numVoted;
	
	
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
		
		this.votes = new ArrayList<Integer>();
		this.candidates = new ArrayList<String>();
		this.numVoted = 0;
		
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
		broadcastAll("CHAT|ALL|" + user.getUsername() + "|" + remainder);
	}

	public void sendChatMafia(String remainder, User user, String org) {
		broadcastTo("CHAT|MAFIA|" + user.getUsername() + "|" + remainder, mafiaPeople);
	}
	
	public void vote(String remainder, User user, String org) {
		String[] args = remainder.split("|");
		String target = args[0];
		for(int i = 0; i < candidates.size(); i++) {
			if(candidates.get(i).equals(target)) {
				votes.set(i,votes.get(i) + 1);
				numVoted++;
				return;
			}
		}
		candidates.add(target);
		votes.add(1);
		numVoted++;
	}

	/*public void voteMafia(String remainder, User user, String org) {
		String[] args = remainder.split("|");
		String target = args[0];
		for(int i = 0; i < candidates.size(); i++) {
			if(candidates.get(i).equals(target)) {
				votes.set(i,votes.get(i) + 1);
				return;
			}
		}
		candidates.add(target);
		votes.add(1);
		
		numVoted = numVoted + 1;
	}*/
	
	public String getUsernames(ArrayList<User> arr) {
		String returnThis = "";
		for(int i = 0; i < arr.size(); i++) {
			returnThis += "|" + arr.get(i).getUsername();
		}
		return returnThis;
	}
	
	public User getVotingTarget() {
		User returnThis = null;
		
		int mostVotes = 0;
		ArrayList<String> usernames = new ArrayList<String>();
		for(int i = 0; i < votes.size(); i++) {
			if(votes.get(i) > mostVotes) {
				usernames = new ArrayList<String>();
				usernames.add(candidates.get(i));
				mostVotes = votes.get(i);
			}else if(votes.get(i) == mostVotes) {
				usernames.add(candidates.get(i));
			}
		}
		
		String deadman = usernames.get(random.nextInt(usernames.size()));
		for(int i = 0; i < alive.size(); i++) {
			if(alive.get(i).getUsername().equals(deadman)) {
				returnThis = alive.get(i);
			}
		}
		
		return returnThis;
	}
	
	public void removeUser(User deadman) {
		dead.add(deadman);
		alive.remove(deadman);
		for(int i = 0; i < mafiaPeople.size(); i++) {
			if(deadman.equals(mafiaPeople.get(i))) {
				mafiaPeople.remove(i);
				return;
			}
		}
		for(int i = 0; i < townsPeople.size(); i++) {
			if(deadman.equals(townsPeople.get(i))) {
				townsPeople.remove(i);
				return;
			}
		}
	}
	
	public String getVoteResults() {
		String returnThis = "";
		for(int i = 0; i < candidates.size(); i++) {
			returnThis += "|" + candidates.get(i) + "|" + votes.get(i);
		}
		return returnThis;
	}

	@Override
	public void run() {
		while(everyone.size() < totalPlayers) {
			Thread.yield();
		}
		Thread.yield();
		
		numVoted = 0;
		this.votes = new ArrayList<Integer>();
		this.candidates = new ArrayList<String>();
		
		started = true;
		User deadman;
		
		broadcastAll("STATUS|GAME_START");
		
		for(int i = 0; i < numMafia; i++) {
			int removeThis = random.nextInt(townsPeople.size());
			User user = townsPeople.remove(removeThis);
			mafiaPeople.add(user);
		}
		
		broadcastTo("CLASS|MAFIA", mafiaPeople);
		broadcastTo("CLASS|TOWNS", townsPeople);
		
		
		String players = "PLAYER_NAMES" + getUsernames(alive);
		broadcastAll(players);
		
		while(true) {
			
			
			broadcastAll("STATUS|GAME_NIGHT");
			
			broadcastTo("VOTE|MAFIA", mafiaPeople);
			broadcastTo("MAFIA_NAMES" + getUsernames(mafiaPeople), mafiaPeople);
			broadcastTo("TOWNS_NAMES" + getUsernames(townsPeople), mafiaPeople);
			
			while(numVoted < mafiaPeople.size()) {
				Thread.yield();
			}
			
			deadman = getVotingTarget();
			broadcastAll("VOTE_RESULTS|MAFIA" + getVoteResults());
			removeUser(deadman);
			
			if(mafiaPeople.size() == 0) {
				broadcastAll("WIN|TOWNSPEOPLE");
				break;
			}else if(townsPeople.size() == 0) {
				broadcastAll("WIN|MAFIA");
				break;
			}
					
			numVoted = 0;
			this.votes = new ArrayList<Integer>();
			this.candidates = new ArrayList<String>();
			
			broadcastAll("STATUS|GAME_DAY");
			
			broadcastTo("VOTE|ALL", alive);
			players = "ALIVE_NAMES" + getUsernames(alive);
			broadcastAll(players);
			
			while(numVoted < alive.size()) {
				//System.out.println(numVoted + " " + alive.size());
				Thread.yield();
			}
			
			deadman = getVotingTarget();
			broadcastAll("VOTE_RESULTS|TOWNS" + getVoteResults());
			removeUser(deadman);
			
			if(mafiaPeople.size() == 0) {
				broadcastAll("WIN|TOWNSPEOPLE");
				break;
			}else if(townsPeople.size() == 0) {
				broadcastAll("WIN|MAFIA");
				break;
			}
			
			numVoted = 0;
			this.votes = new ArrayList<Integer>();
			this.candidates = new ArrayList<String>();
			
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