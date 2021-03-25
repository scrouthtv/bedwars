package me.scrouthtv.commands;

import me.scrouthtv.main.Main;
import me.scrouthtv.maps.IMap;
import me.scrouthtv.maps.DimAdapter;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class MapCommands {
	public static boolean buildBedwars(final CommandSender sender, final Command cmd, final String[] args) {
		IMap map;
		Player p;
		if (args.length == 1) {
			map = Main.instance().getMapManager().getByName(args[0]);
			
			if (map == null) {
				System.out.println("Map doesn't exist, creating it.");
				map = Main.instance().getMapManager().createNewMap(args[0]);
			}
			
			if (sender instanceof Player) {
				p = (Player) sender;
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
		
		if (map != null && p != null) {
			p.sendMessage("Wooosh...");
			map.playerJoin(p);
			p.setGameMode(GameMode.CREATIVE);
			return true;
	 	}
		
		return false;
	}
	
	public static boolean cloneMap(final CommandSender sender, final Command cmd, final String[] args) {
		if (args.length != 2) {
			sender.sendMessage(ChatColor.RED + "Please specify two map names.");
			return false;
		} else {
			IMap map = Main.instance().getMapManager().getByName(args[0]);
			if (map == null) {
				sender.sendMessage(ChatColor.RED + "Invalid map " + args[0]);
				return false;
			}
			
			IMap clone = map.cloneMap(args[1]);
			if (clone == null) {
				sender.sendMessage(ChatColor.RED + "Cloning failed");
			} else {
				sender.sendMessage(ChatColor.GREEN + "Success!");
			}
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
}
