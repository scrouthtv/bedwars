package me.scrouthtv.commands;

import me.scrouthtv.shop.Ingot;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveIngot {
	public static boolean giveIngot(final CommandSender sender, final Command cmd, final String[] args) {
		if (args.length == 1) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "This command can only be used by a player.");
				return false;
			}
			
			final Player p = (Player) sender;
			
			Ingot i;
			
			switch (args[0].toLowerCase()) {
				case "bronze":
					i = Ingot.INGOT_BRONZE;
					break;
				case "silver":
					i = Ingot.INGOT_SILVER;
					break;
				case "gold":
					i = Ingot.INGOT_GOLD;
					break;
				default:
					sender.sendMessage(String.format("%sInvalid resource \"%s\".", ChatColor.RED, args[0].toLowerCase()));
					return false;
			}
			
			p.getInventory().addItem(i.asStack(64));
			return true;
			
		} else {
			sender.sendMessage(ChatColor.RED + "Please specify the resource type.");
			return false;
		}
	}
}
