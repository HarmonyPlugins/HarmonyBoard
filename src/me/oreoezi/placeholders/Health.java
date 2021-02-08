package me.oreoezi.placeholders;

import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyPlaceholder;

public class Health extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "health";
	}
	@Override
	public String getValue(Player player) {
		return String.valueOf(player.getHealth());
	}

}
