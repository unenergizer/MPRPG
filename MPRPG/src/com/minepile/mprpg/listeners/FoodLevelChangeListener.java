package com.minepile.mprpg.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerCharacterManager;

public class FoodLevelChangeListener  implements Listener {

	public static MPRPG plugin;

	@SuppressWarnings("static-access")
	public FoodLevelChangeListener(MPRPG plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		Entity entity = event.getEntity();

		if (entity instanceof Player) {
			Player player = (Player) entity;
			
			if (PlayerCharacterManager.isPlayerLoaded(player) == false) {
				//Lets prevent players who have not chosen their character or class from loosing food level.
				event.setCancelled(true);
			}
		}
	}
}
