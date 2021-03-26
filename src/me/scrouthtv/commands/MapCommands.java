package me.scrouthtv.commands;

import me.scrouthtv.game.BedwarsMap;
import me.scrouthtv.game.BuildProcedure;
import me.scrouthtv.game.BuilderRegistry;
import me.scrouthtv.main.Main;
import me.scrouthtv.maps.IMap;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MapCommands {
	public static boolean buildBedwars(final CommandSender sender, final Command cmd, final String[] args) {
		IMap map;
		Player p;
		if (args.length == 1) {
			if (sender instanceof Player) {
				buildBW((Player) sender, args[0]);
			} else {
				sender.sendMessage(ChatColor.RED + "Please specify a player.");
				return false;
			}
			
		} else if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Please specify a map.");
			return false;
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "NOT IMPLEMENTED");
			return false;
		}
		
		return false;
	}
	
	private static boolean buildBW(final Player p, final String map) {
		Main.instance().getMapRegistry().printMaps();
		
		// First test if the bwmap already exists and they just want to edit it:
		BedwarsMap bwmap = Main.instance().getMapRegistry().getMap(map);
		if (bwmap != null) {
			System.out.println("resuming building an existing map");
			BuildProcedure proc = new BuildProcedure(p, bwmap);
			proc.startBuilding();
			return true;
		} else {
			// bwmap does not exist, create a new one:
			IMap imap = Main.instance().getMapManager().getByName(map);
			
			if (imap == null) {
				System.out.println("Map doesn't exist, creating it.");
				imap = Main.instance().getMapManager().createNewMap(map);
			}
			
			BuildProcedure proc = new BuildProcedure(p, imap);
			proc.start();
			return true;
		}
	}
	
	public static boolean listMaps(final CommandSender sender, final Command cmd, final String[] args) {
		List<IMap> maps = Main.instance().getMapManager().listMaps();
		sender.sendMessage(String.format("%d known maps:", maps.size()));
		for (IMap m : maps)
			sender.sendMessage(m.toString());
		return true;
	}
	
	public static boolean saveBedwars(final CommandSender sender, final Command command, final String[] args) {
		IMap imap = Main.instance().getMapManager().getByName("test5clone");
		BedwarsMap bwmap = new BedwarsMap(imap);
		bwmap.NOPROD_changeValues();
		System.out.println(bwmap.getMap());
		
		IMap imap2 = Main.instance().getMapManager().getByName("test5");
		BedwarsMap bwmap2 = new BedwarsMap(imap2);
		
		Main.instance().getMapRegistry().printMaps();
		
		try {
			File bw = new File(Main.instance().getDataFolder(), "test5clone.yml");
			bw.createNewFile();
			FileConfiguration config = YamlConfiguration.loadConfiguration(bw);
			/*config.set("asdf", bwmap.serialize());*/
			Main.instance().getMapRegistry().storeConfig(config);
			sender.sendMessage(ChatColor.GREEN + "Success.");
			config.save(bw);
			
			return true;
		} catch (IOException ex) {
			sender.sendMessage(ChatColor.RED + "Error writing config:");
			ex.printStackTrace();
			
			return false;
		}
	}
	
	public static boolean loadBedwars(final CommandSender sender, final Command command, final String[] args) {
		File bw = new File(Main.instance().getDataFolder(), "test5clone.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(bw);
		Main.instance().getMapRegistry().loadConfig(config);
		
		Main.instance().getMapRegistry().printMaps();
		
		return true;
	}
}
