package com.minepile.mprpg.items;

import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;

public class MerchantManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static MerchantManager merchantManagerInstance = new MerchantManager();

	//Create instance
	public static MerchantManager getInstance() {
		return merchantManagerInstance;
	}
	
	//Setup MerchantManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	

    /**
     * This will be toggled when a player left-clicks or right clicks a player.
     * 
     * @param player The player who clicked the NPC.
     */
	public static void toggleCitizenInteract(Player player) {
		// TODO Auto-generated method stub
		player.sendMessage(player.getName() + " you have clicked a Merchant NPC!");
	}
}
