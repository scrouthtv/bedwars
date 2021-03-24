package me.scrouthtv.shop;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class Shop implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent ev) {
		ev.setCancelled(true);
		
		IShop shop = new FastShop();
		
		shop.setCategories(new ShopCategory[]{ShopSets.Blocks, ShopSets.Combat});
		
		ev.getPlayer().openInventory(shop.getInventory());
	}
	
}
