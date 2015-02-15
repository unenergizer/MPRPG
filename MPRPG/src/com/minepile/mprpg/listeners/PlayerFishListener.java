package com.minepile.mprpg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.professions.Fishing;

public class PlayerFishListener implements Listener{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public PlayerFishListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerFish(PlayerFishEvent event) {
		Player player = event.getPlayer();
			
		if (event.getCaught() != null) {
			//Toggle the fishing manager code.
			Fishing.toggleFishing(player);
		}
	}
}