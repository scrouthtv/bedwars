package me.scrouthtv.shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ShopSets {
	
	public static final ShopCategory Blocks = new ShopCategory(new ItemStack(Material.SMOOTH_SANDSTONE), new ShopTrade[]{
			new ShopTrade(Ingot.INGOT_BRONZE, 1, new ItemStack(Material.SMOOTH_SANDSTONE, 7)),
			new ShopTrade(Ingot.INGOT_BRONZE, 7, new ItemStack(Material.END_STONE, 2)),
			new ShopTrade(Ingot.INGOT_SILVER, 2, new ItemStack(Material.WHITE_WOOL, 1))
	});
	
	public static final ShopCategory Combat = new ShopCategory(ShopItems.KNOCK_STICK, new ShopTrade[]{
			new ShopTrade(Ingot.INGOT_BRONZE, 8, ShopItems.KNOCK_STICK),
			ShopTrade.EMPTY_TRADE,
			new ShopTrade(Ingot.INGOT_SILVER, 1, ShopItems.SWORD_ONE),
			new ShopTrade(Ingot.INGOT_SILVER, 5, ShopItems.SWORD_TWO),
			new ShopTrade(Ingot.INGOT_GOLD, 3, ShopItems.SWORD_THREE)
	});
	
}
