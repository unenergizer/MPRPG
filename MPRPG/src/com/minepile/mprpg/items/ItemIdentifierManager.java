package com.minepile.mprpg.items;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;

public class ItemIdentifierManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static ItemIdentifierManager itemIdentifierManagerInstance = new ItemIdentifierManager();
	
	//Create instance
	public static ItemIdentifierManager getInstance() {
		return itemIdentifierManagerInstance;
	}
	
	//Setup AlchemyManager
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
		//Send player a message.
		player.sendMessage(ChatColor.GRAY + npc.getDisplayName() + ChatColor.DARK_GRAY + ": "
				+ ChatColor.WHITE + "Umm, " + playerName + " you have no items to identify.");
	}
}
