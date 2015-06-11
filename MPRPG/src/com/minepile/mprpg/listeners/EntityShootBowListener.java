package com.minepile.mprpg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

import com.minepile.mprpg.MPRPG;

public class EntityShootBowListener implements Listener {

	public static MPRPG plugin;

	@SuppressWarnings("static-access")
	public EntityShootBowListener(MPRPG plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onEntityShootBow(EntityShootBowEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
	}
}
