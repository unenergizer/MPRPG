package com.minepile.mprpg.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.items.LoreManager;

public class InventoryCloseListener implements Listener{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public InventoryCloseListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (event.getPlayer() instanceof Player) {
			Player player = (Player) event.getPlayer();
			
			LoreManager.applyHpBonus(player, true);
			
			if (event.getInventory().getType().equals(Material.ENDER_CHEST)) {
				//Play a closing sound.
				player.playSound(player.getLocation(), Sound.CHEST_CLOSE, .5F, 1F);
			}
		}
	}
}