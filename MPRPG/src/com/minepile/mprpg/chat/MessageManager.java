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
				"EXP: " + 
				percentBar(expPercent) + 
				ChatColor.GRAY + ChatColor.BOLD + " " + expPercent + "%" + 
				ChatColor.RESET + ChatColor.GRAY + " [" + 
				ChatColor.BLUE + currentExp + " / " + expGoal +
				ChatColor.RESET + ChatColor.GRAY + "] "
				+ ChatColor.GREEN + "+" + ChatColor.GRAY+ exp + " EXP";
	}
	
	/**
	 * EXP level formatting for chat.
	 * 
	 * @param exp The amount of experience added.
	 * @param currentExp The experience amount before new total.
	 * @param expGoal The experience needed to level.
	 * @return Fancy String displaying colored experience level.
	 */
	public static String showHPBar(int exp, int currentExp, int expGoal){
		int expPercent = ((100 * currentExp) / expGoal);
	
		return ChatColor.GRAY + "" + ChatColor.BOLD + 
				"EXP: " + 
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
	 * @param hpPercent The percent to display.
	 * @return Fancy String with colored percentage bar.
	 */
	public static String percentBar(double hpPercent) {
		if (hpPercent <= 5) {
			return ChatColor.RED + "" + ChatColor.BOLD + "|" + ChatColor.GRAY + ChatColor.BOLD + "|||||||||||||||||||";
		} else if (hpPercent > 5 && hpPercent <= 10) {
			return ChatColor.RED + "" + ChatColor.BOLD + "||" + ChatColor.GRAY + ChatColor.BOLD + "||||||||||||||||||";
		} else if (hpPercent > 10 && hpPercent <= 15) {
			return ChatColor.RED + "" + ChatColor.BOLD + "|||" + ChatColor.GRAY + ChatColor.BOLD + "|||||||||||||||||";
		} else if (hpPercent > 15 && hpPercent <= 20) {
			return ChatColor.RED + "" + ChatColor.BOLD + "||||" + ChatColor.GRAY + ChatColor.BOLD + "||||||||||||||||";
		} else if (hpPercent > 20 && hpPercent <= 25) {
			return ChatColor.RED + "" + ChatColor.BOLD + "|||||" + ChatColor.GRAY + ChatColor.BOLD + "|||||||||||||||";
		} else if (hpPercent > 25 && hpPercent <= 30) {
			return ChatColor.RED + "" + ChatColor.BOLD + "||||||" + ChatColor.GRAY + ChatColor.BOLD + "||||||||||||||";
		} else if (hpPercent > 30 && hpPercent <= 35) {
			return ChatColor.YELLOW + "" + ChatColor.BOLD + "|||||||" + ChatColor.GRAY + ChatColor.BOLD + "|||||||||||||";
		} else if (hpPercent > 35 && hpPercent <= 40) {
			return ChatColor.YELLOW + "" + ChatColor.BOLD + "||||||||" + ChatColor.GRAY + ChatColor.BOLD + "||||||||||||";
		} else if (hpPercent > 40 && hpPercent <= 45) {
			return ChatColor.YELLOW + "" + ChatColor.BOLD + "|||||||||" + ChatColor.GRAY + ChatColor.BOLD + "|||||||||||";
		} else if (hpPercent > 45 && hpPercent <= 50) {
			return ChatColor.YELLOW + "" + ChatColor.BOLD + "||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "||||||||||";
		} else if (hpPercent > 50 && hpPercent <= 55) {
			return ChatColor.YELLOW + "" + ChatColor.BOLD + "|||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "|||||||||";
		} else if (hpPercent > 55 && hpPercent <= 60) {
			return ChatColor.YELLOW + "" + ChatColor.BOLD + "||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "||||||||";
		} else if (hpPercent > 60 && hpPercent <= 65) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "|||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "|||||||";
		} else if (hpPercent > 65 && hpPercent <= 70) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "||||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "||||||";
		} else if (hpPercent > 70 && hpPercent <= 75) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "|||||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "|||||";
		} else if (hpPercent > 75 && hpPercent <= 80) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "||||||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "||||";
		} else if (hpPercent > 80 && hpPercent <= 85) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "|||||||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "|||";
		} else if (hpPercent > 85 && hpPercent <= 90) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "||||||||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "||";
		} else if (hpPercent > 90 && hpPercent <= 95) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "||||||||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "||";
		} else if (hpPercent > 95 && hpPercent <= 100) {
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
