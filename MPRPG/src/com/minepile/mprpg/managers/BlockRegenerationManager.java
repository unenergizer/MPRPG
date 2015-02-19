package com.minepile.mprpg.managers;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import com.minepile.mprpg.MPRPG;

public class BlockRegenerationManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static BlockRegenerationManager blockRegenManagerInstance = new BlockRegenerationManager();
	
	//Setup regeneration variables
	public static int blockRegenTime = 10; 				//Time it takes for an block to regenerate.
	public static int blockRegenRate = 5; 				//Default between 90 and 200 seconds.
	public static int blockRegenTick = blockRegenRate * 20; //Time it takes for an block to regenerate.
	public static int blockID = 0;						//The current ID of the hashMaps.  Resets on reload.
	public static int blockIDsRemoved = 0;				//Keep track of how many blocks we have removed from the HashMaps.
	
	public static HashMap<Integer, Material> blockType = new HashMap<Integer, Material>(); //ID > BLOCK
	public static HashMap<Integer, Integer> blockTimeLeft = new HashMap<Integer, Integer>(); //ID > TimeLeft
	public static HashMap<Integer, Integer> blockX = new HashMap<Integer, Integer>(); //ID > X CORD
	public static HashMap<Integer, Integer> blockY = new HashMap<Integer, Integer>(); //ID > Y CORD
	public static HashMap<Integer, Integer> blockZ = new HashMap<Integer, Integer>(); //ID > Z CORD
	
	
	//Create instance
	public static BlockRegenerationManager getInstance() {
		return blockRegenManagerInstance;
	}
	
	//Setup BlockRegenerationManager
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
				//Lets loop through the hashMaps to find any blocks that need to be reverted.
				for (int i = blockIDsRemoved; i < blockID; i++) {
					int timeLeft = blockTimeLeft.get(i);
					
					if(timeLeft <= 0) {
						Block block = Bukkit.getWorld("world").getBlockAt(blockX.get(i), blockY.get(i), blockZ.get(i));
						block.setType(blockType.get(i));

						blockType.remove(i);
						blockTimeLeft.remove(i);
						blockX.remove(i);
						blockY.remove(i);
						blockZ.remove(i);
						
						blockIDsRemoved++;
					} else {
						blockTimeLeft.put(i, timeLeft - blockRegenRate);
					}
				}
            }
        }, 0L, blockRegenTick);
	}
	
	public static void resetAllBlocks() {
		for (int i = blockIDsRemoved; i < blockID; i++) {
			int timeLeft = blockTimeLeft.get(i);
			
			Block block = Bukkit.getWorld("world").getBlockAt(blockX.get(i), blockY.get(i), blockZ.get(i));
			block.setType(blockType.get(i));

			blockType.remove(i);
			blockTimeLeft.remove(i);
			blockX.remove(i);
			blockY.remove(i);
			blockZ.remove(i);
				
			blockIDsRemoved++;
		}
	}
	
	public static void setBlock(Player player, Material type, Material tempBlock, Location location) {
		
		Block block = location.getBlock();
		
		//Replace the broken block with a temporary block, until it has regenerated.
		block.setType(tempBlock);
		
		//Save the block's information.
		blockType.put(blockID, type);
		blockTimeLeft.put(blockID, blockRegenTime);
		blockX.put(blockID, location.getBlockX());
		blockY.put(blockID, location.getBlockY());
		blockZ.put(blockID, location.getBlockZ());
		
		//Increment the block counter (used to get the block's ID number.
		blockID++;
	}
}
