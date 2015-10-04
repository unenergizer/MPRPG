package com.minepile.mprpg.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.util.ItemBuilder;

public class ChestMenuManager {

	//setup instance variables
	public static MPRPG plugin;
	public static ChestMenuManager instance = new ChestMenuManager();

	//Configuration files
	private static File itemsConfigFile;
	private static FileConfiguration menuItemsConfig;
	private static File pagesConfigFile;
	private static FileConfiguration menuPagesConfig;
	private static String FILE_PATH = "plugins/MPRPG/gui/";
	private static String ITEM_FILE = "items.yml";
	private static String PAGE_FILE = "pages.yml";

	//Protect inventory hashmap.
	private static ConcurrentHashMap<UUID, Boolean> inventoryProtected = new ConcurrentHashMap<UUID, Boolean>();

	//Create instance
	public static ChestMenuManager getInstance() {
		return instance;
	}

	//Setup ChestMenuManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		//If citizens configuration does not exist, create the file. Otherwise lets load the file.
		if(!(new File(FILE_PATH + ITEM_FILE)).exists() || !(new File(FILE_PATH + PAGE_FILE).exists())) {
			createConfig();
		} else {
			//lets load the configuration file.
			itemsConfigFile = new File(FILE_PATH + ITEM_FILE);
			menuItemsConfig =  YamlConfiguration.loadConfiguration(itemsConfigFile);

			pagesConfigFile = new File(FILE_PATH + PAGE_FILE);
			menuPagesConfig =  YamlConfiguration.loadConfiguration(pagesConfigFile);
		}

		//Build menu pages
		//buildMenuPage();
	}

	public static void protectInventoryFromPlayer(Player player, String invName) {
		String invNameFormatted = invName.replace(" ", "_");
		if (getMenuExistsInConfig(invNameFormatted) == true) {
			addPlayerToProtectedInventory(player);
		} else if (invName.equalsIgnoreCase(ChatColor.stripColor("Game Menu")) || invName.equalsIgnoreCase(ChatColor.stripColor("Game Menu"))) {
			addPlayerToProtectedInventory(player);
		}
	}

	public static void addPlayerToProtectedInventory(Player player) {
		UUID id = player.getUniqueId();
		inventoryProtected.put(id, true);
	}

	public static void removePlayerFromProtectedInventory(Player player) {
		UUID id = player.getUniqueId();
		inventoryProtected.remove(id);
	}

	public static boolean isInventoryProtectedFromPlayer(Player player) {
		UUID id = player.getUniqueId();
		if (inventoryProtected.containsKey(id)) {
			return true;
		} else {
			return false;
		}
	}

	public static Inventory buildMenuPage(Player player, String pageName) {
		//Get config page
		String owner = menuPagesConfig.getString(pageName + ".owner");	
		int rows = menuPagesConfig.getInt(pageName + ".rows");
		int size = 9 * rows;
		int loopSize = size - 1; //We subtract 1 from the loop size, because an inventory starts at 0 and not 1.
		Inventory menu;

		if (owner.equalsIgnoreCase("player")) {
			menu = Bukkit.createInventory(player, size, pageName.replace("_", " "));
		} else {
			menu = Bukkit.createInventory(null, size, pageName.replace("_", " "));
		}

		//Define and place the items in the menu.
		for (int i = 0; i <= loopSize; i++) {

			try {
				String itemName = menuPagesConfig.getString(pageName + "." + Integer.toString(i));
				ItemStack item = buildItem(itemName);
				menu.setItem(i, item);
			} catch (NullPointerException exception) { }

		}
		return menu;
	}

	private static ItemStack buildItem(String itemName) {

		String name = menuItemsConfig.getString(itemName + ".name").replace("_", " ");
		String colorName = ChatColor.translateAlternateColorCodes('&', name);
		int itemId = menuItemsConfig.getInt(itemName + ".id");
		List<String> itemDescription = menuItemsConfig.getStringList(itemName + ".lore");

		Material mat = Material.getMaterial(itemId);
		ItemStack item;

		if (!mat.equals(Material.STAINED_GLASS_PANE)) {
			if (itemDescription != null) {
				List<String> colorDescription = new ArrayList<>();

				for (String string : itemDescription) {
					colorDescription.add(ChatColor.translateAlternateColorCodes('&', string.replace("*", "•")));
				}

				item = new ItemBuilder(mat).setTitle(colorName).addLores(colorDescription).build();
			} else {
				item = new ItemBuilder(mat).setTitle(colorName).build();
			}
		} else {
			//Set black GUI pain.
			item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(" ");
			
			//Set the item meta
			item.setItemMeta(meta);
		}

		return item;
	}

	private void getItemAction(String itemName) {
		String itemAction = menuItemsConfig.getString(itemName + ".action");
		//gets the items action.
	}

	/**
	 * Creates a new configuration file on a players first visit to the server.
	 */
	private static void createConfig() {

		itemsConfigFile = new File(FILE_PATH + ITEM_FILE);
		pagesConfigFile = new File(FILE_PATH + PAGE_FILE);

		menuItemsConfig =  YamlConfiguration.loadConfiguration(itemsConfigFile);
		menuPagesConfig =  YamlConfiguration.loadConfiguration(pagesConfigFile);

		//Menu Items
		menuItemsConfig.set("Test_Item", "Test_Item");
		menuItemsConfig.set("Test_Item.name", "Merchant");
		menuItemsConfig.set("Test_Item.id", 30);
		menuItemsConfig.set("Test_Item.lore", "This is a description");
		menuItemsConfig.set("Test_Item.action", "PRINT_STRING");

		//Menu Pages
		menuPagesConfig.set("Main_Menu", "Main_Menu");
		menuPagesConfig.set("Main_Menu.owner", "player");
		menuPagesConfig.set("Main_Menu.rows", 6);
		menuPagesConfig.set("Main_Menu.0", "Test_Item");
		menuPagesConfig.set("Main_Menu.10", "Test_Item");
		menuPagesConfig.set("Main_Menu.20", "Test_Item");
		menuPagesConfig.set("Main_Menu.30", "Test_Item");


		try {
			menuItemsConfig.save(itemsConfigFile);
			menuPagesConfig.save(pagesConfigFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public static boolean getMenuExistsInConfig(String invName) {
		String name = menuPagesConfig.getString(invName + ".owner");
		if (name != null) {
			return true;
		} else {
			return false;
		}
	}
}
