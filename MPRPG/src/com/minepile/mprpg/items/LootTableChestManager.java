package com.minepile.mprpg.items;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.minepile.mprpg.MPRPG;

public class LootTableChestManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static LootTableChestManager LootTableChestManagerInstance = new LootTableChestManager();
	static String LootTableFilePath = "plugins/MPRPG/items/LootTableChests.yml";
	
	//Configuration file that holds currency information.
	static File configFile;
	static FileConfiguration lootTableConfig;
	
	//Create instance
	public static LootTableChestManager getInstance() {
		return LootTableChestManagerInstance;
	}
	
	//Setup Chest Loot Tables Manager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		//If configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(LootTableFilePath)).exists()){
			createConfig();
        } else {
        	//lets load the configuration file.
        	configFile = new File(LootTableFilePath);
        	lootTableConfig =  YamlConfiguration.loadConfiguration(configFile);
        }
	}	
	
	/**
	 * This creates the configuration file that will hold data to save loot table information.
	 */
    private static void createConfig() {
    
        configFile = new File(LootTableFilePath);
        lootTableConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        //set copper currency
        lootTableConfig.set("copper", "copper");
        lootTableConfig.set("copper.itemId", 366); //366 = Clay_Brick
        
        //set silver currency
        lootTableConfig.set("silver", "silver");
        lootTableConfig.set("silver.itemId", 265); //265 = Iron_ingot
        
        //set gold currency
        lootTableConfig.set("gold", "gold");
        lootTableConfig.set("gold.itemId", 266); //266 = Gold_ingot

        //set premium currency
        lootTableConfig.set("premium", "premium");
        lootTableConfig.set("premium.itemId", 388); //388 = Emerald
        
        try {
        	lootTableConfig.save(configFile);	//Save the file.
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
