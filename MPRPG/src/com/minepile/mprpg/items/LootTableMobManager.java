package com.minepile.mprpg.items;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.minepile.mprpg.MPRPG;

public class LootTableMobManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static LootTableMobManager mobDropTablesManagerInstance = new LootTableMobManager();
	static String LootTableFilePath = "plugins/MPRPG/items/LootTableMobs.yml";
	
	//Configuration file that holds currency information.
	static FileConfiguration lootTableConfig;
	
	//Create instance
	public static LootTableMobManager getInstance() {
		return mobDropTablesManagerInstance;
	}
	
	//Setup Loot table Manager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		//If configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(LootTableFilePath)).exists()){
			createConfig();
        } else {
        	//lets load the configuration file.
        	File configFile = new File(LootTableFilePath);
        	lootTableConfig =  YamlConfiguration.loadConfiguration(configFile);
        }
	}
	
	public static void toggleLootTableDrop(String lootTable, Location loc) {
		
        File configFile = new File(LootTableFilePath);
        FileConfiguration lootTableConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        String itemName = lootTableConfig.getString(lootTable + ".armorItem");
        ArmorItemManager.dropItem(itemName, loc);
	}
	
	//This creates the configuration file that will hold data to save loot table information.
    private static void createConfig() {
    	
        File configFile = new File(LootTableFilePath);
        FileConfiguration currencyItemsConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        //set a default loot table
        currencyItemsConfig.set("junk01", "junk01");
        currencyItemsConfig.set("junk01.armorItem", "testArmorDrop");
        currencyItemsConfig.set("junk01.currency", "copper");
        currencyItemsConfig.set("junk01.currencyDropMin", 0);
        currencyItemsConfig.set("junk01.currencyDropMax", 10);
        

        
        try {
        	currencyItemsConfig.save(configFile);	//Save the file.
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
	
	//TODO:
	/*
	 	Possible loot table configuration:
	 	
	 	tableName:   (junk drops, epic drops, etc)
	 		tableName.armor = id1, id2, id3, etc
	 		tableName.weapon = id1, id2, id3, etc
	 		tableName.consumables = id1, id2, id3, etc
	 		tableName.currency = id1
	 		tableName.misc = id1
	 */
	
}
