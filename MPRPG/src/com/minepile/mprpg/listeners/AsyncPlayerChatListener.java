package com.minepile.mprpg.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.chat.ChatManager;
import com.minepile.mprpg.player.PlayerManager;

public class AsyncPlayerChatListener implements Listener{
	
	public static MPRPG plugin;
	public boolean allPlayersCanPlaceBlocks = true;
	
	@SuppressWarnings("static-access")
	public AsyncPlayerChatListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		
		if (event.getPlayer() instanceof Player) {
			Player player = event.getPlayer();
			String chatFocus = PlayerManager.getPlayerConfigString(player, "setting.chat.focus");
			
	        /////////////////////////////
			// Chat Channels Reference //
			/////////////////////////////
			//setting.chatchannel.admin
	        //setting.chatchannel.global
	        //setting.chatchannel.guild
	        //setting.chatchannel.help
	        //setting.chatchannel.local
	        //setting.chatchannel.mod
	        //setting.chatchannel.party
	        //setting.chatchannel.pm
	        //setting.chatchannel.trade
			
			//Get clan tag (if any).
			String clanTag = ChatManager.getClanTag(player);
			
			//Append a staff tag (if any).
			String prefix = ChatManager.getStaffPrefix(player);
			
			if (chatFocus.equalsIgnoreCase("admin")) {
				event.setFormat(ChatColor.RED + "" + ChatColor.BOLD + "Admin " +
						prefix + clanTag +
						ChatColor.GRAY + "%s" + ChatColor.DARK_GRAY + ": " + 
						ChatColor.WHITE + "%s");
			} else if (chatFocus.equalsIgnoreCase("global")) {
				event.setFormat(ChatColor.GREEN + "" + ChatColor.BOLD + "Global " +
						prefix + clanTag +
						ChatColor.GRAY + "%s" + ChatColor.DARK_GRAY + ": " + 
						ChatColor.WHITE + "%s");
			} else if (chatFocus.equalsIgnoreCase("guild")) {
				event.setFormat(ChatColor.GREEN + "" + ChatColor.BOLD + "Guild " +
						prefix + clanTag +
						ChatColor.GRAY + "%s" + ChatColor.DARK_GRAY + ": " + 
						ChatColor.WHITE + "%s");
			} else if (chatFocus.equalsIgnoreCase("help")) {
				event.setFormat(ChatColor.AQUA + "" + ChatColor.BOLD + "Help " +
						prefix + clanTag +
						ChatColor.GRAY + "%s" + ChatColor.DARK_GRAY + ": " + 
						ChatColor.WHITE + "%s");
			} else if (chatFocus.equalsIgnoreCase("local")) {
				event.setFormat(ChatColor.GRAY + "" + ChatColor.BOLD + "Local " +
						prefix + clanTag +
						ChatColor.GRAY + "%s" + ChatColor.DARK_GRAY + ": " + 
						ChatColor.WHITE + "%s");
			} else if (chatFocus.equalsIgnoreCase("mod")) {
				event.setFormat(ChatColor.GOLD + "" + ChatColor.BOLD + "Staff " +
						prefix + clanTag +
						ChatColor.GRAY + "%s" + ChatColor.DARK_GRAY + ": " + 
						ChatColor.WHITE + "%s");
			} else if (chatFocus.equalsIgnoreCase("party")) {
				event.setFormat(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Party " +
						prefix + clanTag +
						ChatColor.GRAY + "%s" + ChatColor.DARK_GRAY + ": " + 
						ChatColor.WHITE + "%s");
			} else if (chatFocus.equalsIgnoreCase("trade")) {
				event.setFormat(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Trade " +
						prefix + clanTag +
						ChatColor.GRAY + "%s" + ChatColor.DARK_GRAY + ": " + 
						ChatColor.WHITE + "%s");
			} else {
				event.setFormat(ChatColor.GREEN + "" + ChatColor.BOLD + "" +
						prefix + clanTag +
						ChatColor.GRAY + "%s" + ChatColor.DARK_GRAY + ": " + 
						ChatColor.WHITE + "%s");
			}
		} else {
			event.setFormat(ChatColor.GOLD + "" + ChatColor.BOLD + "MPRPG" + 
					ChatColor.DARK_GRAY + ": " + ChatColor.RESET + "%s");
		}
	}
}