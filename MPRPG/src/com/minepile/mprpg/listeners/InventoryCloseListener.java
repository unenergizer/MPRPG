package com.minepile.mprpg.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.items.LootTableChestManager;

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
			
			if (inv.getType().equals(Material.ENDER_CHEST)) {
				//Play a closing sound.
				player.playSound(player.getLocation(), Sound.CHEST_CLOSE, .5F, 1F);
				player.sendMessage("ender closed chest");
			}
			
			//If the closed inventory is a chest
			if (LootTableChestManager.isChestEmpty(inv) == true) {
				Location loc = LootTableChestManager.getOpenedLootChestLocation(player);
				LootTableChestManager.toggleChestRespawn(inv, loc);
				LootTableChestManager.playerClosedLootChest(player);
			}
		}
	}
}