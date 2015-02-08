package com.minepile.mprpg.managers;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import com.minepile.mprpg.MPRPG;

public class OreRegenerationManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static OreRegenerationManager oreRegenManagerInstance = new OreRegenerationManager();
	
	//Setup regeneration variables
	public static int oreRegenTime = 10; 				//Time it takes for an ore to regenerate.
	public static int oreRegenRate = 5; 				//Default between 90 and 200 seconds.
	public static int oreRegenTick = oreRegenRate * 20; //Time it takes for an ore to regenerate.
	public static int oreID = 0;						//The current ID of the hashMaps.  Resets on reload.
	public static int oreIDsRemoved = 0;				//Keep track of how many ores we have removed from the HashMaps.
	
	public static HashMap<Integer, Material> oreType = new HashMap<Integer, Material>(); //ID > ORE
	public static HashMap<Integer, Integer> oreTimeLeft = new HashMap<Integer, Integer>(); //ID > TimeLeft
	public static HashMap<Integer, Integer> oreX = new HashMap<Integer, Integer>(); //ID > X CORD
	public static HashMap<Integer, Integer> oreY = new HashMap<Integer, Integer>(); //ID > Y CORD
	public static HashMap<Integer, Integer> oreZ = new HashMap<Integer, Integer>(); //ID > Z CORD
	
	
	//Create instance
	public static OreRegenerationManager getInstance() {
		return oreRegenManagerInstance;
	}
	
	//Setup OreRegenerationManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		//Start the timer used to reset blocks.
		resetBlock();
	}
	
	public void resetBlock() {
		
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
				//Lets loop through the hashMaps to find any ores that need to be reverted.
				for (int i = oreIDsRemoved; i < oreID; i++) {
					int timeLeft = oreTimeLeft.get(i);
					
					if(timeLeft <= 0) {
						Block block = Bukkit.getWorld("world").getBlockAt(oreX.get(i), oreY.get(i), oreZ.get(i));
						block.setType(oreType.get(i));

						oreType.remove(i);
						oreTimeLeft.remove(i);
						oreX.remove(i);
						oreY.remove(i);
						oreZ.remove(i);
						
						oreIDsRemoved++;
					} else {
						oreTimeLeft.put(i, timeLeft - oreRegenRate);
					}
				}
            }
        }, 0L, oreRegenTick);
	}
	
	public static void setBlock(Player player, Material type, Location location) {
		
		Block block = location.getBlock();
		
		//Set the mine ore as stone.
		block.setType(Material.STONE);
		
		//Save the ore's information.
		oreType.put(oreID, type);
		oreTimeLeft.put(oreID, oreRegenTime);
		oreX.put(oreID, location.getBlockX());
		oreY.put(oreID, location.getBlockY());
		oreZ.put(oreID, location.getBlockZ());
		
		//Increment the ore counter (used to get the ore's ID number.
		oreID++;
	}
}
