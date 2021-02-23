package me.oreoezi.datamanagers;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.oreoezi.harmonyboard.HarmonyBoard;


public class Configs
{
	private HarmonyBoard main;
	public HashMap<String, FileConfiguration> configs = new HashMap<String, FileConfiguration>();
	public HashMap<String, FileConfiguration> scoreboards = new HashMap<String, FileConfiguration>();
	public HashMap<String, FileConfiguration> animations = new HashMap<String, FileConfiguration>();
    public Configs(HarmonyBoard main) {
    	this.main = main;
    	createSubdirectories();
    }
    private void createSubdirectories() {
    	String folder = main.getDataFolder().getAbsolutePath();
    	Path sbpath = Paths.get(folder + "/Scoreboards");
    	Path anpath = Paths.get(folder + "/Animations");
    	try {
			if (!Files.exists(sbpath)) Files.createDirectories(sbpath);
			if (!Files.exists(anpath)) Files.createDirectories(anpath);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	createDefaultAnimSB();
    }
    private void createDefaultAnimSB() {
    	String folder = main.getDataFolder().getAbsolutePath();
    	Path sbpath = Paths.get(folder + "/Scoreboards/default.yml");
    	Path anpath = Paths.get(folder + "/Animations/default.yml");
    	try {
			if (!Files.exists(sbpath)) {
				Files.createFile(sbpath);
				File config_file = new File(folder + "/Scoreboards/default.yml");
				FileConfiguration file_config = (FileConfiguration)YamlConfiguration.loadConfiguration(config_file);
                Reader config_defaults = new InputStreamReader(main.getResource("sb_example.yml"), "UTF-8");
                file_config.setDefaults((Configuration)YamlConfiguration.loadConfiguration(config_defaults));
                file_config.options().copyDefaults(true);
                file_config.save(config_file);
			}
			if (!Files.exists(anpath)) {
				Files.createFile(anpath);
				File config_file = new File(folder + "/Animations/default.yml");
				FileConfiguration file_config = (FileConfiguration)YamlConfiguration.loadConfiguration(config_file);
                Reader config_defaults = new InputStreamReader(main.getResource("an_example.yml"), "UTF-8");
                file_config.setDefaults((Configuration)YamlConfiguration.loadConfiguration(config_defaults));
                file_config.options().copyDefaults(true);
                file_config.save(config_file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	createConfigs();
    }
    private void createConfigs() {
    	String[] confignames = new String[] { "config", "language"};
        for (int i=0;i<confignames.length; i++) {
            File config_file = new File(main.getDataFolder() + "/" + confignames[i] + ".yml");
            FileConfiguration file_config = (FileConfiguration)YamlConfiguration.loadConfiguration(config_file);
            if (!config_file.exists()) {
                try {
                    Reader config_defaults = new InputStreamReader(main.getResource(String.valueOf(confignames[i]) + ".yml"), "UTF-8");
                    file_config.setDefaults((Configuration)YamlConfiguration.loadConfiguration(config_defaults));
                    file_config.options().copyDefaults(true);
                    file_config.save(config_file);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.configs.put(confignames[i], file_config);
        }
        getScoreboards();
        getAnimations();
    }
    private void getScoreboards() {
    	File sb_folder = new File(main.getDataFolder()+"/Scoreboards");
    	String[] scoreboards = sb_folder.list();
    	for (int i=0;i<scoreboards.length;i++) {
            File config_file = new File(main.getDataFolder() + "/Scoreboards/" + scoreboards[i]);
            FileConfiguration file_config = (FileConfiguration)YamlConfiguration.loadConfiguration(config_file);
            this.scoreboards.put(scoreboards[i].replace(".yml", ""), file_config);
    	}
    }
    private void getAnimations() {
    	File sb_folder = new File(main.getDataFolder()+"/Animations");
    	String[] scoreboards = sb_folder.list();
    	for (int i=0;i<scoreboards.length;i++) {
            File config_file = new File(main.getDataFolder() + "/Animations/" + scoreboards[i]);
            FileConfiguration file_config = (FileConfiguration)YamlConfiguration.loadConfiguration(config_file);
            this.animations.put(scoreboards[i].replace(".yml", ""), file_config);
    	}
    }
    public FileConfiguration getConfig(String config_name) {
        return this.configs.get(config_name);
    }
    
    public FileConfiguration getScoreboard(String scoreboard_name) {
    	 return this.scoreboards.get(scoreboard_name);
    }
    
    public FileConfiguration getAnimation(String animation_name) {
   	 return this.animations.get(animation_name);
   }
}
