package ServerSocket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GamesManager {
	ArrayList<GameThread> games = new ArrayList<GameThread>();
	
	public GamesManager() {
		
	}

	public GameThread getGameByName(String name) {
		for(int i = 0; i < games.size(); i++) {
			if(games.get(i).getGameName().equals(name)) {
				return games.get(i);
			}
		}
		return null;
	}
	
	public boolean existGame(String name) {
		for(int i = 0; i < games.size(); i++) {
			if(games.get(i).getGameName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public void addGame(GameThread game) {
		games.add(game);
	}
	
	public void removeGame(String name) {
		for(int i = 0; i < games.size(); i++) {
			if(games.get(i).getGameName().equals(name)) {
				games.remove(i);
				return;
			}
		}
	}
}
