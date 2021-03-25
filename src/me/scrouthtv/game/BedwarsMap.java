package me.scrouthtv.game;

import me.scrouthtv.main.Main;
import me.scrouthtv.maps.IMap;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.configuration.serialization.SerializableAs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

@SerializableAs("BedwarsMap")
public class BedwarsMap implements ConfigurationSerializable {
	
	static {
		ConfigurationSerialization.registerClass(BedwarsMap.class);
	}
	
	public BedwarsMap(@Nullable IMap map) {
		this();
		this.map = map;
	}
	
	/**
	 * Create a new map with default settings.
	 */
	private BedwarsMap() {
		teamSize = 4;
		teamNumber = 4;
	}
	
	private static final String TEAM_SIZE_KEY_IDENT = "team-size";
	private static final String TEAM_NUMBER_KEY_IDENT = "number-of-teams";
	private static final String MAP_UUID_KEY_IDENT = "map";
	private static final String SPAWNER_IDENT = "spawners";
	
	private int teamSize;
	private int teamNumber;
	@Nullable private IMap map;
	
	public int getTeamSize() {
		return teamSize;
	}
	
	protected void setTeamSize(final int teamSize) {
		this.teamSize = teamSize;
	}
	
	public int getTeamNumber() {
		return teamNumber;
	}
	
	protected void setTeamNumber(final int teamNumber) {
		this.teamNumber = teamNumber;
	}
	
	@Nullable
	public IMap getMap() {
		return map;
	}
	
	/**
	 * serialize attempts to store this map into the top-level of a configuration.
	 * If the settings are not complete, it returns a partial serialization.
	 *
	 * @return a map that stores all non-transient properties of this BedwarsMap.
	 */
	@Nonnull
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> result = new LinkedHashMap<>();
		
		result.put(TEAM_SIZE_KEY_IDENT, "team-size");
		result.put(TEAM_NUMBER_KEY_IDENT, "number-of-teams");
		result.put(MAP_UUID_KEY_IDENT, map.UUID());
		
		return result;
	}
	
	@Nonnull
	public static BedwarsMap deserialize(final Map<String, Object> map) {
		BedwarsMap b = new BedwarsMap();
		
		if (map.get(TEAM_SIZE_KEY_IDENT) instanceof Integer)
			b.teamSize = (Integer) map.get(TEAM_SIZE_KEY_IDENT);
		else
			System.out.println("team size not an integer: " + map.get(TEAM_SIZE_KEY_IDENT));
		
		if (map.get(TEAM_NUMBER_KEY_IDENT) instanceof Integer)
			b.teamNumber = (Integer) map.get(TEAM_NUMBER_KEY_IDENT);
		else
			System.out.println("team number not an integer: " + map.get(TEAM_NUMBER_KEY_IDENT));
		
		b.map = Main.instance().getMapManager().getByUUID((String) map.get(MAP_UUID_KEY_IDENT));
		System.out.println("Got this map: " + b.map);
		
		return b;
	}
}
