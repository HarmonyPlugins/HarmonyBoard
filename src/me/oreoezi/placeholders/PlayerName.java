package me.oreoezi.placeholders;

import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyPlaceholder;

public class PlayerName extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "player";
	}
	@Override
	public String getValue(Player player) {
		return player.getName();
	}
}
