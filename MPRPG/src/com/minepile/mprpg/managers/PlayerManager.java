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
	
	public static void updatePlayerConfig(Player player, String stat, int amount) {
    	
		String uuid = player.getUniqueId().toString();
    	
        File configFile = new File("plugins/MPRPG/players/" + uuid + ".yml");
        FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
        playerConfig.set(stat, amount);

        try {
            playerConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
	}
	
	public static int getPlayerConfig(Player player, String stat) {
    	
		String uuid = player.getUniqueId().toString();
    	
        File configFile = new File("plugins/MPRPG/players/" + uuid + ".yml");
        FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
        return (int) playerConfig.get(stat);
	}
	
	public static void loadPlayerConfig(Player player) {
		
		String uuid = player.getUniqueId().toString();
		
		//Check to make sure the player configuration exists.
		//Player configurations are saved with the UUID (Mojang's Unique User Identifier).
        if(new File("plugins/MPRPG/players/" + uuid + ".yml").exists()){
        	
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
        playerConfig.set("playerLVL", 1);
        playerConfig.set("playerEXP", 0);
        playerConfig.set("miningLVL", 1);
        playerConfig.set("miningEXP", 0);

        try {
            playerConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
