package com.minepile.mprpg.professions;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.Trait;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.minepile.mprpg.MPRPG;

public class Blacksmithing {
	
	//setup instance variables
	public static MPRPG plugin;
	static Blacksmithing blacksmithingManagerInstance = new Blacksmithing();
	
	//NPC
	private static NPC npc;
	
	//Holograms
	private static Hologram bankHologram01;
	
	//Create instance
	public static Blacksmithing getInstance() {
		return blacksmithingManagerInstance;
	}
	
	//Setup BlacksmithingManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		setupAnvilHolograms();
		//spawnNPC();
	}
	
	/**
	 * This will disable this class.
	 */
	public static void disable() {
		removeHolograms();
		//removeNPC();
	}	
	
	/**
	 * This will spawn an NPC.
	 *
	private void spawnNPC() {
		World world = Bukkit.getWorld("world");
		Location bs = new Location(world, 30, 79, 20);
		
		//Spawn blacksmith NPC.
		NPCRegistry registry = CitizensAPI.getNPCRegistry();
		npc = registry.createNPC(EntityType.PLAYER, "Blacksmith");
		npc.spawn(bs);
		npc.addTrait(Class<LookClose extends Trait> trait);
		npc.setProtected(true);
		//npc.data().setPersistent("lookclose", true);
	}*/
	
	//This will remove an NPC.
	private static void removeNPC() {
		npc.destroy();
	}
	
	/**
	 * This will create a hologram that will display over the Blacksmith Anvil.
	 */
    private static void setupAnvilHolograms() {
    	Location bank01 = new Location(Bukkit.getWorld("world"), 28.5, 80.5, 16.5);
    	
    	bankHologram01 = HologramsAPI.createHologram(plugin, bank01);
    	bankHologram01.appendTextLine(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Blacksmith Anvil");
    }
    
    /**
     * This will delete the hologram on server reload or shut down.
     */
    private static void removeHolograms() {
    	bankHologram01.delete();
    }
}
