package me.oreoezi.harmonyboard;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.oreoezi.command.CommandTabComplete;
import me.oreoezi.datamanagers.Configs;
import me.oreoezi.datamanagers.Database;
import me.oreoezi.utils.ConfigUtils;

public class HarmonyBoard extends JavaPlugin {
	public Configs configs;
	public ThreadMain threadmain;
	public EventMain eventmain;
	public CommandMain commandmain;
	public ConfigUtils cfu;
	public Database db;
	public boolean papi = false;
	@Override
	public void onEnable() {
		this.configs = new Configs(this);
		if (configs.getConfig("config").getBoolean("save_scoreboard_preferences")) {
			if (configs.getConfig("config").getBoolean("mysql")) {
				String host = configs.getConfig("config").getString("host");
				String port = configs.getConfig("config").getString("port");
				String user = configs.getConfig("config").getString("username");	
				String pass = configs.getConfig("config").getString("password");
				String db_name = configs.getConfig("config").getString("database");
				db = new Database(host,port,user,pass,db_name);
			}
			else {
				db = new Database(this.getDataFolder() + "/database.sql");
			}
			new BukkitRunnable() {
				@Override
				public void run() {
					db.SetData("CREATE TABLE IF NOT EXISTS toggle_off (uuid VARCHAR(256))");
				}		
			}.runTaskLaterAsynchronously(this, 20);
		}
		cfu = new ConfigUtils(this);
		this.eventmain = new EventMain(this);
		getServer().getPluginManager().registerEvents(eventmain, this);
		threadmain = new ThreadMain(this);
		threadmain.runTaskTimerAsynchronously(this, configs.getConfig("config").getInt("scoreboard_update_rate"), configs.getConfig("config").getInt("scoreboard_update_rate"));
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			papi = true;
			getLogger().info("Detected PlaceHolderAPI.");
		}
		commandmain = new CommandMain(this);
		getCommand("harmonyboard").setExecutor(commandmain);
		getCommand("harmonyboard").setTabCompleter(new CommandTabComplete(this));
	}
}
