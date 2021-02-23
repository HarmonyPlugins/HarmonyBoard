package me.oreoezi.placeholders.symbols;

import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyPlaceholder;

public class Skull extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "skull";
	}
	@Override
	public String getValue(Player player) {
		return "☠";
	}
}
