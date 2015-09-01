package com.minepile.mprpg.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.Inventory;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.items.LootTableChestManager;
import com.minepile.mprpg.world.BlockRegenerationManager;

public class BlockDamageListener implements Listener{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public BlockDamageListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockDamage(BlockDamageEvent event) {
		
		Block block = event.getBlock();
		
		//If the player is damaging a CHEST, it must be a loot chest!
		if (block.getType().equals(Material.CHEST)) {
			event.setInstaBreak(true);
			
			Chest chest = (Chest) block.getState();
			Inventory inv = chest.getInventory();		
			
			//If the block is a empty loot chest, put loot inside it.
			if (LootTableChestManager.isChestEmpty(inv)) {
				LootTableChestManager.toggleChestLoot(inv);
			}
			
			//Setup the broken chest to be regenerated.
			BlockRegenerationManager.setBlock(Material.CHEST, Material.AIR, block.getLocation());
		}
	}
}