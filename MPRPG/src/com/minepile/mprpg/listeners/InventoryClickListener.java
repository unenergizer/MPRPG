package com.minepile.mprpg.listeners;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.equipment.LoreManager;
import com.minepile.mprpg.player.PlayerMenuManager;

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
				String invName = event.getClickedInventory().getName();
				
				if (invName.startsWith(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Game Menu")) {
					player.sendMessage("Game Menu - Click toggle");
					ItemStack clickedItem = event.getCurrentItem();
					PlayerMenuManager.playerInteractMenu(player, invName, clickedItem);
				}
				event.setCancelled(true);
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
			
			//Check the armor slot if the player has shift clicked.
			//This will make check if the player has shift clicked
			//armor into the armor slot.
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
				LoreManager.applyHpBonus(player, true);

			} //END Run method.
		}, 5); //(20 ticks = 1 second)
	}
}