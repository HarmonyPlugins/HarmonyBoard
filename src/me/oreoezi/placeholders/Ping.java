package me.oreoezi.placeholders;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.oreoezi.utils.HarmonyPlaceholder;

public class Ping extends HarmonyPlaceholder{
	private int getPing(Player player) {
		String version = Bukkit.getServer().getVersion();
		if (version.contains("1.17"))
			return ((org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer) player).getHandle().e;
		if (version.contains("1.16.5") || version.contains("1.16.4"))
			return ((org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer) player).getHandle().ping;
		if (version.contains("1.16.3") || version.contains("1.16.2"))
			return ((org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer) player).getHandle().ping;
		if (version.contains("1.16"))
			return ((org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer) player).getHandle().ping;
		if (version.contains("1.15"))
			return ((org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer) player).getHandle().ping;
		if (version.contains("1.14"))
			return ((org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer) player).getHandle().ping;
		if (version.contains("1.13"))
			return ((org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer) player).getHandle().ping;
		if (version.contains("1.12"))
			return ((org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer) player).getHandle().ping;
		return ((org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer) player).getHandle().ping;	
	}
	@Override
	public String getName() {
		return "ping";
	}
	@Override
	public String getValue(Player player) {
		return String.valueOf(getPing(player));
	}
}
