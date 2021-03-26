package me.scrouthtv.game.building;

import me.scrouthtv.game.BuildProcedure;
import me.scrouthtv.utils.UI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BuildersFinalizer implements BuildingItem {
	
	private static final ItemStack FINALIZE_ITEM = new ItemStack(Material.NETHER_STAR);
	
	static {
		ItemMeta meta = FINALIZE_ITEM.getItemMeta();
		meta.setDisplayName("Save the map ->");
		FINALIZE_ITEM.setItemMeta(meta);
	}
	
	private final BuildProcedure ctx;
	private final Player holder;
	
	public BuildersFinalizer(final BuildProcedure ctx) {
		this.ctx = ctx;
		holder = ctx.getPlayer();
	}
	
	@Override
	public void enterTool() {
		holder.getInventory().addItem(FINALIZE_ITEM.clone());
	}
	
	@Override
	public void exitTool() {
		holder.getInventory().remove(FINALIZE_ITEM);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent ev) {
		if (!ev.getPlayer().equals(holder)) return;
		if (ev.getItem() == null || !ev.getItem().equals(FINALIZE_ITEM)) return;
		
		ev.setCancelled(true);
		
		if (ctx.canFinish()) {
			ev.getPlayer().sendMessage(ChatColor.BLUE + "Okay. Saving the map.");
			ctx.buildingFinished();
			UI.playSuccessSound(ev.getPlayer());
		} else {
			ev.getPlayer().sendMessage(ChatColor.RED + "Error. There's something missing");
			UI.playErrorSound(ev.getPlayer());
		}
	}
}
