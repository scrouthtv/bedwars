package me.scrouthtv.main;

import me.scrouthtv.commands.GiveIngot;
import me.scrouthtv.shop.Shop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements CommandExecutor {

	@Override
	public void onEnable() {
		getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "Starting Bedwars 1");
		
		Bukkit.getPluginManager().registerEvents(new Shop(), this);
	}
	
	@Override
	public void onDisable() {
		getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "Stopping Bedwars 1");
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		switch (label) {
			case "give-ingot":
				return GiveIngot.giveIngot(sender, command, args);
		}
		return false;
	}
}
