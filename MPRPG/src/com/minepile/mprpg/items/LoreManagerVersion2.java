package com.minepile.mprpg.items;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.items.ItemAttributes.ItemAttribute;

public class LoreManagerVersion2 {

	//setup instance variables
	public static MPRPG plugin;
	static LoreManagerVersion2 loreManagerV2Instance = new LoreManagerVersion2();
	
	//String regex.
	private static Pattern armorRegex = 		Pattern.compile("[+](\\d+)[ ](armor)");
	private static Pattern blindnessRegex = 	Pattern.compile("[+](\\d+)[ ](blindness)");
	private static Pattern blockChanceRegex = 	Pattern.compile("[+](\\d+)[ ](block chance)");
	private static Pattern coldDamageRegex = 	Pattern.compile("[+](\\d+)[ ](cold damage)");
	private static Pattern coldResistRegex = 	Pattern.compile("[+](\\d+)[ ](cold resistance)");
	private static Pattern critChanceRegex = 	Pattern.compile("[+](\\d+)[ ](critical hit chance)");
	private static Pattern damageRegex = 		Pattern.compile("[+](\\d+)[ ](damage)");
	private static Pattern dodgeChanceRegex =	Pattern.compile("[+](\\d+)[ ](dodge chance)");
	private static Pattern fireDamageRegex = 	Pattern.compile("[+](\\d+)[ ](fire damage)");
	private static Pattern fireResistRegex = 	Pattern.compile("[+](\\d+)[ ](fire resistance)");
	private static Pattern goldFindRegex = 		Pattern.compile("[+](\\d+)[ ](gold find)");
	private static Pattern healthRegex = 		Pattern.compile("[+](\\d+)[ ](health)");
	private static Pattern healthRegenRegex = 	Pattern.compile("[+](\\d+)[ ](health regeneration)");
	private static Pattern itemFindRegex = 		Pattern.compile("[+](\\d+)[ ](item find)");
	private static Pattern knockbackRegex = 	Pattern.compile("[+](\\d+)[ ](knockback)");
	private static Pattern lifestealRegex = 	Pattern.compile("[+](\\d+)[ ](lifesteal)");
	private static Pattern manaRegex = 			Pattern.compile("[+](\\d+)[ ](mana)");
	private static Pattern manaRegenRegex = 	Pattern.compile("[+](\\d+)[ ](mana regeneration)");
	private static Pattern poisonDamageRegex = 	Pattern.compile("[+](\\d+)[ ](poison damage)");
	private static Pattern poisonResistRegex = 	Pattern.compile("[+](\\d+)[ ](poison resistance)");
	private static Pattern reflectionRegex = 	Pattern.compile("[+](\\d+)[ ](damage reflection)");
	private static Pattern slownessRegex = 		Pattern.compile("[+](\\d+)[ ](slowness)");
	private static Pattern staminaRegex = 		Pattern.compile("[+](\\d+)[ ](stamina)");
	private static Pattern staminaRegenRegex = 	Pattern.compile("[+](\\d+)[ ](stamina regeneration)");
	private static Pattern thornDamageRegex = 	Pattern.compile("[+](\\d+)[ ](thorn damage)");
	private static Pattern thornResistRegex = 	Pattern.compile("[+](\\d+)[ ](thorn resistance)");

	//Create instance
	public static LoreManagerVersion2 getInstance() {
		return loreManagerV2Instance;
	}

	//Setup LoveManager V2
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	

	public void togglePlayerDamage(Entity damager, Entity victim) {

		if (damager instanceof LivingEntity) {
			LivingEntity livingDamager = (LivingEntity) damager;
			double damage = getDamageBonus(livingDamager);
			double coldDamage = getColdDamage((LivingEntity) damager) - getColdResist((LivingEntity) victim);
			double fireDamage = getFireDamage(livingDamager) - getFireResist((LivingEntity) victim);
			double poisonDamage = getPoisonDamage(livingDamager) - getPoisonResist((LivingEntity) victim);
			double thronDamage = getThornDamage(livingDamager) - getThornResist((LivingEntity) victim);

			double totalDamage = damage + coldDamage + fireDamage + poisonDamage + thronDamage;

			//TODO: Something here
			
		} else if (damager instanceof Arrow) {
			double damage = getArrorDamage(damager);
		}
	}

	/**
	 * This will apply the players current attribute totals placed on armor.
	 * @param player
	 */
	public void applyHPBonus(Player player) {
		//TODO: Apply player HP to Minecraft HP Bar and Player HP Hashmap.

		//Loop through the armor slots and find armor.
		getHealthPointsBonus(player);
		

		//Show the total attributes the player currently has.
		displayAttributes(player);
	}

