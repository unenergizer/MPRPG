package com.minepile.mprpg.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Dye;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerCharacterManager;
import com.minepile.mprpg.player.PlayerHearthStoneManager;
import com.minepile.mprpg.player.PlayerManager;
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
	static ItemStack trackingDevice, hearthStone, changeLobby, cosmeticMenu, helpBook, achievements, gameStats, gameCredits, guildManagement, playerSettings, on, off;
	static ItemStack fullHelpBook;
	
	//Create instance
	public static PlayerMenuManager getInstance() {
		return menuManagerInstance;
	}

	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		makeMenuItems();
		makeHelpBook();
	}
	
	private void makeHelpBook() {
		ArrayList<String> lores = new ArrayList<String>();

		lores.add(ChatColor.GRAY + "Soulbound");
		lores.add("");
		lores.add(ChatColor.GRAY + "This book covers all the help topics.");
		lores.add(ChatColor.GRAY + " Read this and get a reward!");

		//Give user the welcome/stats book.
		fullHelpBook = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta bookMeta = (BookMeta) fullHelpBook.getItemMeta();
		bookMeta.setTitle(ChatColor.GOLD + "" + ChatColor.BOLD + "Help Book");
		bookMeta.setLore(lores);
		bookMeta.setAuthor(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "MinePile");
		bookMeta.addPage(ChatColor.RED + "" + ChatColor.BOLD + "     Valenguard" + ChatColor.RESET +
				"\n" + "         a game by" +
				"\n" + "     " + ChatColor.BLUE + ChatColor.UNDERLINE + "www.MinePile.com" + ChatColor.RESET +
				"\n" + "" +
				"\n" + "" +
				"\n" + ChatColor.BOLD + "Contents:" + ChatColor.RESET +
				"\n" + "   1. Index" +
				"\n" + "   2. Introduction" +
				"\n" + "   3. Game Guide" +
				"\n" + "   4. Rules" +
				"\n" + "   5. World Map" +
				"\n" + "   6. Commands");
		bookMeta.addPage(ChatColor.RED + "" + ChatColor.BOLD + "    Introduction" + ChatColor.RESET +
				"\n" + "    * the beggining *" +
				"\n" + "" +
				"\n" + "Welcome traveler!  You are about to embark on an epic adventure full of treasure and wonder." + 
				"\n" + "Tread carefully, as the world has been corrupted by an unknown evil force.  Are you up for a challenge?");
		bookMeta.addPage(ChatColor.RED + "" + ChatColor.BOLD + "     Game Guide" + ChatColor.RESET +
				"\n" + "     * how to play *" +
				"\n" + "" +
				"\n" + "This is a Role Playing Game (RPG), which has several custom features not found on any other server." +
				"\n" + "" +
				"\n" + "" +
				"\n" + "" + ChatColor.BOLD + "Guide:" +
				"\n" + ChatColor.BLUE + ChatColor.UNDERLINE + "www.MinePile.com/guide");
		bookMeta.addPage(ChatColor.RED + "" + ChatColor.BOLD + "   Server Rules" + ChatColor.RESET +
				"\n" + "   * don't break'em *" +
				"\n" + "" +
				"\n" + ChatColor.BOLD + "Rules:" + ChatColor.RESET +
				"\n" + "1. Do not hack!" +
				"\n" + "2. Do not advertise!" +
				"\n" + "3. Don't ask for items!" +
				"\n" + "4. Respect players." +
				"\n" + "5. Respect staff members." +
				"\n" + "6. No full CAPS messages.");
		bookMeta.addPage(ChatColor.RED + "" + ChatColor.BOLD + "     World Map" + ChatColor.RESET +
				"\n" + "  * whoa, thats big *" +
				"\n" + "" +
				"\n" + "The map can be found with the link below." +
				"\n" + "" +
				"\n" + "" +
				"\n" + ChatColor.BOLD + "Map:" + ChatColor.RESET +
				"\n" + ChatColor.BLUE + ChatColor.UNDERLINE + "www.MinePile.com/map");
		bookMeta.addPage(ChatColor.RED + "" + ChatColor.BOLD + "     Commands" + ChatColor.RESET +
				"\n" + "    * boss mode *" +
				"\n" + "" +
				"\n" + "There are many commands that will help you on your adventures." +
				"\n" + "" +
				"\n" + "The next few pages will contain a list of all the commands you can use." +
				"\n" + "");
		bookMeta.addPage(ChatColor.BOLD + "/help" + ChatColor.RESET +
				"\n" + "Gives you a copy of this book" +
				"\n" + "" +
				"\n" + ChatColor.BOLD + "/c <ChannelName>" + ChatColor.RESET +
				"\n" + "Changes your chat channel." +
				"\n" + "" +
				"\n" + ChatColor.BOLD + "/pm <PlayerName>" + ChatColor.RESET +
				"\n" + "Sends a private message.");
		bookMeta.addPage(ChatColor.BOLD + "/r <MessageHere>" + ChatColor.RESET +
				"\n" + "Quick replay to last pm." +
				"\n" + "" +
				"\n" + ChatColor.BOLD + "/armorstats" + ChatColor.RESET +
				"\n" + "Lists all your stats." +
				"\n" + "" +
				"\n" + ChatColor.BOLD + "/roll <NumberHere>" + ChatColor.RESET +
				"\n" + "Rolls a dice.  Can be used to decide who gets an item drop.  Can also be used for gambling.");
		bookMeta.addPage(ChatColor.BOLD + "/guild" + ChatColor.RESET +
				"\n" + "Various guild related commands." +
				"\n" + "" +
				"\n" + ChatColor.BOLD + "/spawn" + ChatColor.RESET +
				"\n" + "Takes you back to spawn.");
		

		fullHelpBook.setItemMeta(bookMeta);
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

		ArrayList<String> loreHelpBook = new ArrayList<String>();
		ArrayList<String> loreAchievements = new ArrayList<String>();
		ArrayList<String> loreGameStats = new ArrayList<String>();
		ArrayList<String> loreGameCredits = new ArrayList<String>();
		ArrayList<String> lorePlayerSettings = new ArrayList<String>();
		
		ArrayList<String> loreGuildManagement = new ArrayList<String>();

		
		loreTrackingDevice.add(ChatColor.GRAY + "This menu will allow you to");
		loreTrackingDevice.add(ChatColor.GRAY + " track party members, guild");
		loreTrackingDevice.add(ChatColor.GRAY + " members, locations, and NPC's.");

		loreHearthStone.add(ChatColor.GRAY + "This will warp you to the last");
		loreHearthStone.add(ChatColor.GRAY + " Inn you set your home at.");

		loreChangeLobby.add(ChatColor.GRAY + "This will let you change what game");
		loreChangeLobby.add(ChatColor.GRAY + " ");

		loreCosmeticMenu.add(ChatColor.GRAY + "Support the server and buy cool");
		loreCosmeticMenu.add(ChatColor.GRAY + " cosmetic items here!  The best");
		loreCosmeticMenu.add(ChatColor.GRAY + " way to stand out in a crowd!");

		loreHelpBook.add(ChatColor.GRAY + "This book covers a few topis.");
		loreHelpBook.add(ChatColor.DARK_PURPLE + " 1" + ChatColor.GRAY + ". It covers commands.");
		loreHelpBook.add(ChatColor.DARK_PURPLE + " 2" + ChatColor.GRAY + ". It covers help topics.");
		loreHelpBook.add(ChatColor.DARK_PURPLE + " 3" + ChatColor.GRAY + ". It covers server rules.");

		loreAchievements.add(ChatColor.GRAY + "This shows your current");
		loreAchievements.add(ChatColor.GRAY + " characters in game");
		loreAchievements.add(ChatColor.GRAY + " achievements.");

		loreGameStats.add(ChatColor.GRAY + "This shows your current");
		loreGameStats.add(ChatColor.GRAY + " characters in game");
		loreGameStats.add(ChatColor.GRAY + " statistics.");

		loreGameCredits.add(ChatColor.GRAY + "This server has taken 1000's of");
		loreGameCredits.add(ChatColor.GRAY + " hours to make.  We started from");
		loreGameCredits.add(ChatColor.GRAY + " basically nothing.  This feature");
		loreGameCredits.add(ChatColor.GRAY + " was made to thank all that was");
		loreGameCredits.add(ChatColor.GRAY + " involved with the making of this");
		loreGameCredits.add(ChatColor.GRAY + " server.");
		loreGameCredits.add("");
		loreGameCredits.add(ChatColor.LIGHT_PURPLE + "Left-Click" + ChatColor.GRAY + " to start watching the");
		loreGameCredits.add(ChatColor.GRAY + " server credits.  You must be in");
		loreGameCredits.add(ChatColor.GRAY + " a safe-zone to use this..");
		

		lorePlayerSettings.add(ChatColor.GRAY + "Edit your game settings here.");
		lorePlayerSettings.add(ChatColor.GRAY + " The settings menu holds a lot");
		lorePlayerSettings.add(ChatColor.GRAY + " of options to customize your");
		lorePlayerSettings.add(ChatColor.GRAY + " game experience.");

		loreGuildManagement.add(ChatColor.GRAY + "This menu will let you manage");
		loreGuildManagement.add(ChatColor.GRAY + " your guild.  You can also see");
		loreGuildManagement.add(ChatColor.GRAY + " what members are online here.");


		//Row 1
		trackingDevice = new ItemBuilder(Material.COMPASS).setTitle(ChatColor.GREEN + "Tracking Device").addLores(loreTrackingDevice).build();
		hearthStone = new ItemBuilder(Material.NETHER_STAR).setTitle(ChatColor.LIGHT_PURPLE + "Hearthstone").addLores(loreHearthStone).build();
		changeLobby = new ItemBuilder(Material.WATCH).setTitle(ChatColor.GREEN + "Change Lobby").addLores(loreChangeLobby).build();
		cosmeticMenu = new ItemBuilder(Material.ENDER_CHEST).setTitle(ChatColor.AQUA + "Cosmetic Menu").addLores(loreCosmeticMenu).build();

		//Row 2
		helpBook = new ItemBuilder(Material.BOOK, 1).setTitle(ChatColor.RED + "Game Help").addLores(loreHelpBook).build();
		achievements = new ItemBuilder(Material.ENCHANTED_BOOK, 1).setTitle(ChatColor.GREEN + "Player Achievement").addLores(loreAchievements).build();
		gameStats = new ItemBuilder(Material.MAP).setTitle(ChatColor.GREEN + "Game Stats").addLores(loreGameStats).build();
		gameCredits = new ItemBuilder(Material.CAKE).setTitle(ChatColor.GREEN + "Server Credits").addLores(loreGameCredits).build();
		playerSettings = new ItemBuilder(Material.REDSTONE_COMPARATOR).setTitle(ChatColor.RED + "Player Settings").addLores(lorePlayerSettings).build();
		
		//Row 3
		guildManagement = new ItemBuilder(Material.BANNER).setTitle(ChatColor.GREEN + "Guild Management").addLores(loreGuildManagement).build();
		
	}

	/**
	 * This will create various menu's for the player logging in.
	 * 
	 * @param player The player who is getting menus created for them.
	 */
	public static void createMenu(Player player) {

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

		ItemStack playerSkull = new ItemStack(Material.SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
		SkullMeta skullMeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
		skullMeta.setOwner(player.getName());
		skullMeta.setDisplayName(ChatColor.AQUA + "Player Info");
		skullMeta.setLore(playerInfo);
		playerSkull.setItemMeta(skullMeta);

		//Row 1
		mainMenu.setItem(11, playerSkull);
		mainMenu.setItem(12, trackingDevice.clone());
		mainMenu.setItem(13, hearthStone.clone());
		mainMenu.setItem(14, changeLobby.clone());
		mainMenu.setItem(15, cosmeticMenu.clone());

		//Row 2
		mainMenu.setItem(20, helpBook.clone());
		mainMenu.setItem(21, achievements.clone());
		mainMenu.setItem(22, gameStats.clone());
		mainMenu.setItem(23, gameCredits.clone());
		mainMenu.setItem(24, playerSettings.clone());
		
		//Row 3
		mainMenu.setItem(31, guildManagement.clone());

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
			if (item.getType().equals(Material.SKULL_ITEM)) {

				//Save the players character
				PlayerCharacterManager.saveCharacter(player);

				//Removes the player from the game.
				//Removes players from hashMaps.
				PlayerManager.removePlayer(player);
				PlayerCharacterManager.removePlayer(player);

				//Setup player again.
				PlayerCharacterManager.initializePlayer(player);
				
			} else if (item.getType().equals(Material.BOOK)) {
				//Give the player the rule book.
				giveRuleBook(player);
				
			} else if (item.getType().equals(Material.NETHER_STAR)) {
				//Let the player use the hearth stone.
				PlayerHearthStoneManager.useHearthStone(player);
				
			} else if (item.getType().equals(Material.REDSTONE_COMPARATOR)) {
				//Open up the settings menu.
				player.openInventory(playerSettingsMenu.get(player.getUniqueId()));
				
			} else {
				player.sendMessage(ChatColor.GREEN + "Feature coming soon! ™");
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

	public static void giveRuleBook(Player player) {

		

		//Setup for placing the item in blank inventory slot
		boolean hasBook = false;
		int invSize = player.getInventory().getSize();
		int freeSlots = 0;
		
		//Lets loop through he inventory and make sure the player
		//doesn't already have a book.
		for (int i = 0; i < invSize; i++) {
			ItemStack item = player.getInventory().getItem(i);
			
			//Check for empty slot.
			if (item == null) {
				freeSlots++;
			}
			
			//Check if the book exists.
			if (item != null && item.isSimilar(fullHelpBook)) {
				hasBook = true;
				player.sendMessage(ChatColor.RED + "You already have a copy of the help book.");

				//Play a sound.
				player.playSound(player.getLocation(), Sound.NOTE_BASS, 1F, 1F);
			}
		}
		
		//Check to see if the player has a book.
		if (hasBook == false && freeSlots > 0) {
			//Loop through the players inventory and find a free inventory slot.
			for (int i = 0; i < invSize; i++) {
				ItemStack item = player.getInventory().getItem(i);
				
				//Check for empty slot.
				if (item == null) {
					//Make sure we have not already sent them a book.
					if (hasBook == false) {
						//Place book in empty slot.
						player.getInventory().setItem(i, fullHelpBook.clone());
						hasBook = true;
					}
				} 
			}
		}
		
		//If we could not place a book, notify the player.
		if (hasBook == false) {
			player.sendMessage(ChatColor.RED + "You do not have room in your inventory for the help book.");

			//Play a sound.
			player.playSound(player.getLocation(), Sound.NOTE_BASS, 1F, 1F);
		}
	}
}