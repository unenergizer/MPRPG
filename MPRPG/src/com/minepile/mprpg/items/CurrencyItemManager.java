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
	static File configFile;
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
        	configFile = new File(currencyItemsFilePath);
            currencyItemConfig =  YamlConfiguration.loadConfiguration(configFile);
        }
	}	
	
	/**
	 * Converts a currency String to a Material.
	 * 
	 * @param type A type of currency.
	 * @return A Material that can be converted to an ItemStack.
	 */
	public static Material getCurrencyType(String type) {
		//Convert Copper from a String to a Material.
		if (type.equalsIgnoreCase("copper")) {
			int itemId = currencyItemConfig.getInt("copper.itemId");	//Gets the Minecraft item ID from the config file.
			return ItemGeneratorManager.convertItemIdToMaterial(itemId);//Returns the Material for copper.
		
		//Convert Silver from a String to a Material.	
		} else if (type.equalsIgnoreCase("silver")) {
			int itemId = currencyItemConfig.getInt("silver.itemId");	//Gets the Minecraft item ID from the config file.
			return ItemGeneratorManager.convertItemIdToMaterial(itemId);//Returns the Material for silver.
		
		//Convert Gold from a String to a Material.	
		} else if (type.equalsIgnoreCase("gold")) {
			int itemId = currencyItemConfig.getInt("gold.itemId");		//Gets the Minecraft item ID from the config file.
			return ItemGeneratorManager.convertItemIdToMaterial(itemId);//Returns the Material for gold.
			
		//Convert "Premium Currency" from a String to a Material.			
		} else if (type.equalsIgnoreCase("premium")) {
			int itemId = currencyItemConfig.getInt("premium.itemId");	//Gets the Minecraft item ID from the config file.
			return ItemGeneratorManager.convertItemIdToMaterial(itemId);//Returns the Material for "premium currency."
		
		//Could not get the currency from the parameter String.	
		} else {
			Bukkit.broadcastMessage(ChatColor.RED + "getCurrencyType - unable to get currency type.");
			return null;
		}
	}
	
	/**
	 * This creates the configuration file that will hold data to save currency information.
	 */
    private static void createConfig() {
    	
        configFile = new File(currencyItemsFilePath);
        currencyItemConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        //set copper currency
        currencyItemConfig.set("copper", "copper");
        currencyItemConfig.set("copper.itemId", 366); //366 = Clay_Brick
        
        //set silver currency
        currencyItemConfig.set("silver", "silver");
        currencyItemConfig.set("silver.itemId", 265); //265 = Iron_ingot
       
        //set gold currency
        currencyItemConfig.set("gold", "gold");
        currencyItemConfig.set("gold.itemId", 266); //266 = Gold_ingot

        //set premium currency
        currencyItemConfig.set("premium", "premium");
        currencyItemConfig.set("premium.itemId", 388); //388 = Emerald
        
        try {
        	currencyItemConfig.save(configFile);	//Save the file.
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
	
}
