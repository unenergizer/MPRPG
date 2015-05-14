package com.minepile.mprpg.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import com.minepile.mprpg.MPRPG;


public class EntityChangeBlockListener  implements Listener {
	
	@SuppressWarnings("unused")
	private MPRPG plugin;
	
	public EntityChangeBlockListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityChangeBlock(EntityChangeBlockEvent event) {
		//Cancel entity block changes.
		event.setCancelled(true);
	}
}
