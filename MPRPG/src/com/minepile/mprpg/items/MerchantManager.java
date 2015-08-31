package com.minepile.mprpg.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.gui.ChestMenuManager;

public class MerchantManager {
	
	//setup instance variables
	public static MPRPG plugin;
	public static MerchantManager merchantManagerInstance = new MerchantManager();

	public static Inventory menu, armor, weapons, food, potions, sell;
	
	//Create instance
	public static MerchantManager getInstance() {
		return merchantManagerInstance;
	}
	
	//Setup MerchantManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		createMenu();
	}	

    /**
     * This will be toggled when a player left-clicks or right clicks a player.
     * 
     * @param player The player who clicked the NPC.
     */
	public static void toggleCitizenInteract(Player player) {
		player.openInventory(menu);
	}
	
	/**
	 * This will build the menus for use with NPC's.
	 */
    private void createMenu() {
		String pageName = "Item_Merchant";
    	menu = ChestMenuManager.buildMenuPage(null, pageName);
	}
}
