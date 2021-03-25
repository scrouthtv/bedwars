package me.scrouthtv.main;

import me.scrouthtv.game.BedwarsBuildingItems;
import me.scrouthtv.game.BedwarsMap;
import me.scrouthtv.game.BedwarsMapCreatorGui;
import me.scrouthtv.maps.IMap;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 * Building a map consists of these steps:
 *  1. Create / load the target IMap.
 *  2. Basic setup (teams, players).
 *  3. Create a BedwarsMap.
 *  4. Teleport the player to it.
 *  5. Give the player the building tools.
 *  6. Wait for the player to finish building.
 *  7. Teleport the player back to the hub.
 *  8. Store the configuration to the disk.
 *
 * BuildProcedure handles steps 1 - 5.
 */
public class BuildProcedure implements BedwarsMapCreatorGui.CreatorFinishCallback {
	private final Player p;
	private BedwarsBuildingItems tools;
	
	private final IMap map;
	private BedwarsMap bwmap;
	
	public BuildProcedure(final Player p, final IMap map) {
		this.p = p;
		this.map = map;
	}
	
	public void start() {
		BedwarsMapCreatorGui gui = new BedwarsMapCreatorGui(map);
		gui.setCallback(this);
		gui.show(p);
	}
	
	@Override
	public void setupFinished(final BedwarsMap map) {
		this.bwmap = map;
		p.teleport(this.map.getWorld().getSpawnLocation());
		p.setGameMode(GameMode.CREATIVE);
		
		tools = new BedwarsBuildingItems(p, map);
		tools.enterTools();
	}
}
