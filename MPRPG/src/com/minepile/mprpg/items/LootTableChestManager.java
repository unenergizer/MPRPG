package com.minepile.mprpg.items;

import java.io.File;
import java.io.IOException;
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
import com.minepile.mprpg.items.ItemQualityManager.ItemQuality;
import com.minepile.mprpg.items.ItemTierManager.ItemTier;
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

	@SuppressWarnings("unchecked")
	public static void toggleChestLoot(Inventory inv) {
		if (isChestEmpty(inv) == true) {
			String lootTable = "junk01";

			List<String> consumableItems = (List<String>) lootTableConfig.getList(lootTable + ".consumableItem");
			List<String> miscItems = (List<String>) lootTableConfig.getList(lootTable + ".miscItem");
			
			//Random armor drops
			boolean dropRandomArmor = lootTableConfig.getBoolean(lootTable + ".random.armor.enabled");
			ItemTier randArmorTier = ItemTierManager.getItemTierEnum((String) lootTableConfig.get(lootTable + ".random.armor.tier"));
			ItemQuality randArmorQuality = ItemQualityManager.getItemQualityEnum((String) lootTableConfig.get(lootTable + ".random.armor.quality"));
			
			//Random weapon drops
			boolean dropRandomWeapon = lootTableConfig.getBoolean(lootTable + ".random.weapon.enabled");
			ItemTier randWeaponTier = ItemTierManager.getItemTierEnum((String) lootTableConfig.get(lootTable + ".random.weapon.tier"));
			ItemQuality randWeaponQuality = ItemQualityManager.getItemQualityEnum((String) lootTableConfig.get(lootTable + ".random.weapon.quality"));
			
			//Currency Drops
			String currencyType = lootTableConfig.getString(lootTable + ".currency");
			int currencyMin = lootTableConfig.getInt(lootTable + ".currencyDropMin");
			int currencyMax = lootTableConfig.getInt(lootTable + ".currencyDropMax");
			
			//Random slots
			int smallChest = 27;
			int randArmorSlot = (int) ((Math.random() * smallChest) - 1);
			int randWeaponSlot = (int) ((Math.random() * smallChest) - 1);
			int randConsumableSlot = (int) ((Math.random() * smallChest) - 1);
			int randMiscSlot = (int) ((Math.random() * smallChest) - 1);
			int randCurrencySlot = (int) ((Math.random() * smallChest) - 1);
			
			//Drops random armor.
			if (dropRandomArmor == true) {
				if (dropItem() == true) {
					double randNumber = Math.random() * 100;
					
					if (randArmorTier.equals(ItemTier.T1)) {
						if (randNumber <= 25) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.LEATHER_BOOTS, 1), randArmorTier, randArmorQuality, false);
							
							inv.setItem(randArmorSlot, randArmor);
						} else if (randNumber > 25 && randNumber <= 50) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						} else if (randNumber > 50 && randNumber <= 75) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.LEATHER_HELMET, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						} else if (randNumber > 75 && randNumber <= 100) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						}
					} else if (randArmorTier.equals(ItemTier.T2)) {
						if (randNumber <= 25) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.LEATHER_BOOTS, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						} else if (randNumber > 25 && randNumber <= 50) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						} else if (randNumber > 50 && randNumber <= 75) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.LEATHER_HELMET, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						} else if (randNumber > 75 && randNumber <= 100) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						}
						
					} else if (randArmorTier.equals(ItemTier.T3)) {
						if (randNumber <= 25) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.CHAINMAIL_BOOTS, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						} else if (randNumber > 25 && randNumber <= 50) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						} else if (randNumber > 50 && randNumber <= 75) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.CHAINMAIL_HELMET, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						} else if (randNumber > 75 && randNumber <= 100) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						}
						
					} else if (randArmorTier.equals(ItemTier.T4)) {
						if (randNumber <= 25) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.IRON_BOOTS, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						} else if (randNumber > 25 && randNumber <= 50) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.IRON_CHESTPLATE, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						} else if (randNumber > 50 && randNumber <= 75) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.IRON_HELMET, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						} else if (randNumber > 75 && randNumber <= 100) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.IRON_LEGGINGS, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						}
						
					} else if (randArmorTier.equals(ItemTier.T5)) {
						if (randNumber <= 25) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.DIAMOND_BOOTS, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						} else if (randNumber > 25 && randNumber <= 50) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.DIAMOND_CHESTPLATE, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						} else if (randNumber > 50 && randNumber <= 75) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.DIAMOND_HELMET, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						} else if (randNumber > 75 && randNumber <= 100) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.DIAMOND_LEGGINGS, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						}
						
					} else if (randArmorTier.equals(ItemTier.T6)) {
						if (randNumber <= 25) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.GOLD_BOOTS, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						} else if (randNumber > 25 && randNumber <= 50) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.GOLD_CHESTPLATE, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						} else if (randNumber > 50 && randNumber <= 75) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.GOLD_HELMET, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						} else if (randNumber > 75 && randNumber <= 100) {
							ItemStack randArmor = RandomItemFactory.createArmor(new ItemStack(Material.GOLD_LEGGINGS, 1), randArmorTier, randArmorQuality, false);
							inv.setItem(randArmorSlot, randArmor);
						}
					}
				}
			}	
			//Drops random weapon.
			if (dropRandomWeapon == true) {
				if (dropItem() == true) {
					//ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.GOLD_SWORD, 1), randWeaponTier, randWeaponQuality, false);

					//Generate drops
					//inv.setItem(randArmorSlot, randArmor);
					
					double randNumber = Math.random() * 100;
					
					if (randWeaponTier.equals(ItemTier.T1)) {
						if (randNumber <= 25) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.WOOD_AXE, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						} else if (randNumber > 25 && randNumber <= 50) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.WOOD_SPADE, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						} else if (randNumber > 50 && randNumber <= 75) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.WOOD_SWORD, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						} else if (randNumber > 75 && randNumber <= 100) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.STICK, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						}
					} else if (randWeaponTier.equals(ItemTier.T2)) {
						if (randNumber <= 25) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.STONE_AXE, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						} else if (randNumber > 25 && randNumber <= 50) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.STONE_SPADE, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						} else if (randNumber > 50 && randNumber <= 75) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.STONE_SWORD, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						} else if (randNumber > 75 && randNumber <= 100) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.STICK, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						}
						
					} else if (randWeaponTier.equals(ItemTier.T3)) {
						if (randNumber <= 25) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.STONE_AXE, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						} else if (randNumber > 25 && randNumber <= 50) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.STONE_SPADE, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						} else if (randNumber > 50 && randNumber <= 75) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.STONE_SWORD, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						} else if (randNumber > 75 && randNumber <= 100) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.BONE, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						}
						
					} else if (randWeaponTier.equals(ItemTier.T4)) {
						if (randNumber <= 25) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.IRON_AXE, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						} else if (randNumber > 25 && randNumber <= 50) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.IRON_SPADE, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						} else if (randNumber > 50 && randNumber <= 75) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.IRON_SWORD, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						} else if (randNumber > 75 && randNumber <= 100) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.BONE, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						}
						
					} else if (randWeaponTier.equals(ItemTier.T5)) {
						if (randNumber <= 25) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.DIAMOND_AXE, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						} else if (randNumber > 25 && randNumber <= 50) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.DIAMOND_SPADE, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						} else if (randNumber > 50 && randNumber <= 75) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.DIAMOND_SWORD, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						} else if (randNumber > 75 && randNumber <= 100) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.BLAZE_ROD, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						}
						
					} else if (randWeaponTier.equals(ItemTier.T6)) {
						if (randNumber <= 25) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.GOLD_AXE, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						} else if (randNumber > 25 && randNumber <= 50) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.GOLD_SPADE, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						} else if (randNumber > 50 && randNumber <= 75) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.GOLD_SWORD, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						} else if (randNumber > 75 && randNumber <= 100) {
							ItemStack randWeapon = RandomItemFactory.createWeapon(new ItemStack(Material.BLAZE_ROD, 1), randWeaponTier, randWeaponQuality, false);
							inv.setItem(randWeaponSlot, randWeapon);
						}
					}
				}
			}
			//Drop consumable items.
			if (consumableItems != null) {
				for (int i = 0; i < consumableItems.size(); i++) {
					if (dropItem() == true) {

						ItemStack consumable = ConsumableItemManager.makeItem((String) consumableItems.get(i));

						//Place weapons on third row in chest.
						inv.setItem(randConsumableSlot, consumable);

					}
				}
			}
			//Drop misc items.
			if (miscItems != null) {
				for (int i = 0; i < miscItems.size(); i++) {
					if (dropItem() == true) {

						ItemStack misc = MiscItemManager.makeItem((String) miscItems.get(i));

						//Place weapons on third row in chest.
						inv.setItem(randMiscSlot, misc);

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
					inv.setItem(randCurrencySlot, money);
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
		lootTableConfig.set("junk01.currency", "copper");
		lootTableConfig.set("junk01.currencyDropMin", 0);
		lootTableConfig.set("junk01.currencyDropMax", 10);
		lootTableConfig.set("junk01.random.armor.enabled", true);
		lootTableConfig.set("junk01.random.armor.tier", "T1");
		lootTableConfig.set("junk01.random.armor.quality", "JUNK");
		lootTableConfig.set("junk01.random.weapon.enabled", true);
		lootTableConfig.set("junk01.random.weapon.tier", "T1");
		lootTableConfig.set("junk01.random.weapon.quality", "JUNK");

		try {
			lootTableConfig.save(configFile);	//Save the file.
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
