package com.minepile.mprpg.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.managers.PlayerMenuManager;

public class PlayerDropItemListener  implements Listener{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public PlayerDropItemListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		
		Item itemDrop = event.getItemDrop();
		Material item = itemDrop.getItemStack().getType();
		Player player = event.getPlayer();
		
		//TODO: Cancel mainMenu drops (compass).
		if (item.equals(Material.COMPASS)) {
			
			//Cancel the drop. Prevents item from hitting the ground.
			event.setCancelled(true);
			
			//Now lets replace it with a new one.
			PlayerMenuManager.givePlayerMenu(player);
		}
	}
}
