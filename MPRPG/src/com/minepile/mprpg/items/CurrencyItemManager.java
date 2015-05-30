package com.minepile.mprpg.items;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.minepile.mprpg.MPRPG;

public class CurrencyItemManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static CurrencyItemManager currencyItemManagerInstance = new CurrencyItemManager();
	static String currencyItemsFilePath = "plugins/MPRPG/items/Currency.yml";
	
	//Configuration file that holds currency information.
	static FileConfiguration currencyItemConfig;
	
	//Create instance
	public static CurrencyItemManager getInstance() {
		return currencyItemManagerInstance;
	}
	
	//Setup currencyItemManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		//If configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(currencyItemsFilePath)).exists()){
			createConfig();
        } else {
        	//lets load the configuration file.
        	File configFile = new File(currencyItemsFilePath);
            currencyItemConfig =  YamlConfiguration.loadConfiguration(configFile);
        }
	}	
	
	public static Material getCurrencyType(String type) {
		if (type.equalsIgnoreCase("copper")) {
			int itemId = currencyItemConfig.getInt("copper.itemId");
			return convertItemIdToMaterial(itemId);
		} else if (type.equalsIgnoreCase("silver")) {
			int itemId = currencyItemConfig.getInt("silver.itemId");
			return convertItemIdToMaterial(itemId);
		} else if (type.equalsIgnoreCase("gold")) {
			int itemId = currencyItemConfig.getInt("gold.itemId");
			return convertItemIdToMaterial(itemId);
		} else if (type.equalsIgnoreCase("premium")) {
			int itemId = currencyItemConfig.getInt("premium.itemId");
			return convertItemIdToMaterial(itemId);
		} else {
			Bukkit.broadcastMessage(ChatColor.RED + "getCurrencyType - unable to get currency type.");
			return null;
		}
	}
	
	private static Material convertItemIdToMaterial(int itemId) {
		Material item = Material.getMaterial(itemId);
		return item;
	}
	
	//This creates the configuration file that will hold data to save currency information.
    private static void createConfig() {
    	
        File configFile = new File(currencyItemsFilePath);
        FileConfiguration currencyItemsConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        //set copper currency
        currencyItemsConfig.set("copper", "copper");
        currencyItemsConfig.set("copper.itemId", 366); //366 = Clay_Brick
        
        //set silver currency
        currencyItemsConfig.set("silver", "silver");
        currencyItemsConfig.set("silver.itemId", 265); //265 = Iron_ingot
        
        //set gold currency
        currencyItemsConfig.set("gold", "gold");
        currencyItemsConfig.set("gold.itemId", 266); //266 = Gold_ingot

        //set premium currency
        currencyItemsConfig.set("premium", "premium");
        currencyItemsConfig.set("premium.itemId", 388); //388 = Emerald
        
        try {
        	currencyItemsConfig.save(configFile);	//Save the file.
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
	
}
