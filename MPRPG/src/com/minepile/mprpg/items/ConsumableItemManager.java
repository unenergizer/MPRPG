package com.minepile.mprpg.items;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.minepile.mprpg.MPRPG;

public class ConsumableItemManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static ConsumableItemManager consumableItemManagerInstance = new ConsumableItemManager();
	static String consumableItemsFilePath = "plugins/MPRPG/items/Consumable.yml";
	
	//Configuration file that holds consumable item information.
	static File configFile;
	static FileConfiguration consumableItemConfig;
	
	//Create instance
	public static ConsumableItemManager getInstance() {
		return consumableItemManagerInstance;
	}
	
	//Setup Consumable item
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		//If configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(consumableItemsFilePath)).exists()){
			createConfig();
        } else {
        	//lets load the configuration file.
        	configFile = new File(consumableItemsFilePath);
        	consumableItemConfig =  YamlConfiguration.loadConfiguration(configFile);
        }
	}	
	
	/**
	 * This creates the configuration file that will hold data to save consumable item information.
	 */
    private static void createConfig() {
    	
        configFile = new File(consumableItemsFilePath);
        consumableItemConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        //set copper currency
        consumableItemConfig.set("copper", "copper");
        consumableItemConfig.set("copper.itemId", 366); //366 = Clay_Brick
        
        //set silver currency
        consumableItemConfig.set("silver", "silver");
        consumableItemConfig.set("silver.itemId", 265); //265 = Iron_ingot
        
        //set gold currency
        consumableItemConfig.set("gold", "gold");
        consumableItemConfig.set("gold.itemId", 266); //266 = Gold_ingot

        //set premium currency
        consumableItemConfig.set("premium", "premium");
        consumableItemConfig.set("premium.itemId", 388); //388 = Emerald
        
        try {
        	consumableItemConfig.save(configFile);	//Save the file.
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
	
}
