package com.minepile.mprpg.chat;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.guild.GuildManager;
import com.minepile.mprpg.player.PlayerCharacterManager;

public class ChatManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static ChatManager chatManagerInstance = new ChatManager();
	
	//MAIN STATS
	static HashMap<String, Integer> healthPoints = new HashMap<String, Integer>();

	//Create instance
	public static ChatManager getInstance() {
		return chatManagerInstance;
	}
	
	//Setup ChatManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Displays the players guild tag in the chat console.
	 * 
	 * @param player The player to assign a guild tag.
	 * @return The players guild tag, if one exists.
	 */
	public static String getGuildTag(Player player) {
		String guildName = PlayerCharacterManager.getPlayerConfigString(player, "guild.name");
		String guildTag = GuildManager.getGuildConfigString(guildName, "guildTag");
		
		if (guildTag != null) {
			return ChatColor.GRAY + "[" + guildTag + "] ";
		} else {
			return "";
		}
	}
	
	/**
	 * Sets a staff members prefix in the chat console.
	 * 
	 * @param player The player to assign a staff tag to.
	 * @return The players staff tag, if one exists.
	 */
	public static String getStaffPrefix(Player player) {
		int isAdmin = (int) PlayerCharacterManager.getPlayerConfigDouble(player, "permissions.admin");
		int isDev = (int) PlayerCharacterManager.getPlayerConfigDouble(player, "permissions.dev");
		int isMod = (int) PlayerCharacterManager.getPlayerConfigDouble(player, "permissions.mod");

		if (player.isOp()) {
			return ChatColor.RED + "" + ChatColor.BOLD + "OP ";
		} else if (isAdmin == 1) {
			return ChatColor.RED + "" + ChatColor.BOLD + "ADMIN ";
		} else if (isDev == 1) {
			return ChatColor.GOLD + "" + ChatColor.BOLD + "DEV ";
		} else if (isMod == 1) {
			return ChatColor.GOLD + "" + ChatColor.BOLD + "MOD ";
		} else {
			return "";
		}
	}
	
}