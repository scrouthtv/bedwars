package me.scrouthtv.shop;

import org.bukkit.inventory.ItemStack;

public class ShopCategory {
	private ItemStack title;
	private ShopTrade[] trades;
	
	public ShopCategory(ItemStack title, ShopTrade[] trades) {
		this.title = title;
		this.trades = trades;
	}
	
	public ItemStack getTitle() {
		return title;
	}
	
	public ShopTrade[] getTrades() {
		return trades;
	}
}
