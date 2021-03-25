package me.scrouthtv.game;

import me.scrouthtv.main.Main;
import me.scrouthtv.utils.ColoredWool;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BedwarsBuildingItems implements Listener {
	private final Player p;
	private final BedwarsMap map;
	
	public BedwarsBuildingItems(final Player p, final BedwarsMap map) {
		this.p = p;
		this.map = map;
	}
	
	public void enterTools() {
		ItemStack i;
		ItemMeta meta;
		
		i = new ItemStack(ColoredWool.woolFromColor(BedwarsTeam.colorOrder[0]));
		meta = i.getItemMeta();
		meta.setDisplayName("Team 1's wool");
		i.setItemMeta(meta);
		
		p.getInventory().addItem(i);
		
		Bukkit.getPluginManager().registerEvents(this, Main.instance());
	}
	
	public void exitTools() {
		HandlerList.unregisterAll(this);
		p.getInventory().clear();
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent ev) {
		if (ev.getPlayer() != p) return;
		
		Item i = ev.getItemDrop();
		
		DyeColor woolDye = ColoredWool.colorFromWool(i.getItemStack().getType());
		if (woolDye != null) {
			woolDye = BedwarsTeam.colorOrder[teamNumberFromWool(woolDye) + 1];
			ev.getPlayer().getInventory().getItemInMainHand().setType(ColoredWool.woolFromColor(woolDye));
		}
	}
	
	/**
	 * Searches for the first (and hopefully only) team that uses this color.
	 * If no such team could be found, -1 is returned.
	 */
	public static int teamNumberFromWool(DyeColor c) {
		for (int i = 0; i < BedwarsTeam.colorOrder.length; i++) {
			if (BedwarsTeam.colorOrder[i] == c) return i;
		}
		
		return -1;
	}
}
