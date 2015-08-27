package com.minepile.mprpg.professions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.minepile.mprpg.MPRPG;

public class Blacksmithing {
	
	//setup instance variables
	public static MPRPG plugin;
	public static Blacksmithing blacksmithingManagerInstance = new Blacksmithing();
	
	//Holograms
	private static Hologram blacksmithAnvil;
	
	//Create instance
	public static Blacksmithing getInstance() {
		return blacksmithingManagerInstance;
	}
	
	//Setup BlacksmithingManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		setupAllHolograms();
	}
	
	/**
	 * This will disable this class.
	 */
	public static void disable() {
		removeAllHolograms();
	}	

    /**
     * This will be toggled when a player left-clicks or right clicks a player.
     * 
     * @param player The player who clicked the NPC.
     */
	public static void toggleCitizenInteract(Player player) {
		// TODO Auto-generated method stub
		player.sendMessage(player.getName() + " you have clicked a Blacksmith NPC!");
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
