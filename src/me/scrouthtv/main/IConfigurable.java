package me.scrouthtv.main;

import org.bukkit.configuration.file.FileConfiguration;

public interface IConfigurable {
	public void loadConfig(FileConfiguration config);
	public void storeConfig(FileConfiguration config);
}
