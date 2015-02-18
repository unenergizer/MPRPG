package com.minepile.mprpg.managers;

import java.util.ArrayList;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.minepile.mprpg.MPRPG;

public class PlayerMenuManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static PlayerMenuManager menuManagerInstance = new PlayerMenuManager();
	
	//Define inventory menu's here.
	public static Inventory mainMenu = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + 
			"" + ChatColor.BOLD + "Player Menu!" + ChatColor.BLUE + " Not Ready Yet");
	public static Inventory settingsMenu = Bukkit.createInventory(null, 27, ChatColor.GREEN + "Game Settings");
	
	//Create instance
	public static PlayerMenuManager getInstance() {
		return menuManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		//Lets add all the items (menu options) to the (chest) menu's.
		addItemsToMenu();
	}
	
	public static void addItemsToMenu() {
		//Define the items in the Main Menu.
		mainMenu.setItem(0, new ItemStack(Material.BOOK, 1));
        mainMenu.setItem(1, new ItemStack(Material.ENCHANTED_BOOK, 1));
        mainMenu.setItem(2, new ItemStack(Material.MAP));
        mainMenu.setItem(3, new ItemStack(Material.WOOD_SWORD));
        mainMenu.setItem(6, new ItemStack(Material.ENDER_CHEST));
        mainMenu.setItem(7, new ItemStack(Material.MONSTER_EGG));
        mainMenu.setItem(8, new ItemStack(Material.REDSTONE_COMPARATOR));
        
        mainMenu.setItem(18, new ItemStack(Material.COMPASS));
        mainMenu.setItem(19, new ItemStack(Material.WATCH));
        mainMenu.setItem(21, new ItemStack(Material.GOLD_INGOT));
        mainMenu.setItem(22, new ItemStack(Material.IRON_INGOT));
        mainMenu.setItem(23, new ItemStack(Material.CLAY_BRICK));

        mainMenu.setItem(25, new ItemStack(Material.PORTAL));
        mainMenu.setItem(26, new ItemStack(Material.ENCHANTMENT_TABLE));
        
		//Define the items in the Settings Menu.
		
	}
	
	//This will give the player the menu, if they don't already have it.
	public static void givePlayerMenu(Player player) {
		PlayerInventory playerInv = player.getInventory();
		
		if (playerInv.contains(Material.COMPASS)) {
			playerInv.remove(Material.COMPASS);
		}
		
		if (!playerInv.contains(Material.COMPASS)) {
				
			ItemStack tool = new ItemStack(Material.COMPASS);
			ItemMeta meta = tool.getItemMeta();
			
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Game Menu");
			
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "This is the main menu for the game.");
			lore.add(ChatColor.GRAY + "With this menu you can view your");
			lore.add(ChatColor.GRAY + "statistics and change your settings.");
			lore.add(ChatColor.GRAY + "This item can not be removed from your");
			lore.add(ChatColor.GRAY + "inventory.");
			
			//Set the item lore
			meta.setLore(lore);
			//Set the item meta
			tool.setItemMeta(meta);
			
			playerInv.setItem(8, tool);
		}
	}
}
