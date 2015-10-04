package com.minepile.mprpg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerCharacterManager;
import com.minepile.mprpg.player.PlayerManager;

public class PlayerQuitListener  implements Listener {

	public static MPRPG plugin;

	@SuppressWarnings("static-access")
	public PlayerQuitListener(MPRPG plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		if (PlayerCharacterManager.isPlayerLoaded(player)) {
			
			//Save the players character
			PlayerCharacterManager.saveCharacter(player);
			
			//Removes the player from the game.
			//Removes players from hashMaps.
			PlayerManager.removePlayer(player);
			PlayerCharacterManager.removePlayer(player);
		} else {
			//This will remove the player from the HashMaps if they make a new character,
			//but logout before they select a class.
			PlayerCharacterManager.removePlayer(player);
		}
		
		//Set empty quit message.
		//Do this to prevent chat spam.
		event.setQuitMessage("");
	}
}
