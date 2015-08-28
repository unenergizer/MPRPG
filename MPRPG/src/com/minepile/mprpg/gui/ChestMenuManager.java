package com.minepile.mprpg.gui;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.professions.Fishing;

public class ChestMenuManager {
	
	//setup instance variables
	public static MPRPG plugin;
	public static ChestMenuManager instance = new ChestMenuManager();
	
	//Menu Name Prefix
	private static String namePrefix = ChatColor.YELLOW + "" + ChatColor.BOLD;
	
	//Menu Names
	private static String alchemistStand = "Alchemy Stand";
	private static String alchemistTrainer = "Alchemist Trainer";
	private static String blacksmithAnvil = "Blacksmith Anvil";
	private static String blacksmithTrainer = "Blacksmith Trainer";
	private static String cookingTrainer = "Cooking Trainer";
	private static String fishingTrainer = "Fishing Trainer";
	private static String herbalismTrainer = "Herbalist Trainer";
	private static String innKeeper = "Inn Keeper";
	private static String itemMerchant = "Item Store";
	private static String miningTrainer = "Mining Trainer";
	
	//MinePile menu related names.
	private static String mpMenuAchievements = "Player Achievements";	
	private static String mpMenuCosmetic = "Cosmentic Items";	
	private static String mpMenuPremiumStore = "eCash Store";
	private static String mpMenuGuild = "Guild";	
	private static String mpMenuMain = "MinePile Menu";	
	private static String mpMenuPets = "Pet Menu";
	private static String mpMenuRules = "Rules";	
	private static String mpMenuSettings = "Game Settings";	
	private static String mpMenuStats = "Statistics";	
	private static String mpMenuTracking = "Tracking Device";
	
	//Create instance
	public static ChestMenuManager getInstance() {
		return instance;
	}
	
	//Setup ChestMenuManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * This ENUM defines what type of menus exist in game.
	 * 
	 * @author Andrew
	 */
	public static enum MenuType {
		
		//NPC related menus.
		ALCHEMIST_STAND (namePrefix + alchemistStand),	
		ALCHEMIST_TRAINER (namePrefix + alchemistTrainer),	
		BLACKSMITH_ANVIL (namePrefix + blacksmithAnvil),	
		BLACKSMITH_TRAINER (namePrefix + blacksmithTrainer),	
		COOKING_TRAINER (namePrefix + cookingTrainer),	
		FISHING_TRAINER (namePrefix + fishingTrainer),	
		HERBALISM_TRAINER (namePrefix + herbalismTrainer),	
		INN_KEEPER (namePrefix + innKeeper),	
		ITEM_MERCHANT (namePrefix + itemMerchant),	
		MINING_TRAINER (namePrefix + miningTrainer),
		
		//MinePile related menus.
		MP_MENU_ACHIEVEMENTS (namePrefix + mpMenuAchievements),	
		MP_MENU_COSMETIC (namePrefix + mpMenuCosmetic),	
		MP_MENU_ECASH_STORE (namePrefix + mpMenuPremiumStore),	
		MP_MENU_GUILD (namePrefix + mpMenuGuild),	
		MP_MENU_MAIN (namePrefix + mpMenuMain),	
		MP_MENU_PET (namePrefix + mpMenuPets),		
		MP_MENU_RULES (namePrefix + mpMenuRules),	
		MP_MENU_SETTINGS (namePrefix + mpMenuSettings),	
		MP_MENU_STATS (namePrefix + mpMenuStats),	
		MP_MENU_TRACKING (namePrefix + mpMenuTracking);

		private String name;

		MenuType(String s) {
			this.name = s;
		}

		public String getName() {
			return name;
		}
	}
	
