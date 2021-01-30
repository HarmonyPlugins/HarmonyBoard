package me.oreoezi.harmonyboard;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			if (args.length == 0 || args[0] == "help")
				cmh.sendHelpList(player);
			else {
				boolean foundcommand = false;
				for (int i=0;i<cmh.commands.size();i++) {
					if (args[0].equals(cmh.commands.get(i).getName())) {
						if (player.hasPermission(cmh.commands.get(i).getPermission())) {
							cmh.commands.get(i).onExec(player, args);
						}
						else player.sendMessage(main.cfu.getMessage("prefix") + " " + main.cfu.getMessage("messages.error.no_permission"));
						foundcommand = true;
					}
				}
				if (!foundcommand) player.sendMessage(main.cfu.getMessage("prefix") + " " + main.cfu.getMessage("messages.error.invalid_command"));
			}
		}
		else sender.sendMessage(main.cfu.getMessage("prefix") + " " +  main.cfu.getMessage("messages.error.nonplayer"));
		return true;
	}
}
