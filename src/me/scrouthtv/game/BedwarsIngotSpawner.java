package me.scrouthtv.game;

import me.scrouthtv.shop.Ingot;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.configuration.serialization.SerializableAs;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;

@SerializableAs("ingot-spawner")
public class BedwarsIngotSpawner implements ConfigurationSerializable {
	
	private static final String LOCATION_IDENT = "loc";
	private static final String RESOURCE_IDENT = "what";
	private static final String TICK_SPEED_IDENT = "speed";
	private static final String AMOUNT_IDENT = "amount";
	
	private Location loc;
	private Ingot resource;
	private int tickSpeed;
	private int amount;
	
	static {
		ConfigurationSerialization.registerClass(BedwarsIngotSpawner.class);
	}
	
	@Nonnull
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> result = new LinkedHashMap<>();
		
		result.put(LOCATION_IDENT, loc);
		result.put(RESOURCE_IDENT, resource);
		result.put(TICK_SPEED_IDENT, tickSpeed);
		result.put(AMOUNT_IDENT, amount);
		
		return result;
	}
	
	public static BedwarsIngotSpawner deserialize(Map<String, Object> map) {
		BedwarsIngotSpawner s = new BedwarsIngotSpawner();
		
		if (map.get(LOCATION_IDENT) instanceof Location)
			s.loc = (Location) map.get(LOCATION_IDENT);
		else
			System.out.println("Invalid location: " + map.get(LOCATION_IDENT));
		
		s.resource = Ingot.fromString((String) map.get(RESOURCE_IDENT));
		if (s.resource == null)
			System.out.println("Invalid resource: " + map.get(RESOURCE_IDENT));
		
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
}
