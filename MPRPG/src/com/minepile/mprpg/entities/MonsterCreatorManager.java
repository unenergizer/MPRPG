package com.minepile.mprpg.entities;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;

public class MonsterCreatorManager {
	
	//setup instance variables
	public static MPRPG plugin;
	private static MonsterCreatorManager monsterCreatorManagerInstance = new MonsterCreatorManager();
	private static String mobTypeFilePath = "plugins/MPRPG/mobs/monsterType.yml";
	
	//Create instance
	public static MonsterCreatorManager getInstance() {
		return monsterCreatorManagerInstance;
	}
	
	//Configuration file that holds monster information.
	//static File configFile;
	//static FileConfiguration monsterConfig;
	
	//Setup MobManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		//If monster configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(mobTypeFilePath)).exists()){
			createMonsterConfig();
        }
	}
	
	/**
	 * Sets an entitie the player has created.
	 * 
	 * @param player The player who created the entitie.
	 * @param mobName The entities name.
	 * @param location The XYZ location in the World the entitie will spawn.
	 */
	public static void setEntitie(Player player, String mobName, Location location) {
		
		int currentCount = getMobIdTotals();
		final int newCount = currentCount + 1;
		double x = location.getBlockX();
		double y = location.getBlockY() + 2;
		double z = location.getBlockZ();
		
		//Set monster and add to config.
        File monsterIdConfigFile = new File(MonsterManager.mobTypeIdPath);
        FileConfiguration monsterIdConfig =  YamlConfiguration.loadConfiguration(monsterIdConfigFile);
        monsterIdConfig.set("settings", "settings");
        monsterIdConfig.set("settings.countTotal", newCount);
        monsterIdConfig.set(Integer.toString(newCount), mobName);
        monsterIdConfig.set(Integer.toString(newCount) + ".player", player.getName());
        monsterIdConfig.set(Integer.toString(newCount) + ".mobType", mobName);
        monsterIdConfig.set(Integer.toString(newCount) + ".Y", y);
        monsterIdConfig.set(Integer.toString(newCount) + ".X", x);
        monsterIdConfig.set(Integer.toString(newCount) + ".Y", y);
        monsterIdConfig.set(Integer.toString(newCount) + ".Z", z);

        try {
        	monsterIdConfig.save(monsterIdConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //Spawn the monster in the game.
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				MonsterManager.setupEntity(newCount);
			}
		}, 2 * 20L);
	}
	
	/**
	 * Gets the total entities in the configuration file.
	 */
	public static int getMobIdTotals() {
        
		//Get mobType config values
        int countTotal = MonsterManager.getMonsterIdConfig().getInt("settings.countTotal");
        
		return countTotal;
	}
	
	/**
	 * This will create a new entry in the monser configuration file.
	 * 
	 * @param player The player who created the entry.
	 * @param mobName The name of the entitie.
	 * @param nameColor The color of the entities name.
	 * @param entityType The type of entitie to spawn.
	 * @param mobLevel The level displayed next to the entities name.
	 * @param mobHP The hitpoints that are assigned to the entitie.
	 * @param runRadius How far the entitie can move away from its spawn location.
	 * @param lootTable A list of items that might drop when the entitie dies.
	 */
	public static void createNewMonster(Player player, String mobName, String nameColor, EntityType entityType, int mobLevel, int mobHP, int runRadius, String lootTable) {

    	File configFile = new File(mobTypeFilePath);
        FileConfiguration monsterConfig =  YamlConfiguration.loadConfiguration(configFile);
        monsterConfig.set(mobName, mobName);
        monsterConfig.set(mobName + ".player", player.getName());
        monsterConfig.set(mobName + ".mobNameColor", nameColor);
        monsterConfig.set(mobName + ".entity", entityType.toString());
        monsterConfig.set(mobName + ".mobLVL", mobLevel);
        monsterConfig.set(mobName + ".mobHP", mobHP);
        monsterConfig.set(mobName + ".runRadius", runRadius);
        monsterConfig.set(mobName + ".lootTable", lootTable);

        try {
        	monsterConfig.save(configFile);
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
	
	/**
	 * TODO: This will edit a previously made monster in the monster configuration file.
	 */
	public static void editMonster() {
		
	}
	
	/**
	 * TODO: This will delete an entry from the monster configuration file.
	 */
	public static void deleteMonster() {
		
	}
	
	/**
	 * This creates the configuration file that will hold data to save mob attributes.
	 */
    private static void createMonsterConfig() {

    	File configFile = new File(mobTypeFilePath);
        FileConfiguration monsterConfig =  YamlConfiguration.loadConfiguration(configFile);
        configFile = new File(mobTypeFilePath);
        monsterConfig =  YamlConfiguration.loadConfiguration(configFile);

        try {
        	monsterConfig.save(configFile);	//Save the file.
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
	public static FileConfiguration getMonsterConfig() {

    	File configFile = new File(mobTypeFilePath);
        FileConfiguration monsterConfig =  YamlConfiguration.loadConfiguration(configFile);
		return monsterConfig;
	}

	public static void setMobConfigInt(String mobName, String config, int value) {

    	File configFile = new File(mobTypeFilePath);
        FileConfiguration monsterConfig =  YamlConfiguration.loadConfiguration(configFile);
        monsterConfig.set(config, value);

        try {
            monsterConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
	}
    
	public static int getMobConfigInt(String mobName, String value) {

    	File configFile = new File(mobTypeFilePath);
        FileConfiguration monsterConfig =  YamlConfiguration.loadConfiguration(configFile);
        return (int) monsterConfig.get(value);
	}
	
	public static void setMobConfigString(String mobName, String config, String value) {

    	File configFile = new File(mobTypeFilePath);
        FileConfiguration monsterConfig =  YamlConfiguration.loadConfiguration(configFile);
        monsterConfig.set(config, value);

        try {
            monsterConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
	}
	
	public static String getMobConfigString(String mobName, String value) {

    	File configFile = new File(mobTypeFilePath);
        FileConfiguration monsterConfig =  YamlConfiguration.loadConfiguration(configFile);
        return  (String) monsterConfig.get(value);
	}

	public static String getMobTypeFilePath() {
		return mobTypeFilePath;
	}
}
