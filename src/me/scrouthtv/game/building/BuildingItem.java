package me.scrouthtv.game.building;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public interface BuildingItem extends Listener {
	void setup();
	void enterTool(Player p);
	void exitTool(Player p);
}
