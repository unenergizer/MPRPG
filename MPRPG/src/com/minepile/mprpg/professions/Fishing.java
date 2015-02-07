package com.minepile.mprpg.professions;

import com.minepile.mprpg.MPRPG;

public class Fishing {
	
	//setup instance variables
	public static MPRPG plugin;
	static Fishing fishingManagerInstance = new Fishing();
	
	//Create instance
	public static Fishing getInstance() {
		return fishingManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	
	
}
