package com.minepile.mprpg.items;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerHealthTagManager;
import com.minepile.mprpg.player.PlayerManager;

public class LoreManager {

	//setup instance variables
	public static MPRPG plugin;
	static LoreManager loreManagerInstance = new LoreManager();

	private static Pattern healthRegex = Pattern.compile("[+](\\d+)[ ](health)");
	private static Pattern negHealthRegex = Pattern.compile("[-](\\d+)[ ]()");
	private static Pattern regenRegex = Pattern.compile("[+](\\d+)[ ](regen)");
	private static Pattern staminaSpeedRegex = Pattern.compile("[+](\\d+)[ ](stamina)");
	private static Pattern damageValueRegex = Pattern.compile("[+](\\d+)[ ](damage)");
	private static Pattern negitiveDamageValueRegex = Pattern.compile("[-](\\d+)[ ]()");
	private static Pattern damageRangeRegex = Pattern.compile("(\\d+)(-)(\\d+)[ ]()");
	private static Pattern dodgeRegex = Pattern.compile("[+](\\d+)[%][ ](dodge)");
	private static Pattern critChanceRegex = Pattern.compile("[+](\\d+)[%][ ](crit chance)");
	private static Pattern critDamageRegex = Pattern.compile("[+](\\d+)[ ](crit damage)");
	private static Pattern lifestealRegex = Pattern.compile("[+](\\d+)[ ](life steal)");
	private static Pattern armorRegex = Pattern.compile("[+](\\d+)[ ](armor)");
	private static HashMap<String, Timestamp> staminaLog = new HashMap<String, Timestamp>();
	private static Random generator = new Random();

	//Create instance
	public static LoreManager getInstance() {
		return loreManagerInstance;
	}

