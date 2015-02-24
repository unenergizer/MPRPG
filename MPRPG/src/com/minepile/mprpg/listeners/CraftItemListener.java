package com.minepile.mprpg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.equipment.LoreManager;

public class CraftItemListener implements Listener {
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public CraftItemListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onCraftItem(CraftItemEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) {
			return;
		}
		for (ItemStack item : event.getInventory().getContents()) {
			if (!LoreManager.canUse((Player)event.getWhoClicked(), item))
			{
				event.setCancelled(true);
				return;
			}
		}
	}
}
