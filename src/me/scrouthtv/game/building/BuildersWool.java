package me.scrouthtv.game.building;

import me.scrouthtv.game.BedwarsTeam;
import me.scrouthtv.game.BuildProcedure;
import me.scrouthtv.utils.ColoredWool;
import org.bukkit.DyeColor;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BuildersWool implements BuildingItem {
	private final BuildProcedure ctx;
	
	private ItemStack i;
	
	public void setup() {
		i = new ItemStack(ColoredWool.woolFromColor(BedwarsTeam.colorOrder[0]));
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName("Team 1's wool");
		i.setItemMeta(meta);
	}
	
	public BuildersWool(BuildProcedure ctx) {
		this.ctx = ctx;
	}
	
	public void enterTool(Player p) {
		p.getInventory().addItem(i);
	}
	
	public void exitTool(Player p) {
		p.getInventory().removeItem(i);
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent ev) {
		if (ev.getItemDrop().getItemStack() != i) return;
		
		Item i = ev.getItemDrop();
		ev.setCancelled(true);
		
		DyeColor woolDye = ColoredWool.colorFromWool(i.getItemStack().getType());
		if (woolDye != null) {
			int nextTeam = BedwarsTeam.teamNumberFromColor(woolDye) + 1;
			woolDye = BedwarsTeam.colorOrder[nextTeam];
			ev.getPlayer().getInventory().getItemInMainHand().setType(ColoredWool.woolFromColor(woolDye));
		}
	}
	
}
