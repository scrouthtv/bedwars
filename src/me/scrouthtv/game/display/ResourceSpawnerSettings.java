package me.scrouthtv.game.display;

import me.scrouthtv.game.BedwarsIngotSpawner;
import me.scrouthtv.main.Main;
import me.scrouthtv.shop.Ingot;
import me.scrouthtv.utils.Heads;
import me.scrouthtv.utils.UI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ResourceSpawnerSettings implements Listener {
	
	private static final ItemStack LESS_ITEMS = Heads.HEAD_ARROW_LEFT.clone();
	private static final ItemStack MORE_ITEMS = Heads.HEAD_ARROW_RIGHT.clone();
	private static final ItemStack FASTER_ITEMS = Heads.HEAD_ARROW_LEFT.clone();
	private static final ItemStack SLOWER_ITEMS = Heads.HEAD_ARROW_RIGHT.clone();
	
	private int resource;
	private int amount;
	private int tickSpeed;
	
	private SpawnerChangedCallback callback;
	
	public interface SpawnerChangedCallback {
		void modifyFinished(Ingot resource, int amount, int tickSpeed);
	}
	
	static {
		ItemMeta meta = LESS_ITEMS.getItemMeta();
		meta.setDisplayName("-1 item per spawn");
		LESS_ITEMS.setItemMeta(meta);
		
		meta = MORE_ITEMS.getItemMeta();
		meta.setDisplayName("+1 item per spawn");
		MORE_ITEMS.setItemMeta(meta);
		
		meta = FASTER_ITEMS.getItemMeta();
		meta.setDisplayName("Reduce spawn cooldown");
		FASTER_ITEMS.setItemMeta(meta);
		
		meta = SLOWER_ITEMS.getItemMeta();
		meta.setDisplayName("Increase spawn cooldown");
		SLOWER_ITEMS.setItemMeta(meta);
	}
	
	private final Inventory gui;
	
	public ResourceSpawnerSettings(final BedwarsIngotSpawner spawner) {
		resource = Ingot.idxInValues(spawner.getResource());
		amount = spawner.getAmount();
		tickSpeed = spawner.getTickSpeed();
		
		gui = Bukkit.createInventory(null, 2*9, "Change spawner settings");
		update();
		
		Bukkit.getPluginManager().registerEvents(this, Main.instance());
	}
	
	private void update() {
		gui.setItem(0, Ingot.values()[resource].asStack(1));
		
		gui.setItem(2, LESS_ITEMS);
		gui.setItem(3, new ItemStack(Material.REDSTONE, amount));
		gui.setItem(4, MORE_ITEMS);
		
		gui.setItem(6, FASTER_ITEMS);
		gui.setItem(7, new ItemStack(Material.CLOCK, tickSpeed));
		gui.setItem(8, SLOWER_ITEMS);
	}
	
	public void setCallback(final SpawnerChangedCallback callback) {
		this.callback = callback;
	}
	
	public void open(Player p) {
		p.openInventory(gui);
	}
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent ev) {
		if (ev.getInventory() != gui) return;
		
		HandlerList.unregisterAll(this);
		
		if (callback != null) callback.modifyFinished(Ingot.values()[resource], amount, tickSpeed);
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent ev) {
		if (ev.getInventory() != gui) return;
		
		ev.setCancelled(true);
		
		boolean ok = false;
		
		switch (ev.getSlot()) {
			case 0:
				resource = (resource + 1) % Ingot.values().length;
				ok = true;
				break;
			case 2:
				if (amount > 1) {
					amount--;
					ok = true;
				}
				break;
			case 4:
				if (amount < 64) {
					amount++;
					ok = true;
				}
				break;
			case 6:
				if (tickSpeed > 1) {
					tickSpeed--;
					ok = true;
				}
				break;
			case 8:
				tickSpeed++;
				ok = true;
				break;
		}
		
		if (ok) UI.playSuccessSound((Player) ev.getWhoClicked());
		else UI.playErrorSound((Player) ev.getWhoClicked());
		update();
	}
}
