package me.oreoezi.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import me.oreoezi.datamanagers.Configs;
import me.oreoezi.harmonyboard.EventMain;
import me.oreoezi.harmonyboard.HarmonyBoard;
import me.oreoezi.harmonyboard.ThreadMain;
import me.oreoezi.utils.HarmonyCommand;

public class CommandReload extends HarmonyCommand {
	private HarmonyBoard main;
	public CommandReload(HarmonyBoard main) {
		super(main);
		this.main = main;
	}
	@Override
	public void onExec(Player player, String[] args) {
		main.configs = new Configs(main);
		main.threadmain.destroy();
		HandlerList.unregisterAll(main.eventmain);
		main.eventmain = new EventMain(main);
		Bukkit.getServer().getPluginManager().registerEvents(main.eventmain, main);
		for (Player pl : Bukkit.getOnlinePlayers()) {
			main.eventmain.addPlayer(pl);
		}
		main.threadmain.cancel();
		main.threadmain = new ThreadMain(main);
		main.threadmain.runTaskTimerAsynchronously(main, main.configs.getConfig("config").getInt("scoreboard_update_rate"), main.configs.getConfig("config").getInt("scoreboard_update_rate"));
		player.sendMessage(main.cfu.getMessage("prefix") + " " + main.cfu.getMessage("admin.reloaded"));
	}
	@Override
	public String getName() {
		return "reload";
	}
	@Override
	public String getPermission() {
		return "harmonyboard.reload";
	}
	@Override
	public String getDescription() {
		return "Reloads the Harmonyboard configs";
	}
}
