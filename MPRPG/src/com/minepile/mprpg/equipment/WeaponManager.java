package com.minepile.mprpg.equipment;

import com.minepile.mprpg.MPRPG;

public class WeaponManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static WeaponManager weaponManagerInstance = new WeaponManager();
	
	//Create instance
	public static WeaponManager getInstance() {
		return weaponManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	

}
