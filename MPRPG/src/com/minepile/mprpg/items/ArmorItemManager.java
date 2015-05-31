package com.minepile.mprpg.items;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.minepile.mprpg.MPRPG;

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
	
	//This creates the configuration file that will hold data to save armor information.
    private static void createConfig() {
    	
        File configFile = new File(armorItemsFilePath);
        FileConfiguration armorItemsConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        armorItemsConfig.set("testItem", "testItem");
        armorItemsConfig.set("testItem" + ".itemNameColor", "gray");
        armorItemsConfig.set("testItem" + ".armorType", 298);
        armorItemsConfig.set("testItem" + ".hpMin", 0);
        armorItemsConfig.set("testItem" + ".hpMax", 10);
        armorItemsConfig.set("testItem" + ".hpRegenMin", 0);
        armorItemsConfig.set("testItem" + ".hpRegenMax", 3);
        armorItemsConfig.set("testItem" + ".staminaMin", 0);
        armorItemsConfig.set("testItem" + ".staminaMax", 1);
        armorItemsConfig.set("testItem" + ".dodgeMin", 0);
        armorItemsConfig.set("testItem" + ".dodgeMax", 1);
        armorItemsConfig.set("testItem" + ".reflectionMin", 0);
        armorItemsConfig.set("testItem" + ".reflectionMax", 1);
        armorItemsConfig.set("testItem" + ".blockMin", 0);
        armorItemsConfig.set("testItem" + ".blockMax", 1);
        armorItemsConfig.set("testItem" + ".thornsMin", 0);
        armorItemsConfig.set("testItem" + ".thornsMax", 1);
        armorItemsConfig.set("testItem" + ".itemFindMin", 0);
        armorItemsConfig.set("testItem" + ".itemFindMax", 1);
        armorItemsConfig.set("testItem" + ".goldFindMin", 0);
        armorItemsConfig.set("testItem" + ".goldFindMax", 1);

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

	Example:
	
    itemName: Tattered Helm of the Weak
    itemName.amor = Armor Percentages
	itemName.hpMin = 5
	itemName.hpMax = 10
	itemName.hpRegenMin = 0
	itemName.hpRegenMax = 3
	itemName.staminaRegenMin = 0
	itemName.staminaRegenMax = 3
	itemName.dodgeMin = 0
	itemName.dodgeMax = 1
	itemName.reflectionMin = 0
	itemName.reflectionMax = 1
	itemName.blockMin = 0
	itemName.blockMax = 1
	itemName.thornsMin = 0
	itemName.thornsMax = 3
	itemName.itemFindMin = 0
	itemName.itemFindMax = 0
	itemName.goldFindMin = 0
	itemName.goldFindMax = 1
     
     */
    
    //Creating items from the command line might not be a good idea. Needs 21 arguments...
    /* 
	public static void createNewArmor(Player player, String itemName, String itemNameColor, Material armor, int mobLevel, int mobHP, int runRadius) {
    	
		if (armor.equals(Material.LEATHER_BOOTS) || armor.equals(Material.LEATHER_CHESTPLATE) || 
				armor.equals(Material.LEATHER_HELMET) || armor.equals(Material.LEATHER_LEGGINGS) || 
				armor.equals(Material.CHAINMAIL_BOOTS) || armor.equals(Material.CHAINMAIL_CHESTPLATE) || 
				armor.equals(Material.CHAINMAIL_HELMET) || armor.equals(Material.CHAINMAIL_LEGGINGS) || 
				armor.equals(Material.IRON_BOOTS) || armor.equals(Material.IRON_CHESTPLATE) || 
				armor.equals(Material.IRON_HELMET) || armor.equals(Material.IRON_LEGGINGS) || 
				armor.equals(Material.DIAMOND_BOOTS) || armor.equals(Material.DIAMOND_CHESTPLATE) || 
				armor.equals(Material.DIAMOND_HELMET) || armor.equals(Material.DIAMOND_LEGGINGS) || 
				armor.equals(Material.GOLD_BOOTS) || armor.equals(Material.GOLD_CHESTPLATE) || 
				armor.equals(Material.GOLD_HELMET) || armor.equals(Material.GOLD_LEGGINGS)) {
		
	        File configFile = new File(armorItemsFilePath);
	        FileConfiguration armorItemsConfig =  YamlConfiguration.loadConfiguration(configFile);
	        armorItemsConfig.set(itemName, itemName);
	        armorItemsConfig.set(itemName + ".player", player.getName());
	        armorItemsConfig.set(itemName + ".itemNameColor", itemNameColor);
	        armorItemsConfig.set(itemName + ".armorType", armorId);
	        armorItemsConfig.set(itemName + ".hpMin", x);
	        armorItemsConfig.set(itemName + ".hpMax", x);
	        armorItemsConfig.set(itemName + ".hpRegenMin", x);
	        armorItemsConfig.set(itemName + ".hpRegenMax", x);
	        armorItemsConfig.set(itemName + ".staminaMin", x);
	        armorItemsConfig.set(itemName + ".staminaMax", x);
	        armorItemsConfig.set(itemName + ".dodgeMin", x);
	        armorItemsConfig.set(itemName + ".dodgeMax", x);
	        armorItemsConfig.set(itemName + ".reflectionMin", x);
	        armorItemsConfig.set(itemName + ".reflectionMax", x);
	        armorItemsConfig.set(itemName + ".blockMin", x);
	        armorItemsConfig.set(itemName + ".blockMax", x);
	        armorItemsConfig.set(itemName + ".thornsMin", x);
	        armorItemsConfig.set(itemName + ".thornsMax", x);
	        armorItemsConfig.set(itemName + ".itemFindMin", x);
	        armorItemsConfig.set(itemName + ".itemFindMax", x);
	        armorItemsConfig.set(itemName + ".goldFindMin", x);
	        armorItemsConfig.set(itemName + ".goldFindMax", x);
	        
	
	
	        try {
	            armorItemsConfig.save(configFile);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
	        
	        //Success message!
	        player.sendMessage(" ");
	    	player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD +"-" + ChatColor.DARK_GRAY + 
	    			"<[ " + ChatColor.AQUA + "Success!! " + ChatColor.DARK_GRAY + "]>" + 
	    			ChatColor.DARK_GRAY + ChatColor.BOLD + "---------------------------------");
	    	player.sendMessage("   " + ChatColor.GREEN + player.getName() + ChatColor.RESET + 
	    			", your new Armor has been added to the config" + ChatColor.DARK_GRAY + ". ");
		} else {
	        player.sendMessage(" ");
	    	player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD +"-" + ChatColor.DARK_GRAY + 
	    			"<[ " + ChatColor.RED + "ERROR!! " + ChatColor.DARK_GRAY + "]>" + 
	    			ChatColor.DARK_GRAY + ChatColor.BOLD + "---------------------------------");
	    	player.sendMessage("   " + ChatColor.GREEN + player.getName() + ChatColor.RESET + 
	    			", you must use an armor items to create new armor types." + ChatColor.DARK_GRAY + ". ");
		}
	}
	*/
}
