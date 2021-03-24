package me.scrouthtv.shop;

import org.bukkit.inventory.Inventory;

public interface IShop {
	public Inventory getInventory();
	public void setTrades(ShopTrade[] t);
}
