package me.scrouthtv.shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Ingot {
	
	INGOT_BRONZE(Material.BRICK, "Bronze"),
	INGOT_SILVER(Material.IRON_INGOT, "Silver"),
	INGOT_GOLD(Material.GOLD_INGOT, "Gold");
	
	private Material m;
	private String name;
	
	private Ingot(Material m, String name) {
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
}
