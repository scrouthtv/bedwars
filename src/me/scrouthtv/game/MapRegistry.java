package me.scrouthtv.game;

import me.scrouthtv.main.IConfigurable;
import me.scrouthtv.main.Main;
import me.scrouthtv.maps.IMap;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
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
	
	public Collection<BedwarsMap> listMaps() {
		return maps.values();
	}
	
	@Override
	public void loadConfig(final FileConfiguration config) {
		System.out.println("Looking for these maps:");
		System.out.println(config.getStringList(MAP_LIST_IDENT));
		
		for (String uuid : config.getStringList(MAP_LIST_IDENT)) {
			MemorySection group = (MemorySection) config.get(uuid);
			IMap map = Main.instance().getMapManager().getByUUID((String) group.get(MAP_UUID_IDENT));
			
			MemorySection mapData = (MemorySection) group.get(BWMAP_IDENT);
			BedwarsMap bwmap = BedwarsMap.deserialize(mapData.getValues(true));
			maps.put(map, bwmap);
		}
	}
	
	public void printMaps() {
		System.out.println(" == MAP LIST (" + maps.size() + " total) == ");
		for (Map.Entry<IMap, BedwarsMap> map : maps.entrySet()) {
			System.out.println(map.getKey());
			System.out.println(map.getKey().getWorld().getName() + " - " + map.getKey().getWorld().getUID());
			map.getValue().print();
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
	
	private static final String DEFAULT_SAVE_NAME = "maps.yml";
	
	public void saveMaps() throws IOException {
		saveMaps(DEFAULT_SAVE_NAME);
	}
	
	public void saveMaps(String file) throws IOException {
		File bw = new File(Main.instance().getDataFolder(), file);
		bw.createNewFile();
		FileConfiguration config = YamlConfiguration.loadConfiguration(bw);
		storeConfig(config);
		config.save(bw);
	}
	
	public void loadMaps() {
		loadMaps(DEFAULT_SAVE_NAME);
	}
	
	public void loadMaps(String file) {
		final File f = new File(Main.instance().getDataFolder(), file);
		FileConfiguration config = YamlConfiguration.loadConfiguration(f);
		loadConfig(config);
	}
	
}
