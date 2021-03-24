package me.scrouthtv.shop;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class FastShop implements IShop {
	
	private Inventory inv;
	
	private ShopTrade[] trades;
	
	protected FastShop() {
		inv = Bukkit.createInventory(null, 5*9, "SHOP");
	}
	
	public Inventory getInventory() {
		return inv;
	}
	
	@Override
	public void setTrades(final ShopTrade[] t) {
		trades = t;
		
		// Display the first 9 items:
		for (int i = 0; i < t.length && i < 9; i++) {
			setTrade(i, t[i]);
		}
	}
	
	private void setTrade(final int position, final ShopTrade t) {
		inv.setItem(position, t.getProduct());
		inv.setItem(position + 9,t.getCost().asStack(t.getCostAmount()));
	}
}
