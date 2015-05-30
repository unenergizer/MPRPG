package com.minepile.mprpg.items;

import com.minepile.mprpg.MPRPG;

public class CurrencyItemManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static CurrencyItemManager currencyItemManagerInstance = new CurrencyItemManager();
	static String currencyItemsFilePath = "plugins/MPRPG/items/Currency.yml";
	
	//Create instance
	public static CurrencyItemManager getInstance() {
		return currencyItemManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	
	
}
