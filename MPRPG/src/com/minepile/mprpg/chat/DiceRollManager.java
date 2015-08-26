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
	
	/**
	 * Displays a virtual dice roll in the players chat console.
	 * 
	 * @param diceSize How big the dice should be.
	 * @return The number the dice landed on after it was rolled.
	 */
	public static int onDiceRoll(int diceSize) {
		if (diceSize < 10000) {
			int roll = (int) (Math.random() * diceSize) + 1;
			return roll;
		} else {
			int fixedRoll = (int) (Math.random() * 10000) + 1;
			return fixedRoll;
		}
	}
}
