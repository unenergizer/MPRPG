package com.minepile.mprpg.professions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.minepile.mprpg.MPRPG;

public class Alchemy {
	
	//setup instance variables
	public static MPRPG plugin;
	static Alchemy AlchemyManagerInstance = new Alchemy();
	
	//Holograms
	private static Hologram alchemyStand;
	
	//Create instance
	public static Alchemy getInstance() {
		return AlchemyManagerInstance;
	}
	
	//Setup AlchemyManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		setupAlchemyHolograms();
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
    private static void setupAlchemyHolograms() {
    	Location alcStandHologram = new Location(Bukkit.getWorld("world"), 47.5, 81, -4.5);
    	
    	alchemyStand = HologramsAPI.createHologram(plugin, alcStandHologram);
    	alchemyStand.appendTextLine(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Alchemy Stand");
    }
    
    /**
     * This will delete the hologram on server reload or shut down.
     */
    private static void removeHolograms() {
    	alchemyStand.delete();
    }
    


    /**
     * This will be toggled when a player left-clicks or right clicks a player.
     * 
     * @param player The player who clicked the NPC.
     */
	public static void toggleCitizenInteract(Player player) {
		// TODO Auto-generated method stub
		player.sendMessage(player.getName() + " you have clicked a Alchemy NPC!");
	}
}
