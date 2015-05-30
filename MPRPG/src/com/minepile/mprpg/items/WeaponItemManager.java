package com.minepile.mprpg.items;

import com.minepile.mprpg.MPRPG;

public class WeaponItemManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static WeaponItemManager weaponItemManagerInstance = new WeaponItemManager();
	static String weaponItemsFilePath = "plugins/MPRPG/items/Weapons.yml";
	
	//Create instance
	public static WeaponItemManager getInstance() {
		return weaponItemManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	
	
}
