package me.oreoezi.placeholders;

import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyPlaceholder;

public class PosZ extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "posz";
	}
	@Override
	public String getValue(Player player) {
		return String.valueOf(player.getLocation().getBlockZ());
	}
}
