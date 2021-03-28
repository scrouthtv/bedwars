package me.scrouthtv.game;

import me.scrouthtv.main.Main;
import me.scrouthtv.maps.IMap;
import me.scrouthtv.utils.ColoredBed;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class BedwarsGame implements Listener {
	private final BedwarsMap properties;
	private final String name;
	private final IMap world;
	private BedwarsTeam[] teams;
	
	private List<Vector> playerPlacedBlocks = new ArrayList<>();
	
	private final boolean[] hasBed;
	
	private GameState state = GameState.STATE_INVALID;
	
	public enum GameState {
		STATE_INVALID,
		STATE_INIT,
		STATE_PLAY,
		STATE_END;
	}
	
	BedwarsGame(final BedwarsMap map, final String name) {
		properties = map;
		this.name = name;
		
		world = properties.getMap().cloneMap(name);
		
		teams = new BedwarsTeam[properties.getTeamNumber()];
		for (int i = 0; i < properties.getTeamNumber(); i++)
			teams[i] = new BedwarsTeam(properties.getTeamSize());
		
		Main.instance().getGameRegistry().registerGame(this);
		
		hasBed = new boolean[properties.getTeamNumber()];
		
		state = GameState.STATE_INIT;
	}
	
	public boolean playerJoin(Player p) {
		if (state != GameState.STATE_INIT) return false;
		if (currentPlayers() >= maxPlayers()) return false;
		
		final int team = smallestTeam();
		
		if (teams[team].playerJoin(p)) {
			Main.instance().getPlayerRegistry().registerPlayer(p, this);
			return true;
		} else {
			return false;
		}
	}
	
	private int smallestTeam() {
		int smallestTeamIdx = 0;
		int smallestTeamSize = properties.getTeamSize();
		
		for (int i = 0; i < teams.length; i++) {
			if (teams[i].getCurrentPlayers() < smallestTeamSize) {
				smallestTeamIdx = i;
				smallestTeamSize = teams[i].getCurrentPlayers();
			}
		}
		
		return smallestTeamIdx;
	}
	
	public int currentPlayers() {
		int players = 0;
		for (BedwarsTeam t : teams)
			players += t.getCurrentPlayers();
		return players;
	}
	
	public int maxPlayers() {
		return properties.getTeamNumber() * properties.getTeamSize();
	}
	
	public boolean startGame() {
		if (state != GameState.STATE_INIT) return false;
		
		for (int i = 0; i < properties.getTeamNumber(); i++) {
			hasBed[i] = true;
		}
		
		state = GameState.STATE_PLAY;
		
		return true;
	}
	
	protected void onDamage(EntityDamageEvent ev) {
		if (ev instanceof Player) {
			// Revive the player if he dies:
			final Player p = (Player) ev.getEntity();
			if (p.getHealth() < ev.getFinalDamage()) {
				ev.setCancelled(true);
				p.teleport(ev.getEntity().getWorld().getSpawnLocation());
			}
			
		} else {
			ev.setCancelled(true);
		}
	}
	
	protected void blockPlace(BlockPlaceEvent ev) {
		if (state != GameState.STATE_PLAY) return;
		
		playerPlacedBlocks.add(ev.getBlock().getLocation().toVector());
	}
	
	protected void blockBreak(BlockBreakEvent ev) {
		if (state != GameState.STATE_PLAY) return;
		
		if (playerPlacedBlocks.contains(ev.getBlock().getLocation().toVector())) {
			// a player-placed block, continue
		} else if (ColoredBed.isColoredBed(ev.getBlock().getType())) {
			// destroy the bed
			final int team = properties.getTeamByBed(ev.getBlock().getLocation().toVector());
			hasBed[team] = false;
		} else {
			// a block of the map, don't destroy it
			ev.setCancelled(true);
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void start() {
	
	}
	
	public boolean canStart() {
		if (state != GameState.STATE_INIT) return false;
		
		return true;
	}
	
	public BedwarsMap getProperties() {
		return properties;
	}
	
	public IMap getWorld() {
		return world;
	}
}