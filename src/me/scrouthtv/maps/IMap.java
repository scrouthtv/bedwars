package me.scrouthtv.maps;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

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
	
	/**
	 * vecInWorld places returns the location of a vector in this map.
	 */
	public Location vecInWorld(final Vector v);
	
	/**
	 * blockInWorld returns the block at the specified position.
	 */
	public Block blockInWorld(final Vector v);
}
