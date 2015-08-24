package com.minepile.mprpg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.items.ItemLoreFactory;
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
			String playerName = player.getName();

			double currentHP = PlayerManager.getHealthPoints(playerName);
			double maxHealthPoints = PlayerManager.getMaxHealthPoints(playerName);
			
			double healthRegen = PlayerManager.getBaseHealthRegenRate() + ItemLoreFactory.getInstance().getHealthPointsRegenerate(player);
			double healthPointsFinal = currentHP + healthRegen;
			
			double hpBarPercent = (20 * healthPointsFinal) / maxHealthPoints;		

			if (healthPointsFinal < maxHealthPoints){
				PlayerManager.setPlayerHitPoints(player, healthPointsFinal);


				if ((hpBarPercent + 1) >= 18 && healthPointsFinal < maxHealthPoints) {
					player.setHealth(18);
				} else if (healthPointsFinal >= maxHealthPoints) {
					player.setHealth(20);
				} else {
					player.setHealth(hpBarPercent);
				}

				//Shows the player a bar with their stats.
				PlayerManager.displayActionBar(player);

				//Update the players health tag.
				PlayerHealthTagManager.updateHealthTag(player);
			} else if (hpBarPercent <= 0) {
				event.setCancelled(true);
			}
		}
	}
}
