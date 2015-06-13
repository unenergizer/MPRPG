package com.minepile.mprpg.equipment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minepile.mprpg.MPRPG;

public class WeaponManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static WeaponManager weaponManagerInstance = new WeaponManager();
	private static String FILE_PATH = "plugins/MPRPG/items/weapons.yml";
	
	FileConfiguration weaponConfig;
	
	//Create instance
	public static WeaponManager getInstance() {
		return weaponManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		//If mining configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(FILE_PATH)).exists()){
			//createWeaponConfiguration();
        } else {
        	//lets load the configuration file.
        	File configFile = new File(FILE_PATH);
            weaponConfig =  YamlConfiguration.loadConfiguration(configFile);

            //TODO: 
        }
	}	
	
	public enum itemQuality {
		JUNK,		//Grey
		COMMON,		//White
		UNCOMMON,	//Green
		RARE,		//Blue
		EPIC,		//Purple
		LEGENDARY	//Gold
	}
	
	public void makeItem(Player player, Material weapon) {
		
		if (weapon.equals(Material.WOOD_AXE) || weapon.equals(Material.WOOD_SPADE) ||
				weapon.equals(Material.WOOD_SWORD) || weapon.equals(Material.WOOD_HOE)) {
			//Generate wood weapon here:
			
		} else if (weapon.equals(Material.STONE_AXE) || weapon.equals(Material.STONE_SPADE) ||
				weapon.equals(Material.STONE_SWORD) || weapon.equals(Material.STONE_HOE)) {
			//Generate stone weapon here:
			
		} else if (weapon.equals(Material.IRON_AXE) || weapon.equals(Material.IRON_SPADE) ||
				weapon.equals(Material.IRON_SWORD) || weapon.equals(Material.IRON_HOE)) {
			//Generate iron weapon here:
			
		} else if (weapon.equals(Material.GOLD_AXE) || weapon.equals(Material.GOLD_SPADE) ||
				weapon.equals(Material.GOLD_SWORD) || weapon.equals(Material.GOLD_HOE)) {
			//Generate gold weapon here:
			
		} else if (weapon.equals(Material.DIAMOND_AXE) || weapon.equals(Material.DIAMOND_SPADE) ||
				weapon.equals(Material.DIAMOND_SWORD) || weapon.equals(Material.DIAMOND_HOE)) {
			//Generate diamond weapon here:
			
		}
	}
	
	public static void updateItemMeta(Player player, Material weapon, itemQuality quality, String itemName) {
		ItemStack is = player.getInventory().getItemInHand();
		ItemMeta im = is.getItemMeta();
		
		
		//Set the item name
		if (quality.equals(itemQuality.JUNK)) {
			im.setDisplayName(ChatColor.GRAY + itemName);
		} else if (quality.equals(itemQuality.COMMON)) {
			im.setDisplayName(ChatColor.WHITE + itemName);
		} else if (quality.equals(itemQuality.UNCOMMON)) {
			im.setDisplayName(ChatColor.GREEN + itemName);
		} else if (quality.equals(itemQuality.RARE)) {
			im.setDisplayName(ChatColor.BLUE + itemName);
		} else if (quality.equals(itemQuality.EPIC)) {
			im.setDisplayName(ChatColor.LIGHT_PURPLE + itemName);
		} else if (quality.equals(itemQuality.LEGENDARY)) {
			im.setDisplayName(ChatColor.GOLD + itemName);
		}
		
		
		//Set the item lore
		ArrayList<String> lore = new ArrayList<String>();
		
		/*
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.BOLD + "LVL: " + ChatColor.RESET +
				ChatColor.LIGHT_PURPLE + currentPickLVL);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.BOLD + "EXP: " + 
				ChatColor.RESET + ChatColor.BLUE + currentPickEXP + " / " + expToNextLevel);
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.BOLD + "EXP: " + 
				MessageManager.percentBar(expPercent) + ChatColor.GRAY + " " + expPercent + "%");
		lore.add(" ");//create blank space
		
		if (tool.equals(Material.WOOD_PICKAXE)) {
			lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.ITALIC + "A pickaxe made of wood.");
		} else if (tool.equals(Material.STONE_PICKAXE)) {
			lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.ITALIC + "A pickaxe made of stone.");
		} else if (tool.equals(Material.IRON_PICKAXE)) {
			lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.ITALIC + "A pickaxe made of iron.");
		} else if (tool.equals(Material.GOLD_PICKAXE)) {
			lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.ITALIC + "A pickaxe made of gold.");
		} else {
			lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.ITALIC + "A pickaxe made of diamond.");
		}
		*/
		//Set the item lore
		im.setLore(lore);
		//Set the item meta
		is.setItemMeta(im);
		
	}
	
	
	public static void createWeaponConfiguration() {
    	
        File configFile = new File(FILE_PATH);
        FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
        playerConfig.set("quality.junk.damageMin", 1);
        playerConfig.set("quality.junk.damageMax", 20);
        playerConfig.set("quality.common.damageMin", 10);
        playerConfig.set("quality.common.damageMax", 40);
        playerConfig.set("quality.uncommon.damageMin", 30);
        playerConfig.set("quality.uncommon.damageMax", 60);
        playerConfig.set("quality.rare.damageMin", 50);
        playerConfig.set("quality.rare.damageMax", 120);
        playerConfig.set("quality.epic.damageMin", 100);
        playerConfig.set("quality.epic.damageMax", 200);
        playerConfig.set("quality.legendary.damageMin", 150);
        playerConfig.set("quality.legendary.damageMax", 400);

        try {
            playerConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
