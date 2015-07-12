package com.minepile.mprpg.professions;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.minepile.mprpg.MPRPG;

public class Alchemy {
	
	//setup instance variables
	public static MPRPG plugin;
	static Alchemy AlchemyManagerInstance = new Alchemy();
	
	//Holograms
	private static Hologram bankHologram01;
	
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
    	Location bank01 = new Location(Bukkit.getWorld("world"), 47.5, 81.5, -4.5);
    	
    	bankHologram01 = HologramsAPI.createHologram(plugin, bank01);
    	bankHologram01.appendTextLine(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Alchemy Stand");
    }
    
    /**
     * This will delete the hologram on server reload or shut down.
     */
    private static void removeHolograms() {
    	bankHologram01.delete();
    }
}
