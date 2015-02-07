package com.minepile.mprpg.managers;

import com.minepile.mprpg.MPRPG;

public class BankChestManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static BankChestManager weaponManagerInstance = new BankChestManager();
	
	//Create instance
	public static BankChestManager getInstance() {
		return weaponManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	

}
