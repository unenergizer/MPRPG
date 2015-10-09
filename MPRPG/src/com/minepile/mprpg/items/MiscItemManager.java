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
import com.minepile.mprpg.util.StringAlignUtil;
import com.minepile.mprpg.util.StringAlignUtil.Alignment;

public class MiscItemManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static MiscItemManager itemManagerInstance = new MiscItemManager();
	static String miscItemsFilePath = "plugins/MPRPG/items/MiscItems.yml";
	
	//Configuration file that holds "miscellaneous item" information.
	static File configFile;
	static FileConfiguration miscItemConfig;
	
	//Create instance
	public static MiscItemManager getInstance() {
		return itemManagerInstance;
	}
	
	//Setup MistItemsManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		//If configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(miscItemsFilePath)).exists()){
			createConfig();
        } else {
        	//lets load the configuration file.
        	configFile = new File(miscItemsFilePath);
        	miscItemConfig =  YamlConfiguration.loadConfiguration(configFile);
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
		configFile = new File(miscItemsFilePath);
		miscItemConfig =  YamlConfiguration.loadConfiguration(configFile);

		String name = itemName.replaceAll("_", " ");
		int itemNumber = miscItemConfig.getInt(itemName + ".itemId");
		String type = miscItemConfig.getString(itemName + ".itemType");
		List<String> itemDescription = (List<String>) miscItemConfig.getList(itemName + ".itemDescription");

		/////////////////////
		/// SET ITEM LORE ///
		/////////////////////
		Material itemMaterial = ItemGeneratorManager.convertItemIdToMaterial(itemNumber);
		ItemStack item = new ItemStack(itemMaterial, 1);
		ItemMeta im = item.getItemMeta();
		
		//Set the items Name
		im.setDisplayName(ChatColor.WHITE + name);

		//Set the item lore
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + type);	//Define the quality of item.
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
	 * This creates the configuration file that will hold data to save "miscellaneous item" information.
	 */
    private static void createConfig() {
    	
        configFile = new File(miscItemsFilePath);
        miscItemConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        configFile = new File(miscItemsFilePath);
        miscItemConfig =  YamlConfiguration.loadConfiguration(configFile);
        //set copper currency
        miscItemConfig.set("TNT", "");
        miscItemConfig.set("TNT.itemId", 46);
        miscItemConfig.set("TNT.itemType", "Equipment");
        miscItemConfig.set("TNT.itemDescription", "This is a test item.");
        
        try {
        	miscItemConfig.save(configFile);	//Save the file.
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
