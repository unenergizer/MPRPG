package com.minepile.mprpg.managers;

import java.util.HashMap;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;

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
	
	public static String getClanTag(Player player) {
		String clanTag = PlayerManager.getPlayerConfigString(player, "permissions.clan.tag");
		
		if (clanTag != null) {
			return ChatColor.GRAY + "[" + clanTag.toUpperCase() + "] ";
		} else {
			return "";
		}
	}
	
	public static String getStaffPrefix(Player player) {
		int isAdmin = PlayerManager.getPlayerConfigInt(player, "permissions.admin");
		int isDev = PlayerManager.getPlayerConfigInt(player, "permissions.dev");
		int isMod = PlayerManager.getPlayerConfigInt(player, "permissions.mod");

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