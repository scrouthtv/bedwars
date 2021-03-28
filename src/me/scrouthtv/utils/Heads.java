package me.scrouthtv.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class Heads {
	
	public static final ItemStack HEAD_ARROW_UP = createSkullFromOwner("MHF_ArrowUp");
	public static final ItemStack HEAD_ARROW_DOWN = createSkullFromOwner("MHF_ArrowDown");
	public static final ItemStack HEAD_ARROW_LEFT = createSkullFromOwner("MHF_ArrowLeft");
	public static final ItemStack HEAD_ARROW_RIGHT = createSkullFromOwner("MHF_ArrowRight");
	
	private static ItemStack createSkullFromOwner(String owner) {
		ItemStack i = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) i.getItemMeta();
		meta.setOwner(owner);
		i.setItemMeta(meta);
		return i;
	}
}
