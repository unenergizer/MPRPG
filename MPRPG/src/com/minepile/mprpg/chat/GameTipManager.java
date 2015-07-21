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

	//Setup instance variables.
	public static MPRPG plugin;
	static GameTipManager gameTipManagerInstance = new GameTipManager();
    static String FILE_PATH = "plugins/MPRPG/language/tips.yml";
    
    //Configuration file.
    private static File configFile;
    private static FileConfiguration gameTipConfig;
	
	//Setup regeneration variables.
	private static int gameTipTime = 60 * 10;			//Time it takes for a message to show up. 60 seconds * 15 (15 minutes)
	
	//Create instance.
	public static GameTipManager getInstance() {
		return gameTipManagerInstance;
	}

	//Setup GameTipManager.
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
	 * TODO: Load messages in from a list.
	 * <p>
	 * This starts the thread that will loop over and over displaying tips and
	 * other useful information to the player.
	 */
	private void startTipMessages() {
		
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
            	Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "TIP" 
            			+ ChatColor.DARK_GRAY + ChatColor.BOLD + ": " 
            			+ ChatColor.YELLOW + "Please report all bugs to staff members"  
            			+ ChatColor.DARK_GRAY + ".");
            }
        }, 0L, 20 * gameTipTime);
	}
	
	/**
	 * TODO:  Load all messages from configuration file.
	 * 
	 * @return A list of all tips (messages) in the configuration file.
	 */
	private static void loadMessages() {
		configFile = new File(FILE_PATH);
		gameTipConfig =  YamlConfiguration.loadConfiguration(configFile);
		List<String> list = (List<String>) gameTipConfig.getList("GameTips");
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
