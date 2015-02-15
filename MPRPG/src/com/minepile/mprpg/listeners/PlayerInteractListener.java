package com.minepile.mprpg.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.managers.BankChestManager;
import com.minepile.mprpg.managers.PlayerMenuManager;

public class PlayerInteractListener implements Listener{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public PlayerInteractListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		
		 if ((event.getAction().equals(Action.RIGHT_CLICK_AIR)) || (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			 if (event.getItem() != null) {
	             if (event.getItem().getType().equals(Material.COMPASS)) {
	                 player.openInventory(PlayerMenuManager.mainMenu);
	             }
			 }
		 }
		 if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && block.getType().equals(Material.ENDER_CHEST)) {
			 
			 //Cancel opening the ender chest and show a custom chest.
			 event.setCancelled(true);
			 
			 //Show custom chest.
			 player.openInventory(BankChestManager.getBank(player));
		 }
     }
}
