package com.minepile.mprpg.managers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;

public class PlayerManager {
	
	//setup variables
	public static MPRPG plugin;
	static PlayerManager playerManagerInstance = new PlayerManager();
	
	//Create instance
	public static PlayerManager getInstance() {
		return playerManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	
	
	public static void loadPlayerConfig(Player player) {
		
		String uuid = player.getUniqueId().toString();
		
		//Check to make sure the player configuration exists.
		//Player configurations are saved with the UUID (Mojang's Unique User Identifier).
        if(new File("plugins/MPRPG/players/" + uuid + "config.yml").exists()){
        	
            //Lets load the players configuration file.
            
        	/* This code will load settings from the user's configuration file. 
        	File configFile = new File("plugins/MPRPG/players/" + uuid + ".yml");
            FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
            playerConfig.get("miningExp");
            */
            
        } else {
        	
        	//The players file does not exist. Lets create the player file now.
        	createPlayerConfig(player);
        }
	}

    public static void createPlayerConfig(Player player) {
 	
    	String uuid = player.getUniqueId().toString();
    	String playerName = player.getName();
    	
        File configFile = new File("plugins/MPRPG/players/" + uuid + ".yml");
        FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
        playerConfig.set("playerName", playerName);
        playerConfig.set("miningExp", 0);

        try {
            playerConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
