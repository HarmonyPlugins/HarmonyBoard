package me.oreoezi.placeholders;

import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyPlaceholder;

public class PosY extends HarmonyPlaceholder{
	@Override
	public String getName() {
		return "posy";
	}
	@Override
	public String getValue(Player player) {
		return String.valueOf(Math.floor(player.getLocation().getY()*10)/10);
	}

}
