package me.oreoezi.harmonyboard;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.clip.placeholderapi.PlaceholderAPI;
import me.oreoezi.utils.HarmonyAnimation;
import me.oreoezi.utils.HarmonyScoreboard;

public class ThreadMain extends BukkitRunnable {
	private HarmonyBoard main;
	private ArrayList<HarmonyAnimation> anims;
	
	public ThreadMain(HarmonyBoard main) {
		this.main = main;
		this.anims = new ArrayList<HarmonyAnimation>();
		createAnims();
	}
	@Override
	public void run() {
		updateAnimations();
		for (Player player : main.eventmain.playerboard.keySet()) {
			updateScoreboard(player);
		}		
	}
	private void updateScoreboard(Player player) {
		String boardtype = main.eventmain.boardtype.get(player.getName());
		HarmonyScoreboard board = main.eventmain.playerboard.get(player);
		FileConfiguration config = main.configs.getScoreboard(boardtype);
		for (int i=0;i<config.getInt("size");i++) {
			board.setLine(config.getInt("size")-i, parseLine(config.getString("lines."+i), player));
		}
	}
	private void createAnims() {
		for (Object key : main.configs.animations.keySet()) {
			FileConfiguration an_config = main.configs.getAnimation((String) key);
			anims.add(new HarmonyAnimation(an_config, (String) key));
		}
	}
	private void updateAnimations() {
		for (int i=0;i<anims.size();i++) {
			anims.get(i).updateAnimation();
		}
	}
	public void destroy() {
		for (Object key : main.eventmain.playerboard.keySet()) {
			main.eventmain.playerboard.get(key).destroy();
		}
	}
	private String parseLine(String input, Player player) {
		String line = input;
		line = line.replaceAll("%player%", player.getName());
		line = line.replaceAll("%online%", String.valueOf(Bukkit.getOnlinePlayers().size()));
		for (int i=0;i<anims.size();i++) {
			if (anims.get(i) !=null)
				line = line.replaceAll("a%" + anims.get(i).getName() + "%a", anims.get(i).getCurrentFrame());
		}
		if (main.papi) line = PlaceholderAPI.setPlaceholders(player, line);
		line = ChatColor.translateAlternateColorCodes('&', line);
		return line;
	}
}
