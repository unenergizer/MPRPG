package com.minepile.mprpg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.gui.ChestMenuManager;
import com.minepile.mprpg.items.LootTableChestManager;

public class InventoryOpenListener implements Listener{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public InventoryOpenListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		Player player = (Player) event.getPlayer();
		
		if (event.getPlayer() instanceof Player) {
			Inventory inv = event.getInventory();
			InventoryType invType = inv.getType();
			String invName = inv.getName();
			
			//Test to see if the inventory is protected.
			//If it is, this method will notify the chestMenuManager 
			//that this inventory needs to be protected.
			ChestMenuManager.protectInventoryFromPlayer(player, invName);
			
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
				//If this chest is a loot chest, lets add loot to it.
				if (invName.equalsIgnoreCase("container.chest")) {
					
					//This method checks if the chest is empty.
					//If it is it will generate some chest loot.
					LootTableChestManager.toggleChestLoot(inv);
				}
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