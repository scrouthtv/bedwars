package me.scrouthtv.game.building;

import me.scrouthtv.game.BedwarsTeam;
import me.scrouthtv.game.BuildProcedure;
import me.scrouthtv.utils.ColoredBed;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class BuildersBed implements BuildingItem {
	
	private static final ItemStack NO_MORE_BEDS = new ItemStack(Material.BARRIER);
	
	static {
		ItemMeta meta = NO_MORE_BEDS.getItemMeta();
		meta.setDisplayName("All beds have been built");
		NO_MORE_BEDS.setItemMeta(meta);
	}
	
	private final BuildProcedure ctx;
	private final Player holder;
	private int team;
	
	public BuildersBed(BuildProcedure ctx) {
		this.ctx = ctx;
		holder = ctx.getPlayer();
	}
	
	public void enterTool() {
		refreshItem();
	}
	
	public void exitTool() {
		// Remove all beds from the player's inventory
		for (DyeColor c : DyeColor.values())
			holder.getInventory().remove(ColoredBed.bedFromColor(c));
		holder.getInventory().remove(NO_MORE_BEDS);
	}
	
	private void refreshItem() {
		// Remove all beds:
		for (DyeColor c : DyeColor.values())
			holder.getInventory().remove(ColoredBed.bedFromColor(c));
		holder.getInventory().remove(NO_MORE_BEDS);
		
		// Give them a new bed with correct color & label,
		// or a barrier if all beds have been placed:
		
		if (team == -1) {
			holder.getInventory().addItem(NO_MORE_BEDS.clone());
			holder.sendMessage(ChatColor.BLUE + "All beds have been placed.");
		} else {
			ItemStack i = new ItemStack(ColoredBed.bedFromColor(BedwarsTeam.colorOrder[team]));
			ItemMeta meta = i.getItemMeta();
			meta.setDisplayName("Team " + (team + 1) + "'s bed");
			i.setItemMeta(meta);
			holder.getInventory().addItem(i);
			System.out.println("asdfasdf: " + team);
		}
	}
	
	@EventHandler
	public void onBuild(BlockPlaceEvent ev) {
		if (!ev.getPlayer().equals(holder)) return;
		
		final DyeColor c = ColoredBed.colorFromBed(ev.getItemInHand().getType());
		
		if (ev.getItemInHand().equals(NO_MORE_BEDS)) {
			// If it's the barrier, simply cancel the event:
			ev.setCancelled(true);
		} else if (c != null) {
			// If it's a colored bed, set the bed location:
			final int team = BedwarsTeam.teamNumberFromColor(c);
			Vector loc = ev.getBlockPlaced().getLocation().toVector();
			ctx.setBedLocation(team, loc);
			ev.getPlayer().sendMessage(ChatColor.BLUE + "Placed the bed of team " + team + " to " + loc);
			
			// and give them the next bed:
			nextTeam();
			refreshItem();
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent ev) {
		if (!ev.getPlayer().equals(holder)) return;
		if (ColoredBed.colorFromBed(ev.getBlock().getType()) == null) return;
		
		final int target = ctx.getMap().getTeamByBed(ev.getBlock().getLocation().toVector());
		if (target == -1)
			ev.getPlayer().sendMessage(ChatColor.RED + "Could not find the bed's owner.");
		else {
			ctx.setBedLocation(target, null);
			nextTeam();
			refreshItem();
		}
	}
	
	private void nextTeam() {
		// find the next team whose bed isn't set yet:
		int target = -1;
		for (int i = team + 1; i != team; i++) {
			i %= ctx.getMap().getTeamNumber();
			System.out.println(i);
			if (ctx.getMap().getBedLocation(i) == null) {
				System.out.println("team " + i + " doesnt have a bed");
				target = i;
				break;
			}
		}
		team = target;
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent ev) {
		if (!ev.getPlayer().equals(holder)) return;
		if (ColoredBed.colorFromBed(ev.getItemDrop().getItemStack().getType()) == null) return;
		
		ev.setCancelled(false);
		ev.getItemDrop().remove();
		nextTeam();
		refreshItem();
	}
}
