package com.minepile.mprpg.chat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitScheduler;

import com.minepile.mprpg.MPRPG;

public class GameTipManager {

	//setup instance variables
	public static MPRPG plugin;
	static GameTipManager gameTipManagerInstance = new GameTipManager();
    static String FILE_PATH = "plugins/MPRPG/language/tips.yml";
    
    //Config file.
    static File configFile;
    static FileConfiguration gameTipConfig;
	
	//Setup regeneration variables
	private static int gameTipTime = 60 * 10;			//Time it takes for a message to show up. 60 seconds * 15 (15 minutes)
	
	//Create instance
	public static GameTipManager getInstance() {
		return gameTipManagerInstance;
	}

	//Setup GameTipManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		//If fishing configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(FILE_PATH)).exists()){
			createConfig();
        } else {
        	//lets load the configuration file.
        	configFile = new File(FILE_PATH);
            gameTipConfig =  YamlConfiguration.loadConfiguration(configFile);
        }
		//Load messages form configuration.
		loadMessages();
		
		//Start the timer used to reset blocks.
		startTipMessages();
	}
	
	/**
	 * Resets a block back to its original state.
	 */
	private void startTipMessages() {
		
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
            	Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "TIP: " + ChatColor.YELLOW + "Please report all bugs to staff members.");
			
            }
        }, 0L, 20 * gameTipTime);
	}
	
	public static List<?> loadMessages() {
		configFile = new File(FILE_PATH);
		gameTipConfig =  YamlConfiguration.loadConfiguration(configFile);
		return gameTipConfig.getList("GameTips");
	}
	
	/**
	 * This creates the configuration file that has the EXP leveling requirements.
	 */
    private static void createConfig() {
    	
        configFile = new File(FILE_PATH);
        gameTipConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        //Loop through and create each level for fishing.
        gameTipConfig.set("GameTips", "GameTips");
		gameTipConfig.set("GameTips", "This is a test message.");

        try {
            gameTipConfig.save(configFile);	//Save the file.
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
