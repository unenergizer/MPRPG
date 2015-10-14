package com.minepile.mprpg.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.gui.PlayerMenuManager;
import com.minepile.mprpg.items.SoulboundManager;
import com.minepile.mprpg.player.PlayerCharacterManager;

public class PlayerDropItemListener  implements Listener{

	public static MPRPG plugin;

	@SuppressWarnings("static-access")
	public PlayerDropItemListener(MPRPG plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {

		ItemStack itemDrop = event.getItemDrop().getItemStack();
		Material material = itemDrop.getType();
		Player player = event.getPlayer();

		//TODO: Cancel mainMenu drops (compass).
		if (material.equals(Material.COMPASS)) {

			//This will remove the item, so they can get a new one later.
			event.getItemDrop().remove();

			//Now lets replace it with a new one.
			PlayerMenuManager.givePlayerMenu(player);
		}

		//If player throws out an item that is soulbound, delete it.
		if (itemDrop.getItemMeta() != null && itemDrop != null) {
			if (PlayerCharacterManager.isPlayerLoaded(player)) {
				if (SoulboundManager.isItemSoulbound(itemDrop)) {

					//This will remove the item.
					event.getItemDrop().remove();

					//Show them the deleted message.
					SoulboundManager.showSoulboundMessage(player);
				}
			}
		}
	}
}
