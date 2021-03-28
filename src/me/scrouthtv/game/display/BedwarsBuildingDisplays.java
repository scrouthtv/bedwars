package me.scrouthtv.game.display;

import me.scrouthtv.game.BedwarsIngotSpawner;
import me.scrouthtv.game.BuildProcedure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BedwarsBuildingDisplays {
	
	private final BuildProcedure paren;
	
	private final Map<BedwarsIngotSpawner, ResourceSpawnerDisplay> spawnerDisplay = new HashMap<>();
	
	public BedwarsBuildingDisplays(BuildProcedure proc) {
		this.paren = proc;
	}
	
	public void updateSpawners() {
		final List<BedwarsIngotSpawner> hasSpawners = paren.getMap().getSpawners();
		
		for (Map.Entry<BedwarsIngotSpawner, ResourceSpawnerDisplay> spawner : spawnerDisplay.entrySet()) {
			if (!hasSpawners.contains(spawner.getKey())) {
				// Remove all spawners that were removed:
				spawner.getValue().remove();
			} else {
				// Update all spawners that still exist:
				spawner.getValue().update();
				hasSpawners.remove(spawner.getKey());
			}
		}
		
		for (BedwarsIngotSpawner newSpawner : hasSpawners) {
			// Create displays for all new spawners:
			final ResourceSpawnerDisplay display = new ResourceSpawnerDisplay(paren, newSpawner);
			display.update();
			spawnerDisplay.put(newSpawner, display);
		}
	}
	
	public void exit() {
		for (ResourceSpawnerDisplay display : spawnerDisplay.values()) {
			display.remove();
		}
	}
}
