package ServerSocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

public class Server {

	private static Map<Integer, ServerThread> map = new HashMap<Integer, ServerThread>();
	
	public void onMessage(int thisID, String message) {
		ServerThread st = map.get(thisID);
		st.onMessage(message);
	}

	public void close(int thisID) {
		ServerThread st = map.get(thisID);
		st.stop();
		map.remove(thisID);
	}

	public void open(int thisID, Session session) {
		ServerThread st = new ServerThread(session);
		map.put(thisID, st);
	}
}
