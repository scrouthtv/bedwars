package me.scrouthtv.main;

import me.scrouthtv.game.BedwarsBuildingItems;
import me.scrouthtv.game.BedwarsMap;
import me.scrouthtv.game.BedwarsMapCreatorGui;
import me.scrouthtv.maps.IMap;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

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
 * BuildProcedure handles all steps.
 */
public class BuildProcedure implements BedwarsMapCreatorGui.CreatorFinishCallback {
	private final Player p;
	private BedwarsBuildingItems tools;
	
	private final IMap map;
	private BedwarsMap bwmap;
	
	private BuildStage stage = BuildStage.STAGE_INVALID;
	
	public BuildProcedure(final Player p, final IMap map) {
		this.p = p;
		this.map = map;
		
		Main.instance().getBuilders().registerBuilder(p, this);
		
		stage = BuildStage.STAGE_INIT;
	}
	
	public void start() {
		if (stage != BuildStage.STAGE_INIT) return;
		
		stage = BuildStage.STAGE_CREATE;
		BedwarsMapCreatorGui gui = new BedwarsMapCreatorGui(map);
		gui.setCallback(this);
		gui.show(p);
	}
	
	public BuildStage getStage() {
		return stage;
	}
	
	@Override
	public void setupFinished(final BedwarsMap map) {
		if (stage != BuildStage.STAGE_CREATE) return;
		
		stage = BuildStage.STAGE_BUILDING;
		this.bwmap = map;
		p.teleport(this.map.getWorld().getSpawnLocation());
		p.setGameMode(GameMode.CREATIVE);
		
		tools = new BedwarsBuildingItems(p, this);
		tools.enterTools();
	}
	
	public void setBedLocation(int team, Vector loc) {
		if (stage != BuildStage.STAGE_BUILDING) return;
		
		
	}
	
	public static enum BuildStage {
		/**
		 * Invalid building stage.
		 */
		STAGE_INVALID,
		/**
		 * Procedure has been initialized, but not started.
		 */
		STAGE_INIT,
		/**
		 * Procedure has been started, awaiting basic configuration.
		 * We are at step 2.
		 */
		STAGE_CREATE,
		/**
		 * Map has been configured, builder is currently building.
		 * We are at step 6.
		 */
		STAGE_BUILDING,
		/**
		 * Procedure has exited.
		 */
		STAGE_FINISHED;
	}
}
