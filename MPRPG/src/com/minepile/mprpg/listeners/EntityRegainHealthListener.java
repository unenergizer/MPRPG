package com.minepile.mprpg.listeners;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerAttributesManager;
import com.minepile.mprpg.player.PlayerHealthTagManager;
import com.minepile.mprpg.player.PlayerManager;

public class EntityRegainHealthListener implements Listener{

	public static MPRPG plugin;

	@SuppressWarnings("static-access")
	public EntityRegainHealthListener(MPRPG plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onEntityRegainHealth(EntityRegainHealthEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			UUID uuid = player.getUniqueId();
			//TODO
			
			
			double currentHP = PlayerManager.getHealthPoints(uuid);
			double maxHealthPoints = PlayerManager.getMaxHealthPoints(uuid);
			
			double healthRegen = PlayerAttributesManager.getHealthPointRegeneration(player);
			double totalRegen = maxHealthPoints * healthRegen;
			double healthPointsFinal = currentHP + totalRegen;
			
			double hpPercent = healthPointsFinal / maxHealthPoints;
			double hpDisplay = hpPercent * 20;
			
			if (healthPointsFinal < maxHealthPoints){
				PlayerManager.setHealthPoints(player, healthPointsFinal);


				if (hpDisplay > 20 && healthPointsFinal < maxHealthPoints) {
					player.setHealth(19);
				} else if (healthPointsFinal >= maxHealthPoints) {
					player.setHealth(20);
				} else {
					player.setHealth(hpDisplay);
				}

				//Shows the player a bar with their stats.
				PlayerManager.displayActionBar(player);

				//Update the players health tag.
				PlayerHealthTagManager.updateHealthTag(player);
				
			} else if (hpDisplay <= 0) {
				event.setCancelled(true);
			}
			
		}
	}
}
