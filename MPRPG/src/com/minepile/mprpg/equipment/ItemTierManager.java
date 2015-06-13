package com.minepile.mprpg.equipment;

import net.md_5.bungee.api.ChatColor;

import com.minepile.mprpg.MPRPG;

public class ItemTierManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static ItemTierManager itemQualityManagerInstance = new ItemTierManager();
	
	//Create instance
	public static ItemTierManager getInstance() {
		return itemQualityManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	
	
	/**
	 * The item's Tier in game.
	 * 
	 * @author Andrew
	 */
	public static enum ItemTier {
		
		T1 (ChatColor.GRAY + ""),
		T2 (ChatColor.WHITE + ""),
		T3 (ChatColor.GREEN + ""),
		T4 (ChatColor.BLUE + ""),
		T5 (ChatColor.DARK_PURPLE + ""),
		T6 (ChatColor.GOLD + "");

		private String name;

		ItemTier(String s) {
			this.name = s;
		}

		public String getName() {
			return name;
		}
	}
	
	/**
	 * Gets the name formatting for use with naming items and coloring the names.
	 * 
	 * @param quality the quality of the item.
	 * @return the String formatting used to dress up an item name.
	 */
	/*
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
	*/
	/**
	 * Gets the String of the ItemQuality name.
	 * 
	 * @param quality the quality of the item.
	 * @return the name String of the ItemQuality.
	 */
	/*
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
	*/
	
	/**
	 * Gets the ItemTier Enum from a String.
	 * 
	 * @param tier the String name for a ItemQualty Enum.
	 * @return the ItemQuality Enum.
	 */
	public static ItemTier getItemTierEnum(String tier) {
		ItemTier itemTierEnum = null;
		
		if (tier.equalsIgnoreCase("t1")) {
			itemTierEnum = ItemTier.T1;
			
		} else if (tier.equalsIgnoreCase("t2")) {
			itemTierEnum = ItemTier.T2;
			
		} else if (tier.equalsIgnoreCase("t3")) {
			itemTierEnum = ItemTier.T3;
			
		} else if (tier.equalsIgnoreCase("t4")) {
			itemTierEnum = ItemTier.T4;
			
		} else if (tier.equalsIgnoreCase("t5")) {
			itemTierEnum = ItemTier.T5;
			
		} else if (tier.equalsIgnoreCase("t6")) {
			itemTierEnum = ItemTier.T6;
		}
		
		return itemTierEnum;
	}
}

