package com.minepile.mprpg.items;

import com.minepile.mprpg.MPRPG;

public class ArmorItemManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static ArmorItemManager armorItemManagerInstance = new ArmorItemManager();
	
	//Create instance
	public static ArmorItemManager getInstance() {
		return armorItemManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	
	
}
