package com.minepile.mprpg.equipment;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.equipment.ItemQualityManager.ItemQuality;

public class ArmorManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static ArmorManager armorManagerInstance = new ArmorManager();
	
	//Create instance
	public static ArmorManager getInstance() {
		return armorManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	public enum LoreType {
		
		EXP("EXP"),
		DMG("DMG"),
		HP("HP"),
		STRENGTH("STR"),
		INTELLECT("INT"),
		CRIT("CRIT"),
		ITEMFIND("item find"),
		GOLDFIND("gold find");

		private String name;

		LoreType(String s)
		{
			this.name = s;
		}

		public String getName()
		{
			return name;
		}
	}
	
	//TODO: Use or delete.
	public void getHealth (LivingEntity entity) {}
	public void getDamage (LivingEntity entity) {}
	public void getStrength (LivingEntity entity) {}
	public void getIntellect (LivingEntity entity) {}
	public void getCrit (LivingEntity entity) {}
	public void getItemFind (LivingEntity entity) {}
	public void getGoldFind (LivingEntity entity) {}
	
	
	public static void makeArmor(ItemStack item) {
		Material armorType = item.getType();
		
		// TODO Auto-generated method stub
		if (armorType.equals(Material.LEATHER_BOOTS)) {
			setLore(item, ItemQuality.JUNK);
			
		} else if (armorType.equals(Material.LEATHER_CHESTPLATE)) {
			setLore(item, ItemQuality.COMMON);
			
		} else if (armorType.equals(Material.LEATHER_HELMET)) {
			setLore(item, ItemQuality.UNCOMMON);
			
		} else if (armorType.equals(Material.LEATHER_LEGGINGS)) {
			setLore(item, ItemQuality.RARE);
		} else {
			setLore(item, ItemQuality.LEGENDARY);
		}
	}
	
	
	/**
	 * Sets the item's lore. This lore will be read to calculate different attributes.
	 * 
	 * @param item the item that is going to get lore added to it
	 * @param quality the quality of the item. Example: junk, uncommon, common, rare, epic, and legendary.
	 */
	public static void setLore(ItemStack item, ItemQuality quality) {

		ItemMeta im = item.getItemMeta();
		boolean cointainsLore = im.hasLore();
		
		if (cointainsLore == false) {
			
			//Setup variables
			int hp = (int) (Math.random() * 100) + 1;
			int dmg = 5;
			int goldFind = 1;
			
			String itemName = "Armor test";
			String nameFormatting = ItemQualityManager.getStringFormatting(quality);
			String itemDescription = "This is a test for armor.";	//Sets the item's description
			
			//Set the items Name
			im.setDisplayName(nameFormatting + itemName);
			
			//Set the item lore
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ItemQualityManager.getQualityName(quality)); 			//Define the quality of item.
			lore.add("");									//create blank space
			lore.add(ChatColor.DARK_PURPLE + "+" + hp + " HEALTH");
			lore.add(ChatColor.DARK_PURPLE + "+" + dmg + " DAMAGE");
			lore.add(ChatColor.GREEN + "GOLD FIND: " + goldFind + "%");
			lore.add(" ");													//create blank space
			
			//Set the items description
			lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + itemDescription);
			
			
			//Set the item lore
			im.setLore(lore);
			//Set the item meta
			item.setItemMeta(im);
		}
	}
}
