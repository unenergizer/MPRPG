package com.minepile.mprpg.chat;

import com.minepile.mprpg.MPRPG;

public class DiceRollManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static DiceRollManager diceRollManagerInstance = new DiceRollManager();
	
	//Create instance
	public static DiceRollManager getInstance() {
		return diceRollManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	public static int onDiceRoll(int diceSize) {
		if (diceSize < 10000) {
			int roll = (int) (Math.random() * diceSize);
			return roll;
		} else {
			int fixedRoll = (int) (Math.random() * 10000);
			return fixedRoll;
		}
	}
}
