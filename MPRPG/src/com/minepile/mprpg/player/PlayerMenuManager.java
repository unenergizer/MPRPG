package com.minepile.mprpg.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.util.ItemBuilder;

public class PlayerMenuManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static PlayerMenuManager menuManagerInstance = new PlayerMenuManager();
	
	//Player Menus
	static HashMap<UUID, Inventory> playerMainMenu = new HashMap<UUID, Inventory>();
	static HashMap<UUID, Inventory> playerSettingsMenu = new HashMap<UUID, Inventory>();
	
	//Menu Names
	static String gameMenu = "Game Menu";
	static String gameSettingsMenu = "Settings";
	
	//Global Menu Items
	static ItemStack on, off;
	
	//Create instance
	public static PlayerMenuManager getInstance() {
		return menuManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		//Setup global menu items
		Dye dyeOn = new Dye(Material.INK_SACK);
		Dye dyeOff = new Dye(Material.INK_SACK);
		dyeOn.setColor(DyeColor.LIME);
		dyeOff.setColor(DyeColor.LIME);
		on = dyeOn.toItemStack(1);
		off = dyeOff.toItemStack(1);
		
	}
	
	/**
	 * This will create various menu's for the player logging in.
	 * 
	 * @param player The player who is getting menus created for them.
	 */
	public static void createMenu(Player player) {
		String playerName = player.getName();
		
		/////////////////
		/// MAIN MENU ///
		/////////////////
		
		Inventory mainMenu = Bukkit.createInventory(player, 27, playerName + "'s " + ChatColor.RED + gameMenu);
		//Define the items in the Main Menu.
		mainMenu.setItem(0, new ItemBuilder(Material.BOOK, 1).setTitle(ChatColor.GREEN + "Rule Book").build());
        mainMenu.setItem(1, new ItemBuilder(Material.ENCHANTED_BOOK, 1).setTitle(ChatColor.GREEN + "Achievement").build());
        mainMenu.setItem(2, new ItemBuilder(Material.MAP).setTitle(ChatColor.GREEN + "Stats").build());
        mainMenu.setItem(3, new ItemBuilder(Material.WOOD_SWORD).setTitle(ChatColor.GREEN + "Guild Info").build());
        mainMenu.setItem(6, new ItemBuilder(Material.ENDER_CHEST).setTitle(ChatColor.GREEN + "Cosmetic Menu").build());
        mainMenu.setItem(7, new ItemBuilder(Material.MONSTER_EGG).setTitle(ChatColor.GREEN + "Pet Menu").build());
        mainMenu.setItem(8, new ItemBuilder(Material.REDSTONE_COMPARATOR).setTitle(ChatColor.GREEN + "Settings").build());
        
        mainMenu.setItem(18, new ItemBuilder(Material.COMPASS).setTitle(ChatColor.GREEN + "Tracking Device").build());
        mainMenu.setItem(19, new ItemBuilder(Material.WATCH).setTitle(ChatColor.GREEN + "Game/Lobby Menu").build());
        mainMenu.setItem(21, new ItemBuilder(Material.GOLD_INGOT).setTitle(ChatColor.GREEN + "Gold value here").build());
        mainMenu.setItem(22, new ItemBuilder(Material.IRON_INGOT).setTitle(ChatColor.GREEN + "Silver value here").build());
        mainMenu.setItem(23, new ItemBuilder(Material.CLAY_BRICK).setTitle(ChatColor.GREEN + "Copper value here").build());

        mainMenu.setItem(25, new ItemBuilder(Material.DIAMOND).setTitle(ChatColor.GREEN + "eChash value here").build());
        mainMenu.setItem(26, new ItemBuilder(Material.ENCHANTMENT_TABLE).setTitle(ChatColor.GREEN + "Server Store").build());
        
        playerMainMenu.put(player.getUniqueId(), mainMenu);
        
        /////////////////////
        /// SETTINGS MENU ///
        /////////////////////
        
		Inventory settingsMenu = Bukkit.createInventory(player, 36, playerName + "'s " + ChatColor.RED + gameSettingsMenu);
		//Row Header
		settingsMenu.setItem(0, new ItemBuilder(Material.HOPPER).setTitle(ChatColor.GREEN + "Previous Menu / Back").build());//Back to previous menu
		settingsMenu.setItem(1, new ItemBuilder(Material.STAINED_GLASS_PANE,  1, (short) 15).setTitle(" ").build());
		settingsMenu.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE,  1, (short) 15).setTitle(" ").build());
		settingsMenu.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE,  1, (short) 15).setTitle(" ").build());
		settingsMenu.setItem(4, new ItemBuilder(Material.SIGN).setTitle(ChatColor.AQUA + "Game Settings").build());			//About menu
		settingsMenu.setItem(5, new ItemBuilder(Material.STAINED_GLASS_PANE,  1, (short) 15).setTitle(" ").build());
		settingsMenu.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE,  1, (short) 15).setTitle(" ").build());
		settingsMenu.setItem(7, new ItemBuilder(Material.STAINED_GLASS_PANE,  1, (short) 15).setTitle(" ").build());
		settingsMenu.setItem(8, new ItemBuilder(Material.REDSTONE_BLOCK).setTitle(ChatColor.RED + "Close Menu").build());	//Exit Menu
        
		//Options row 1.
		settingsMenu.setItem(18, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 2).setTitle(ChatColor.GREEN + "Monster Debug Messages").build());//Monster Debug Messages
		settingsMenu.setItem(20, new ItemBuilder(Material.REDSTONE).setTitle(ChatColor.GREEN + "Player Debug Messages").build());				//Player HP Debug Messages
		settingsMenu.setItem(22, new ItemBuilder(Material.STONE_PICKAXE).setTitle(ChatColor.GREEN + "Profession Debug Messages").build());		//Profession Debug Messages
		settingsMenu.setItem(24, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setTitle(ChatColor.GREEN + "Friends Requests").build());	//Friend Requests
		settingsMenu.setItem(26, new ItemBuilder(Material.BED).setTitle(ChatColor.GREEN + "Guild Requests").build());							//Guild Requests						//Guild Requests
        
		
		//Option settings for row 1
		settingsMenu.setItem(27, on);
		settingsMenu.setItem(29, on);
		settingsMenu.setItem(31, off);
		settingsMenu.setItem(33, off);
		settingsMenu.setItem(35, off);
		
        playerSettingsMenu.put(player.getUniqueId(), settingsMenu);
        
	}
	
	/**
	 * Deletes a player menu from the HashMap.
	 * 
	 * @param player The player's who will have their menu's deleted.
	 */
	public static void deleteMenu(Player player) {
		UUID id = player.getUniqueId();
		
		playerMainMenu.remove(id);
		playerSettingsMenu.remove(id);
	}
	
	/**
	 * This will give the player the menu, if they don't already have it.
	 * 
	 * @param player The player to give the Game Menu to.
	 */
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
			lore.add(ChatColor.GRAY + "A menu that allows you to change game settings");
			lore.add(ChatColor.GRAY + "and view your user profile.");
			lore.add("");
			lore.add(ChatColor.GREEN + "Right-Click" + ChatColor.GRAY + ": Opens your game menu.");
			lore.add(ChatColor.BLUE + "Sneak + Right-Click" + ChatColor.GRAY + ": Places your chest shop.");
			lore.add(ChatColor.LIGHT_PURPLE + "Sneak + Left-Click" + ChatColor.GRAY + ": Opens your private realm.");
			
			//Set the item lore
			meta.setLore(lore);
			//Set the item meta
			tool.setItemMeta(meta);
			
			playerInv.setItem(8, tool);
		}
	}
	
	/**
	 * Opens the players menu.
	 * 
	 * @param player The player to open the menu for.
	 */
	public static void openPlayerMenu(Player player) {
		player.openInventory(playerMainMenu.get(player.getUniqueId()));
		
		/*
		PageInventory inv = new PageInventory(player);
		ItemStack items = new ItemBuilder(Material.SKULL_ITEM).setTitle("Test").build();
		
		inv.setPages(items);
		inv.setTitle("Players online");
		inv.openInventory();
		*/
	}
	
	/**
	 * Instructions on what will happen when a player interacts with the menu.
	 * 
	 * @param player The player interacting with the menu.
	 * @param menuName The name of the menu being interacted with.
	 * @param item The item in the menu that has been clicked.
	 */
	public static void playerInteractMenu(Player player, String menuName, ItemStack item) {
		if (menuName.endsWith(gameMenu)) {
			if (item.getType().equals(Material.ENCHANTMENT_TABLE)) {
				player.sendMessage("Game Menu - You clicked the enchant table.");
			}
			
			if (item.getType().equals(Material.REDSTONE_COMPARATOR)) {
				//Open up the settings menu.
				player.openInventory(playerSettingsMenu.get(player.getUniqueId()));
			}
		} else if (menuName.contains(gameSettingsMenu)) {
			boolean healthDebugMessages = PlayerManager.getPlayerConfigBoolean(player, "setting.chat.healthDebug");
			boolean monsterDebugMessages = PlayerManager.getPlayerConfigBoolean(player, "setting.chat.monsterDebug");
			boolean professionDebugMessages = PlayerManager.getPlayerConfigBoolean(player, "setting.chat.professionDebug");
			
			if (item.equals(on) && (player.getInventory().getItem(27).equals(item))) {
				PlayerManager.setPlayerConfigBoolean(player, "setting.chat.monsterDebug", false);
				//TODO: set the item icon to off
			} else {
				PlayerManager.setPlayerConfigBoolean(player, "setting.chat.monsterDebug", true);
				//TODO: set the item icon to on
			}
		}
	}
}
