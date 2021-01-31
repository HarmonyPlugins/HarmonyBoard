package me.oreoezi.harmonyboard;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.oreoezi.command.CommandTabComplete;
import me.oreoezi.datamanagers.Configs;
import me.oreoezi.utils.ConfigUtils;

public class HarmonyBoard extends JavaPlugin {
	public Configs configs;
	public ThreadMain threadmain;
	public EventMain eventmain;
	public CommandMain commandmain;
	public ConfigUtils cfu;
	public boolean papi = false;
	@Override
	public void onEnable() {
		this.configs = new Configs(this);
		cfu = new ConfigUtils(this);
		this.eventmain = new EventMain(this);
		getServer().getPluginManager().registerEvents(eventmain, this);
		threadmain = new ThreadMain(this);
		threadmain.runTaskTimerAsynchronously(this, 1L, 1L);
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			papi = true;
			getLogger().info("Detected PlaceHolderAPI.");
		}
		commandmain = new CommandMain(this);
		getCommand("harmonyboard").setExecutor(commandmain);
		getCommand("harmonyboard").setTabCompleter(new CommandTabComplete(this));
	}
}
