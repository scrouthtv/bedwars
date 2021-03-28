package me.scrouthtv.game;

import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class BuilderRegistry {
	private Map<Player, BuildProcedure> builders = new HashMap<>();
	
	@Nullable
	public BuildProcedure getProc(Player p) {
		return builders.get(p);
	}
	
	void registerBuilder(final Player p, final BuildProcedure proc) {
		builders.put(p, proc);
	}
	
	void unregisterBuilder(final Player p) {
		builders.remove(p);
	}
	
	public void closeAll() {
		for (BuildProcedure proc : builders.values()) {
			proc.abort();
		}
	}
}
