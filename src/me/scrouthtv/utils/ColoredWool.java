package me.scrouthtv.utils;

import org.bukkit.DyeColor;
import org.bukkit.Material;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

public class ColoredWool {
	
	private static final Map<DyeColor, Material> colors = new EnumMap<DyeColor, Material>(DyeColor.class);
	
	static {
		colors.put(DyeColor.WHITE, Material.WHITE_WOOL);
		colors.put(DyeColor.ORANGE, Material.ORANGE_WOOL);
		colors.put(DyeColor.MAGENTA, Material.MAGENTA_WOOL);
		colors.put(DyeColor.LIGHT_BLUE, Material.LIGHT_BLUE_WOOL);
		colors.put(DyeColor.YELLOW, Material.YELLOW_WOOL);
		colors.put(DyeColor.LIME, Material.LIME_WOOL);
		colors.put(DyeColor.PINK, Material.PINK_WOOL);
		colors.put(DyeColor.GRAY, Material.GRAY_WOOL);
		colors.put(DyeColor.LIGHT_GRAY, Material.LIGHT_GRAY_WOOL);
		colors.put(DyeColor.CYAN, Material.CYAN_WOOL);
		colors.put(DyeColor.PURPLE, Material.PURPLE_WOOL);
		colors.put(DyeColor.BLUE, Material.BLUE_WOOL);
		colors.put(DyeColor.BROWN, Material.BROWN_WOOL);
		colors.put(DyeColor.GREEN, Material.GREEN_WOOL);
		colors.put(DyeColor.RED, Material.RED_WOOL);
		colors.put(DyeColor.BLACK, Material.BLACK_WOOL);
	}
	
	@Nullable
	public static DyeColor colorFromWool(Material m) {
		for (Map.Entry<DyeColor, Material> wool : colors.entrySet()) {
			if (wool.getValue() == m) return wool.getKey();
		}
		
		return null;
	}
	
	@Nullable
	public static Material woolFromColor(DyeColor c) {
		return colors.get(c);
	}
	
}
