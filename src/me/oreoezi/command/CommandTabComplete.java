package me.oreoezi.command;


import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.oreoezi.harmonyboard.HarmonyBoard;

public class CommandTabComplete implements TabCompleter {
	private HarmonyBoard main;
	public CommandTabComplete(HarmonyBoard main) {
		this.main = main;
	}
	@Override
	public java.util.List<String> onTabComplete(CommandSender sender, Command command, String commandLable, String[] args){
		java.util.List<String> cmds = new ArrayList<String>();
		for (int i=0;i<main.commandmain.cmh.commands.size();i++) {
			if (args.length == 1) {
				if (sender.hasPermission(main.commandmain.cmh.commands.get(i).getPermission())) {
					cmds.add(main.commandmain.cmh.commands.get(i).getName());
				}
			}
			else if (args.length > 1) {
				if (args[0].equals(main.commandmain.cmh.commands.get(i).getName())) {
					if (main.commandmain.cmh.commands.get(i).getSuggestions().get(args.length-1) != null) {
						ArrayList<String> copy = main.commandmain.cmh.commands.get(i).getSuggestions().get(args.length-1);
						for (int j=0;j<copy.size();j++) {
							cmds.add(copy.get(j));
						}
					}
				}
			}
		}
		return cmds;
	}
}
