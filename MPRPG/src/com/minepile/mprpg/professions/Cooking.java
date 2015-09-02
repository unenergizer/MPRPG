package com.minepile.mprpg.professions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.gui.ChestMenuManager;

public class Cooking {
	
	//setup instance variables
	public static MPRPG plugin;
	static Cooking cookingManagerInstance = new Cooking();
	
	public static Inventory menu;
	
	//Create instance
	public static Cooking getInstance() {
		return cookingManagerInstance;
	}
	
	//Setup CookingManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		createMenu();
	}	
	
	/**
	 * This will build the menus for use with NPC's.
	 */
    private void createMenu() {
		String pageName = "Cooking_Trainer";
    	menu = ChestMenuManager.buildMenuPage(null, pageName);
	}

    /**
     * This will be toggled when a player left-clicks or right clicks a player.
     * 
     * @param player The player who clicked the NPC.
     */
	public static void toggleCitizenInteract(Player player) {
		player.openInventory(menu);
	}
	
	//TODO: Implement this profession!
}
