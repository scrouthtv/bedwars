package me.scrouthtv.game.building;

import me.scrouthtv.game.BedwarsTeam;
import me.scrouthtv.game.BuildProcedure;
import me.scrouthtv.utils.ColoredBed;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class BuildersBed implements BuildingItem {
	private final BuildProcedure ctx;
	
	private ItemStack i;
	private int team;
	
	public BuildersBed(BuildProcedure ctx) {
		this.ctx = ctx;
	}
	
	public void setup() {
		team = 0;
		i = new ItemStack(ColoredBed.bedFromColor(BedwarsTeam.colorOrder[0]));
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName("Team 1's bed");
		i.setItemMeta(meta);
	}
	
	public void enterTool(final Player p) {
		p.getInventory().addItem(i);
	}
	
	public void exitTool(final Player p) {
		p.getInventory().removeItem(i);
	}
	
	@EventHandler
	public void onBuild(BlockPlaceEvent ev) {
		if (ev.getItemInHand() != i) return;
		
		if (ev.getItemInHand().getType().equals(Material.BARRIER)) {
			ev.setCancelled(true);
			ev.getPlayer().sendMessage(ChatColor.BLUE + "All beds have been placed.");
		} else {
			Vector loc = ev.getBlockPlaced().getLocation().toVector();
			ctx.setBedLocation(team, loc);
			nextItem();
			ev.getPlayer().sendMessage(ChatColor.BLUE + "Placed the bed of team " + team + " to " + loc);
		}
	}
	
	private void nextItem() {
		// find the next team whose bed isn't set yet:
		int target = -1;
		for (int i = team; i != team - 1; i = (i+1) % ctx.getMap().getTeamNumber()) {
			if (ctx.getMap().getBedLocation(i) == null) {
				target = i;
				break;
			}
		}
		
		// if all beds are set, replace the item with a barrier.
		team = target;
		if (target == -1) {
			i.setType(Material.BARRIER);
		} else {
			i.setType(ColoredBed.bedFromColor(BedwarsTeam.colorOrder[target]));
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent ev) {
		if (ev.getItemDrop().getItemStack() != i) return;
		
		nextItem();
	}
}
