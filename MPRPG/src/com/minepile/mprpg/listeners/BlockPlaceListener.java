package com.minepile.mprpg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.managers.MessageManager;

public class BlockPlaceListener implements Listener{
	
	public static MPRPG plugin;
	public boolean allPlayersCanPlaceBlocks = true;
	
	@SuppressWarnings("static-access")
	public BlockPlaceListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		
		if (allPlayersCanPlaceBlocks == false) {
			//Cancel the event.
			event.setCancelled(false);
			
			
			//show debug message
			if (MessageManager.canShowAdminDebugMessage() == true) {
				player.sendMessage(MessageManager.selectMessagePrefix("debug") 
						+ "You can not place blocks.");
			}
		}
		
	}

}