package com.minepile.mprpg.professions;

import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;

public class Cooking {
	
	//setup instance variables
	public static MPRPG plugin;
	static Cooking cookingManagerInstance = new Cooking();
	
	//Create instance
	public static Cooking getInstance() {
		return cookingManagerInstance;
	}
	
	//Setup CookingManager
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
		player.sendMessage(player.getName() + " you have clicked a Cooking NPC!");
	}
	
	//TODO: Implement this profession!
}
