package com.minepile.mprpg.chat;

import com.minepile.mprpg.MPRPG;

public class GameTipManager {

	//setup instance variables
	public static MPRPG plugin;
	static GameTipManager gameTipManagerInstance = new GameTipManager();

	//Create instance
	public static GameTipManager getInstance() {
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
