package me.oreoezi.placeholders.symbols;

import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyPlaceholder;

public class Omega extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "omega";
	}
	@Override
	public String getValue(Player player) {
		return "Ω";
	}

}
