package com.minepile.mprpg.items;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
	static int dropPercentage = 10;

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

	public static void toggleChestLoot(Player player, Inventory inv) {

		if (inv.getContents().length <= 0) {
			String lootTable = "junk01";

			ArrayList armorItems = (ArrayList) lootTableConfig.getList(lootTable + ".armorItem");
			ArrayList weaponItems = (ArrayList) lootTableConfig.getList(lootTable + ".weaponItem");

			//Drop armor
			if (armorItems != null) {
				for (int i = 0; i < armorItems.size(); i++) {
					if (dropItem() == true) {

						ItemStack armor = ArmorItemManager.makeItem((String) armorItems.get(i));

						//Place items in chest
						inv.setItem(i, armor);

					}
				}
			}
			//Drop weapons
			if (weaponItems != null) {
				for (int i = 0; i < weaponItems.size(); i++) {
					if (dropItem() == true) {

						ItemStack weapon = WeaponItemManager.makeItem((String) weaponItems.get(i));

						//Place weapons on third row in chest.
						inv.setItem(i + 19, weapon);

					}
				}
			}
		} else {
			player.sendMessage("This chest has items in it!");
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
