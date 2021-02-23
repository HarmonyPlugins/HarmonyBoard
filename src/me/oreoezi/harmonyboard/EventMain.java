package me.oreoezi.harmonyboard;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.oreoezi.utils.HandleScoreboardVersion;
import me.oreoezi.utils.HarmonyScoreboard;


public class EventMain implements Listener {
	public HashMap<Player, HarmonyScoreboard> playerboard;
	public HashMap<String, String> boardtype;
	private HarmonyBoard main;
	public EventMain(HarmonyBoard main) { 
		this.main = main;
		this.playerboard = new HashMap<Player, HarmonyScoreboard>();
		this.boardtype = new HashMap<String, String>();
	}
	public void addPlayer(Player player) {
		
		new BukkitRunnable() {
			@Override
			public void run() {
				boolean setsb = true;
				if (main.db != null) {
					if (main.db.GetData("SELECT * FROM toggle_off WHERE uuid='" + player.getUniqueId().toString() + "'").size() > 0) {
						setsb = false;
					}
				}
				if (setsb) {
					boolean def = true;
					if (main.configs.getConfig("config").getBoolean("enable_restricted_scoreboards")) {
						for (Object res : main.configs.getConfig("config").getConfigurationSection("restricted_scoreboards").getKeys(false).toArray()) {
							if (player.hasPermission(main.configs.getConfig("config").getString("restricted_scoreboards."+res))) {
								boardtype.put(player.getName(), (String) res);
								playerboard.put(player, HandleScoreboardVersion.handleScoreboardVersion(main.getServer().getVersion(),main.configs.getScoreboard((String) res).getString("title"), player));
								def = false;
							}
						}
					}
					if (def && main.configs.getConfig("config").getBoolean("enable_default_scoreboard")) {
						boardtype.put(player.getName(), main.configs.getConfig("config").getString("default_scoreboard"));
						playerboard.put(player, HandleScoreboardVersion.handleScoreboardVersion(main.getServer().getVersion(),main.configs.getScoreboard(main.configs.getConfig("config").getString("default_scoreboard")).getString("title"), player));
					}
					else if (def && main.configs.getConfig("config").getBoolean("enable_perworld_scoreboards")) {
						for (Object res : main.configs.getConfig("config").getConfigurationSection("world_scoreboards").getKeys(false).toArray()) {
							if (player.getWorld().getName().equals(main.configs.getConfig("config").getString("world_scoreboards." + res))) {
								boardtype.put(player.getName(), (String) res);
								playerboard.put(player, HandleScoreboardVersion.handleScoreboardVersion(main.getServer().getVersion(),main.configs.getScoreboard((String) res).getString("title"), player));
							}
						}
					}
				}
			}
		}.runTaskLater(main, 2L);
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		addPlayer(event.getPlayer());
	}
	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		playerboard.remove(event.getPlayer());
		boardtype.remove(event.getPlayer().getName());
	}
	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent event) {
		if (main.configs.getConfig("config").getBoolean("enable_perworld_scoreboards")) {
			for (Object res : main.configs.getConfig("config").getConfigurationSection("world_scoreboards").getKeys(false).toArray()) {
				if (event.getPlayer().getWorld().getName().equals(main.configs.getConfig("config").getString("world_scoreboards." + res))) {
					if (playerboard.get(event.getPlayer()) != null) playerboard.get(event.getPlayer()).destroy();
					playerboard.remove(event.getPlayer());
					boardtype.remove(event.getPlayer().getName());
					boardtype.put(event.getPlayer().getName(), (String) res);
					playerboard.put(event.getPlayer(), HandleScoreboardVersion.handleScoreboardVersion(main.getServer().getVersion(),main.configs.getScoreboard((String) res).getString("title"), event.getPlayer()));
				}
			}
		}
	}
}
