package me.oreoezi.placeholders.symbols;

import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyPlaceholder;

public class King extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "king";
	}
	@Override
	public String getValue(Player player) {
		return "♔";
	}

}
