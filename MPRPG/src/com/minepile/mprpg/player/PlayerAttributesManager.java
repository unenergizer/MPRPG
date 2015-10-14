package com.minepile.mprpg.player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.items.ItemLoreFactory;

public class PlayerAttributesManager {

	//setup instance variables
	public static MPRPG plugin;
	static PlayerAttributesManager playerAttributesManagerInstance = new PlayerAttributesManager();

	//Hashmap
	private static HashMap<UUID, Inventory> attributeAssignMenu = new HashMap<UUID, Inventory>();

	//Create instance
	public static PlayerAttributesManager getInstance() {
		return playerAttributesManagerInstance;
	}

	//Setup PlayerAttributeManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

	}
	
	/**
	 * Base Stats:
	 * 	Strength
	 * 	Agility
	 * 	Stamina
	 * 	Intellect
	 * 	Spirit
	 * 	Armor
	 * 	Damage
	 * 
	 *  Reflect
	 *  Block
	 *  Dodge
	 * 	Critical Damage
	 *  Critical Chance
	 * 
	 * Magic Damage:
	 * 	Fire Damage
	 * 	Ice Damage
	 * 	Lightning Damage
	 * 	Poison Damage
	 * 	Paralyze Damage
	 * 	Blindness Damage
	 * 
	 * Resistances:
	 * 	Fire Resistance
	 * 	Ice Resistance
	 * 	Lightning Resistance
	 * 	Poison Resistance
	 * 	Paralyze Resistance
	 *  Blindness Resistance
	 *  
	 * Extras:
	 * 	Waterbreathing
	 * 	Personality
	 *  Gold Find
	 *  Magic Find
	 *  Knockback
	 *  Lifesteal
	 */
	
	//Archer base attributes
	private static int archerStrength = 22;
	private static int archerAgility = 19;
	private static int archerVitality = 24;
	private static int archerIntellect = 19;
	private static int archerSpirit = 20;
	private static int archerArmor = 38;

	//Mage base attributes
	private static int mageStrength = 15;
	private static int mageAgility = 23;
	private static int mageVitality = 19;
	private static int mageIntellect = 27;
	private static int mageSpirit = 22;
	private static int mageArmor = 46;

	//Rogue base attributes
	private static int rogueStrength = 21;
	private static int rogueAgility = 23;
	private static int rogueVitality = 21;
	private static int rogueIntellect = 20;
	private static int rogueSpirit = 21;
	private static int rogueArmor = 46;

	//Warrior base attributes
	private static int warriorStrength = 23;
	private static int warriorAgility = 20;
	private static int warriorVitality = 22;
	private static int warriorIntellect = 20;
	private static int warriorSpirit = 22;
	private static int warriorArmor = 40;

	/**
	 * This creates a menu for the player that will allow them to assign attribute points to their character.
	 * Note: Characters get attribute points to spend after they level up.
	 * 
	 * @param player The player we are creating the personalized menu for.
	 */
	public static void createAttributeAssignMenu(Player player) {
		UUID uuid = player.getUniqueId();

		//Lets get the players current attribute points:
		double currentStrength = PlayerCharacterManager.getPlayerConfigDouble(player, "player.attributes.strength");
		double currentAgility = PlayerCharacterManager.getPlayerConfigDouble(player, "player.attributes.agility");
		double currentVitality = PlayerCharacterManager.getPlayerConfigDouble(player, "player.attributes.vitality");
		double currentIntellect = PlayerCharacterManager.getPlayerConfigDouble(player, "player.attributes.intellect");
		double currentSpirit = PlayerCharacterManager.getPlayerConfigDouble(player, "player.attributes.spirit");


		//Lets create some basic items.
		ItemStack strength = new ItemStack(Material.PAPER, 1);
		ItemStack agility = new ItemStack(Material.PAPER, 1);
		ItemStack vitality = new ItemStack(Material.PAPER, 1);
		ItemStack intellect = new ItemStack(Material.PAPER, 1);
		ItemStack spirit = new ItemStack(Material.PAPER, 1);
		ItemStack confirm = new ItemStack(Material.INK_SACK, 1, (short) 10);

		//Get the meta data of the items
		ItemMeta metaStrength = strength.getItemMeta();
		ItemMeta metaAgility = agility.getItemMeta();
		ItemMeta metaVitality = vitality.getItemMeta();
		ItemMeta metaIntellect = intellect.getItemMeta();
		ItemMeta metaSpirit = spirit.getItemMeta();
		ItemMeta metaConfirm = spirit.getItemMeta();

		//Set the display name of the items.
		metaStrength.setDisplayName(ChatColor.GREEN + "Strength");
		metaAgility.setDisplayName(ChatColor.GREEN + "Agility");
		metaVitality.setDisplayName(ChatColor.GREEN + "Vitality");
		metaIntellect.setDisplayName(ChatColor.GREEN + "Intellect");
		metaSpirit.setDisplayName(ChatColor.GREEN + "Spirit");
		metaConfirm.setDisplayName(ChatColor.AQUA + "Click to Confirm your Selection.");

		//Lets create an array for each item.
		ArrayList<String> listStrength = new ArrayList<String>();
		ArrayList<String> listAgility = new ArrayList<String>();
		ArrayList<String> listVitality = new ArrayList<String>();
		ArrayList<String> listIntellect = new ArrayList<String>();
		ArrayList<String> listSpirit = new ArrayList<String>();

		////////////////////////////////////////
		// Lets add the data to each item now //
		////////////////////////////////////////

		String clickToAdd = ChatColor.GRAY + "Click to add one point" + ChatColor.DARK_GRAY + ".";
		String currentValues = ChatColor.LIGHT_PURPLE + "" +ChatColor.BOLD + "Current Values" + ChatColor.DARK_GRAY + ":";
		String percent = ChatColor.GRAY + "%";

		//Strength
		listStrength.add(clickToAdd);
		listStrength.add("");
		listStrength.add(currentValues);
		listStrength.add(ChatColor.GRAY + "Melee Damage: " + ChatColor.RED + new DecimalFormat("##.###").format(currentStrength * 0.001) + percent);
		listStrength.add(ChatColor.GRAY + "Unarmed Damage: " + ChatColor.RED + new DecimalFormat("##.###").format(currentStrength * 0.002) + percent);
		listStrength.add("");
		listStrength.add("");
		listStrength.add(ChatColor.YELLOW + "Increases damage with melee weapons" + ChatColor.DARK_GRAY + ".");

		//Agility
		listAgility.add(clickToAdd);
		listAgility.add("");
		listAgility.add(currentValues);
		listAgility.add(ChatColor.GRAY + "Armor: " + ChatColor.RED + new DecimalFormat("##.###").format(currentAgility * 0.05) + percent);
		listAgility.add(ChatColor.GRAY + "Ranged Damage: " + ChatColor.RED + new DecimalFormat("##.###").format(currentAgility * 0.001) + percent);
		listAgility.add(ChatColor.GRAY + "Dodge Chance: " + ChatColor.RED + new DecimalFormat("##.###").format(currentAgility * 0.05) + percent);
		listAgility.add(ChatColor.GRAY + "Critical Hit Chance: " + ChatColor.RED + new DecimalFormat("##.###").format(currentAgility * 0.05) + percent);
		listAgility.add("");
		listAgility.add("");
		listAgility.add(ChatColor.YELLOW + "Increases armor" + ChatColor.DARK_GRAY + ".");
		listAgility.add(ChatColor.YELLOW + "Increases damage with ranged weapons" + ChatColor.DARK_GRAY + ".");
		listAgility.add(ChatColor.YELLOW + "Increases chance to dodge an attack" + ChatColor.DARK_GRAY + ".");
		listAgility.add(ChatColor.YELLOW + "Increases critical hit chance" + ChatColor.DARK_GRAY + ".");

		//Vitality
		listVitality.add(clickToAdd);
		listVitality.add("");
		listVitality.add(currentValues);
		listVitality.add(ChatColor.GRAY + "Hit Points: " + ChatColor.RED + new DecimalFormat("##.###").format(currentVitality * 10));
		listVitality.add(ChatColor.GRAY + "Stamina Points: " + ChatColor.RED + new DecimalFormat("##.###").format(currentVitality * 10));
		listVitality.add("");
		listVitality.add("");
		listVitality.add(ChatColor.YELLOW + "Increases health Points" + ChatColor.DARK_GRAY + ".");
		listVitality.add(ChatColor.YELLOW + "Increases stamina Points" + ChatColor.DARK_GRAY + ".");

		//Intellect
		listIntellect.add(clickToAdd);
		listIntellect.add("");
		listIntellect.add(currentValues);
		listIntellect.add(ChatColor.GRAY + "Mana Points: " + ChatColor.RED + new DecimalFormat("##.###").format(currentIntellect * 10));
		listIntellect.add(ChatColor.GRAY + "Magic Resistance: " + ChatColor.RED + new DecimalFormat("##.###").format(currentIntellect * 0.05) + percent);
		listIntellect.add("");
		listIntellect.add("");
		listIntellect.add(ChatColor.YELLOW + "Increases mana points" + ChatColor.DARK_GRAY + ".");
		listIntellect.add(ChatColor.YELLOW + "Reduces magic damage" + ChatColor.DARK_GRAY + ".");

		//Spirit
		listSpirit.add(clickToAdd);
		listSpirit.add("");
		listSpirit.add(currentValues);
		listSpirit.add(ChatColor.GRAY + "Hit point regeneration: " + ChatColor.RED + new DecimalFormat("##.###").format(currentSpirit * 0.01) + percent);
		listSpirit.add(ChatColor.GRAY + "Mana point regeneration: " + ChatColor.RED + new DecimalFormat("##.###").format(currentSpirit * 0.01) + percent);
		listSpirit.add(ChatColor.GRAY + "Stamina point regeneration: " + ChatColor.RED + new DecimalFormat("##.###").format(currentSpirit * 0.01) + percent);
		listSpirit.add("");
		listSpirit.add("");
		listSpirit.add(ChatColor.YELLOW + "Increases hit point regeneration" + ChatColor.DARK_GRAY + ".");
		listSpirit.add(ChatColor.YELLOW + "Increases mana point regeneration" + ChatColor.DARK_GRAY + ".");
		listSpirit.add(ChatColor.YELLOW + "Increases stamina point regeneration" + ChatColor.DARK_GRAY + ".");


		//Add the list of strings to the meta data lore.
		metaStrength.setLore(listStrength);
		metaAgility.setLore(listAgility);
		metaVitality.setLore(listVitality);
		metaIntellect.setLore(listIntellect);
		metaSpirit.setLore(listSpirit);

		//Set Item meta data
		strength.setItemMeta(metaStrength);
		agility.setItemMeta(metaAgility);
		vitality.setItemMeta(metaVitality);
		intellect.setItemMeta(metaIntellect);
		spirit.setItemMeta(metaSpirit);
		confirm.setItemMeta(metaConfirm);

		//Create the menu
		Inventory attributeMenu = Bukkit.createInventory(player, 9 * 3, "Assign Attribute Points");

		//Place the items in the menu
		attributeMenu.setItem(11, strength);
		attributeMenu.setItem(12, agility);
		attributeMenu.setItem(13, vitality);
		attributeMenu.setItem(14, intellect);
		attributeMenu.setItem(15, spirit);
		attributeMenu.setItem(16, confirm);

		//Menu creation is finished.  Add menu to HashMap for player.
		attributeAssignMenu.put(uuid, attributeMenu);

	}
	
	/**
	 * This will open a menu the player can use to assing their attribute points.
	 * 
	 * @param player The player who requested to open the menu.
	 */
	public static void openAttributeAssingMenu(Player player){
		UUID uuid = player.getUniqueId();
		player.openInventory(attributeAssignMenu.get(uuid));
	}
	
	/**
	 * This will recalculate and apply the players new attributes to the respective HashMaps.
	 * This is called frequently after equipment is applied or taken off.
	 * 
	 * @param player The player who will get the new attribute recalculation.
	 */
	public static void applyNewAttributes(Player player, boolean playSound) {
		//Apply health related attributes.
		applyMaxHealthPoints(player);
		
		//Apply stamina related attributes.
		applyMaxStaminaPoints(player);
		
		//Apply mana related attributes.
		applyMaxManaPoints(player);
		
		//Play sound effect.
		if (playSound) {
			//player.playSound(player.getLocation(), Sound.ANVIL_LAND, .5F, 1F);
		}
	}
	
	/**
	 * This will recalculate and apply the players new HP to the respective HashMaps.
	 * This is called frequently after equipment is applied or taken off.
	 * 
	 * @param player The player who will get the new updated max health total.
	 */
	private static void applyMaxHealthPoints(Player player) {
		UUID uuid = player.getUniqueId();

		double oldMaxHP = PlayerManager.getMaxHealthPoints(uuid);
		double newMaxHP = getMaxHealthPoints(player);
		double currentHP = PlayerManager.getHealthPoints(uuid);
		
		//If the players HP is greater than the base HP + HP bonus,
		//set the players HP.  BaseHP + Armor HP.
		if (currentHP > newMaxHP) {

			//entity.setHealth(newHP);
			PlayerManager.setHealthPoints(player, newMaxHP);
			player.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "New HP: " + ChatColor.RESET + newMaxHP);

			//Set players heart's level to 20 (full health).
			player.setHealth(20);

		}
		
		//Set new maxHP if the value has changed.
		if (oldMaxHP != newMaxHP) {

			double hpPercent = currentHP / newMaxHP;
			double hpDisplay = hpPercent * 20;

			//Set the players new HP
			if (hpDisplay > 20) {
				//Stop HP from going over 20.
				player.setHealth(20);
				
			} else if (hpDisplay <= 0) {
				//Stop HP from going under 1.
				player.setHealth(1);
				
			} else {
				//Set the players HP.
				player.setHealth(hpDisplay);
			}
			
			//Set the new max HP.
			PlayerManager.setMaxHealthPoints(uuid, newMaxHP);
			
			//Set message
			player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "New MaxHP: " + ChatColor.RESET + Integer.toString((int) newMaxHP));
		}
	}
	
	/**
	 * This will recalculate and apply the players new Stamina Points to the respective HashMaps.
	 * This is called frequently after equipment is applied or taken off.
	 * 
	 * @param player The player who will get the new updated max stamina point total.
	 */
	private static void applyMaxStaminaPoints(Player player) {
		UUID uuid = player.getUniqueId();

		double oldMaxSP = PlayerManager.getMaxStaminaPoints(uuid);
		double newMaxSP = getMaxStaminaPoints(player);
		double currentSP = PlayerManager.getStaminaPoints(uuid);
		
		//If the players HP is greater than the base HP + HP bonus,
		//set the players HP.  BaseHP + Armor HP.
		if (currentSP > newMaxSP) {

			//entity.setHealth(newHP);
			PlayerManager.setStaminaPoints(player, newMaxSP);
			player.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "New Stamina: " + ChatColor.RESET + newMaxSP);

			//Set players heart's level to 20 (full health).
			player.setFoodLevel(20);

		}
		
		//Set new maxHP if the value has changed.
		if (oldMaxSP != newMaxSP) {

			double spPercent = currentSP / newMaxSP;
			double spDisplay = spPercent * 20;

			//Set the players new Stamina Point value
			if (spDisplay > 20) {
				//Stop FoodLevel from going over 20.
				player.setFoodLevel(20);
				
			} else if (spDisplay <= 0) {
				//Stop FoodLevel from going under 1.
				player.setFoodLevel(1);
				
			} else {
				//Set the players Stamina.
				player.setFoodLevel((int) spDisplay);
			}
			
			//Set the new max Stamina Points.
			PlayerManager.setMaxStaminaPoints(uuid, newMaxSP);
			
			//Set message
			player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "New MaxStamina: " + ChatColor.RESET + Integer.toString((int) newMaxSP));
		}
	}
	
	/**
	 * This will recalculate and apply the players new Stamina Points to the respective HashMaps.
	 * This is called frequently after equipment is applied or taken off.
	 * 
	 * @param player The player who will get the new updated max stamina point total.
	 */
	private static void applyMaxManaPoints(Player player) {
		UUID uuid = player.getUniqueId();

		double oldMaxMP = PlayerManager.getMaxManaPoints(uuid);
		double newMaxMP = getMaxManaPoints(player);
		double currentMP = PlayerManager.getManaPoints(uuid);
		
		//If the players MP is greater than the base MP + MP bonus,
		//set the players MP.  BaseMP + Armor MP.
		if (currentMP > newMaxMP) {

			//entity.setHealth(newMP);
			PlayerManager.setManaPoints(uuid, newMaxMP);
			player.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "New Mana: " + ChatColor.RESET + newMaxMP);

			//Set players heart's level to 20 (full health).
			player.setFoodLevel(20);

		}
		
		//Set new maxMP if the value has changed.
		if (oldMaxMP != newMaxMP) {
			
			//Set the new max Mana Points.
			PlayerManager.setMaxManaPoints(uuid, newMaxMP);
			
			//Set message
			player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "New MaxMana: " + ChatColor.RESET + Integer.toString((int) newMaxMP));
		}
	}
	
	/**
	 * Gets the maximum health points the player has based on attribute points, armor attributes and weapon attributes.
	 * 
	 * @param player The player that we will get the maximum health points for.
	 * @return The players maximum health points.
	 */
	public static double getMaxHealthPoints(Player player) {
		String cc = PlayerCharacterManager.getPlayerClass(player);

		double currentVitality = PlayerCharacterManager.getPlayerConfigDouble(player, "player.attributes.vitality");
		double armorVitality = ItemLoreFactory.getVitality(player);

		double hpPerPoint = 5;

		if (cc.equalsIgnoreCase("ARCHER")) {
			double totalVitality = archerVitality + currentVitality + armorVitality;
			double finalHP = totalVitality * hpPerPoint;

			return finalHP;
		} else if (cc.equalsIgnoreCase("MAGE")) {
			double totalVitality = mageVitality + currentVitality + armorVitality;
			double finalHP = totalVitality * hpPerPoint;

			return finalHP;
		} else if (cc.equalsIgnoreCase("ROGUE")) {
			double totalVitality = rogueVitality + currentVitality + armorVitality;
			double finalHP = totalVitality * hpPerPoint;

			return finalHP;
		} else if (cc.equalsIgnoreCase("WARRIOR")) {
			double totalVitality = warriorVitality + currentVitality + armorVitality;
			double finalHP = totalVitality * hpPerPoint;

			return finalHP;
		} else {
			return 0;
		}
	}
	
	/**
	 * Gets the maximum stamina points the player has based on attribute points, armor attributes and weapon attributes.
	 * 
	 * @param player The player that we will get the maximum stamina points for.
	 * @return The players maximum stamina points.
	 */
	public static double getMaxStaminaPoints(Player player) {
		String cc = PlayerCharacterManager.getPlayerClass(player);

		double currentVitality = PlayerCharacterManager.getPlayerConfigDouble(player, "player.attributes.vitality");
		double armorVitality = ItemLoreFactory.getVitality(player);

		double staminaPerPoint = 5;

		if (cc.equalsIgnoreCase("ARCHER")) {
			double totalVitality = archerVitality + currentVitality + armorVitality;
			double finalStaminaPoints = totalVitality * staminaPerPoint;

			return finalStaminaPoints;
		} else if (cc.equalsIgnoreCase("MAGE")) {
			double totalVitality = mageVitality + currentVitality + armorVitality;
			double finalHP = totalVitality * staminaPerPoint;

			return finalHP;
		} else if (cc.equalsIgnoreCase("ROGUE")) {
			double totalVitality = rogueVitality + currentVitality + armorVitality;
			double finalHP = totalVitality * staminaPerPoint;

			return finalHP;
		} else if (cc.equalsIgnoreCase("WARRIOR")) {
			double totalVitality = warriorVitality + currentVitality + armorVitality;
			double finalHP = totalVitality * staminaPerPoint;

			return finalHP;
		} else {
			return 0;
		}
	}
	
	/**
	 * Gets the maximum stamina points the player has based on attribute points, armor attributes and weapon attributes.
	 * 
	 * @param player The player that we will get the maximum stamina points for.
	 * @return The players maximum stamina points.
	 */
	public static double getMaxManaPoints(Player player) {
		String cc = PlayerCharacterManager.getPlayerClass(player);

		double currentIntellect = PlayerCharacterManager.getPlayerConfigDouble(player, "player.attributes.intellect");
		double armorIntellect = ItemLoreFactory.getIntellect(player);

		double manaPerPoint = 5;

		if (cc.equalsIgnoreCase("ARCHER")) {
			double totalIntellect = archerIntellect + currentIntellect + armorIntellect;
			double finalIntellectPoints = totalIntellect * manaPerPoint;

			return finalIntellectPoints;
		} else if (cc.equalsIgnoreCase("MAGE")) {
			double totalIntellect = mageIntellect + currentIntellect + armorIntellect;
			double finalHP = totalIntellect * manaPerPoint;

			return finalHP;
		} else if (cc.equalsIgnoreCase("ROGUE")) {
			double totalIntellect = rogueIntellect + currentIntellect + armorIntellect;
			double finalHP = totalIntellect * manaPerPoint;

			return finalHP;
		} else if (cc.equalsIgnoreCase("WARRIOR")) {
			double totalIntellect = warriorIntellect + currentIntellect + armorIntellect;
			double finalHP = totalIntellect * manaPerPoint;

			return finalHP;
		} else {
			return 0;
		}
	}

	/**
	 * Gets the players total health point regeneration rate.
	 * 
	 * @param player The player who's health will be regenerated.
	 * @return The amount of total regeneration rate the player will receive.
	 */
	public static double getHealthPointRegeneration(Player player) {
		String cc = PlayerCharacterManager.getPlayerClass(player);

		double currentSpirit = PlayerCharacterManager.getPlayerConfigDouble(player, "player.attributes.spirit");
		double armorSpirit = ItemLoreFactory.getSpirit(player);

		double spiritModifier = 0.001;

		if (cc.equalsIgnoreCase("ARCHER")) {
			double totalSpirit = archerSpirit + currentSpirit + armorSpirit;
			double hpRegeneration = totalSpirit * spiritModifier;

			return hpRegeneration;
		} else if (cc.equalsIgnoreCase("MAGE")) {
			double totalSpirit = mageSpirit + currentSpirit + armorSpirit;
			double hpRegeneration = totalSpirit * spiritModifier;

			return hpRegeneration;
		} else if (cc.equalsIgnoreCase("ROGUE")) {
			double totalSpirit = rogueSpirit + currentSpirit + armorSpirit;
			double hpRegeneration = totalSpirit * spiritModifier;

			return hpRegeneration;
		} else if (cc.equalsIgnoreCase("WARRIOR")) {
			double totalSpirit = warriorSpirit + currentSpirit + armorSpirit;
			double hpRegeneration = totalSpirit * spiritModifier;

			return hpRegeneration;
		} else {
			return 0;
		}
	}
	
	/**
	 * Gets the players total stamina point regeneration rate.
	 * 
	 * @param player The player who's stamina will be regenerated.
	 * @return The amount of total regeneration rate the player will receive.
	 */
	public static double getStaminaPointRegeneration(Player player) {
		String cc = PlayerCharacterManager.getPlayerClass(player);

		double currentSpirit = PlayerCharacterManager.getPlayerConfigDouble(player, "player.attributes.spirit");
		double armorSpirit = ItemLoreFactory.getSpirit(player);

		double spiritModifier = 0.001;

		if (cc.equalsIgnoreCase("ARCHER")) {
			double totalSpirit = archerSpirit + currentSpirit + armorSpirit;
			double spRegeneration = totalSpirit * spiritModifier;

			return spRegeneration;
		} else if (cc.equalsIgnoreCase("MAGE")) {
			double totalSpirit = mageSpirit + currentSpirit + armorSpirit;
			double spRegeneration = totalSpirit * spiritModifier;

			return spRegeneration;
		} else if (cc.equalsIgnoreCase("ROGUE")) {
			double totalSpirit = rogueSpirit + currentSpirit + armorSpirit;
			double spRegeneration = totalSpirit * spiritModifier;
			
			return spRegeneration;
		} else if (cc.equalsIgnoreCase("WARRIOR")) {
			double totalSpirit = warriorSpirit + currentSpirit + armorSpirit;
			double spRegeneration = totalSpirit * spiritModifier;

			return spRegeneration;
		} else {
			return 0;
		}
	}
	
	/**
	 * Gets the players total mana point regeneration rate.
	 * 
	 * @param player The player who's mana will be regenerated.
	 * @return The amount of total regeneration rate the player will receive.
	 */
	public static double getManaPointRegeneration(Player player) {
		String cc = PlayerCharacterManager.getPlayerClass(player);

		double currentSpirit = PlayerCharacterManager.getPlayerConfigDouble(player, "player.attributes.spirit");
		double armorSpirit = ItemLoreFactory.getSpirit(player);

		double spiritModifier = 0.001;

		if (cc.equalsIgnoreCase("ARCHER")) {
			double totalSpirit = archerSpirit + currentSpirit + armorSpirit;
			double mpRegeneration = totalSpirit * spiritModifier;

			return mpRegeneration;
		} else if (cc.equalsIgnoreCase("MAGE")) {
			double totalSpirit = mageSpirit + currentSpirit + armorSpirit;
			double mpRegeneration = totalSpirit * spiritModifier;

			return mpRegeneration;
		} else if (cc.equalsIgnoreCase("ROGUE")) {
			double totalSpirit = rogueSpirit + currentSpirit + armorSpirit;
			double mpRegeneration = totalSpirit * spiritModifier;

			return mpRegeneration;
		} else if (cc.equalsIgnoreCase("WARRIOR")) {
			double totalSpirit = warriorSpirit + currentSpirit + armorSpirit;
			double mpRegeneration = totalSpirit * spiritModifier;

			return mpRegeneration;
		} else {
			return 0;
		}
	}

}
