package com.minepile.mprpg.items;

import com.minepile.mprpg.MPRPG;

public class MobDropTablesManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static MobDropTablesManager mobDropTablesManagerInstance = new MobDropTablesManager();
	
	//Create instance
	public static MobDropTablesManager getInstance() {
		return mobDropTablesManagerInstance;
	}
	
	//Setup Drop Tables Manager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	//TODO:
	/*
	 	Possible loot table configuration:
	 	
	 	tableName:   (junk drops, epic drops, etc)
	 		tableName.armor = id1, id2, id3, etc
	 		tableName.weapon = id1, id2, id3, etc
	 		tableName.consumables = id1, id2, id3, etc
	 		tableName.currency = id1
	 		tableName.misc = id1
	 */
	
}
