package me.oreoezi.utils.boardversions;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyScoreboard;
import net.minecraft.network.chat.ChatMessage;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardObjective;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardScore;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardTeam;
import net.minecraft.server.ScoreboardServer.Action;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardObjective;
import net.minecraft.world.scores.ScoreboardScore;
import net.minecraft.world.scores.ScoreboardTeam;
import net.minecraft.world.scores.criteria.IScoreboardCriteria;
public class Scoreboard_1_17 extends HarmonyScoreboard{
	private PlayerConnection connection;
	private Scoreboard scoreboard;
	private ScoreboardObjective sb_obj;
	private HashMap<Integer, String> lines;
	private ArrayList<ScoreboardTeam> teams;
	public Scoreboard_1_17(String name, Player player) {
		super(name, player);
    	lines = new HashMap<Integer, String>();
    	teams = new ArrayList<ScoreboardTeam>();
    	connection = ((CraftPlayer)player).getHandle().b;
    	scoreboard = new Scoreboard();
    	sb_obj = scoreboard.registerObjective(player.getName(), IScoreboardCriteria.a,new ChatMessage(player.getName()), IScoreboardCriteria.EnumScoreboardHealthDisplay.a);
    	sb_obj.setDisplayName(new ChatMessage(ChatColor.translateAlternateColorCodes('&',name)));
    	connection.sendPacket(new PacketPlayOutScoreboardObjective(sb_obj, 0));
    	connection.sendPacket(new PacketPlayOutScoreboardDisplayObjective(1, sb_obj));
	}
	@Override 
	public void setTitle(String title) {
		sb_obj.setDisplayName(new ChatMessage(ChatColor.translateAlternateColorCodes('&',title)));
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
		    	connection.sendPacket(PacketPlayOutScoreboardTeam.a(team, true));
		    	connection.sendPacket(new PacketPlayOutScoreboardScore(Action.a, sb_obj.getName(), txt, pos));
			}
			else {
				for (int i=0;i<teams.size();i++) {
					if (teams.get(i).getDisplayName().getString().equals("line"+pos)) {
						teams.get(i).setPrefix(new ChatMessage(prefix));
						teams.get(i).setSuffix(new ChatMessage(suffix));
						connection.sendPacket(PacketPlayOutScoreboardTeam.a(teams.get(i), false));
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
			connection.sendPacket(PacketPlayOutScoreboardTeam.a(teams.get(i), false));
		}
		connection.sendPacket(new PacketPlayOutScoreboardObjective(sb_obj, 1));
	}
}
