package com.minepile.mprpg.items;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.items.ItemQualityManager.ItemQuality;
import com.minepile.mprpg.items.ItemTierManager.ItemTier;

public class RandomItemFactory {

	//setup instance variables
	public static MPRPG plugin;
	static RandomItemFactory randomItemFactoryInstance = new RandomItemFactory();
	private static String randomArmorFilePath = "plugins/MPRPG/items/RandomArmor.yml";
	private static String randomWeaponFilePath = "plugins/MPRPG/items/RandomWeapon.yml";

	//Configuration file that holds item information.
	private static File armorConfigFile;
	private static File weaponConfigFile;

	private static FileConfiguration armorConfig;
	private static FileConfiguration weaponConfig;
	
	//String Attribute names
	private static String strengthStr = ChatColor.GRAY + " Strength";
	private static String agilityStr = ChatColor.GRAY + " Agility";
	private static String staminaStr = ChatColor.GRAY + " Stamina";
	private static String intellectStr = ChatColor.GRAY + " Intellect";
	private static String spiritStr = ChatColor.GRAY + " Spirit";
	private static String armorStr = ChatColor.GRAY + " Armor";
	private static String damageStr = ChatColor.GRAY + " Damage";
	
	private static String fireResistStr = ChatColor.GRAY + " Fire Resistance";
	private static String iceResistStr = ChatColor.GRAY + " ice Resistance";
	private static String lightningResistStr = ChatColor.GRAY + " Lightning Resistance";
	private static String poisonResistStr = ChatColor.GRAY + " Poison Resistance";
	private static String paralyzeResistStr = ChatColor.GRAY + " Paralyze Resistance";
	private static String blindnessResistStr = ChatColor.GRAY + " Blindness Resistance";
	
	private static String fireDamageStr = ChatColor.GRAY + " Fire Damage";
	private static String iceDamageStr = ChatColor.GRAY + " ice Damage";
	private static String lightningDamageStr = ChatColor.GRAY + " Lightning Damage";
	private static String poisonDamageStr = ChatColor.GRAY + " Poison Damage";
	private static String paralyzeDamageStr = ChatColor.GRAY + " Paralyze Damage";
	private static String blindnessDamageStr = ChatColor.GRAY + " Blindness Damage";
	
	private static String waterBreathingStr = ChatColor.GRAY + " Waterbreathing";
	private static String personalityStr = ChatColor.GRAY + " Personality";
	private static String goldFindStr = ChatColor.GRAY + " Gold Find";
	private static String magicFindStr = ChatColor.GRAY + " Magic Find";
	private static String criticalChanceStr = ChatColor.GRAY + " Critical Chance";
	private static String criticalDamageStr = ChatColor.GRAY + " Critical Damage";
	private static String knockbackStr = ChatColor.GRAY + " Knockback";
	private static String lifestealStr = ChatColor.GRAY + " Lifesteal";

	//Create instance
	public static RandomItemFactory getInstance() {
		return randomItemFactoryInstance;
	}

