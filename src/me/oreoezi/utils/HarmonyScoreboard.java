package me.oreoezi.utils;
import org.bukkit.entity.Player;



public class HarmonyScoreboard {
	private boolean deleted = false;
    public HarmonyScoreboard(String name, Player player) {
    }
    public void setLine(int pos, String text) {
    	
    }
    public void setTitle(String title) {
    	
    }
    public void delete() {
    	deleted = true;
    	destroy();
    }
    public boolean getDeleted() {
    	return deleted;
    }
    public void destroy() {
    	
    }
}
