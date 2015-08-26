package com.minepile.mprpg.entities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.minepile.mprpg.MPRPG;

public class ItemIdentifierManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static ItemIdentifierManager itemIdentifierManagerInstance = new ItemIdentifierManager();
	
	//Holograms
	private static Hologram itemIdentifierNPC;
	
	//Create instance
	public static ItemIdentifierManager getInstance() {
		return itemIdentifierManagerInstance;
	}
	
	//Setup AlchemyManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		setupHolograms();
	}
	
	/**
	 * This will disable this class.
	 */
	public static void disable() {
		removeHolograms();
	}		
	
	/**
	 * This will create a hologram that will display over the Alchemy stand.
	 */
    private static void setupHolograms() {
    	Location npcHologram = new Location(Bukkit.getWorld("world"), 25.5, 82, 6.5);
    	
    	itemIdentifierNPC = HologramsAPI.createHologram(plugin, npcHologram);
    	itemIdentifierNPC.appendTextLine(ChatColor.YELLOW + "" + ChatColor.BOLD + "Item Identifier");
    }
    
    /**
     * This will delete the hologram on server reload or shut down.
     */
    private static void removeHolograms() {
    	itemIdentifierNPC.delete();
    }
}
