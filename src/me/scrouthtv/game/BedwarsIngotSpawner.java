package me.scrouthtv.game;

import me.scrouthtv.shop.Ingot;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

public class BedwarsIngotSpawner implements ConfigurationSerializable {
	
	private static final String LOCATION_IDENT = "loc";
	private static final String RESOURCE_IDENT = "what";
	private static final String TICK_SPEED_IDENT = "speed";
	private static final String AMOUNT_IDENT = "amount";
	
	private final Vector loc;
	private final Ingot resource;
	private int tickSpeed;
	private int amount;
	
	BedwarsIngotSpawner(Vector loc, Ingot resource) {
		this.loc = loc;
		this.resource = resource;
		this.tickSpeed = 10;
		this.amount = 1;
	}
	
	void setSpeed(int tickSpeed, int amount) {
		this.tickSpeed = tickSpeed;
		this.amount = amount;
	}
	
	@Nonnull
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> result = new LinkedHashMap<>();
		
		result.put(LOCATION_IDENT, loc.serialize());
		result.put(RESOURCE_IDENT, resource.toString());
		result.put(TICK_SPEED_IDENT, tickSpeed);
		result.put(AMOUNT_IDENT, amount);
		
		return result;
	}
	
	@Nullable
	public static BedwarsIngotSpawner deserialize(Map<String, Object> map) {
		Vector loc;
		Ingot i;
		
		if (map.containsKey(LOCATION_IDENT))
			loc = Vector.deserialize((Map<String, Object>) map.get(LOCATION_IDENT));
		else {
			System.out.println("Invalid location: " + map.get(LOCATION_IDENT));
			return null;
		}
		
		i = Ingot.fromString((String) map.get(RESOURCE_IDENT));
		if (i == null)
			System.out.println("Invalid resource: " + map.get(RESOURCE_IDENT));
		
		BedwarsIngotSpawner s = new BedwarsIngotSpawner(loc, i);
		
		if (map.get(TICK_SPEED_IDENT) instanceof Integer)
			s.tickSpeed = (Integer) map.get(TICK_SPEED_IDENT);
		else
			System.out.println("Invalid tick speed: " + map.get(TICK_SPEED_IDENT));
		
		if (map.get(AMOUNT_IDENT) instanceof Integer)
			s.amount = (Integer) map.get(AMOUNT_IDENT);
		else
			System.out.println("Invalid amount: " + map.get(AMOUNT_IDENT));
		
		return s;
	}
	
	public Vector getLocation() {
		return loc;
	}
	
	public Ingot getResource() {
		return resource;
	}
}
