package com.minepile.mprpg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.gui.ChestMenuManager;
import com.minepile.mprpg.items.ItemLoreFactory;

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
				//Armor was moved into our out of this slot.
				//Lets update the players attributes.
				delayedSlotUpdate(player);
				break;
			case CONTAINER:
				//Name of the inventory.
				String invName = event.getClickedInventory().getName().replace(" ", "_");
				ItemStack clickedItem = event.getCurrentItem();
				
				//Don't allow the player to move items in inventories.
				//The players default inventory is called "container.inventory"
				//Loot chests are called "container.chest"
				//We should not stop them from moving items in that inventory.
				
				/*
				if ((!(invName.equalsIgnoreCase("container.inventory"))) && (!(invName.equalsIgnoreCase("container.chest")))) {
					ItemStack clickedItem = event.getCurrentItem();
					PlayerMenuManager.playerInteractMenu(player, invName, clickedItem);
					event.setCancelled(true);
				}
				*/
				
				//Check if the string can be converted to a menu type.  If it can not, then 
				//the event does not need to be canceled.
				if (ChestMenuManager.getMenuExistsInConfig(invName) == true) {
					//Toggles an item click.
					//ChestMenuManager.toggleChestMenuClick(player, invName, clickedItem);
					
					//Cancel the item pickup/click (or item move).
					event.setCancelled(true);
				}
				
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
				ItemLoreFactory.getInstance().applyHPBonus(player, true);

			} //END Run method.
		}, 5); //(20 ticks = 1 second)
	}
}