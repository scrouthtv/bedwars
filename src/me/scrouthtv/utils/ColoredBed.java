package me.scrouthtv.utils;

import org.bukkit.DyeColor;
import org.bukkit.Material;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

public class ColoredBed {
	
	private static final Map<DyeColor, Material> colors = new EnumMap<DyeColor, Material>(DyeColor.class);
	
	static {
		colors.put(DyeColor.WHITE, Material.WHITE_BED);
		colors.put(DyeColor.ORANGE, Material.ORANGE_BED);
		colors.put(DyeColor.MAGENTA, Material.MAGENTA_BED);
		colors.put(DyeColor.LIGHT_BLUE, Material.LIGHT_BLUE_BED);
		colors.put(DyeColor.YELLOW, Material.YELLOW_BED);
		colors.put(DyeColor.LIME, Material.LIME_BED);
		colors.put(DyeColor.PINK, Material.PINK_BED);
		colors.put(DyeColor.GRAY, Material.GRAY_BED);
		colors.put(DyeColor.LIGHT_GRAY, Material.LIGHT_GRAY_BED);
		colors.put(DyeColor.CYAN, Material.CYAN_BED);
		colors.put(DyeColor.PURPLE, Material.PURPLE_BED);
		colors.put(DyeColor.BLUE, Material.BLUE_BED);
		colors.put(DyeColor.BROWN, Material.BROWN_BED);
		colors.put(DyeColor.GREEN, Material.GREEN_BED);
		colors.put(DyeColor.RED, Material.RED_BED);
		colors.put(DyeColor.BLACK, Material.BLACK_BED);
	}
	
	public static boolean isColoredBed(Material m) {
		return colorFromBed(m) != null;
	}
	
	@Nullable
	public static DyeColor colorFromBed(Material m) {
		for (Map.Entry<DyeColor, Material> bed : colors.entrySet()) {
			if (bed.getValue() == m) return bed.getKey();
		}
		
		return null;
	}
	
	@Nullable
	public static Material bedFromColor(DyeColor c) {
		return colors.get(c);
	}
	
}
