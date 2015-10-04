package com.minepile.mprpg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerCharacterManager;
import com.minepile.mprpg.player.PlayerManager;

public class PlayerExpChangeListener implements Listener{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("unused")
	private static int taskID; 
	
	@SuppressWarnings("static-access")
	public PlayerExpChangeListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerExpChange(PlayerExpChangeEvent event) {
		Player player = event.getPlayer();
		
		//Update the player boss bar.
		delayedBarUpdate(player);
	}
	
	//It seems that the client responds better if we give it time to
	//set the experience, then update the boss bar..
	public void delayedBarUpdate(final Player player) {
		//Lets start a  task
		taskID = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				//Update the player boss bar.
				//PlayerManager.updatePlayerBossbar(player);
				
				int level = player.getLevel();
				int configLevel = (int) PlayerCharacterManager.getPlayerConfigDouble(player, "player.playerLVL");
		      	
				//Player level has changed.
				if (level != configLevel) {
					//Show level up.
					PlayerManager.levelUp(player, level);
				}
			} //END Run method.
		}, 5); //(20 ticks = 1 second)
	}
}