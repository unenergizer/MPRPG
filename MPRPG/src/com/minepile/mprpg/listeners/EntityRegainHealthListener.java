package com.minepile.mprpg.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.equipment.LoreManager;

public class EntityRegainHealthListener implements Listener{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public EntityRegainHealthListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityRegainHealth(EntityRegainHealthEvent event) {
		if(event.getEntity() instanceof Player) {
			
			if (((event.getEntity() instanceof Player)) && 
					(event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED)) {
				event.setAmount(event.getAmount() + LoreManager.getRegenBonus((LivingEntity)event.getEntity()));
				if (event.getAmount() <= 0.0D) {
					event.setCancelled(true);
				}
			}
		}
	}

}
