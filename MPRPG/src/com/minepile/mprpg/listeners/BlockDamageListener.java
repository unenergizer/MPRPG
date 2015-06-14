package com.minepile.mprpg.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

import com.minepile.mprpg.MPRPG;

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
		}
	}
}