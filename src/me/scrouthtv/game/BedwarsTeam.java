package me.scrouthtv.game;

import org.bukkit.DyeColor;

public class BedwarsTeam {
	private final BedwarsPlayer[] players;
	
	public BedwarsTeam(final int slots) {
		players = new BedwarsPlayer[slots];
	}
	
	public int getCurrentPlayers() {
		int count = 0;
		for (BedwarsPlayer p : players) {
			if (p != null) count++;
		}
		
		return count;
	}
	
	/**
	 * Join the player into the first available slots.
	 * If no slots are available, false is returned.
	 *
	 * @return whether joining was successful.
	 */
	public boolean playerJoin(BedwarsPlayer p) {
		for (int i = 0; i < players.length; i++) {
			if (players[i] == null) {
				players[i] = p;
				return true;
			}
		}
		
		return false;
	}
	
	public int getMaxPlayers() {
		return players.length;
	}
	
	public static final DyeColor[] colorOrder = {
			DyeColor.BLUE, DyeColor.RED,
			DyeColor.YELLOW, DyeColor.PINK,
			DyeColor.BLACK, DyeColor.GREEN, DyeColor.ORANGE, DyeColor.WHITE,
			DyeColor.LIME, DyeColor.LIGHT_BLUE, DyeColor.MAGENTA, DyeColor.BROWN, DyeColor.LIGHT_GRAY, DyeColor.PURPLE, DyeColor.CYAN, DyeColor.GRAY
	};
}
