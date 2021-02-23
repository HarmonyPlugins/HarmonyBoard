package me.oreoezi.placeholders;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyPlaceholder;

public class OnlinePlayers extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "online";
	}
	@Override
	public String getValue(Player player) {
		return String.valueOf(Bukkit.getOnlinePlayers().size());
	}

}
