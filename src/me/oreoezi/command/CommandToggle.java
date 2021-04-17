package me.oreoezi.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.oreoezi.harmonyboard.HarmonyBoard;
import me.oreoezi.utils.HarmonyCommand;

public class CommandToggle extends HarmonyCommand {
	private HarmonyBoard main;
	public CommandToggle(HarmonyBoard main) {
		super(main);
		this.main = main;
	}
	@Override
	public void onExec(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (main.eventmain.playerboard.get(player) != null) {
				main.eventmain.playerboard.get(player).destroy();
				main.eventmain.playerboard.remove(player);
				main.eventmain.boardtype.remove(player.getName());
				if (main.configs.getConfig("language").getString("messages.admin.toggle_off") != null) player.sendMessage(main.cfu.getMessage("prefix") + " " + main.cfu.getMessage("admin.toggle_off"));
				if (main.db != null) main.db.SetData("INSERT INTO toggle_off (uuid) VALUES ('" + player.getUniqueId().toString() + "')");
			}
			else {
				main.eventmain.addPlayer(player);
				if (main.configs.getConfig("language").getString("messages.admin.toggle_on") != null) player.sendMessage(main.cfu.getMessage("prefix") + " " + main.cfu.getMessage("admin.toggle_on"));
				if (main.db != null) main.db.SetData("DELETE FROM toggle_off WHERE UUID='" + player.getUniqueId().toString() + "'");
			}
		}
		else sender.sendMessage(main.cfu.getMessage("prefix") + " " + main.cfu.getMessage("messages.error.nonplayer"));
	}
	@Override
	public String getName() {
		return "toggle";
	}
	@Override
	public String getPermission() {
		return "harmonyboard.toggle";
	}
	@Override
	public String getDescription() {
		return "Toggles your scoreboard";
	}
}
