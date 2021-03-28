package me.scrouthtv.commands;

import me.scrouthtv.game.BedwarsGame;
import me.scrouthtv.game.BedwarsMap;
import me.scrouthtv.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BedwarsCommands {
	
	public static boolean bwCreate(final CommandSender sender, final Command cmd, final String[] args) {
		if (args.length != 2) {
			sender.sendMessage(ChatColor.RED + "Expected two parameters.");
			return false;
		}
		
		BedwarsMap map = Main.instance().getMapRegistry().getMap(args[0]);
		BedwarsGame game = map.createGame(args[1]);
		
		if (game != null) {
			sender.sendMessage(ChatColor.GREEN + "Success!");
		} else {
			sender.sendMessage(ChatColor.RED + "Error creating the game.");
		}
		
		return true;
	}
	
	public static boolean bwJoin(final CommandSender sender, final Command cmd, final String[] args) {
		if (args.length == 1) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "This command can only be used from a player.");
				return false;
			}
			
			Player p = (Player) sender;
			
			if (Main.instance().getPlayerRegistry().getGame(p) != null) {
				sender.sendMessage(ChatColor.RED + "You are already in a game. Please leave this game first using /bw-leave.");
				return false;
			}
			
			final BedwarsGame game = Main.instance().getGameRegistry().getGame(args[0]);
			if (game == null) {
				sender.sendMessage(ChatColor.RED + "Unknown game " + args[0]);
				return false;
			}
			
			if (game.playerJoin(p)) {
				sender.sendMessage(ChatColor.GREEN + "Success!");
				return true;
			} else {
				sender.sendMessage(ChatColor.RED + "Error joining the game.");
				return false;
			}
			
		} else {
			sender.sendMessage(ChatColor.RED + "Expected one parameter.");
			return false;
		}
	}
	
	public static boolean bwStart(final CommandSender sender, final Command cmd, final String[] args) {
		if (args.length != 1) {
			sender.sendMessage(ChatColor.RED + "Please specify a game.");
			return false;
		}
		
		BedwarsGame game = Main.instance().getGameRegistry().getGame(args[0]);
		if (game == null) {
			sender.sendMessage(ChatColor.RED + "Unknown game " + args[0] + "");
			return false;
		}
		
		if (!game.canStart()) {
			sender.sendMessage(ChatColor.RED + "Game can't start at the moment.");
			return false;
		}
		
		game.startGame();
		return true;
	}
}
