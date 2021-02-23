package me.oreoezi.placeholders.symbols;

import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyPlaceholder;

public class StarHollow extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "star_hollow";
	}
	@Override
	public String getValue(Player player) {
		return "☆";
	}

}
