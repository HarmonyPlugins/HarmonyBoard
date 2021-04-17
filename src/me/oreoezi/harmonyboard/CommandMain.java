package me.oreoezi.harmonyboard;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.oreoezi.command.CommandHelp;

public class CommandMain implements CommandExecutor {
	private HarmonyBoard main;
	public CommandHelp cmh;
	public CommandMain(HarmonyBoard main) {
		this.main = main;
		cmh = new CommandHelp(main);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0 || args[0] == "help")
			cmh.sendHelpList(sender);
		else {
			boolean foundcommand = false;
			for (int i=0;i<cmh.commands.size();i++) {
				if (args[0].equals(cmh.commands.get(i).getName())) {
					if (sender.hasPermission(cmh.commands.get(i).getPermission())) {
						cmh.commands.get(i).onExec(sender, args);
					}
					else sender.sendMessage(main.cfu.getMessage("prefix") + " " + main.cfu.getMessage("messages.error.no_permission"));
					foundcommand = true;
				}
			}
			if (!foundcommand) sender.sendMessage(main.cfu.getMessage("prefix") + " " + main.cfu.getMessage("messages.error.invalid_command"));
		}
		return true;
	}
}
