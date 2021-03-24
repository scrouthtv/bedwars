package me.scrouthtv.maps;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DimAdapter implements IMapManager {
	
	private List<Dim> maps = new ArrayList<>();
	
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
		final Dim d = new Dim(w);
		maps.add(d);
		
		return d;
	}
	
	@Nullable
	@Override
	public IMap getByName(final String name) {
		for (Dim d : maps) {
			if (d.w.getName() == name) {
				return d;
			}
		}
		
		return null;
	}
	
	public class Dim implements IMap {
		
		private final World w;
		
		private Dim(World w) {
			this.w = w;
		}
		
		@Override
		public IMap cloneMap(final String target) {
			throw new RuntimeException("not impl");
		}
		
		@Override
		public boolean playerJoin(final Player p) {
			throw new RuntimeException("not impl");
		}
		
		@Override
		public String toString() {
			return w.getName();
		}
	}
	
}
