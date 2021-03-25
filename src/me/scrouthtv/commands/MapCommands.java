package me.scrouthtv.commands;

import me.scrouthtv.game.BedwarsBuildingItems;
import me.scrouthtv.game.BedwarsMap;
import me.scrouthtv.game.MapRegistry;
import me.scrouthtv.main.BuildProcedure;
import me.scrouthtv.main.Main;
import me.scrouthtv.maps.IMap;
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
			BuildProcedure proc = new BuildProcedure(p, map);
			proc.start();
			return true;
	 	}
		
		return false;
	}
	
	public static boolean listMaps(final CommandSender sender, final Command cmd, final String[] args) {
		List<IMap> maps = Main.instance().getMapManager().listMaps();
		sender.sendMessage(String.format("%d known maps:", maps.size()));
		for (IMap m : maps)
			sender.sendMessage(m.toString());
		return true;
	}
}
