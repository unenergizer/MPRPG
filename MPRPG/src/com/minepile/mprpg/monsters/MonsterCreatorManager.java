package com.minepile.mprpg.monsters;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;

public class MonsterCreatorManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static MonsterCreatorManager monsterCreatorManagerInstance = new MonsterCreatorManager();
	static String mobTypeFilePath = "plugins/MPRPG/mobs/monsterType.yml";
	
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
		if(!(new File(mobTypeFilePath)).exists()){
			createMonsterConfig();
        } else {
        	//lets load the configuration file.
        	File configFile = new File(mobTypeFilePath);
            monsterConfig =  YamlConfiguration.loadConfiguration(configFile);

            //setup and spawn monsters
            
        }
	}
	
	public static void setMonster(Player player, String mobName, Location location) {
		
		
		
		World world = player.getWorld();
		double x = location.getBlockX();
		double y = location.getBlockY() + 2;
		double z = location.getBlockZ();
		Location newLoc = new Location(player.getWorld(), x, y, z);
		
		//Set monster and add to config.
        File monsterIdConfigFile = new File("plugins/MPRPG/mobs/monsterId.yml");
        FileConfiguration monsterIdConfig =  YamlConfiguration.loadConfiguration(monsterIdConfigFile);
        monsterIdConfig.set(mobName, mobName);
        monsterIdConfig.set(mobName + ".player", player.getName());
        monsterIdConfig.set(mobName + ".X", x);
        monsterIdConfig.set(mobName + ".Y", y);
        monsterIdConfig.set(mobName + ".Z", z);

        try {
        	monsterIdConfig.save(monsterIdConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
		
        
        
        //Get mobType config values
        File monsterTypeConfigFile = new File(mobTypeFilePath);
        FileConfiguration monsterTypeConfig =  YamlConfiguration.loadConfiguration(monsterTypeConfigFile);
        String color = monsterTypeConfig.getString(mobName + ".mobNameColor");
        EntityType entity = EntityType.fromName(monsterTypeConfig.getString(mobName + ".entity"));
        int lvl = monsterTypeConfig.getInt(mobName + ".mobLVL");
        int hp = monsterTypeConfig.getInt(mobName + ".mobHP");
        int runRadius = monsterTypeConfig.getInt(mobName + ".mobRadius");
        
        //Spawn the monster in the game.
        MonsterManager.spawnMob(world, newLoc, entity, color, mobName, lvl, hp, runRadius);

	}
	
	public static void respawnMonster() {
		
	}
	
	public static void createNewMonster(Player player, String mobName, String nameColor, EntityType entityType, int mobLevel, int mobHP, int runRadius) {
    	
        File configFile = new File(mobTypeFilePath);
        FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
        playerConfig.set(mobName, mobName);
        playerConfig.set(mobName + ".player", player.getName());
        playerConfig.set(mobName + ".mobNameColor", nameColor);
        playerConfig.set(mobName + ".entity", entityType.toString());
        playerConfig.set(mobName + ".mobLVL", mobLevel);
        playerConfig.set(mobName + ".mobHP", mobLevel);
        playerConfig.set(mobName + ".runRadius", runRadius);

        try {
            playerConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        //Success message!
        player.sendMessage(" ");
    	player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD +"-" + ChatColor.DARK_GRAY + 
    			"<[ " + ChatColor.AQUA + "Success!! " + ChatColor.DARK_GRAY + "]>" + 
    			ChatColor.DARK_GRAY + ChatColor.BOLD + "---------------------------------");
    	player.sendMessage("   " + ChatColor.GREEN + player.getName() + ChatColor.RESET + 
    			", your mobType has been added" + ChatColor.DARK_GRAY + ". " +
    			ChatColor.RESET + "You can now" + ChatColor.DARK_GRAY + ":");
    	player.sendMessage("");
    	player.sendMessage(ChatColor.YELLOW + "    1" + ChatColor.DARK_GRAY + ". " + 
    			ChatColor.RESET + "Edit this mobType" + ChatColor.DARK_GRAY + ". " +
    			ChatColor.YELLOW + "   /mm manager edit " + mobName);
    	player.sendMessage(ChatColor.RED + "    2" + ChatColor.DARK_GRAY + ". " + 
    			ChatColor.RESET + "Delete this mobType" + ChatColor.DARK_GRAY + ". " +
    			ChatColor.RED + "/mm manager delete " + mobName);
    	player.sendMessage(ChatColor.LIGHT_PURPLE + "    3" + ChatColor.DARK_GRAY + ". " + 
    			ChatColor.RESET + "Set this mobType" + ChatColor.DARK_GRAY + ". " +
    			ChatColor.LIGHT_PURPLE + "    /mm manager set " + mobName);
	}
	
	public static void editMonster() {
		
	}
	
	public static void deleteMonster() {
		
	}
	
	//This creates the configuration file that will hold data to save mob attributes.
    private static void createMonsterConfig() {
    	
        File configFile = new File(mobTypeFilePath);
        FileConfiguration monsterConfig =  YamlConfiguration.loadConfiguration(configFile);

        try {
        	monsterConfig.save(configFile);	//Save the file.
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
	public static void setMobConfigInt(String mobName, String config, int value) {
        File configFile = new File(mobTypeFilePath);
        FileConfiguration mobConfig =  YamlConfiguration.loadConfiguration(configFile);
        mobConfig.set(config, value);

        try {
            mobConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
	}
    
	public static int getMobConfigInt(String mobName, String value) {
        File configFile = new File(mobTypeFilePath);
        FileConfiguration mobConfig =  YamlConfiguration.loadConfiguration(configFile);
        return (int) mobConfig.get(value);
	}
	
	public static void setMobConfigString(String mobName, String config, String value) {
        File configFile = new File(mobTypeFilePath);
        FileConfiguration mobConfig =  YamlConfiguration.loadConfiguration(configFile);
        mobConfig.set(config, value);

        try {
            mobConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
	}
	
	public static String getMobConfigString(String mobName, String value) {
        File configFile = new File(mobTypeFilePath);
        FileConfiguration mobConfig =  YamlConfiguration.loadConfiguration(configFile);
        return  (String) mobConfig.get(value);
	}
}
