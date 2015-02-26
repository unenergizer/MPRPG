package com.minepile.mprpg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

import com.minepile.mprpg.MPRPG;

public class InventoryOpenListener implements Listener{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public InventoryOpenListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		if (event.getPlayer() instanceof Player) {
			InventoryType invType = event.getInventory().getType();
			
			switch(invType) {
			case ANVIL:
				event.setCancelled(true);
				break;
			case BEACON:
				event.setCancelled(true);
				break;
			case BREWING:
				event.setCancelled(true);
				break;
			case CHEST:
				break;
			case CRAFTING:
				event.setCancelled(true);
				break;
			case CREATIVE:
				event.setCancelled(true);
				break;
			case DISPENSER:
				event.setCancelled(true);
				break;
			case DROPPER:
				event.setCancelled(true);
				break;
			case ENCHANTING:
				event.setCancelled(true);
				break;
			case ENDER_CHEST:
				break;
			case FURNACE:
				event.setCancelled(true);
				break;
			case HOPPER:
				event.setCancelled(true);
				break;
			case MERCHANT:
				event.setCancelled(true);
				break;
			case PLAYER:
				break;
			case WORKBENCH:
				event.setCancelled(true);
				break;
			default:
				break;
			
			}
		}
	}
}