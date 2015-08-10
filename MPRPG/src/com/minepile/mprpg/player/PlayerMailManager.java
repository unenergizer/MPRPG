package com.minepile.mprpg.player;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.minepile.mprpg.MPRPG;

public class PlayerMailManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static PlayerMailManager playerMailManagerInstance = new PlayerMailManager();
	
	 //Holograms
	static Hologram bankHologram01;
	
	//Create instance
	public static PlayerMailManager getInstance() {
		return playerMailManagerInstance;
	}
	
	//Setup PlayerMailManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		setupMailHolograms();
	}
	
	/**
	 * This will disable this class.
	 */
	public static void disable() {
		removeHolograms();
	}	
	
	/**
	 * This creates a Hologram to show over the Mailbox.
	 */
    public static void setupMailHolograms() {
    	Location bank01 = new Location(Bukkit.getWorld("world"), 21.5, 81.6, -8.5);
    	
    	bankHologram01 = HologramsAPI.createHologram(plugin, bank01);
    	bankHologram01.appendTextLine(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Mailbox");
    }
    
    /**
     * This will delete the Hologram when server reloads or shuts down.
     */
    public static void removeHolograms() {
    	bankHologram01.delete();
    }
}
