package com.minepile.mprpg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.equipment.LoreManager;

public class InventoryClickListener implements Listener{

	public static MPRPG plugin;
	
	@SuppressWarnings("unused")
	private static int taskID; 

	@SuppressWarnings("static-access")
	public InventoryClickListener(MPRPG plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();

			switch(event.getSlotType()) {
			case ARMOR:
				delayedSlotUpdate(player);
				break;
			case CONTAINER:
				break;
			case CRAFTING:
				event.setCancelled(true);
				break;
			case FUEL:
				event.setCancelled(true);
				break;
			case OUTSIDE:
				break;
			case QUICKBAR:
				break;
			case RESULT:
				event.setCancelled(true);
				break;
			default:
				break;
			}
			
			if (event.isShiftClick()) {
				delayedSlotUpdate(player);
			}
		}
	}
	
	
	
	//It seems that the client responds better if we give it time to
	//set the armor, and then read the contents of the armor slots.
	public void delayedSlotUpdate(final Player player) {
		//Lets start a repeating task
		taskID = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				LoreManager.applyHpBonus(player);

			} //END Run method.
		}, 10); //(20 ticks = 1 second)
	}
}