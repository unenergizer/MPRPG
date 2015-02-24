package com.minepile.mprpg.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.equipment.LoreManager;

public class EntityTargetListener implements Listener {
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public EntityTargetListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityTarget(EntityTargetEvent event) {
	    if ((event.getEntity() instanceof LivingEntity)) {
	      LivingEntity e = (LivingEntity)event.getEntity();
	      
	      LoreManager.applyHpBonus(e);
	    }
	}
}
