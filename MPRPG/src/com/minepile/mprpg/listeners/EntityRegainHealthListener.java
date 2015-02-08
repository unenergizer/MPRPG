package com.minepile.mprpg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.managers.PlayerManager;

public class EntityRegainHealthListener implements Listener{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public EntityRegainHealthListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityRegainHealth(EntityRegainHealthEvent event) {
		if(event.getEntity() instanceof Player) {
			
			//cancel the event to do health custom health addition.
			event.setCancelled(true);
			
			Player player = (Player) event.getEntity(); //Player who was attacked
			double amount = event.getAmount();
			
			//Now do manual health removal.
			PlayerManager.setPlayerHealthPoints(player, amount, true);
		}
	}

}
