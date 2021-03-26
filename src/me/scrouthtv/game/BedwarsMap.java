package me.scrouthtv.game;

import me.scrouthtv.main.Main;
import me.scrouthtv.maps.IMap;
import me.scrouthtv.shop.Ingot;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SerializableAs("BedwarsMap")
public class BedwarsMap implements ConfigurationSerializable, Cloneable {
	
	private static final String TEAM_SIZE_KEY_IDENT = "team-size";
	private static final String TEAM_NUMBER_KEY_IDENT = "number-of-teams";
	private static final String MAP_UUID_KEY_IDENT = "map";
	private static final String SPAWNER_IDENT = "spawners";

	static {
		ConfigurationSerialization.registerClass(BedwarsMap.class);
	}

	private int teamSize;
	private int teamNumber;
	@Nonnull
	private IMap map;
	
	private List<BedwarsIngotSpawner> spawners = new ArrayList<>();
	
	/**
	 * Create a new map with default settings.
	 */
	public BedwarsMap(@Nonnull IMap map) {
		teamSize = 4;
		teamNumber = 4;
		this.map = map;
		
		Main.instance().getMapRegistry().registerMap(this);
	}
	
	public void NOPROD_changeValues() {
		teamSize = 2;
		teamNumber = 8;
		spawners.add(new BedwarsIngotSpawner(new Vector(5.0, 120.0, 5.0), Ingot.INGOT_BRONZE));
		spawners.get(0).setSpeed(20, 10);
		spawners.add(new BedwarsIngotSpawner(new Vector(-5.0, 120.0, -5.0), Ingot.INGOT_SILVER));
		spawners.get(0).setSpeed(5, 1);
	}
	
	public BedwarsGame createGame(String name) {
		return new BedwarsGame(this, name);
	}
	
	@Nonnull
	public static BedwarsMap deserialize(final Map<String, Object> map) {
		IMap imap = Main.instance().getMapManager().getByUUID((String) map.get(MAP_UUID_KEY_IDENT));
		BedwarsMap b = new BedwarsMap(imap);
		System.out.println("Got this map: " + b.map);
		
		if (map.get(TEAM_SIZE_KEY_IDENT) instanceof Integer)
			b.teamSize = (Integer) map.get(TEAM_SIZE_KEY_IDENT);
		else
			System.out.println("team size not an integer: " + map.get(TEAM_SIZE_KEY_IDENT));
		
		if (map.get(TEAM_NUMBER_KEY_IDENT) instanceof Integer)
			b.teamNumber = (Integer) map.get(TEAM_NUMBER_KEY_IDENT);
		else
			System.out.println("team number not an integer: " + map.get(TEAM_NUMBER_KEY_IDENT));
		
		Object spawners = map.get(SPAWNER_IDENT);
		System.out.println(spawners);
		
		return b;
	}
	
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
	
	@Nonnull
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
		
		result.put(TEAM_SIZE_KEY_IDENT, teamSize);
		result.put(TEAM_NUMBER_KEY_IDENT, teamNumber);
		result.put(MAP_UUID_KEY_IDENT, map.UUID());
		result.put(SPAWNER_IDENT, spawners);
		
		return result;
	}
}
