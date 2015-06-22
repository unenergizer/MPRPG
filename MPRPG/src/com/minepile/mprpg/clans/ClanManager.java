package com.minepile.mprpg.clans;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;

public class ClanManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static ClanManager clanManagerInstance = new ClanManager();
	static String clanFilePathStart = "plugins/MPRPG/clans/";
	static String clanFilePathEnd = ".yml";
	
	//Create instance
	public static ClanManager getInstance() {
		return clanManagerInstance;
	}
	
	//Setup ClanManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	
	
	//TODO: Save each clan to its own configuration file.
	
	public static void addClan(Player player, String clanName) {

    	String uuid = player.getUniqueId().toString();
    	String playerName = player.getName();
    	
        File configFile = new File(clanFilePathStart + clanName + clanFilePathEnd);
        FileConfiguration clanConfig =  YamlConfiguration.loadConfiguration(configFile);
        clanConfig.set("clanName", clanName);
        clanConfig.set("clantag", null);
        clanConfig.set("clanActive", true);
        clanConfig.set("clanDisbandDate", null);
        clanConfig.set("clanOwner", playerName);
        clanConfig.set("clanOwnerUUID", uuid);
        clanConfig.set("clanStaff", null);
        clanConfig.set("clanMembers", null);

        try {
            clanConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
	}

	public static void removeClan(Player player, String clanName) {
		File configFile = new File(clanFilePathStart + clanName + clanFilePathEnd);
        configFile.delete();
        //TODO: Remove 
	}

	public static void addPlayer(Player player, String clanName) {
		
	}
	
	public static void removePlayer(Player player, String clanName) {
		
	}
	
	public static void promotePlayer(Player player, String clanName) {
		
	}

	public static void demotePlayer(Player player, String clanName) {
		
	}

	public static void setMotd(Player player, String clanName) {
		
	}

	public static void memberList(Player player, String clanName) {
		
	}
	
	/**
	 * Gets a boolean value from the configuration.
	 * 
	 * @param clan The clan name
	 * @param value The entry to get.
	 * @return A string form the clans config file.
	 */
	public static boolean getClanConfigBoolean(String clan, String value) {
    	File configFile = new File(clanFilePathStart + clan + clanFilePathEnd);
        FileConfiguration clanConfig =  YamlConfiguration.loadConfiguration(configFile);
        return clanConfig.getBoolean(value);
	}	
	
	/**
	 * Gets a boolean value from the configuration.
	 * 
	 * @param clan The clan name
	 * @param value The entry to get.
	 * @return A string form the clans config file.
	 */
	public static void setClanConfigBoolean(String clan, String config, boolean value) {
        File configFile = new File(clanFilePathStart + clan + clanFilePathEnd);
        FileConfiguration clanConfig =  YamlConfiguration.loadConfiguration(configFile);
        clanConfig.set(config, value);

        try {
            clanConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
	}
	
	/**
	 * Gets an Integer value from the configuration.
	 * 
	 * @param clan The clan name
	 * @param value The entry to get.
	 * @return A string form the clan's configuration file.
	 */ 
	public static int getClanConfigInt(String clan, String value) {
    	File configFile = new File(clanFilePathStart + clan + clanFilePathEnd);
        FileConfiguration clanConfig =  YamlConfiguration.loadConfiguration(configFile);
        return (int) clanConfig.get(value);
	}
	
	/**
	 * Sets an Integer value from the configuration.
	 * 
	 * @param clan The clan name
	 * @param value The entry to get.
	 * @return A string form the clan's configuration file.
	 */ 
	public static void setClanConfigInt(String clan, String config, int value) {
        File configFile = new File(clanFilePathStart + clan + clanFilePathEnd);
        FileConfiguration clanConfig =  YamlConfiguration.loadConfiguration(configFile);
        clanConfig.set(config, value);

        try {
            clanConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
	}
	
	/**
	 * Sets a string value from the configuration.
	 * 
	 * @param clan The clan name
	 * @param value The entry to get.
	 * @return A string form the clan's configuration file.
	 */
	public static void setClanConfigString(String clan, String config, String value) {
    	 File configFile = new File(clanFilePathStart + clan + clanFilePathEnd);
        FileConfiguration clanConfig =  YamlConfiguration.loadConfiguration(configFile);
        clanConfig.set(config, value);

        try {
            clanConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
	}
	
	/**
	 * Gets a string value from the configuration.
	 * 
	 * @param clan The clan name
	 * @param value The entry to get.
	 * @return A string form the clan's configuration file.
	 */
	public static String getClanConfigString(String clan, String value) {
    	File configFile = new File(clanFilePathStart + clan + clanFilePathEnd);
        FileConfiguration clanConfig =  YamlConfiguration.loadConfiguration(configFile);
        return  (String) clanConfig.get(value);
	}

}
