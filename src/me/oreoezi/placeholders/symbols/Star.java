package me.oreoezi.placeholders.symbols;

import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyPlaceholder;

public class Star extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "star";
	}
	@Override
	public String getValue(Player player) {
		return "★";
	}
}
