package me.oreoezi.placeholders;

import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyPlaceholder;

public class PosXOW extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "posxow";
	}
	@Override
	public String getValue(Player player) {
		if (player.getWorld().getEnvironment().name().equals("NETHER")) {
			return String.valueOf(Math.floor(player.getLocation().getX() * 8));
		}
		return String.valueOf(Math.floor(player.getLocation().getX()*10)/10);
	}
}
