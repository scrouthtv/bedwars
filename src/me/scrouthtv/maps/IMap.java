package me.scrouthtv.maps;

import org.bukkit.World;
import org.bukkit.entity.Player;

public interface IMap {
	IMap cloneMap(String target);
	
	/**
	 * playerJoin makes the specified player join the map.
	 * This could mean something like teleporting them to the correct dimension
	 * or connecting them to a different proxy.
	 * @param p The player.
	 * @return Whether the operation was successful.
	 */
	public boolean playerJoin(Player p);
	
	/**
	 * getWorld returns the underlying Bukkit world.
	 */
	public World getWorld();
	
	/**
	 * All IMap implementations should feature a sane toString() representation
	 * for both debugging and user experience.
	 * @return a basic string representation of this map.
	 */
	public String toString();
	
	/**
	 * UUID shall return a unique string that can be used to identify the map.
	 */
	public String UUID();
}
