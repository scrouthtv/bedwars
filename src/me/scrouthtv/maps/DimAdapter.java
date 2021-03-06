package me.scrouthtv.maps;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
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
	
	@Nullable
	@Override
	public IMap getByUUID(final String uuid) {
		return getByWorld(Bukkit.getWorld(UUID.fromString(uuid)));
	}
	
	@Nonnull
	public IMap getByWorld(final World world) {
		for (Dim map : maps) {
			if (map.getWorld().equals(world))
				return map;
		}
		
		Dim d = new Dim(world);
		maps.add(d);
		return d;
	}
	
	@Override
	public void loadConfig(final FileConfiguration config) {
		List<String> mapNames = config.getStringList(MAP_LIST_IDENT);
		for (String mapName : mapNames) {
			World w = Bukkit.createWorld(new WorldCreator(mapName));
			if (w != null) this.maps.add(new Dim(w));
		}
	}
	
	@Override
	public void storeConfig(final FileConfiguration config) {
		List<String> mapNames = new ArrayList<>();
		for (Dim map : maps) {
			mapNames.add(map.w.getName());
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
		public World getWorld() {
			return w;
		}
		
		@Override
		public String toString() {
			return w.getName();
		}
		
		@Override
		public String UUID() {
			return w.getUID().toString();
		}
		
		@Override
		public Location vecInWorld(final Vector v) {
			return new Location(w, v.getX(), v.getY(), v.getZ());
		}
		
		@Override
		public Block blockInWorld(final Vector v) {
			return w.getBlockAt(v.getBlockX(), v.getBlockY(), v.getBlockZ());
		}
		
		@Override
		public int hashCode() {
			return w.getUID().hashCode();
		}
	}
	
}
