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
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		setupMailHolograms();
	}	
	
    public static void setupMailHolograms() {
    	Location bank01 = new Location(Bukkit.getWorld("world"), 21.5, 81.5, -8.5);
    	
    	bankHologram01 = HologramsAPI.createHologram(plugin, bank01);
    	bankHologram01.appendTextLine(ChatColor.YELLOW + "" + ChatColor.BOLD + "Mailbox");
    }
    
    public static void removeMailHolograms() {
    	bankHologram01.delete();
    }
}
