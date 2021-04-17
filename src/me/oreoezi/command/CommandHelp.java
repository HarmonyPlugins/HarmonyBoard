package me.oreoezi.command;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import me.oreoezi.harmonyboard.HarmonyBoard;
import me.oreoezi.utils.HarmonyCommand;

public class CommandHelp {
	public ArrayList<HarmonyCommand> commands;
	private HarmonyBoard main;
	public CommandHelp(HarmonyBoard main) {
		commands = new ArrayList<HarmonyCommand>();
		this.main = main;
		genCommandList(main);
	}
	private void genCommandList(HarmonyBoard main) {
		commands.add(new CommandReload(main));
		commands.add(new CommandScoreboard(main));
		commands.add(new CommandToggle(main));
	}
	public void sendHelpList(CommandSender player) {
		if (player.hasPermission("harmonyboard.help")) {
			player.sendMessage("§7===========================================");
			player.sendMessage(" ");
			for (int i=0;i<commands.size();i++) {
				if (player.hasPermission(commands.get(i).getPermission()))
					player.sendMessage(" §b/hboard " + commands.get(i).getName() + " " + commands.get(i).getArgs() + " §7| §f" + commands.get(i).getDescription());
			}
			player.sendMessage(" ");
			player.sendMessage("§7===========================================");
		}
		else player.sendMessage(main.cfu.getMessage("prefix") + " " + main.cfu.getMessage("error.no_permission"));
	}
}
