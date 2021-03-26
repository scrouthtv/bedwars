package me.scrouthtv.game.building;

import me.scrouthtv.game.BuildProcedure;
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
		items.add(new BuildersResourceSpawner(this.parent));
		items.add(new BuildersFinalizer(this.parent));
	}
	
	public void enterTools() {
		p.getInventory().clear();
		for (BuildingItem i : items) {
			Bukkit.getPluginManager().registerEvents(i, Main.instance());
			i.enterTool();
		}
	}
	
	public void exitTools() {
		for (BuildingItem i : items) {
			i.exitTool();
			HandlerList.unregisterAll(i);
		}
	}
}
