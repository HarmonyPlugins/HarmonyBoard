package me.oreoezi.placeholders;

import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyPlaceholder;

public class PosX extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "posx";
	}
	@Override
	public String getValue(Player player) {
		return String.valueOf(player.getLocation().getBlockX());
	}
}
