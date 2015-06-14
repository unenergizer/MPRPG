package com.minepile.mprpg.items;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.minepile.mprpg.MPRPG;

public class WeaponItemManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static WeaponItemManager weaponItemManagerInstance = new WeaponItemManager();
	static String weaponItemsFilePath = "plugins/MPRPG/items/Weapons.yml";
	
	//Configuration file that holds weapon information.
	static File configFile;
	static FileConfiguration weaponItemConfig;
	
	//Create instance
	public static WeaponItemManager getInstance() {
		return weaponItemManagerInstance;
	}
	
	//Setup Weapon item manager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		//If configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(weaponItemsFilePath)).exists()){
			createConfig();
        } else {
        	//lets load the configuration file.
        	configFile = new File(weaponItemsFilePath);
        	weaponItemConfig =  YamlConfiguration.loadConfiguration(configFile);
        }
	}	
	
	/**
	 * This creates the configuration file that will hold data to save weapon information.
	 */
    private static void createConfig() {
    	
        configFile = new File(weaponItemsFilePath);
        weaponItemConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        weaponItemConfig.set("quality.junk.damageMin", 1);
        weaponItemConfig.set("quality.junk.damageMax", 20);
        weaponItemConfig.set("quality.common.damageMin", 10);
        weaponItemConfig.set("quality.common.damageMax", 40);
        weaponItemConfig.set("quality.uncommon.damageMin", 30);
        weaponItemConfig.set("quality.uncommon.damageMax", 60);
        weaponItemConfig.set("quality.rare.damageMin", 50);
        weaponItemConfig.set("quality.rare.damageMax", 120);
        weaponItemConfig.set("quality.epic.damageMin", 100);
        weaponItemConfig.set("quality.epic.damageMax", 200);
        weaponItemConfig.set("quality.legendary.damageMin", 150);
        weaponItemConfig.set("quality.legendary.damageMax", 400);
        
        try {
        	weaponItemConfig.save(configFile);	//Save the file.
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
	
	//TODO:
	/*
	Weapon attributes:
	
	Knockback %
	Fire
	Critical Hit Bonus
	Poison
	Slow
	Blindness
	Life Steal
	Ice
	 */
	
}
