package com.minepile.mprpg.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerManager;

public class EntityDeathListener implements Listener{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public EntityDeathListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			String playerName = player.getName();
			
			//Heal the player
			player.setHealth(20);
			player.setFoodLevel(20);
			PlayerManager.setPlayerHitPoints(player, PlayerManager.getMaxHealthPoints(playerName));
			
			player.setGameMode(GameMode.CREATIVE);
			
			PlayerManager.teleportPlayerToSpawn(player);
			player.setGameMode(GameMode.SURVIVAL);

			//Heal the player
			PlayerManager.setPlayerHitPoints(player, PlayerManager.getMaxHealthPoints(playerName));
			new BukkitRunnable() {
				@Override
		    	public void run() {

				}
			}.runTaskLater(plugin, 1); //run after 1 tick
		} else {
			event.getDrops().clear();
		}
	}
}