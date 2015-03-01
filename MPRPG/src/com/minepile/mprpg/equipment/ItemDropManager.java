package com.minepile.mprpg.equipment;

import com.minepile.mprpg.MPRPG;

public class ItemDropManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static ItemDropManager itemDropManagerInstance = new ItemDropManager();
	
	//Create instance
	public static ItemDropManager getInstance() {
		return itemDropManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}

	public static void toggleItemDrops(String entityName) {
		
		String name = entityName.toLowerCase();
		if (name.startsWith("weak")){
			
			getCopperDrop(15,12);
		} else if (name.startsWith("peon")){

			getCopperDrop(15,12);
		} else if (name.startsWith("lazy")){

			getCopperDrop(15,12);
		} else {

			getCopperDrop(15,12);
		}
	}	
	
	public static int getCopperDrop(int maxGold, int diceSize) {
		int dropChance = 50;
		int money = (int) (Math.random() * maxGold);
		int diceRoll = (int) (Math.random() * diceSize);
		int dropPercent = (100 * diceRoll) / diceSize;
		
		if (dropPercent > dropChance) {
			return money;
		} else {
			return 0;
		}
	}
	
	public static int getSilverDrop(int maxGold, int diceSize) {
		int dropChance = 50;
		int money = (int) (Math.random() * maxGold);
		int diceRoll = (int) (Math.random() * diceSize);
		int dropPercent = (100 * diceRoll) / diceSize;
		
		if (dropPercent > dropChance) {
			return money;
		} else {
			return 0;
		}
	}
	
	public static int getGoldDrop(int maxGold, int diceSize) {
		int dropChance = 50;
		int money = (int) (Math.random() * maxGold);
		int diceRoll = (int) (Math.random() * diceSize);
		int dropPercent = (100 * diceRoll) / diceSize;
		
		if (dropPercent > dropChance) {
			return money;
		} else {
			return 0;
		}
	}
}
