package com.minepile.mprpg.chat;

import com.minepile.mprpg.MPRPG;

public class gameTipManager {

	//setup instance variables
	public static MPRPG plugin;
	static gameTipManager gameTipManagerInstance = new gameTipManager();

	//Create instance
	public static gameTipManager getInstance() {
		return gameTipManagerInstance;
	}

	//Setup GameTipManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		//Start the timer used to reset blocks.
		startTipMessages();
	}	
	
	public void startTipMessages() {
		
	}
}
