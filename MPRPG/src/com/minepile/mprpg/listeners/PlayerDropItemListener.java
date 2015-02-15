package com.minepile.mprpg.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.minepile.mprpg.MPRPG;

public class PlayerDropItemListener  implements Listener{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public PlayerDropItemListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerDropItemEvent event) {
		
		Material itemDrop = event.getItemDrop().getItemStack().getType();
		
		//TODO: Cancel mainMenu drops (compass).
		if (itemDrop.equals(Material.COMPASS)) {
			event.setCancelled(true);
		}
	}
}
