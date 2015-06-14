package com.minepile.mprpg.items;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.minepile.mprpg.MPRPG;

public class MiscItemManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static MiscItemManager itemManagerInstance = new MiscItemManager();
	static String miscItemsFilePath = "plugins/MPRPG/items/MiscItems.yml";
	
	//Configuration file that holds "miscellaneous item" information.
	static File configFile;
	static FileConfiguration miscItemManagerConfig;
	
	//Create instance
	public static MiscItemManager getInstance() {
		return itemManagerInstance;
	}
	
	//Setup MistItemsManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		//If configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(miscItemsFilePath)).exists()){
			createConfig();
        } else {
        	//lets load the configuration file.
        	configFile = new File(miscItemsFilePath);
        	miscItemManagerConfig =  YamlConfiguration.loadConfiguration(configFile);
        }
	}	
	
	/**
	 * This creates the configuration file that will hold data to save "miscellaneous item" information.
	 */
    private static void createConfig() {
    	
        configFile = new File(miscItemsFilePath);
        miscItemManagerConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        //set copper currency
        miscItemManagerConfig.set("copper", "copper");
        miscItemManagerConfig.set("copper.itemId", 366); //366 = Clay_Brick
        
        //set silver currency
        miscItemManagerConfig.set("silver", "silver");
        miscItemManagerConfig.set("silver.itemId", 265); //265 = Iron_ingot
        
        //set gold currency
        miscItemManagerConfig.set("gold", "gold");
        miscItemManagerConfig.set("gold.itemId", 266); //266 = Gold_ingot

        //set premium currency
        miscItemManagerConfig.set("premium", "premium");
        miscItemManagerConfig.set("premium.itemId", 388); //388 = Emerald
        
        try {
        	miscItemManagerConfig.save(configFile);	//Save the file.
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
