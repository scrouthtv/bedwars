package me.scrouthtv.shop;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ShopItems {
	public static final ItemStack KNOCK_STICK = new ItemStack(Material.STICK);
	
	public static final ItemStack SWORD_ONE = new ItemStack(Material.GOLDEN_SWORD);
	public static final ItemStack SWORD_TWO = new ItemStack(Material.GOLDEN_SWORD);
	public static final ItemStack SWORD_THREE = new ItemStack(Material.IRON_SWORD);
	
	static {
		KNOCK_STICK.addUnsafeEnchantment(Enchantment.KNOCKBACK, 3);
		
		SWORD_ONE.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		SWORD_TWO.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		SWORD_TWO.addEnchantment(Enchantment.DURABILITY, 2);
		SWORD_THREE.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		SWORD_THREE.addEnchantment(Enchantment.DURABILITY, 2);
		SWORD_THREE.addEnchantment(Enchantment.FIRE_ASPECT, 1);
	}
}
