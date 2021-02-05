package me.oreoezi.utils.boardversions;


import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import me.oreoezi.utils.HarmonyScoreboard;
import net.minecraft.server.v1_16_R3.IScoreboardCriteria;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import net.minecraft.server.v1_16_R3.Scoreboard;
import net.minecraft.server.v1_16_R3.ScoreboardObjective;
import net.minecraft.server.v1_16_R3.ScoreboardScore;
import net.minecraft.server.v1_16_R3.ScoreboardServer.Action;
import net.minecraft.server.v1_16_R3.ScoreboardTeam;
import net.minecraft.server.v1_16_R3.ChatMessage;

public class Scoreboard_1_16_4 extends HarmonyScoreboard{
	private PlayerConnection connection;
	private Scoreboard scoreboard;
	private ScoreboardObjective sb_obj;
	private HashMap<Integer, String> lines;
	private ArrayList<ScoreboardTeam> teams;
	public Scoreboard_1_16_4(String name, Player player) {
		super(name, player);
    	lines = new HashMap<Integer, String>();
    	teams = new ArrayList<ScoreboardTeam>();
    	connection = ((CraftPlayer)player).getHandle().playerConnection;
    	scoreboard = new Scoreboard();
    	sb_obj = scoreboard.registerObjective(player.getName(), IScoreboardCriteria.DUMMY,new ChatMessage(player.getName()), IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER);
    	sb_obj.setDisplayName(new ChatMessage(ChatColor.translateAlternateColorCodes('&',name)));
    	connection.sendPacket(new PacketPlayOutScoreboardObjective(sb_obj, 0));
    	connection.sendPacket(new PacketPlayOutScoreboardDisplayObjective(1, sb_obj));
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
				String txt = "§b";
				for (int i=0;i<pos;i++) {
					txt += "§a";
				}
				ScoreboardTeam team = new ScoreboardTeam(scoreboard, "line"+pos);
				team.setDisplayName(new ChatMessage("line"+pos));
				team.setPrefix(new ChatMessage(prefix));
				team.setSuffix(new ChatMessage(suffix));
				team.getPlayerNameSet().add(txt);
				teams.add(team);
		    	ScoreboardScore sbs = new ScoreboardScore(scoreboard, sb_obj, txt);
		    	sbs.setScore(pos);
		    	connection.sendPacket(new PacketPlayOutScoreboardTeam(team, 0));
		    	connection.sendPacket(new PacketPlayOutScoreboardScore(Action.CHANGE, sb_obj.getName(), txt, pos));
			}
			else {
				for (int i=0;i<teams.size();i++) {
					if (teams.get(i).getDisplayName().getString().equals("line"+pos)) {
						teams.get(i).setPrefix(new ChatMessage(prefix));
						teams.get(i).setSuffix(new ChatMessage(suffix));
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