	/**
	 * This will toggle an action based on the item the player clicked in a menu.
	 * 
	 * @param player The player who clicked an item in the menu.
	 * @param invName The name of the inventory/menu that was opened.
	 * @param clickedItem The item that the player clicked.
	 */
	public static void toggleChestMenuClick(Player player, String invName, ItemStack clickedItem) {
		MenuType menu = getMenuFromString(invName);
		
		switch(menu) {
		case ALCHEMIST_STAND:
			break;
		case ALCHEMIST_TRAINER:
			break;
		case BLACKSMITH_ANVIL:
			break;
		case BLACKSMITH_TRAINER:
			break;
		case COOKING_TRAINER:
			break;
		case FISHING_TRAINER:
			Fishing.toggleGiveFishinRod(player, clickedItem);
			break;
		case HERBALISM_TRAINER:
			break;
		case INN_KEEPER:
			break;
		case ITEM_MERCHANT:
			break;
		case MINING_TRAINER:
			break;
		case MP_MENU_ACHIEVEMENTS:
			break;
		case MP_MENU_COSMETIC:
			break;
		case MP_MENU_ECASH_STORE:
			break;
		case MP_MENU_GUILD:
			break;
		case MP_MENU_MAIN:
			break;
		case MP_MENU_PET:
			break;
		case MP_MENU_RULES:
			break;
		case MP_MENU_SETTINGS:
			break;
		case MP_MENU_STATS:
			break;
		case MP_MENU_TRACKING:
			break;
		default:
			break;
		}
		
	}

	private static MenuType getMenuFromString(String menuName) {
		if (menuName.equalsIgnoreCase(MenuType.ALCHEMIST_STAND.getName())) {
			return MenuType.ALCHEMIST_STAND;
		} else if (menuName.equalsIgnoreCase(MenuType.ALCHEMIST_TRAINER.getName())) {
			return MenuType.ALCHEMIST_TRAINER;
		} else if (menuName.equalsIgnoreCase(MenuType.BLACKSMITH_ANVIL.getName())) {
			return MenuType.BLACKSMITH_ANVIL;
		} else if (menuName.equalsIgnoreCase(MenuType.BLACKSMITH_TRAINER.getName())) {
			return MenuType.BLACKSMITH_TRAINER;
		} else if (menuName.equalsIgnoreCase(MenuType.COOKING_TRAINER.getName())) {
			return MenuType.COOKING_TRAINER;
		} else if (menuName.equalsIgnoreCase(MenuType.FISHING_TRAINER.getName())) {
			return MenuType.FISHING_TRAINER;
		} else if (menuName.equalsIgnoreCase(MenuType.HERBALISM_TRAINER.getName())) {
			return MenuType.HERBALISM_TRAINER;
		} else if (menuName.equalsIgnoreCase(MenuType.INN_KEEPER.getName())) {
			return MenuType.INN_KEEPER;
		} else if (menuName.equalsIgnoreCase(MenuType.ITEM_MERCHANT.getName())) {
			return MenuType.ITEM_MERCHANT;
		} else if (menuName.equalsIgnoreCase(MenuType.MINING_TRAINER.getName())) {
			return MenuType.MINING_TRAINER;
		} else if (menuName.equalsIgnoreCase(MenuType.MP_MENU_ACHIEVEMENTS.getName())) {
			return MenuType.MP_MENU_ACHIEVEMENTS;
		} else if (menuName.equalsIgnoreCase(MenuType.MP_MENU_COSMETIC.getName())) {
			return MenuType.MP_MENU_COSMETIC;
		} else if (menuName.equalsIgnoreCase(MenuType.MP_MENU_ECASH_STORE.getName())) {
			return MenuType.MP_MENU_ECASH_STORE;
		} else if (menuName.equalsIgnoreCase(MenuType.MP_MENU_GUILD.getName())) {
			return MenuType.MP_MENU_GUILD;
		} else if (menuName.equalsIgnoreCase(MenuType.MP_MENU_MAIN.getName())) {
			return MenuType.MP_MENU_MAIN;
		} else if (menuName.equalsIgnoreCase(MenuType.MP_MENU_PET.getName())) {
			return MenuType.MP_MENU_PET;
		} else if (menuName.equalsIgnoreCase(MenuType.MP_MENU_RULES.getName())) {
			return MenuType.MP_MENU_RULES;
		} else if (menuName.equalsIgnoreCase(MenuType.MP_MENU_SETTINGS.getName())) {
			return MenuType.MP_MENU_SETTINGS;
		} else if (menuName.equalsIgnoreCase(MenuType.MP_MENU_STATS.getName())) {
			return MenuType.MP_MENU_STATS;
		} else if (menuName.equalsIgnoreCase(MenuType.MP_MENU_TRACKING.getName())) {
			return MenuType.MP_MENU_TRACKING;
		} else {
			return null;
		}
	}
}
