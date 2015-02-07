package com.minepile.mprpg.professions;

import com.minepile.mprpg.MPRPG;

public class Cooking {
	
	//setup instance variables
	public static MPRPG plugin;
	static Cooking cookingManagerInstance = new Cooking();
	
	//Create instance
	public static Cooking getInstance() {
		return cookingManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	
	
}
