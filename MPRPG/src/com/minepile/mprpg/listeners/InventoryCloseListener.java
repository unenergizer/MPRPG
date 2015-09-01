package com.minepile.mprpg.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.gui.ChestMenuManager;

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
			Inventory inv = event.getInventory();
			inv.getHolder();
			
			
			if (inv.getType().equals(Material.ENDER_CHEST)) {
				//Play a closing sound.
				player.playSound(player.getLocation(), Sound.CHEST_CLOSE, .5F, 1F);
			}
			
			//If the player is damaging a CHEST, it must be a loot chest!
			if (inv.getType().equals(Material.CHEST)) {
				//Setup the broken chest to be regenerated.
				//BlockRegenerationManager.setBlock(Material.CHEST, Material.AIR, block.getLocation());
			}
			
			//Remove player from protectedInventoryChestHashMap if Possible.
			if (ChestMenuManager.isInventoryProtectedFromPlayer(player)) {
				ChestMenuManager.removePlayerFromProtectedInventory(player);
			}
		}
	}
}