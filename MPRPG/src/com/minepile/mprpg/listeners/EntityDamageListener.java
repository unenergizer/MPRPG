package com.minepile.mprpg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.managers.PlayerManager;

public class EntityDamageListener implements Listener{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public EntityDamageListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			
			//cancel the event to do health custom health removal.
			event.setCancelled(true);
			
			Player player = (Player) event.getEntity(); //Player who was attacked
			double damage = event.getDamage();
			
			//Now do manual health removal.
			PlayerManager.setPlayerHealthPoints(player, damage, false);
		}
	}

}
