package me.scrouthtv.game;

import me.scrouthtv.main.Main;
import me.scrouthtv.maps.IMap;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class MapRegistry {
	private static Map<IMap, BedwarsMap> maps = new HashMap<>();
	
	@Nullable
	public static BedwarsMap getMap(IMap m) {
		return maps.get(m);
	}
	
	@Nullable
	public static BedwarsMap getMap(String name) {
		return maps.get(Main.instance().getMapManager().getByName(name));
	}
	
	static void registerMap(BedwarsMap m) {
		maps.put(m.getMap(), m);
	}
}
