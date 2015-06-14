package com.minepile.mprpg.items;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import com.minepile.mprpg.equipment.ItemQualityManager;
import com.minepile.mprpg.equipment.ItemQualityManager.ItemQuality;
import com.minepile.mprpg.equipment.ItemTierManager;
import com.minepile.mprpg.equipment.ItemTierManager.ItemTier;

public class ArmorItemManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static ArmorItemManager armorItemManagerInstance = new ArmorItemManager();
	static String armorItemsFilePath = "plugins/MPRPG/items/Armor.yml";
	
	//Configuration file that holds armor information.
	FileConfiguration armorItemConfig;
	
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
	public static void dropItem(String itemName, Location loc) {
		
		/////////////////////////////
		/// GRAB ITEM FROM CONFIG ///
		/////////////////////////////
        File configFile = new File(armorItemsFilePath);
        FileConfiguration armorItemsConfig =  YamlConfiguration.loadConfiguration(configFile);
        //armorItemConfig.get(value);
        
        ItemTier tier = ItemTierManager.getItemTierEnum(armorItemsConfig.getString(itemName + ".itemTier"));
        ItemQuality quality = ItemQualityManager.getItemQualityEnum(armorItemsConfig.getString(itemName + ".itemQuality"));
        String itemDescription = armorItemsConfig.getString(itemName + ".itemDescription");
        int itemNumber = armorItemsConfig.getInt(itemName + ".item");
        int hpMin = armorItemsConfig.getInt(itemName + ".hpMin");
        int hpMax = armorItemsConfig.getInt(itemName + ".hpMax");
        int hpRegenMin = armorItemsConfig.getInt(itemName + ".hpRegenMin");
        int hpRegenMax = armorItemsConfig.getInt(itemName + ".hpRegenMax");
        //int damageMin = armorItemsConfig.getInt(itemName + ".damageMin");
        //int damageMax = armorItemsConfig.getInt(itemName + ".damageMax");
        int staminaMin = armorItemsConfig.getInt(itemName + ".staminaMin");
        int staminaMax = armorItemsConfig.getInt(itemName + ".staminaMax");
        int dodgeMin = armorItemsConfig.getInt(itemName + ".dodgeMin");
        int dodgeMax = armorItemsConfig.getInt(itemName + ".dodgeMax");
        int reflectionMin = armorItemsConfig.getInt(itemName + ".reflectionMin");
        int reflectionMax = armorItemsConfig.getInt(itemName + ".reflectionMax");
        int blockMin = armorItemsConfig.getInt(itemName + ".blockMin");
        int blockMax = armorItemsConfig.getInt(itemName + ".blockMax");
        int thornsMin = armorItemsConfig.getInt(itemName + ".thornsMin");
        int thornsMax = armorItemsConfig.getInt(itemName + ".thornsMax");
        int itemFindMin = armorItemsConfig.getInt(itemName + ".itemFindMin");
        int itemFindMax = armorItemsConfig.getInt(itemName + ".itemFindMax");
        int goldFindMin = armorItemsConfig.getInt(itemName + ".goldFindMin");
        int goldFindMax = armorItemsConfig.getInt(itemName + ".goldFindMax");
        
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
		boolean cointainsLore = im.hasLore();
		
		if (cointainsLore == false) {
			
			//String itemName = "Armor test";
			String nameFormatting = ItemQualityManager.getStringFormatting(quality);
			String itemQuality =  ItemQualityManager.getItemQualityString(quality);
			
			//Set the items Name
			im.setDisplayName(nameFormatting + itemName);
			
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
			Bukkit.getWorld("world").dropItemNaturally(loc, item);
		}
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
	
	//This creates the configuration file that will hold data to save armor information.
    private static void createConfig() {
    	
        File configFile = new File(armorItemsFilePath);
        FileConfiguration armorItemsConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        armorItemsConfig.set("testArmorDrop", "testArmorDrop");
        armorItemsConfig.set("testArmorDrop" + ".itemTier", "t1");
        armorItemsConfig.set("testArmorDrop" + ".itemQuality", "junk");
        armorItemsConfig.set("testArmorDrop" + ".itemDescription", "This is a custom item that came from the config!");
        armorItemsConfig.set("testArmorDrop" + ".item", 298);
        armorItemsConfig.set("testArmorDrop" + ".hpMin", 1);
        armorItemsConfig.set("testArmorDrop" + ".hpMax", 30);
        armorItemsConfig.set("testArmorDrop" + ".hpRegenMin", 0);
        armorItemsConfig.set("testArmorDrop" + ".hpRegenMax", 3);
        //armorItemsConfig.set("testArmorDrop" + ".damageMin", 0);
        //armorItemsConfig.set("testArmorDrop" + ".damageMax", 1);
        armorItemsConfig.set("testArmorDrop" + ".staminaMin", 0);
        armorItemsConfig.set("testArmorDrop" + ".staminaMax", 1);
        armorItemsConfig.set("testArmorDrop" + ".dodgeMin", 0);
        armorItemsConfig.set("testArmorDrop" + ".dodgeMax", 1);
        armorItemsConfig.set("testArmorDrop" + ".reflectionMin", 0);
        armorItemsConfig.set("testArmorDrop" + ".reflectionMax", 1);
        armorItemsConfig.set("testArmorDrop" + ".blockMin", 0);
        armorItemsConfig.set("testArmorDrop" + ".blockMax", 1);
        armorItemsConfig.set("testArmorDrop" + ".thornsMin", 0);
        armorItemsConfig.set("testArmorDrop" + ".thornsMax", 1);
        armorItemsConfig.set("testArmorDrop" + ".itemFindMin", 0);
        armorItemsConfig.set("testArmorDrop" + ".itemFindMax", 1);
        armorItemsConfig.set("testArmorDrop" + ".goldFindMin", 0);
        armorItemsConfig.set("testArmorDrop" + ".goldFindMax", 1);

        try {
        	armorItemsConfig.save(configFile);	//Save the file.
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
