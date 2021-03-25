package me.scrouthtv.commands;

import me.scrouthtv.game.BedwarsGame;
import me.scrouthtv.game.BedwarsMap;
import me.scrouthtv.game.GameRegistry;
import me.scrouthtv.game.MapRegistry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BedwarsCommands {
	
	public static boolean bwStart(final CommandSender sender, final Command cmd, final String[] args) {
		if (args.length != 2) {
			sender.sendMessage(ChatColor.RED + "Expected two parameters.");
			return false;
		}
		
		BedwarsMap map = MapRegistry.getMap(args[0]);
		BedwarsGame game = map.createGame(args[1]);
		
		return true;
	}
	
	public static boolean bwJoin(final CommandSender sender, final Command cmd, final String[] args) {
		if (args.length == 1) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "This command can only be used from a player.");
				return false;
			}
			
			Player p = (Player) sender;
			
			final BedwarsGame game = GameRegistry.getGame(args[0]);
			if (game == null) {
				sender.sendMessage(ChatColor.RED + "Unknown game " + args[0]);
				return false;
			}
			
			return game.playerJoin(p);
			
		} else {
			sender.sendMessage(ChatColor.RED + "Expected one parameter.");
			return false;
		}
	}
}
