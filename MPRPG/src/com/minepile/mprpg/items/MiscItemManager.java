package com.minepile.mprpg.items;

import com.minepile.mprpg.MPRPG;

public class MiscItemManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static MiscItemManager itemManagerInstance = new MiscItemManager();
	
	//Create instance
	public static MiscItemManager getInstance() {
		return itemManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	
	
}
