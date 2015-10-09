package com.minepile.mprpg.items;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.items.ItemQualityManager.ItemQuality;
import com.minepile.mprpg.items.ItemTierManager.ItemTier;
import com.minepile.mprpg.util.StringAlignUtil;
import com.minepile.mprpg.util.StringAlignUtil.Alignment;

public class ConsumableItemManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static ConsumableItemManager consumableItemManagerInstance = new ConsumableItemManager();
	static String consumableItemsFilePath = "plugins/MPRPG/items/Consumable.yml";
	
	//Configuration file that holds consumable item information.
	static File configFile;
	static FileConfiguration consumableItemConfig;
	
	//Create instance
	public static ConsumableItemManager getInstance() {
		return consumableItemManagerInstance;
	}
	
	//Setup Consumable item
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		//If configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(consumableItemsFilePath)).exists()){
			createConfig();
        } else {
        	//lets load the configuration file.
        	configFile = new File(consumableItemsFilePath);
        	consumableItemConfig =  YamlConfiguration.loadConfiguration(configFile);
        }
	}	
	
	/**
	 * Generates the item that needs to be dropped based on the settings from the configuration file.
	 * 
	 * @param itemName the item name specified inside the configuration file for this item type
	 */
	public static ItemStack makeItem(String itemName) {
		
		//Util to make sure line length isn't really long for descriptions. 
		StringAlignUtil util = new StringAlignUtil(25, Alignment.CENTER);
		
		/////////////////////////////
		/// GRAB ITEM FROM CONFIG ///
		/////////////////////////////
		configFile = new File(consumableItemsFilePath);
		consumableItemConfig =  YamlConfiguration.loadConfiguration(configFile);

		String name = itemName.replaceAll("_", " ");
		int itemNumber = consumableItemConfig.getInt(itemName + ".itemId");
		ItemTier tier = ItemTierManager.getItemTierEnum(consumableItemConfig.getString(itemName + ".itemTier"));
		String type = consumableItemConfig.getString(itemName + ".itemType");
		ItemQuality quality = ItemQualityManager.getItemQualityEnum(consumableItemConfig.getString(itemName + ".itemQuality"));
		List<String> itemDescription = (List<String>) consumableItemConfig.getList(itemName + ".itemDescription");

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
		lore.add(ChatColor.GRAY + "" + type);	//Define the quality of item.
		lore.add("");																		//create blank space		

		//Set the items description
		if (!itemDescription.isEmpty()) {
			for (int i = 0; i < itemDescription.size(); i++) {
				lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + itemDescription.get(i));
			}
		}

		//Set the item lore
		im.setLore(lore);
		//Set the item meta
		item.setItemMeta(im);

		return item;
	}
	
	/**
	 * This creates the configuration file that will hold data to save consumable item information.
	 */
    private static void createConfig() {
    	
        configFile = new File(consumableItemsFilePath);
        consumableItemConfig =  YamlConfiguration.loadConfiguration(configFile);
        //set copper currency
        consumableItemConfig.set("Cooked_Chicken", "Cooked_Chicken");
        consumableItemConfig.set("Cooked_Chicken.itemId", 366);
        consumableItemConfig.set("Cooked_Chicken.itemTier", "T1");
        consumableItemConfig.set("Cooked_Chicken.itemQuality", "Junk");
        consumableItemConfig.set("Cooked_Chicken.itemType", "Food");
        consumableItemConfig.set("Cooked_Chicken.itemDescription", "This is a test item.");
        
        try {
        	consumableItemConfig.save(configFile);	//Save the file.
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
	
}
