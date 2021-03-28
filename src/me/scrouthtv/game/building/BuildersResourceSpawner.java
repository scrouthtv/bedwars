package me.scrouthtv.game.building;

import me.scrouthtv.game.BedwarsIngotSpawner;
import me.scrouthtv.game.BuildProcedure;
import me.scrouthtv.shop.Ingot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class BuildersResourceSpawner implements BuildingItem {
	
	private static final ItemStack[] is = new ItemStack[Ingot.values().length];
	
	static {
		ItemMeta meta;
		for (int i = 0; i < Ingot.values().length; i++) {
			is[i] = new ItemStack(Ingot.values()[i].asStack(1));
			meta = is[i].getItemMeta();
			meta.setDisplayName("Resource spawner for " + Ingot.values()[i].toString());
			is[i].setItemMeta(meta);
		}
	}
	
	private final BuildProcedure ctx;
	private final Player holder;
	private int resource;
	
	public BuildersResourceSpawner(BuildProcedure ctx) {
		this.ctx = ctx;
		holder = ctx.getPlayer();
		resource = 0;
	}
	
	@Override
	public void enterTool() {
		refreshItem();
	}
	
	private void nextItem() {
		resource = (resource+1) % is.length;
	}
	
	private void refreshItem() {
		for (ItemStack i : is)
			holder.getInventory().remove(i);
		
		holder.getInventory().addItem(is[resource]);
	}
	
	@Override
	public void exitTool() {
		for (ItemStack i : is)
			holder.getInventory().remove(i);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent ev) {
		if (ev.getPlayer() == null || ev.getPlayer().getInventory().getItemInMainHand() == null) return;
		if (!ev.getPlayer().equals(holder)) return;
		if (ev.getHand() != EquipmentSlot.HAND) return;
		if (!ev.getPlayer().getInventory().getItemInMainHand().equals(is[resource])) return;
		
		if (ev.getClickedBlock() == null || ev.getBlockFace() == null) return;
		Vector block = ev.getClickedBlock().getRelative(ev.getBlockFace()).getLocation().toVector();
		System.out.println(block);
		double x = block.getX() - (block.getX() % 1) + 0.5;
		double y = block.getY() - (block.getY() % 1) + 0.2;
		double z = block.getZ() - (block.getZ() % 1) + 0.5;
		Vector target = new Vector(x, y, z);
		
		ctx.addSpawner(new BedwarsIngotSpawner(target, Ingot.values()[resource]));
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent ev) {
		if (!ev.getPlayer().equals(holder)) return;
		if (!ev.getItemDrop().getItemStack().equals(is[resource])) return;
		
		ev.setCancelled(false);
		ev.getItemDrop().remove();
		nextItem();
		refreshItem();
	}
}
