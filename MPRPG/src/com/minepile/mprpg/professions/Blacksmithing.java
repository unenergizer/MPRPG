package com.minepile.mprpg.professions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.gui.ChestMenuManager;

public class Blacksmithing {
	
	//setup instance variables
	public static MPRPG plugin;
	public static Blacksmithing blacksmithingManagerInstance = new Blacksmithing();
	
	//Holograms
	private static Hologram blacksmithAnvil;
	
	public static Inventory menu, anvilMenu;
	
	//Create instance
	public static Blacksmithing getInstance() {
		return blacksmithingManagerInstance;
	}
	
	//Setup BlacksmithingManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		setupAllHolograms();
		
		createMenu();
	}
	
	/**
	 * This will disable this class.
	 */
	public static void disable() {
		removeAllHolograms();
	}	
	
	/**
	 * This will build the menus for use with NPC's.
	 */
    private void createMenu() {
		String pageName = "Blacksmith_Trainer";
		String anvilPageName = "Blacksmith_Anvil";
    	menu = ChestMenuManager.buildMenuPage(null, pageName);
    	anvilMenu = ChestMenuManager.buildMenuPage(null, anvilPageName);
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
     * This will be toggled when a player left-clicks or right clicks an anvil.
     * 
     * @param player The player who clicked the Anvil.
     */
	public static void toggleAnvilInteract(Player player) {
		player.openInventory(anvilMenu);
	}
	
	/**
	 * This will create a hologram that will display over the Blacksmith Anvil.
	 */
    private static void setupAllHolograms() {
    	Location anvilLoc = new Location(Bukkit.getWorld("world"), 28.5, 80.5, 16.5);
    	blacksmithAnvil = HologramsAPI.createHologram(plugin, anvilLoc);
    	blacksmithAnvil.appendTextLine(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Blacksmith Anvil");
    }
    
    /**
     * This will delete the hologram on server reload or shut down.
     */
    private static void removeAllHolograms() {
    	blacksmithAnvil.delete();
    }
}
