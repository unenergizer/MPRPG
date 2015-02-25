package com.minepile.mprpg.equipment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerManager;

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
		}
	}
	
	public static int getLore(HumanEntity entity) {
		
		Player player = (Player) entity;
		
    	player.sendMessage("resetting player");
    	PlayerManager.resetPlayerHashMap(player);
    	
		for (ItemStack item : player.getEquipment().getArmorContents()) {
        	player.sendMessage(ChatColor.GRAY + "Armor loop");
			
        	if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
	        	ItemMeta im = item.getItemMeta();
				player.sendMessage(ChatColor.GRAY + "hasItemMeta == true");
				
				List<String> lore = im.getLore();
				String allLore = lore.toString().toLowerCase();
				
				player.sendMessage(allLore);
				
				Pattern pattern = Pattern.compile("(hp:)[ ][0-9]+");
				Matcher matcher = pattern.matcher(allLore);
				
		        if (matcher.find()) {
		        	String getNums = matcher.group(0).toString();
		        	
		        	player.sendMessage("matcher string: " + getNums);
		        	
		        	Pattern numsOnly = Pattern.compile("[0-9]+");
		        	Matcher numMatch = numsOnly.matcher(getNums);
		        	
		        	player.sendMessage("fist: " + matcher.group(0));
		        	
		        	if (numMatch.find()) {
		        		player.sendMessage("second: " + numMatch.group(0));
		        		player.sendMessage(ChatColor.GREEN + "HP updated!! match: " + numMatch.group(0).toString());
			        	PlayerManager.updatePlayerHashMap((Player) player, "hp", Integer.valueOf(numMatch.group(0)).intValue());
		        	}
		        	
		        	//int match = Integer.valueOf(matcher.group(1));
		        	//player.sendMessage(ChatColor.GREEN + "HP updated!! match: " + Integer.toString(match));
		        	//PlayerManager.updateHashMap((Player) player, "hp", Integer.valueOf(matcher.group(1)).intValue());
		        }
	        } else {
	        	player.sendMessage("resetting player");
	        	PlayerManager.resetPlayerHashMap(player);
	        }
		}
		return 0;
	}
	
	public static void setLore(Player player, Item item) {
		
		ItemStack is = item.getItemStack();
		ItemMeta im = is.getItemMeta();
		boolean cointainsLore = im.hasLore();
		
		if (cointainsLore == false) {
			
			//Setup variables
			int hp = 20;
			int dmg = 5;
			int goldFind = 1;
			
			String itemDescription = "This is a test for armor.";
			
			//Set the item name
			im.setDisplayName(ChatColor.RED + "Armor test");
			
				
			//Set the item lore
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "+" + hp + " health");
			lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "+" + dmg + " damage");
			lore.add(ChatColor.RED + "" + ChatColor.BOLD + "Gold Find: " + goldFind + "%");
			lore.add(" ");//create blank space
			
			
			lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + itemDescription);
			
			
			//Set the item lore
			im.setLore(lore);
			//Set the item meta
			is.setItemMeta(im);
		}
	}
}
