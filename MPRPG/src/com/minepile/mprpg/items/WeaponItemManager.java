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

public class WeaponItemManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static WeaponItemManager weaponItemManagerInstance = new WeaponItemManager();
	static String weaponItemsFilePath = "plugins/MPRPG/items/Weapons.yml";
	
	//Configuration file that holds weapon information.
	static File configFile;
	static FileConfiguration weaponItemConfig;
	
	//Create instance
	public static WeaponItemManager getInstance() {
		return weaponItemManagerInstance;
	}
	
	//Setup Weapon item manager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		//If configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(weaponItemsFilePath)).exists()){
			createConfig();
        } else {
        	//lets load the configuration file.
        	configFile = new File(weaponItemsFilePath);
        	weaponItemConfig =  YamlConfiguration.loadConfiguration(configFile);
        }
	}	
	
	/**
	 * Generates the item that needs to be dropped based on the settings from the configuration file.
	 * <p>
	 * This method is currently only for weapon drops.
	 * 
	 * @param itemName the item name specified inside the configuration file for this item type
	 */
	public static ItemStack makeItem(String itemName) {

		/////////////////////////////
		/// GRAB ITEM FROM CONFIG ///
		/////////////////////////////
		configFile = new File(weaponItemsFilePath);
		weaponItemConfig =  YamlConfiguration.loadConfiguration(configFile);

		String name = itemName.replaceAll("_", " ");
		ItemTier tier = ItemTierManager.getItemTierEnum(weaponItemConfig.getString(itemName + ".itemTier"));
		ItemQuality quality = ItemQualityManager.getItemQualityEnum(weaponItemConfig.getString(itemName + ".itemQuality"));
		List<String> itemDescription = (List<String>) weaponItemConfig.getList(itemName + ".itemDescription");
		int itemNumber = weaponItemConfig.getInt(itemName + ".item");
		int weaponMax = weaponItemConfig.getInt(itemName + ".weaponMax");	
		int weaponMin = weaponItemConfig.getInt(itemName + ".weaponMin");	
		int blindnessMax = weaponItemConfig.getInt(itemName + ".blindnessMax");	
		int blindnessMin = weaponItemConfig.getInt(itemName + ".blindnessMin");	
		int blockMax = weaponItemConfig.getInt(itemName + ".blockMax");	
		int blockMin = weaponItemConfig.getInt(itemName + ".blockMin");	
		int coldDamageMax = weaponItemConfig.getInt(itemName + ".coldDamageMax");	
		int coldDamageMin = weaponItemConfig.getInt(itemName + ".coldDamageMin");	
		int coldResistMax = weaponItemConfig.getInt(itemName + ".coldResistMax");	
		int coldResistMin = weaponItemConfig.getInt(itemName + ".coldResistMin");	
		int critChanceMax = weaponItemConfig.getInt(itemName + ".critChanceMax");	
		int critChanceMin = weaponItemConfig.getInt(itemName + ".critChanceMin");	
		int damageMax = weaponItemConfig.getInt(itemName + ".damageMax");	
		int damageMin = weaponItemConfig.getInt(itemName + ".damageMin");	
		int dodgeMax = weaponItemConfig.getInt(itemName + ".dodgeMax");	
		int dodgeMin = weaponItemConfig.getInt(itemName + ".dodgeMin");	
		int fireDamageMax = weaponItemConfig.getInt(itemName + ".fireDamageMax");	
		int fireDamageMin = weaponItemConfig.getInt(itemName + ".fireDamageMin");	
		int fireResistMax = weaponItemConfig.getInt(itemName + ".fireResistMax");	
		int fireResistMin = weaponItemConfig.getInt(itemName + ".fireResistMin");	
		int goldFindMax = weaponItemConfig.getInt(itemName + ".goldFindMax");	
		int goldFindMin = weaponItemConfig.getInt(itemName + ".goldFindMin");	
		int hpMax = weaponItemConfig.getInt(itemName + ".hpMax");	
		int hpMin = weaponItemConfig.getInt(itemName + ".hpMin");	
		int hpRegenMax = weaponItemConfig.getInt(itemName + ".hpRegenMax");	
		int hpRegenMin = weaponItemConfig.getInt(itemName + ".hpRegenMin");	
		int itemFindMax = weaponItemConfig.getInt(itemName + ".itemFindMax");	
		int itemFindMin = weaponItemConfig.getInt(itemName + ".itemFindMin");
		int knockbackMax = weaponItemConfig.getInt(itemName + ".knockbackMax");	
		int knockbackMin = weaponItemConfig.getInt(itemName + ".knockbackMin");	
		int lifestealMax = weaponItemConfig.getInt(itemName + ".lifestealMax");	
		int lifestealMin = weaponItemConfig.getInt(itemName + ".lifestealMin");	
		int manaMax = weaponItemConfig.getInt(itemName + ".manaMax");	
		int manaMin = weaponItemConfig.getInt(itemName + ".manaMin");	
		int manaRegenMax = weaponItemConfig.getInt(itemName + ".manaRegenMax");	
		int manaRegenMin = weaponItemConfig.getInt(itemName + ".manaRegenMin");	
		int poisonDamageMax = weaponItemConfig.getInt(itemName + ".poisonDamageMax");	
		int poisonDamageMin = weaponItemConfig.getInt(itemName + ".poisonDamageMin");	
		int poisonResistMax = weaponItemConfig.getInt(itemName + ".poisonResistMax");	
		int poisonResistMin = weaponItemConfig.getInt(itemName + ".poisonResistMin");	
		int reflectionMax = weaponItemConfig.getInt(itemName + ".reflectionMax");	
		int reflectionMin = weaponItemConfig.getInt(itemName + ".reflectionMin");	
		int slownessMax = weaponItemConfig.getInt(itemName + ".slownessMax");	
		int slownessMin = weaponItemConfig.getInt(itemName + ".slownessMin");	
		int staminaMax = weaponItemConfig.getInt(itemName + ".staminaMax");	
		int staminaMin = weaponItemConfig.getInt(itemName + ".staminaMin");	
		int staminaRegenMax = weaponItemConfig.getInt(itemName + ".staminaRegenMax");	
		int staminaRegenMin = weaponItemConfig.getInt(itemName + ".staminaRegenMin");	
		int thornsDamageMax = weaponItemConfig.getInt(itemName + ".thornsDamageMax");	
		int thornsDamageMin = weaponItemConfig.getInt(itemName + ".thornsDamageMin");
		int thornsResistMax = weaponItemConfig.getInt(itemName + ".thornsResistMax");	
		int thornsResistMin = weaponItemConfig.getInt(itemName + ".thornsResistMin");

		////////////////////////////
		/// Calculate Item Stats ///
		////////////////////////////
		int weapon = randomInt(weaponMin, weaponMax);
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

		if (weapon > 0) 		{ lore.add(ChatColor.GREEN + "+" + weapon + " weapon"); }
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
	 * This creates the configuration file that will hold data to save weapon information.
	 */
	private static void createConfig() {

		ArrayList<String> testDescription = new ArrayList<String>();
		
		// Adding items to arrayList
		testDescription.add("This is a test item");
		testDescription.add("from the config.");

		configFile = new File(weaponItemsFilePath);
		weaponItemConfig =  YamlConfiguration.loadConfiguration(configFile);

		weaponItemConfig.set("testWeaponDrop", "testWeaponDrop");
		weaponItemConfig.set("testWeaponDrop" + ".itemTier", "t1");
		weaponItemConfig.set("testWeaponDrop" + ".itemQuality", "junk");
		weaponItemConfig.set("testWeaponDrop" + ".itemDescription", testDescription);
		weaponItemConfig.set("testWeaponDrop" + ".item", 268);
		weaponItemConfig.set("testWeaponDrop" + ".weaponMax", 1);	
		weaponItemConfig.set("testWeaponDrop" + ".weaponMin", 1);	
		weaponItemConfig.set("testWeaponDrop" + ".blindnessMax", 2);	
		weaponItemConfig.set("testWeaponDrop" + ".blindnessMin", 2);	
		weaponItemConfig.set("testWeaponDrop" + ".blockMax", 3);	
		weaponItemConfig.set("testWeaponDrop" + ".blockMin", 3);	
		weaponItemConfig.set("testWeaponDrop" + ".coldDamageMax", 4);	
		weaponItemConfig.set("testWeaponDrop" + ".coldDamageMin", 4);	
		weaponItemConfig.set("testWeaponDrop" + ".coldResistMax", 5);	
		weaponItemConfig.set("testWeaponDrop" + ".coldResistMin", 5);	
		weaponItemConfig.set("testWeaponDrop" + ".critChanceMax", 6);	
		weaponItemConfig.set("testWeaponDrop" + ".critChanceMin", 6);	
		weaponItemConfig.set("testWeaponDrop" + ".damageMax", 7);	
		weaponItemConfig.set("testWeaponDrop" + ".damageMin", 7);	
		weaponItemConfig.set("testWeaponDrop" + ".dodgeMax", 8);	
		weaponItemConfig.set("testWeaponDrop" + ".dodgeMin", 8);	
		weaponItemConfig.set("testWeaponDrop" + ".fireDamageMax", 9);	
		weaponItemConfig.set("testWeaponDrop" + ".fireDamageMin", 9);	
		weaponItemConfig.set("testWeaponDrop" + ".fireResistMax", 10);	
		weaponItemConfig.set("testWeaponDrop" + ".fireResistMin", 10);	
		weaponItemConfig.set("testWeaponDrop" + ".goldFindMax", 11);	
		weaponItemConfig.set("testWeaponDrop" + ".goldFindMin", 11);	
		weaponItemConfig.set("testWeaponDrop" + ".hpMax", 12);	
		weaponItemConfig.set("testWeaponDrop" + ".hpMin", 12);	
		weaponItemConfig.set("testWeaponDrop" + ".hpRegenMax", 13);	
		weaponItemConfig.set("testWeaponDrop" + ".hpRegenMin", 13);	
		weaponItemConfig.set("testWeaponDrop" + ".itemFindMax", 14);	
		weaponItemConfig.set("testWeaponDrop" + ".itemFindMin", 14);
		weaponItemConfig.set("testWeaponDrop" + ".knockbackMax", 15);	
		weaponItemConfig.set("testWeaponDrop" + ".knockbackMin", 15);	
		weaponItemConfig.set("testWeaponDrop" + ".lifestealMax", 16);	
		weaponItemConfig.set("testWeaponDrop" + ".lifestealMin", 16);	
		weaponItemConfig.set("testWeaponDrop" + ".manaMax", 17);	
		weaponItemConfig.set("testWeaponDrop" + ".manaMin", 17);	
		weaponItemConfig.set("testWeaponDrop" + ".manaRegenMax", 18);	
		weaponItemConfig.set("testWeaponDrop" + ".manaRegenMin", 18);	
		weaponItemConfig.set("testWeaponDrop" + ".poisonDamageMax", 19);	
		weaponItemConfig.set("testWeaponDrop" + ".poisonDamageMin", 19);	
		weaponItemConfig.set("testWeaponDrop" + ".poisonResistMax", 20);	
		weaponItemConfig.set("testWeaponDrop" + ".poisonResistMin", 20);	
		weaponItemConfig.set("testWeaponDrop" + ".reflectionMax", 21);	
		weaponItemConfig.set("testWeaponDrop" + ".reflectionMin", 21);	
		weaponItemConfig.set("testWeaponDrop" + ".slownessMax", 22);	
		weaponItemConfig.set("testWeaponDrop" + ".slownessMin", 22);	
		weaponItemConfig.set("testWeaponDrop" + ".staminaMax", 23);	
		weaponItemConfig.set("testWeaponDrop" + ".staminaMin", 23);	
		weaponItemConfig.set("testWeaponDrop" + ".staminaRegenMax", 24);	
		weaponItemConfig.set("testWeaponDrop" + ".staminaRegenMin", 24);	
		weaponItemConfig.set("testWeaponDrop" + ".thornsDamageMax", 25);	
		weaponItemConfig.set("testWeaponDrop" + ".thornsDamageMin", 25);
		weaponItemConfig.set("testWeaponDrop" + ".thornsResistMax", 26);	
		weaponItemConfig.set("testWeaponDrop" + ".thornsResistMin", 26);

		try {
			weaponItemConfig.save(configFile);	//Save the file.
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
