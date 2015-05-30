package com.minepile.mprpg.entities;

import com.minepile.mprpg.MPRPG;

public class EntityTierManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static EntityTierManager entityTierManagerInstance = new EntityTierManager();
	
	//Create instance
	public static EntityTierManager getInstance() {
		return entityTierManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	
	
	public static enum EntityTier {
		
		T1_ZOMBIE_WEAK (""),
		T1_ZOMBIE_STRONG (""),
		T1_ZOMBIE_ELITE (""),
		T1_ZOMBIE_BOSS (""),
		
		T1_SPIDER_WEAK (""),
		T1_SPIDER_STRONG (""),
		T1_SPIDER_ELITE (""),
		T1_SPIDER_BOSS ("");

		private String name;

		EntityTier(String s)
		{
			this.name = s;
		}

		public String getName()
		{
			return name;
		}
	}
}

