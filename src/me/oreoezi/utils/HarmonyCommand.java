package me.oreoezi.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import me.oreoezi.harmonyboard.HarmonyBoard;

public class HarmonyCommand {
	public HarmonyCommand(HarmonyBoard main) {
		
	}
	public void onExec(Player player, String[] args) {
		
	}
	public String getName() {
		return "testcommand";
	}
	public String getPermission() {
		return "harmonyboard.help";
	}
	public String getDescription() {
		return "none";
	}
	public String getArgs() {
		return "";
	}
	public HashMap<Integer, ArrayList<String>> getSuggestions() {
		HashMap<Integer, ArrayList<String>> nothing = new HashMap<Integer, ArrayList<String>>();
		return nothing;
	}
}
