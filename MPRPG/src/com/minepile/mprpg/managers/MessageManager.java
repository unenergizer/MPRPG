package com.minepile.mprpg.managers;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;

public class MessageManager {
	
	//setup variables
	public static MPRPG plugin;
	static MessageManager messageManagerInstance = new MessageManager();
	
	//Message Manager specific variables
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
	
	/*
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
	
	/*
	 * Displays the welcome message everyone see's when they login to the server.
	 * 
	 * @param the player the welcome message will be displayed too
	 */
	public static void displayWelcomeMessage(Player player) {
		String welcomeMessage = " \n" + " \n" + ChatColor.GOLD + "                        " + ChatColor.BOLD 
				+ "MinePile: " + ChatColor.WHITE + "" + ChatColor.BOLD + "RPGMMO v0.0.1 \n" + 
				ChatColor.RESET + ChatColor.GRAY + "                          http://www.MinePile.com/" + " \n" + 
				" \n" + " \n";
		player.sendMessage(welcomeMessage);
	}
	
	//////////////////////////////////////////////
	// Getters and Setters ///////////////////////
	//////////////////////////////////////////////
	
	/*
	 * Returns a boolean if the debug messages
	 * are turned on or off.
	 */
	public static boolean canShowDebugMessage() {
		return showDebugMessage;
	}
	
	/*
	 * Set the boolean to turn debug messages on or off.
	 * 
	 * @param showDebugMessage sets debug messages on or off
	 */
	public void setShowDebugMessage(boolean showDebugMessage) {
		MessageManager.showDebugMessage = showDebugMessage;
	}
}
