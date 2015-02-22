package com.minepile.mprpg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.equipment.ArmorManager;

public class InventoryCloseListener implements Listener{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public InventoryCloseListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		event.getPlayer().sendMessage("InventoryCloseEvent");
		if (event.getPlayer() instanceof Player) {
			event.getPlayer().sendMessage("InventoryCloseEvent");
			ArmorManager.getLore(event.getPlayer());
		}
	}
}