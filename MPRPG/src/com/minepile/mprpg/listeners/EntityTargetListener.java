package com.minepile.mprpg.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.items.ItemLoreFactory;

public class EntityTargetListener implements Listener {

	public static MPRPG plugin;

	@SuppressWarnings("static-access")
	public EntityTargetListener(MPRPG plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onEntityTarget(EntityTargetEvent event) {
		if ((event.getEntity() instanceof LivingEntity)) {
			LivingEntity entity = (LivingEntity)event.getTarget();

			if (entity instanceof Player) {
				if (!entity.hasMetadata("NPC")) {
					ItemLoreFactory.getInstance().applyHPBonus((Player) entity, false);
				}
			}
		}
	}
}
