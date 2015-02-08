package com.minepile.mprpg.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.minepile.mprpg.MPRPG;

public class EntityDeathListener implements Listener{
	
	public static MPRPG plugin;
	public boolean allPlayersCanPlaceBlocks = true;
	
	@SuppressWarnings("static-access")
	public EntityDeathListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		//Not doing anything here yet.
		
	}
}