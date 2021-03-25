package me.scrouthtv.game;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class GameRegistry {
	private static Map<String, BedwarsGame> games = new HashMap<>();
	
	@Nullable
	public static BedwarsGame getGame(String name) {
		return games.get(name);
	}
	
	static void registerGame(BedwarsGame g) {
		games.put(g.getName(), g);
	}
}
