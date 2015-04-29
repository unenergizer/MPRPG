package com.minepile.mprpg.professions;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.minepile.mprpg.MPRPG;

public class Blacksmithing {
	
	//setup instance variables
	public static MPRPG plugin;
	static Blacksmithing blacksmithingManagerInstance = new Blacksmithing();
	
	//Holograms
	static Hologram bankHologram01;
	
	//Create instance
	public static Blacksmithing getInstance() {
		return blacksmithingManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		setupAnvilHolograms();
	}	
	
    public static void setupAnvilHolograms() {
    	Location bank01 = new Location(Bukkit.getWorld("world"), 28.5, 80.5, 16.5);
    	
    	bankHologram01 = HologramsAPI.createHologram(plugin, bank01);
    	bankHologram01.appendTextLine(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Blacksmith Anvil");
    }
    
    public static void removeHolograms() {
    	bankHologram01.delete();
    }
}
