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
	
	public static enum ItemQuality {
		
		JUNK (ChatColor.GRAY + "" + ChatColor.ITALIC + "Junk"),
		COMMON (ChatColor.WHITE + "" + ChatColor.ITALIC + "Common"),
		UNCOMMON (ChatColor.GREEN + "" + ChatColor.ITALIC + "Uncommon"),
		RARE (ChatColor.BLUE + "" + ChatColor.ITALIC + "Rare"),
		EPIC (ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Epic"),
		LEGENDARY (ChatColor.GOLD + "" + ChatColor.ITALIC + "Legendary");

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
}

