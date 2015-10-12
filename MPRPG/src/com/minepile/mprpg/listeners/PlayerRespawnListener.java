package com.minepile.mprpg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerAttributesManager;
import com.minepile.mprpg.player.PlayerManager;

public class PlayerRespawnListener implements Listener {
	
	public static MPRPG plugin;
	
	@SuppressWarnings("unused")
	private static int taskID;
	
	@SuppressWarnings("static-access")
	public PlayerRespawnListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		delayedUpdate(player);
		
	}
	
	public void delayedUpdate(final Player player) {
		//Lets start a repeating task
		taskID = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				
				//TODO: ItemLoreFactory.getInstance().applyHPBonus(player, false);
				PlayerAttributesManager.applyNewAttributes(player, false);
				PlayerManager.teleportPlayerToSpawn(player);

			} //END Run method.
		}, 5); //(20 ticks = 1 second)
	}
}
