package com.minepile.mprpg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.gui.ChestMenuManager;
import com.minepile.mprpg.items.ItemLoreFactory;
import com.minepile.mprpg.player.PlayerCharacterManager;

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
				int slot = event.getSlot();
				//ItemStack clickedItem = event.getCurrentItem();

				if (invName.equals("Character_Selection")) {
					//Cancel the item pickup/click (or item move).
					event.setCancelled(true);

					//Toggle player clicking a character slot.
					PlayerCharacterManager.toggleCharacterSelectionInteract(player, slot);
				}

				//Check if the string can be converted to a menu type.  If it can not, then 
				//the event does not need to be canceled.
				if (ChestMenuManager.isInventoryProtectedFromPlayer(player) == true) {
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
				String invName = event.getClickedInventory().getName().replace(" ", "_");

				//Test if the chest is protected.
				ChestMenuManager.protectInventoryFromPlayer(player, invName);

				//If the chest is protected, prevent the player from adding 
				//items to the inventory with shift-click.
				if (ChestMenuManager.isInventoryProtectedFromPlayer(player) == true) {
					//Cancel event if the chest is a protected menu (inventory).
					event.setCancelled(true);
				} else {
					if (PlayerCharacterManager.isPlayerLoaded(player)) {
						//Update armor attributes.
						delayedSlotUpdate(player);
					}
				}
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