package me.scrouthtv.game;

import me.scrouthtv.maps.IMap;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class BedwarsGame implements Listener {
	private final BedwarsMap properties;
	private final String name;
	private final IMap world;
	private BedwarsTeam[] teams;
	
	BedwarsGame(final BedwarsMap map, final String name) {
		properties = map;
		this.name = name;
		
		world = properties.getMap().cloneMap(name);
		
		teams = new BedwarsTeam[properties.getTeamNumber()];
		for (int i = 0; i < properties.getTeamNumber(); i++)
			teams[i] = new BedwarsTeam(properties.getTeamSize());
		
		GameRegistry.registerGame(this);
	}
	
	public boolean playerJoin(Player p) {
		if (currentPlayers() >= maxPlayers()) return false;
		
		final int team = smallestTeam();
		
		return teams[team].playerJoin(new BedwarsPlayer(p));
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
	
	/*@EventHandler
	public void onDamage(EntityDamageEvent ev) {
		if (!ev.getEntity().getWorld().equals(map.getWorld()))
			return;
		if (ev.getEntity().getWorld() != map.getWorld())
			System.out.println("They're equal but not identical.");
		
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
	}*/
	
	public String getName() {
		return name;
	}
}