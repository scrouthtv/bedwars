package me.scrouthtv.shop;

import org.bukkit.inventory.ItemStack;

public class ShopTrade {
	
	private Ingot cost;
	private int costAmount;
	private ItemStack product;
	
	public ShopTrade(Ingot cost, int costAmount, ItemStack product) {
		this.cost = cost;
		this.costAmount = costAmount;
		this.product = product;
	}
	
	public Ingot getCost() {
		return cost;
	}
	
	public int getCostAmount() {
		return costAmount;
	}
	
	public ItemStack getProduct() {
		return product;
	}
	
}