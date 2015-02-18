package com.minepile.mprpg.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;

public class CommandManager implements CommandExecutor{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public CommandManager(MPRPG plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player player = (Player) sender;
		
		
		if (label.equalsIgnoreCase("test")) {
			player.sendMessage("Test recieved!");
		}
		return false;
	}
}
