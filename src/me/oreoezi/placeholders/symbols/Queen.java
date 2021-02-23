package me.oreoezi.placeholders.symbols;

import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyPlaceholder;

public class Queen  extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "queen";
	}
	@Override
	public String getValue(Player player) {
		return "♕";
	}

}
