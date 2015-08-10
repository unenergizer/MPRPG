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
	
	//Configuration file that holds armor information.
	private static File armorConfigFile;
	private static File weaponConfigFile;

	private static FileConfiguration armorConfig;
	private static FileConfiguration weaponConfig;
	
	//Create instance
	public static RandomItemFactory getInstance() {
		return randomItemFactoryInstance;
	}

	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		//If mining configuration does not exist, create it. Otherwise lets load the config.

		if(!(new File(randomArmorFilePath)).exists()){
			createConfig();
        }
		if(!(new File(randomWeaponFilePath)).exists()){
			createConfig();
        }
	}

	public static ItemStack createArmor(ItemStack item, ItemTier tier, ItemQuality quality) {

		/////////////////////////////
		/// GRAB ITEM FROM CONFIG ///
		/////////////////////////////
		armorConfigFile = new File(randomArmorFilePath);
		armorConfig =  YamlConfiguration.loadConfiguration(armorConfigFile);

		//String name = tier.replaceAll("_", " ");
		//List<String> itemDescription = (List<String>) armorConfig.getList(tier + ".itemDescription");
		
		int armorMax = armorConfig.getInt(tier + "." + quality + ".armorMax");
		int armorMin = armorConfig.getInt(tier + "." + quality + ".armorMin");
		int blockMax = armorConfig.getInt(tier + "." + quality + ".blockMax");	
		int blockMin = armorConfig.getInt(tier + "." + quality + ".blockMin");	
		int coldDamageMax = armorConfig.getInt(tier + "." + quality + ".coldDamageMax");	
		int coldDamageMin = armorConfig.getInt(tier + "." + quality + ".coldDamageMin");	
		int coldResistMax = armorConfig.getInt(tier + "." + quality + ".coldResistMax");	
		int coldResistMin = armorConfig.getInt(tier + "." + quality + ".coldResistMin");	
		int critChanceMax = armorConfig.getInt(tier + "." + quality + ".critChanceMax");	
		int critChanceMin = armorConfig.getInt(tier + "." + quality + ".critChanceMin");	
		int damageMax = armorConfig.getInt(tier + "." + quality + ".damageMax");	
		int damageMin = armorConfig.getInt(tier + "." + quality + ".damageMin");	
		int dodgeMax = armorConfig.getInt(tier + "." + quality + ".dodgeMax");	
		int dodgeMin = armorConfig.getInt(tier + "." + quality + ".dodgeMin");	
		int fireDamageMax = armorConfig.getInt(tier + "." + quality + ".fireDamageMax");	
		int fireDamageMin = armorConfig.getInt(tier + "." + quality + ".fireDamageMin");	
		int fireResistMax = armorConfig.getInt(tier + "." + quality + ".fireResistMax");	
		int fireResistMin = armorConfig.getInt(tier + "." + quality + ".fireResistMin");	
		int goldFindMax = armorConfig.getInt(tier + "." + quality + ".goldFindMax");	
		int goldFindMin = armorConfig.getInt(tier + "." + quality + ".goldFindMin");	
		int hpMax = armorConfig.getInt(tier + "." + quality + ".healthPointsMax");	
		int hpMin = armorConfig.getInt(tier + "." + quality + ".healthPointsMin");	
		int hpRegenMax = armorConfig.getInt(tier + "." + quality + ".healthRegenMax");	
		int hpRegenMin = armorConfig.getInt(tier + "." + quality + ".healthRegenMin");	
		int itemFindMax = armorConfig.getInt(tier + "." + quality + ".itemFindMax");	
		int itemFindMin = armorConfig.getInt(tier + "." + quality + ".itemFindMin");
		int knockbackMax = armorConfig.getInt(tier + "." + quality + ".knockbackMax");	
		int knockbackMin = armorConfig.getInt(tier + "." + quality + ".knockbackMin");	
		int lifestealMax = armorConfig.getInt(tier + "." + quality + ".lifestealMax");	
		int lifestealMin = armorConfig.getInt(tier + "." + quality + ".lifestealMin");	
		int manaMax = armorConfig.getInt(tier + "." + quality + ".manaPointsMax");	
		int manaMin = armorConfig.getInt(tier + "." + quality + ".manaPointsMin");	
		int manaRegenMax = armorConfig.getInt(tier + "." + quality + ".manaRegenMax");	
		int manaRegenMin = armorConfig.getInt(tier + "." + quality + ".manaRegenMin");	
		int manastealMax = armorConfig.getInt(tier + "." + quality + ".manastealMax");	
		int manastealMin = armorConfig.getInt(tier + "." + quality + ".manastealMin");	
		int poisonDamageMax = armorConfig.getInt(tier + "." + quality + ".poisonDamageMax");	
		int poisonDamageMin = armorConfig.getInt(tier + "." + quality + ".poisonDamageMin");	
		int poisonResistMax = armorConfig.getInt(tier + "." + quality + ".poisonResistMax");	
		int poisonResistMin = armorConfig.getInt(tier + "." + quality + ".poisonResistMin");	
		int reflectionMax = armorConfig.getInt(tier + "." + quality + ".reflectionMax");	
		int reflectionMin = armorConfig.getInt(tier + "." + quality + ".reflectionMin");	
		int slownessMax = armorConfig.getInt(tier + "." + quality + ".slownessMax");	
		int slownessMin = armorConfig.getInt(tier + "." + quality + ".slownessMin");	
		int staminaMax = armorConfig.getInt(tier + "." + quality + ".staminaMax");	
		int staminaMin = armorConfig.getInt(tier + "." + quality + ".staminaMin");	
		int staminaRegenMax = armorConfig.getInt(tier + "." + quality + ".staminaRegenMax");	
		int staminaRegenMin = armorConfig.getInt(tier + "." + quality + ".staminaRegenMin");	
		int thornsDamageMax = armorConfig.getInt(tier + "." + quality + ".thornsDamageMax");	
		int thornsDamageMin = armorConfig.getInt(tier + "." + quality + ".thornsDamageMin");
		int thornsResistMax = armorConfig.getInt(tier + "." + quality + ".thornsResistMax");	
		int thornsResistMin = armorConfig.getInt(tier + "." + quality + ".thornsResistMin");

		////////////////////////////
		/// Calculate Item Stats ///
		////////////////////////////
		int armor = randomInt(armorMin, armorMax);
		int block = randomInt(blockMin, blockMax);
		int coldDamage = randomInt(coldDamageMin, coldDamageMax);
		int coldResist = randomInt(coldResistMin, coldResistMax);
		int critChance = randomInt(critChanceMin, critChanceMax);
		int damage = randomInt(damageMin, damageMax);
		int dodgeChance = randomInt(dodgeMin, dodgeMax);
		int fireDamage = randomInt(fireDamageMin, fireDamageMax);
		int fireResist = randomInt(fireResistMin, fireResistMax);
		int goldFind = randomInt(goldFindMin, goldFindMax);
		int hp = randomInt(hpMin, hpMax);
		int hpRegen = randomInt(hpRegenMin, hpRegenMax);
		int itemFind = randomInt(itemFindMin, itemFindMax);
		int knockback = randomInt(knockbackMin, knockbackMax);
		int lifesteal = randomInt(lifestealMin, lifestealMax);
		int mana = randomInt(manaMin, manaMax);
		int manaRegen = randomInt(manaRegenMin, manaRegenMax);
		int manasteal = randomInt(manastealMin, manastealMax);
		int poisonDamage = randomInt(poisonDamageMin, poisonDamageMax);
		int poisonResist = randomInt(poisonResistMin, poisonResistMax);
		int reflection = randomInt(reflectionMin, reflectionMax);
		int slowness = randomInt(slownessMin, slownessMax);
		int stamina = randomInt(staminaMin, staminaMax);
		int staminaRegen = randomInt(staminaRegenMin, staminaRegenMax);
		int thornsDamage = randomInt(thornsDamageMin, thornsDamageMax);
		int thornsResist = randomInt(thornsResistMin, thornsResistMax);

		/////////////////////
		/// SET ITEM LORE ///
		/////////////////////
		ItemMeta im = item.getItemMeta();

		String nameFormatting = ItemQualityManager.getStringFormatting(quality);

		//Set the items Name
		im.setDisplayName(nameFormatting + "BetterNameComingSoon");

		//Set the item lore
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + tier + ": " + quality);	//Define the quality of item.
		lore.add("");																		//create blank space

		if (armor > 0) 		{ lore.add(ChatColor.GREEN + "+" + armor + " Armor"); }
		if (block > 0) 		{ lore.add(ChatColor.DARK_PURPLE + "+" + block + " Block Chance"); }
		if (coldDamage > 0) { lore.add(ChatColor.RED + "+" + coldDamage + " Cold Damage"); }
		if (coldResist > 0) { lore.add(ChatColor.BLUE + "+" + coldResist + " Cold Resistance"); }
		if (critChance > 0) { lore.add(ChatColor.DARK_PURPLE + "+" + critChance + " Critical Hit Chance"); }
		if (damage > 0) 	{ lore.add(ChatColor.RED + "+" + damage + " Damage"); }
		if (dodgeChance > 0){ lore.add(ChatColor.DARK_PURPLE + "+" + dodgeChance + " Dodge Chance"); }
		if (fireDamage > 0) { lore.add(ChatColor.RED + "+" + fireDamage + " Fire Damage"); }
		if (fireResist > 0) { lore.add(ChatColor.BLUE + "+" + fireResist + " Fire Resistance"); }
		if (goldFind > 0)	{ lore.add(ChatColor.GOLD + "+" + goldFind + " Gold Find"); }			
		if (hp > 0) 		{ lore.add(ChatColor.GREEN + "+" + hp + " Health"); }
		if (hpRegen > 0) 	{ lore.add(ChatColor.GREEN + "+" + hpRegen + " Health Regeneration"); }
		if (itemFind > 0)	{ lore.add(ChatColor.GOLD + "+" + itemFind + " Item Find"); }			
		if (knockback > 0)	{ lore.add(ChatColor.DARK_PURPLE + "+" + knockback + " Knockback"); }			
		if (lifesteal > 0)	{ lore.add(ChatColor.DARK_PURPLE + "+" + lifesteal + " Lifesteal"); }			
		if (mana > 0)		{ lore.add(ChatColor.GREEN + "+" + mana + " Mana"); }			
		if (manaRegen > 0)	{ lore.add(ChatColor.GREEN + "+" + manaRegen + " Mana Regeneration"); }	
		if (manasteal > 0)	{ lore.add(ChatColor.DARK_PURPLE + "+" + manasteal + " Manasteal"); }			
		if (poisonDamage >0){ lore.add(ChatColor.RED + "+" + poisonDamage + " Poison Damage"); }			
		if (poisonResist >0){ lore.add(ChatColor.BLUE + "+" + poisonResist + " Poison Resistance"); }			
		if (reflection > 0) { lore.add(ChatColor.DARK_PURPLE + "+" + reflection + " Damage Reflection"); }
		if (slowness > 0)	{ lore.add(ChatColor.DARK_PURPLE + "+" + slowness + " Slowness"); }			
		if (stamina > 0)	{ lore.add(ChatColor.GREEN + "+" + stamina + " Stamina"); }			
		if (staminaRegen >0){ lore.add(ChatColor.GREEN + "+" + staminaRegen + " Stamina Regeneration"); }
		if (thornsDamage > 0){ lore.add(ChatColor.RED + "+" + thornsDamage + " Thorn Damage"); }			
		if (thornsResist > 0){ lore.add(ChatColor.BLUE + "+" + thornsResist + " Thorn Resistance"); }			

		//Set the item lore
		im.setLore(lore);
		//Set the item meta
		item.setItemMeta(im);

		return item;
	}

	public static ItemStack createWeapon(ItemStack item, ItemTier tier, ItemQuality quality) {

		/////////////////////////////
		/// GRAB ITEM FROM CONFIG ///
		/////////////////////////////
		weaponConfigFile = new File(randomArmorFilePath);
		weaponConfig =  YamlConfiguration.loadConfiguration(weaponConfigFile);

		//String name = tier.replaceAll("_", " ");
		//List<String> itemDescription = (List<String>) armorConfig.getList(tier + ".itemDescription");
		int armorMax = weaponConfig.getInt(tier + "." + quality + ".armorMax");	
		int armorMin = weaponConfig.getInt(tier + "." + quality + ".armorMin");	
		int blindnessMax = weaponConfig.getInt(tier + "." + quality + ".blindnessMax");	
		int blindnessMin = weaponConfig.getInt(tier + "." + quality + ".blindnessMin");	
		int blockMax = weaponConfig.getInt(tier + "." + quality + ".blockMax");	
		int blockMin = weaponConfig.getInt(tier + "." + quality + ".blockMin");	
		int coldDamageMax = weaponConfig.getInt(tier + "." + quality + ".coldDamageMax");	
		int coldDamageMin = weaponConfig.getInt(tier + "." + quality + ".coldDamageMin");	
		int coldResistMax = weaponConfig.getInt(tier + "." + quality + ".coldResistMax");	
		int coldResistMin = weaponConfig.getInt(tier + "." + quality + ".coldResistMin");	
		int critChanceMax = weaponConfig.getInt(tier + "." + quality + ".critChanceMax");	
		int critChanceMin = weaponConfig.getInt(tier + "." + quality + ".critChanceMin");	
		int damageMax = weaponConfig.getInt(tier + "." + quality + ".damageMax");	
		int damageMin = weaponConfig.getInt(tier + "." + quality + ".damageMin");	
		int dodgeMax = weaponConfig.getInt(tier + "." + quality + ".dodgeMax");	
		int dodgeMin = weaponConfig.getInt(tier + "." + quality + ".dodgeMin");	
		int fireDamageMax = weaponConfig.getInt(tier + "." + quality + ".fireDamageMax");	
		int fireDamageMin = weaponConfig.getInt(tier + "." + quality + ".fireDamageMin");	
		int fireResistMax = weaponConfig.getInt(tier + "." + quality + ".fireResistMax");	
		int fireResistMin = weaponConfig.getInt(tier + "." + quality + ".fireResistMin");	
		int goldFindMax = weaponConfig.getInt(tier + "." + quality + ".goldFindMax");	
		int goldFindMin = weaponConfig.getInt(tier + "." + quality + ".goldFindMin");	
		int hpMax = weaponConfig.getInt(tier + "." + quality + ".hpMax");	
		int hpMin = weaponConfig.getInt(tier + "." + quality + ".hpMin");	
		int hpRegenMax = weaponConfig.getInt(tier + "." + quality + ".hpRegenMax");	
		int hpRegenMin = weaponConfig.getInt(tier + "." + quality + ".hpRegenMin");	
		int itemFindMax = weaponConfig.getInt(tier + "." + quality + ".itemFindMax");	
		int itemFindMin = weaponConfig.getInt(tier + "." + quality + ".itemFindMin");
		int knockbackMax = weaponConfig.getInt(tier + "." + quality + ".knockbackMax");	
		int knockbackMin = weaponConfig.getInt(tier + "." + quality + ".knockbackMin");	
		int lifestealMax = weaponConfig.getInt(tier + "." + quality + ".lifestealMax");	
		int lifestealMin = weaponConfig.getInt(tier + "." + quality + ".lifestealMin");	
		int manaMax = weaponConfig.getInt(tier + "." + quality + ".manaMax");	
		int manaMin = weaponConfig.getInt(tier + "." + quality + ".manaMin");	
		int manaRegenMax = weaponConfig.getInt(tier + "." + quality + ".manaRegenMax");	
		int manaRegenMin = weaponConfig.getInt(tier + "." + quality + ".manaRegenMin");	
		int manastealMax = weaponConfig.getInt(tier + "." + quality + ".manastealMax");	
		int manastealMin = weaponConfig.getInt(tier + "." + quality + ".manastealMin");	
		int poisonDamageMax = weaponConfig.getInt(tier + "." + quality + ".poisonDamageMax");	
		int poisonDamageMin = weaponConfig.getInt(tier + "." + quality + ".poisonDamageMin");	
		int poisonResistMax = weaponConfig.getInt(tier + "." + quality + ".poisonResistMax");	
		int poisonResistMin = weaponConfig.getInt(tier + "." + quality + ".poisonResistMin");	
		int reflectionMax = weaponConfig.getInt(tier + "." + quality + ".reflectionMax");	
		int reflectionMin = weaponConfig.getInt(tier + "." + quality + ".reflectionMin");	
		int slownessMax = weaponConfig.getInt(tier + "." + quality + ".slownessMax");	
		int slownessMin = weaponConfig.getInt(tier + "." + quality + ".slownessMin");	
		int staminaMax = weaponConfig.getInt(tier + "." + quality + ".staminaMax");	
		int staminaMin = weaponConfig.getInt(tier + "." + quality + ".staminaMin");	
		int staminaRegenMax = weaponConfig.getInt(tier + "." + quality + ".staminaRegenMax");	
		int staminaRegenMin = weaponConfig.getInt(tier + "." + quality + ".staminaRegenMin");	
		int thornsDamageMax = weaponConfig.getInt(tier + "." + quality + ".thornsDamageMax");	
		int thornsDamageMin = weaponConfig.getInt(tier + "." + quality + ".thornsDamageMin");
		int thornsResistMax = weaponConfig.getInt(tier + "." + quality + ".thornsResistMax");	
		int thornsResistMin = weaponConfig.getInt(tier + "." + quality + ".thornsResistMin");

		////////////////////////////
		/// Calculate Item Stats ///
		////////////////////////////
		int armor = randomInt(armorMin, armorMax);
		int blindness = randomInt(blindnessMin, blindnessMax);
		int block = randomInt(blockMin, blockMax);
		int coldDamage = randomInt(coldDamageMin, coldDamageMax);
		int coldResist = randomInt(coldResistMin, coldResistMax);
		int critChance = randomInt(critChanceMin, critChanceMax);
		int damage = randomInt(damageMin, damageMax);
		int dodgeChance = randomInt(dodgeMin, dodgeMax);
		int fireDamage = randomInt(fireDamageMin, fireDamageMax);
		int fireResist = randomInt(fireResistMin, fireResistMax);
		int goldFind = randomInt(goldFindMin, goldFindMax);
		int hp = randomInt(hpMin, hpMax);
		int hpRegen = randomInt(hpRegenMin, hpRegenMax);
		int itemFind = randomInt(itemFindMin, itemFindMax);
		int knockback = randomInt(knockbackMin, knockbackMax);
		int lifesteal = randomInt(lifestealMin, lifestealMax);
		int mana = randomInt(manaMin, manaMax);
		int manaRegen = randomInt(manaRegenMin, manaRegenMax);
		int manasteal = randomInt(manastealMin, manastealMax);
		int poisonDamage = randomInt(poisonDamageMin, poisonDamageMax);
		int poisonResist = randomInt(poisonResistMin, poisonResistMax);
		int reflection = randomInt(reflectionMin, reflectionMax);
		int slowness = randomInt(slownessMin, slownessMax);
		int stamina = randomInt(staminaMin, staminaMax);
		int staminaRegen = randomInt(staminaRegenMin, staminaRegenMax);
		int thornsDamage = randomInt(thornsDamageMin, thornsDamageMax);
		int thornsResist = randomInt(thornsResistMin, thornsResistMax);

		/////////////////////
		/// SET ITEM LORE ///
		/////////////////////
		ItemMeta im = item.getItemMeta();

		String nameFormatting = ItemQualityManager.getStringFormatting(quality);

		//Set the items Name
		im.setDisplayName(nameFormatting + "BetterNameComingSoon");

		//Set the item lore
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + tier + ": " + quality);	//Define the quality of item.
		lore.add("");																		//create blank space

		if (armor > 0) 		{ lore.add(ChatColor.GREEN + "+" + armor + " Armor"); }
		if (blindness > 0) 	{ lore.add(ChatColor.DARK_PURPLE + "+" + blindness + " Blindness"); }
		if (block > 0) 		{ lore.add(ChatColor.DARK_PURPLE + "+" + block + " Block Chance"); }
		if (coldDamage > 0) { lore.add(ChatColor.RED + "+" + coldDamage + " Cold Damage"); }
		if (coldResist > 0) { lore.add(ChatColor.BLUE + "+" + coldResist + " Cold Resistance"); }
		if (critChance > 0) { lore.add(ChatColor.DARK_PURPLE + "+" + critChance + " Critical Hit Chance"); }
		if (damage > 0) 	{ lore.add(ChatColor.RED + "+" + damage + " Damage"); }
		if (dodgeChance > 0){ lore.add(ChatColor.DARK_PURPLE + "+" + dodgeChance + " Dodge Chance"); }
		if (fireDamage > 0) { lore.add(ChatColor.RED + "+" + fireDamage + " Fire Damage"); }
		if (fireResist > 0) { lore.add(ChatColor.BLUE + "+" + fireResist + " Fire Resistance"); }
		if (goldFind > 0)	{ lore.add(ChatColor.GOLD + "+" + goldFind + " Gold Find"); }			
		if (hp > 0) 		{ lore.add(ChatColor.GREEN + "+" + hp + " Health"); }
		if (hpRegen > 0) 	{ lore.add(ChatColor.GREEN + "+" + hpRegen + " Health Regeneration"); }
		if (itemFind > 0)	{ lore.add(ChatColor.GOLD + "+" + itemFind + " Item Find"); }			
		if (knockback > 0)	{ lore.add(ChatColor.DARK_PURPLE + "+" + knockback + " Knockback"); }			
		if (lifesteal > 0)	{ lore.add(ChatColor.DARK_PURPLE + "+" + lifesteal + " Lifesteal"); }			
		if (mana > 0)		{ lore.add(ChatColor.GREEN + "+" + mana + " Mana"); }			
		if (manaRegen > 0)	{ lore.add(ChatColor.GREEN + "+" + manaRegen + " Mana Regeneration"); }	
		if (manasteal > 0)	{ lore.add(ChatColor.DARK_PURPLE + "+" + manasteal + " Manasteal"); }			
		if (poisonDamage >0){ lore.add(ChatColor.RED + "+" + poisonDamage + " Poison Damage"); }			
		if (poisonResist >0){ lore.add(ChatColor.BLUE + "+" + poisonResist + " Poison Resistance"); }			
		if (reflection > 0) { lore.add(ChatColor.DARK_PURPLE + "+" + reflection + " Damage Reflection"); }
		if (slowness > 0)	{ lore.add(ChatColor.DARK_PURPLE + "+" + slowness + " Slowness"); }			
		if (stamina > 0)	{ lore.add(ChatColor.GREEN + "+" + stamina + " Stamina"); }			
		if (staminaRegen >0){ lore.add(ChatColor.GREEN + "+" + staminaRegen + " Stamina Regeneration"); }
		if (thornsDamage > 0){ lore.add(ChatColor.RED + "+" + thornsDamage + " Thorn Damage"); }			
		if (thornsResist > 0){ lore.add(ChatColor.BLUE + "+" + thornsResist + " Thorn Resistance"); }			

		//Set the item lore
		im.setLore(lore);
		//Set the item meta
		item.setItemMeta(im);

		return item;
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
	 * Creates a new configuration file for random armor and weapons.
	 */
	private static void createConfig() {

		//Create the configuration file for random armor and weapons. 
		File armorConfigFile = new File(randomArmorFilePath);
		File weaponConfigFile = new File(randomWeaponFilePath);
		
		FileConfiguration armorConfig =  YamlConfiguration.loadConfiguration(armorConfigFile);
		FileConfiguration weaponConfig =  YamlConfiguration.loadConfiguration(weaponConfigFile);
		
		////////////////////
		/// RANDOM ARMOR ///
		////////////////////
		armorConfig.set("T1", "T1");
		armorConfig.set("T1.JUNK.armorMin", 5);
		armorConfig.set("T1.JUNK.armorMax", 5);
		armorConfig.set("T1.JUNK.blockChanceMin", 5);
		armorConfig.set("T1.JUNK.blockChanceMax", 5);
		armorConfig.set("T1.JUNK.coldDamageMin", 5);
		armorConfig.set("T1.JUNK.coldDamageMax", 5);
		armorConfig.set("T1.JUNK.coldResistMin", 5);
		armorConfig.set("T1.JUNK.coldResistMax", 5);
		armorConfig.set("T1.JUNK.critChanceMin", 5);
		armorConfig.set("T1.JUNK.critChanceMax", 5);
		armorConfig.set("T1.JUNK.damageMin", 5);
		armorConfig.set("T1.JUNK.damageMax", 5);
		armorConfig.set("T1.JUNK.dodgeChanceMin", 5);
		armorConfig.set("T1.JUNK.dodgeChanceMax", 5);
		armorConfig.set("T1.JUNK.fireDamageMin", 5);
		armorConfig.set("T1.JUNK.fireDamageMax", 5);
		armorConfig.set("T1.JUNK.fireResistMin", 5);
		armorConfig.set("T1.JUNK.fireResistMax", 5);
		armorConfig.set("T1.JUNK.goldFindMin", 5);
		armorConfig.set("T1.JUNK.goldFindMax", 5);
		armorConfig.set("T1.JUNK.healthPointsMin", 5);
		armorConfig.set("T1.JUNK.healthPointsMax", 5);
		armorConfig.set("T1.JUNK.healthRegenMin", 5);
		armorConfig.set("T1.JUNK.healthRegenMax", 5);
		armorConfig.set("T1.JUNK.itemFindMin", 5);
		armorConfig.set("T1.JUNK.itemFindMax", 5);
		armorConfig.set("T1.JUNK.knockbackMin", 5);
		armorConfig.set("T1.JUNK.knockbackMax", 5);
		armorConfig.set("T1.JUNK.lifestealMin", 5);
		armorConfig.set("T1.JUNK.lifestealMax", 5);
		armorConfig.set("T1.JUNK.manaPointsMin", 5);
		armorConfig.set("T1.JUNK.manaPointsMax", 5);
		armorConfig.set("T1.JUNK.manaRegenMin", 5);
		armorConfig.set("T1.JUNK.manaRegenMax", 5);
		armorConfig.set("T1.JUNK.manastealMin", 5);
		armorConfig.set("T1.JUNK.manastealMax", 5);
		armorConfig.set("T1.JUNK.poisonDamageMin", 5);
		armorConfig.set("T1.JUNK.poisonDamageMax", 5);
		armorConfig.set("T1.JUNK.poisonResistMin", 5);
		armorConfig.set("T1.JUNK.poisonResistMax", 5);
		armorConfig.set("T1.JUNK.reflectionMin", 5);
		armorConfig.set("T1.JUNK.reflectionMax", 5);
		armorConfig.set("T1.JUNK.slownessMin", 5);
		armorConfig.set("T1.JUNK.slownessMax", 5);
		armorConfig.set("T1.JUNK.staminaPointMin", 5);
		armorConfig.set("T1.JUNK.staminaPointMax", 5);
		armorConfig.set("T1.JUNK.thornDamageMin", 5);
		armorConfig.set("T1.JUNK.thornDamageMax", 5);
		armorConfig.set("T1.JUNK.thornResistMin", 5);
		armorConfig.set("T1.JUNK.thornResistMax", 5);

		
		//////////////////////
		/// RANDOM WEAPONS ///
		//////////////////////		 
		weaponConfig.set("T1", "T1");
		weaponConfig.set("T1.JUNK.blindnessMin", 5);
		weaponConfig.set("T1.JUNK.blindnessMax", 5);
		weaponConfig.set("T1.JUNK.blockChanceMin", 5);
		weaponConfig.set("T1.JUNK.blockChanceMax", 5);
		weaponConfig.set("T1.JUNK.coldDamageMin", 5);
		weaponConfig.set("T1.JUNK.coldDamageMax", 5);
		weaponConfig.set("T1.JUNK.critChanceMin", 5);
		weaponConfig.set("T1.JUNK.critChanceMax", 5);
		weaponConfig.set("T1.JUNK.damageMin", 5);
		weaponConfig.set("T1.JUNK.damageMax", 5);
		weaponConfig.set("T1.JUNK.dodgeChanceMin", 5);
		weaponConfig.set("T1.JUNK.dodgeChanceMax", 5);
		weaponConfig.set("T1.JUNK.fireDamageMin", 5);
		weaponConfig.set("T1.JUNK.fireDamageMax", 5);
		weaponConfig.set("T1.JUNK.goldFindMin", 5);
		weaponConfig.set("T1.JUNK.goldFindMax", 5);
		weaponConfig.set("T1.JUNK.itemFindMin", 5);
		weaponConfig.set("T1.JUNK.itemFindMax", 5);
		weaponConfig.set("T1.JUNK.knockbackMin", 5);
		weaponConfig.set("T1.JUNK.knockbackMax", 5);
		weaponConfig.set("T1.JUNK.lifestealMin", 5);
		weaponConfig.set("T1.JUNK.lifestealMax", 5);
		weaponConfig.set("T1.JUNK.manastealMin", 5);
		weaponConfig.set("T1.JUNK.manastealMax", 5);
		weaponConfig.set("T1.JUNK.poisonDamageMin", 5);
		weaponConfig.set("T1.JUNK.poisonDamageMax", 5);
		weaponConfig.set("T1.JUNK.reflectionMin", 5);
		weaponConfig.set("T1.JUNK.reflectionMax", 5);
		weaponConfig.set("T1.JUNK.slownessMin", 5);
		weaponConfig.set("T1.JUNK.slownessMax", 5);
		weaponConfig.set("T1.JUNK.thornDamageMin", 5);
		weaponConfig.set("T1.JUNK.thornDamageMax", 5);

		try {
			armorConfig.save(armorConfigFile);
			weaponConfig.save(weaponConfigFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
