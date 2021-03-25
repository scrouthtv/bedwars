package me.scrouthtv.main;

import me.scrouthtv.commands.IngotCommands;
import me.scrouthtv.commands.MapCommands;
import me.scrouthtv.maps.IMapManager;
import me.scrouthtv.maps.DimAdapter;
import me.scrouthtv.shop.Shop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements CommandExecutor {
	
	private static Main instance;
	
	private IMapManager mapManager;
	
	/**
	 * instance returns the plugin instance that was the last to be enabled.
	 */
	public static Main instance() {
		return instance;
	}

	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "Starting Bedwars 1");
		
		Bukkit.getPluginManager().registerEvents(new Shop(), this);
		
		instance = this;
		
		mapManager = new DimAdapter();
		
		mapManager.loadConfig(getConfig());
	}
	
	public IMapManager getMapManager() {
		return mapManager;
	}
	
	@Override
	public void onDisable() {
		getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "Stopping Bedwars 1");
		
		mapManager.storeConfig(getConfig());
		saveConfig();
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		// Using command.getLabel() allows us to match /bedwars:xyz as well
		switch (command.getLabel()) {
			case "give-ingot":
				return IngotCommands.giveIngot(sender, command, args);
			case "build-bedwars":
				return MapCommands.buildBedwars(sender, command, args);
			case "list-maps":
				return MapCommands.listMaps(sender, command, args);
			case "clone-map":
				return MapCommands.cloneMap(sender, command, args);
		}
		return false;
	}
}
