package me.scrouthtv.utils;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class UI {
	public static void playErrorSound(final Player p) {
		p.playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, SoundCategory.MASTER, 1, 1);
	}
	
	public static void playSuccessSound(final Player p) {
		p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 1, 1);
	}
	
	public static void playBedDestroySound(final Player p) {
		p.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1, 1);
	}
}
