package me.scrouthtv.utils;

import org.bukkit.block.Block;
import org.bukkit.block.data.type.Bed;

import javax.annotation.Nullable;

public class BlockUtil {
	
	/**
	 * Searches for the block of a specific part of a bed.
	 * If the passed block is already the specified part, the block is returned.
	 * If the passed block isn't a bed at all, null is returned.
	 */
	@Nullable
	public static Block findBedPart(Block block, Bed.Part part) {
		if (!(block.getBlockData() instanceof Bed)) return null;
		
		Bed bed = (Bed) block.getBlockData();
		
		if (bed.getPart().equals(part)) {
			return block;
		} else {
			return block.getRelative(bed.getFacing());
		}
	}
}
