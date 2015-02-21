package com.minepile.mprpg.equipment;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minepile.mprpg.MPRPG;

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
	
	//TODO: Use or delete.
	public void getHealth (LivingEntity entity) {}
	public void getDamage (LivingEntity entity) {}
	public void getStrength (LivingEntity entity) {}
	public void getIntellect (LivingEntity entity) {}
	public void getCrit (LivingEntity entity) {}
	public void getItemFind (LivingEntity entity) {}
	public void getGoldFind (LivingEntity entity) {}
	
	public static void makeArmor(Player player, Item item) {
		EntityType armorType = item.getType();
		setLore(player, item);
		// TODO Auto-generated method stub
		if (armorType.equals(Material.LEATHER_BOOTS)) {
			setLore(player, item);
		} else if (armorType.equals(Material.LEATHER_CHESTPLATE)) {
			setLore(player, item);
		} else if (armorType.equals(Material.LEATHER_HELMET)) {
			setLore(player, item);
		} else if (armorType.equals(Material.LEATHER_LEGGINGS)) {
			setLore(player, item);
		} else {
			player.sendMessage("makeArmor() this should not happen.");
		}
	}
	
	public static void setLore(Player player, Item item) {
		
		player.sendMessage("setLore() invoked");
		
		ItemStack is = item.getItemStack();
		ItemMeta im = is.getItemMeta();
		boolean cointainsLore = im.hasLore();
		
		if (cointainsLore == false) {
			
			player.sendMessage("setLore() invoked - containsLore == null");
			
			//Setup variables
			int hp = 100;
			int dmg = 5;
			int goldFind = 1;
			
			String itemDescription = "This is a test for armor.";
			
			//Set the item name
			im.setDisplayName(ChatColor.RED + "Armor test");
			
				
			//Set the item lore
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "HP: " + ChatColor.RESET +
					ChatColor.LIGHT_PURPLE + hp );
			lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "DMG: " + 
					ChatColor.RESET + ChatColor.BLUE + dmg);
			lore.add(ChatColor.RED + "" + ChatColor.BOLD + "Gold Find: " + 
					ChatColor.GRAY + " " + goldFind + "%");
			lore.add(" ");//create blank space
			
			
			lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + itemDescription);
			
			
			//Set the item lore
			im.setLore(lore);
			//Set the item meta
			is.setItemMeta(im);
		} else {
			player.sendMessage("ContainsLore?");
		}
	}
}
