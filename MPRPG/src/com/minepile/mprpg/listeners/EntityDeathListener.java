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
		//Not doing anything here yet.
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			player.setGameMode(GameMode.CREATIVE);
			//Heal the player
			player.setHealth(PlayerManager.getMaxHealthPoints(player));
			
			PlayerManager.teleportPlayerToSpawn(player);
			player.setGameMode(GameMode.SURVIVAL);
			new BukkitRunnable() {
				@Override
		    	public void run() {

				}
			}.runTaskLater(plugin, 1); //run after 1 tick
		}
	}
}