	public void displayAttributes(Player player) {
		//TODO: Show player all of their attribute totals.
		
		String stringPrefix = ChatColor.GREEN + "";
		String seperator = ChatColor.DARK_GRAY + ": ";
		String stringSuffix = ChatColor.YELLOW + "";
		
		//Show player their total attributes.
		player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Your Attributes" 
				+ ChatColor.DARK_GRAY + ChatColor.BOLD + ":");
		player.sendMessage("");
		player.sendMessage(stringPrefix +"Armor" + seperator + stringSuffix + Integer.toString((int) getArmorBonus(player)));
		player.sendMessage(stringPrefix +"Blindness" + seperator + stringSuffix + Integer.toString((int) getBlindnessBonus(player)));
		player.sendMessage(stringPrefix +"Block" + seperator + stringSuffix + Integer.toString((int) getBlockBonus(player)));
		player.sendMessage(stringPrefix +"Cold Damage" + seperator + stringSuffix + Integer.toString((int) getColdDamage(player)));
		player.sendMessage(stringPrefix +"Cold Resist" + stringSuffix + Integer.toString((int) getColdResist(player)));
		player.sendMessage(stringPrefix +"Critical Hit Chance" + seperator + stringSuffix + Integer.toString((int) getCriticalBonus(player)));
		player.sendMessage(stringPrefix +"Damage" + seperator + stringSuffix + Integer.toString((int) getDamageBonus(player)));
		player.sendMessage(stringPrefix +"Dodge" + seperator + stringSuffix + Integer.toString((int) getDodgeBonus(player)));
		player.sendMessage(stringPrefix +"Fire Damage" + seperator + stringSuffix + Integer.toString((int) getFireDamage(player)));
		player.sendMessage(stringPrefix +"Fire Resist" + seperator + stringSuffix + Integer.toString((int) getFireResist(player)));
		player.sendMessage(stringPrefix +"Gold Find" + seperator + stringSuffix + Integer.toString((int) getGoldFindBonus(player)));
		player.sendMessage(stringPrefix +"HP" + seperator + stringSuffix + Integer.toString((int) getHealthPointsBonus(player)));
		player.sendMessage(stringPrefix +"HP Regen" + seperator + stringSuffix + Integer.toString((int) getHealthPointsRegenerate(player)));
		player.sendMessage(stringPrefix +"Item Find" + seperator + stringSuffix + Integer.toString((int) getItemFindBonus(player)));
		player.sendMessage(stringPrefix +"Knockback" + seperator + stringSuffix + Integer.toString((int) getKnockBackBonus(player)));
		player.sendMessage(stringPrefix +"Lifesteal" + seperator + stringSuffix + Integer.toString((int) getLifeStealBonus(player)));
		player.sendMessage(stringPrefix +"Mana" + seperator + stringSuffix + Integer.toString((int) getManaPointsBonus(player)));
		player.sendMessage(stringPrefix +"Mana Regen" + seperator + stringSuffix + Integer.toString((int) getManaPointsRegenerate(player)));
		player.sendMessage(stringPrefix +"Poison Damage" + seperator + stringSuffix + Integer.toString((int) getPoisonDamage(player)));
		player.sendMessage(stringPrefix +"Poison Resist" + seperator + stringSuffix + Integer.toString((int) getPoisonResist(player)));
		player.sendMessage(stringPrefix +"Reflection" + seperator + stringSuffix + Integer.toString((int) getReflectionBonus(player)));
		player.sendMessage(stringPrefix +"Slow" + seperator + stringSuffix + Integer.toString((int) getSlowBonus(player)));
		player.sendMessage(stringPrefix +"Stamina" + seperator + stringSuffix + Integer.toString((int) getStaminaBonus(player)));
		player.sendMessage(stringPrefix +"Stamina Regen" + seperator + stringSuffix + Integer.toString((int) getStaminaRegenerate(player)));
		player.sendMessage(stringPrefix +"Thorn Damage" + seperator + stringSuffix + Integer.toString((int) getThornDamage(player)));
		player.sendMessage(stringPrefix +"Thorn Resist" + seperator + stringSuffix + Integer.toString((int) getThornResist(player)));
	}

	private void applyEffect(LivingEntity entity, ItemAttributes itemAttribute, int duration, int amplifier) {
		if (itemAttribute.equals(ItemAttribute.BLINDNESS_EFFECT)) {
			entity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration, amplifier));

		} else if (itemAttribute.equals(ItemAttribute.BLOCK_EFFECT)) {
			Bukkit.getWorlds().get(0).playSound(entity.getLocation(), Sound.ANVIL_LAND, .8f, .8f);

		} else if (itemAttribute.equals(ItemAttribute.COLD_EFFECT)) {
			entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, amplifier));

		} else if (itemAttribute.equals(ItemAttribute.CRITICAL_EFFECT)) {
			Bukkit.getWorlds().get(0).playSound(entity.getLocation(), Sound.HURT_FLESH, .8f, .8f);

		} else if (itemAttribute.equals(ItemAttribute.DAMAGE_EFFECT)) {
			//TODO: Cool blood particle effect.

		} else if (itemAttribute.equals(ItemAttribute.DODGE_EFFECT)) {
			Bukkit.getWorlds().get(0).playSound(entity.getLocation(), Sound.BAT_TAKEOFF, .8f, .8f);

		} else if (itemAttribute.equals(ItemAttribute.FIRE_EFFECT)) {
			entity.setFireTicks(duration);

		} else if (itemAttribute.equals(ItemAttribute.KNOCKBACK_EFFECT)) {
			Bukkit.getWorlds().get(0).playSound(entity.getLocation(), Sound.SHEEP_SHEAR, .8f, .8f);

		} else if (itemAttribute.equals(ItemAttribute.LIFESTEAL_EFFECT)) {
			Bukkit.getWorlds().get(0).playSound(entity.getLocation(), Sound.PORTAL_TRAVEL, .8f, .8f);

		} else if (itemAttribute.equals(ItemAttribute.POISION_EFFECT)) {
			entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, duration, amplifier));

		} else if (itemAttribute.equals(ItemAttribute.SLOW_EFFECT)) {
			entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, amplifier));

		} else if (itemAttribute.equals(ItemAttribute.THORN_EFFECT)) {
			Bukkit.getWorlds().get(0).playSound(entity.getLocation(), Sound.DIG_STONE, .8f, .8f);

		}
	}
	
	private double getArmorBonus(LivingEntity entity) {
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
		//Return the value.
		return value.intValue();
	}

	private double getArrorDamage(Entity damager) {
		// TODO Auto-generated method stub
		return 0;
	}

	private double getBlindnessBonus(LivingEntity entity) {
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
				Matcher matcher = blindnessRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		//Return the value.
		return value.intValue();
	}

	private double getBlockBonus(LivingEntity entity) {
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
				Matcher matcher = blockChanceRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		//Return the value.
		return value.intValue();
	}

	private double getColdDamage(LivingEntity entity) {
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
				Matcher matcher = coldDamageRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		//Return the value.
		return value.intValue();
	}

	private double getColdResist(LivingEntity entity) {
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
				Matcher matcher = coldResistRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		//Return the value.
		return value.intValue();
	}


	private double getCriticalBonus(LivingEntity entity) {
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
				Matcher matcher = critChanceRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		//Return the value.
		return value.intValue();
	}


	private double getDamageBonus(LivingEntity entity) {
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
		//Return the value.
		return value.intValue();
	}


	private double getDodgeBonus(LivingEntity entity) {
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
				Matcher matcher = dodgeChanceRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		//Return the value.
		return value.intValue();
	}

	private double getFireDamage(LivingEntity entity) {
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
		//Return the value.
		return value.intValue();
	}

	private double getFireResist(LivingEntity entity) {
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
				Matcher matcher = fireResistRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		//Return the value.
		return value.intValue();
	}

	private double getGoldFindBonus(LivingEntity entity) {
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
		//Return the value.
		return value.intValue();
	}

	private double getHealthPointsBonus(LivingEntity entity) {
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
				Matcher matcher = healthRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		//Return the value.
		return value.intValue();
	}

	private double getHealthPointsRegenerate(LivingEntity entity) {
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
				Matcher matcher = healthRegenRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		//Return the value.
		return value.intValue();
	}


	private double getItemFindBonus(LivingEntity entity) {
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
				Matcher matcher = itemFindRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		//Return the value.
		return value.intValue();
	}

	private double getKnockBackBonus(LivingEntity entity) {
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
		//Return the value.
		return value.intValue();
	}

	private double getLifeStealBonus(LivingEntity entity) {
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
		//Return the value.
		return value.intValue();
	}

	private double getManaPointsBonus(LivingEntity entity) {
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
				Matcher matcher = manaRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		//Return the value.
		return value.intValue();
	}

	private double getManaPointsRegenerate(LivingEntity entity) {
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
				Matcher matcher = manaRegenRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		//Return the value.
		return value.intValue();
	}


	private double getPoisonDamage(LivingEntity entity) {
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
		//Return the value.
		return value.intValue();
	}

	private double getPoisonResist(LivingEntity entity) {
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
				Matcher matcher = poisonResistRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		//Return the value.
		return value.intValue();
	}

	private double getReflectionBonus(LivingEntity entity) {
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
				Matcher matcher = reflectionRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		//Return the value.
		return value.intValue();
	}

	private double getSlowBonus(LivingEntity entity) {
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
				Matcher matcher = slownessRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		//Return the value.
		return value.intValue();
	}

	private double getStaminaBonus(LivingEntity entity) {
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
				Matcher matcher = staminaRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		//Return the value.
		return value.intValue();
	}

	private double getStaminaRegenerate(LivingEntity entity) {
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
				Matcher matcher = staminaRegenRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		//Return the value.
		return value.intValue();
	}


	private double getThornDamage(LivingEntity entity) {
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
				Matcher matcher = thornDamageRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		//Return the value.
		return value.intValue();
	}

	private double getThornResist(LivingEntity entity) {
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
				Matcher matcher = thornResistRegex.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					value = Integer.valueOf(value.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		//Return the value.
		return value.intValue();
	}

}
