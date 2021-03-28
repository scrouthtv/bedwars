package me.scrouthtv.game;

import me.scrouthtv.game.display.BedwarsBuildingDisplays;
import me.scrouthtv.game.building.BedwarsBuildingItems;
import me.scrouthtv.main.Main;
import me.scrouthtv.maps.IMap;
import me.scrouthtv.shop.Ingot;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

/**
 * Building a map consists of these steps:
 *  1. Create / load the target IMap.
 *  2. Basic setup (teams, players).
 *  3. Create a BedwarsMap.
 *  4. Teleport the player to it.
 *  5. Give the player the building tools.
 *  6. Wait for the player to finish building.
 *  7. Teleport the player back to the hub.
 *
 * BuildProcedure handles steps 2 - 7.
 */
public class BuildProcedure implements BedwarsMapCreatorGui.CreatorFinishCallback {
	private final Player p;
	private BedwarsBuildingItems tools;
	private BedwarsBuildingDisplays disps;
	
	private final IMap map;
	private BedwarsMap bwmap;
	
	private BuildStage stage = BuildStage.STAGE_INVALID;
	
	/**
	 * Starts the building of a new bedwars map based on a builder and world.
	 */
	public BuildProcedure(final Player p, final IMap map) {
		this.p = p;
		this.map = map;
		
		Main.instance().getBuilders().registerBuilder(p, this);
		
		stage = BuildStage.STAGE_INIT;
	}
	
	/**
	 * Allows to go back to an already finished map and change some things afterwards.
	 * Currently, it only goes back to the building stage (step 6).
	 *
	 * The bwmap instance returned at the end is the same as this one.
	 */
	public BuildProcedure(final Player p, BedwarsMap bwmap) {
		this.p = p;
		this.map = bwmap.getMap();
		this.bwmap = bwmap;
		
		Main.instance().getBuilders().registerBuilder(p, this);
		
		stage = BuildStage.STAGE_BUILDING;
	}
	
	public void start() {
		if (stage != BuildStage.STAGE_INIT) return;
		
		stage = BuildStage.STAGE_CREATE;
		
		// Open the gui - step 2:
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
		
		// 3. Create a BedwarsMap:
		this.bwmap = map;
		
		startBuilding();
	}
	
	public void startBuilding() {
		// 4. Teleport the player to it:
		p.teleport(this.map.getWorld().getSpawnLocation());
		p.sendMessage("Setting you to creative");
		p.setGameMode(GameMode.CREATIVE);
		
		// 5. Give the player the building tools:
		tools = new BedwarsBuildingItems(p, this);
		tools.enterTools();
		
		disps = new BedwarsBuildingDisplays(this);
		disps.updateSpawners();
	}
	
	/**
	 * Tests whether this build can be finished at this point, or
	 * if there are still things missing.
	 * It tests
	 *  - whether all beds have been placed.
	 */
	public boolean canFinish() {
		// test whether all beds have been placed:
		for (int i = 0; i < bwmap.getTeamNumber(); i++) {
			if (bwmap.getBedLocation(i) == null) return false;
		}
		
		return true;
	}
	
	@Nullable
	public BedwarsMap buildingFinished() {
		if (stage != BuildStage.STAGE_BUILDING) return null;
		
		stage = BuildStage.STAGE_FINISHED;
		
		// 7. Teleport the player back to the hub:
		disps.exit();
		tools.exitTools();
		p.setGameMode(GameMode.SURVIVAL);
		p.teleport(Bukkit.getWorld("world").getSpawnLocation());
		
		Main.instance().getBuilders().unregisterBuilder(p);
		
		Main.instance().getMapRegistry().printMaps();
		
		return bwmap;
	}
	
	public void addSpawner(final BedwarsIngotSpawner spawner) {
		bwmap.addSpawner(spawner);
		disps.updateSpawners();
		
	}
	
	/**
	 * Sets the bed location of a specified team.
	 * The position can be set to null to indicate that the bed hasn't been
	 * placed.
	 */
	public void setBedLocation(int team, @Nullable Vector loc) {
		if (stage != BuildStage.STAGE_BUILDING) return;
		
		bwmap.setBedLocation(team, loc);
	}
	
	/**
	 * Modifies the specified spawner to use the specified options.
	 * If the spawner is not a part of the currently built map,
	 * or we're not in the building stage,
	 * nothing happens.
	 */
	public void modifySpawner(BedwarsIngotSpawner spawner, Ingot resource, int amount, int tickSpeed) {
		if (stage != BuildStage.STAGE_BUILDING) return;
		if (!bwmap.getSpawners().contains(spawner)) return;
		
		spawner.setResource(resource);
		spawner.setAmount(amount);
		spawner.setTickSpeed(tickSpeed);
	}
	
	@Nullable
	public BedwarsMap getMap() {
		return bwmap;
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public void abort() {
		switch (stage) {
			case STAGE_INIT:
			case STAGE_INVALID:
				break;
			case STAGE_CREATE:
				p.closeInventory();
			case STAGE_BUILDING:
				disps.exit();
				tools.exitTools();
				Main.instance().getBuilders().unregisterBuilder(p);
			case STAGE_FINISHED:
				break;
		}
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
