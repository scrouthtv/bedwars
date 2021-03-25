package me.scrouthtv.maps;

import me.scrouthtv.main.IConfigurable;
import org.bukkit.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface IMapManager extends IConfigurable {
	List<IMap> listMaps();
	
	/**
	 * Create a new, empty world. For convenience, the map manager should add a single
	 * block at the spawn to start with.
	 *
	 * @param name Name of the map to be created.
	 * @return The map.
	 */
	IMap createNewMap(String name);
	
	@Nullable IMap getByName(String name);
	
	@Nullable IMap getByUUID(String UUID);
	
	@Nonnull IMap getByWorld(World w);
}
