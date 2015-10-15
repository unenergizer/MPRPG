package com.minepile.mprpg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.gui.ChestMenuManager;
import com.minepile.mprpg.gui.PlayerMenuManager;
import com.minepile.mprpg.items.SoulboundManager;
import com.minepile.mprpg.player.PlayerAttributesManager;
import com.minepile.mprpg.player.PlayerCharacterManager;

public class InventoryClickListener implements Listener{

	public static MPRPG plugin;

	@SuppressWarnings("static-access")
	public InventoryClickListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {

			Player player = (Player) event.getWhoClicked();
			SlotType slotType = event.getSlotType();
			ItemStack clickedItem = event.getCurrentItem();

			//Check to see if player is interacting with a chest.
			if(event.getInventory().getType().equals(InventoryType.CHEST) && clickedItem != null) {

				//Name of the inventory.
				String invName = event.getInventory().getTitle();
				int slot = event.getSlot();

				//Close inventories if they are a redstone block.
				if (clickedItem.getType().equals(Material.REDSTONE_BLOCK)) {
					player.closeInventory();
				}

				//Stop player from putting their soulbound items in chests.
				if (PlayerCharacterManager.isPlayerLoaded(player)) {
					if (SoulboundManager.isItemSoulbound(clickedItem)) {

						//Cancel the event.
						event.setCancelled(true);

						//Send the player a message.
						player.sendMessage(ChatColor.RED + "You can not place a Soulbound item in a chest.");

						//Play a sound.
						player.playSound(player.getLocation(), Sound.NOTE_BASS, 1F, 1F);
					}
				}

				//Stop player from putting their compass menu inside chests.
				if (clickedItem.getType().equals(Material.COMPASS)) {
					//Cancel the event.
					event.setCancelled(true);
					
					//Prevent the compass button in the compass menu from making noise when clicked.
					if (event.getView().getBottomInventory().contains(clickedItem)) {
						//Send the player a message.
						player.sendMessage(ChatColor.RED + "You can not place your compass menu in a chest.");

						//Play a sound.
						player.playSound(player.getLocation(), Sound.NOTE_BASS, 1F, .5F);
					}
				}

				if (invName.equalsIgnoreCase("Game Menu")) {
					//Cancel the item pickup/click (or item move).
					event.setCancelled(true);
					
					if (event.getView().getTopInventory().contains(clickedItem)) {
						//Toggle player clicking a character slot.
						PlayerMenuManager.playerInteractMenu(player, "Game Menu", clickedItem);
					}

					//Play error sound if the item clicked was in the players inventory.
					if (event.getView().getBottomInventory().contains(clickedItem) && !clickedItem.getType().equals(Material.COMPASS)) {

						//Send the player a message.
						player.sendMessage(ChatColor.RED + "You can not move items when this menu is open.");

						//Play a sound.
						player.playSound(player.getLocation(), Sound.NOTE_BASS, 1F, 1F);
					}
				}

				if (invName.equals("Character Selection")) {
					//Cancel the item pickup/click (or item move).
					event.setCancelled(true);

					//Toggle player clicking a character slot.
					PlayerCharacterManager.toggleCharacterSelectionInteract(player, slot);
				}

				//Check if the string can be converted to a menu type.  If it can not, then 
				//the event does not need to be canceled.
				if (ChestMenuManager.isPageProtected(invName)) {
					//Toggles an item click.
					//ChestMenuManager.toggleChestMenuClick(player, invName, clickedItem);

					//Cancel the item pickup/click (or item move).
					event.setCancelled(true);
					if (event.getView().getBottomInventory().contains(clickedItem)) {
						//Send the player a message.
						player.sendMessage(ChatColor.RED + "You can not move items when this menu is open.");

						//Play a sound.
						player.playSound(player.getLocation(), Sound.NOTE_BASS, 1F, 1F);
					}
				}
			}

			//Check armor slots.
			if (slotType.equals(SlotType.ARMOR)) {
				//Armor was moved into our out of this slot.
				//Lets update the players attributes.
				delayedSlotUpdate(player);
			}

			//Cancel events from these slot types.
			if (slotType.equals(SlotType.CRAFTING) || slotType.equals(SlotType.FUEL) || slotType.equals(SlotType.RESULT)) {
				event.setCancelled(true);
			}

			//Check if the player is throwing an soulbound item outside their inventory.
			if (slotType.equals(SlotType.OUTSIDE)) {
				//If player throws out an item that is soulbound, delete it.
				if (event.getCursor() != null && event.getCurrentItem() == null) {

					ItemStack item = event.getCursor();
					if (item.getItemMeta() != null && item != null) {
						//Test if the item is soulbound.
						if (SoulboundManager.isItemSoulbound(item)) {

							//Set the item to air.
							event.setCursor(new ItemStack(Material.AIR));

							//Show them the deleted message.
							SoulboundManager.showSoulboundMessage(player);
						}
					}
				}
			}

			//Check the armor slot if the player has shift clicked.
			//This will make check if the player has shift clicked
			//armor into the armor slot.
			if (event.isShiftClick()) {

				if (PlayerCharacterManager.isPlayerLoaded(player)) {
					//Update armor attributes.
					delayedSlotUpdate(player);
				}
			}

		}
	}


	//It seems that the client responds better if we give it time to
	//set the armor, and then read the contents of the armor slots.
	public void delayedSlotUpdate(final Player player) {
		//Lets start a repeating task
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				//Armor was applied, update the
				//players game attributes.
				PlayerAttributesManager.applyNewAttributes(player, true);
			} //END Run method.
		}, 5); //(20 ticks = 1 second)
	}
}