package com.minepile.mprpg.items;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.items.ItemQualityManager.ItemQuality;
import com.minepile.mprpg.items.ItemTierManager.ItemTier;

public class ArmorItemManager {

	//setup instance variables
	public static MPRPG plugin;
	static ArmorItemManager armorItemManagerInstance = new ArmorItemManager();
	static String armorItemsFilePath = "plugins/MPRPG/items/Armor.yml";

	//Configuration file that holds armor information.
	static File configFile;
	static FileConfiguration armorItemConfig;

	//Create instance
	public static ArmorItemManager getInstance() {
		return armorItemManagerInstance;
	}

	//Setup ArmorItemManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		//If configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(armorItemsFilePath)).exists()){
			createConfig();
		} else {
			//lets load the configuration file.
			File configFile = new File(armorItemsFilePath);
			armorItemConfig =  YamlConfiguration.loadConfiguration(configFile);
		}
	}	

	/**
	 * Generates the item that needs to be dropped based on the settings from the configuration file.
	 * <p>
	 * This method is currently only for Armor drops.
	 * 
	 * @param itemName the item name specified inside the configuration file for this item type
	 */
	public static ItemStack makeItem(String itemName) {

		/////////////////////////////
		/// GRAB ITEM FROM CONFIG ///
		/////////////////////////////
		configFile = new File(armorItemsFilePath);
		armorItemConfig =  YamlConfiguration.loadConfiguration(configFile);

		String name = itemName.replaceAll("_", " ");
		ItemTier tier = ItemTierManager.getItemTierEnum(armorItemConfig.getString(itemName + ".itemTier"));
		ItemQuality quality = ItemQualityManager.getItemQualityEnum(armorItemConfig.getString(itemName + ".itemQuality"));
		String itemDescription = armorItemConfig.getString(itemName + ".itemDescription");
		int itemNumber = armorItemConfig.getInt(itemName + ".item");
		int hpMin = armorItemConfig.getInt(itemName + ".hpMin");
		int hpMax = armorItemConfig.getInt(itemName + ".hpMax");
		int hpRegenMin = armorItemConfig.getInt(itemName + ".hpRegenMin");
		int hpRegenMax = armorItemConfig.getInt(itemName + ".hpRegenMax");
		//int damageMin = armorItemConfig.getInt(itemName + ".damageMin");
		//int damageMax = armorItemConfig.getInt(itemName + ".damageMax");
		int staminaMin = armorItemConfig.getInt(itemName + ".staminaMin");
		int staminaMax = armorItemConfig.getInt(itemName + ".staminaMax");
		int dodgeMin = armorItemConfig.getInt(itemName + ".dodgeMin");
		int dodgeMax = armorItemConfig.getInt(itemName + ".dodgeMax");
		int reflectionMin = armorItemConfig.getInt(itemName + ".reflectionMin");
		int reflectionMax = armorItemConfig.getInt(itemName + ".reflectionMax");
		int blockMin = armorItemConfig.getInt(itemName + ".blockMin");
		int blockMax = armorItemConfig.getInt(itemName + ".blockMax");
		int thornsMin = armorItemConfig.getInt(itemName + ".thornsMin");
		int thornsMax = armorItemConfig.getInt(itemName + ".thornsMax");
		int itemFindMin = armorItemConfig.getInt(itemName + ".itemFindMin");
		int itemFindMax = armorItemConfig.getInt(itemName + ".itemFindMax");
		int goldFindMin = armorItemConfig.getInt(itemName + ".goldFindMin");
		int goldFindMax = armorItemConfig.getInt(itemName + ".goldFindMax");

		////////////////////////////
		/// Calculate Item Stats ///
		////////////////////////////
		int hp = randomInt(hpMin, hpMax);
		int hpRegen = randomInt(hpRegenMin, hpRegenMax);
		//int damage = randomInt(damageMin, damageMax);
		int stamina = randomInt(staminaMin, staminaMax);
		int dodge = randomInt(dodgeMin, dodgeMax);
		int reflection = randomInt(reflectionMin, reflectionMax);
		int block = randomInt(blockMin, blockMax);
		int thorns = randomInt(thornsMin, thornsMax);
		int itemFind = randomInt(itemFindMin, itemFindMax);
		int goldFind = randomInt(goldFindMin, goldFindMax);


		/////////////////////
		/// SET ITEM LORE ///
		/////////////////////
		Material itemMaterial = ItemGeneratorManager.convertItemIdToMaterial(itemNumber);
		ItemStack item = new ItemStack(itemMaterial, 1);
		ItemMeta im = item.getItemMeta();

		//String itemName = "Armor test";
		String nameFormatting = ItemQualityManager.getStringFormatting(quality);
		String itemQuality =  ItemQualityManager.getItemQualityString(quality);

		//Set the items Name
		im.setDisplayName(nameFormatting + name);

		//Set the item lore
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + tier + ": " + itemQuality);	//Define the quality of item.
		lore.add("");																		//create blank space

		if (hp > 0) 		{ lore.add(ChatColor.RED + "+" + hp + " Health"); }
		if (hpRegen > 0) 	{ lore.add(ChatColor.RED + "+" + hpRegen + " Health Regen"); }
		if (stamina > 0) 	{ lore.add(ChatColor.RED + "+" + stamina + " Stamina"); }
		if (dodge > 0) 		{ lore.add(ChatColor.RED + "+" + dodge + " Dodge"); }
		if (reflection > 0) { lore.add(ChatColor.RED + "+" + reflection + " Reflection"); }
		if (block > 0) 		{ lore.add(ChatColor.RED + "+" + block + " Block"); }
		if (thorns > 0) 	{ lore.add(ChatColor.RED + "+" + thorns + " Thorns"); }			
		if (itemFind > 0)	{ lore.add(ChatColor.DARK_PURPLE + "+" + itemFind + " Item Find"); }			
		if (goldFind > 0)	{ lore.add(ChatColor.GOLD + "+" + goldFind + " Gold Find"); }			

		lore.add(" ");													//create blank space

		//Set the items description
		lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + itemDescription);


		//Set the item lore
		im.setLore(lore);
		//Set the item meta
		item.setItemMeta(im);

		return item;
	}

	/**
	 * Generates a random integer between the min value and the max value.
	 * 
	 * @param min the minimal value
	 * @param max the maximum value
	 * @return a random value between the min value and the max value
	 */
	public static int randomInt(int min, int max) {
		Random rand = new Random();

		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	/**
	 * This creates the configuration file that will hold data to save armor information.
	 */
	private static void createConfig() {

		configFile = new File(armorItemsFilePath);
		armorItemConfig =  YamlConfiguration.loadConfiguration(configFile);

		armorItemConfig.set("testArmorDrop", "testArmorDrop");
		armorItemConfig.set("testArmorDrop" + ".itemTier", "t1");
		armorItemConfig.set("testArmorDrop" + ".itemQuality", "junk");
		armorItemConfig.set("testArmorDrop" + ".itemDescription", "This is a custom item that came from the config!");
		armorItemConfig.set("testArmorDrop" + ".item", 298);
		armorItemConfig.set("testArmorDrop" + ".hpMin", 1);
		armorItemConfig.set("testArmorDrop" + ".hpMax", 30);
		armorItemConfig.set("testArmorDrop" + ".hpRegenMin", 0);
		armorItemConfig.set("testArmorDrop" + ".hpRegenMax", 3);
		//armorItemConfig.set("testArmorDrop" + ".damageMin", 0);
		//armorItemConfig.set("testArmorDrop" + ".damageMax", 1);
		armorItemConfig.set("testArmorDrop" + ".staminaMin", 0);
		armorItemConfig.set("testArmorDrop" + ".staminaMax", 1);
		armorItemConfig.set("testArmorDrop" + ".dodgeMin", 0);
		armorItemConfig.set("testArmorDrop" + ".dodgeMax", 1);
		armorItemConfig.set("testArmorDrop" + ".reflectionMin", 0);
		armorItemConfig.set("testArmorDrop" + ".reflectionMax", 1);
		armorItemConfig.set("testArmorDrop" + ".blockMin", 0);
		armorItemConfig.set("testArmorDrop" + ".blockMax", 1);
		armorItemConfig.set("testArmorDrop" + ".thornsMin", 0);
		armorItemConfig.set("testArmorDrop" + ".thornsMax", 1);
		armorItemConfig.set("testArmorDrop" + ".itemFindMin", 0);
		armorItemConfig.set("testArmorDrop" + ".itemFindMax", 1);
		armorItemConfig.set("testArmorDrop" + ".goldFindMin", 0);
		armorItemConfig.set("testArmorDrop" + ".goldFindMax", 1);

		try {
			armorItemConfig.save(configFile);	//Save the file.
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	//TODO: 
	/*
    Attributes:

    Armor Percentages
	HP Bonus
	HP Regeneration
	Energy/Stamina Regeneration
	Dodge %
	Reflection
	Block %
	Thorns
	Item Find %
	Gold Find %
	 */
}
