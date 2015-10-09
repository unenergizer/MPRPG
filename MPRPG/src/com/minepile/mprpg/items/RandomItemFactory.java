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
	 * @return Returns a item with random attributes.
	 */
	public static ItemStack createArmor(ItemStack item, ItemTier tier, ItemQuality quality) {

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
		 * 
		 * Extras:
		 * 	Waterbreathing
		 * 	Personality
		 *  Gold Find
		 *  Magic Find
		 */

		//Base stats
		int strengthMin = armorConfig.getInt(tier + "." + quality + ".strengthMin");
		int strengthMax = armorConfig.getInt(tier + "." + quality + ".strengthMax");
		int agilityMin = armorConfig.getInt(tier + "." + quality + ".agilityMin");
		int agilityMax = armorConfig.getInt(tier + "." + quality + ".agilityMax");
		int staminaMin = armorConfig.getInt(tier + "." + quality + ".staminaMin");
		int staminaMax = armorConfig.getInt(tier + "." + quality + ".staminaMax");
		int intellectMin = armorConfig.getInt(tier + "." + quality + ".intellectMin");
		int intellectMax = armorConfig.getInt(tier + "." + quality + ".intellectMax");
		int spiritMin = armorConfig.getInt(tier + "." + quality + ".spiritMin");
		int spiritMax = armorConfig.getInt(tier + "." + quality + ".spiritMax");
		int armorMin = armorConfig.getInt(tier + "." + quality + ".armorMin");		//EVERY ARMOR ITEM MUST HAVE ARMOR STAT!!!
		int armorMax = armorConfig.getInt(tier + "." + quality + ".armorMax");		//EVERY ARMOR ITEM MUST HAVE ARMOR STAT!!!

		//Resistances
		int fireResistMin = armorConfig.getInt(tier + "." + quality + ".fireResistMin");
		int fireResistMax = armorConfig.getInt(tier + "." + quality + ".fireResistMax");
		int iceResistMin = armorConfig.getInt(tier + "." + quality + ".iceResistMin");
		int iceResistMax = armorConfig.getInt(tier + "." + quality + ".iceResistMax");
		int lightningResistMin = armorConfig.getInt(tier + "." + quality + ".lightningResistMin");
		int lightningResistMax = armorConfig.getInt(tier + "." + quality + ".lightningResistMax");
		int poisonResistMin = armorConfig.getInt(tier + "." + quality + ".poisonResistMin");
		int poisonResistMax = armorConfig.getInt(tier + "." + quality + ".poisonResistMax");
		int paralyzeResistMin = armorConfig.getInt(tier + "." + quality + ".paralyzeResistMin");
		int paralyzeResistMax = armorConfig.getInt(tier + "." + quality + ".paralyzeResistMax");
		int blindnessResistMin = armorConfig.getInt(tier + "." + quality + ".blindnessResistMin");
		int blindnessResistMax = armorConfig.getInt(tier + "." + quality + ".blindnessResistMax");

		//Extras
		int waterBreathingMin = armorConfig.getInt(tier + "." + quality + ".waterBreathingMin");
		int waterBreathingMax = armorConfig.getInt(tier + "." + quality + ".waterBreathingMax");
		int personalityMin = armorConfig.getInt(tier + "." + quality + ".personalityMin");
		int personalityMax = armorConfig.getInt(tier + "." + quality + ".personalityMax");
		int goldFindMin = armorConfig.getInt(tier + "." + quality + ".goldFindMin");
		int goldFindMax = armorConfig.getInt(tier + "." + quality + ".goldFindMax");
		int magicFindMin = armorConfig.getInt(tier + "." + quality + ".magicFindMin");
		int magicFindMax = armorConfig.getInt(tier + "." + quality + ".magicFindMax");


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
		attributesBaseFinal.add(ChatColor.WHITE + Integer.toString(armor) + " Armor");

		//Lets start the break down and see what attributes can be on what tier and quality item.

		if (quality.equals(ItemQuality.JUNK)) {

			////////////////////////////////////////////////
			//// JUNK //////////////////////////////////////
			////////////////////////////////////////////////

			attributesBaseTemp.add(ChatColor.WHITE + " +" + strength + " Strength");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + agility + " Agility");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + stamina + " Stamina");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + intellect + " Intellect");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + spirit + " Spirit");


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

			attributesBaseTemp.add(ChatColor.WHITE + " +" + strength + " Strength");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + agility + " Agility");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + stamina + " Stamina");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + intellect + " Intellect");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + spirit + " Spirit");

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

			attributesBaseTemp.add(ChatColor.WHITE + " +" + strength + " Strength");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + agility + " Agility");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + stamina + " Stamina");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + intellect + " Intellect");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + spirit + " Spirit");

			attributesMagicTemp.add(ChatColor.WHITE + " +" + fireResist + " Fire Resistance");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + iceResist + " Ice Resistance");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + lightningResist + " Lightning Resistance");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + poisonResist + " Poison Resistance");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + paralyzeResist + " Fire Resistance");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + blindnessResist + " Blindness Resistance");

			attributesExtrasTemp.add(ChatColor.WHITE + " +" + waterBreathing + " Water Breathing");
			attributesExtrasTemp.add(ChatColor.WHITE + " +" + personality + " Personality");
			attributesExtrasTemp.add(ChatColor.WHITE + " +" + goldFind + " Gold Find");
			attributesExtrasTemp.add(ChatColor.WHITE + " +" + magicFind + " Magic FInd");

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

			attributesBaseTemp.add(ChatColor.WHITE + " +" + strength + " Strength");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + agility + " Agility");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + stamina + " Stamina");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + intellect + " Intellect");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + spirit + " Spirit");

			attributesMagicTemp.add(ChatColor.WHITE + " +" + fireResist + " Fire Resistance");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + iceResist + " Ice Resistance");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + lightningResist + " Lightning Resistance");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + poisonResist + " Poison Resistance");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + paralyzeResist + " Fire Resistance");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + blindnessResist + " Blindness Resistance");

			attributesExtrasTemp.add(ChatColor.WHITE + " +" + waterBreathing + " Water Breathing");
			attributesExtrasTemp.add(ChatColor.WHITE + " +" + personality + " Personality");
			attributesExtrasTemp.add(ChatColor.WHITE + " +" + goldFind + " Gold Find");
			attributesExtrasTemp.add(ChatColor.WHITE + " +" + magicFind + " Magic FInd");

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

			attributesBaseTemp.add(ChatColor.WHITE + " +" + strength + " Strength");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + agility + " Agility");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + stamina + " Stamina");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + intellect + " Intellect");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + spirit + " Spirit");

			attributesMagicTemp.add(ChatColor.WHITE + " +" + fireResist + " Fire Resistance");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + iceResist + " Ice Resistance");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + lightningResist + " Lightning Resistance");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + poisonResist + " Poison Resistance");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + paralyzeResist + " Fire Resistance");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + blindnessResist + " Blindness Resistance");

			attributesExtrasTemp.add(ChatColor.WHITE + " +" + waterBreathing + " Water Breathing");
			attributesExtrasTemp.add(ChatColor.WHITE + " +" + personality + " Personality");
			attributesExtrasTemp.add(ChatColor.WHITE + " +" + goldFind + " Gold Find");
			attributesExtrasTemp.add(ChatColor.WHITE + " +" + magicFind + " Magic FInd");

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

			attributesBaseTemp.add(ChatColor.WHITE + " +" + strength + " Strength");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + agility + " Agility");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + stamina + " Stamina");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + intellect + " Intellect");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + spirit + " Spirit");

			attributesMagicTemp.add(ChatColor.WHITE + " +" + fireResist + " Fire Resistance");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + iceResist + " Ice Resistance");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + lightningResist + " Lightning Resistance");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + poisonResist + " Poison Resistance");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + paralyzeResist + " Fire Resistance");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + blindnessResist + " Blindness Resistance");

			attributesExtrasTemp.add(ChatColor.WHITE + " +" + waterBreathing + " Water Breathing");
			attributesExtrasTemp.add(ChatColor.WHITE + " +" + personality + " Personality");
			attributesExtrasTemp.add(ChatColor.WHITE + " +" + goldFind + " Gold Find");
			attributesExtrasTemp.add(ChatColor.WHITE + " +" + magicFind + " Magic FInd");

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
				lore.add(ChatColor.LIGHT_PURPLE + " ⊡" + ChatColor.RESET + " Socketable");
			} else if (randSocket > 90) {
				lore.add(ChatColor.LIGHT_PURPLE + " ⊡" + ChatColor.RESET + " Socketable");
			} else if (randSocket >95) {
				lore.add(ChatColor.LIGHT_PURPLE + " ⊡" + ChatColor.RESET + " Socketable");
			}
		}

		//Add merchant sell price
		lore.add("");
		lore.add("Merchant Sell Price" + ChatColor.DARK_GRAY + ":"); 
		lore.add(ChatColor.RESET + "0" + ChatColor.GOLD + "g " 
				+ ChatColor.RESET + "0" + ChatColor.GRAY + "s " 
				+ ChatColor.RESET + "10" + ChatColor.RED + "c");

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
	 * @return Returns a item with random attributes.
	 */
	public static ItemStack createWeapon(ItemStack item, ItemTier tier, ItemQuality quality) {

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
		 * 
		 * Magic Damage:
		 * 	Fire Damage
		 * 	Ice Damage
		 * 	Lightning Damage
		 * 	Poison Damage
		 * 	Paralyze Damage
		 */

		//Base stats
		int strengthMin = weaponConfig.getInt(tier + "." + quality + ".strengthMin");
		int strengthMax = weaponConfig.getInt(tier + "." + quality + ".strengthMax");
		int agilityMin = weaponConfig.getInt(tier + "." + quality + ".agilityMin");
		int agilityMax = weaponConfig.getInt(tier + "." + quality + ".agilityMax");
		int staminaMin = weaponConfig.getInt(tier + "." + quality + ".staminaMin");
		int staminaMax = weaponConfig.getInt(tier + "." + quality + ".staminaMax");
		int intellectMin = weaponConfig.getInt(tier + "." + quality + ".intellectMin");
		int intellectMax = weaponConfig.getInt(tier + "." + quality + ".intellectMax");
		int spiritMin = weaponConfig.getInt(tier + "." + quality + ".spiritMin");
		int spiritMax = weaponConfig.getInt(tier + "." + quality + ".spiritMax");
		int damageLowMin = weaponConfig.getInt(tier + "." + quality + ".damageLowMin");
		int damageLowMax = weaponConfig.getInt(tier + "." + quality + ".damageLowMax");
		int damageHighMin = weaponConfig.getInt(tier + "." + quality + ".damageHighMin");
		int damageHighMax = weaponConfig.getInt(tier + "." + quality + ".damageHighMax");

		//Magical Damage
		int fireDamageMin = weaponConfig.getInt(tier + "." + quality + ".fireDamageMin");
		int fireDamageMax = weaponConfig.getInt(tier + "." + quality + ".fireDamageMax");
		int iceDamageMin = weaponConfig.getInt(tier + "." + quality + ".iceDamageMin");
		int iceDamageMax = weaponConfig.getInt(tier + "." + quality + ".iceDamageMax");
		int lightningDamageMin = weaponConfig.getInt(tier + "." + quality + ".lightningDamageMin");
		int lightningDamageMax = weaponConfig.getInt(tier + "." + quality + ".lightningDamageMax");
		int poisonDamageMin = weaponConfig.getInt(tier + "." + quality + ".poisonDamageMin");
		int poisonDamageMax = weaponConfig.getInt(tier + "." + quality + ".poisonDamageMax");
		int paralyzeDamageMin = weaponConfig.getInt(tier + "." + quality + ".paralyzeDamageMin");
		int paralyzeDamageMax = weaponConfig.getInt(tier + "." + quality + ".paralyzeDamageMax");
		int blindnessDamageMin = weaponConfig.getInt(tier + "." + quality + ".blindnessDamageMin");
		int blindnessDamageMax = weaponConfig.getInt(tier + "." + quality + ".blindnessDamageMax");


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

		//Temporary arrays
		ArrayList<String> attributesBaseTemp = new ArrayList<String>();
		ArrayList<String> attributesMagicTemp = new ArrayList<String>();

		//Always add "damage" attribute to an weapon item.
		//Lets do this beforehand, it does not need to be picked, its automatic.
		attributesBaseFinal.add(ChatColor.WHITE + "" + damageMin + "-" + damageMax + " Weapon");

		//Lets start the break down and see what attributes can be on what tier and quality item.

		if (quality.equals(ItemQuality.JUNK)) {

			////////////////////////////////////////////////
			//// JUNK //////////////////////////////////////
			////////////////////////////////////////////////

			attributesBaseTemp.add(ChatColor.WHITE + " +" + strength + " Strength");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + agility + " Agility");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + stamina + " Stamina");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + intellect + " Intellect");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + spirit + " Spirit");


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

			attributesBaseTemp.add(ChatColor.WHITE + " +" + strength + " Strength");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + agility + " Agility");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + stamina + " Stamina");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + intellect + " Intellect");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + spirit + " Spirit");

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

			attributesBaseTemp.add(ChatColor.WHITE + " +" + strength + " Strength");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + agility + " Agility");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + stamina + " Stamina");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + intellect + " Intellect");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + spirit + " Spirit");

			attributesMagicTemp.add(ChatColor.WHITE + " +" + fireDamage + " Fire Damage");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + iceDamage + " Ice Damage");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + lightningDamage + " Lightning Damage");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + poisonDamage + " Poison Damage");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + paralyzeDamage + " Fire Damage");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + blindnessDamage + " Blindness Damage");

			//Get random amount of attributes (1 or 2).
			if (tier.equals(ItemTier.T1)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 1, 2);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 0, 1);

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

			attributesBaseTemp.add(ChatColor.WHITE + " +" + strength + " Strength");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + agility + " Agility");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + stamina + " Stamina");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + intellect + " Intellect");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + spirit + " Spirit");

			attributesMagicTemp.add(ChatColor.WHITE + " +" + fireDamage + " Fire Damage");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + iceDamage + " Ice Damage");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + lightningDamage + " Lightning Damage");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + poisonDamage + " Poison Damage");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + paralyzeDamage + " Fire Damage");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + blindnessDamage + " Blindness Damage");

			//Get random amount of attributes (1 or 2).
			if (tier.equals(ItemTier.T1)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);

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

			attributesBaseTemp.add(ChatColor.WHITE + " +" + strength + " Strength");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + agility + " Agility");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + stamina + " Stamina");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + intellect + " Intellect");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + spirit + " Spirit");

			attributesMagicTemp.add(ChatColor.WHITE + " +" + fireDamage + " Fire Damage");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + iceDamage + " Ice Damage");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + lightningDamage + " Lightning Damage");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + poisonDamage + " Poison Damage");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + paralyzeDamage + " Fire Damage");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + blindnessDamage + " Blindness Damage");

			//Get random amount of attributes (1 or 2).
			if (tier.equals(ItemTier.T1)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 2, 3);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 1, 2);

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

			attributesBaseTemp.add(ChatColor.WHITE + " +" + strength + " Strength");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + agility + " Agility");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + stamina + " Stamina");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + intellect + " Intellect");
			attributesBaseTemp.add(ChatColor.WHITE + " +" + spirit + " Spirit");

			attributesMagicTemp.add(ChatColor.WHITE + " +" + fireDamage + " Fire Damage");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + iceDamage + " Ice Damage");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + lightningDamage + " Lightning Damage");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + poisonDamage + " Poison Damage");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + paralyzeDamage + " Fire Damage");
			attributesMagicTemp.add(ChatColor.WHITE + " +" + blindnessDamage + " Blindness Damage");

			//Get random amount of attributes (1 or 2).
			if (tier.equals(ItemTier.T1)) {
				ArrayList<String> processedBase = getRandomArrayIndex(attributesBaseTemp, 4, 5);
				ArrayList<String> processedMagic = getRandomArrayIndex(attributesMagicTemp, 2, 3);

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

		//Add random socket
		if (quality != ItemQuality.JUNK) {
			if (randSocket > 80) {
				lore.add(" ");
				lore.add(ChatColor.LIGHT_PURPLE + " ⊡" + ChatColor.RESET + " Socketable");
			} else if (randSocket > 90) {
				lore.add(ChatColor.LIGHT_PURPLE + " ⊡" + ChatColor.RESET + " Socketable");
			} else if (randSocket >95) {
				lore.add(ChatColor.LIGHT_PURPLE + " ⊡" + ChatColor.RESET + " Socketable");
			}
		}

		//Add merchant sell price
		lore.add("");
		lore.add("Merchant Sell Price" + ChatColor.DARK_GRAY + ":"); 
		lore.add(ChatColor.RESET + "0" + ChatColor.GOLD + "g " 
				+ ChatColor.RESET + "0" + ChatColor.GRAY + "s " 
				+ ChatColor.RESET + "10" + ChatColor.RED + "c");

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
		armorConfig.set("T1.JUNK.waterBreathingMin", 1);
		armorConfig.set("T1.JUNK.waterBreathingMax", 5);
		armorConfig.set("T1.JUNK.personalityMin", 1);
		armorConfig.set("T1.JUNK.personalityMax", 5);
		armorConfig.set("T1.JUNK.goldFindMin", 1);
		armorConfig.set("T1.JUNK.goldFindMax", 5);
		armorConfig.set("T1.JUNK.magicFindMin", 1);
		armorConfig.set("T1.JUNK.magicFindMax", 5);


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

		try {
			armorConfig.save(armorConfigFile);
			weaponConfig.save(weaponConfigFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
