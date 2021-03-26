package me.scrouthtv.game.building;

import me.scrouthtv.game.BuildProcedure;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BuildersShopSpawner implements BuildingItem {
	
	private final BuildProcedure ctx;
	
	private ItemStack i;
	
	public BuildersShopSpawner(BuildProcedure ctx) {
		this.ctx = ctx;
	}
	
	public void setup() {
		i = new ItemStack(Material.VILLAGER_SPAWN_EGG);
	}
	
	public void enterTool(final Player p) {
		p.getInventory().addItem(i);
	}
	
	public void exitTool(final Player p) {
		p.getInventory().removeItem(i);
	}
}
