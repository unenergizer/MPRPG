package com.minepile.mprpg.listeners;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.managers.MessageManager;
import com.minepile.mprpg.player.PlayerManager;

public class PlayerJoinListener implements Listener {
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public PlayerJoinListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		String playerName = player.getName();
		
		//Load the player
		PlayerManager.setupPlayer(player);
		
		//Show the user the welcome message.
		MessageManager.displayWelcomeMessage(player);
		
		//Prevent the global user login message.
		//"PlayerName" has joined the game.
		event.setJoinMessage("");
		
		//Send player specific/private join message.
		//This message is not displayed to all users.
		player.sendMessage(ChatColor.GRAY + "Welcome " + playerName + "!");
	}
}
