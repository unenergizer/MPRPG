package com.minepile.mprpg.professions;

import com.minepile.mprpg.MPRPG;

public class Blacksmithing {
	
	//setup instance variables
	public static MPRPG plugin;
	static Blacksmithing blacksmithingManagerInstance = new Blacksmithing();
	
	//Create instance
	public static Blacksmithing getInstance() {
		return blacksmithingManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	
	
}
