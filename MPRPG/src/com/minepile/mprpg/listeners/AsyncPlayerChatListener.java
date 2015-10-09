package com.minepile.mprpg.listeners;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.chat.ChatManager;
import com.minepile.mprpg.player.PlayerCharacterManager;

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
			String playerName = player.getName();
			String msg = event.getMessage();
			
			if (PlayerCharacterManager.isPlayerLoaded(player)) {
				String chatFocus = PlayerCharacterManager.getPlayerConfigString(player, "setting.chat.focus");

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
					
					//Cancel the even because we don't want to broadcast this message globally.
					event.setCancelled(true);
					
					//Get players near by that will recieve this message.
					List<Entity> localPlayers = player.getNearbyEntities(20, 20, 20);
					int messagesRecieved = 0;
					
					//Loop through the list of entities.
					for (int i = 0; i < localPlayers.size(); i++) {
						//The entity that is near by the player
						Entity entity = localPlayers.get(i);
						
						//If the entity is a player, then send them the message.
						if (entity instanceof Player) {
							
							//Make sure the entity is not a npc
							if (!entity.hasMetadata("NPC")) {
								
								//Finally display the local message to near by players.
								entity.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Local " +
										prefix + clanTag +
										ChatColor.GRAY + playerName + ChatColor.DARK_GRAY + ": " + 
										ChatColor.WHITE + msg);
								
								//Increment how many players received the message.
								messagesRecieved++;
							}
						}
					}
					
					//Send player their own message.
					player.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Local " +
							prefix + clanTag +
							ChatColor.GRAY + playerName + ChatColor.DARK_GRAY + ": " + 
							ChatColor.WHITE + msg);
					
					//If no one is around to hear their message, let the player know.
					if (messagesRecieved == 0) {
						player.sendMessage("");
						player.sendMessage(ChatColor.RED + "No one heard your message!");
						player.sendMessage(ChatColor.GRAY + "Change your chat channel with the command: /c");
					}
					
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
				//Make the player create a character before they can talk.
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "You can not chat until you have selected or created a new character" + ChatColor.DARK_GRAY + ".");
			}
		} else {
			event.setFormat(ChatColor.GOLD + "" + ChatColor.BOLD + "MPRPG" + 
					ChatColor.DARK_GRAY + ": " + ChatColor.RESET + "%s");
		}
	}
}