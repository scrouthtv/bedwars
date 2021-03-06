package me.scrouthtv.game;

import me.scrouthtv.main.Main;
import me.scrouthtv.maps.IMap;
import me.scrouthtv.utils.Heads;
import me.scrouthtv.utils.UI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * BedwarsMapCreatorGui is used for the process of *creating* a BedwarsMap
 * from an IMap. Keep in mind that it cannot be used for loading a BedwarsMap
 * that already exists (e.g. in a config file).
 *
 * After the player finalizes the setup (clicks the OK button), the callback
 * method is called.
 */
public class BedwarsMapCreatorGui implements Listener {
	private BedwarsMap map;
	private Inventory inv;
	private CreatorFinishCallback callback;
	
	public interface CreatorFinishCallback {
		void setupFinished(BedwarsMap map);
	}
	
	private static final ItemStack SAVE_ITEM = new ItemStack(Material.WRITABLE_BOOK);
	private static final ItemStack INCREMENT = Heads.HEAD_ARROW_UP.clone();
	private static final ItemStack DECREMENT = Heads.HEAD_ARROW_DOWN.clone();
	private static final ItemStack TEAM_SIZE = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
	private static final ItemStack TEAM_NUMBER = new ItemStack(Material.GOLDEN_SWORD);
	
	static {
		ItemMeta meta;
		
		meta = SAVE_ITEM.getItemMeta();
		meta.setDisplayName("Create map =>");
		SAVE_ITEM.setItemMeta(meta);
		
		meta = INCREMENT.getItemMeta();
		meta.setDisplayName("+1");
		INCREMENT.setItemMeta(meta);
		
		meta =  DECREMENT.getItemMeta();
		meta.setDisplayName("-1");
		DECREMENT.setItemMeta(meta);
	}
	
	public BedwarsMapCreatorGui(final IMap map) {
		this.map = new BedwarsMap(map);
		inv = Bukkit.createInventory(null, 4*9, "Configure " + map.toString());
	}
	
	private void refreshInventory() {
		inv.clear();
		
		ItemStack myTeamSize = TEAM_SIZE.clone();
		ItemStack myTeamNumber = TEAM_NUMBER.clone();
		
		ItemMeta meta = myTeamSize.getItemMeta();
		meta.setDisplayName(String.format("%d players per team", map.getTeamSize()));
		myTeamSize.setItemMeta(meta);
		myTeamSize.setAmount(map.getTeamSize());
		
		meta = myTeamNumber.getItemMeta();
		meta.setDisplayName(String.format("%d teams total", map.getTeamNumber()));
		myTeamNumber.setItemMeta(meta);
		myTeamNumber.setAmount(map.getTeamNumber());
		
		inv.setItem(0 + 0*9, INCREMENT);
		inv.setItem(0 + 1*9, myTeamSize);
		inv.setItem(0 + 2*9, DECREMENT);
		
		inv.setItem(1 + 0*9, INCREMENT);
		inv.setItem(1 + 1*9, myTeamNumber);
		inv.setItem(1 + 2*9, DECREMENT);
		
		inv.setItem(8 + 3*9, SAVE_ITEM);
	}
	
	public void setCallback(final CreatorFinishCallback callback) {
		this.callback = callback;
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent ev) {
		if (ev.getInventory() != inv) {
			return;
		}
		
		Player p = (Player) ev.getWhoClicked();
		
		if (ev.getSlot() == 8 + 3*9) {
			p.closeInventory();
			p.sendMessage(ChatColor.GREEN + "Okay!");
			if (callback != null) callback.setupFinished(map);
		} else if (ev.getSlot() >= 0*9 && ev.getSlot() < 1*9) {
			// Increment something:
			boolean ok = false;
			switch (ev.getSlot() - 0*9) {
				case 0:
					ok = changeTeamSize(+1);
					break;
				case 1:
					ok = changeTeamNumber(+1);
					break;
			}
			
			playClickSound(p, ok);
			refreshInventory();
		} else if (ev.getSlot() >= 2*9 && ev.getSlot() < 3*9) {
			// Decrement something:
			boolean ok = false;
			switch (ev.getSlot() - 2*9) {
				case 0:
					ok = changeTeamSize(-1);
					break;
				case 1:
					ok = changeTeamNumber(-1);
					break;
			}
			
			playClickSound(p, ok);
			refreshInventory();
		}
		
		ev.setCancelled(true);
	}
	
	private void playClickSound(Player p, boolean success) {
		if (success) {
			UI.playSuccessSound(p);
		} else {
			UI.playErrorSound(p);
		}
	}
	
	private boolean changeTeamSize(int amount) {
		int newAmount = map.getTeamSize() + amount;
		if (newAmount <= 0) {
			map.setTeamSize(1);
			return false;
		} else {
			map.setTeamSize(newAmount);
			return true;
		}
	}
	
	private boolean changeTeamNumber(int amount) {
		int newAmount = map.getTeamNumber() + amount;
		if (newAmount <= 0) {
			map.setTeamNumber(1);
			return false;
		} else if (newAmount > 16) {
			map.setTeamNumber(16);
			return false;
		} else {
			map.setTeamNumber(newAmount);
			return true;
		}
	}
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent ev) {
		if (ev.getInventory() == inv) {
			ev.getPlayer().sendMessage(ChatColor.RED + "Aborting map creating.");
			HandlerList.unregisterAll(this);
		}
	}
	
	public void show(final Player p) {
		refreshInventory();
		p.openInventory(inv);
		Bukkit.getPluginManager().registerEvents(this, Main.instance());
	}
}
