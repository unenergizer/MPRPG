package com.minepile.mprpg.items;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.items.ItemQualityManager.ItemQuality;
import com.minepile.mprpg.items.ItemTierManager.ItemTier;

public class LootTableMobManager {

	//setup instance variables
	public static MPRPG plugin;
	static LootTableMobManager mobDropTablesManagerInstance = new LootTableMobManager();
	static String LootTableFilePath = "plugins/MPRPG/items/LootTableMobs.yml";

	//Drop percentage
	private static double dropPercentage = 100;

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
		
		@SuppressWarnings("unchecked")
		List<String> armorItems = (List<String>) lootTableConfig.getList(lootTable + ".armorItem");
		@SuppressWarnings("unchecked")
		List<String> weaponItems = (List<String>) lootTableConfig.getList(lootTable + ".weaponItem");
		@SuppressWarnings("unchecked")
		List<String> consumableItems = (List<String>) lootTableConfig.getList(lootTable + ".consumableItem");
		@SuppressWarnings("unchecked")
		List<String> miscItems = (List<String>) lootTableConfig.getList(lootTable + ".miscItem");

		//Random armor drops
		boolean dropRandomArmor = lootTableConfig.getBoolean(lootTable + ".random.armor.enabled");
		ItemTier randArmorTier = ItemTierManager.getItemTierEnum((String) lootTableConfig.get(lootTable + ".random.armor.tier"));
		ItemQuality randArmorQuality = ItemQualityManager.getItemQualityEnum((String) lootTableConfig.get(lootTable + ".random.armor.quality"));
		
		//Random weapon drops
		boolean dropRandomWeapon = lootTableConfig.getBoolean(lootTable + ".random.weapon.enabled");
		ItemTier randWeaponTier = ItemTierManager.getItemTierEnum((String) lootTableConfig.get(lootTable + ".random.weapon.tier"));
		ItemQuality randWeaponQuality = ItemQualityManager.getItemQualityEnum((String) lootTableConfig.get(lootTable + ".random.weapon.quality"));

		//Currency drops
		String currencyType = lootTableConfig.getString(lootTable + ".currency");
		int currencyMin = lootTableConfig.getInt(lootTable + ".currencyDropMin");
		int currencyMax = lootTableConfig.getInt(lootTable + ".currencyDropMax");

