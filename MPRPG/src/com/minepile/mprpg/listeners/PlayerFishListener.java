package com.minepile.mprpg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.professions.Fishing;

public class PlayerFishListener implements Listener{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public PlayerFishListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerFish(PlayerFishEvent event) {
		
		Player player = event.getPlayer();
		
		//Lets get the state of the fishing event.
		//Lets only toggle profession fishing if
		//the CAUGHT_FISH state is triggered.
		//This will prevent profession leveling
		//if the player tries to fish a mob.
		switch(event.getState()){
		case CAUGHT_ENTITY:
			break;
		case CAUGHT_FISH:
			//Fish was caught, lets remove the fist item.
			event.getCaught().remove();
			//Toggle the fishing profession code.
			Fishing.toggleFishing(player);
			break;
		case FAILED_ATTEMPT:
			break;
		case FISHING:
			break;
		case IN_GROUND:
			break;
		default:
			break;
		}
	}
}