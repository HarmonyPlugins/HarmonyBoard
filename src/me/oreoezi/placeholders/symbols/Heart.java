package me.oreoezi.placeholders.symbols;

import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyPlaceholder;

public class Heart extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "heart";
	}
	@Override
	public String getValue(Player player) {
		return "♥";
	}


}