	//Setup RandomItemFactory
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		if(!(new File(randomArmorFilePath)).exists()){
			createConfig();
		}
		if(!(new File(randomWeaponFilePath)).exists()){
			createConfig();
		}
	}

	/**
	 * This will take a number of attributes in a list and randomly select
	 * a number of attributes based on the minimum and maximum attribute values.
	 * 
	 * @param list A list of attributes.
	 * @param attributesMin The minimum amount of attributes to return.
	 * @param attributesMax The maximum amount of attributes to return.
	 * @return A new list of processedBase attributes that will be on an item.
	 */
	private static ArrayList<String> getRandomArrayIndex(ArrayList<String> list, int attributesMin, int attributesMax) {
		ArrayList<String> tempList = new ArrayList<String>();
		int roll = randomInt(attributesMin, attributesMax);

		//Loops a number of times based on how many attributes are needed.
		for (int i = 1; i <= roll; i++) {

			//Get random number from 0 (array index start) to array size (-1).
			int randIndex = randomInt(0, list.size() - 1);
			String attribute = list.get(randIndex);

			//Prevent duplicates.
			if (!tempList.contains(attribute)) {
				tempList.add(attribute);
			}
		}

		//Return new array.
		return tempList;
	}

	/**
	 * Generates a random integer between the min value and the max value.
	 * 
	 * @param min the minimal value
	 * @param max the maximum value
	 * @return a random value between the min value and the max value
	 */
	public static int randomInt(int min, int max) {
		Random rand = new Random();

		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	/**
	 * Generates random attributes based on the item tier and quality.
	 * 
	 * @param item The item that we are adding attributes to.
	 * @param tier The tier of the item we are adding attributes to.
	 * @param quality The quality of the item we are adding attributes to.
	 * @param soulbound This will make the item trade-able or not.
	 * @return Returns a item with random attributes.
	 */
	public static ItemStack createArmor(ItemStack item, ItemTier tier, ItemQuality quality, boolean soulbound) {

		/////////////////////////////
		/// GRAB ITEM FROM CONFIG ///
		/////////////////////////////
		armorConfigFile = new File(randomArmorFilePath);
		armorConfig =  YamlConfiguration.loadConfiguration(armorConfigFile);

		//String name = tier.replaceAll("_", " ");
		//List<String> itemDescription = (List<String>) armorConfig.getList(tier + ".itemDescription");

		/**
		 * Base Stats:
		 * 	Strength
		 * 	Agility
		 * 	Stamina
		 * 	Intellect
		 * 	Spirit
		 * 	+ Armor (must have every time)
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
		 *  Critical Chance
		 */

		//Base stats
		String configPath = tier + "." + quality;
		
		int strengthMin = armorConfig.getInt(configPath + ".strengthMin");
		int strengthMax = armorConfig.getInt(configPath + ".strengthMax");
		int agilityMin = armorConfig.getInt(configPath + ".agilityMin");
		int agilityMax = armorConfig.getInt(configPath + ".agilityMax");
		int staminaMin = armorConfig.getInt(configPath + ".staminaMin");
		int staminaMax = armorConfig.getInt(configPath + ".staminaMax");
		int intellectMin = armorConfig.getInt(configPath + ".intellectMin");
		int intellectMax = armorConfig.getInt(configPath + ".intellectMax");
		int spiritMin = armorConfig.getInt(configPath + ".spiritMin");
		int spiritMax = armorConfig.getInt(configPath + ".spiritMax");
		int armorMin = armorConfig.getInt(configPath + ".armorMin");		//EVERY ARMOR ITEM MUST HAVE ARMOR STAT!!!
		int armorMax = armorConfig.getInt(configPath + ".armorMax");		//EVERY ARMOR ITEM MUST HAVE ARMOR STAT!!!

		//Resistances
		int fireResistMin = armorConfig.getInt(configPath + ".fireResistMin");
		int fireResistMax = armorConfig.getInt(configPath + ".fireResistMax");
		int iceResistMin = armorConfig.getInt(configPath + ".iceResistMin");
		int iceResistMax = armorConfig.getInt(configPath + ".iceResistMax");
		int lightningResistMin = armorConfig.getInt(configPath + ".lightningResistMin");
		int lightningResistMax = armorConfig.getInt(configPath + ".lightningResistMax");
		int poisonResistMin = armorConfig.getInt(configPath + ".poisonResistMin");
		int poisonResistMax = armorConfig.getInt(configPath + ".poisonResistMax");
		int paralyzeResistMin = armorConfig.getInt(configPath + ".paralyzeResistMin");
		int paralyzeResistMax = armorConfig.getInt(configPath + ".paralyzeResistMax");
		int blindnessResistMin = armorConfig.getInt(configPath + ".blindnessResistMin");
		int blindnessResistMax = armorConfig.getInt(configPath + ".blindnessResistMax");

		//Extras
		int waterBreathingMin = armorConfig.getInt(configPath + ".waterBreathingMin");
		int waterBreathingMax = armorConfig.getInt(configPath + ".waterBreathingMax");
		int personalityMin = armorConfig.getInt(configPath + ".personalityMin");
		int personalityMax = armorConfig.getInt(configPath + ".personalityMax");
		int goldFindMin = armorConfig.getInt(configPath + ".goldFindMin");
		int goldFindMax = armorConfig.getInt(configPath + ".goldFindMax");
		int magicFindMin = armorConfig.getInt(configPath + ".magicFindMin");
		int magicFindMax = armorConfig.getInt(configPath + ".magicFindMax");
		int criticalChanceMin = armorConfig.getInt(configPath + ".criticalChanceMin");
		int criticalChanceMax = armorConfig.getInt(configPath + ".criticalChanceMax");


		////////////////////////////
		/// Calculate Item Stats ///
		////////////////////////////

		//Base stats
		int strength = randomInt(strengthMin, strengthMax);
		int agility = randomInt(agilityMin, agilityMax);
		int stamina = randomInt(staminaMin, staminaMax);
		int intellect = randomInt(intellectMin, intellectMax);
		int spirit = randomInt(spiritMin, spiritMax);
		int armor = randomInt(armorMin, armorMax);		//EVERY ARMOR ITEM MUST HAVE ARMOR STAT!!!

		//Resistances
		int fireResist = randomInt(fireResistMin, fireResistMax);
		int iceResist = randomInt(iceResistMin, iceResistMax);
		int lightningResist = randomInt(lightningResistMin, lightningResistMax);
		int poisonResist = randomInt(poisonResistMin, poisonResistMax);
		int paralyzeResist = randomInt(paralyzeResistMin, paralyzeResistMax);
		int blindnessResist = randomInt(blindnessResistMin, blindnessResistMax);

		//Extras
		int waterBreathing = randomInt(waterBreathingMin, waterBreathingMax);
		int personality = randomInt(personalityMin, personalityMax);
		int goldFind = randomInt(goldFindMin, goldFindMax);
		int magicFind = randomInt(magicFindMin, magicFindMax);
		int criticalChance = randomInt(criticalChanceMin, criticalChanceMax);

		///////////////////////////////////
		/// PICK RANDOM ITEM ATTRIBUTES ///
		///////////////////////////////////

		/**
		 * ItemTiers: T1, T2, T3, T4, T5, T6
		 * 
		 * ItemQuality: Junk, Common, Uncommon, Rare, Epic, Legendary
		 */

		//Final arrays (processedBase).
		ArrayList<String> attributesBaseFinal = new ArrayList<String>();
		ArrayList<String> attributesMagicFinal = new ArrayList<String>();
		ArrayList<String> attributesExtrasFinal = new ArrayList<String>();

		//Temporary arrays
		ArrayList<String> attributesBaseTemp = new ArrayList<String>();
		ArrayList<String> attributesMagicTemp = new ArrayList<String>();
		ArrayList<String> attributesExtrasTemp = new ArrayList<String>();

		//Always add "armor" attribute to an armor item.
		//Lets do this beforehand, it does not need to be picked, its automatic.
		attributesBaseFinal.add(ChatColor.YELLOW + Integer.toString(armor) + armorStr);

		//Lets start the break down and see what attributes can be on what tier and quality item.

		if (quality.equals(ItemQuality.JUNK)) {

			////////////////////////////////////////////////
			//// JUNK //////////////////////////////////////
			////////////////////////////////////////////////

			attributesBaseTemp.add(ChatColor.YELLOW + "+" + strength + strengthStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + agility + agilityStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + stamina + staminaStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + intellect + intellectStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + spirit + spiritStr);


			//Get random amount of attributes (1 or 2).
			if (tier.equals(ItemTier.T1)) {
				//ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 1);
			} else if (tier.equals(ItemTier.T2)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T3)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T4)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T5)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T6)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
				//Clean up
				processedBase.clear();
			}

			//Lets do some cleanup.
			attributesBaseTemp.clear();
			attributesMagicTemp.clear();
			attributesExtrasTemp.clear();

		} else if (quality.equals(ItemQuality.COMMON)) {

			////////////////////////////////////////////////
			//// COMMON ////////////////////////////////////
			////////////////////////////////////////////////

			//Randomly pick 1 or 2 attributes.

			attributesBaseTemp.add(ChatColor.YELLOW + "+" + strength + strengthStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + agility + agilityStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + stamina + staminaStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + intellect + intellectStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + spirit + spiritStr);

			//Get random amount of attributes (1 or 2).
			if (tier.equals(ItemTier.T1)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
			} else if (tier.equals(ItemTier.T2)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T3)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T4)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T5)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T6)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
				//Clean up
				processedBase.clear();
			}

			//Lets do some cleanup.
			attributesBaseTemp.clear();
			attributesMagicTemp.clear();
			attributesExtrasTemp.clear();

		} else if (quality.equals(ItemQuality.UNCOMMON)) {

			////////////////////////////////////////////////
			//// UNCOMMON //////////////////////////////////
			////////////////////////////////////////////////


			//Randomly pick 1, 2, or 3 attributes,
			//With possibility of magic resistance and
			//attribute extras.

			attributesBaseTemp.add(ChatColor.YELLOW + "+" + strength + strengthStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + agility + agilityStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + stamina + staminaStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + intellect + intellectStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + spirit + spiritStr);

			attributesMagicTemp.add(ChatColor.YELLOW + "+" + fireResist + fireResistStr);
			attributesMagicTemp.add(ChatColor.YELLOW + "+" + iceResist + iceResistStr);
			attributesMagicTemp.add(ChatColor.YELLOW + "+" + lightningResist + lightningResistStr);
			attributesMagicTemp.add(ChatColor.YELLOW + "+" + poisonResist + poisonResistStr);
			attributesMagicTemp.add(ChatColor.YELLOW + "+" + paralyzeResist + paralyzeResistStr);
			attributesMagicTemp.add(ChatColor.YELLOW + "+" + blindnessResist + blindnessResistStr);

			attributesExtrasTemp.add(ChatColor.YELLOW + "+" + waterBreathing + waterBreathingStr);
			attributesExtrasTemp.add(ChatColor.YELLOW + "+" + personality + personalityStr);
			attributesExtrasTemp.add(ChatColor.YELLOW + "+" + goldFind + goldFindStr);
			attributesExtrasTemp.add(ChatColor.YELLOW + "+" + magicFind + " Magic Find");
			attributesExtrasTemp.add(ChatColor.YELLOW + "+" + criticalChance + criticalChanceStr);

			//Get random amount of attributes (1 or 2).
			if (tier.equals(ItemTier.T1)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 1, 2);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 0, 1);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T2)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 1, 2);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 0, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T3)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 1, 2);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 0, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T4)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 1, 2);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 0, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T5)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 1, 2);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 0, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T6)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 1, 2);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 0, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			}

			//Lets do some cleanup.
			attributesBaseTemp.clear();
			attributesMagicTemp.clear();
			attributesExtrasTemp.clear();

		} else if (quality.equals(ItemQuality.EPIC)) {

			////////////////////////////////////////////////
			//// EPIC //////////////////////////////////////
			////////////////////////////////////////////////

			//Randomly pick 2 or 3 attributes,
			//With possibility of magic resistance and
			//attribute extras.

			attributesBaseTemp.add(ChatColor.YELLOW + "+" + strength + strengthStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + agility + agilityStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + stamina + staminaStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + intellect + intellectStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + spirit + spiritStr);

			attributesMagicTemp.add(ChatColor.YELLOW + "+" + fireResist + fireResistStr);
			attributesMagicTemp.add(ChatColor.YELLOW + "+" + iceResist + iceResistStr);
			attributesMagicTemp.add(ChatColor.YELLOW + "+" + lightningResist + lightningResistStr);
			attributesMagicTemp.add(ChatColor.YELLOW + "+" + poisonResist + poisonResistStr);
			attributesMagicTemp.add(ChatColor.YELLOW + "+" + paralyzeResist + paralyzeResistStr);
			attributesMagicTemp.add(ChatColor.YELLOW + "+" + blindnessResist + blindnessResistStr);

			attributesExtrasTemp.add(ChatColor.YELLOW + "+" + waterBreathing + waterBreathingStr);
			attributesExtrasTemp.add(ChatColor.YELLOW + "+" + personality + personalityStr);
			attributesExtrasTemp.add(ChatColor.YELLOW + "+" + goldFind + goldFindStr);
			attributesExtrasTemp.add(ChatColor.YELLOW + "+" + magicFind + magicFindStr);
			attributesExtrasTemp.add(ChatColor.YELLOW + "+" + criticalChance + criticalChanceStr);

			//Get random amount of attributes (1 or 2).
			if (tier.equals(ItemTier.T1)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 1, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T2)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 1, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T3)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 1, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T4)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 1, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T5)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 1, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T6)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 1, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			}

			//Lets do some cleanup.
			attributesBaseTemp.clear();
			attributesMagicTemp.clear();
			attributesExtrasTemp.clear();

		} else if (quality.equals(ItemQuality.RARE)) {

			////////////////////////////////////////////////
			//// RARE //////////////////////////////////////
			////////////////////////////////////////////////

			//Randomly pick 2 or 3 attributes,
			//With possibility of magic resistance and
			//attribute extras.

			attributesBaseTemp.add(ChatColor.YELLOW + "+" + strength + strengthStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + agility + agilityStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + stamina + staminaStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + intellect + intellectStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + spirit + spiritStr);

			attributesMagicTemp.add(ChatColor.YELLOW + "+" + fireResist + fireResistStr);
			attributesMagicTemp.add(ChatColor.YELLOW + "+" + iceResist + iceResistStr);
			attributesMagicTemp.add(ChatColor.YELLOW + "+" + lightningResist + lightningResistStr);
			attributesMagicTemp.add(ChatColor.YELLOW + "+" + poisonResist + poisonResistStr);
			attributesMagicTemp.add(ChatColor.YELLOW + "+" + paralyzeResist + paralyzeResistStr);
			attributesMagicTemp.add(ChatColor.YELLOW + "+" + blindnessResist + blindnessResistStr);

			attributesExtrasTemp.add(ChatColor.YELLOW + "+" + waterBreathing + waterBreathingStr);
			attributesExtrasTemp.add(ChatColor.YELLOW + "+" + personality + personalityStr);
			attributesExtrasTemp.add(ChatColor.YELLOW + "+" + goldFind + goldFindStr);
			attributesExtrasTemp.add(ChatColor.YELLOW + "+" + magicFind + magicFindStr);
			attributesExtrasTemp.add(ChatColor.YELLOW + "+" + criticalChance + criticalChanceStr);

			//Get random amount of attributes (1 or 2).
			if (tier.equals(ItemTier.T1)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 1, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T2)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 1, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T3)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 1, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T4)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 1, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T5)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 1, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T6)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 1, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			}

			//Lets do some cleanup.
			attributesBaseTemp.clear();
			attributesMagicTemp.clear();
			attributesExtrasTemp.clear();

		} else if (quality.equals(ItemQuality.LEGENDARY)) {

			///////////////////////////////////////////////////////////////////////////////////////////////////////
			//// LEGENDARY ////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////////

			//Randomly pick 4 or 5 attributes,
			//With possibility of magic resistance and
			//attribute extras.

			attributesBaseTemp.add(ChatColor.YELLOW + "+" + strength + strengthStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + agility + agilityStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + stamina + staminaStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + intellect + intellectStr);
			attributesBaseTemp.add(ChatColor.YELLOW + "+" + spirit + spiritStr);

			attributesMagicTemp.add(ChatColor.YELLOW + "+" + fireResist + fireResistStr);
			attributesMagicTemp.add(ChatColor.YELLOW + "+" + iceResist + iceResistStr);
			attributesMagicTemp.add(ChatColor.YELLOW + "+" + lightningResist + lightningResistStr);
			attributesMagicTemp.add(ChatColor.YELLOW + "+" + poisonResist + poisonResistStr);
			attributesMagicTemp.add(ChatColor.YELLOW + "+" + paralyzeResist + paralyzeResistStr);
			attributesMagicTemp.add(ChatColor.YELLOW + "+" + blindnessResist + blindnessResistStr);

			attributesExtrasTemp.add(ChatColor.YELLOW + "+" + waterBreathing + waterBreathingStr);
			attributesExtrasTemp.add(ChatColor.YELLOW + "+" + personality + personalityStr);
			attributesExtrasTemp.add(ChatColor.YELLOW + "+" + goldFind + goldFindStr);
			attributesExtrasTemp.add(ChatColor.YELLOW + "+" + magicFind + magicFindStr);
			attributesExtrasTemp.add(ChatColor.YELLOW + "+" + criticalChance + criticalChanceStr);

			//Get random amount of attributes (1 or 2).
			if (tier.equals(ItemTier.T1)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 4, 5);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 2, 3);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 2, 3);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T2)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 4, 5);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 2, 3);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 2, 3);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T3)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 4, 5);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 2, 3);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 2, 3);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T4)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 4, 5);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 2, 3);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 2, 3);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T5)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 4, 5);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 2, 3);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 2, 3);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T6)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 4, 5);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 2, 3);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesExtrasTemp, 2, 3);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedExtras.size() - 1; i++) {
					attributesExtrasFinal.add(processedExtras.get(i));
				}

				//Clean up
				processedBase.clear();
			}

			//Lets do some cleanup.
			attributesBaseTemp.clear();
			attributesMagicTemp.clear();
			attributesExtrasTemp.clear();

		}	



		/////////////////////
		/// SET ITEM LORE ///
		/////////////////////
		ItemMeta im = item.getItemMeta();

		String nameFormatting = ChatColor.BOLD + ItemQualityManager.getStringFormatting(quality);
		String itemType = item.getType().toString().replace("_", " ");
		String itemInfo = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + quality + " " + tier + " " + itemType;

		double randSocket = Math.random() * 100;

		//Set the items Name
		im.setDisplayName(nameFormatting + "BetterNameComingSoon v2");

		//Set the item lore
		ArrayList<String> lore = new ArrayList<String>();

		lore.add(itemInfo.toUpperCase());	//Define the quality of item.
		
		//Mark if item is soulbound
		if (soulbound == true) {
			lore.add(ChatColor.GRAY + "Soulbound");
		}

		lore.add(" "); //Spacer																

		//Add base attributes
		for (int i = 0; i < attributesBaseFinal.size(); i++) {
			lore.add(attributesBaseFinal.get(i));
		}

		//Add magic Resistance attributes
		if (!attributesMagicFinal.isEmpty()) {
			for (int i = 0; i < attributesMagicFinal.size(); i++) {
				lore.add(attributesMagicFinal.get(i));
			}
		}

		//Add "extras" attributes
		if (!attributesExtrasFinal.isEmpty()) {
			for (int i = 0; i < attributesExtrasFinal.size(); i++) {
				lore.add(attributesExtrasFinal.get(i));
			}
		}

		//Add random socket
		if (quality != ItemQuality.JUNK) {
			if (randSocket > 80) {
				lore.add(" ");
				lore.add(ChatColor.LIGHT_PURPLE + " ⊡" + ChatColor.GRAY + " Socketable");
			} else if (randSocket > 90) {
				lore.add(ChatColor.LIGHT_PURPLE + " ⊡" + ChatColor.GRAY + " Socketable");
			} else if (randSocket >95) {
				lore.add(ChatColor.LIGHT_PURPLE + " ⊡" + ChatColor.GRAY + " Socketable");
			}
		}

		//Add merchant sell price
		lore.add("");
		lore.add("");
		lore.add(ChatColor.GRAY + "Sell Value" + ChatColor.DARK_GRAY + ": "
				+ ChatColor.WHITE + "0" + ChatColor.GOLD + "g " 
				+ ChatColor.WHITE + "0" + ChatColor.GRAY + "s " 
				+ ChatColor.WHITE + "10" + ChatColor.RED + "c");

		//Set the item lore
		im.setLore(lore);
		//Set the item meta
		item.setItemMeta(im);

		return item;
	}



	/**
	 * Generates random attributes based on the item tier and quality.
	 * 
	 * @param item The item that we are adding attributes to.
	 * @param tier The tier of the item we are adding attributes to.
	 * @param quality The quality of the item we are adding attributes to.
	 * @param soulbound This will make the item trade-able or not.
	 * @return Returns a item with random attributes.
	 */
	public static ItemStack createWeapon(ItemStack item, ItemTier tier, ItemQuality quality, boolean soulbound) {

		/////////////////////////////
		/// GRAB ITEM FROM CONFIG ///
		/////////////////////////////
		weaponConfigFile = new File(randomWeaponFilePath);
		weaponConfig =  YamlConfiguration.loadConfiguration(weaponConfigFile);

		//String name = tier.replaceAll("_", " ");
		//List<String> itemDescription = (List<String>) weaponConfig.getList(tier + ".itemDescription");

		/**
		 * Base Stats:
		 * 	Strength
		 * 	Agility
		 * 	Stamina
		 * 	Intellect
		 * 	Spirit
		 * 	+ Damage (must have every time)
		 * 	Critical Damage
		 * 
		 * Magic Damage:
		 * 	Fire Damage
		 * 	Ice Damage
		 * 	Lightning Damage
		 * 	Poison Damage
		 * 	Paralyze Damage
		 * 	Blindness Damage
		 * 
		 * Extras:
		 *  Knockback
		 *  Lifesteal
		 */

		//Base stats
		
		String configPath = tier + "." + quality;
		
		int strengthMin = weaponConfig.getInt(configPath + ".strengthMin");
		int strengthMax = weaponConfig.getInt(configPath + ".strengthMax");
		int agilityMin = weaponConfig.getInt(configPath + ".agilityMin");
		int agilityMax = weaponConfig.getInt(configPath + ".agilityMax");
		int staminaMin = weaponConfig.getInt(configPath + ".staminaMin");
		int staminaMax = weaponConfig.getInt(configPath + ".staminaMax");
		int intellectMin = weaponConfig.getInt(configPath + ".intellectMin");
		int intellectMax = weaponConfig.getInt(configPath + ".intellectMax");
		int spiritMin = weaponConfig.getInt(configPath + ".spiritMin");
		int spiritMax = weaponConfig.getInt(configPath + ".spiritMax");
		int damageLowMin = weaponConfig.getInt(configPath + ".damageLowMin");
		int damageLowMax = weaponConfig.getInt(configPath + ".damageLowMax");
		int damageHighMin = weaponConfig.getInt(configPath + ".damageHighMin");
		int damageHighMax = weaponConfig.getInt(configPath + ".damageHighMax");

		//Magical Damage
		int fireDamageMin = weaponConfig.getInt(configPath + ".fireDamageMin");
		int fireDamageMax = weaponConfig.getInt(configPath + ".fireDamageMax");
		int iceDamageMin = weaponConfig.getInt(configPath + ".iceDamageMin");
		int iceDamageMax = weaponConfig.getInt(configPath + ".iceDamageMax");
		int lightningDamageMin = weaponConfig.getInt(configPath + ".lightningDamageMin");
		int lightningDamageMax = weaponConfig.getInt(configPath + ".lightningDamageMax");
		int poisonDamageMin = weaponConfig.getInt(configPath + ".poisonDamageMin");
		int poisonDamageMax = weaponConfig.getInt(configPath + ".poisonDamageMax");
		int paralyzeDamageMin = weaponConfig.getInt(configPath + ".paralyzeDamageMin");
		int paralyzeDamageMax = weaponConfig.getInt(configPath + ".paralyzeDamageMax");
		int blindnessDamageMin = weaponConfig.getInt(configPath + ".blindnessDamageMin");
		int blindnessDamageMax = weaponConfig.getInt(configPath + ".blindnessDamageMax");
		int criticalDamageMin = weaponConfig.getInt(configPath + ".criticalDamageMin");
		int criticalDamageMax = weaponConfig.getInt(configPath + ".criticalDamageMax");
		
		//Extras
		int knockbackMin = weaponConfig.getInt(configPath + ".knockbackMin");
		int knockbackMax = weaponConfig.getInt(configPath + ".knockbackMax");
		int lifestealMin = weaponConfig.getInt(configPath + ".lifestealMin");
		int lifestealMax = weaponConfig.getInt(configPath + ".lifestealMax");


		////////////////////////////
		/// Calculate Item Stats ///
		////////////////////////////

		//Base stats
		int strength = randomInt(strengthMin, strengthMax);
		int agility = randomInt(agilityMin, agilityMax);
		int stamina = randomInt(staminaMin, staminaMax);
		int intellect = randomInt(intellectMin, intellectMax);
		int spirit = randomInt(spiritMin, spiritMax);
		int damageMin = randomInt(damageLowMin, damageLowMax);
		int damageMax = randomInt(damageHighMin, damageHighMax);

		//Damage
		int fireDamage = randomInt(fireDamageMin, fireDamageMax);
		int iceDamage = randomInt(iceDamageMin, iceDamageMax);
		int lightningDamage = randomInt(lightningDamageMin, lightningDamageMax);
		int poisonDamage = randomInt(poisonDamageMin, poisonDamageMax);
		int paralyzeDamage = randomInt(paralyzeDamageMin, paralyzeDamageMax);
		int blindnessDamage = randomInt(blindnessDamageMin, blindnessDamageMax);
		int criticalDamage = randomInt(criticalDamageMin, criticalDamageMax);

		//Extras
		int knockback = randomInt(knockbackMin, knockbackMax);
		int lifesteal = randomInt(lifestealMin, lifestealMax);

		///////////////////////////////////
		/// PICK RANDOM ITEM ATTRIBUTES ///
		///////////////////////////////////

		/**
		 * ItemTiers: T1, T2, T3, T4, T5, T6
		 * 
		 * ItemQuality: Junk, Common, Uncommon, Rare, Epic, Legendary
		 */

		//Final arrays (processedBase).
		ArrayList<String> attributesBaseFinal = new ArrayList<String>();
		ArrayList<String> attributesMagicFinal = new ArrayList<String>();
		ArrayList<String> attributesExtrasFinal = new ArrayList<String>();

		//Temporary arrays
		ArrayList<String> attributesBaseTemp = new ArrayList<String>();
		ArrayList<String> attributesMagicTemp = new ArrayList<String>();
		ArrayList<String> attributesExtrasTemp = new ArrayList<String>();

		//Always add "damage" attribute to an weapon item.
		//Lets do this beforehand, it does not need to be picked, its automatic.
		attributesBaseFinal.add(ChatColor.RED + "" + damageMin + ChatColor.GRAY + " - " + ChatColor.RED + damageMax + damageStr);

		//Lets start the break down and see what attributes can be on what tier and quality item.

		if (quality.equals(ItemQuality.JUNK)) {

			////////////////////////////////////////////////
			//// JUNK //////////////////////////////////////
			////////////////////////////////////////////////

			attributesBaseTemp.add(ChatColor.RED + "+" + strength + strengthStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + agility + agilityStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + stamina + staminaStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + intellect + intellectStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + spirit + spiritStr);


			//Get random amount of attributes (1 or 2).
			if (tier.equals(ItemTier.T1)) {
				//ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 1);
			} else if (tier.equals(ItemTier.T2)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T3)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T4)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T5)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T6)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
				//Clean up
				processedBase.clear();
			}

			//Lets do some cleanup.
			attributesBaseTemp.clear();
			attributesMagicTemp.clear();

		} else if (quality.equals(ItemQuality.COMMON)) {

			////////////////////////////////////////////////
			//// COMMON ////////////////////////////////////
			////////////////////////////////////////////////

			//Randomly pick 1 or 2 attributes.

			attributesBaseTemp.add(ChatColor.RED + "+" + strength + strengthStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + agility + agilityStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + stamina + staminaStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + intellect + intellectStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + spirit + spiritStr);

			//Get random amount of attributes (1 or 2).
			if (tier.equals(ItemTier.T1)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
			} else if (tier.equals(ItemTier.T2)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T3)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T4)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T5)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T6)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 0, 2);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}
				//Clean up
				processedBase.clear();
			}

			//Lets do some cleanup.
			attributesBaseTemp.clear();
			attributesMagicTemp.clear();

		} else if (quality.equals(ItemQuality.UNCOMMON)) {

			////////////////////////////////////////////////
			//// UNCOMMON //////////////////////////////////
			////////////////////////////////////////////////


			//Randomly pick 1, 2, or 3 attributes,
			//With possibility of magic resistance and
			//attribute extras.

			attributesBaseTemp.add(ChatColor.RED + "+" + strength + strengthStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + agility + agilityStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + stamina + staminaStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + intellect + intellectStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + spirit + spiritStr);

			attributesMagicTemp.add(ChatColor.RED + "+" + fireDamage + fireDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + iceDamage + iceDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + lightningDamage + lightningDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + poisonDamage + poisonDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + paralyzeDamage + paralyzeDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + blindnessDamage + blindnessDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + criticalDamage + criticalDamageStr);

			attributesExtrasTemp.add(ChatColor.RED + "+" + knockback + knockbackStr);
			attributesExtrasTemp.add(ChatColor.RED + "+" + lifesteal + lifestealStr);

			//Get random amount of attributes (1 or 2).
			if (tier.equals(ItemTier.T1)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 1, 2);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 0, 1);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T2)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 1, 2);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 0, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T3)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 1, 2);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 0, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T4)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 1, 2);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 0, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T5)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 1, 2);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 0, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T6)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 1, 2);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 0, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			}

			//Lets do some cleanup.
			attributesBaseTemp.clear();
			attributesMagicTemp.clear();

		} else if (quality.equals(ItemQuality.EPIC)) {

			////////////////////////////////////////////////
			//// EPIC //////////////////////////////////////
			////////////////////////////////////////////////

			//Randomly pick 2 or 3 attributes,
			//With possibility of magic resistance and
			//attribute extras.

			attributesBaseTemp.add(ChatColor.RED + "+" + strength + strengthStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + agility + agilityStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + stamina + staminaStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + intellect + intellectStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + spirit + spiritStr);

			attributesMagicTemp.add(ChatColor.RED + "+" + fireDamage +fireDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + iceDamage + iceDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + lightningDamage + lightningDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + poisonDamage + poisonDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + paralyzeDamage + paralyzeDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + blindnessDamage + blindnessDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + criticalDamage + criticalDamageStr);

			attributesExtrasTemp.add(ChatColor.RED + "+" + knockback + knockbackStr);
			attributesExtrasTemp.add(ChatColor.RED + "+" + lifesteal + lifestealStr);

			//Get random amount of attributes (1 or 2).
			if (tier.equals(ItemTier.T1)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T2)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T3)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T4)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T5)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T6)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			}

			//Lets do some cleanup.
			attributesBaseTemp.clear();
			attributesMagicTemp.clear();

		} else if (quality.equals(ItemQuality.RARE)) {

			////////////////////////////////////////////////
			//// RARE //////////////////////////////////////
			////////////////////////////////////////////////

			//Randomly pick 2 or 3 attributes,
			//With possibility of magic resistance and
			//attribute extras.

			attributesBaseTemp.add(ChatColor.RED + "+" + strength + strengthStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + agility + agilityStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + stamina + staminaStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + intellect + intellectStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + spirit + spiritStr);

			attributesMagicTemp.add(ChatColor.RED + "+" + fireDamage +fireDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + iceDamage + iceDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + lightningDamage + lightningDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + poisonDamage + poisonDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + paralyzeDamage + paralyzeDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + blindnessDamage + blindnessDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + criticalDamage + criticalDamageStr);

			attributesExtrasTemp.add(ChatColor.RED + "+" + knockback + knockbackStr);
			attributesExtrasTemp.add(ChatColor.RED + "+" + lifesteal + lifestealStr);

			//Get random amount of attributes (1 or 2).
			if (tier.equals(ItemTier.T1)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T2)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T3)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T4)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T5)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T6)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			}

			//Lets do some cleanup.
			attributesBaseTemp.clear();
			attributesMagicTemp.clear();

		} else if (quality.equals(ItemQuality.LEGENDARY)) {

			///////////////////////////////////////////////////////////////////////////////////////////////////////
			//// LEGENDARY ////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////////

			//Randomly pick 4 or 5 attributes,
			//With possibility of magic resistance and
			//attribute extras.

			attributesBaseTemp.add(ChatColor.RED + "+" + strength + strengthStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + agility + agilityStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + stamina + staminaStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + intellect + intellectStr);
			attributesBaseTemp.add(ChatColor.RED + "+" + spirit + spiritStr);

			attributesMagicTemp.add(ChatColor.RED + "+" + fireDamage +fireDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + iceDamage + iceDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + lightningDamage + lightningDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + poisonDamage + poisonDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + paralyzeDamage + paralyzeDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + blindnessDamage + blindnessDamageStr);
			attributesMagicTemp.add(ChatColor.RED + "+" + criticalDamage + criticalDamageStr);

			attributesExtrasTemp.add(ChatColor.RED + "+" + knockback + knockbackStr);
			attributesExtrasTemp.add(ChatColor.RED + "+" + lifesteal + lifestealStr);

			//Get random amount of attributes (1 or 2).
			if (tier.equals(ItemTier.T1)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 4, 5);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 2, 3);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T2)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 4, 5);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 2, 3);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T3)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 4, 5);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 2, 3);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T4)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 4, 5);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 2, 3);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T5)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 4, 5);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 2, 3);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			} else if (tier.equals(ItemTier.T6)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 4, 5);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 2, 3);
				ArrayList<String> processedExtras = getRandomArrayIndex(attributesMagicTemp, 0, 1);

				//Add the processedBase attributes to the Final Attributes list.
				for (int i = 0; i <= processedBase.size() - 1; i++) {
					attributesBaseFinal.add(processedBase.get(i));
				}

				//Add the processedMagic attributes to the Final Attributes list.
				for (int i = 0; i <= processedMagic.size() - 1; i++) {
					attributesMagicFinal.add(processedMagic.get(i));
				}

				//Clean up
				processedBase.clear();
			}

			//Lets do some cleanup.
			attributesBaseTemp.clear();
			attributesMagicTemp.clear();
		}	



		/////////////////////
		/// SET ITEM LORE ///
		/////////////////////
		ItemMeta im = item.getItemMeta();

		String nameFormatting = ChatColor.BOLD + ItemQualityManager.getStringFormatting(quality);
		String itemType = item.getType().toString().replace("_", " ");
		String itemInfo = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + quality + " " + tier + " " + itemType;

		double randSocket = Math.random() * 100;

		//Set the items Name
		im.setDisplayName(nameFormatting + "BetterNameComingSoon v2");

		//Set the item lore
		ArrayList<String> lore = new ArrayList<String>();

		lore.add(itemInfo.toUpperCase());	//Define the quality of item.
		
		//Mark if item is soulbound
		if (soulbound == true) {
			lore.add(ChatColor.GRAY + "Soulbound");
		}

		lore.add(" "); //Spacer																

		//Add base attributes
		for (int i = 0; i < attributesBaseFinal.size(); i++) {
			lore.add(attributesBaseFinal.get(i));
		}

		//Add magic Damage attributes
		if (!attributesMagicFinal.isEmpty()) {
			for (int i = 0; i < attributesMagicFinal.size(); i++) {
				lore.add(attributesMagicFinal.get(i));
			}
		}

		//Add magic Damage attributes
		if (!attributesExtrasFinal.isEmpty()) {
			for (int i = 0; i < attributesExtrasFinal.size(); i++) {
				lore.add(attributesExtrasFinal.get(i));
			}
		}

		//Add random socket
		if (quality != ItemQuality.JUNK) {
			if (randSocket > 80) {
				lore.add(" ");
				lore.add(ChatColor.LIGHT_PURPLE + " ⊡" + ChatColor.GRAY + " Socketable");
			} else if (randSocket > 90) {
				lore.add(ChatColor.LIGHT_PURPLE + " ⊡" + ChatColor.GRAY + " Socketable");
			} else if (randSocket >95) {
				lore.add(ChatColor.LIGHT_PURPLE + " ⊡" + ChatColor.GRAY + " Socketable");
			}
		}

		//Add merchant sell price
		lore.add("");
		lore.add("");
		lore.add(ChatColor.GRAY + "Sell Value" + ChatColor.DARK_GRAY + ": " 
				+ ChatColor.WHITE + "0" + ChatColor.GOLD + "g " 
				+ ChatColor.WHITE + "0" + ChatColor.GRAY + "s " 
				+ ChatColor.WHITE + "10" + ChatColor.RED + "c");

		//Set the item lore
		im.setLore(lore);
		//Set the item meta
		item.setItemMeta(im);

		return item;
	}


	/**
	 * Creates a new configuration file for random weapon and weapons.
	 */
	private static void createConfig() {

		//Create the configuration file for random weapon and weapons. 
		File armorConfigFile = new File(randomWeaponFilePath);
		File weaponConfigFile = new File(randomWeaponFilePath);

		FileConfiguration armorConfig =  YamlConfiguration.loadConfiguration(armorConfigFile);
		FileConfiguration weaponConfig =  YamlConfiguration.loadConfiguration(weaponConfigFile);

		////////////////////
		/// RANDOM ARMOR ///
		////////////////////
		armorConfig.set("T1", "T1");
		armorConfig.set("T1.JUNK.strengthMin", 1);
		armorConfig.set("T1.JUNK.strengthMax", 5);
		armorConfig.set("T1.JUNK.agilityMin", 1);
		armorConfig.set("T1.JUNK.agilityMax", 5);
		armorConfig.set("T1.JUNK.staminaMin", 1);
		armorConfig.set("T1.JUNK.staminaMax", 5);
		armorConfig.set("T1.JUNK.intellectMin", 1);
		armorConfig.set("T1.JUNK.intellectMax", 5);
		armorConfig.set("T1.JUNK.spiritMin", 1);
		armorConfig.set("T1.JUNK.spiritMax", 5);
		armorConfig.set("T1.JUNK.armorMin", 1);
		armorConfig.set("T1.JUNK.armorMax", 5);
		armorConfig.set("T1.JUNK.fireResistMin", 1);
		armorConfig.set("T1.JUNK.fireResistMax", 5);
		armorConfig.set("T1.JUNK.iceResistMin", 1);
		armorConfig.set("T1.JUNK.iceResistMax", 5);
		armorConfig.set("T1.JUNK.lightningResistMin", 1);
		armorConfig.set("T1.JUNK.lightningResistMax", 5);
		armorConfig.set("T1.JUNK.poisonResistMin", 1);
		armorConfig.set("T1.JUNK.poisonResistMax", 5);
		armorConfig.set("T1.JUNK.paralyzeResistMin", 1);
		armorConfig.set("T1.JUNK.paralyzeResistMax", 5);
		armorConfig.set("T1.JUNK.blindnessResistMin", 1);
		armorConfig.set("T1.JUNK.blindnessResistMax", 5);
		armorConfig.set("T1.JUNK.waterBreathingMin", 1);
		armorConfig.set("T1.JUNK.waterBreathingMax", 5);
		armorConfig.set("T1.JUNK.personalityMin", 1);
		armorConfig.set("T1.JUNK.personalityMax", 5);
		armorConfig.set("T1.JUNK.goldFindMin", 1);
		armorConfig.set("T1.JUNK.goldFindMax", 5);
		armorConfig.set("T1.JUNK.magicFindMin", 1);
		armorConfig.set("T1.JUNK.magicFindMax", 5);
		armorConfig.set("T1.JUNK.criticalChanceMin", 1);
		armorConfig.set("T1.JUNK.criticalChanceMax", 5);


		//////////////////////
		/// RANDOM WEAPONS ///
		//////////////////////
		weaponConfig.set("T1", "T1");
		weaponConfig.set("T1.JUNK.damageLowMin", 1);
		weaponConfig.set("T1.JUNK.damageLowMax", 5);
		weaponConfig.set("T1.JUNK.damageHighMin", 1);
		weaponConfig.set("T1.JUNK.damageHighMax", 5);
		weaponConfig.set("T1.JUNK.strengthMin", 1);
		weaponConfig.set("T1.JUNK.strengthMax", 5);
		weaponConfig.set("T1.JUNK.agilityMin", 1);
		weaponConfig.set("T1.JUNK.agilityMax", 5);
		weaponConfig.set("T1.JUNK.staminaMin", 1);
		weaponConfig.set("T1.JUNK.staminaMax", 5);
		weaponConfig.set("T1.JUNK.intellectMin", 1);
		weaponConfig.set("T1.JUNK.intellectMax", 5);
		weaponConfig.set("T1.JUNK.spiritMin", 1);
		weaponConfig.set("T1.JUNK.spiritMax", 5);
		weaponConfig.set("T1.JUNK.fireDamageMin", 1);
		weaponConfig.set("T1.JUNK.fireDamageMax", 5);
		weaponConfig.set("T1.JUNK.iceDamageMin", 1);
		weaponConfig.set("T1.JUNK.iceDamageMax", 5);
		weaponConfig.set("T1.JUNK.lightningDamageMin", 1);
		weaponConfig.set("T1.JUNK.lightningDamageMax", 5);
		weaponConfig.set("T1.JUNK.poisonDamageMin", 1);
		weaponConfig.set("T1.JUNK.poisonDamageMax", 5);
		weaponConfig.set("T1.JUNK.paralyzeDamageMin", 1);
		weaponConfig.set("T1.JUNK.paralyzeDamageMax", 5);
		weaponConfig.set("T1.JUNK.blindnessDamageMin", 1);
		weaponConfig.set("T1.JUNK.blindnessDamageMax", 5);
		weaponConfig.set("T1.JUNK.criticalDamageMin", 1);
		weaponConfig.set("T1.JUNK.criticalDamageMax", 5);
		weaponConfig.set("T1.JUNK.knockbackMin", 1);
		weaponConfig.set("T1.JUNK.knockbackMax", 5);
		weaponConfig.set("T1.JUNK.lifestealMin", 1);
		weaponConfig.set("T1.JUNK.lifestealMax", 5);

		try {
			armorConfig.save(armorConfigFile);
			weaponConfig.save(weaponConfigFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
