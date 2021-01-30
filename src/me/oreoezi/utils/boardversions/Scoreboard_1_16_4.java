package me.oreoezi.utils.boardversions;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyScoreboard;
import net.minecraft.server.v1_16_R3.IScoreboardCriteria;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import net.minecraft.server.v1_16_R3.Scoreboard;
import net.minecraft.server.v1_16_R3.ScoreboardObjective;
import net.minecraft.server.v1_16_R3.ScoreboardScore;
import net.minecraft.server.v1_16_R3.ScoreboardServer.Action;
import net.minecraft.server.v1_16_R3.ChatMessage;

public class Scoreboard_1_16_4 extends HarmonyScoreboard{
	private PlayerConnection connection;
	private Scoreboard scoreboard;
	private ScoreboardObjective sb_obj;
	private ArrayList<ScoreboardScore> scores;
	public Scoreboard_1_16_4(String name, Player player) {
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
