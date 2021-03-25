package me.scrouthtv.maps;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.*;

public class DimAdapter implements IMapManager {
	
	private Set<Dim> maps = new HashSet<>();
	
	private static final String MAP_LIST_IDENT = "maps";
	
	@Override
	public List<IMap> listMaps() {
		return new ArrayList<>(maps);
	}
	
	@Override
	public IMap createNewMap(final String name) {
		final WorldCreator creator = new WorldCreator(name);
		creator.generator(new VoidGenerator());
		creator.seed(0);
		creator.type(WorldType.FLAT);
		
		final World w = Bukkit.createWorld(creator);
		w.setSpawnLocation(new Location(w, 0.5, 120, 0.5));
		(new Location(w, 0, 118, 0)).getBlock().setType(Material.STONE);
		
		final Dim d = new Dim(w);
		maps.add(d);
		
		return d;
	}
	
	@Nullable
	@Override
	public IMap getByName(final String name) {
		for (Dim d : maps) {
			if (d.w.getName().equals(name)) return d;
		}
		
		return null;
	}
	
	@Override
	public void loadConfig(final FileConfiguration config) {
		List<String> mapNames = config.getStringList(MAP_LIST_IDENT);
		System.out.println("Read these maps:");
		for (String mapName : mapNames) {
			System.out.println(mapName);
			World w = Bukkit.createWorld(new WorldCreator(mapName));
			//World w = Bukkit.getWorld(mapName);
			System.out.println(w);
			if (w != null) this.maps.add(new Dim(w));
		}
		System.out.println("Available maps:");
		System.out.println(Bukkit.getWorlds());
	}
	
	@Override
	public void storeConfig(final FileConfiguration config) {
		List<String> mapNames = new ArrayList<>();
		System.out.println("Writing these maps:");
		for (Dim map : maps) {
			mapNames.add(map.w.getName());
			System.out.println(map.w.getName());
		}
		config.set(MAP_LIST_IDENT, mapNames);
	}
	
	public class Dim implements IMap {
		
		private final World w;
		
		private Dim(World w) {
			this.w = w;
		}
		
		@Override
		public IMap cloneMap(final String target) {
			final Dim d = new Dim(DimCloner.cloneDim(w, target));
			DimAdapter.this.maps.add(d);
			return d;
		}
		
		@Override
		public boolean playerJoin(final Player p) {
			return p.teleport(w.getSpawnLocation());
		}
		
		@Override
		public String toString() {
			return w.getName();
		}
		
		@Override
		public int hashCode() {
			return w.getUID().hashCode();
		}
	}
	
}
