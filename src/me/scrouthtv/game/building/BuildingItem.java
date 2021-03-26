package me.scrouthtv.game.building;

import org.bukkit.event.Listener;

/**
 * A BuildingItem is a tool that a builder can use for building a BedWars map,
 * e.g. a tool that can set the bed of a team.
 *
 * It can be an item that is given to him by calling enterTool().
 * Any cleanup can be done in exitTool().
 *
 * The BuildingItem can handle any event, and is guaranteed to only get events
 * during building. It will however read events from all players (even non-builders).
 */
public interface BuildingItem extends Listener {
	void enterTool();
	void exitTool();
}
