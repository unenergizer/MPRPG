package com.minepile.mprpg.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.monsters.MonsterManager;


public class CreatureSpawnListener  implements Listener {
	
	@SuppressWarnings("unused")
	private MPRPG plugin;
	
	public CreatureSpawnListener(MPRPG plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		
		boolean eventStatus = MonsterManager.getEventStatus();
		
		event.setCancelled(eventStatus);

		//Following line of code names the mob when naturally spawned.
		//MonsterManager.setMobName(event.getEntity());;
	}

}
