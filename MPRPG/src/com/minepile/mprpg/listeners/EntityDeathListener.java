package com.minepile.mprpg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerManager;

public class EntityDeathListener implements Listener{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public EntityDeathListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		//Not doing anything here yet.
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			
			PlayerManager.teleportPlayerToSpawn(player);
		} else {
			event.getDrops().clear();
		}
	}
}