package me.scrouthtv.main;

import me.scrouthtv.commands.BedwarsCommands;
import me.scrouthtv.commands.IngotCommands;
import me.scrouthtv.commands.MapCommands;
import me.scrouthtv.game.*;
import me.scrouthtv.maps.IMapManager;
import me.scrouthtv.maps.DimAdapter;
import me.scrouthtv.shop.Shop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements CommandExecutor {
	
	private static Main instance;
	
	private IMapManager mapManager;
	private MapRegistry mapRegistry;
	private BuilderRegistry builders;
	private GameRegistry gameRegistry;
	private PlayerRegistry playerRegistry;
	
	/**
	 * instance returns the plugin instance that was the last to be enabled.
	 */
	public static Main instance() {
		return instance;
	}
	
	@Override
	public void onLoad() {
		ConfigurationSerialization.registerClass(BedwarsIngotSpawner.class, "bedwars-ingot-spawner");
	}
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "Starting Bedwars 1");
		
		Bukkit.getPluginManager().registerEvents(new Shop(), this);
		
		instance = this;
		
		mapManager = new DimAdapter();
		mapManager.loadConfig(getConfig());
		
		mapRegistry = new MapRegistry();
		
		builders = new BuilderRegistry();
		
		gameRegistry = new GameRegistry();
		Bukkit.getPluginManager().registerEvents(gameRegistry, this);
		
		playerRegistry = new PlayerRegistry();
		Bukkit.getPluginManager().registerEvents(playerRegistry, this);
	}
	
	public IMapManager getMapManager() {
		return mapManager;
	}
	
	public MapRegistry getMapRegistry() {
		return mapRegistry;
	}
	
	public BuilderRegistry getBuilders() {
		return builders;
	}
	
	public GameRegistry getGameRegistry() {
		return gameRegistry;
	}
	
	public PlayerRegistry getPlayerRegistry() {
		return playerRegistry;
	}
	
	@Override
	public void onDisable() {
		getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "Stopping Bedwars 1");
		
		builders.closeAll();
		
		mapManager.storeConfig(getConfig());
		saveConfig();
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		// Using cmd.getLabel() allows us to match /bedwars:xyz as well
		switch (cmd.getLabel()) {
			case "give-ingot":
				return IngotCommands.giveIngot(sender, cmd, args);
			case "build-bedwars":
				return MapCommands.buildBedwars(sender, cmd, args);
			case "save-bedwars":
				return MapCommands.saveBedwars(sender, cmd, args);
			case "load-bedwars":
				return MapCommands.loadBedwars(sender, cmd, args);
			case "list-maps":
				return MapCommands.listMaps(sender, cmd, args);
			case "bw-create":
				return BedwarsCommands.bwCreate(sender, cmd, args);
			case "bw-start":
				return BedwarsCommands.bwStart(sender, cmd, args);
			case "bw-join":
				return BedwarsCommands.bwJoin(sender, cmd, args);
		}
		return false;
	}
}
