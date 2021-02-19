package me.oreoezi.command;

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
	public void onExec(Player player, String[] args) {
		if (main.eventmain.playerboard.get(player) != null) {
			main.eventmain.playerboard.get(player).destroy();
			main.eventmain.playerboard.remove(player);
			main.eventmain.boardtype.remove(player.getName());
		}
		else {
			main.eventmain.addPlayer(player);
		}
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
