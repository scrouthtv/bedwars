package me.scrouthtv.maps;

import javax.annotation.Nullable;
import java.util.List;

public interface IMapManager {
	List<IMap> listMaps();
	IMap createNewMap(String name);
	@Nullable IMap getByName(String name);
}
