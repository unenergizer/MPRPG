package com.minepile.mprpg.items;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.minepile.mprpg.MPRPG;

public class LootTableMobManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static LootTableMobManager mobDropTablesManagerInstance = new LootTableMobManager();
	static String LootTableFilePath = "plugins/MPRPG/items/LootTableMobs.yml";
	
	static int dropPercentage = 50;
	
	//Configuration file that holds currency information.
	static File configFile;
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
        	configFile = new File(LootTableFilePath);
        	lootTableConfig =  YamlConfiguration.loadConfiguration(configFile);
        }
	}
	
	public static void toggleLootTableDrop(String lootTable, Location loc) {
		
        //configFile = new File(LootTableFilePath);
        //lootTableConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        
        ArrayList itemName = (ArrayList) lootTableConfig.getList(lootTable + ".armorItem");
        
        for (int i = 0; i < itemName.size(); i++) {
        	if (dropItem() == true) {
        		ArmorItemManager.dropItem((String) itemName.get(i), loc);
        	}
        }
	}
	
	private static boolean dropItem() {
		Random rand = new Random();
		
		int randomNum = rand.nextInt() * 100 + 1;
		
		if (randomNum > dropPercentage) { 
			return true;
		} else {
			return false;
		}
	}
	
	//This creates the configuration file that will hold data to save loot table information.
    private static void createConfig() {
    	
        configFile = new File(LootTableFilePath);
        lootTableConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        //set a default loot table
        lootTableConfig.set("junk01", "junk01");
        lootTableConfig.set("junk01.armorItem", "testArmorDrop");
        lootTableConfig.set("junk01.currency", "copper");
        lootTableConfig.set("junk01.currencyDropMin", 0);
        lootTableConfig.set("junk01.currencyDropMax", 10);
        

        
        try {
        	lootTableConfig.save(configFile);	//Save the file.
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
