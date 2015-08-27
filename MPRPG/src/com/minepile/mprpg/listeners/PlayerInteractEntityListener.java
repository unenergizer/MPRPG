package com.minepile.mprpg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.entities.CitizensManager;

public class PlayerInteractEntityListener implements Listener{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public PlayerInteractEntityListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {

		if (event.getRightClicked() instanceof Player) {
			Player npc = (Player) event.getRightClicked();
			
			//Check to see if entity is a Citizens NPC.
			if (npc.hasMetadata("NPC")) {
				
				Player player = event.getPlayer();
				CitizensManager.onCitizenInteract(player, npc);
			}
		}
	}
}