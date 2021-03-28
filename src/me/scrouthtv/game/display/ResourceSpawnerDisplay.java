package me.scrouthtv.game.display;

import me.scrouthtv.game.BedwarsIngotSpawner;
import me.scrouthtv.game.BuildProcedure;
import me.scrouthtv.main.Main;
import me.scrouthtv.shop.Ingot;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class ResourceSpawnerDisplay implements Listener, ResourceSpawnerSettings.SpawnerChangedCallback {
	private final BuildProcedure paren;
	private final BedwarsIngotSpawner spawner;
	
	@Nullable
	private ResourceSpawnerSettings gui;
	
	private final Item item;
	
	public ResourceSpawnerDisplay(final BuildProcedure paren, final BedwarsIngotSpawner spawner) {
		this.paren = paren;
		this.spawner = spawner;
		
		final World w = paren.getMap().getMap().getWorld();
		item = w.dropItem(spawner.getLocation().toLocation(w), spawner.getResource().asStack(1));
		
		item.setInvulnerable(true);
		item.setPersistent(true);
		item.setCustomName("heyyy");
		item.setCustomNameVisible(true);
		item.setPickupDelay(20);
		item.setVelocity(new Vector(0, 0, 0));
		update();
		
		Bukkit.getPluginManager().registerEvents(this, Main.instance());
	}
	
	public void update() {
		item.setCustomName(String.format("%d %s every %s",
				spawner.getAmount(), spawner.getResource().toString(),
				spawner.getTickSpeed() == 1 ? "tick" : spawner.getTickSpeed() + " ticks"));
		item.setItemStack(spawner.getResource().asStack(1));
	}
	
	public void remove() {
		item.remove();
	}

	@EventHandler
	public void onPickup(EntityPickupItemEvent ev) {
		if (ev.getItem() != item) return;
		
		ev.setCancelled(true);
		
		Player p = (Player) ev.getEntity();
		if (gui != null) return;
		
		gui = new ResourceSpawnerSettings(spawner);
		gui.setCallback(this);
		gui.open(p);
	}
	
	@Override
	public void modifyFinished(final Ingot resource, final int amount, final int tickSpeed) {
		gui = null;
		item.setPickupDelay(40);
		paren.modifySpawner(spawner, resource, amount, tickSpeed);
		update();
	}
}
