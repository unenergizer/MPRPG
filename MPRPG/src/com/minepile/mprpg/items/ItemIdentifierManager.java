package com.minepile.mprpg.items;

import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;

public class ItemIdentifierManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static ItemIdentifierManager itemIdentifierManagerInstance = new ItemIdentifierManager();
	
	//Create instance
	public static ItemIdentifierManager getInstance() {
		return itemIdentifierManagerInstance;
	}
	
	//Setup AlchemyManager
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
		player.sendMessage(player.getName() + " you have clicked a Item_Identifier NPC!");
	}
}
