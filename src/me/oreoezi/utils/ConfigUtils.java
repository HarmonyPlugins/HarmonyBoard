package me.oreoezi.utils;

import org.bukkit.ChatColor;

import me.oreoezi.datamanagers.Configs;
import me.oreoezi.harmonyboard.HarmonyBoard;

public class ConfigUtils {
	private HarmonyBoard main;
	public ConfigUtils(HarmonyBoard main) {
		this.main = main;
	} 
	public String getMessage(String index) {
		if (main.configs.getConfig("language").getString(index) != null)
			return ChatColor.translateAlternateColorCodes('&', main.configs.getConfig("language").getString(index));
		else return ChatColor.translateAlternateColorCodes('&', main.configs.getConfig("language").getString("messages." + index));
	}
	public static String getMessage(Configs configs, String index) {
		return ChatColor.translateAlternateColorCodes('&', configs.getConfig("language").getString(index));
	}
}
