package com.minepile.mprpg.equipment;

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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;
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
	private static Pattern restrictionRegex = Pattern.compile("(restriction:)[ ][0-9]+");
	private static Pattern levelRegex = Pattern.compile("level (\\d+)()");
	public static HashMap<String, Timestamp> staminaLog = new HashMap<String, Timestamp>();
	private static Random generator = new Random();

	//Create instance
	public static LoreManager getInstance() {
		return loreManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		for (Player players : Bukkit.getOnlinePlayers()) {
			applyHpBonus(players);
		}
	}

	public static boolean canUse(Player player, ItemStack item){
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			List<String> lore = item.getItemMeta().getLore();
			String allLore = lore.toString().toLowerCase();
			Matcher valueMatcher = levelRegex.matcher(allLore);
			if (valueMatcher.find()) {
				if (player.getLevel() < Integer.valueOf(valueMatcher.group(1)).intValue()) {
					player.sendMessage("Item was not able to be equipped.");
					return false;
				}
			}
			valueMatcher = restrictionRegex.matcher(allLore);
			if (valueMatcher.find()) {
				if (player.hasPermission("loreattributes." + valueMatcher.group(2))) {
					return true;
				}
				player.sendMessage(item.getType().toString());
				return false;
			}
		}
		return true;
	}

	public static int getDodgeBonus(LivingEntity entity) {
		Integer dodgeBonus = Integer.valueOf(0);
		for (ItemStack item : entity.getEquipment().getArmorContents()) {
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
				List<String> lore = item.getItemMeta().getLore();
				String allLore = lore.toString().toLowerCase();

				Matcher valueMatcher = dodgeRegex.matcher(allLore);
				if (valueMatcher.find()) {
					dodgeBonus = Integer.valueOf(dodgeBonus.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
				}
			}
		}
		ItemStack item = entity.getEquipment().getItemInHand();
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			Object lore = item.getItemMeta().getLore();
			String allLore = lore.toString().toLowerCase();

			Matcher valueMatcher = dodgeRegex.matcher(allLore);
			if (valueMatcher.find()) {
				dodgeBonus = Integer.valueOf(dodgeBonus.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
			}
		}
		return dodgeBonus.intValue();
	}

	public static boolean dodgedAttack(LivingEntity entity) {
		if (!entity.isValid()) {
			return false;
		}
		Integer chance = Integer.valueOf(getDodgeBonus(entity));


		Integer roll = Integer.valueOf(generator.nextInt(100) + 1);
		if (chance.intValue() >= roll.intValue()) {
			return true;
		}
		return false;
	}

	private static int getCritChance(LivingEntity entity) {
		Integer chance = Integer.valueOf(0);
		for (ItemStack item : entity.getEquipment().getArmorContents()) {
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
				List<String> lore = item.getItemMeta().getLore();
				String allLore = lore.toString().toLowerCase();

				Matcher valueMatcher = critChanceRegex.matcher(allLore);
				if (valueMatcher.find()) {
					chance = Integer.valueOf(chance.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
				}
			}
		}
		ItemStack item = entity.getEquipment().getItemInHand();
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			Object lore = item.getItemMeta().getLore();
			String allLore = lore.toString().toLowerCase();

			Matcher valueMatcher = critChanceRegex.matcher(allLore);
			if (valueMatcher.find()) {
				chance = Integer.valueOf(chance.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
			}
		}
		return chance.intValue();
	}

	private static boolean critStamina(LivingEntity entity) {
		if (!entity.isValid()) {
			return false;
		}
		Integer chance = Integer.valueOf(getCritChance(entity));

		Integer roll = Integer.valueOf(generator.nextInt(100) + 1);
		if (chance.intValue() >= roll.intValue()) {
			return true;
		}
		return false;
	}

	public static int getArmorBonus(LivingEntity entity) {
		Integer armor = Integer.valueOf(0);
		for (ItemStack item : entity.getEquipment().getArmorContents()) {
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
				List<String> lore = item.getItemMeta().getLore();
				String allLore = lore.toString().toLowerCase();

				Matcher valueMatcher = armorRegex.matcher(allLore);
				if (valueMatcher.find()) {
					armor = Integer.valueOf(armor.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
				}
			}
		}
		ItemStack item = entity.getEquipment().getItemInHand();
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			Object lore = item.getItemMeta().getLore();
			String allLore = lore.toString().toLowerCase();

			Matcher valueMatcher = armorRegex.matcher(allLore);
			if (valueMatcher.find()) {
				armor = Integer.valueOf(armor.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
			}
		}
		return armor.intValue();
	}

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
		return steal.intValue();
	}

	public static int getCritDamage(LivingEntity entity) {
		if (!critStamina(entity)) {
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
	
	/*
	 * Applies a players HP ARMOR + HP Base to get total HP.
	 * 
	 * @param entity A living entity.
	 */
	public static void applyHpBonus(LivingEntity entity) {
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
			
			//If the players HP is greater than the base HP + HP bonus,
			//set the players HP.  BaseHP + Armor HP.
			if (PlayerManager.getHealthPoints(playerName) > getBaseHealth(player) + hpToAdd.intValue()) {
				//Set player HP. BaseHP + Armor HP
				int newHP = getBaseHealth((Player)entity) + hpToAdd.intValue();
				double totalHP = PlayerManager.getMaxHealthPoints(playerName);
				
				if (newHP != totalHP) {
					//entity.setHealth(newHP);
					PlayerManager.setHealthPoints(entity.getName(), newHP);
					entity.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "New HP: " + ChatColor.RESET + newHP);
					
					entity.setHealth(20 * PlayerManager.getHealthPoints(playerName) / PlayerManager.getMaxHealthPoints(playerName));
				}
			}
			
			//Change the players MAX HP.
			int newMaxHP = getBaseHealth(player) + hpToAdd.intValue();
			double totalHP = PlayerManager.getMaxHealthPoints(playerName);
			if (newMaxHP != totalHP) {

				PlayerManager.setMaxHealthPoints(entity.getName(), newMaxHP);
				entity.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "New MaxHP: " + ChatColor.RESET + newMaxHP);

				entity.setHealth(20 * PlayerManager.getHealthPoints(playerName) / PlayerManager.getMaxHealthPoints(playerName));
			}
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
	public static int getBaseHealth(Player player) {
		int hp = PlayerManager.getBaseHealthPoints();
		return hp;
	}
	
	/*
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
	
	/*
	 * This method displays the users current equipped armor and weapon lore statistics.
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
