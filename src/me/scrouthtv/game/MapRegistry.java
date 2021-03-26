package me.scrouthtv.game;

import me.scrouthtv.main.IConfigurable;
import me.scrouthtv.main.Main;
import me.scrouthtv.maps.IMap;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nullable;
import java.util.*;

public class MapRegistry implements IConfigurable {
	private Map<IMap, BedwarsMap> maps = new HashMap<>();
	
	private static final String MAP_LIST_IDENT = "bedwars-maps";
	private static final String MAP_UUID_IDENT = "map-uuid";
	private static final String BWMAP_IDENT = "game-settings";
	
	@Nullable
	public BedwarsMap getMap(IMap m) {
		return maps.get(m);
	}
	
	@Nullable
	public BedwarsMap getMap(String name) {
		return maps.get(Main.instance().getMapManager().getByName(name));
	}
	
	void registerMap(BedwarsMap m) {
		maps.put(m.getMap(), m);
	}
	
	@Override
	public void loadConfig(final FileConfiguration config) {
		System.out.println("Looking for these maps:");
		System.out.println(config.getStringList(MAP_LIST_IDENT));
		
		for (String uuid : config.getStringList(MAP_LIST_IDENT)) {
			Map<String, Object> group = (Map<String, Object>) config.get(uuid);
			IMap map = Main.instance().getMapManager().getByUUID((String) group.get(MAP_UUID_IDENT));
			BedwarsMap bwmap = BedwarsMap.deserialize((Map<String, Object>) group.get(BWMAP_IDENT));
			maps.put(map, bwmap);
		}
	}
	
	public void printMaps() {
		System.out.println(" == MAP LIST (" + maps.size() + " total) == ");
		for (Map.Entry<IMap, BedwarsMap> map : maps.entrySet()) {
			System.out.println(map.getKey());
			System.out.println(map.getKey().getWorld().getName() + " - " + map.getKey().getWorld().getUID());
			System.out.println(String.format(" %d x %d", map.getValue().getTeamNumber(), map.getValue().getTeamSize()));
		}
		System.out.println(" ========================== ");
	}
	
	@Override
	public void storeConfig(final FileConfiguration config) {
		List<String> mapList = new ArrayList<>();
		for (IMap map : maps.keySet()) mapList.add(map.UUID());
		config.set(MAP_LIST_IDENT, mapList);
		
		for (Map.Entry<IMap, BedwarsMap> map : maps.entrySet())
			config.set(map.getKey().UUID(), serializeMap(map.getKey(), map.getValue()));
	}
	
	private Map<String, Object> serializeMap(IMap imap, BedwarsMap bwmap) {
		Map<String, Object> serial = new LinkedHashMap<>();
		
		serial.put(MAP_UUID_IDENT, imap.UUID());
		serial.put(BWMAP_IDENT, bwmap.serialize());
		
		return serial;
	}
	
}
