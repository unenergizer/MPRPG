package com.minepile.mprpg.items;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;

public class LootTableMobManager {

	//setup instance variables
	public static MPRPG plugin;
	static LootTableMobManager mobDropTablesManagerInstance = new LootTableMobManager();
	static String LootTableFilePath = "plugins/MPRPG/items/LootTableMobs.yml";

	//Drop percentage
	private static double dropPercentage = 2.5;

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

	/**
	 * Drops random loot from the configuration file.
	 * 
	 * @param lootTable The specific loot table that contains items to drop
	 * @param loc The location that items should drop in the World
	 */
	public static void toggleLootTableDrop(String lootTable, Location loc) {

		List<String> armorItems = (List<String>) lootTableConfig.getList(lootTable + ".armorItem");
		List<String> weaponItems = (List<String>) lootTableConfig.getList(lootTable + ".weaponItem");

		//Drop armor
		if (armorItems != null) {
			for (int i = 0; i < armorItems.size(); i++) {
				if (dropItem() == true) {
					ItemStack armor = ArmorItemManager.makeItem((String) armorItems.get(i));

					//Generate drops
					Bukkit.getWorld("world").dropItemNaturally(loc, armor);

				}
			}
		}
		//Drop weapons
		if (weaponItems != null) {
			for (int i = 0; i < weaponItems.size(); i++) {
				if (dropItem() == true) {
					
					ItemStack weapon = WeaponItemManager.makeItem((String) weaponItems.get(i));

					//Generate drops
					Bukkit.getWorld("world").dropItemNaturally(loc, weapon);

				}
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

		double randomNum = rand.nextDouble() * 100;
		if (randomNum <= dropPercentage) { 
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
		lootTableConfig.set("junk01.weaponItem", "testWeaponDrop");
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
