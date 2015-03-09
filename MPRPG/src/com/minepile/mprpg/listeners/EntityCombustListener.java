package com.minepile.mprpg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

import com.minepile.mprpg.MPRPG;


public class EntityCombustListener  implements Listener {
	
	@SuppressWarnings("unused")
	private MPRPG plugin;
	
	public EntityCombustListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityCombust(EntityCombustEvent event) {
		//Prevent mobs from catching on fire.
		if (!(event.getEntity() instanceof Player)) {
			event.setCancelled(true);
		}
	}
}
