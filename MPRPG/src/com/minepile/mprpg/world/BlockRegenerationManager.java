package com.minepile.mprpg.world;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitScheduler;

import com.minepile.mprpg.MPRPG;

/**
 * This class will replace a block with another block.  After the time expires the block will switch back to its previous form.
 * <p>
 * This is great for block regeneration.  Professions, explosions, and more can be tracked here and regenerated after some time.
 * @author Andrew
 *
 */
public class BlockRegenerationManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static BlockRegenerationManager blockRegenManagerInstance = new BlockRegenerationManager();
	
	//Setup regeneration variables
	private static int chestRegenTime = 60 * 15;			//Time it takes for a chest to regenerate. 60 seconds * 15 (15 minutes)
	private static int blockRegenTime = 120; 				//Time it takes for an block to regenerate. 120 = 2 Minutes (60*2)
	private static int blockRegenRate = 5; 					//Default between 90 and 200 seconds intervals.
	private static int blockRegenTick = blockRegenRate * 20;//Time it takes for an block to regenerate.
	private static int blockID = 0;							//The current ID of the hashMaps.  Resets on reload.
	private static int blockIDsRemoved = 0;					//Keep track of how many blocks we have removed from the HashMaps.
	
	private static HashMap<Integer, Material> blockType = new HashMap<Integer, Material>();    //ID > BLOCK - They type of block to regenerate.
	private static HashMap<Integer, Integer>  blockTimeLeft = new HashMap<Integer, Integer>(); //ID > Respawn TimeLeft
	private static HashMap<Integer, Location> blockLoc = new HashMap<Integer, Location>(); 	   //ID > Block Location
	
	//Create instance
	public static BlockRegenerationManager getInstance() {
		return blockRegenManagerInstance;
	}
	
	//Setup BlockRegenerationManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		//Start the timer used to reset blocks.
		blockResetTimer();
	}
	
	/**
	 * This will disable the block regeneration and replace all blocks back to original state.
	 */
	public static void disable() {
		resetAllBlocks();
	}
	
	/**
	 * Resets a block back to its original state.
	 */
	private void blockResetTimer() {
		
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
				//Lets loop through the hashMaps to find any blocks that need to be reverted.
				for (int i = blockIDsRemoved; i < blockID; i++) {
					int timeLeft = blockTimeLeft.get(i);
					
					if(timeLeft <= 0) {
						Block block = blockLoc.get(i).getBlock();
						block.setType(blockType.get(i));

						blockType.remove(i);
						blockTimeLeft.remove(i);
						blockLoc.remove(i);
						
						blockIDsRemoved++;
					} else {
						blockTimeLeft.put(i, timeLeft - blockRegenRate);
					}
				}
            }
        }, 0L, blockRegenTick);
	}
	
	/**
	 * Resets all blocks back to their original state.
	 * <p>
	 * This is used for server reloads.
	 */
	public static void resetAllBlocks() {
		for (int i = blockIDsRemoved; i < blockID; i++) {
			Block block = blockLoc.get(i).getBlock();
			block.setType(blockType.get(i));

			blockType.remove(i);
			blockTimeLeft.remove(i);
			blockLoc.remove(i);
				
			blockIDsRemoved++;
		}
	}
	
	/**
	 * This will set a temporary block in a broken blocks location.
	 * 
	 * @param type The type of block broken.
	 * @param tempBlock The temporary block to replace the broken block.
	 * @param location The XYZ location in the world the block was broken.
	 */
	public static void setBlock(Material type, Material tempBlock, Location location) {
		
		Block block = location.getBlock();
		
		//Replace the broken block with a temporary block, until it has regenerated.
		block.setType(tempBlock);
		
		//Save the block's information.
		blockType.put(blockID, type);
		
		//This will set the respawn times for chests and broken items.
		if (type.equals(Material.CHEST)) {
			blockTimeLeft.put(blockID, chestRegenTime);
		} else {
			blockTimeLeft.put(blockID, blockRegenTime);
		}
		
		blockLoc.put(blockID, location);
		
		//Increment the block counter (used to get the block's ID number.
		blockID++;
	}
}
