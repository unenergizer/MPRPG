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
	static Hologram bankHologram01;
	
	//Create instance
	public static Alchemy getInstance() {
		return AlchemyManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		setupAlchemyHolograms();
	}	
	
    public static void setupAlchemyHolograms() {
    	Location bank01 = new Location(Bukkit.getWorld("world"), 47.5, 81.5, -4.5);
    	
    	bankHologram01 = HologramsAPI.createHologram(plugin, bank01);
    	bankHologram01.appendTextLine(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Alchemy Stand");
    }
    
    public static void removeHolograms() {
    	bankHologram01.delete();
    }
}
