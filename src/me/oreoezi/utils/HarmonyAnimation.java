package me.oreoezi.utils;

import org.bukkit.configuration.file.FileConfiguration;

public class HarmonyAnimation {
	private FileConfiguration config;
	private int frame=0;
	private int interval=0;
	private String currentFrame;
	private String name;
	public HarmonyAnimation(FileConfiguration config, String name) {
		this.config = config;
		this.name = name;
		currentFrame = config.getString("lines."+frame);
	}
	public void updateAnimation() {
		interval++;
		if (interval%config.getInt("delay") == 0) {
			frame++;
			if (frame >= config.getInt("size")) frame=0;
			currentFrame = config.getString("lines."+frame);
			interval = 0;
		}
	}
	public String getCurrentFrame() {
		return currentFrame;
	}
	public String getName() {
		return name;
	}
}
