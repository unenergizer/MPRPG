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

        mainMenu.setItem(25, new ItemStack(Material.DIAMOND));
        mainMenu.setItem(26, new ItemStack(Material.ENCHANTMENT_TABLE));
        
        playerMainMenu.put(player.getUniqueId(), mainMenu);
        
        /////////////////////
        /// SETTINGS MENU ///
        /////////////////////
        
		Inventory settingsMenu = Bukkit.createInventory(player, 36, playerName + "'s " + ChatColor.RED + gameSettingsMenu);
		//Row Header
		settingsMenu.setItem(0, new ItemStack(Material.HOPPER));				//Back to previous menu
		settingsMenu.setItem(1, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));
		settingsMenu.setItem(2, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));
		settingsMenu.setItem(3, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));
		settingsMenu.setItem(4, new ItemStack(Material.BOOK));					//About menu
		settingsMenu.setItem(5, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));
		settingsMenu.setItem(6, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));
		settingsMenu.setItem(7, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));
		settingsMenu.setItem(8, new ItemStack(Material.REDSTONE_BLOCK));		//Exit Menu
        
		//Options row 1.
		settingsMenu.setItem(18, new ItemStack(Material.SKULL_ITEM, 1, (short) 2));	//Monster Debug Messages
		settingsMenu.setItem(20, new ItemStack(Material.REDSTONE));					//Player HP Debug Messages
		settingsMenu.setItem(22, new ItemStack(Material.STONE_PICKAXE));			//Profession Debug Messages
		settingsMenu.setItem(24, new ItemStack(Material.SKULL_ITEM, 1, (short) 3));	//Friend Requests
		settingsMenu.setItem(26, new ItemStack(Material.BED));						//Guild Requests
        
		
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
	
	/**
	 * Opens the players menu.
	 * 
	 * @param player The player to open the menu for.
	 */
	public static void openPlayerMenu(Player player) {
		player.openInventory(playerMainMenu.get(player.getUniqueId()));
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
