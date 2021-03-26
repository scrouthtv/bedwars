package me.scrouthtv.game.building;

import me.scrouthtv.game.BedwarsMap;
import me.scrouthtv.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.List;

public class BedwarsBuildingItems {
	private final Player p;
	private final BedwarsMap map;
	
	private final List<BuildingItem> items = new ArrayList<>();
	
	public BedwarsBuildingItems(final Player p, final BedwarsMap map) {
		this.p = p;
		this.map = map;
		
		items.add(new Wool(this));
		items.add(new Bed(this));
	}
	
	BedwarsMap getMap() {
		return map;
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
