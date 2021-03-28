package me.scrouthtv.shop;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SerializableAs("bedwars-resource")
public enum Ingot {
	
	INGOT_BRONZE(Material.BRICK, "Bronze"),
	INGOT_SILVER(Material.IRON_INGOT, "Silver"),
	INGOT_GOLD(Material.GOLD_INGOT, "Gold");
	
	private Material m;
	private String name;
	
	Ingot(Material m, String name) {
		this.m = m;
		this.name = name;
	}
	
	public ItemStack asStack(int amount) {
		final ItemStack i = new ItemStack(m, amount);
		final ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(name);
		i.setItemMeta(meta);
		return i;
	}
	
	/**
	 * Searches for the position of a specified ingot in the values() list.
	 * If the ingot couldn't be found, -1 is returned.
	 */
	public static int idxInValues(Ingot i) {
		for (int j = 0; j < values().length; j++) {
			if (values()[j] == i) return j;
		}
		
		return -1;
	}
	
	/**
	 * toString creates an ingot-unique string identifier for this ingot.
	 */
	@Nonnull
	public String toString() {
		return name;
	}
	
	/**
	 * fromString returns the ingot that is associated with the given string.
	 * @param s a string representation of an ingot, obtained by calling toString() on it.
	 */
	@Nullable
	public static Ingot fromString(String s) {
		for (final Ingot i : values()) {
			if (i.toString().equals(s))
				return i;
		}
		
		return null;
	}
}