		//Drops custom armor.
		if (armorItems != null) {
			for (int i = 0; i < armorItems.size(); i++) {
				if (dropItem() == true) {
					ItemStack armor = ArmorItemManager.makeItem((String) armorItems.get(i));

					//Generate drops
					Bukkit.getWorld("world").dropItemNaturally(loc, armor);

				}
			}
		}		
		//Drops random armor.
		if (dropRandomArmor == true) {
			if (dropItem() == true) {
				double randNumber = Math.random() * 100;
				
				if (randArmorTier.equals(ItemTier.T1)) {
					if (randNumber <= 25) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.LEATHER_BOOTS, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					} else if (randNumber > 25 && randNumber <= 50) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					} else if (randNumber > 50 && randNumber <= 75) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.LEATHER_HELMET, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					} else if (randNumber > 75 && randNumber <= 100) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					}
				} else if (randArmorTier.equals(ItemTier.T2)) {
					if (randNumber <= 25) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.LEATHER_BOOTS, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					} else if (randNumber > 25 && randNumber <= 50) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					} else if (randNumber > 50 && randNumber <= 75) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.LEATHER_HELMET, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					} else if (randNumber > 75 && randNumber <= 100) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					}
					
				} else if (randArmorTier.equals(ItemTier.T3)) {
					if (randNumber <= 25) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.CHAINMAIL_BOOTS, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					} else if (randNumber > 25 && randNumber <= 50) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					} else if (randNumber > 50 && randNumber <= 75) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.CHAINMAIL_HELMET, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					} else if (randNumber > 75 && randNumber <= 100) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					}
					
				} else if (randArmorTier.equals(ItemTier.T4)) {
					if (randNumber <= 25) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.IRON_BOOTS, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					} else if (randNumber > 25 && randNumber <= 50) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.IRON_CHESTPLATE, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					} else if (randNumber > 50 && randNumber <= 75) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.IRON_HELMET, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					} else if (randNumber > 75 && randNumber <= 100) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.IRON_LEGGINGS, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					}
					
				} else if (randArmorTier.equals(ItemTier.T5)) {
					if (randNumber <= 25) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.DIAMOND_BOOTS, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					} else if (randNumber > 25 && randNumber <= 50) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.DIAMOND_CHESTPLATE, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					} else if (randNumber > 50 && randNumber <= 75) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.DIAMOND_HELMET, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					} else if (randNumber > 75 && randNumber <= 100) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.DIAMOND_LEGGINGS, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					}
					
				} else if (randArmorTier.equals(ItemTier.T6)) {
					if (randNumber <= 25) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.GOLD_BOOTS, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					} else if (randNumber > 25 && randNumber <= 50) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.GOLD_CHESTPLATE, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					} else if (randNumber > 50 && randNumber <= 75) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.GOLD_HELMET, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					} else if (randNumber > 75 && randNumber <= 100) {
						ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.GOLD_LEGGINGS, 1), randArmorTier, randArmorQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randArmor);
					}
				}
			}
		}
		//Drop custom weapons.
		if (weaponItems != null) {
			for (int i = 0; i < weaponItems.size(); i++) {
				if (dropItem() == true) {

					ItemStack weapon = WeaponItemManager.makeItem((String) weaponItems.get(i));

					//Generate drops
					Bukkit.getWorld("world").dropItemNaturally(loc, weapon);

				}
			}
		}		
		//Drops random weapon.
		if (dropRandomWeapon == true) {
			if (dropItem() == true) {
				//ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.GOLD_SWORD, 1), randWeaponTier, randWeaponQuality, false);

				//Generate drops
				//Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
				
				double randNumber = Math.random() * 100;
				
				if (randWeaponTier.equals(ItemTier.T1)) {
					if (randNumber <= 25) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.WOOD_AXE, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					} else if (randNumber > 25 && randNumber <= 50) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.WOOD_SPADE, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					} else if (randNumber > 50 && randNumber <= 75) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.WOOD_SWORD, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					} else if (randNumber > 75 && randNumber <= 100) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.STICK, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					}
				} else if (randWeaponTier.equals(ItemTier.T2)) {
					if (randNumber <= 25) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.STONE_AXE, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					} else if (randNumber > 25 && randNumber <= 50) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.STONE_SPADE, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					} else if (randNumber > 50 && randNumber <= 75) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.STONE_SWORD, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					} else if (randNumber > 75 && randNumber <= 100) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.STICK, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					}
					
				} else if (randWeaponTier.equals(ItemTier.T3)) {
					if (randNumber <= 25) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.STONE_AXE, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					} else if (randNumber > 25 && randNumber <= 50) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.STONE_SPADE, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					} else if (randNumber > 50 && randNumber <= 75) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.STONE_SWORD, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					} else if (randNumber > 75 && randNumber <= 100) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.BONE, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					}
					
				} else if (randWeaponTier.equals(ItemTier.T4)) {
					if (randNumber <= 25) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.IRON_AXE, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					} else if (randNumber > 25 && randNumber <= 50) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.IRON_SPADE, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					} else if (randNumber > 50 && randNumber <= 75) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.IRON_SWORD, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					} else if (randNumber > 75 && randNumber <= 100) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.BONE, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					}
					
				} else if (randWeaponTier.equals(ItemTier.T5)) {
					if (randNumber <= 25) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.DIAMOND_AXE, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					} else if (randNumber > 25 && randNumber <= 50) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.DIAMOND_SPADE, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					} else if (randNumber > 50 && randNumber <= 75) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.DIAMOND_SWORD, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					} else if (randNumber > 75 && randNumber <= 100) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.BLAZE_ROD, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					}
					
				} else if (randWeaponTier.equals(ItemTier.T6)) {
					if (randNumber <= 25) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.GOLD_AXE, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					} else if (randNumber > 25 && randNumber <= 50) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.GOLD_SPADE, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					} else if (randNumber > 50 && randNumber <= 75) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.GOLD_SWORD, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					} else if (randNumber > 75 && randNumber <= 100) {
						ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.BLAZE_ROD, 1), randWeaponTier, randWeaponQuality, false);
						Bukkit.getWorld("world").dropItemNaturally(loc, randWeapon);
					}
				}
			}
		}
		//Drop Consumable items.
		if (consumableItems != null) {
			for (int i = 0; i < consumableItems.size(); i++) {
				if (dropItem() == true) {

					ItemStack consumable = ConsumableItemManager.makeItem((String) consumableItems.get(i));

					//Generate drops
					Bukkit.getWorld("world").dropItemNaturally(loc, consumable);

				}
			}
		}
		//Drop misc items.
		if (miscItems != null) {
			for (int i = 0; i < miscItems.size(); i++) {
				if (dropItem() == true) {

					ItemStack misc = MiscItemManager.makeItem((String) miscItems.get(i));

					//Generate drops
					Bukkit.getWorld("world").dropItemNaturally(loc, misc);

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

				//Generate drops
				Bukkit.getWorld("world").dropItemNaturally(loc, money);
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
		lootTableConfig.set("junk01.consumableItem", "testConsumableDrop");
		lootTableConfig.set("junk01.random.armor.enabled", true);
		lootTableConfig.set("junk01.random.armor.tier", "T1");
		lootTableConfig.set("junk01.random.armor.quality", "JUNK");
		lootTableConfig.set("junk01.random.weapon.enabled", false);
		lootTableConfig.set("junk01.random.weapon.tier", "T1");
		lootTableConfig.set("junk01.random.weapon.quality", "JUNK");
		lootTableConfig.set("junk01.currency", "copper");
		lootTableConfig.set("junk01.currencyDropMin", 0);
		lootTableConfig.set("junk01.currencyDropMax", 2);

		try {
			lootTableConfig.save(configFile);	//Save the file.
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
