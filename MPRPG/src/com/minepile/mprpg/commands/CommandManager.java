package com.minepile.mprpg.commands;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.managers.MessageManager;
import com.minepile.mprpg.managers.PlayerManager;

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
						"ChatChannels: global, guild, help, local, party, pm, and trade.");
			} else if (args.length == 1) {
				
				String arg = args[0].toString();
				
				if (arg.equalsIgnoreCase("global") || arg.toString().equalsIgnoreCase("guild") || 
						arg.equalsIgnoreCase("help") ||
						arg.equalsIgnoreCase("local") || arg.equalsIgnoreCase("party") ||
						arg.equalsIgnoreCase("pm") || arg.equalsIgnoreCase("trade") ||
						arg.equalsIgnoreCase("admin") || arg.equalsIgnoreCase("mod")) {
					
					//Set the players chat channel focus.
					PlayerManager.setPlayerConfigString(player, "setting.chat.focus", arg);
					
					//Tell the player their chat channel has been changed.
					player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + 
							"       Your chat channel has been changed to " + arg);
					
				} else {
					player.sendMessage(MessageManager.selectMessagePrefix("debug") +
							"Please specify what channel you want to join.");
					player.sendMessage(MessageManager.selectMessagePrefix("debug") + 
							"ChatChannels: global, guild, help, local, party, pm, and trade.");
				}
			} else {
				player.sendMessage(MessageManager.selectMessagePrefix("debug") +
						"Please specify what channel you want to join.");
				player.sendMessage(MessageManager.selectMessagePrefix("debug") + 
						"ChatChannels: global, guild, help, local, party, pm, and trade.");
			}
		}
		return false;
	}
}
