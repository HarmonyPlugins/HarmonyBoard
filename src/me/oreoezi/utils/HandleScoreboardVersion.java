package me.oreoezi.utils;

import org.bukkit.entity.Player;

import me.oreoezi.utils.boardversions.Scoreboard_1_12;
import me.oreoezi.utils.boardversions.Scoreboard_1_13;
import me.oreoezi.utils.boardversions.Scoreboard_1_14;
import me.oreoezi.utils.boardversions.Scoreboard_1_15;
import me.oreoezi.utils.boardversions.Scoreboard_1_16;
import me.oreoezi.utils.boardversions.Scoreboard_1_16_2;
import me.oreoezi.utils.boardversions.Scoreboard_1_16_4;
import me.oreoezi.utils.boardversions.Scoreboard_1_8;

public class HandleScoreboardVersion {
	public static HarmonyScoreboard handleScoreboardVersion(String version, String title, Player player) {	
		if (version.contains("1.12")) 
			return new Scoreboard_1_12(title, player);
		if (version.contains("1.13"))
			return new Scoreboard_1_13(title, player);
		if (version.contains("1.14"))
			return new Scoreboard_1_14(title, player);
		if (version.contains("1.15"))
			return new Scoreboard_1_15(title, player);
		if (version.contains("1.16.5") || version.contains("1.16.4") || version.contains("1.16.3"))
			return new Scoreboard_1_16_4(title, player);
		if (version.contains("1.16.2"))
			return new Scoreboard_1_16_2(title, player);
		if (version.contains("1.16"))
			return new Scoreboard_1_16(title, player);
		return new Scoreboard_1_8(title, player);
	}
}
