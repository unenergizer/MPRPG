package com.minepile.mprpg.player;

import io.puharesource.mc.titlemanager.api.TabTitleObject;
import io.puharesource.mc.titlemanager.api.TitleObject;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.inventory.BankChestManager;
import com.minepile.mprpg.inventory.InventorySave;

public class PlayerCharacterManager {

	//setup instance variables
	public static MPRPG plugin;
	static PlayerCharacterManager playerCharacterManagerInstance = new PlayerCharacterManager();

	//File paths
	private static String playerFilePathStart = "plugins/MPRPG/players/";
	private static String playerFilePathEnd = ".yml";

	//Holograms
	private static Hologram hologram0, hologram1, hologram2, hologram3;

	//Player Menus
	private static HashMap<UUID, Integer> currentCharacterSlot = new HashMap<UUID, Integer>();
	private static HashMap<UUID, Inventory> characterSelectMenu = new HashMap<UUID, Inventory>();
	private static HashMap<UUID, String> classSelectClickCount = new HashMap<UUID, String>();

	public static Inventory menu;

	//Create instance
	public static PlayerCharacterManager getInstance() {
		return playerCharacterManagerInstance;
	}

	//Setup PlayerMailManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		setupHolograms();

		//Server reloaded.  Force players to go back to character selection screen.
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,  new Runnable() {
			public void run() {
				for (Player players: Bukkit.getOnlinePlayers()) {
					if (!players.hasMetadata("NPC")) {
						initializePlayer(players);
					}
				}
			}
		}, 20L);
	}

	/**
	 * This will disable this class.
	 */
	public static void disable() {
		//Removes holograms.
		removeHolograms();

		//Save any online players characters before disabling.
		for (Player players : Bukkit.getOnlinePlayers()) {
			if (!players.hasMetadata("NPC")) {
				if (PlayerCharacterManager.isPlayerLoaded(players)) {
					PlayerCharacterManager.saveCharacter(players);
				}
			}
		}

		//Clear HashMap.
		characterSelectMenu.clear();
		currentCharacterSlot.clear();
		classSelectClickCount.clear();
	}	

	/**
	 * This creates a Hologram to show over the CharacterSelection boxes.
	 */
	public static void setupHolograms() {
		World world = Bukkit.getWorld("world");
		Location lib0 = new Location(world, 42.5, 82, -285.5);
		Location lib1 = new Location(world, 35.5, 82, -292.5);
		Location lib2 = new Location(world, 42.5, 82, -299.5);
		Location lib3 = new Location(world, 49.5, 82, -292.5);
		String character = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Please Select a Character";
		String click = ChatColor.BOLD + "RIGHT-CLICK" + ChatColor.RESET + ChatColor.GRAY + " or " + ChatColor.RESET + ChatColor.BOLD + "LEFT-CLICK";

		hologram0 = HologramsAPI.createHologram(plugin, lib0);
		hologram0.appendTextLine(character);
		hologram0.appendTextLine(click);

		hologram1 = HologramsAPI.createHologram(plugin, lib1);
		hologram1.appendTextLine(character);
		hologram1.appendTextLine(click);

		hologram2 = HologramsAPI.createHologram(plugin, lib2);
		hologram2.appendTextLine(character);
		hologram2.appendTextLine(click);

		hologram3 = HologramsAPI.createHologram(plugin, lib3);
		hologram3.appendTextLine(character);
		hologram3.appendTextLine(click);
	}

	/**
	 * This will delete the Hologram when server reloads or shuts down.
	 */
	public static void removeHolograms() {
		hologram0.delete();
		hologram1.delete();
		hologram2.delete();
		hologram3.delete();
	}
	
	/**
	 * This will save the state of the current character being played.
	 * 
	 * @param player The player who owns the character.
	 */
	public static void saveCharacter(Player player) {
		UUID uuid = player.getUniqueId();

		//Get player stats.
		double logoutHP = PlayerManager.getHealthPoints(uuid);
		double logoutMaxHP = PlayerManager.getMaxHealthPoints(uuid);
		double logoutStamina = PlayerManager.getStaminaPoints(uuid);
		double logoutMaxStamina = PlayerManager.getMaxStaminaPoints(uuid);
		double logoutMana = PlayerManager.getManaPoints(uuid);
		double logoutMaxMana = PlayerManager.getMaxManaPoints(uuid);
		String experience = Float.toString(player.getExp());
		double x = player.getLocation().getX();
		double y = player.getLocation().getY();
		double z = player.getLocation().getZ();
		
		//Save player stas.
		setPlayerConfigDouble(player, "player.logout.hp", logoutHP);
		setPlayerConfigDouble(player, "player.logout.maxhp", logoutMaxHP);
		setPlayerConfigDouble(player, "player.logout.stamina", logoutStamina);
		setPlayerConfigDouble(player, "player.logout.maxStamina", logoutMaxStamina);
		setPlayerConfigDouble(player, "player.logout.mana", logoutMana);
		setPlayerConfigDouble(player, "player.logout.maxMana", logoutMaxMana);
		setPlayerConfigString(player, "player.playerEXP", experience);
		setPlayerConfigDouble(player, "player.logout.x", x);
		setPlayerConfigDouble(player, "player.logout.y", y);
		setPlayerConfigDouble(player, "player.logout.z", z);
		
		
		//Lets save the players inventory.
		Inventory playerInv = player.getInventory();
		
		for (int i = 0; i <= playerInv.getSize(); i++) {
			ItemStack item = playerInv.getItem(i);
			InventorySave.saveItemStack(player, "playerInv", i, item);
		}
		
		//Lets save the players armor.
		ItemStack boots = player.getEquipment().getBoots();
		ItemStack leggings = player.getEquipment().getLeggings();
		ItemStack chestplate = player.getEquipment().getChestplate();
		ItemStack helmet = player.getEquipment().getHelmet();

		InventorySave.saveItemStack(player, "armorInv", 100, boots);
		InventorySave.saveItemStack(player, "armorInv", 101, leggings);
		InventorySave.saveItemStack(player, "armorInv", 102, chestplate);
		InventorySave.saveItemStack(player, "armorInv", 103, helmet);
		
		//Save the players bank
		BankChestManager.saveBank(player);
	}
	
	/**
	 * This begins the setup of a player who has just logged in.  This
	 * method is also ran if the server reloads, forcing all players to
	 * select a character to play on.
	 * 
	 * @param player The player who has just logged into the server.
	 */
	public static void initializePlayer(Player player) {
		UUID uuid = player.getUniqueId();

		//Check to make sure the player configuration exists.
		//Player configurations are saved with the UUID (Mojang's Unique User Identifier).
		if(!new File(playerFilePathStart + uuid + playerFilePathEnd).exists()){
			//The players file does not exist. Lets create the player file now.
			createPlayerConfig(player);
		}

		//Set player tab menu text
		String header = ChatColor.YELLOW + "" + ChatColor.BOLD + "MinePile" 
				+ ChatColor.WHITE + ChatColor.BOLD + ": RPGMMO " 
				+ plugin.getPluginVersion();
		String footer = ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "www.MinePile.com";
		new TabTitleObject(header, footer).send(player);

		//Change player gamemode to 0, just incase.
		player.setGameMode(GameMode.SURVIVAL);

		//Make sure the players HP and food levels are max.
		player.setHealth(20);
		player.setFoodLevel(20);

		//Set the players exp and level to 0.
		player.setExp(0);
		player.setLevel(0);

		//Clear a players inventory of items and armor.
		player.getInventory().clear();
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);

		//update the players health tag
		if (PlayerHealthTagManager.getSb() != null && PlayerHealthTagManager.getObj() != null) {
			PlayerHealthTagManager.addPlayer(player);
		}
		
		//Teleport players to the character selection location.
		player.teleport(new Location(Bukkit.getWorld("World"), 42.5, 81, -292.5));
				
		//Create character selection menu.
		createMenu(player);
	}

	/**
	 * This is a conditional test to see if the player has finished the character selection and class selection process.
	 * 
	 * @param player The player who is being checked.
	 * @return Returns true if the player has selected their character and class.
	 */
	public static boolean isPlayerLoaded(Player player) {
		if (isCharacterSelected(player) == true && isClassSelected(player) == true) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This will create various menu's for the player logging in.
	 * 
	 * @param player The player who is getting menus created for them.
	 */
	public static void createMenu(Player player) {

		//Lets create some basic items.
		ItemStack newChar = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta meta = newChar.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Create new Character!");
		newChar.setItemMeta(meta);

		//Place the items in the menu.

		Inventory mainMenu = Bukkit.createInventory(player, 9, "Character Selection");
		//Define the items in the Main Menu.

		//Lets loop through 9 inventory slots.
		for (int i = 0; i <= 8; i++) {
			//If the player has a character slot with a class name, then make a stats skull.
			//Otherwise, if a character slot with a class names does not exist, put a "Create new character" icon.
			if (getPlayerConfigString(player, "player.charClass", i).equals("none")) {
				//Create new character icon.
				mainMenu.setItem(i, newChar);
			} else {
				//Make individualized item with character stats.
				mainMenu.setItem(i, createMenuItems(player, i));
			}
		}
		//Menu creation is finished.  Add menu to HashMap for player.
		characterSelectMenu.put(player.getUniqueId(), mainMenu);
	}
	
	/**
	 * This will creat a player skull to represent a already made character.
	 * 
	 * @param player The player who we are making the menu items for.
	 * @param slot The inventory slot number the item will go.
	 * @return Returns an ItemStack of a player skull with various stats on it.
	 */
	public static ItemStack createMenuItems(Player player, int slot) {
		//Get player stats.
		String itemTitle = ChatColor.AQUA + "" +ChatColor.BOLD + "Character " + Integer.toString(slot + 1); 
		String characterName = player.getName();
		String characterClass = getPlayerConfigString(player, "player.charClass", slot);
		int level = getPlayerConfigInt(player, "player.playerLVL", slot);
		int hp = getPlayerConfigInt(player, "player.logout.hp", slot);
		int maxhp = getPlayerConfigInt(player, "player.logout.maxhp", slot);
		int stamina = getPlayerConfigInt(player, "player.logout.stamina", slot);
		int maxStamina = getPlayerConfigInt(player, "player.logout.maxStamina", slot);
		int mana = getPlayerConfigInt(player, "player.logout.mana", slot);
		int maxMana = getPlayerConfigInt(player, "player.logout.maxMana", slot);
		String guild = getPlayerConfigString(player, "guild.name", slot);
		
		//Add stast to array list.
		List lores = Arrays.asList(" ",
				ChatColor.GREEN + "Name: " + ChatColor.GRAY + characterName,
				ChatColor.GREEN + "Guild: " + ChatColor.GRAY + guild,
				" ",
				ChatColor.GREEN + "Class: " + ChatColor.GRAY + characterClass,
				ChatColor.GREEN + "Level: " + ChatColor.GRAY + level,
				ChatColor.GREEN + "HP: " + ChatColor.GRAY + hp + "/" + maxhp,
				ChatColor.GREEN + "Mana: " + ChatColor.GRAY + mana + "/" + maxMana,
				ChatColor.GREEN + "Stamina: " + ChatColor.GRAY + stamina + "/" + maxStamina);
		
		//Make the skull item.
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
		SkullMeta skullMeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
		skullMeta.setOwner(player.getName());
		skullMeta.setDisplayName(itemTitle);
		skullMeta.setLore(lores);
		skull.setItemMeta(skullMeta);
		return skull;
	}

	/**
	 * This will be toggled when a player left-clicks or right clicks an bookshelf.
	 * 
	 * @param player The player who clicked the bookshelf.
	 */
	public static void toggleCharacterSelectionMenu(Player player) {
		player.playSound(player.getLocation(), Sound.CHEST_OPEN, .8f, .8f);
		player.openInventory(characterSelectMenu.get(player.getUniqueId()));
	}
	
	/**
	 * This happens when a player selects a menu item from the character selection menu.
	 * 
	 * @param player The player who has made a menu selection.
	 * @param slot The slot the player clicked.
	 */
	public static void toggleCharacterSelectionInteract(Player player, int slot) {
		UUID uuid = player.getUniqueId();

		//Set the player slot (character) the player choose.
		setCurrentCharacterSlot(uuid, slot);

		if (isClassSelected(player) == true) {
			//Teleport players to their last logout location.
			double x = getPlayerConfigDouble(player, "player.logout.x");
			double y = getPlayerConfigDouble(player, "player.logout.y") + 1;
			double z = getPlayerConfigDouble(player, "player.logout.z");
			Location loc = new Location(Bukkit.getWorld("World"), x, y, z);
			
			PlayerManager.teleportPlayerToSpawn(player, loc);

			//Remove character selection menu from memory.
			characterSelectMenu.remove(player.getUniqueId());

			//Setup the player
			PlayerManager.setupPlayer(player);

		} else {
			//Teleport new players to the character class selection location.
			player.teleport(new Location(Bukkit.getWorld("World"), 42.5, 81, -175.5));
		}
	}

	/**
	 * This happens when a new character is created and they have selected their class.
	 * 
	 * @param player The player that is getting a class assigned.
	 * @param charClass The class the player choose.  Examples: archer, mage, rouge, and warrior.
	 */
	public static void toggleClassSelectionInteract(Player player, String charClass) {
		UUID uuid = player.getUniqueId();
		
		//Enable click counting for confirmation.
		if (!classSelectClickCount.containsKey(uuid)) {
			
			toggleClassSelectionText(player, charClass);
			classSelectClickCount.put(uuid, charClass);
			
		} else if (classSelectClickCount.get(uuid).equals(charClass)) {
			
			//Lets create the info needed to make a new player character.
			createPlayerCharacter(player);
	
			//Set the player class the player choose.
			setPlayerClassSelection(player, charClass);
	
			//Teleport new players to the main spawn location.
			PlayerManager.teleportPlayerToSpawn(player);
	
			//Setup the player
			PlayerManager.setupPlayer(player);

			//Show Success Message.
			new TitleObject(ChatColor.GREEN + "Success!", ChatColor.GOLD + "You character has been created!").send(player);
			
			player.playSound(player.getLocation(), Sound.LEVEL_UP, 1f, 1f);
			
			//Remove the player from the ClickCount HashMap.
			classSelectClickCount.remove(uuid);
		} else {
			toggleClassSelectionText(player, charClass);
			classSelectClickCount.put(uuid, charClass);
		}
	}
	
	/**
	 * Displays information about the class that was clicked.
	 * 
	 * @param player The player who clicked a class NPC.
	 * @param charClass The name of the class that was clicked.
	 */
	private static void toggleClassSelectionText(Player player, String charClass) {
		//Send Blank Message
		player.sendMessage("");
		player.sendMessage("");
		
		//Show class specific title and info.
		if (charClass.equalsIgnoreCase("ARCHER")) {
			player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "-------------------" +
					ChatColor.DARK_GRAY + "<[" +
					ChatColor.GOLD + " Archer " + ChatColor.DARK_GRAY + "]>" +
					ChatColor.BOLD + "-----------------");
			player.sendMessage("");
			player.sendMessage(ChatColor.GREEN + "Archers are associated with the wisdom of nature. Archers tend to be wise, cunning, and perceptive.  Many are skilled in stealth, wilderness survival, herbalism, and tracking. Archery and swordplay are common to them, though there are many instances where archers use a variety of weapons, skills, and sometimes magic or have a resistance to magic.");
			
		} else if (charClass.equalsIgnoreCase("MAGE")) {
			player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "-------------------" +
					ChatColor.DARK_GRAY + "<[" +
					ChatColor.GOLD + " Mage " + ChatColor.DARK_GRAY + "]>" +
					ChatColor.BOLD + "-------------------");
			player.sendMessage("");
			player.sendMessage(ChatColor.GREEN + "Magi are wizards of immense knowledge and skill. Their obvious physical frailty is deceptive, for they can call upon the cosmic energies of the Twisting Nether.  Rarely do magi engage in melee combat.  Instead, they prefer to attack from a distance, hurling powerful bolts of frost and flame at their unsuspecting enemies.");
			
		} else if (charClass.equalsIgnoreCase("ROGUE")) {
			player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "-------------------" +
					ChatColor.DARK_GRAY + "<[" +
					ChatColor.GOLD + " Rogue " + ChatColor.DARK_GRAY + "]>" +
					ChatColor.BOLD + "------------------");
			player.sendMessage("");
			player.sendMessage(ChatColor.GREEN + "Rogues are most successful when their deeds never come to light.  Fond of poisons and silent projectile weapons, rogues rely on a blend of stealth and minor mysticism.  Usually employed by rich nobles or local governments, the rogue redistributes wealth or eliminates designated targets. A rogue's allegiance lasts only as long as the latest contract.");
			
		} else if (charClass.equalsIgnoreCase("WARRIOR")) {
			player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "-------------------" +
					ChatColor.DARK_GRAY + "<[" +
					ChatColor.GOLD + " Warrior " + ChatColor.DARK_GRAY + "]>" +
					ChatColor.BOLD + "-----------------");
			player.sendMessage("");
			player.sendMessage(ChatColor.GREEN + "Warriors train constantly and strive for perfection in armed combat.  Though they come from all walks of life, they are united by their singular commitment to engage in glorious battle.  Many warriors become adventurers and danger-seeking fortune hunters.  A typical warrior is strong, tough, and exceptionally violent.");
		}

		player.sendMessage(ChatColor.YELLOW + "Click again to comfirm your selection.");
		player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD +"---------------------------------------------");
	}

	/**
	 * The player has logged out before selecting a character and a class.
	 * Lets remove them from any HashMaps.
	 * 
	 * @param player The player that logged out.
	 */
	public static void removePlayer(Player player) {
		currentCharacterSlot.remove(player.getUniqueId());
	}
	
	/**
	 * This creates additional configuration information based on what character slot the player clicked.
	 * @param player The player who is having config info generated for them.
	 */
	public static void createPlayerCharacter(Player player) {

		String uuid = player.getUniqueId().toString();
		String slot = Integer.toString(getCurrentCharacterSlot(player));

		File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
		FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);

		playerConfig.set(slot + ".player.charClass", "none");
		playerConfig.set(slot + ".player.playerLVL", 1);
		playerConfig.set(slot + ".player.playerEXP", 0);
		playerConfig.set(slot + ".player.logout.hp", 100);
		playerConfig.set(slot + ".player.logout.maxhp", 100);
		playerConfig.set(slot + ".player.logout.stamina", 100);
		playerConfig.set(slot + ".player.logout.maxStamina", 100);
		playerConfig.set(slot + ".player.logout.mana", 100);
		playerConfig.set(slot + ".player.logout.maxMana", 100);
		playerConfig.set(slot + ".player.logout.x", 43);
		playerConfig.set(slot + ".player.logout.y", 76);
		playerConfig.set(slot + ".player.logout.z", -34);

		playerConfig.set(slot + ".guild.owner", false);
		playerConfig.set(slot + ".guild.name", null);

		playerConfig.set(slot + ".permissions.admin", 0);
		playerConfig.set(slot + ".permissions.dev", 0);
		playerConfig.set(slot + ".permissions.mod", 0);

		playerConfig.set(slot + ".setting.chat.languagefilter", 1);
		playerConfig.set(slot + ".setting.chat.focus", "local");
		playerConfig.set(slot + ".setting.chat.lastpm", null);

		playerConfig.set(slot + ".setting.chat.healthDebug", true);
		playerConfig.set(slot + ".setting.chat.monsterDebug", true);
		playerConfig.set(slot + ".setting.chat.professionDebug", true);

		playerConfig.set(slot + ".setting.chatchannel.admin", 1);
		playerConfig.set(slot + ".setting.chatchannel.global", 1);
		playerConfig.set(slot + ".setting.chatchannel.guild", 1);
		playerConfig.set(slot + ".setting.chatchannel.help", 1);
		playerConfig.set(slot + ".setting.chatchannel.local", 1);
		playerConfig.set(slot + ".setting.chatchannel.mod", 1);
		playerConfig.set(slot + ".setting.chatchannel.party", 1);
		playerConfig.set(slot + ".setting.chatchannel.pm", 1);
		playerConfig.set(slot + ".setting.chatchannel.trade", 1);

		playerConfig.set(slot + ".economy.gold", 0);
		playerConfig.set(slot + ".economy.silver", 0);
		playerConfig.set(slot + ".economy.copper", 15);
		playerConfig.set(slot + ".economy.portalCash", 0);
		playerConfig.set(slot + ".economy.bankRows", 2);
		playerConfig.set(slot + ".economy.shopRows", 1);

		try {
			playerConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new configuration file on a players first visit to the server.
	 */
	private static void createPlayerConfig(Player player) {

		String uuid = player.getUniqueId().toString();
		String playerName = player.getName();

		File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
		FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
		playerConfig.set("playerName", playerName);
		playerConfig.set("lastCharacterPlayed", 0);
		playerConfig.set("0.player.charClass", "none");
		playerConfig.set("1.player.charClass", "none");
		playerConfig.set("2.player.charClass", "none");
		playerConfig.set("3.player.charClass", "none");
		playerConfig.set("4.player.charClass", "none");
		playerConfig.set("5.player.charClass", "none");
		playerConfig.set("6.player.charClass", "none");
		playerConfig.set("7.player.charClass", "none");
		playerConfig.set("8.player.charClass", "none");

		try {
			playerConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static boolean isCharacterSelected(Player player) {
		UUID uuid = player.getUniqueId();

		if (currentCharacterSlot.get(uuid) != null) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isClassSelected(Player player) {
		String currentClass = getPlayerClass(player);

		if (!currentClass.equals("none")) {
			return true;
		} else {
			return false;
		}
	}

	public static String getPlayerClass(Player player) {
		return getPlayerConfigString(player,"player.charClass");
	}

	public static void setPlayerClassSelection(Player player, String rpgClass) {
		String currentClass = getPlayerClass(player);

		//Make sure the player can not change their class after it has been selected.
		//If no class exists, let them set it now.
		if (currentClass.equals("none")) {
			setPlayerConfigString(player, "player.charClass", rpgClass);
		} else {
			player.sendMessage(ChatColor.RED + "You already selected the " + currentClass + " class!");
		}
	}


	public static void setCurrentCharacterSlot(UUID uuid, int slot) {
		currentCharacterSlot.put(uuid, slot);
	}

	/**
	 * Gets the current character or character slot the player is playing on.
	 * 
	 * @param player The player that is playing the game.
	 * @return The character slot number the player is playing on.
	 */
	public static int getCurrentCharacterSlot(Player player) {
		UUID uuid = player.getUniqueId();
		int slot = currentCharacterSlot.get(uuid);
		return slot;
	}

	public static Inventory getCharacterSelectMenu(Player player) {
		return characterSelectMenu.get(player.getUniqueId());
	}

	public static void setPlayerConfigDouble(Player player, String config, double value) {

		String uuid = player.getUniqueId().toString();
		String slot = Integer.toString(getCurrentCharacterSlot(player));

		File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
		FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
		playerConfig.set(slot + "." + config, value);

		try {
			playerConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public static double getPlayerConfigDouble(Player player, String value) {

		String uuid = player.getUniqueId().toString();
		String slot = Integer.toString(getCurrentCharacterSlot(player));

		File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
		FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
		return playerConfig.getDouble(slot + "." + value);
	}

	public static double getPlayerConfigDouble(Player player, String value, int slot) {

		String uuid = player.getUniqueId().toString();

		File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
		FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
		return playerConfig.getDouble(slot + "." + value);
	}

	public static void setPlayerConfigInt(Player player, String config, int value) {

		String uuid = player.getUniqueId().toString();
		String slot = Integer.toString(getCurrentCharacterSlot(player));

		File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
		FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
		playerConfig.set(slot + "." + config, value);

		try {
			playerConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public static int getPlayerConfigInt(Player player, String value) {

		String uuid = player.getUniqueId().toString();
		String slot = Integer.toString(getCurrentCharacterSlot(player));

		File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
		FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
		return playerConfig.getInt(slot + "." + value);
	}

	public static int getPlayerConfigInt(Player player, String value, int slot) {

		String uuid = player.getUniqueId().toString();

		File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
		FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
		return playerConfig.getInt(slot + "." + value);
	}

	public static boolean getPlayerConfigBoolean(Player player, String value) {

		String uuid = player.getUniqueId().toString();
		String slot = Integer.toString(getCurrentCharacterSlot(player));

		File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
		FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
		return playerConfig.getBoolean(slot + "." + value);
	}

	public static boolean getPlayerConfigBoolean(Player player, String value, int slot) {

		String uuid = player.getUniqueId().toString();

		File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
		FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
		return playerConfig.getBoolean(slot + "." + value);
	}

	public static void setPlayerConfigBoolean(Player player, String config, boolean value) {

		String uuid = player.getUniqueId().toString();
		String slot = Integer.toString(getCurrentCharacterSlot(player));

		File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
		FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
		playerConfig.set(slot + "." + config, value);

		try {
			playerConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public static void setPlayerConfigString(Player player, String config, String value) {

		String uuid = player.getUniqueId().toString();
		String slot = Integer.toString(getCurrentCharacterSlot(player));

		File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
		FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
		playerConfig.set(slot + "." + config, value);

		try {
			playerConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public static String getPlayerConfigString(Player player, String value) {

		String uuid = player.getUniqueId().toString();
		String slot = Integer.toString(getCurrentCharacterSlot(player));

		File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
		FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
		return playerConfig.getString(slot + "." + value);
	}

	public static String getPlayerConfigString(Player player, String value, int slot) {

		String uuid = player.getUniqueId().toString();

		File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
		FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
		return playerConfig.getString(slot + "." + value);
	}

}
