package com.minepile.mprpg.items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.minepile.mprpg.MPRPG;

public class MerchantManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static MerchantManager merchantManagerInstance = new MerchantManager();
    
    //Holograms
  	private static Hologram merchantTrainer;

	//Create instance
	public static MerchantManager getInstance() {
		return merchantManagerInstance;
	}
	
	//Setup MerchantManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		//Add holograms.
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
		player.sendMessage(player.getName() + " you have clicked a Merchant NPC!");
	}
	
	/**
	 * This will create a hologram that will display over the Blacksmith Anvil.
	 */
    private static void setupAllHolograms() {
    	Location trainerLoc = new Location(Bukkit.getWorld("world"), 27.5, 82, -13.5);
    	
    	merchantTrainer = HologramsAPI.createHologram(plugin, trainerLoc);
    	merchantTrainer.appendTextLine(ChatColor.YELLOW + "" + ChatColor.BOLD + "Item Merchant");
    }
	
    /**
     * This will delete the hologram on server reload or shut down.
     */
    private static void removeAllHolograms() {
    	merchantTrainer.delete();
    }
}
