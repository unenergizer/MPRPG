package com.minepile.mprpg.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerCharacterManager;
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
	static ItemStack trackingDevice, hearthStone, changeLobby, cosmeticMenu, ruleBook, achievements, gameStats, guildManagement, playerSettings, on, off;
	
	//Create instance
	public static PlayerMenuManager getInstance() {
		return menuManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		makeMenuItems();
	}
	
	private void makeMenuItems() {
		//Setup global menu items
		Dye dyeOn = new Dye(Material.INK_SACK);
		Dye dyeOff = new Dye(Material.INK_SACK);
		dyeOn.setColor(DyeColor.LIME);
		dyeOff.setColor(DyeColor.LIME);
		on = dyeOn.toItemStack(1);
		off = dyeOff.toItemStack(1);
		
		/////////////////////////////////
		//// Main Menu Items ////////////
		/////////////////////////////////

		ArrayList<String> loreTrackingDevice = new ArrayList<String>();
		ArrayList<String> loreHearthStone = new ArrayList<String>();
		ArrayList<String> loreChangeLobby = new ArrayList<String>();
		ArrayList<String> loreCosmeticMenu = new ArrayList<String>();
		
		ArrayList<String> loreRuleBook = new ArrayList<String>();
		ArrayList<String> loreAchievements = new ArrayList<String>();
		ArrayList<String> loreGameStats = new ArrayList<String>();
		ArrayList<String> loreGuildManagement = new ArrayList<String>();
		ArrayList<String> lorePlayerSettings = new ArrayList<String>();
		
		loreTrackingDevice.add(ChatColor.GRAY + "This menu will allow you to");
		loreTrackingDevice.add(ChatColor.GRAY + " track party members, guild");
		loreTrackingDevice.add(ChatColor.GRAY + " members, locations, and NPC's.");
		
		loreHearthStone.add(ChatColor.GRAY + "This will warp you to the last");
		loreHearthStone.add(ChatColor.GRAY + " Inn you set your home at.");
		
		loreChangeLobby.add(ChatColor.GRAY + "This will let you change what game");
		loreChangeLobby.add(ChatColor.GRAY + " ");
		
		loreCosmeticMenu.add(ChatColor.GRAY + "Support the server and buy cool");
		loreCosmeticMenu.add(ChatColor.GRAY + " cosmetic items here!  This best");
		loreCosmeticMenu.add(ChatColor.GRAY + " way to stand out in a crowd!");
		
		loreRuleBook.add(ChatColor.GRAY + "This book covers all the rules.");
		loreRuleBook.add(ChatColor.GRAY + " Read this and get a reward!");
		
		loreAchievements.add(ChatColor.GRAY + "This shows your current");
		loreAchievements.add(ChatColor.GRAY + " characters in game");
		loreAchievements.add(ChatColor.GRAY + " achievements.");
		
		loreGameStats.add(ChatColor.GRAY + "This shows your current");
		loreGameStats.add(ChatColor.GRAY + " characters in game");
		loreGameStats.add(ChatColor.GRAY + " statistics.");
		
		loreGuildManagement.add(ChatColor.GRAY + "This menu will let you manage");
		loreGuildManagement.add(ChatColor.GRAY + " your guild.  You can also see");
		loreGuildManagement.add(ChatColor.GRAY + " what members are online here.");
		
		lorePlayerSettings.add(ChatColor.GRAY + "Edit your game settings here.");
		lorePlayerSettings.add(ChatColor.GRAY + " The settings menu holds a lot");
		lorePlayerSettings.add(ChatColor.GRAY + " of options to customize your");
		lorePlayerSettings.add(ChatColor.GRAY + " game experience.");
		
		
		//Row 1
		trackingDevice = new ItemBuilder(Material.COMPASS).setTitle(ChatColor.GREEN + "Tracking Device").addLores(loreTrackingDevice).build();
		hearthStone = new ItemBuilder(Material.NETHER_STAR).setTitle(ChatColor.LIGHT_PURPLE + "Hearth Stone").addLores(loreHearthStone).build();
		changeLobby = new ItemBuilder(Material.WATCH).setTitle(ChatColor.GREEN + "Change Lobby").addLores(loreChangeLobby).build();
		cosmeticMenu = new ItemBuilder(Material.ENDER_CHEST).setTitle(ChatColor.AQUA + "Cosmetic Menu").addLores(loreCosmeticMenu).build();
		
		//Row 2
		ruleBook = new ItemBuilder(Material.BOOK, 1).setTitle(ChatColor.RED + "Rule Book").addLores(loreRuleBook).build();
        achievements = new ItemBuilder(Material.ENCHANTED_BOOK, 1).setTitle(ChatColor.GREEN + "Player Achievement").addLores(loreAchievements).build();
        gameStats = new ItemBuilder(Material.MAP).setTitle(ChatColor.GREEN + "Game Stats").addLores(loreGameStats).build();
        guildManagement = new ItemBuilder(Material.CAKE).setTitle(ChatColor.GREEN + "Guild Management").addLores(loreGuildManagement).build();
        playerSettings = new ItemBuilder(Material.REDSTONE_COMPARATOR).setTitle(ChatColor.RED + "Player Settings").addLores(lorePlayerSettings).build();
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
		
		Inventory mainMenu = Bukkit.createInventory(player, 9 * 4, gameMenu);
		//Define the items in the Main Menu.
		
		ArrayList<String> playerInfo = new ArrayList<String>();

		playerInfo.add(ChatColor.GRAY + "Various information about");
		playerInfo.add(ChatColor.GRAY + " your character.");
		playerInfo.add("");
		playerInfo.add(ChatColor.GRAY + "Class: " + ChatColor.RED + PlayerCharacterManager.getPlayerClass(player));
		playerInfo.add(ChatColor.GRAY + "Level: " + ChatColor.RED + player.getLevel());
		playerInfo.add(ChatColor.GRAY + "Guild: " + ChatColor.RED + "null");
		playerInfo.add("");
		playerInfo.add(ChatColor.GRAY + "Money: " + ChatColor.RED + "null");
		playerInfo.add(ChatColor.GRAY + "eCash: " + ChatColor.RED + "null");
		playerInfo.add("");
		playerInfo.add(ChatColor.LIGHT_PURPLE + "Left-Click" + ChatColor.GRAY + " to change your");
		playerInfo.add(ChatColor.GRAY + " character. You must be");
		playerInfo.add(ChatColor.GRAY + " out of combat to switch.");
		
		
		//Row 1
		mainMenu.setItem(11, new ItemBuilder(Material.SKULL_ITEM).setTitle(ChatColor.AQUA + "Player Info").addLores(playerInfo).build());
		mainMenu.setItem(12, trackingDevice.clone());
		mainMenu.setItem(13, hearthStone.clone());
		mainMenu.setItem(14, changeLobby.clone());
		mainMenu.setItem(15, cosmeticMenu.clone());
		
		//Row 2
		mainMenu.setItem(20, ruleBook.clone());
        mainMenu.setItem(21, achievements.clone());
        mainMenu.setItem(22, gameStats.clone());
        mainMenu.setItem(23, guildManagement.clone());
        mainMenu.setItem(24, playerSettings.clone());
        
        //Save players menu.
        playerMainMenu.put(player.getUniqueId(), mainMenu);
        
        /////////////////////
        /// SETTINGS MENU ///
        /////////////////////
        
		Inventory settingsMenu = Bukkit.createInventory(player, 36, gameSettingsMenu);
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
		
		//Don't give the player a compass, if they already have one.
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
	}
	
	/**
	 * Instructions on what will happen when a player interacts with the menu.
	 * 
	 * @param player The player interacting with the menu.
	 * @param menuName The name of the menu being interacted with.
	 * @param item The item in the menu that has been clicked.
	 */
	public static void playerInteractMenu(Player player, String menuName, ItemStack item) {
		if (menuName.equalsIgnoreCase(gameMenu)) {
			if (item.getType().equals(Material.ENCHANTMENT_TABLE)) {
				player.sendMessage("Game Menu - You clicked the enchant table.");
			}
			
			if (item.getType().equals(Material.REDSTONE_COMPARATOR)) {
				//Open up the settings menu.
				player.openInventory(playerSettingsMenu.get(player.getUniqueId()));
			}
		} else if (menuName.equalsIgnoreCase(gameSettingsMenu)) {
			boolean healthDebugMessages = PlayerCharacterManager.getPlayerConfigBoolean(player, "setting.chat.healthDebug");
			boolean monsterDebugMessages = PlayerCharacterManager.getPlayerConfigBoolean(player, "setting.chat.monsterDebug");
			boolean professionDebugMessages = PlayerCharacterManager.getPlayerConfigBoolean(player, "setting.chat.professionDebug");
			
			if (item.equals(on) && (player.getInventory().getItem(27).equals(item))) {
				PlayerCharacterManager.setPlayerConfigBoolean(player, "setting.chat.monsterDebug", false);
				//TODO: set the item icon to off
			} else {
				PlayerCharacterManager.setPlayerConfigBoolean(player, "setting.chat.monsterDebug", true);
				//TODO: set the item icon to on
			}
		}
	}
}