	//Setup LoreManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		for (Player players : Bukkit.getOnlinePlayers()) {
			applyHpBonus(players, false);
		}
	}
	
	/**
	 * This will disable this class and perform different tasks for server restart or shut down.
	 */
	public static void disable() {
		staminaLog.clear();
	}
	
	/**
	 * Returns the dodge bonus from all of the players equipped armor and the
	 * weapon that is currently in the players hand.  
	 * 
	 * @param entity the player entity that is wearing armor or holding a weapon
	 * @param return the total amount of dodge bonus from all armor slots and current weapon
	 */
	public static int getDodgeBonus(LivingEntity entity) {
		Integer dodgeBonus = Integer.valueOf(0);
		
		//Loop through players armor slots
		for (ItemStack item : entity.getEquipment().getArmorContents()) {
			//If the item exists and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
				List<String> lore = item.getItemMeta().getLore();	//Array lists of lore strings
				String allLore = lore.toString().toLowerCase();		//Put's all lore in one lowercase string

				//Get the matcher pattern to find "Dodge bonus"
				Matcher valueMatcher = dodgeRegex.matcher(allLore);
				if (valueMatcher.find()) {
					//If a match was found, add that value to the variable dodgeBonus.
					dodgeBonus = Integer.valueOf(dodgeBonus.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
				}
			}
		}
		//Get Dodge value from item in hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item exists and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			Object lore = item.getItemMeta().getLore();			//Array lists of lore strings
			String allLore = lore.toString().toLowerCase();		//Put's all lore in one lowercase string
			
			//Get the matcher pattern to find "Dodge bonus"
			Matcher valueMatcher = dodgeRegex.matcher(allLore);
			if (valueMatcher.find()) {
				//If a match was found, add that value to the variable dodgeBonus.
				dodgeBonus = Integer.valueOf(dodgeBonus.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
			}
		}
		//Returns the dodge bonus of armor and weapons.
		return dodgeBonus.intValue();
	}
	
	/**
	 * This method will calculate if the player has dodged an attack from another entity.
	 * 
	 * @param entity the entity that may or may not dodge an attack
	 * @param return returns true if the player dodged an attack
	 */
	public static boolean dodgedAttack(LivingEntity entity) {
		//If not a valid entity, cancel a dodged attack.
		if (!entity.isValid()) {
			return false;
		}
		//Gets the dodge bonus (if any) from the entites armor and weapon
		Integer chance = Integer.valueOf(getDodgeBonus(entity));

		//Get the roll for dodge. 1-100
		Integer roll = Integer.valueOf(generator.nextInt(100) + 1);
		if (chance.intValue() >= roll.intValue()) {
			//If the dodge value was greater than the roll
			//then return true. Dodge was a success!
			return true;
		}
		//The player didn't dodge the attack.
		return false;
	}
	
	/**
	 * Returns the critical hit chance from all of the players equipped armor and the
	 * weapon that is currently in the players hand.  
	 * 
	 * @param entity the player entity that is wearing armor or holding a weapon
	 * @param return the total amount of "critical chance" from all armor slots and current weapon
	 */
	private static int getCritChance(LivingEntity entity) {
		Integer chance = Integer.valueOf(0);
		
		//Loop through the players armor slots
		for (ItemStack item : entity.getEquipment().getArmorContents()) {
			//If the item exists and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
				List<String> lore = item.getItemMeta().getLore();	//Array lists of lore strings
				String allLore = lore.toString().toLowerCase();		//Put's all lore in one lowercase string
				
				//Get the matcher pattern to find "critical hit chance"
				Matcher valueMatcher = critChanceRegex.matcher(allLore);
				if (valueMatcher.find()) {
					//If a match was found, add that value to the variable "chance."
					chance = Integer.valueOf(chance.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
				}
			}
		}
		//Get critical hit chance value from item in hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item exists and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			Object lore = item.getItemMeta().getLore();			//Array lists of lore strings
			String allLore = lore.toString().toLowerCase();		//Put's all lore in one lowercase string
			
			//Get the matcher pattern to find "critical hit chance"
			Matcher valueMatcher = critChanceRegex.matcher(allLore);
			if (valueMatcher.find()) {
				//If a match was found, add that value to the variable "chance."
				chance = Integer.valueOf(chance.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
			}
		}
		//Returns the critical hit chance of armor and weapons.
		return chance.intValue();
	}
	
	/**
	 * This method will calculate if the player has landed a critical attack on another entity.
	 * 
	 * @param entity the entity that may or may not have made a critical attack
	 * @param return returns true if the player has landed a critical attack
	 */
	private static boolean critAttack(LivingEntity entity) {
		if (!entity.isValid()) {
			return false;
		}
		Integer chance = Integer.valueOf(getCritChance(entity));

		Integer roll = Integer.valueOf(generator.nextInt(100) + 1);
		if (chance.intValue() >= roll.intValue()) {
			//If the critical hit chance value was greater than the roll
			//then return true. Critical attack was a success!
			return true;
		}
		//The player didn't land a critical hit.
		return false;
	}
	
	/**
	 * Get's the players total armor bonus from all armor slots and the item being held in the players hand.
	 * 
	 * @param entity the player entity that is wearing armor or holding a weapon 
	 * @return return the total amount of "armor bonus" from all armor slots and current weapon
	 */
	public static int getArmorBonus(LivingEntity entity) {
		Integer armor = Integer.valueOf(0);
		
		//Loop through the players armor slots
		for (ItemStack item : entity.getEquipment().getArmorContents()) {
			//If the item exists and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
				List<String> lore = item.getItemMeta().getLore();	//Array lists of lore strings
				String allLore = lore.toString().toLowerCase();		//Put's all lore in one lowercase string
				
				//Get the matcher pattern to find "armor" attribute
				Matcher valueMatcher = armorRegex.matcher(allLore);
				if (valueMatcher.find()) {
					//If a match was found, add that value to the variable "armor."
					armor = Integer.valueOf(armor.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
				}
			}
		}
		//Get armor value from item in hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item exists and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			Object lore = item.getItemMeta().getLore();			//Array lists of lore strings
			String allLore = lore.toString().toLowerCase();		//Put's all lore in one lowercase string

			//Get the matcher pattern to find "armor" attribute
			Matcher valueMatcher = armorRegex.matcher(allLore);
			if (valueMatcher.find()) {
				//If a match was found, add that value to the variable "armor."
				armor = Integer.valueOf(armor.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
			}
		}
		//Returns the "armor value" of armor and weapons.
		return armor.intValue();
	}
	
	/**
	 * Gets the amount of "life steal" a weapon has.
	 * 
	 * @param entity entity the player entity that is wearing armor or holding a weapon
	 * @return return the total amount of "life steal" from all armor slots and current weapon
	 */
	public static int getLifeSteal(LivingEntity entity) {
		Integer steal = Integer.valueOf(0);
		for (ItemStack item : entity.getEquipment().getArmorContents()) {
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
				List<String> lore = item.getItemMeta().getLore();
				String allLore = lore.toString().toLowerCase();

				Matcher valueMatcher = lifestealRegex.matcher(allLore);
				if (valueMatcher.find()) {
					steal = Integer.valueOf(steal.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
				}
			}
		}
		ItemStack item = entity.getEquipment().getItemInHand();
		if ((item != null) && 
				(item.hasItemMeta()) && 
				(item.getItemMeta().hasLore()))
		{
			Object lore = item.getItemMeta().getLore();
			String allLore = lore.toString().toLowerCase();

			Matcher valueMatcher = lifestealRegex.matcher(allLore);
			if (valueMatcher.find()) {
				steal = Integer.valueOf(steal.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
			}
		}
		//Returns the life steal value of armor and weapons.
		return steal.intValue();
	}

	public static int getCritDamage(LivingEntity entity) {
		if (!critAttack(entity)) {
			return 0;
		}
		Integer damage = Integer.valueOf(0);
		for (ItemStack item : entity.getEquipment().getArmorContents()) {
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
				List<String> lore = item.getItemMeta().getLore();
				String allLore = lore.toString().toLowerCase();

				Matcher valueMatcher = critDamageRegex.matcher(allLore);
				if (valueMatcher.find()) {
					damage = Integer.valueOf(damage.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
				}
			}
		}
		ItemStack item = entity.getEquipment().getItemInHand();
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			Object lore = item.getItemMeta().getLore();
			String allLore = lore.toString().toLowerCase();

			Matcher valueMatcher = critDamageRegex.matcher(allLore);
			if (valueMatcher.find()) {
				damage = Integer.valueOf(damage.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
			}
		}
		return damage.intValue();
	}

	private static double getStaminaCooldown(Player player) {
		return PlayerManager.getBaseStaminaRegenRate() * 0.1D - getStaminaSpeed(player) * 0.1D;
	}

	public static void addAttackCooldown(String playerName) {
		Timestamp able = new Timestamp((long) (new Date().getTime() + getStaminaCooldown(Bukkit.getPlayerExact(playerName)) * 1000.0D));

		staminaLog.put(playerName, able);
	}

	public static boolean canAttack(String playerName) {
		if (!staminaLog.containsKey(playerName)) {
			return true;
		}
		Date now = new Date();
		if (now.after(staminaLog.get(playerName))) {
			return true;
		}
		return false;
	}

	private static double getStaminaSpeed(Player player) {
		if (player == null) {
			return 1.0D;
		}
		double speed = 1.0D;
		for (ItemStack item : player.getEquipment().getArmorContents()) {
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
				List<String> lore = item.getItemMeta().getLore();
				String allLore = lore.toString().toLowerCase();

				Matcher valueMatcher = staminaSpeedRegex.matcher(allLore);
				if (valueMatcher.find()) {
					speed += Integer.valueOf(valueMatcher.group(1)).intValue();
				}
			}
		}
		ItemStack item = player.getEquipment().getItemInHand();
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			Object lore = item.getItemMeta().getLore();
			String allLore = lore.toString().toLowerCase();

			Matcher valueMatcher = staminaSpeedRegex.matcher(allLore);
			if (valueMatcher.find()) {
				speed += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		return speed;
	}

	/**
	 * Applies a players HP ARMOR + "default HP Base" to get the players total HP.
	 * 
	 * @param entity A living entity.
	 */
	public static void applyHpBonus(LivingEntity entity, boolean playSoundEffect) {
		//If the entity is not valid, cancel this method.
		if (!entity.isValid()) {
			return;
		}
		//Get armor HP bonuses
		Integer hpToAdd = Integer.valueOf(getHpBonus(entity));

		//Make sure the entity is a player.
		if ((entity instanceof Player)) {

			Player player = ((Player) entity).getPlayer();
			String playerName = player.getName();
			double currentHP = PlayerManager.getHealthPoints(playerName);
			double newMaxHP = getBaseHealth(player) + hpToAdd.doubleValue();
			double oldMaxHP = PlayerManager.getMaxHealthPoints(playerName);

			//If the players HP is greater than the base HP + HP bonus,
			//set the players HP.  BaseHP + Armor HP.
			if (currentHP > newMaxHP) {

				//entity.setHealth(newHP);
				PlayerManager.setHealthPoints(entity.getName(), newMaxHP);
				entity.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "New HP: " + ChatColor.RESET + newMaxHP);
				
				//Set players heart's level to 20 (full health).
				entity.setHealth(20);

			}
			
			//Lets grab the players HP again, just incase it was updated above.
			double updatedHP = PlayerManager.getHealthPoints(playerName);
			//This is the percent determines how many play hearts are shown.
			double healthPercent = ((20 * updatedHP) / newMaxHP);
			
			//If the players maximum HP has changed, then lets update
			//the user on that change.  If the oldMaxHP equals the newMaxHP
			//then no change has been made and the user does not need updates.
			if (newMaxHP != oldMaxHP) {

				PlayerManager.setMaxHealthPoints(entity.getName(), newMaxHP);
				entity.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "New MaxHP: " + ChatColor.RESET + Integer.toString((int) newMaxHP));
				
				//If the players HP percent is over 20,
				//then just set the players hearts to 20.
				if (healthPercent >= 20) {
					entity.setHealth(20);
					
				} else if (healthPercent < 0.01) { //Player died.
					
					PlayerManager.killPlayer(player);
				} else {
					entity.setHealth(healthPercent);
				}

				if (playSoundEffect == true) {
					//Play sound effect.
					((Player) entity).playSound(entity.getLocation(), Sound.ANVIL_LAND, .5F, 1F);
				}
			}
			//Update the players health tag.
			PlayerHealthTagManager.updateHealthTag(player);
		}
	}

	public static int getHpBonus(LivingEntity entity) {
		Integer hpToAdd = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = lore.toString().toLowerCase();

				//Prepare for text matching using regular expressions.
				Matcher negmatcher = negHealthRegex.matcher(allLore);
				Matcher matcher = healthRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					hpToAdd = Integer.valueOf(hpToAdd.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
				if (negmatcher.find()) {
					hpToAdd = Integer.valueOf(hpToAdd.intValue() - Integer.valueOf(negmatcher.group(1)).intValue());
				}
				if (hpToAdd.intValue() < 0) {
					hpToAdd = Integer.valueOf(0);
				}
			}
		}
		//Return the HP value.
		return hpToAdd.intValue();
	}

	//Get player base health points.
	//This is defined in the PlayerManager.
	public static double getBaseHealth(Player player) {
		double hp = PlayerManager.getBaseHealthPoints();
		return hp;
	}

	/**
	 * Get the players regeneration bonuses.
	 * 
	 * @param entity A living entity.
	 */
	public static int getRegenBonus(LivingEntity entity) {
		//If entity is not valid, lets cancel the method.
		if (!entity.isValid()) {
			return 0;
		}
		//Regeneration bonus
		Integer regenBonus = Integer.valueOf(0);

		//Loop through the armor slots and get the Regeneration Bonus for all Armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and the item has meta and item lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of strings, and add the item lore to it.
				List<String> lore = item.getItemMeta().getLore();
				//Get the lore array, and make each entry lowercase.
				String allLore = lore.toString().toLowerCase();
				//Prepare to use regular expressions to find the regeneration lore.
				Matcher matcher = regenRegex.matcher(allLore);
				//If we can find a RegenBonus in the armor, then lets get that and add it to the end value.
				if (matcher.find()) {
					regenBonus = Integer.valueOf(regenBonus.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		//Return the regeneration bonus.
		return regenBonus.intValue();
	}

	public static int getDamageBonus(LivingEntity entity) {
		if (!entity.isValid()) {
			return 0;
		}
		Integer damageMin = Integer.valueOf(0);
		Integer damageMax = Integer.valueOf(0);
		Integer damageBonus = Integer.valueOf(0);
		for (ItemStack item : entity.getEquipment().getArmorContents()) {
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
				List<String> lore = item.getItemMeta().getLore();
				String allLore = lore.toString().toLowerCase();

				Matcher rangeMatcher = damageRangeRegex.matcher(allLore);
				Matcher valueMatcher = damageValueRegex.matcher(allLore);
				if (rangeMatcher.find()) {
					damageMin = Integer.valueOf(damageMin.intValue() + Integer.valueOf(rangeMatcher.group(1)).intValue());
					damageMax = Integer.valueOf(damageMax.intValue() + Integer.valueOf(rangeMatcher.group(3)).intValue());
				}
				if (valueMatcher.find()) {
					damageBonus = Integer.valueOf(damageBonus.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
				}
			}
		}
		ItemStack item = entity.getEquipment().getItemInHand();
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			Object lore = item.getItemMeta().getLore();
			String allLore = lore.toString().toLowerCase();
			Matcher negValueMatcher = negitiveDamageValueRegex.matcher(allLore);
			Matcher rangeMatcher = damageRangeRegex.matcher(allLore);
			Matcher valueMatcher = damageValueRegex.matcher(allLore);
			if (rangeMatcher.find()) {
				damageMin = Integer.valueOf(damageMin.intValue() + Integer.valueOf(rangeMatcher.group(1)).intValue());
				damageMax = Integer.valueOf(damageMax.intValue() + Integer.valueOf(rangeMatcher.group(3)).intValue());
			}
			if (valueMatcher.find()) {
				damageBonus = Integer.valueOf(damageBonus.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
				damageBonus = Integer.valueOf(damageBonus.intValue() - Integer.valueOf(negValueMatcher.group(1)).intValue());
			}
		}
		if (damageMax.intValue() < 1) {
			damageMax = Integer.valueOf(1);
		}
		if (damageMin.intValue() < 1) {
			damageMin = Integer.valueOf(1);
		}
		return (int)Math.round(Math.random() * (damageMax.intValue() - damageMin.intValue()) + damageMin.intValue() + damageBonus.intValue() + getCritDamage(entity));
	}

	public static boolean useRangeOfDamage(LivingEntity entity) {
		//If the entity is not valid, lets exit the method.
		if (!entity.isValid()) {
			return false;
		}
		//Lets loop through the armor slots and get the armor contents.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the armor slot is not null and the item has meta and the item has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
				//Create an array of strings from the items lore.
				List<String> lore = item.getItemMeta().getLore();
				//Convert the array of strings to a single string, all lower case.
				String allLore = lore.toString().toLowerCase();
				//Prepare the string to math the damage range.
				Matcher rangeMatcher = damageRangeRegex.matcher(allLore);
				//If a match is found, return true.
				if (rangeMatcher.find()) {
					return true;
				}
			}
		}
		//Get the item in the players hand (looking for a weapon).
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and the item has meta and the item has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			//Get the items lore.
			Object lore = item.getItemMeta().getLore();
			//Convert the array of strings to a single string, all lower case.
			String allLore = lore.toString().toLowerCase();
			//Prepare the string to math the damage range.
			Matcher rangeMatcher = damageRangeRegex.matcher(allLore);
			//If a match is found, return true.
			if (rangeMatcher.find()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Displays the users current equipped armor and weapon lore statistics.
	 * 
	 * @param player The player who ran the "/armorstats" or "/lorestats" command.
	 */
	public static void displayLoreStats(Player player) {
		HashSet<String> message = new HashSet<String>();
		if (getHpBonus(player) != 0) {
			message.add(ChatColor.GRAY + "" + ChatColor.BOLD + "HitPoints" + 
					ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + ChatColor.WHITE + getHpBonus(player));
		}
		if (getRegenBonus(player) != 0) {
			message.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Regeneration" + 
					ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + ChatColor.WHITE + getRegenBonus(player));
		}
		if (getStaminaSpeed(player) != 0) {
			message.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Stamina Speed" + 
					ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + ChatColor.WHITE + getStaminaSpeed(player));
		}
		if (getDamageBonus(player) != 0) {
			message.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Damage" + 
					ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + ChatColor.WHITE + getDamageBonus(player));
		}
		if (getDodgeBonus(player) != 0) {
			message.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Dodge" + 
					ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + ChatColor.WHITE + getDodgeBonus(player) +
					ChatColor.GRAY + "%");
		}
		if (getCritChance(player) != 0) {
			message.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Critical Hit Chance" + 
					ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + ChatColor.WHITE + getCritChance(player) +
					ChatColor.GRAY + "%");
		}
		if (getCritDamage(player) != 0) {
			message.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Critical Damage" + 
					ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + ChatColor.WHITE + getCritDamage(player));
		}
		if (getLifeSteal(player) != 0) {
			message.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Life Steal" + 
					ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + ChatColor.WHITE + getLifeSteal(player));
		}
		if (getArmorBonus(player) != 0) {
			message.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Armor" + 
					ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + ChatColor.WHITE + getArmorBonus(player));
		}
		String newMessage = "";
		for (String toSend : message)
		{
			newMessage = newMessage + "   " + toSend;
			if (newMessage.length() > 40)
			{
				player.sendMessage(newMessage);
				newMessage = "";
			}
		}
		if (newMessage.length() > 0) {
			player.sendMessage(newMessage);
		}
		message.clear();
	}
}
