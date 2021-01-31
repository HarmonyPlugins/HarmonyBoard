package me.oreoezi.HarmonyBoardAPI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.oreoezi.harmonyboard.HarmonyBoard;
import me.oreoezi.utils.HandleScoreboardVersion;
import me.oreoezi.utils.HarmonyPlaceholder;

public class HarmonyBoardAPI {
	public static void registerPlaceholder(HarmonyPlaceholder placeholder) {
		HarmonyBoard main = (HarmonyBoard) Bukkit.getServer().getPluginManager().getPlugin("HarmonyScoreboard");
		main.threadmain.placeholders.add(placeholder);
	}
	public static void unregisterPlaceholder(HarmonyPlaceholder placeholder) {
		HarmonyBoard main = (HarmonyBoard) Bukkit.getServer().getPluginManager().getPlugin("HarmonyScoreboard");
		main.threadmain.placeholders.remove(placeholder);
	}
	public static void unregisterPlaceholder(String name) {
		HarmonyBoard main = (HarmonyBoard) Bukkit.getServer().getPluginManager().getPlugin("HarmonyScoreboard");
		for (int i=0;i<main.threadmain.placeholders.size();i++) {
			if(main.threadmain.placeholders.get(i).getName().equals(name)) {
				main.threadmain.placeholders.remove(i);
				break;
			}
		}
	}
	public static void setScoreboard(Player player, String scoreboard) throws HarmonyBoardException {
		HarmonyBoard main = (HarmonyBoard) Bukkit.getServer().getPluginManager().getPlugin("HarmonyScoreboard");
		if (player == null) {
			throw new HarmonyBoardException("Player parameter cannot be null.");
		}
		else if (main.configs.getScoreboard(scoreboard) == null) {
			throw new HarmonyBoardException("Scoreboard cannot be found.");
		}
		else {
			if (main.eventmain.playerboard.get(player) != null) main.eventmain.playerboard.get(player).destroy();
			main.eventmain.playerboard.remove(player);
			main.eventmain.boardtype.remove(player.getName());
			main.eventmain.boardtype.put(player.getName(), scoreboard);
			main.eventmain.playerboard.put(player, HandleScoreboardVersion.handleScoreboardVersion(main.getServer().getVersion(),main.configs.getScoreboard(scoreboard).getString("title"), player));
		}
	}
}
