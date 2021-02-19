package me.oreoezi.placeholders;

import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyPlaceholder;

public class PosZOW extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "poszow";
	}
	@Override
	public String getValue(Player player) {
		if (player.getWorld().getEnvironment().name().equals("NETHER")) {
			return String.valueOf(Math.floor(player.getLocation().getZ() * 8));
		}
		return String.valueOf(Math.floor(player.getLocation().getZ()*10)/10);
	}

}
