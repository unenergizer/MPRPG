package com.minepile.mprpg.managers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;

public class PlayerManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static PlayerManager playerManagerInstance = new PlayerManager();
	
	//MAIN STATS
	static HashMap<String, Integer> healthPoints = new HashMap<String, Integer>();
	static HashMap<String, Integer> energyPoints = new HashMap<String, Integer>();
	static HashMap<String, Integer> swordDamage = new HashMap<String, Integer>();
	static HashMap<String, Integer> polearmDamage = new HashMap<String, Integer>();
	static HashMap<String, Integer> wandDamage = new HashMap<String, Integer>();
	static HashMap<String, Integer> axeDamage = new HashMap<String, Integer>();
	static HashMap<String, Integer> iceDamage = new HashMap<String, Integer>();
	static HashMap<String, Integer> fireDamage = new HashMap<String, Integer>();
	
	//Base statistic rates
	static int baseHealthPoints = 50;
	static int baseEnergyPoints = 50;
	static int baseHealthRegenRate = 1;
	static int baseEnergyRegenRate = 1;
	
	//Create instance
	public static PlayerManager getInstance() {
		return playerManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	

	//This performs the necessary steps to load in a player.
	//Creates a configuration file for new players and loads 
	//players statistics into the hashMaps.
	public static void setupPlayer(Player player) {
		
		String playerName = player.getName();
		String uuid = player.getUniqueId().toString();
		
		//Check to make sure the player configuration exists.
		//Player configurations are saved with the UUID (Mojang's Unique User Identifier).
        if(!new File("plugins/MPRPG/players/" + uuid + ".yml").exists()){
        	//The players file does not exist. Lets create the player file now.
        	createPlayerConfig(player);
        }
        
        //Read armor and set stats
        //update HashMap info
        healthPoints.put(playerName, baseHealthPoints);
		energyPoints.put(playerName, baseEnergyPoints);
	}
	
	//Remove players from the game. Will remove players
	//from hashMaps and saves any data if necessary.
	public static void removePlayer(Player player) {
		//Get Player's name.
		String playerName = player.getName();
		
		//remove player from hashmaps.
		healthPoints.remove(playerName);
		energyPoints.remove(playerName);
		swordDamage.remove(playerName);
		polearmDamage.remove(playerName);
		wandDamage.remove(playerName);
		axeDamage.remove(playerName);
		iceDamage.remove(playerName);
		fireDamage.remove(playerName);
	}

    public static void createPlayerConfig(Player player) {
 	
    	String uuid = player.getUniqueId().toString();
    	String playerName = player.getName();
    	
        File configFile = new File("plugins/MPRPG/players/" + uuid + ".yml");
        FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
        playerConfig.set("playerName", playerName);
        playerConfig.set("playerLVL", 1);
        playerConfig.set("playerEXP", 0);
        playerConfig.set("gold", 0);
        playerConfig.set("silver", 0);
        playerConfig.set("copper", 32);
        playerConfig.set("portalCash", 0);
        playerConfig.set("bankRows", 1);
        playerConfig.set("shopRows", 1);
        playerConfig.set("blackSmithingLVL", 1);
        playerConfig.set("blackSmithingEXP", 0);
        playerConfig.set("cookingLVL", 1);
        playerConfig.set("cookingEXP", 0);
        playerConfig.set("fishingLVL", 1);
        playerConfig.set("fishingEXP", 0);
        playerConfig.set("herbalismLVL", 1);
        playerConfig.set("herbalismEXP", 0);
        playerConfig.set("miningLVL", 1);
        playerConfig.set("miningEXP", 0);

        try {
            playerConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
	public static void setPlayerConfigStat(Player player, String stat, int amount) {
    	
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
    
	public static int getPlayerConfigStat(Player player, String stat) {
    	
		String uuid = player.getUniqueId().toString();
    	
        File configFile = new File("plugins/MPRPG/players/" + uuid + ".yml");
        FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
        return (int) playerConfig.get(stat);
	}
}
