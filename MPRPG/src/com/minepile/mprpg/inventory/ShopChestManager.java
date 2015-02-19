package com.minepile.mprpg.inventory;

import com.minepile.mprpg.MPRPG;

public class ShopChestManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static ShopChestManager shopChestManagerInstance = new ShopChestManager();
	
	//Create instance
	public static ShopChestManager getInstance() {
		return shopChestManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	

}
