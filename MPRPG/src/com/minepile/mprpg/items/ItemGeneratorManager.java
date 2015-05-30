package com.minepile.mprpg.items;

import com.minepile.mprpg.MPRPG;

public class ItemGeneratorManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static ItemGeneratorManager itemGeneratorManagerInstance = new ItemGeneratorManager();
	
	//Create instance
	public static ItemGeneratorManager getInstance() {
		return itemGeneratorManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	
	
}
