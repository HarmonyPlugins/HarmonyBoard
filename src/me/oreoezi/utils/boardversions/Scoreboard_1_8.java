package me.oreoezi.utils.boardversions;


import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import me.oreoezi.utils.HarmonyScoreboard;
import net.minecraft.server.v1_8_R3.IScoreboardCriteria;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardObjective;
import net.minecraft.server.v1_8_R3.ScoreboardScore;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;

public class Scoreboard_1_8 extends HarmonyScoreboard{
	private PlayerConnection connection;
	private Scoreboard scoreboard;
	private ScoreboardObjective sb_obj;
	private HashMap<Integer, String> lines;
	private ArrayList<ScoreboardTeam> teams;
	public Scoreboard_1_8(String name, Player player) {
		super(name, player);
    	lines = new HashMap<Integer, String>();
    	teams = new ArrayList<ScoreboardTeam>();
    	connection = ((CraftPlayer)player).getHandle().playerConnection;
    	scoreboard = new Scoreboard();
    	sb_obj = scoreboard.registerObjective(player.getName(), IScoreboardCriteria.b);
    	sb_obj.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
    	connection.sendPacket(new PacketPlayOutScoreboardObjective(sb_obj, 0));
    	connection.sendPacket(new PacketPlayOutScoreboardDisplayObjective(1, sb_obj));
	}
	@Override 
	public void setTitle(String title) {
		sb_obj.setDisplayName(ChatColor.translateAlternateColorCodes('&',title));
		connection.sendPacket(new PacketPlayOutScoreboardObjective(sb_obj, 2));
	}
	@Override
	public void setLine(int pos, String text) {
		if (!lines.getOrDefault(pos, "").equals(text)) {
			String prefix = text.substring(0, text.length()/2);
			String suffix = "";
			if (prefix.endsWith("§")) {
				prefix = prefix.substring(0, prefix.length()-1);
				suffix += "§";
				suffix += text.substring(text.length()/2, text.length());
			}
			else {
				suffix += ChatColor.getLastColors(prefix);
				suffix += text.substring(text.length()/2, text.length());
			}
			if (lines.get(pos) == null) {
				String txt = "§r";
				for (int i=0;i<pos;i++) {
					txt += "§r";
				}
				ScoreboardTeam team = new ScoreboardTeam(scoreboard, "line"+pos);
				team.setDisplayName("line"+pos);
				team.setPrefix(prefix);
				team.setSuffix(suffix);
				team.getPlayerNameSet().add(txt);
				teams.add(team);
		    	ScoreboardScore sbs = new ScoreboardScore(scoreboard, sb_obj, txt);
		    	sbs.setScore(pos);
		    	connection.sendPacket(new PacketPlayOutScoreboardTeam(team, 0));
		    	connection.sendPacket(new PacketPlayOutScoreboardScore(sbs));
			}
			else {
				for (int i=0;i<teams.size();i++) {
					if (teams.get(i).getDisplayName().equals("line"+pos)) {
						teams.get(i).setPrefix(prefix);
						teams.get(i).setSuffix(suffix);
						connection.sendPacket(new PacketPlayOutScoreboardTeam(teams.get(i), 2));
						break;
					}
				}
			}
	    	lines.put(pos, text);
		}
	}
	@Override 
	public void destroy() {
		for (int i=0;i<teams.size();i++) {
			connection.sendPacket(new PacketPlayOutScoreboardTeam(teams.get(i), 1));
		}
		connection.sendPacket(new PacketPlayOutScoreboardObjective(sb_obj, 1));
	}
}
