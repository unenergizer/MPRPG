package com.minepile.mprpg.entities;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerManager;

public class InnKeeperManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static InnKeeperManager instance = new InnKeeperManager();
	
	//Create instance
	public static InnKeeperManager getInstance() {
		return instance;
	}
	
	//Setup InnKeeperManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	
    /**
     * This will be toggled when a player left-clicks or right clicks a player.
     * 
     * @param player The player who clicked the NPC.
     */
	public static void toggleCitizenInteract(Player player, Player npc) {
		String playerName = player.getName();
		double hp = PlayerManager.getMaxHealthPoints(playerName);
		
		//Send player a message.
		player.sendMessage(ChatColor.GRAY + npc.getDisplayName() + ChatColor.DARK_GRAY + ": "
				+ ChatColor.WHITE + "Welcome back, " + playerName + "!  Let me heal your wounds.");
		player.sendMessage(ChatColor.GREEN + "You have been healed.");
		
		//Heal and feed player.
		PlayerManager.setPlayerHitPoints(player, hp);
		player.setFoodLevel(20);
	}
}
