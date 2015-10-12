package com.minepile.mprpg.listeners;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerCharacterManager;
import com.minepile.mprpg.player.PlayerManager;


public class PlayerToggleSprintListener  implements Listener {

	@SuppressWarnings("unused")
	private MPRPG plugin;

	public PlayerToggleSprintListener(MPRPG plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerToggleSprint(PlayerToggleSprintEvent event) {
		Player player = event.getPlayer();
		if (PlayerCharacterManager.isPlayerLoaded(player)) {
			UUID uuid = player.getUniqueId();
			
			int sprintCost = 5;
			double stamina = PlayerManager.getStaminaPoints(uuid) - sprintCost;
			
			PlayerManager.setStaminaPoints(player, stamina);
		}
	}
}
