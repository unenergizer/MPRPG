package com.minepile.mprpg.player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;

public class PlayerHearthStoneManager {

	//setup instance variables
	public static MPRPG plugin;
	static PlayerHearthStoneManager hearthStoneManagerInstance = new PlayerHearthStoneManager();

	private static ConcurrentHashMap<UUID, Integer> timeLeft = new ConcurrentHashMap<UUID, Integer>();
	private static int hearthStoneTime = 1; //How many minutes it takes to reset the hearthstone.
	
	//Create instance
	public static PlayerHearthStoneManager getInstance() {
		return hearthStoneManagerInstance;
	}

	//Setup HearthStoneManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
			public void run() {
				manageTime();
			}
		}, 0, 20);
	}

	public static void useHearthStone(Player player) {
		UUID uuid = player.getUniqueId();

		if (canUseHearthStone(player)) {
			PlayerManager.teleportPlayerToSpawn(player);
			timeLeft.put(uuid, 60 * hearthStoneTime);

		} else {
			int time = timeLeft.get(uuid);

			//Send error message.
			player.sendMessage(ChatColor.RED + "You can not use this for another " + time + " seconds.");

			//Play a sound.
			player.playSound(player.getLocation(), Sound.NOTE_BASS, 1F, 1F);
		}
	}

	private static boolean canUseHearthStone(Player player) {
		UUID uuid = player.getUniqueId();

		if (!timeLeft.containsKey(uuid)) {
			return true;
		} else {
			return false;
		}
	}

	private static void manageTime() {
		//Loop through the players.
		for (Player players: Bukkit.getOnlinePlayers()) {
			UUID uuid = players.getUniqueId();	

			if (timeLeft.containsKey(uuid)) {
				int time = timeLeft.get(uuid);

				//Adjust time as needed.

				if (time <= 1) {
					timeLeft.remove(uuid);
				} else {
					timeLeft.put(uuid, time - 1);
				}

			}
		}
	}
}
