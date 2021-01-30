package me.oreoezi.utils.boardversions;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyScoreboard;
import net.minecraft.server.v1_16_R2.ChatMessage;
import net.minecraft.server.v1_16_R2.IScoreboardCriteria;
import net.minecraft.server.v1_16_R2.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_16_R2.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_16_R2.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_16_R2.PlayerConnection;
import net.minecraft.server.v1_16_R2.Scoreboard;
import net.minecraft.server.v1_16_R2.ScoreboardObjective;
import net.minecraft.server.v1_16_R2.ScoreboardScore;
import net.minecraft.server.v1_16_R2.ScoreboardServer.Action;

public class Scoreboard_1_16_2 extends HarmonyScoreboard{
	private PlayerConnection connection;
	private Scoreboard scoreboard;
	private ScoreboardObjective sb_obj;
	private ArrayList<ScoreboardScore> scores;
	public Scoreboard_1_16_2(String name, Player player) {
		super(name, player);
    	scores = new ArrayList<ScoreboardScore>();
    	connection = ((CraftPlayer)player).getHandle().playerConnection;
    	scoreboard = new Scoreboard();
    	sb_obj = scoreboard.registerObjective(player.getName(), IScoreboardCriteria.DUMMY,new ChatMessage(player.getName()), IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER);
    	sb_obj.setDisplayName(new ChatMessage(ChatColor.translateAlternateColorCodes('&',name)));
    	connection.sendPacket(new PacketPlayOutScoreboardObjective(sb_obj, 0));
    	connection.sendPacket(new PacketPlayOutScoreboardDisplayObjective(1, sb_obj));
	}
	@Override
	public void setLine(int pos, String text) {
		for (int i=0;i<scores.size();i++) {
    		if (scores.get(i).getScore() == pos) {
    			connection.sendPacket(new PacketPlayOutScoreboardScore(Action.REMOVE, sb_obj.getName(), scores.get(i).getPlayerName(), scores.get(i).getScore()));
    			scores.remove(i);
    			i--;
    		}
    	}
    	ScoreboardScore sbs = new ScoreboardScore(scoreboard, sb_obj, ChatColor.translateAlternateColorCodes('&',text));
    	sbs.setScore(pos);
    	scores.add(sbs);
    	connection.sendPacket(new PacketPlayOutScoreboardScore(Action.CHANGE, sb_obj.getName(), text, pos));
	}
	@Override 
	public void destroy() {
		connection.sendPacket(new PacketPlayOutScoreboardObjective(sb_obj, 1));
	}
}
