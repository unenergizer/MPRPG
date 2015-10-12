package com.minepile.mprpg.items;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;

public class ItemLoreFactory {

	//setup instance variables
	public static MPRPG plugin;
	static ItemLoreFactory loreManagerV2Instance = new ItemLoreFactory();

	//Base attributes regex.
	private static Pattern strengthRegex = 		Pattern.compile("[+](\\d+)[ ](strength)");
	private static Pattern agilityRegex = 		Pattern.compile("[+](\\d+)[ ](agility)");
	private static Pattern vitalityRegex = 		Pattern.compile("[+](\\d+)[ ](vitality)");
	private static Pattern intellectRegex = 	Pattern.compile("[+](\\d+)[ ](intellect)");
	private static Pattern spiritRegex = 		Pattern.compile("[+](\\d+)[ ](spirit)");
	private static Pattern armorRegex = 		Pattern.compile("(\\d+)[ ](armor)");
	private static Pattern damageRegex = 		Pattern.compile("[+](\\d+)[ ](damage)");
	private static Pattern damageRangeRegex = 	Pattern.compile("[+](\\d+)( - )(\\d+)[ ](damage)");

	//Damage attributes regex
	private static Pattern reflectRegex = 		Pattern.compile("[+](\\d+)[ ](reflect)");
	private static Pattern blockRegex = 		Pattern.compile("[+](\\d+)[ ](block)");
	private static Pattern dodgeRegex = 		Pattern.compile("[+](\\d+)[ ](dodge)");
	private static Pattern criticalDamageRegex =Pattern.compile("[+](\\d+)[ ](critical hit damage)");
	private static Pattern criticalChanceRegex =Pattern.compile("[+](\\d+)[ ](critical hit chance)");
	
	//Magic damage
	private static Pattern fireDamageRegex =	Pattern.compile("[+](\\d+)[ ](fire damage)");
	private static Pattern iceDamageRegex = 	Pattern.compile("[+](\\d+)[ ](ice damage)");
	private static Pattern lightningDamageRegex = Pattern.compile("[+](\\d+)[ ](lightning damage)");
	private static Pattern poisonDamageRegex = 	Pattern.compile("[+](\\d+)[ ](poison damage)");
	private static Pattern paralyzeDamageRegex =Pattern.compile("[+](\\d+)[ ](paralyze)");
	private static Pattern blindnessDamageRegex =Pattern.compile("[+](\\d+)[ ](blindness)");

	//Magic Resistnce
	private static Pattern fireResistanceRegex =Pattern.compile("[+](\\d+)[ ](fire resistance)");
	private static Pattern iceResistanceRegex =Pattern.compile("[+](\\d+)[ ](ice resistance)");
	private static Pattern lightningResistanceRegex =Pattern.compile("[+](\\d+)[ ](lightning resistance)");
	private static Pattern poisonResistanceRegex =Pattern.compile("[+](\\d+)[ ](poison resistance)");
	private static Pattern paralyzeResistanceRegex =Pattern.compile("[+](\\d+)[ ](paralyze resistance)");
	private static Pattern blindnessResistanceRegex =Pattern.compile("[+](\\d+)[ ](blindness resistance)");

	//Extras
	private static Pattern waterbreathingRegex =Pattern.compile("[+](\\d+)[ ](waterbreathing)");
	private static Pattern personalityRegex =Pattern.compile("[+](\\d+)[ ](personality)");
	private static Pattern goldFindRegex =Pattern.compile("[+](\\d+)[ ](gold find)");
	private static Pattern magicFindRegex =Pattern.compile("[+](\\d+)[ ](magic find)");
	private static Pattern knockbackRegex =Pattern.compile("[+](\\d+)[ ](knockback)");
	private static Pattern lifestealRegex =Pattern.compile("[+](\\d+)[ ](lifesteal)");

	//Create instance
	public static ItemLoreFactory getInstance() {
		return loreManagerV2Instance;
	}

	//Setup LoveManager V2
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	

