package com.minepile.mprpg.listeners;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.chat.MessageManager;
import com.minepile.mprpg.equipment.LoreManager;
import com.minepile.mprpg.player.PlayerHealthTagManager;
import com.minepile.mprpg.player.PlayerManager;

public class PlayerJoinListener implements Listener {
	
	public static MPRPG plugin;
	
	@SuppressWarnings("unused")
	private static int taskID; 
	
	@SuppressWarnings("static-access")
	public PlayerJoinListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		String playerName = player.getName();
		
		//Show the user the welcome message.
		MessageManager.displayWelcomeMessage(player);
		
		//Prevent the global user login message.
		//"PlayerName" has joined the game.
		event.setJoinMessage("");
		
		//Send player specific/private join message.
		//This message is not displayed to all users.
		player.sendMessage(ChatColor.GRAY + "Welcome " + playerName + "!");
		
		delayedUpdate(player);
	}
	
	//It seems that the client responds better if we give it time to
	//set the experience, then update the players armor..
	public void delayedUpdate(final Player player) {
		//Lets start a  task
		taskID = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				
				//Load the player
				PlayerManager.setupPlayer(player);
				
				//Update the players armor.
				LoreManager.applyHpBonus(player, false);
				PlayerHealthTagManager.updateHealthTag(player);
				
				//Add the players health tag below their name.
				PlayerHealthTagManager.addPlayer(player);
				PlayerHealthTagManager.updateHealthTag(player);
				
			} //END Run method.
		}, 5); //(20 ticks = 1 second)
	}
}
