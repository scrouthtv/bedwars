package me.scrouthtv.game;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * GameRegistry knows all current games.
 * It handles world-based events (entity damage).
 */
public class GameRegistry implements Listener {
	private Map<String, BedwarsGame> games = new HashMap<>();
	
	@Nullable
	public BedwarsGame getGame(String name) {
		return games.get(name);
	}
	
	/**
	 * Finds the (first) BedwarsGame that is played in the specified world.
	 * If none is registered, null is returned.
	 */
	@Nullable
	public BedwarsGame getGame(World w) {
		for (BedwarsGame games : games.values()) {
			if (games.getWorld().getWorld().equals(w)) {
				return games;
			}
		}
		
		return null;
	}
	
	protected void registerGame(BedwarsGame g) {
		games.put(g.getName(), g);
	}
	
	protected void unregisterGame(BedwarsGame g) {
		games.remove(g);
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent ev) {
		@Nullable BedwarsGame game = getGame(ev.getEntity().getWorld());
		if (game != null) {
			game.onDamage(ev);
		}
	}
}
