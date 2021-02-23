package me.oreoezi.placeholders.symbols;

import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyPlaceholder;

public class Telephone extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "telephone";
	}
	@Override
	public String getValue(Player player) {
		return "☏";
	}

}
