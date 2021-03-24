package me.scrouthtv.maps;

import me.scrouthtv.main.IConfigurable;

import javax.annotation.Nullable;
import java.util.List;

public interface IMapManager extends IConfigurable {
	List<IMap> listMaps();
	IMap createNewMap(String name);
	@Nullable IMap getByName(String name);
}
