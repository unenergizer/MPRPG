package com.minepile.mprpg.items;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.minepile.mprpg.MPRPG;

public class LootTableChestManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static LootTableChestManager LootTableChestManagerInstance = new LootTableChestManager();
	static String LootTableFilePath = "plugins/MPRPG/items/LootTableChests.yml";
	
	//Configuration file that holds currency information.
	static File configFile;
	static FileConfiguration lootTableConfig;
	
	//Drop percentage
	static int dropPercentage = 50;
	
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
	
	public static void toggleChestLoot(Player player) {
		
		String lootTable = "junk01";
		Inventory chest = player.getInventory();
		
        ArrayList itemName = (ArrayList) lootTableConfig.getList(lootTable + ".armorItem");
        
        for (int i = 0; i < itemName.size(); i++) {
        	if (dropItem() == true) {
        		//ArmorItemManager.dropItem((String) itemName.get(i), loc);
        	}
        }
	}
	
	/**
	 * Calculates if an item should drop or not.
	 * 
	 * @return A boolean value. True will drop an item. False will NOT drop items.
	 */
	private static boolean dropItem() {
		Random rand = new Random();
		
		int randomNum = rand.nextInt() * 100 + 1;
		
		if (randomNum > dropPercentage) { 
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This creates the configuration file that will hold data to save loot table information.
	 */
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
}
