package com.minepile.mprpg.managers;

import java.util.HashMap;

import com.minepile.mprpg.MPRPG;

public class ChatManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static ChatManager chatManagerInstance = new ChatManager();
	
	//MAIN STATS
	static HashMap<String, Integer> healthPoints = new HashMap<String, Integer>();

	
	//Create instance
	public static ChatManager getInstance() {
		return chatManagerInstance;
	}
	
	//Setup ChatManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	
	
}