package com.minepile.mprpg.items;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.items.ItemQualityManager.ItemQuality;
import com.minepile.mprpg.items.ItemTierManager.ItemTier;

public class ArmorItemManager {

	//setup instance variables
	public static MPRPG plugin;
	static ArmorItemManager armorItemManagerInstance = new ArmorItemManager();
	static String armorItemsFilePath = "plugins/MPRPG/items/Armor.yml";

	//Configuration file that holds armor information.
	static File configFile;
	static FileConfiguration armorItemConfig;

	//Create instance
	public static ArmorItemManager getInstance() {
		return armorItemManagerInstance;
	}

	//Setup ArmorItemManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		//If configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(armorItemsFilePath)).exists()){
			createConfig();
		} else {
			//lets load the configuration file.
			File configFile = new File(armorItemsFilePath);
			armorItemConfig =  YamlConfiguration.loadConfiguration(configFile);
		}
	}	

	/**
	 * Generates the item that needs to be dropped based on the settings from the configuration file.
	 * <p>
	 * This method is currently only for Armor drops.
	 * 
	 * @param itemName the item name specified inside the configuration file for this item type
	 */
	public static ItemStack makeItem(String itemName) {

		/////////////////////////////
		/// GRAB ITEM FROM CONFIG ///
		/////////////////////////////
		configFile = new File(armorItemsFilePath);
		armorItemConfig =  YamlConfiguration.loadConfiguration(configFile);

		String name = itemName.replaceAll("_", " ");
		ItemTier tier = ItemTierManager.getItemTierEnum(armorItemConfig.getString(itemName + ".itemTier"));
		ItemQuality quality = ItemQualityManager.getItemQualityEnum(armorItemConfig.getString(itemName + ".itemQuality"));
		List<String> itemDescription = (List<String>) armorItemConfig.getList(itemName + ".itemDescription");
		int itemNumber = armorItemConfig.getInt(itemName + ".item");
		int armorMax = armorItemConfig.getInt(itemName + ".armorMax");	
		int armorMin = armorItemConfig.getInt(itemName + ".armorMin");	
		int blindnessMax = armorItemConfig.getInt(itemName + ".blindnessMax");	
		int blindnessMin = armorItemConfig.getInt(itemName + ".blindnessMin");	
		int blockMax = armorItemConfig.getInt(itemName + ".blockMax");	
		int blockMin = armorItemConfig.getInt(itemName + ".blockMin");	
		int coldDamageMax = armorItemConfig.getInt(itemName + ".coldDamageMax");	
		int coldDamageMin = armorItemConfig.getInt(itemName + ".coldDamageMin");	
		int coldResistMax = armorItemConfig.getInt(itemName + ".coldResistMax");	
		int coldResistMin = armorItemConfig.getInt(itemName + ".coldResistMin");	
		int critChanceMax = armorItemConfig.getInt(itemName + ".critChanceMax");	
		int critChanceMin = armorItemConfig.getInt(itemName + ".critChanceMin");	
		int damageMax = armorItemConfig.getInt(itemName + ".damageMax");	
		int damageMin = armorItemConfig.getInt(itemName + ".damageMin");	
		int dodgeMax = armorItemConfig.getInt(itemName + ".dodgeMax");	
		int dodgeMin = armorItemConfig.getInt(itemName + ".dodgeMin");	
		int fireDamageMax = armorItemConfig.getInt(itemName + ".fireDamageMax");	
		int fireDamageMin = armorItemConfig.getInt(itemName + ".fireDamageMin");	
		int fireResistMax = armorItemConfig.getInt(itemName + ".fireResistMax");	
		int fireResistMin = armorItemConfig.getInt(itemName + ".fireResistMin");	
		int goldFindMax = armorItemConfig.getInt(itemName + ".goldFindMax");	
		int goldFindMin = armorItemConfig.getInt(itemName + ".goldFindMin");	
		int hpMax = armorItemConfig.getInt(itemName + ".hpMax");	
		int hpMin = armorItemConfig.getInt(itemName + ".hpMin");	
		int hpRegenMax = armorItemConfig.getInt(itemName + ".hpRegenMax");	
		int hpRegenMin = armorItemConfig.getInt(itemName + ".hpRegenMin");	
		int itemFindMax = armorItemConfig.getInt(itemName + ".itemFindMax");	
		int itemFindMin = armorItemConfig.getInt(itemName + ".itemFindMin");
		int knockbackMax = armorItemConfig.getInt(itemName + ".knockbackMax");	
		int knockbackMin = armorItemConfig.getInt(itemName + ".knockbackMin");	
		int lifestealMax = armorItemConfig.getInt(itemName + ".lifestealMax");	
		int lifestealMin = armorItemConfig.getInt(itemName + ".lifestealMin");	
		int manaMax = armorItemConfig.getInt(itemName + ".manaMax");	
		int manaMin = armorItemConfig.getInt(itemName + ".manaMin");	
		int manaRegenMax = armorItemConfig.getInt(itemName + ".manaRegenMax");	
		int manaRegenMin = armorItemConfig.getInt(itemName + ".manaRegenMin");	
		int manastealMax = armorItemConfig.getInt(itemName + ".manastealMax");	
		int manastealMin = armorItemConfig.getInt(itemName + ".manastealMin");	
		int poisonDamageMax = armorItemConfig.getInt(itemName + ".poisonDamageMax");	
		int poisonDamageMin = armorItemConfig.getInt(itemName + ".poisonDamageMin");	
		int poisonResistMax = armorItemConfig.getInt(itemName + ".poisonResistMax");	
		int poisonResistMin = armorItemConfig.getInt(itemName + ".poisonResistMin");	
		int reflectionMax = armorItemConfig.getInt(itemName + ".reflectionMax");	
		int reflectionMin = armorItemConfig.getInt(itemName + ".reflectionMin");	
		int slownessMax = armorItemConfig.getInt(itemName + ".slownessMax");	
		int slownessMin = armorItemConfig.getInt(itemName + ".slownessMin");	
		int staminaMax = armorItemConfig.getInt(itemName + ".staminaMax");	
		int staminaMin = armorItemConfig.getInt(itemName + ".staminaMin");	
		int staminaRegenMax = armorItemConfig.getInt(itemName + ".staminaRegenMax");	
		int staminaRegenMin = armorItemConfig.getInt(itemName + ".staminaRegenMin");	
		int thornsDamageMax = armorItemConfig.getInt(itemName + ".thornsDamageMax");	
		int thornsDamageMin = armorItemConfig.getInt(itemName + ".thornsDamageMin");
		int thornsResistMax = armorItemConfig.getInt(itemName + ".thornsResistMax");	
		int thornsResistMin = armorItemConfig.getInt(itemName + ".thornsResistMin");

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
		Material itemMaterial = ItemGeneratorManager.convertItemIdToMaterial(itemNumber);
		ItemStack item = new ItemStack(itemMaterial, 1);
		ItemMeta im = item.getItemMeta();

		String nameFormatting = ItemQualityManager.getStringFormatting(quality);
		String itemQuality =  ItemQualityManager.getItemQualityString(quality);

		//Set the items Name
		im.setDisplayName(nameFormatting + name);

		//Set the item lore
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + tier + ": " + itemQuality);	//Define the quality of item.
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
		if (manasteal > 0)	{ lore.add(ChatColor.GREEN + "+" + manasteal + " Manasteal"); }			
		if (poisonDamage >0){ lore.add(ChatColor.RED + "+" + poisonDamage + " Poison Damage"); }			
		if (poisonResist >0){ lore.add(ChatColor.BLUE + "+" + poisonResist + " Poison Resistance"); }			
		if (reflection > 0) { lore.add(ChatColor.DARK_PURPLE + "+" + reflection + " Damage Reflection"); }
		if (slowness > 0)	{ lore.add(ChatColor.DARK_PURPLE + "+" + slowness + " Slowness"); }			
		if (stamina > 0)	{ lore.add(ChatColor.GREEN + "+" + stamina + " Stamina"); }			
		if (staminaRegen >0){ lore.add(ChatColor.GREEN + "+" + staminaRegen + " Stamina Regeneration"); }
		if (thornsDamage > 0){ lore.add(ChatColor.RED + "+" + thornsDamage + " Thorn Damage"); }			
		if (thornsResist > 0){ lore.add(ChatColor.BLUE + "+" + thornsResist + " Thorn Resistance"); }			

		lore.add(" ");													//create blank space

		//Set the items description
		if (!itemDescription.isEmpty()) {
			for (int i = 0; i < itemDescription.size(); i++) {
				lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + itemDescription.get(i));
			}
		}


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
	 * This creates the configuration file that will hold data to save armor information.
	 */
	private static void createConfig() {

		ArrayList<String> testDescription = new ArrayList<String>();
		
		// Adding items to arrayList
		testDescription.add("This is a test item");
		testDescription.add("from the config.");

		configFile = new File(armorItemsFilePath);
		armorItemConfig =  YamlConfiguration.loadConfiguration(configFile);

		armorItemConfig.set("testArmorDrop", "testArmorDrop");
		armorItemConfig.set("testArmorDrop" + ".itemTier", "t1");
		armorItemConfig.set("testArmorDrop" + ".itemQuality", "junk");
		armorItemConfig.set("testArmorDrop" + ".itemDescription", testDescription);
		armorItemConfig.set("testArmorDrop" + ".item", 298);
		armorItemConfig.set("testArmorDrop" + ".armorMax", 1);	
		armorItemConfig.set("testArmorDrop" + ".armorMin", 1);	
		armorItemConfig.set("testArmorDrop" + ".blindnessMax", 2);	
		armorItemConfig.set("testArmorDrop" + ".blindnessMin", 2);	
		armorItemConfig.set("testArmorDrop" + ".blockMax", 3);	
		armorItemConfig.set("testArmorDrop" + ".blockMin", 3);	
		armorItemConfig.set("testArmorDrop" + ".coldDamageMax", 4);	
		armorItemConfig.set("testArmorDrop" + ".coldDamageMin", 4);	
		armorItemConfig.set("testArmorDrop" + ".coldResistMax", 5);	
		armorItemConfig.set("testArmorDrop" + ".coldResistMin", 5);	
		armorItemConfig.set("testArmorDrop" + ".critChanceMax", 6);	
		armorItemConfig.set("testArmorDrop" + ".critChanceMin", 6);	
		armorItemConfig.set("testArmorDrop" + ".damageMax", 7);	
		armorItemConfig.set("testArmorDrop" + ".damageMin", 7);	
		armorItemConfig.set("testArmorDrop" + ".dodgeMax", 8);	
		armorItemConfig.set("testArmorDrop" + ".dodgeMin", 8);	
		armorItemConfig.set("testArmorDrop" + ".fireDamageMax", 9);	
		armorItemConfig.set("testArmorDrop" + ".fireDamageMin", 9);	
		armorItemConfig.set("testArmorDrop" + ".fireResistMax", 10);	
		armorItemConfig.set("testArmorDrop" + ".fireResistMin", 10);	
		armorItemConfig.set("testArmorDrop" + ".goldFindMax", 11);	
		armorItemConfig.set("testArmorDrop" + ".goldFindMin", 11);	
		armorItemConfig.set("testArmorDrop" + ".hpMax", 12);	
		armorItemConfig.set("testArmorDrop" + ".hpMin", 12);	
		armorItemConfig.set("testArmorDrop" + ".hpRegenMax", 13);	
		armorItemConfig.set("testArmorDrop" + ".hpRegenMin", 13);	
		armorItemConfig.set("testArmorDrop" + ".itemFindMax", 14);	
		armorItemConfig.set("testArmorDrop" + ".itemFindMin", 14);
		armorItemConfig.set("testArmorDrop" + ".knockbackMax", 15);	
		armorItemConfig.set("testArmorDrop" + ".knockbackMin", 15);	
		armorItemConfig.set("testArmorDrop" + ".lifestealMax", 16);	
		armorItemConfig.set("testArmorDrop" + ".lifestealMin", 16);	
		armorItemConfig.set("testArmorDrop" + ".manaMax", 17);	
		armorItemConfig.set("testArmorDrop" + ".manaMin", 17);	
		armorItemConfig.set("testArmorDrop" + ".manaRegenMax", 18);	
		armorItemConfig.set("testArmorDrop" + ".manaRegenMin", 18);	
		armorItemConfig.set("testArmorDrop" + ".manastealMax", 19);	
		armorItemConfig.set("testArmorDrop" + ".manastealMin", 19);	
		armorItemConfig.set("testArmorDrop" + ".poisonDamageMax", 20);	
		armorItemConfig.set("testArmorDrop" + ".poisonDamageMin", 20);	
		armorItemConfig.set("testArmorDrop" + ".poisonResistMax", 21);	
		armorItemConfig.set("testArmorDrop" + ".poisonResistMin", 21);	
		armorItemConfig.set("testArmorDrop" + ".reflectionMax", 22);	
		armorItemConfig.set("testArmorDrop" + ".reflectionMin", 22);	
		armorItemConfig.set("testArmorDrop" + ".slownessMax", 23);	
		armorItemConfig.set("testArmorDrop" + ".slownessMin", 23);	
		armorItemConfig.set("testArmorDrop" + ".staminaMax", 24);	
		armorItemConfig.set("testArmorDrop" + ".staminaMin", 24);	
		armorItemConfig.set("testArmorDrop" + ".staminaRegenMax", 25);	
		armorItemConfig.set("testArmorDrop" + ".staminaRegenMin", 25);	
		armorItemConfig.set("testArmorDrop" + ".thornsDamageMax", 26);	
		armorItemConfig.set("testArmorDrop" + ".thornsDamageMin", 26);
		armorItemConfig.set("testArmorDrop" + ".thornsResistMax", 27);	
		armorItemConfig.set("testArmorDrop" + ".thornsResistMin", 27);

		try {
			armorItemConfig.save(configFile);	//Save the file.
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
