package com.minepile.mprpg.chat;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;

public class MessageManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static MessageManager messageManagerInstance = new MessageManager();
	
	//Message Manager specific variables
	private static boolean showAdminDebugMessage = false;
	private static boolean showDebugMessage = true;
	
	//Create instance
	public static MessageManager getInstance() {
		return messageManagerInstance;
	}
	
	//Setup MessageManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Prefixes: DEBUG, GLOBAL, HELP, PM, TIP, & USER
	 * 
	 * @param the prefix the message should use
	 */
	public static String selectMessagePrefix(String prefix) {
		if (prefix == "debug") {
			return ChatColor.RED + "" + ChatColor.BOLD + "MP-RPG: " + ChatColor.RESET;
		} else {
			return ChatColor.BOLD + "MP-RPG: " + ChatColor.RESET;
		}
	}
	
	/**
	 * Displays the welcome message everyone see's when they login to the server.
	 * 
	 * @param player The player the welcome message will be displayed too
	 */
	public static void displayWelcomeMessage(Player player) {
		
		//Insert Blank lines to clear previous chat from other servers
		//or the players last session.
		player.sendMessage("");
		player.sendMessage("");
		player.sendMessage("");
		player.sendMessage("");
		player.sendMessage("");
		player.sendMessage("");
		player.sendMessage("");
		player.sendMessage("");
		player.sendMessage("");
		player.sendMessage("");
		player.sendMessage("");
		player.sendMessage("");
		player.sendMessage("");
		player.sendMessage("");
		
		String welcomeMessage = " \n" + " \n" + ChatColor.GOLD + "                        " + ChatColor.BOLD 
				+ "MinePile: " + ChatColor.WHITE + "" + ChatColor.BOLD + "RPGMMO " + plugin.getPluginVersion() +  " \n" + 
				ChatColor.RESET + ChatColor.GRAY + "                          http://www.MinePile.com/" + " \n" + 
				" \n" + " \n";
		player.sendMessage(welcomeMessage);
	}
	
	/**
	 * EXP level formatting for chat.
	 * 
	 * @param exp The amount of experience added.
	 * @param currentExp The experience amount before new total.
	 * @param expGoal The experience needed to level.
	 * @return Fancy String displaying colored experience level.
	 */
	public static String showEXPLevel(int exp, int currentExp, int expGoal){
		int expPercent = ((100 * currentExp) / expGoal);
	
		return ChatColor.GRAY + "" + ChatColor.BOLD + 
				"        EXP: " + 
				percentBar(expPercent) + 
				ChatColor.GRAY + ChatColor.BOLD + " " + expPercent + "%" + 
				ChatColor.RESET + ChatColor.GRAY + " [" + 
				ChatColor.BLUE + currentExp + " / " + expGoal +
				ChatColor.RESET + ChatColor.GRAY + "] "
				+ ChatColor.GREEN + "+" + ChatColor.GRAY+ exp + " EXP";
	}
	
	/**
	 * Percent bar for chat leveling bar.
	 * 
	 * @param expPercent The percent to display.
	 * @return Fancy String with colored percentage bar.
	 */
	public static String percentBar(int expPercent) {
		if (expPercent <= 5) {
			return ChatColor.RED + "" + ChatColor.BOLD + "|" + ChatColor.GRAY + ChatColor.BOLD + "|||||||||||||||||||";
		} else if (expPercent > 5 && expPercent <= 10) {
			return ChatColor.RED + "" + ChatColor.BOLD + "||" + ChatColor.GRAY + ChatColor.BOLD + "||||||||||||||||||";
		} else if (expPercent > 10 && expPercent <= 15) {
			return ChatColor.RED + "" + ChatColor.BOLD + "|||" + ChatColor.GRAY + ChatColor.BOLD + "|||||||||||||||||";
		} else if (expPercent > 15 && expPercent <= 20) {
			return ChatColor.RED + "" + ChatColor.BOLD + "||||" + ChatColor.GRAY + ChatColor.BOLD + "||||||||||||||||";
		} else if (expPercent > 20 && expPercent <= 25) {
			return ChatColor.RED + "" + ChatColor.BOLD + "|||||" + ChatColor.GRAY + ChatColor.BOLD + "|||||||||||||||";
		} else if (expPercent > 25 && expPercent <= 30) {
			return ChatColor.RED + "" + ChatColor.BOLD + "||||||" + ChatColor.GRAY + ChatColor.BOLD + "||||||||||||||";
		} else if (expPercent > 30 && expPercent <= 35) {
			return ChatColor.YELLOW + "" + ChatColor.BOLD + "|||||||" + ChatColor.GRAY + ChatColor.BOLD + "|||||||||||||";
		} else if (expPercent > 35 && expPercent <= 40) {
			return ChatColor.YELLOW + "" + ChatColor.BOLD + "||||||||" + ChatColor.GRAY + ChatColor.BOLD + "||||||||||||";
		} else if (expPercent > 40 && expPercent <= 45) {
			return ChatColor.YELLOW + "" + ChatColor.BOLD + "|||||||||" + ChatColor.GRAY + ChatColor.BOLD + "|||||||||||";
		} else if (expPercent > 45 && expPercent <= 50) {
			return ChatColor.YELLOW + "" + ChatColor.BOLD + "||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "||||||||||";
		} else if (expPercent > 50 && expPercent <= 55) {
			return ChatColor.YELLOW + "" + ChatColor.BOLD + "|||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "|||||||||";
		} else if (expPercent > 55 && expPercent <= 60) {
			return ChatColor.YELLOW + "" + ChatColor.BOLD + "||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "||||||||";
		} else if (expPercent > 60 && expPercent <= 65) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "|||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "|||||||";
		} else if (expPercent > 65 && expPercent <= 70) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "||||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "||||||";
		} else if (expPercent > 70 && expPercent <= 75) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "|||||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "|||||";
		} else if (expPercent > 75 && expPercent <= 80) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "||||||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "||||";
		} else if (expPercent > 80 && expPercent <= 85) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "|||||||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "|||";
		} else if (expPercent > 85 && expPercent <= 90) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "||||||||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "||";
		} else if (expPercent > 90 && expPercent <= 95) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "||||||||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "||";
		} else if (expPercent > 95 && expPercent <= 100) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "|||||||||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "|";
		} else {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "||||||||||||||||||||";
		}
	}
	
	//////////////////////////////////////////////
	// Getters and Setters ///////////////////////
	//////////////////////////////////////////////
	
	public static boolean canShowAdminDebugMessage() {
		return showAdminDebugMessage;
	}

	public static void setShowAdminDebugMessage(boolean showAdminDebugMessage) {
		MessageManager.showAdminDebugMessage = showAdminDebugMessage;
	}

	/**
	 * Returns a boolean if the debug messages
	 * are turned on or off.
	 */
	public static boolean canShowDebugMessage() {
		return showDebugMessage;
	}
	
	/**
	 * Set the boolean to turn debug messages on or off.
	 * 
	 * @param showDebugMessage Sets debug messages on or off
	 */
	public void setShowDebugMessage(boolean showDebugMessage) {
		MessageManager.showDebugMessage = showDebugMessage;
	}
}
