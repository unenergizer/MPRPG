package com.minepile.mprpg.equipment;

import com.minepile.mprpg.MPRPG;

public class ArmorManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static ArmorManager armorManagerInstance = new ArmorManager();
	
	//Create instance
	public static ArmorManager getInstance() {
		return armorManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	
	
}
