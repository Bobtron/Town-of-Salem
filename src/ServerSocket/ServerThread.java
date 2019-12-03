package ServerSocket;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.websocket.Session;

public class ServerThread extends Thread {
	
	private Session session;
	private User user;
	private GameThread gt;
	private GamesManager gm;
	
	public ServerThread(Session session, GamesManager gm) {
		this.session = session;
		this.gm = gm;
	}
	
	public void onMessage(String message) {
		System.out.println(message);
		sendText(message);
		/**large sections is
		 * 
		 * chat of all kinds
		 * game creation, joining
		 * voting
		 */
		
		String[] args = message.split(Pattern.quote("|"));
		String TYPE = args[0];
		String remainder = message.substring(message.indexOf('|') + 1);
		
		String org = message.replace('|', '-');
		
		//System.out.println(TYPE);
		switch (TYPE) {
	        case "CHAT":
	        	typeChat(remainder, org);
	        	break;
	                 
	        case "META":
	        	typeMeta(remainder, org);
	        	break;
	        	
	        case "VOTE":
	        	typeVote(remainder, org);
	        	break;
		}
	}
	
	private void typeChat(String message, String org) {
		String[] args = message.split(Pattern.quote("|"));
		String CMD = args[0];
		String remainder = message.substring(message.indexOf('|') + 1);
		
		switch (CMD) {
	        case "ALL":
	        	gt.sendChatAll(remainder, user, org);
	        	break;
	        	
	        case "MAFIA":
	        	gt.sendChatMafia(remainder, user, org);
	        	break;
		}
	}
	
	private void typeMeta(String message, String org) {
		String[] args = message.split(Pattern.quote("|"));
		String CMD = args[0];
		String remainder = message.substring(message.indexOf('|') + 1);
		
		//System.out.println(CMD);
		switch (CMD) {
	        case "JOIN":
	        	joinGame(remainder, org);
	        	break;
	        case "CREATE":
	        	createGame(remainder, org);
	        	break;
	        case "USER":
	        	createUser(remainder, org);
	        	break;
		}
	}
	
	private void typeVote(String message, String org) {
		String[] args = message.split(Pattern.quote("|"));
		String CMD = args[0];
		String remainder = message.substring(message.indexOf('|') + 1);
		
		switch (CMD) {
	        case "ALL":
	        	gt.voteAll(remainder, user, org);
	        	break;
	        	
	        case "MAFIA":
	        	gt.voteMafia(remainder, user, org);
	        	break;
		}
	}
	
	private void joinGame(String message, String org) {
		String[] args = message.split(Pattern.quote("|"));
		String gameName = args[0];
		
		if(gm.existGame(gameName)) {
			gt = gm.getGameByName(gameName);
			if(gt.hasSpace()) {
				sendText("RESPONSE|TRUE|" + org);
				gt.addUser(user);
			}else {
				sendText("RESPONSE|FALSE|" + org);
			}
		}else {
			sendText("RESPONSE|FALSE|" + org);
		}
	}
	
	private void createGame(String message, String org) {
		String[] args = message.split(Pattern.quote("|"));
		String gameName = args[0];
		int numPlayers = Integer.parseInt(args[1]);
		
		if(gm.existGame(gameName)) {
			sendText("RESPONSE|FALSE|" + org);
		}else {
			GameThread gt = new GameThread(gameName, numPlayers);
			sendText("RESPONSE|TRUE|" + org);
			gt.addUser(user);
			gm.addGame(gt);
		}
	}
	
	private void createUser(String message, String org) {
		String[] args = message.split(Pattern.quote("|"));
		String username = args[0];
		
		//System.out.println(username);
		user = new User(username, this.session);
		sendText("RESPONSE|TRUE|" + org);
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
