package com.minepile.mprpg.professions;

import com.minepile.mprpg.MPRPG;

public class Herbalism {
	
	//setup instance variables
	public static MPRPG plugin;
	static Herbalism herbalismManagerInstance = new Herbalism();
	
	//Create instance
	public static Herbalism getInstance() {
		return herbalismManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	
	
}
