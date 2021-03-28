package me.scrouthtv.game;

import me.scrouthtv.main.Main;
import me.scrouthtv.maps.IMap;
import me.scrouthtv.shop.Ingot;
import me.scrouthtv.utils.ArrayUtil;
import me.scrouthtv.utils.BlockUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Bed;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
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
	private static final String BEDS_IDENT = "beds";

	static {
		ConfigurationSerialization.registerClass(BedwarsMap.class);
	}

	private int teamSize; // serialized & deserialized
	private int teamNumber; // serialized & deserialized
	@Nonnull
	private IMap map; // map->uuid s&d
	
	private List<BedwarsIngotSpawner> spawners = new ArrayList<>(); // s&d
	private Vector[] beds; // s&d
	
	/**
	 * Create a new map with default settings.
	 */
	public BedwarsMap(@Nonnull IMap map) {
		setTeamSize(4);
		setTeamNumber(4);
		this.map = map;
		
		Main.instance().getMapRegistry().registerMap(this);
	}
	
	/**
	 * Only for testing.
	 */
	public void NOPROD_changeValues() {
		setTeamSize(2);
		setTeamNumber(8);
		spawners.add(new BedwarsIngotSpawner(new Vector(5.0, 120.0, 5.0), Ingot.INGOT_BRONZE));
		spawners.get(0).setSpeed(20, 10);
		spawners.add(new BedwarsIngotSpawner(new Vector(-5.0, 120.0, -5.0), Ingot.INGOT_SILVER));
		spawners.get(0).setSpeed(5, 1);
		
		beds[0] = new Vector(1, 1, 1);
		beds[1] = new Vector(-1, 15, 1);
		beds[2] = new Vector(25, 25, 25);
		beds[3] = new Vector(9, -12, 33);
		beds[4] = new Vector(12, 182, 98);
		beds[5] = new Vector(2, 58, 2);
		beds[6] = new Vector(11, 12, 13);
		beds[7] = new Vector(0, 0, 0);
	}
	
	public BedwarsGame createGame(String name) {
		return new BedwarsGame(this, name);
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
	
	/**
	 * Setting the team number also resets all team's beds.
	 */
	protected void setTeamNumber(final int teamNumber) {
		this.teamNumber = teamNumber;
		beds = new Vector[teamNumber];
	}
	
	protected void setBedLocation(final int team, final Vector loc) {
		if (team < 0 || team >= teamNumber)
			return;
		
		beds[team] = loc;
	}
	
	protected void addSpawner(BedwarsIngotSpawner spawner) {
		spawners.add(spawner);
	}
	
	@Nullable
	public BedwarsIngotSpawner getSpawner(Vector loc) {
		for (BedwarsIngotSpawner s : spawners) {
			if (s.getLocation().equals(loc))
				return s;
		}
		
		return null;
	}
	
	public List<BedwarsIngotSpawner> getSpawners() {
		return new ArrayList<>(spawners);
	}
	
	/**
	 * Searches for the team who owns this bed.
	 *
	 * Returns -1 if the team couldn't be found.
	 *
	 * @return the team's number, starting at 0.
	 */
	public int getTeamByBed(Vector loc) {
		final Block bloc = map.blockInWorld(loc);
		final Block base = BlockUtil.findBedPart(bloc, Bed.Part.FOOT);
		final Vector bvec = base.getLocation().toVector();
		
		for (int i = 0; i < beds.length; i++) {
			if (beds[i] != null && beds[i].equals(bvec)) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Retrieves the location of team's bed.
	 * If the location isn't set, or this team does not exist, null is returned.
	 */
	@Nullable
	public Vector getBedLocation(final int team) {
		if (team < 0 || team >= teamNumber)
			return null;
		return beds[team];
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
		result.put(BEDS_IDENT, beds);
		
		return result;
	}
	
	@Nonnull
	public static BedwarsMap deserialize(final Map<String, Object> map) {
		IMap imap = Main.instance().getMapManager().getByUUID((String) map.get(MAP_UUID_KEY_IDENT));
		BedwarsMap b = new BedwarsMap(imap);
		System.out.println("Got this map: " + b.map);
		
		if (map.get(TEAM_SIZE_KEY_IDENT) instanceof Integer)
			b.setTeamSize((Integer) map.get(TEAM_SIZE_KEY_IDENT));
		else
			System.out.println("team size not an integer: " + map.get(TEAM_SIZE_KEY_IDENT));
		
		if (map.get(TEAM_NUMBER_KEY_IDENT) instanceof Integer)
			b.setTeamNumber((Integer) map.get(TEAM_NUMBER_KEY_IDENT));
		else
			System.out.println("team number not an integer: " + map.get(TEAM_NUMBER_KEY_IDENT));
		
		b.spawners = (List<BedwarsIngotSpawner>) map.get(SPAWNER_IDENT);
		
		List<Vector> bedlist = (List<Vector>) map.get(BEDS_IDENT);
		ArrayUtil.listToArray(bedlist, b.beds);
		
		return b;
	}
	
	public void print() {
		System.out.println(String.format(" %d teams each %d players", getTeamNumber(), getTeamSize()));
		System.out.println(" " + spawners.size() + " spawners at");
		for (BedwarsIngotSpawner bwis : spawners)
			System.out.println("  - " + bwis.getLocation() + " (" + bwis.getResource() + ")");
		System.out.println(" beds:");
		for (int i = 0; i < getTeamNumber(); i++) {
			System.out.println("  " + i + ") " + beds[i]);
		}
	}
	
	public Location getSpawnLocation(final int team) {
		System.out.println("not implemented\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		return getMap().getWorld().getSpawnLocation();
	}
	
	public Location getSpectatorSpawnLocation() {
		return getMap().getWorld().getSpawnLocation().add(0, 20, 0);
	}
}
