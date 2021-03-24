package me.scrouthtv.main;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "Starting Bedwars 1");
	}
	
	@Override
	public void onDisable() {
		getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "Stopping Bedwars 1");
	}

}
