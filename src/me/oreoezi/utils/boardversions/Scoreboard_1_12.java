package me.oreoezi.utils.boardversions;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyScoreboard;
import net.minecraft.server.v1_12_R1.IScoreboardCriteria;
import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import net.minecraft.server.v1_12_R1.Scoreboard;
import net.minecraft.server.v1_12_R1.ScoreboardObjective;
import net.minecraft.server.v1_12_R1.ScoreboardScore;

public class Scoreboard_1_12 extends HarmonyScoreboard{
	private PlayerConnection connection;
	private Scoreboard scoreboard;
	private ScoreboardObjective sb_obj;
	private ArrayList<ScoreboardScore> scores;
	public Scoreboard_1_12(String name, Player player) {
		super(name, player);
    	scores = new ArrayList<ScoreboardScore>();
    	connection = ((CraftPlayer)player).getHandle().playerConnection;
    	scoreboard = new Scoreboard();
    	sb_obj = scoreboard.registerObjective(player.getName(), IScoreboardCriteria.b);
    	sb_obj.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
    	connection.sendPacket(new PacketPlayOutScoreboardObjective(sb_obj, 0));
    	connection.sendPacket(new PacketPlayOutScoreboardDisplayObjective(1, sb_obj));
	}
	@Override 
	public void setTitle(String title) {
		sb_obj.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
		connection.sendPacket(new PacketPlayOutScoreboardObjective(sb_obj, 2));
	}
	@Override
	public void setLine(int pos, String text) {
		for (int i=0;i<scores.size();i++) {
    		if (scores.get(i).getScore() == pos) {
    			connection.sendPacket(new PacketPlayOutScoreboardScore(scores.get(i).getPlayerName()));
    			scores.remove(i);
    			i--;
    		}
    	}
    	ScoreboardScore sbs = new ScoreboardScore(scoreboard, sb_obj, ChatColor.translateAlternateColorCodes('&',text));
    	sbs.setScore(pos);
    	scores.add(sbs);
    	connection.sendPacket(new PacketPlayOutScoreboardScore(sbs));
	}
	@Override 
	public void destroy() {
		connection.sendPacket(new PacketPlayOutScoreboardObjective(sb_obj, 1));
	}
}
