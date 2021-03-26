package me.scrouthtv.game.building;

import me.scrouthtv.game.BedwarsTeam;
import me.scrouthtv.game.BuildProcedure;
import me.scrouthtv.utils.ColoredWool;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class BuildersWool implements BuildingItem {
	private final BuildProcedure ctx;
	private final Player holder;
	
	private int team;
	
	public BuildersWool(BuildProcedure ctx) {
		this.ctx = ctx;
		holder = ctx.getPlayer();
	}
	
	public void enterTool() {
		team = 0;
		
		holder.getInventory().addItem(currentItem());
	}
	
	private ItemStack currentItem() {
		return new ItemStack(ColoredWool.woolFromColor(BedwarsTeam.colorOrder[team]));
	}
	
	public void exitTool() {
		for (DyeColor c : DyeColor.values())
			holder.getInventory().remove(ColoredWool.woolFromColor(c));
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent ev) {
		// Are they the building player?
		if (!ev.getPlayer().equals(holder)) return;
		// Did they drop a (colored) wool?
		if (ColoredWool.colorFromWool(ev.getItemDrop().getItemStack().getType()) == null) return;
		
		team = (team+1) % ctx.getMap().getTeamNumber();
		
		// Remove the original wool and give them the new one:
		ev.setCancelled(false);
		ev.getItemDrop().remove();
		ev.getPlayer().getInventory().setItemInMainHand(currentItem());
	}
	
}
