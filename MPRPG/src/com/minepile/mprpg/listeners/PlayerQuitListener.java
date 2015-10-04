package com.minepile.mprpg.listeners;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerCharacterManager;
import com.minepile.mprpg.player.PlayerManager;

public class PlayerQuitListener  implements Listener {

	public static MPRPG plugin;

	@SuppressWarnings("static-access")
	public PlayerQuitListener(MPRPG plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		if (PlayerCharacterManager.isPlayerLoaded(player)) {
			//Save players last health

			double logoutHP = PlayerManager.getHealthPoints(uuid);
			double logoutMaxHP = PlayerManager.getMaxHealthPoints(uuid);
			double logoutStamina = PlayerManager.getStaminaPoints(uuid);
			double logoutMaxStamina = PlayerManager.getMaxStaminaPoints(uuid);
			double logoutMana = PlayerManager.getManaPoints(uuid);
			double logoutMaxMana = PlayerManager.getMaxManaPoints(uuid);
			
			float experience = player.getExp();
			
			double x = player.getLocation().getX();
			double y = player.getLocation().getY();
			double z = player.getLocation().getZ();

			PlayerCharacterManager.setPlayerConfigDouble(player, "player.logout.hp", logoutHP);
			PlayerCharacterManager.setPlayerConfigDouble(player, "player.logout.maxhp", logoutMaxHP);
			PlayerCharacterManager.setPlayerConfigDouble(player, "player.logout.stamina", logoutStamina);
			PlayerCharacterManager.setPlayerConfigDouble(player, "player.logout.maxStamina", logoutMaxStamina);
			PlayerCharacterManager.setPlayerConfigDouble(player, "player.logout.mana", logoutMana);
			PlayerCharacterManager.setPlayerConfigDouble(player, "player.logout.maxMana", logoutMaxMana);

			PlayerCharacterManager.setPlayerConfigDouble(player, "player.playerEXP", experience);
			
			PlayerCharacterManager.setPlayerConfigDouble(player, "player.logout.x", x);
			PlayerCharacterManager.setPlayerConfigDouble(player, "player.logout.y", y);
			PlayerCharacterManager.setPlayerConfigDouble(player, "player.logout.z", z);
			
			//Removes the player from the game.
			//Removes players from hashMaps.
			PlayerManager.removePlayer(player);
			PlayerCharacterManager.removePlayer(player);
		} else {
			//This will remove the player from the HashMaps if they make a new character,
			//but logout before they select a class.
			PlayerCharacterManager.removePlayer(player);
		}
		
		//Set empty quit message.
		//Do this to prevent chat spam.
		event.setQuitMessage("");
	}
}
