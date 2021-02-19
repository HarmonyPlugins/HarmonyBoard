package me.oreoezi.harmonyboard;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.clip.placeholderapi.PlaceholderAPI;
import me.oreoezi.placeholders.*;
import me.oreoezi.placeholders.symbols.*;
import me.oreoezi.utils.HarmonyAnimation;
import me.oreoezi.utils.HarmonyPlaceholder;
import me.oreoezi.utils.HarmonyScoreboard;

public class ThreadMain extends BukkitRunnable {
	private HarmonyBoard main;
	private ArrayList<HarmonyAnimation> anims;
	public ArrayList<HarmonyPlaceholder> placeholders;
	public ThreadMain(HarmonyBoard main) {
		this.main = main;
		this.anims = new ArrayList<HarmonyAnimation>();
		this.placeholders = new ArrayList<HarmonyPlaceholder>();
		createAnims();
		createPlaceholders();
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
		for (int i=0;i<config.getList("lines").size();i++) {
			board.setLine(config.getList("lines").size()-i, parseLine((String) config.getList("lines").get(i), player));
		}
	}
	private void createPlaceholders() {
		placeholders.add(new OnlinePlayers());
		placeholders.add(new PlayerName());
		placeholders.add(new Health());
		placeholders.add(new Ping());
		placeholders.add(new HealthHearts());
		placeholders.add(new Heart());
		placeholders.add(new King());
		placeholders.add(new Omega());
		placeholders.add(new Queen());
		placeholders.add(new Skull());
		placeholders.add(new Star());
		placeholders.add(new StarHollow());
		placeholders.add(new Telephone());
		placeholders.add(new PosX());
		placeholders.add(new PosY());
		placeholders.add(new PosZ());
		placeholders.add(new PosXOW());
		placeholders.add(new PosZOW());
	}
	private void createAnims() {
		for (Object key : main.configs.animations.keySet()) {
			FileConfiguration an_config = main.configs.getAnimation((String) key);
			anims.add(new HarmonyAnimation(an_config, (String) key));
		}
	}
	private void updateAnimations() {
		for (int i=0;i<anims.size();i++) {
			for (int j=0;j<main.configs.getConfig("config").getInt("scoreboard_update_rate");j++)
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
		for (int i=0;i<anims.size();i++) {
			if (anims.get(i) !=null)
				line = line.replaceAll("a%" + anims.get(i).getName() + "%a", anims.get(i).getCurrentFrame());
		}
		for (int i=0;i<placeholders.size();i++) {
			line = line.replaceAll("%"+placeholders.get(i).getName()+"%", placeholders.get(i).getValue(player));
		}
		if (main.papi) line = PlaceholderAPI.setPlaceholders(player, line);
		line = ChatColor.translateAlternateColorCodes('&', line);
		return line;
	}
}
