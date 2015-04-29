package com.minepile.mprpg.monsters;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.minepile.mprpg.MPRPG;

public class MonsterCreatorManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static MonsterCreatorManager monsterCreatorManagerInstance = new MonsterCreatorManager();
	
	//Create instance
	public static MonsterCreatorManager getInstance() {
		return monsterCreatorManagerInstance;
	}
	
	//Configuration file that holds monster information.
	FileConfiguration monsterConfig;
	
	//Setup MobManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		//If monster configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File("plugins/MPRPG/mobs/monsterType.yml")).exists()){
			createMonsterConfig();
        } else {
        	//lets load the configuration file.
        	File configFile = new File("plugins/MPRPG/mobs/monsterType.yml");
            monsterConfig =  YamlConfiguration.loadConfiguration(configFile);

            //setup and spawn monsters
            
        }
	}
	
	public static void spawnMonster(String mobName) {
        File configFile = new File("plugins/MPRPG/mobs/monsterType.yml");
        FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
        playerConfig.get("mob");
        playerConfig.get("mob.mobLVL");
        playerConfig.get("mob.mobHP");

        try {
            playerConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
	}
	
	public static void respawnMonster() {
		
	}
	
	public static void createNewMonster(String mobName, String entityType, int mobLevel, int mobHP) {
    	
        File configFile = new File("plugins/MPRPG/mobs/monsterType.yml");
        FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
        playerConfig.set("mob", mobName);
        playerConfig.set("mob.entity", entityType);
        playerConfig.set("mob.mobLVL", mobLevel);
        playerConfig.set("mob.mobHP", mobLevel);

        try {
            playerConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
	}
	
	public static void editMonster() {
		
	}
	
	public static void deleteMonster() {
		
	}
	
	//This creates the configuration file that will hold data to save mob attributes.
    private static void createMonsterConfig() {
    	
        File configFile = new File("plugins/MPRPG/mobs/monsterType.yml");
        FileConfiguration monsterConfig =  YamlConfiguration.loadConfiguration(configFile);

        try {
        	monsterConfig.save(configFile);	//Save the file.
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
	public static void setMobConfigInt(String mobName, String config, int value) {
        File configFile = new File("plugins/MPRPG/mobs/monsterType.yml");
        FileConfiguration mobConfig =  YamlConfiguration.loadConfiguration(configFile);
        mobConfig.set(config, value);

        try {
            mobConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
	}
    
	public static int getMobConfigInt(String mobName, String value) {
        File configFile = new File("plugins/MPRPG/mobs/monsterType.yml");
        FileConfiguration mobConfig =  YamlConfiguration.loadConfiguration(configFile);
        return (int) mobConfig.get(value);
	}
	
	public static void setMobConfigString(String mobName, String config, String value) {
        File configFile = new File("plugins/MPRPG/mobs/monsterType.yml");
        FileConfiguration mobConfig =  YamlConfiguration.loadConfiguration(configFile);
        mobConfig.set(config, value);

        try {
            mobConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
	}
	
	public static String getMobConfigString(String mobName, String value) {
        File configFile = new File("plugins/MPRPG/mobs/monsterType.yml");
        FileConfiguration mobConfig =  YamlConfiguration.loadConfiguration(configFile);
        return  (String) mobConfig.get(value);
	}
}
