package me.scrouthtv.game;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * PlayerRegistry knows which player is in which team.
 * It handles player-based events (block place, block break).
 */
public class PlayerRegistry implements Listener {
	private final Map<Player, BedwarsGame> players = new HashMap<>();
	
	@Nullable
	public BedwarsGame getGame(Player p) {
		return players.get(p);
	}
	
	protected void registerPlayer(Player p, BedwarsGame g) {
		if (p == null) return;
		
		players.put(p, g);
	}
	
	protected void unregisterPlayer(Player p) {
		players.remove(p);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent ev) {
		@Nullable final BedwarsGame game = players.get(ev.getPlayer());
		if (game != null) game.blockPlace(ev);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent ev) {
		@Nullable final BedwarsGame game = players.get(ev.getPlayer());
		if (game != null) game.blockBreak(ev);
	}
}
