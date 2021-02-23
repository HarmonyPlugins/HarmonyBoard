package me.oreoezi.placeholders;

import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyPlaceholder;

public class HealthHearts extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "health_hearts";
	}
	@Override
	public String getValue(Player player) {
		String out = "";
		for (int i=0;i<player.getHealth()/2;i++) {
			out += "❤";
		}
		return out;
	}
}
