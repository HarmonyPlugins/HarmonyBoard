package me.oreoezi.command;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.oreoezi.harmonyboard.HarmonyBoard;
import me.oreoezi.utils.HandleScoreboardVersion;
import me.oreoezi.utils.HarmonyCommand;

public class CommandScoreboard extends HarmonyCommand {
	private HarmonyBoard main;
	public CommandScoreboard(HarmonyBoard main) {
		super(main);
		this.main = main;
	}
	@Override
	public void onExec(Player player, String[] args) {
		switch(args[1]) {
		case "remove": {
			if (args.length < 3) {
				player.sendMessage(main.cfu.getMessage("prefix") + " " + main.cfu.getMessage("messages.error.invalid_arguments"));
			}
			else {
				boolean found = false;
				for (Player target : Bukkit.getOnlinePlayers()) {
					if (target.getName().equals(args[2])) {
						if (main.eventmain.playerboard.get(target) != null) main.eventmain.playerboard.get(target).destroy();
						main.eventmain.playerboard.remove(target);
						main.eventmain.boardtype.remove(target.getName());
						found = true;
						break;
					}
				}
				if (!found) player.sendMessage(main.cfu.getMessage("prefix") + " " + main.cfu.getMessage("messages.error.invalid_player"));
			}
			break;
		}
		case "set": {
			if (args.length < 4 || main.configs.getScoreboard(args[3]) == null) {
				player.sendMessage(main.cfu.getMessage("prefix") + " " + main.cfu.getMessage("messages.error.invalid_arguments"));
			}
			else {
				boolean found = false;
				for (Player target : Bukkit.getOnlinePlayers()) {
					if (target.getName().equals(args[2])) {
						if (main.eventmain.playerboard.get(target) != null) main.eventmain.playerboard.get(target).destroy();
						main.eventmain.playerboard.remove(target);
						main.eventmain.boardtype.remove(target.getName());
						main.eventmain.boardtype.put(player.getName(), args[3]);
						main.eventmain.playerboard.put(player, HandleScoreboardVersion.handleScoreboardVersion(main.getServer().getVersion(),main.configs.getScoreboard(args[3]).getString("title"), player));
						found = true;
						break;
					}
				}
				if (!found) player.sendMessage(main.cfu.getMessage("prefix") + " " + main.cfu.getMessage("messages.error.invalid_player"));
			}
			break;
		}
		default: {
			player.sendMessage(main.cfu.getMessage("prefix") + " " + main.cfu.getMessage("messages.error.invalid_arguments"));
		}
		}
	}
	@Override
	public String getName() {
		return "scoreboard";
	}
	@Override
	public String getPermission() {
		return "harmonyboard.scoreboard";
	}
	@Override
	public String getDescription() {
		return "Change the scoreboard of another player";
	}
	@Override
	public String getArgs() {
		return "(set/remove) (player) (scoreboard)";
	}
	@Override
	public HashMap<Integer, ArrayList<String>> getSuggestions() {
		HashMap<Integer, ArrayList<String>> params = new HashMap<Integer, ArrayList<String>>();
		ArrayList<String> parargone = new ArrayList<String>();
		parargone.add("set");
		parargone.add("remove");
		params.put(1, parargone);
		ArrayList<String> parargtwo = new ArrayList<String>();
		for (String name : main.configs.scoreboards.keySet()) {
			parargtwo.add(name);
		}
		params.put(3, parargtwo);
		ArrayList<String> plr = new ArrayList<String>();
		for (Player ply : Bukkit.getOnlinePlayers()) {
			plr.add(ply.getName());
		}
		params.put(2, plr);
		return params;
	}
}