	/**
	 * This is used to test and show if attributes are being read from armor and weapon.
	 * 
	 * @param player The player who's armor and weapon we are reading from.
	 */
	public void displayAttributes(Player player) {

		String stringPrefix = ChatColor.GREEN + "";
		String seperator = ChatColor.DARK_GRAY + ": ";
		String stringSuffix = ChatColor.YELLOW + "";

		player.sendMessage(" ");
		player.sendMessage(" ");
		player.sendMessage(" ");
		//Show player their total attributes.
		player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Your Attributes" 
				+ ChatColor.DARK_GRAY + ChatColor.BOLD + ":");
		player.sendMessage("BASE:");
		player.sendMessage(stringPrefix +"Strength" + seperator + stringSuffix + Integer.toString((int) getStrength(player)));
		player.sendMessage(stringPrefix +"Agility" + seperator + stringSuffix + Integer.toString((int) getAgility(player)));
		player.sendMessage(stringPrefix +"Vitality" + seperator + stringSuffix + Integer.toString((int) getVitality(player)));
		player.sendMessage(stringPrefix +"Intellect" + seperator + stringSuffix + Integer.toString((int) getIntellect(player)));
		player.sendMessage(stringPrefix +"Spirit" + seperator + stringSuffix + Integer.toString((int) getSpirit(player)));
		player.sendMessage("Combat:");
		player.sendMessage(stringPrefix +"Armor" + seperator + stringSuffix + Integer.toString((int) getArmor(player)));
		player.sendMessage(stringPrefix +"Damage" + seperator + stringSuffix + Integer.toString((int) getDamage(player)));
		//player.sendMessage(stringPrefix +"DamageRange" + seperator + stringSuffix + Integer.toString((int) getDamageRange(player)));
		player.sendMessage(stringPrefix +"Reflect" + seperator + stringSuffix + Integer.toString((int) getReflect(player)));
		player.sendMessage(stringPrefix +"Block" + seperator + stringSuffix + Integer.toString((int) getBlock(player)));
		player.sendMessage(stringPrefix +"Dodge" + seperator + stringSuffix + Integer.toString((int) getDodge(player)));
		player.sendMessage(stringPrefix +"CriticalDamage" + seperator + stringSuffix + Integer.toString((int) getCriticalDamage(player)));
		player.sendMessage(stringPrefix +"CriticalChance" + seperator + stringSuffix + Integer.toString((int) getCriticalHitChance(player)));
		player.sendMessage(stringPrefix +"Knockback" + seperator + stringSuffix + Integer.toString((int) getKnockback(player)));
		player.sendMessage(stringPrefix +"Lifesteal" + seperator + stringSuffix + Integer.toString((int) getLifesteal(player)));
		player.sendMessage("Magic Damage:");
		player.sendMessage(stringPrefix +"FireDamage" + seperator + stringSuffix + Integer.toString((int) getFireDamage(player)));
		player.sendMessage(stringPrefix +"IceDamage" + seperator + stringSuffix + Integer.toString((int) getIceDamage(player)));
		player.sendMessage(stringPrefix +"LightningDamage" + seperator + stringSuffix + Integer.toString((int) getLightningDamage(player)));
		player.sendMessage(stringPrefix +"PoisonDamage" + seperator + stringSuffix + Integer.toString((int) getPoisonDamage(player)));
		player.sendMessage(stringPrefix +"ParalyzeDamage" + seperator + stringSuffix + Integer.toString((int) getParalyzeDamage(player)));
		player.sendMessage(stringPrefix +"BlindnessDamage" + seperator + stringSuffix + Integer.toString((int) getBlindnessDamage(player)));
		player.sendMessage("Magic Resistance:");
		player.sendMessage(stringPrefix +"FireResistance" + seperator + stringSuffix + Integer.toString((int) getFireResistance(player)));
		player.sendMessage(stringPrefix +"IceResistance" + seperator + stringSuffix + Integer.toString((int) getIceResistance(player)));
		player.sendMessage(stringPrefix +"LightningResistance" + seperator + stringSuffix + Integer.toString((int) getLightningResistance(player)));
		player.sendMessage(stringPrefix +"PoisonResistance" + seperator + stringSuffix + Integer.toString((int) getPoisonResistance(player)));
		player.sendMessage(stringPrefix +"ParalyzeResistance" + seperator + stringSuffix + Integer.toString((int) getParalyzeResistance(player)));
		player.sendMessage(stringPrefix +"BlindnessResistance" + seperator + stringSuffix + Integer.toString((int) getBlindnessResistance(player)));
		player.sendMessage("Extras:");
		player.sendMessage(stringPrefix +"Waterbreathing" + seperator + stringSuffix + Integer.toString((int) getWaterbreathing(player)));
		player.sendMessage(stringPrefix +"Personality" + seperator + stringSuffix + Integer.toString((int) getPersonality(player)));
		player.sendMessage(stringPrefix +"GoldFind" + seperator + stringSuffix + Integer.toString((int) getGoldFind(player)));
		player.sendMessage(stringPrefix +"MagicFind" + seperator + stringSuffix + Integer.toString((int) getMagicFind(player)));
	}
	
	public static double getStrength(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());
				
				//Prepare for text matching using regular expressions.
				Matcher matcher = strengthRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					//Add the value to the total.
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = strengthRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getAgility(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = agilityRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					//Add the value to the total.
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = agilityRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getVitality(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = vitalityRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = vitalityRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getIntellect(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = intellectRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = intellectRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getSpirit(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = spiritRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = spiritRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getArmor(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = armorRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = armorRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getDamage(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = damageRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = damageRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}

	public static boolean getDamageRange(LivingEntity entity) {
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
				String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
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
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			//Prepare the string to math the damage range.
			Matcher rangeMatcher = damageRangeRegex.matcher(allLore);
			//If a match is found, return true.
			if (rangeMatcher.find()) {
				return true;
			}
		}
		return false;
	}
	
	public static double getReflect(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = reflectRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = reflectRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getBlock(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = blockRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = blockRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getDodge(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = dodgeRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = dodgeRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getCriticalDamage(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = criticalDamageRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = criticalDamageRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getCriticalHitChance(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = criticalChanceRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = criticalChanceRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getFireDamage(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = fireDamageRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = fireDamageRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getIceDamage(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = iceDamageRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = iceDamageRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getLightningDamage(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = lightningDamageRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = lightningDamageRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getPoisonDamage(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = poisonDamageRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = poisonDamageRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getParalyzeDamage(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = paralyzeDamageRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = paralyzeDamageRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getBlindnessDamage(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = blindnessDamageRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = blindnessDamageRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getFireResistance(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = fireResistanceRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = fireResistanceRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getIceResistance(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = iceResistanceRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = iceResistanceRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getLightningResistance(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = lightningResistanceRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = lightningResistanceRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getPoisonResistance(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = poisonResistanceRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = poisonResistanceRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getParalyzeResistance(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = paralyzeResistanceRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = paralyzeResistanceRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getBlindnessResistance(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = blindnessResistanceRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = blindnessResistanceRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getWaterbreathing(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = waterbreathingRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = waterbreathingRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getPersonality(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = personalityRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = personalityRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getGoldFind(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = goldFindRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = goldFindRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getMagicFind(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = magicFindRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = magicFindRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getKnockback(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = knockbackRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = knockbackRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
	
	public static double getLifesteal(LivingEntity entity) {
		Integer value = Integer.valueOf(0);

		//Loop through the armor slots and find armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = lifestealRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		
		//Check the item in the players hand.
		ItemStack item = entity.getEquipment().getItemInHand();
		//If the item is not null and has meta and has lore, continue.
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = item.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher valueMatcher = lifestealRegex.matcher(allLore);
			
			//Find the new value.
			if (valueMatcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		//Return the value.
		return value.intValue();
	}
}
