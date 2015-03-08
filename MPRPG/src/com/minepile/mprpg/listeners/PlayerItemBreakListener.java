package com.minepile.mprpg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.equipment.LoreManager;
import com.minepile.mprpg.player.PlayerHealthTagManager;


public class PlayerItemBreakListener  implements Listener {
	
	@SuppressWarnings("unused")
	private MPRPG plugin;
	
	public PlayerItemBreakListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerItemBreak(PlayerItemBreakEvent event) {
		Player player = event.getPlayer();
		
		LoreManager.applyHpBonus(player, true);
		PlayerHealthTagManager.updateHealthTag(player);
	}
}
