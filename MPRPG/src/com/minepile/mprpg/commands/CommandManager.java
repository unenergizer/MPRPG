package com.minepile.mprpg.commands;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.managers.ChatManager;
import com.minepile.mprpg.managers.MessageManager;
import com.minepile.mprpg.player.PlayerManager;

public class CommandManager implements CommandExecutor{
	
	public static MPRPG plugin;

	@SuppressWarnings("static-access")
	public CommandManager(MPRPG plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player player = (Player) sender;
		
		
		if (label.equalsIgnoreCase("cc") || label.equalsIgnoreCase("chat") ||
				label.equalsIgnoreCase("chatchanel") || label.equalsIgnoreCase("ch")) {
			if (args.length == 0) {
				player.sendMessage(MessageManager.selectMessagePrefix("debug") +
						"Please specify what channel you want to join.");
				player.sendMessage(MessageManager.selectMessagePrefix("debug") + 
						"ChatChannels: global, guild, help, local, party, and trade.");
			} else if (args.length == 1) {
				
				String arg = args[0].toString();
				
				if (arg.equalsIgnoreCase("global") || arg.toString().equalsIgnoreCase("guild") || 
						arg.equalsIgnoreCase("help") || arg.equalsIgnoreCase("local") || 
						arg.equalsIgnoreCase("party") || arg.equalsIgnoreCase("trade") ||
						arg.equalsIgnoreCase("admin") || arg.equalsIgnoreCase("mod")) {
					
					String currentChannel = PlayerManager.getPlayerConfigString(player, "setting.chat.focus");
					
					if (arg.equalsIgnoreCase(currentChannel)) {
						//Tell the player their chat channel has NOT been changed.
						player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + 
								"       Your are already in channel " + ChatColor.RED + 
								ChatColor.BOLD + ChatColor.UNDERLINE + arg + 
								ChatColor.RED + ChatColor.BOLD + ".");
					} else {
						//Set the players chat channel focus.
						PlayerManager.setPlayerConfigString(player, "setting.chat.focus", arg);
					
						//Tell the player their chat channel has been changed.
						player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + 
								"       Your chat channel has been changed to " + 
								ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE + arg + 
								ChatColor.YELLOW + ChatColor.BOLD + ".");
					}
					
				} else {
					player.sendMessage(MessageManager.selectMessagePrefix("debug") +
							"Please specify what channel you want to join.");
					player.sendMessage(MessageManager.selectMessagePrefix("debug") + 
							"ChatChannels: global, guild, help, local, party, and trade.");
				}
			} else {
				player.sendMessage(MessageManager.selectMessagePrefix("debug") +
						"Please specify what channel you want to join.");
				player.sendMessage(MessageManager.selectMessagePrefix("debug") + 
						"ChatChannels: global, guild, help, local, party, and trade.");
			}
		}
		
		//Send a private message.
		if (label.equalsIgnoreCase("msg") || label.equalsIgnoreCase("tell") ||
				label.equalsIgnoreCase("message") || label.equalsIgnoreCase("pm")) {
			if (args.length == 0) {
				player.sendMessage(MessageManager.selectMessagePrefix("debug") +
						"Please specify the players name and then your message.");
				player.sendMessage(MessageManager.selectMessagePrefix("debug") + 
						"/msg playerNameHere Hello, it is a good day for an adventure!");
			} else if (args.length == 1) {
				player.sendMessage(MessageManager.selectMessagePrefix("debug") +
						"Please specify your message.");
				player.sendMessage(MessageManager.selectMessagePrefix("debug") + 
						"/msg playerNameHere Hello, it is a good day for an adventure!");
			} else if (args.length >= 2) {
				
				String targetName = args[0];
				String senderName = sender.getName();
				
				if (Bukkit.getServer().getPlayer(args[0]) != null) {
					StringBuilder str = new StringBuilder();
					
					//Start loop at location 1, to skip the username.
					for (int i = 1; i < args.length; i++) {
						str.append(args[i] + " ");
					}
					
					String msg = str.toString();
					
					//Lets set lastPM for both players.
					PlayerManager.setPlayerConfigString(player, "setting.chat.lastpm", targetName);
					PlayerManager.setPlayerConfigString(Bukkit.getPlayer(targetName), "setting.chat.lastpm", player.getName());
					
					//Get clan tag (if any).
					String clanTag = ChatManager.getClanTag(player);
					
					//Append a staff tag (if any).
					String prefix = ChatManager.getStaffPrefix(player);
					
					//Send target player the message.
					Bukkit.getPlayer(targetName).sendMessage(ChatColor.DARK_AQUA + "" + 
							ChatColor.BOLD + "PM " + prefix + clanTag +
							ChatColor.GRAY + senderName + ChatColor.DARK_GRAY + ": " + 
							ChatColor.WHITE + msg);
					
					//Send the sender the same message.
					player.sendMessage(ChatColor.DARK_AQUA + "" + 
							ChatColor.BOLD + "PM " + prefix + clanTag +
							ChatColor.GRAY + senderName + ChatColor.DARK_GRAY + ": " + 
							ChatColor.WHITE + msg);
				} else {
					player.sendMessage(MessageManager.selectMessagePrefix("debug") +
							targetName + " is offline.");
				}
			}
		}
		
		//Send player another private message.
		// "r" = Reply.
		if (label.equalsIgnoreCase("r")) {
			if (args.length == 0) {
				player.sendMessage(MessageManager.selectMessagePrefix("debug") +
						"Please specify the players name and then your message.");
				player.sendMessage(MessageManager.selectMessagePrefix("debug") + 
						"/r Hello, it is a good day for an adventure!");
			} else if (args.length >= 1) {
				
				String targetName = PlayerManager.getPlayerConfigString(player, "setting.chat.lastpm");
				String senderName = sender.getName();
				
				if (Bukkit.getServer().getPlayer(targetName) != null) {
					StringBuilder str = new StringBuilder();
					
					//Start loop at location 0.
					for (int i = 0; i < args.length; i++) {
						str.append(args[i] + " ");
					}
					String msg = str.toString();
					
					//Lets set lastPM for both players.
					PlayerManager.setPlayerConfigString(player, "setting.chat.lastpm", targetName);
					PlayerManager.setPlayerConfigString(Bukkit.getPlayer(targetName), "setting.chat.lastpm", player.getName());
					
					//Get clan tag (if any).
					String clanTag = ChatManager.getClanTag(player);
					
					//Append a staff tag (if any).
					String prefix = ChatManager.getStaffPrefix(player);
					
					//Send target player the message.
					Bukkit.getPlayer(targetName).sendMessage(ChatColor.DARK_AQUA + "" + 
							ChatColor.BOLD + "PM " + prefix + clanTag +
							ChatColor.GRAY + senderName + ChatColor.DARK_GRAY + ": " + 
							ChatColor.WHITE + msg);
					
					//Send the sender the same message.
					player.sendMessage(ChatColor.DARK_AQUA + "" + 
							ChatColor.BOLD + "PM " + prefix + clanTag +
							ChatColor.GRAY + senderName + ChatColor.DARK_GRAY + ": " + 
							ChatColor.WHITE + msg);
				} else {
					player.sendMessage(MessageManager.selectMessagePrefix("debug") +
							targetName + " is offline.");
				}
			}
		}
		return false;
	}
}
