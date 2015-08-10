package com.minepile.mprpg.items;

import net.md_5.bungee.api.ChatColor;

import com.minepile.mprpg.MPRPG;

public class ItemAttributes {
	
	//setup instance variables
	public static MPRPG plugin;
	static ItemAttributes itemAttributesInstance = new ItemAttributes();
	
	//Create instance
	public static ItemAttributes getInstance() {
		return itemAttributesInstance;
	}
	
	//Setup Item Attributes ENUM.
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	
	
	/**
	 * The type of item attributes that can be found in game.
	 * 
	 * @author Andrew
	 */
	public static enum ItemAttribute {
		
		ARMOR_BONUS (ChatColor.GOLD + ""),
		
		BLINDNESS_BONUS (ChatColor.GOLD + ""),
		BLINDNESS_EFFECT (ChatColor.GOLD + ""),
		
		BLOCK_BONUS (ChatColor.GOLD + ""),
		BLOCK_EFFECT (ChatColor.GOLD + ""),
		
		COLD_DMG (ChatColor.GOLD + ""),
		COLD_EFFECT (ChatColor.GOLD + ""),
		COLD_RESIST (ChatColor.GOLD + ""),
		
		CRITICAL_BONUS (ChatColor.GOLD + ""),
		CRITICAL_EFFECT (ChatColor.GOLD + ""),
		
		DAMAGE_BONUS (ChatColor.GOLD + ""),
		DAMAGE_EFFECT (ChatColor.GOLD + ""),
		
		DODGE_BONUS (ChatColor.GOLD + ""),
		DODGE_EFFECT (ChatColor.GOLD + ""),
		
		FIRE_DMG (ChatColor.GOLD + ""),
		FIRE_EFFECT (ChatColor.GOLD + ""),
		FIRE_RESIST (ChatColor.GOLD + ""),
		
		GOLDFIND_BONUS (ChatColor.GOLD + ""),
		GOLDFIND_EFFECT (ChatColor.GOLD + ""),
		
		HP_BONUS (ChatColor.GOLD + ""),
		HP_REGENERATE (ChatColor.GOLD + ""),
		
		ITEMFIND_BONUS (ChatColor.GOLD + ""),
		ITEMFIND_EFFECT (ChatColor.GOLD + ""),
		
		KNOCKBACK_BONUS (ChatColor.GOLD + ""),
		KNOCKBACK_EFFECT (ChatColor.GOLD + ""),
		
		LIFESTEAL_BONUS (ChatColor.GOLD + ""),
		LIFESTEAL_EFFECT (ChatColor.GOLD + ""),
		
		MANA_BONUS (ChatColor.GOLD + ""),
		MANA_REGENERATE (ChatColor.GOLD + ""),
		MANASTEAL_EFFECT (ChatColor.GOLD + ""),
		
		POISION_DMG (ChatColor.GOLD + ""),
		POISION_EFFECT (ChatColor.GOLD + ""),
		POISION_RESIST (ChatColor.GOLD + ""),
		
		SLOW_BONUS (ChatColor.GOLD + ""),
		SLOW_EFFECT (ChatColor.GOLD + ""),
		
		STAMINA_BONUS (ChatColor.GOLD + ""),
		STAMINA_REGENERATE (ChatColor.GOLD + ""),

		THORN_DMG (ChatColor.GOLD + ""),
		THORN_EFFECT (ChatColor.GOLD + ""),
		THORN_RESIST (ChatColor.GOLD + "");

		private String name;

		ItemAttribute(String s) {
			this.name = s;
		}

		public String getName() {
			return name;
		}
	}
}

