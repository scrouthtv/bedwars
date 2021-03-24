package me.scrouthtv.shop;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class FastShop implements IShop, Listener {
	
	private Inventory inv;
	
	private ShopTrade[] trades;
	
	protected FastShop() {
		inv = Bukkit.createInventory(null, 5*9, "SHOP");
	}
	
	public Inventory getInventory() {
		Bukkit.getPluginManager().registerEvents(this, me.scrouthtv.main.Main.instance());
		return inv;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent ev) {
		if (ev.getClickedInventory() != inv) {
			return;
		}
		
		ev.setCancelled(true);
		
		final ShopTrade t = getTrade(ev.getSlot());
		if (t == null) {
			return;
		}
		
		if (!(ev.getWhoClicked() instanceof Player)) {
			return;
		}
		final Player p = (Player) ev.getWhoClicked();
		
		int amount;
		switch (ev.getClick()) {
			case LEFT:
				amount = 1;
				break;
			case RIGHT:
				amount = 5;
				break;
			case SHIFT_LEFT:
				amount = 64;
				break;
			default:
				amount = 3;
				break;
		}
		
		buy(p, t, amount);
	}
	
	private void buy(final Player p, final ShopTrade t, int amount) {
		
		final int ownedResource = countResource(p, t.getCost());
		if (ownedResource / t.getCostAmount() < amount) {
			amount = ownedResource / t.getCostAmount();
		}
		
		removeResource(p, t.getCost(), t.getCostAmount() * amount);
		
		final ItemStack product = t.getProduct();
		product.setAmount(amount * product.getAmount());
		p.getInventory().addItem(product);
	}
	
	private void removeResource(final Player p, final Ingot ingot, int amount) {
		final Inventory inv = p.getInventory();
		final ItemStack ingotStack = ingot.asStack(1);
		
		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack item = inv.getItem(i);
			if (ingotStack.isSimilar(item)) {
				if (item.getAmount() > amount) {
					item.setAmount(item.getAmount() - amount);
					amount = 0;
					return;
				} else {
					amount -= item.getAmount();
					inv.setItem(i, null);
					
					if (amount == 0) return;
				}
			}
		}
	}
	
	private int countResource(final Player p, final Ingot i) {
		int amount = 0;
		
		final ItemStack ingotStack = i.asStack(1);
		
		for (final ItemStack item : p.getInventory()) {
			if (ingotStack.isSimilar(item))
				amount += item.getAmount();
		}
		
		return amount;
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent ev) {
		if (ev.getInventory() != inv) {
			return;
		}
		
		HandlerList.unregisterAll(this);
	}
	
	@Override
	public void setTrades(final ShopTrade[] t) {
		trades = t;
		
		// Display the first 9 items:
		for (int i = 0; i < t.length && i < 9; i++) {
			setTrade(i, t[i]);
		}
	}
	
	private void setTrade(final int position, final ShopTrade t) {
		inv.setItem(position + 9, t.getProduct());
		inv.setItem(position + 2 * 9,t.getCost().asStack(t.getCostAmount()));
	}
	
	@Nullable
	private ShopTrade getTrade(int position) {
		if (position < 9 || position >= 3*9) {
			return null;
		}
		
		return trades[position % 9];
	}
}
