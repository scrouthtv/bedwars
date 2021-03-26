package me.scrouthtv.game;

import me.scrouthtv.game.building.BuildersBed;
import me.scrouthtv.game.building.BuildingItem;
import me.scrouthtv.game.building.BuildersShopSpawner;
import me.scrouthtv.game.building.BuildersWool;
import me.scrouthtv.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.List;

public class BedwarsBuildingItems {
	private final Player p;
	private final BuildProcedure parent;
	
	private final List<BuildingItem> items = new ArrayList<>();
	
	public BedwarsBuildingItems(final Player p, final BuildProcedure parent) {
		this.p = p;
		this.parent = parent;
		
		items.add(new BuildersWool(this.parent));
		items.add(new BuildersBed(this.parent));
		items.add(new BuildersShopSpawner(this.parent));
	}
	
	public void enterTools() {
		for (BuildingItem i : items) {
			Bukkit.getPluginManager().registerEvents(i, Main.instance());
			i.enterTool(p);
		}
	}
	
	public void exitTools() {
		for (BuildingItem i : items) {
			i.exitTool(p);
			HandlerList.unregisterAll(i);
		}
	}
}
