package com.minepile.mprpg.items;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.items.ItemQualityManager.ItemQuality;
import com.minepile.mprpg.items.ItemTierManager.ItemTier;

public class WeaponItemManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static WeaponItemManager weaponItemManagerInstance = new WeaponItemManager();
	static String weaponItemsFilePath = "plugins/MPRPG/items/Weapons.yml";
	
	//Configuration file that holds weapon information.
	static File configFile;
	static FileConfiguration weaponItemConfig;
	
	//Create instance
	public static WeaponItemManager getInstance() {
		return weaponItemManagerInstance;
	}
	
	//Setup Weapon item manager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		//If configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(weaponItemsFilePath)).exists()){
			createConfig();
        } else {
        	//lets load the configuration file.
        	configFile = new File(weaponItemsFilePath);
        	weaponItemConfig =  YamlConfiguration.loadConfiguration(configFile);
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
		configFile = new File(weaponItemsFilePath);
		weaponItemConfig =  YamlConfiguration.loadConfiguration(configFile);

		String name = itemName.replaceAll("_", " ");
		ItemTier tier = ItemTierManager.getItemTierEnum(weaponItemConfig.getString(itemName + ".itemTier"));
		ItemQuality quality = ItemQualityManager.getItemQualityEnum(weaponItemConfig.getString(itemName + ".itemQuality"));
		String itemDescription = weaponItemConfig.getString(itemName + ".itemDescription");
		int itemNumber = weaponItemConfig.getInt(itemName + ".item");
		int iceMin = weaponItemConfig.getInt(itemName + ".iceMin");
		int iceMax = weaponItemConfig.getInt(itemName + ".iceMax");
		int blindMin = weaponItemConfig.getInt(itemName + ".blindMin");
		int blindMax = weaponItemConfig.getInt(itemName + ".blindMax");
		int fireMin = weaponItemConfig.getInt(itemName + ".fireMin");
		int fireMax = weaponItemConfig.getInt(itemName + ".fireMax");
		int knockbackMin = weaponItemConfig.getInt(itemName + ".knockbackMin");
		int knockbackMax = weaponItemConfig.getInt(itemName + ".knockbackMax");
		int lifestealMin = weaponItemConfig.getInt(itemName + ".lifestealMin");
		int lifestealMax = weaponItemConfig.getInt(itemName + ".lifestealMax");
		int armorPenentrationMin = weaponItemConfig.getInt(itemName + ".armorPenentrationMin");
		int armorPenentrationMax = weaponItemConfig.getInt(itemName + ".armorPenentrationMax");
		int critMin = weaponItemConfig.getInt(itemName + ".criticalHitChanceMin");
		int critMax = weaponItemConfig.getInt(itemName + ".criticalHitChanceMax");
		int poisonMin = weaponItemConfig.getInt(itemName + ".poisonMin");
		int poisonMax = weaponItemConfig.getInt(itemName + ".poisonMax");
		

		////////////////////////////
		/// Calculate Item Stats ///
		////////////////////////////
		int ice = randomInt(iceMin, iceMax);
		int blind = randomInt(blindMin, blindMax);
		int fire = randomInt(fireMin, fireMax);
		int knockback = randomInt(knockbackMin, knockbackMax);
		int lifesteal = randomInt(lifestealMin, lifestealMax);
		int armorPenentration = randomInt(armorPenentrationMin, armorPenentrationMax);
		int crit = randomInt(critMin, critMax);
		int poison = randomInt(poisonMin, poisonMax);


		/////////////////////
		/// SET ITEM LORE ///
		/////////////////////
		Material itemMaterial = ItemGeneratorManager.convertItemIdToMaterial(itemNumber);
		ItemStack item = new ItemStack(itemMaterial, 1);
		ItemMeta im = item.getItemMeta();

		String nameFormatting = ItemQualityManager.getStringFormatting(quality);
		String itemQuality =  ItemQualityManager.getItemQualityString(quality);

		//Set the items Name
		im.setDisplayName(nameFormatting + name);

		//Set the item lore
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + tier + ": " + itemQuality);	//Define the quality of item.
		lore.add("");																		//create blank space

		if (ice > 0) 				{ lore.add(ChatColor.RED + "+" + ice + " Ice Damage"); }
		if (blind > 0) 				{ lore.add(ChatColor.RED + "+" + blind + " Blindness"); }
		if (fire > 0) 				{ lore.add(ChatColor.RED + "+" + fire + " Fire Damage"); }
		if (knockback > 0) 			{ lore.add(ChatColor.RED + "+" + knockback + " Knockback"); }
		if (lifesteal > 0) 			{ lore.add(ChatColor.RED + "+" + lifesteal + " Life Steal"); }
		if (armorPenentration > 0) 	{ lore.add(ChatColor.RED + "+" + armorPenentration + " Armor Penentration"); }
		if (crit > 0) 				{ lore.add(ChatColor.RED + "+" + crit + " Critical Hit Chance"); }			
		if (poison > 0)				{ lore.add(ChatColor.DARK_PURPLE + "+" + poison + " Poison Damage"); }			

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
	 * This creates the configuration file that will hold data to save weapon information.
	 */
    private static void createConfig() {
    	
        configFile = new File(weaponItemsFilePath);
        weaponItemConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        weaponItemConfig.set("testWeaponDrop", "testWeaponDrop");
		weaponItemConfig.set("testWeaponDrop" + ".itemTier", "t1");
		weaponItemConfig.set("testWeaponDrop" + ".itemQuality", "junk");
		weaponItemConfig.set("testWeaponDrop" + ".itemDescription", "This is a custom item that came from the config!");
		weaponItemConfig.set("testWeaponDrop" + ".item", 268);
		weaponItemConfig.set("testWeaponDrop" + ".iceMin", 1);
		weaponItemConfig.set("testWeaponDrop" + ".iceMax", 30);
		weaponItemConfig.set("testWeaponDrop" + ".blindMin", 1);
		weaponItemConfig.set("testWeaponDrop" + ".blindMax", 30);
		weaponItemConfig.set("testWeaponDrop" + ".fireMin", 1);
		weaponItemConfig.set("testWeaponDrop" + ".fireMax", 30);
		weaponItemConfig.set("testWeaponDrop" + ".knockbackMin", 1);
		weaponItemConfig.set("testWeaponDrop" + ".knockbackMax", 30);
		weaponItemConfig.set("testWeaponDrop" + ".lifestealMin", 1);
		weaponItemConfig.set("testWeaponDrop" + ".lifestealMax", 30);
		weaponItemConfig.set("testWeaponDrop" + ".armorPenentrationMin", 1);
		weaponItemConfig.set("testWeaponDrop" + ".armorPenentrationMax", 30);
		weaponItemConfig.set("testWeaponDrop" + ".criticalHitChanceMin", 1);
		weaponItemConfig.set("testWeaponDrop" + ".criticalHitChanceMax", 30);
		weaponItemConfig.set("testWeaponDrop" + ".poisonMin", 1);
		weaponItemConfig.set("testWeaponDrop" + ".poisonMax", 30);
        
        try {
        	weaponItemConfig.save(configFile);	//Save the file.
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
	
	//TODO:
	/*
	Weapon attributes:
	
	Knockback %
	Fire
	Critical Hit Bonus
	Poison
	Slow
	Blindness
	Life Steal
	Ice
	 */
	
}
