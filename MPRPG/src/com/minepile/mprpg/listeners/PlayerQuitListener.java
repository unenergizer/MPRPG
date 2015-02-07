package com.minepile.mprpg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.managers.PlayerManager;

public class PlayerQuitListener  implements Listener {
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public PlayerQuitListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		//Removes the player from the game.
		//Removes players from hashMaps.
		PlayerManager.removePlayer(player);
		
		//Set empty quit message.
		//Do this to prevent chat spam.
		event.setQuitMessage("");
	}
}