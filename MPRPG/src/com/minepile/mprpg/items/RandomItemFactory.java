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
		int coldResistMax = armorConfig.getInt(tier + "." + quality + ".coldResistMax");	
		int coldResistMin = armorConfig.getInt(tier + "." + quality + ".coldResistMin");		
		int dodgeMax = armorConfig.getInt(tier + "." + quality + ".dodgeMax");	
		int dodgeMin = armorConfig.getInt(tier + "." + quality + ".dodgeMin");		
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
		int manaMax = armorConfig.getInt(tier + "." + quality + ".manaPointsMax");	
		int manaMin = armorConfig.getInt(tier + "." + quality + ".manaPointsMin");	
		int manaRegenMax = armorConfig.getInt(tier + "." + quality + ".manaRegenMax");	
		int manaRegenMin = armorConfig.getInt(tier + "." + quality + ".manaRegenMin");			
		int poisonResistMax = armorConfig.getInt(tier + "." + quality + ".poisonResistMax");	
		int poisonResistMin = armorConfig.getInt(tier + "." + quality + ".poisonResistMin");	
		int reflectionMax = armorConfig.getInt(tier + "." + quality + ".reflectionMax");	
		int reflectionMin = armorConfig.getInt(tier + "." + quality + ".reflectionMin");		
		int staminaMax = armorConfig.getInt(tier + "." + quality + ".staminaMax");	
		int staminaMin = armorConfig.getInt(tier + "." + quality + ".staminaMin");	
		int staminaRegenMax = armorConfig.getInt(tier + "." + quality + ".staminaRegenMax");	
		int staminaRegenMin = armorConfig.getInt(tier + "." + quality + ".staminaRegenMin");
		int thornsResistMax = armorConfig.getInt(tier + "." + quality + ".thornsResistMax");	
		int thornsResistMin = armorConfig.getInt(tier + "." + quality + ".thornsResistMin");

		////////////////////////////
		/// Calculate Item Stats ///
		////////////////////////////
		int armor = randomInt(armorMin, armorMax);
		int block = randomInt(blockMin, blockMax);
		int coldResist = randomInt(coldResistMin, coldResistMax);
		int dodgeChance = randomInt(dodgeMin, dodgeMax);
		int fireResist = randomInt(fireResistMin, fireResistMax);
		int goldFind = randomInt(goldFindMin, goldFindMax);
		int hp = randomInt(hpMin, hpMax);
		int hpRegen = randomInt(hpRegenMin, hpRegenMax);
		int itemFind = randomInt(itemFindMin, itemFindMax);
		int mana = randomInt(manaMin, manaMax);
		int manaRegen = randomInt(manaRegenMin, manaRegenMax);
		int poisonResist = randomInt(poisonResistMin, poisonResistMax);
		int reflection = randomInt(reflectionMin, reflectionMax);
		int stamina = randomInt(staminaMin, staminaMax);
		int staminaRegen = randomInt(staminaRegenMin, staminaRegenMax);
		int thornsResist = randomInt(thornsResistMin, thornsResistMax);

		/////////////////////
		/// SET ITEM LORE ///
		/////////////////////
		ItemMeta im = item.getItemMeta();

		String nameFormatting = ChatColor.BOLD + ItemQualityManager.getStringFormatting(quality);
		String itemType = item.getType().toString().replace("_", " ");
		String itemInfo = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + quality + " " + tier + " " + itemType;
		double randSocket = Math.random() * 100;

		//Set the items Name
		im.setDisplayName(nameFormatting + "BetterNameComingSoon");

		//Set the item lore
		ArrayList<String> lore = new ArrayList<String>();

		lore.add(itemInfo);	//Define the quality of item.

		lore.add(" "); //Spacer																
		if (armor > 0) 		{ lore.add(ChatColor.WHITE + "+" + armor + " Armor"); }
		if (hp > 0) 		{ lore.add(ChatColor.WHITE + "+" + hp + " Health"); }
		if (hpRegen > 0) 	{ lore.add(ChatColor.WHITE + "+" + hpRegen + " Health Regeneration"); }		
		if (mana > 0)		{ lore.add(ChatColor.WHITE + "+" + mana + " Mana"); }			
		if (manaRegen > 0)	{ lore.add(ChatColor.WHITE + "+" + manaRegen + " Mana Regeneration"); }	
		if (stamina > 0)	{ lore.add(ChatColor.WHITE + "+" + stamina + " Stamina"); }
		if (staminaRegen >0){ lore.add(ChatColor.WHITE + "+" + staminaRegen + " Stamina Regeneration"); }

		//Magic attributes
		if (block != 0 || reflection != 0 || dodgeChance != 0 || coldResist != 0 
				|| fireResist != 0 || poisonResist != 0 || thornsResist != 0 
				|| goldFind != 0 || itemFind != 0) {
			lore.add(" "); //Spacer
		}
		if (block > 0) 		{ lore.add(ChatColor.BLUE + "+" + block + " Block Chance"); }
		if (reflection > 0) { lore.add(ChatColor.BLUE + "+" + reflection + " Damage Reflection"); }
		if (dodgeChance > 0){ lore.add(ChatColor.BLUE + "+" + dodgeChance + " Dodge Chance"); }
		if (coldResist > 0) { lore.add(ChatColor.BLUE + "+" + coldResist + " Cold Resistance"); }	
		if (fireResist > 0) { lore.add(ChatColor.BLUE + "+" + fireResist + " Fire Resistance"); }			
		if (poisonResist >0){ lore.add(ChatColor.BLUE + "+" + poisonResist + " Poison Resistance"); }	
		if (thornsResist > 0){lore.add(ChatColor.BLUE + "+" + thornsResist + " Thorn Resistance"); }			
		if (goldFind > 0)	{ lore.add(ChatColor.GOLD + "+" + goldFind + " Gold Find"); }			
		if (itemFind > 0)	{ lore.add(ChatColor.GOLD + "+" + itemFind + " Item Find"); }	

		//Spacer
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

		//Spacer
		lore.add("");
		lore.add("Sell Price" + ChatColor.DARK_GRAY + ": " 
				+ ChatColor.RESET + "0" + ChatColor.GOLD + "• " 
				+ ChatColor.RESET + "0" + ChatColor.GRAY + "• " 
				+ ChatColor.RESET + "10" + ChatColor.RED + "•");

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

		int blindnessMax = weaponConfig.getInt(tier + "." + quality + ".blindnessMax");	
		int blindnessMin = weaponConfig.getInt(tier + "." + quality + ".blindnessMin");	
		int coldDamageMax = weaponConfig.getInt(tier + "." + quality + ".coldDamageMax");	
		int coldDamageMin = weaponConfig.getInt(tier + "." + quality + ".coldDamageMin");		
		int critChanceMax = weaponConfig.getInt(tier + "." + quality + ".critChanceMax");	
		int critChanceMin = weaponConfig.getInt(tier + "." + quality + ".critChanceMin");	
		int damageMax = weaponConfig.getInt(tier + "." + quality + ".damageMax");	
		int damageMin = weaponConfig.getInt(tier + "." + quality + ".damageMin");	
		int fireDamageMax = weaponConfig.getInt(tier + "." + quality + ".fireDamageMax");	
		int fireDamageMin = weaponConfig.getInt(tier + "." + quality + ".fireDamageMin");	
		int knockbackMax = weaponConfig.getInt(tier + "." + quality + ".knockbackMax");	
		int knockbackMin = weaponConfig.getInt(tier + "." + quality + ".knockbackMin");	
		int lifestealMax = weaponConfig.getInt(tier + "." + quality + ".lifestealMax");	
		int lifestealMin = weaponConfig.getInt(tier + "." + quality + ".lifestealMin");	
		int manastealMax = weaponConfig.getInt(tier + "." + quality + ".manastealMax");	
		int manastealMin = weaponConfig.getInt(tier + "." + quality + ".manastealMin");	
		int poisonDamageMax = weaponConfig.getInt(tier + "." + quality + ".poisonDamageMax");	
		int poisonDamageMin = weaponConfig.getInt(tier + "." + quality + ".poisonDamageMin");	
		int slownessMax = weaponConfig.getInt(tier + "." + quality + ".slownessMax");	
		int slownessMin = weaponConfig.getInt(tier + "." + quality + ".slownessMin");		
		int thornsDamageMax = weaponConfig.getInt(tier + "." + quality + ".thornsDamageMax");	
		int thornsDamageMin = weaponConfig.getInt(tier + "." + quality + ".thornsDamageMin");

		////////////////////////////
		/// Calculate Item Stats ///
		////////////////////////////
		int blindness = randomInt(blindnessMin, blindnessMax);
		int coldDamage = randomInt(coldDamageMin, coldDamageMax);
		int critChance = randomInt(critChanceMin, critChanceMax);
		int damage = randomInt(damageMin, damageMax);
		int fireDamage = randomInt(fireDamageMin, fireDamageMax);
		int knockback = randomInt(knockbackMin, knockbackMax);
		int lifesteal = randomInt(lifestealMin, lifestealMax);
		int manasteal = randomInt(manastealMin, manastealMax);
		int poisonDamage = randomInt(poisonDamageMin, poisonDamageMax);
		int slowness = randomInt(slownessMin, slownessMax);
		int thornsDamage = randomInt(thornsDamageMin, thornsDamageMax);

		/////////////////////
		/// SET ITEM LORE ///
		/////////////////////
		ItemMeta im = item.getItemMeta();

		String nameFormatting = ChatColor.BOLD + ItemQualityManager.getStringFormatting(quality);
		String itemType = item.getType().toString().replace("_", " ");
		String itemInfo = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + quality + " " + tier + " " + itemType;
		double randSocket = Math.random() * 100;

		//Set the items Name
		im.setDisplayName(nameFormatting + "BetterNameComingSoon");

		//Set the item lore
		ArrayList<String> lore = new ArrayList<String>();

		lore.add(itemInfo);	//Define the quality of item.

		lore.add(" "); //Spacer
		if (damage > 0) 	{ lore.add(ChatColor.WHITE + "+" + damage + " Damage"); }

		//Magic attributes
		if (coldDamage != 0 || fireDamage != 0 || poisonDamage != 0 || thornsDamage != 0
				|| blindness != 0 || critChance != 0 || knockback != 0 || slowness != 0 
				|| lifesteal != 0 || manasteal != 0) {
			lore.add(" "); //Spacer
		}																
		if (coldDamage > 0) { lore.add(ChatColor.BLUE + "+" + coldDamage + " Cold Damage"); }
		if (fireDamage > 0) { lore.add(ChatColor.BLUE + "+" + fireDamage + " Fire Damage"); }		
		if (poisonDamage >0){ lore.add(ChatColor.BLUE + "+" + poisonDamage + " Poison Damage"); }
		if (thornsDamage > 0){ lore.add(ChatColor.BLUE + "+" + thornsDamage + " Thorn Damage"); }	
		if (blindness > 0) { lore.add(ChatColor.BLUE + "+" + blindness + " Blindness"); }
		if (critChance > 0) { lore.add(ChatColor.BLUE + "+" + critChance + " Critical Hit Chance"); }
		if (knockback > 0)	{ lore.add(ChatColor.BLUE + "+" + knockback + " Knockback"); }			
		if (slowness > 0)	{ lore.add(ChatColor.BLUE + "+" + slowness + " Slowness"); }		
		if (lifesteal > 0)	{ lore.add(ChatColor.BLUE + "+" + lifesteal + " Lifesteal"); }		
		if (manasteal > 0)	{ lore.add(ChatColor.BLUE + "+" + manasteal + " Manasteal"); }	

		//Spacer
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
		
		//Spacer
		lore.add("");
		lore.add("Sell Price" + ChatColor.DARK_GRAY + ": " 
				+ ChatColor.RESET + "0" + ChatColor.GOLD + "• " 
				+ ChatColor.RESET + "0" + ChatColor.GRAY + "• " 
				+ ChatColor.RESET + "10" + ChatColor.RED + "•");

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
		armorConfig.set("T1.JUNK.coldResistMin", 5);
		armorConfig.set("T1.JUNK.coldResistMax", 5);
		armorConfig.set("T1.JUNK.dodgeChanceMin", 5);
		armorConfig.set("T1.JUNK.dodgeChanceMax", 5);
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
		armorConfig.set("T1.JUNK.manaPointsMin", 5);
		armorConfig.set("T1.JUNK.manaPointsMax", 5);
		armorConfig.set("T1.JUNK.manaRegenMin", 5);
		armorConfig.set("T1.JUNK.manaRegenMax", 5);
		armorConfig.set("T1.JUNK.poisonResistMin", 5);
		armorConfig.set("T1.JUNK.poisonResistMax", 5);
		armorConfig.set("T1.JUNK.reflectionMin", 5);
		armorConfig.set("T1.JUNK.reflectionMax", 5);
		armorConfig.set("T1.JUNK.staminaPointMin", 5);
		armorConfig.set("T1.JUNK.staminaPointMax", 5);
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
		weaponConfig.set("T1.JUNK.fireDamageMin", 5);
		weaponConfig.set("T1.JUNK.fireDamageMax", 5);
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
