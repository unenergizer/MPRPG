package com.minepile.mprpg.items;

import com.minepile.mprpg.MPRPG;

public class ConsumableItemManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static ConsumableItemManager consumableItemManagerInstance = new ConsumableItemManager();
	static String consumableItemsFilePath = "plugins/MPRPG/items/Consumable.yml";
	
	//Create instance
	public static ConsumableItemManager getInstance() {
		return consumableItemManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	
	
}
