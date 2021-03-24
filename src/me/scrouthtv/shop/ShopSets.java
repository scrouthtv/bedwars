package me.scrouthtv.shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ShopSets {
	
	public static final ShopTrade[] BasicShopSet = {
		new ShopTrade(Ingot.INGOT_BRONZE, 1, new ItemStack(Material.SMOOTH_SANDSTONE, 7)),
		new ShopTrade(Ingot.INGOT_BRONZE, 7, new ItemStack(Material.END_STONE, 7)),
		new ShopTrade(Ingot.INGOT_SILVER, 2, new ItemStack(Material.WHITE_WOOL, 1))
	};
	
}
