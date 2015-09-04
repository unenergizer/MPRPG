package com.minepile.mprpg.items;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.world.BlockRegenerationManager;

public class LootTableChestManager {

	//setup instance variables
	public static MPRPG plugin;
	static LootTableChestManager LootTableChestManagerInstance = new LootTableChestManager();
	static String LootTableFilePath = "plugins/MPRPG/items/LootTableChests.yml";

	//Configuration file that holds currency information.
	private static File configFile;
	private static FileConfiguration lootTableConfig;

	//Protect inventory hashmap.
	private static HashMap<UUID, Location> inventoryOpened = new HashMap<UUID, Location>();

	//Drop percentage
	private static double dropPercentage = 2.5;

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

	public static void playerOpenedLootChest(Player player, Location location) {
		UUID id = player.getUniqueId();
		inventoryOpened.put(id, location);
	}

	public static void playerClosedLootChest(Player player) {
		UUID id = player.getUniqueId();
		inventoryOpened.remove(id);
	}

	public static Location getOpenedLootChestLocation(Player player) {
		UUID id = player.getUniqueId();
		return inventoryOpened.get(id);
	}

	public static boolean isChestEmpty(Inventory inv) {
		int size = inv.getSize();
		int air = 0;

		for (ItemStack item : inv.getContents()) {
			if (item == null) {
				air++;
			} else if (item.getType().equals(Material.AIR)) {
				air++;
			}
		}

		if (size == air) {
			return true;
		} else {
			return false;
		}
	}

	public static void toggleChestRespawn(Inventory inv, Location location) {
		if (isChestEmpty(inv) == true) {
			if (inv != null && location != null) {
				//Play sound to show block is being respanwed.
				Bukkit.getWorld("world").playSound(location, Sound.ITEM_BREAK, .8f, .8f);

				//Play a particle effect.
				Bukkit.getWorld("world").playEffect(location, Effect.LAVA_POP, 0);;
				
				//Setup the broken chest to be regenerated.
				BlockRegenerationManager.setBlock(Material.CHEST, Material.AIR, location);
			}
		}
	}

	public static void toggleChestLoot(Inventory inv) {
		if (isChestEmpty(inv) == true) {
			String lootTable = "junk01";

			ArrayList armorItems = (ArrayList) lootTableConfig.getList(lootTable + ".armorItem");
			ArrayList weaponItems = (ArrayList) lootTableConfig.getList(lootTable + ".weaponItem");
			List<String> consumableItems = (List<String>) lootTableConfig.getList(lootTable + ".consumableItem");
			List<String> miscItems = (List<String>) lootTableConfig.getList(lootTable + ".miscItem");

			String currencyType = lootTableConfig.getString(lootTable + ".currency");
			int currencyMin = lootTableConfig.getInt(lootTable + ".currencyDropMin");
			int currencyMax = lootTableConfig.getInt(lootTable + ".currencyDropMax");

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
			//Drop consumable items.
			if (consumableItems != null) {
				for (int i = 0; i < weaponItems.size(); i++) {
					if (dropItem() == true) {

						ItemStack consumable = ConsumableItemManager.makeItem((String) consumableItems.get(i));

						//Place weapons on third row in chest.
						inv.setItem(i + 15, consumable);

					}
				}
			}
			//Drop misc items.
			if (miscItems != null) {
				for (int i = 0; i < miscItems.size(); i++) {
					if (dropItem() == true) {

						ItemStack misc = MiscItemManager.makeItem((String) miscItems.get(i));

						//Place weapons on third row in chest.
						inv.setItem(i + 17, misc);

					}
				}
			}
			//Drop money
			if (currencyType != null) {

				Random rand = new Random();
				int range = currencyMax - currencyMin + 1;
				int randomNum =  rand.nextInt(range) + currencyMin;

				if (randomNum != 0) {
					ItemStack money = CurrencyItemManager.makeItem(currencyType);
					money.setAmount(randomNum);
					//Place weapons on third row in chest.
					inv.setItem(23, money);
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
