package com.minepile.mprpg.equipment;

import net.md_5.bungee.api.ChatColor;

import com.minepile.mprpg.MPRPG;

public class ItemQualityManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static ItemQualityManager itemQualityManagerInstance = new ItemQualityManager();
	
	//Create instance
	public static ItemQualityManager getInstance() {
		return itemQualityManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	
	
	/**
	 * The type of item qualities that can be found in game.
	 * 
	 * @author Andrew
	 */
	public static enum ItemQuality {
		
		JUNK (ChatColor.GRAY + ""),
		COMMON (ChatColor.WHITE + ""),
		UNCOMMON (ChatColor.GREEN + ""),
		RARE (ChatColor.BLUE + ""),
		EPIC (ChatColor.DARK_PURPLE + ""),
		LEGENDARY (ChatColor.GOLD + "");

		private String name;

		ItemQuality(String s)
		{
			this.name = s;
		}

		public String getName()
		{
			return name;
		}
	}
	
	/**
	 * Gets the name formatting for use with naming items and coloring the names.
	 * 
	 * @param quality the quality of the item.
	 * @return the String formatting used to dress up an item name.
	 */
	public static String getStringFormatting(ItemQuality quality) {
		String nameFormatting = "";
		
		if (quality.equals(ItemQuality.JUNK)) {
			nameFormatting = ItemQuality.JUNK.getName();
			
		} else if (quality.equals(ItemQuality.COMMON)) {
			nameFormatting = ItemQuality.COMMON.getName();
			
		} else if (quality.equals(ItemQuality.UNCOMMON)) {
			nameFormatting = ItemQuality.UNCOMMON.getName();
			
		} else if (quality.equals(ItemQuality.RARE)) {
			nameFormatting = ItemQuality.RARE.getName();
			
		} else if (quality.equals(ItemQuality.EPIC)) {
			nameFormatting = ItemQuality.EPIC.getName();
			
		} else if (quality.equals(ItemQuality.LEGENDARY)) {
			nameFormatting = ItemQuality.LEGENDARY.getName();
		}
		
		return nameFormatting;
	}
	
	/**
	 * Gets the String of the ItemQuality name.
	 * 
	 * @param quality the quality of the item.
	 * @return the name String of the ItemQuality.
	 */
	public static String getQualityName(ItemQuality quality) {
		String nameFormatting = "";
		
		if (quality.equals(ItemQuality.JUNK)) {
			nameFormatting = ChatColor.GRAY + "" + ChatColor.ITALIC + "Junk";
			
		} else if (quality.equals(ItemQuality.COMMON)) {
			nameFormatting = ChatColor.GRAY + "" + ChatColor.ITALIC + "Common";
			
		} else if (quality.equals(ItemQuality.UNCOMMON)) {
			nameFormatting = ChatColor.GRAY + "" + ChatColor.ITALIC + "Uncommon";
			
		} else if (quality.equals(ItemQuality.RARE)) {
			nameFormatting = ChatColor.GRAY + "" + ChatColor.ITALIC + "Rare";
			
		} else if (quality.equals(ItemQuality.EPIC)) {
			nameFormatting = ChatColor.GRAY + "" + ChatColor.ITALIC + "Epic";
			
		} else if (quality.equals(ItemQuality.LEGENDARY)) {
			nameFormatting = ChatColor.GRAY + "" + ChatColor.ITALIC + "Legendary";
		}
		
		return nameFormatting;
	}
}

