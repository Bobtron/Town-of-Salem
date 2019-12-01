package ServerSocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

public class Server {

	private static Map<Integer, ServerThread> map = new HashMap<Integer, ServerThread>();
	
	
	public static void onMessage(int thisID, String message) {
		ServerThread st = map.get(thisID);
		st.onMessage(message);
		System.out.println("bb");
	}

	public static void close(int thisID) {
		ServerThread st = map.get(thisID);
		st.stop();
		map.remove(thisID);
	}

	public static void open(int thisID, Session session) {
		ServerThread st = new ServerThread(session);
		map.put(thisID, st);
	}
}
