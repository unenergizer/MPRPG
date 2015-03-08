package com.minepile.mprpg.commands;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.equipment.LoreManager;
import com.minepile.mprpg.managers.ChatManager;
import com.minepile.mprpg.managers.DiceRollManager;
import com.minepile.mprpg.managers.LagManager;
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

		if (sender instanceof Player) {

			Player player = (Player) sender;

			if (label.equalsIgnoreCase("c")) {
				if (args.length == 0) {
					player.sendMessage(" ");
					player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "----------------" +
							ChatColor.DARK_GRAY + "<[" +
							ChatColor.GOLD + " Chat Channels " + ChatColor.DARK_GRAY + "]>" +
							ChatColor.BOLD + "---------------");
					player.sendMessage(" ");
					player.sendMessage(ChatColor.RED + "  " + ChatColor.BOLD + "! " +
							ChatColor.GREEN + ChatColor.BOLD + "Please specify what channel you want to join.");
					player.sendMessage(" ");
					player.sendMessage(ChatColor.BLUE + "  " + ChatColor.BOLD + 
							"Chat Channels" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": " +
							ChatColor.WHITE + "global" + ChatColor.DARK_GRAY + ", " +
							ChatColor.WHITE + "guild" + ChatColor.DARK_GRAY  + ", " +
							ChatColor.WHITE + "help" + ChatColor.DARK_GRAY  + ", " +
							ChatColor.WHITE + "local" + ChatColor.DARK_GRAY + ", " +
							ChatColor.WHITE + "party" + ChatColor.DARK_GRAY + ", " +
							ChatColor.WHITE + "and " + "trade" + 
							ChatColor.DARK_GRAY + ".");
					player.sendMessage(" ");
					player.sendMessage(ChatColor.GRAY + "  " + ChatColor.BOLD + 
							"Example" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + 
							ChatColor.WHITE + "/" + label + " help");
					player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD +"---------------------------------------------");
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
									"       Your are already in the " + ChatColor.WHITE + 
									ChatColor.BOLD + ChatColor.UNDERLINE + arg + 
									ChatColor.RED + ChatColor.BOLD + " channel.");
						} else {
							//Set the players chat channel focus.
							PlayerManager.setPlayerConfigString(player, "setting.chat.focus", arg);

							//Tell the player their chat channel has been changed.
							player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + 
									"       Your are now chatting in the " + 
									ChatColor.WHITE + ChatColor.BOLD + ChatColor.UNDERLINE + arg + 
									ChatColor.YELLOW + ChatColor.BOLD + " channel.");
						}

					} else {
						player.sendMessage(" ");
						player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "----------------" +
								ChatColor.DARK_GRAY + "<[" +
								ChatColor.GOLD + " Chat Channels " + ChatColor.DARK_GRAY + "]>" +
								ChatColor.BOLD + "---------------");
						player.sendMessage(" ");
						player.sendMessage(ChatColor.RED + "  " + ChatColor.BOLD + "! " +
								ChatColor.GREEN + ChatColor.BOLD + "Please specify what channel you want to join.");
						player.sendMessage(" ");
						player.sendMessage(ChatColor.BLUE + "  " + ChatColor.BOLD + 
								"Chat Channels" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": " +
								ChatColor.WHITE + "global" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "guild" + ChatColor.DARK_GRAY  + ", " +
								ChatColor.WHITE + "help" + ChatColor.DARK_GRAY  + ", " +
								ChatColor.WHITE + "local" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "party" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "and " + "trade" + 
								ChatColor.DARK_GRAY + ".");
						player.sendMessage(" ");
						player.sendMessage(ChatColor.GRAY + "  " + ChatColor.BOLD + 
								"Example" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + 
								ChatColor.WHITE + "/" + label + " help");
						player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD +"---------------------------------------------");
					}
				} else {
					player.sendMessage(" ");
					player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "----------------" +
							ChatColor.DARK_GRAY + "<[" +
							ChatColor.GOLD + " Chat Channels " + ChatColor.DARK_GRAY + "]>" +
							ChatColor.BOLD + "---------------");
					player.sendMessage(" ");
					player.sendMessage(ChatColor.RED + "  " + ChatColor.BOLD + "! " +
							ChatColor.GREEN + ChatColor.BOLD + "Please specify what channel you want to join.");
					player.sendMessage(" ");
					player.sendMessage(ChatColor.BLUE + "  " + ChatColor.BOLD + 
							"Chat Channels" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": " +
							ChatColor.WHITE + "global" + ChatColor.DARK_GRAY + ", " +
							ChatColor.WHITE + "guild" + ChatColor.DARK_GRAY  + ", " +
							ChatColor.WHITE + "help" + ChatColor.DARK_GRAY  + ", " +
							ChatColor.WHITE + "local" + ChatColor.DARK_GRAY + ", " +
							ChatColor.WHITE + "party" + ChatColor.DARK_GRAY + ", " +
							ChatColor.WHITE + "and " + "trade" + 
							ChatColor.DARK_GRAY + ".");
					player.sendMessage(" ");
					player.sendMessage(ChatColor.GRAY + "  " + ChatColor.BOLD + 
							"Example" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + 
							ChatColor.WHITE + "/" + label + " help");
					player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD +"---------------------------------------------");
				}
			}

			//Send a private message.
			if (label.equalsIgnoreCase("msg") || label.equalsIgnoreCase("tell") ||
					label.equalsIgnoreCase("pm")) {
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

			//Show player the server lag.
			if (label.equalsIgnoreCase("lag")) {
				double tps = Double.valueOf(String.format("%.2f", LagManager.getTPS()));
				double percent = Double.valueOf(String.format("%.2f", LagManager.getLagPercent()));
				long totalRam = Runtime.getRuntime().totalMemory() / 1048576L;
				long ramUsed = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L;
				long freeRam = Runtime.getRuntime().freeMemory() / 1048576L;

				String tpsStr = Double.toString(tps);
				String percentStr = Double.toString(percent);
				String ramUsedStr = Long.toString(ramUsed);
				String totalRamStr = Long.toString(totalRam);
				String freeRamStr = Long.toString(freeRam);

				player.sendMessage(" ");
				player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "-----------------" +
						ChatColor.DARK_GRAY + "<[" +
						ChatColor.GOLD + " Lag Meter " + ChatColor.DARK_GRAY + "]>" +
						ChatColor.BOLD + "-----------------");
				player.sendMessage(ChatColor.GOLD + "  " + ChatColor.BOLD + "TPS" + 
						ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + ChatColor.GREEN + 
						tpsStr + ChatColor.GRAY + "/" + ChatColor.GREEN + "20");
				player.sendMessage(ChatColor.RED + "    " + ChatColor.BOLD + "LAG" + 
						ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + ChatColor.GREEN + 
						percentStr + ChatColor.GRAY + "/" + ChatColor.GREEN + "100%");
				player.sendMessage(ChatColor.BLUE + "      " + ChatColor.BOLD + "RAM" + 
						ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + ChatColor.GREEN + 
						ramUsedStr + " MB" + ChatColor.GRAY + "/" + ChatColor.GREEN + totalRamStr + " MB");
				player.sendMessage(ChatColor.DARK_PURPLE + "        " + ChatColor.BOLD + "FREE RAM" + 
						ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + ChatColor.GREEN + freeRamStr + " MB");
				player.sendMessage(ChatColor.GRAY + "          " + ChatColor.BOLD + "Condition" + 
						ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + ChatColor.GREEN + 
						"No issues found. Running great.");
				player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD +"---------------------------------------------");
			}

			//Display the players lore stats.
			if (cmd.getLabel().equalsIgnoreCase("lorestats") || cmd.getLabel().equalsIgnoreCase("armorstats")) {
				LoreManager.displayLoreStats((Player)sender);
			}

			//Roll command. Used to get a random number.
			if (cmd.getLabel().equalsIgnoreCase("roll")) {
				if (args.length == 1) {

					try {
						int roll = DiceRollManager.onDiceRoll(Integer.parseInt(args[0]));

						Bukkit.broadcastMessage(ChatColor.GRAY + "     " + ChatColor.BOLD +
								player.getName() + ChatColor.DARK_GRAY + ChatColor.BOLD +
								": " + ChatColor.GRAY + "Rolled a " + ChatColor.UNDERLINE + 
								roll + ChatColor.GRAY + " out of " + ChatColor.UNDERLINE +
								args[0] + ChatColor.GRAY + ".");

					} catch (NumberFormatException e) {
						player.sendMessage(" ");
						player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "------------------" +
								ChatColor.DARK_GRAY + "<[" +
								ChatColor.GOLD + " Dice Roll " + ChatColor.DARK_GRAY + "]>" +
								ChatColor.BOLD + "-----------------");
						player.sendMessage(" ");
						player.sendMessage(ChatColor.RED + "  " + ChatColor.BOLD + "! " +
								ChatColor.GREEN + ChatColor.BOLD + "Please specify the maximum size of your dice roll.");
						player.sendMessage(" ");
						player.sendMessage(ChatColor.GRAY + "  " + ChatColor.BOLD + 
								"Example" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + 
								ChatColor.WHITE + "/" + label + " 100");
						player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD +"---------------------------------------------");
					}
				} else {
					player.sendMessage(" ");
					player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "------------------" +
							ChatColor.DARK_GRAY + "<[" +
							ChatColor.GOLD + " Dice Roll " + ChatColor.DARK_GRAY + "]>" +
							ChatColor.BOLD + "-----------------");
					player.sendMessage(" ");
					player.sendMessage(ChatColor.RED + "  " + ChatColor.BOLD + "! " +
							ChatColor.GREEN + ChatColor.BOLD + "Please specify the maximum size of your dice roll.");
					player.sendMessage(" ");
					player.sendMessage(ChatColor.GRAY + "  " + ChatColor.BOLD + 
							"Example" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + 
							ChatColor.WHITE + "/" + label + " 100");
					player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD +"---------------------------------------------");
				}
			}

			//Teleports the player back to the spawn location.
			if (cmd.getLabel().equalsIgnoreCase("spawn")) {
				PlayerManager.teleportPlayerToSpawn(player);
			}

			//Defines a mob location.
			if (cmd.getLabel().equalsIgnoreCase("mm")) {
				if (player.isOp()) {
					if (args.length == 1 && args[0].toString().equals("set")) {

					} else {
						player.sendMessage(" ");
						player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------" +
								ChatColor.DARK_GRAY + "<[" +
								ChatColor.GOLD + " Monster Manager " + ChatColor.DARK_GRAY + "]>" +
								ChatColor.BOLD + "--------------");
						player.sendMessage(" ");
						player.sendMessage(ChatColor.RED + "  " + ChatColor.BOLD + "! " +
								ChatColor.GREEN + ChatColor.BOLD + "Please specify the monster and its properties.");
						player.sendMessage(" ");
						player.sendMessage(ChatColor.GREEN + "  " + ChatColor.BOLD + 
								"Passive Entities" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": " +
								ChatColor.WHITE + "Bat" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "chicken" + ChatColor.DARK_GRAY  + ", " +
								ChatColor.WHITE + "cow" + ChatColor.DARK_GRAY  + ", " +
								ChatColor.WHITE + "donkey" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "horse" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "mooshroom" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "mule" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "ocelot" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "pig" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "rabbit" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "sheep" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "squid" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "and " + "villager" + 
								ChatColor.DARK_GRAY + ".");
						player.sendMessage(" ");
						player.sendMessage(ChatColor.BLUE + "  " + ChatColor.BOLD + 
								"Neutral Entities" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": " +
								ChatColor.WHITE + "Cave spider" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "enderman" + ChatColor.DARK_GRAY  + ", " +
								ChatColor.WHITE + "iron golem" + ChatColor.DARK_GRAY  + ", " +
								ChatColor.WHITE + "spider" + ChatColor.DARK_GRAY  + ", " +
								ChatColor.WHITE + "wolf" + ChatColor.DARK_GRAY  + ", " +
								ChatColor.WHITE + "and " + "zombie pigman" + 
								ChatColor.DARK_GRAY + ".");
						player.sendMessage(" ");
						player.sendMessage(ChatColor.RED + "  " + ChatColor.BOLD + 
								"Hostile Entities" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": " +
								ChatColor.WHITE + "Blaze" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "chicken jockey" + ChatColor.DARK_GRAY  + ", " +
								ChatColor.WHITE + "creeper" + ChatColor.DARK_GRAY  + ", " +
								ChatColor.WHITE + "elder guardian" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "ender dragon" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "endermite" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "ghast" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "giant" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "guardian" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "killer bunny" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "magma cube" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "siverfish" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "skeleton" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "slime" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "spider jockey" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "witch" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "wither" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "wither skeleton" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "zombie" + ChatColor.DARK_GRAY + ", " +
								ChatColor.WHITE + "and " + "zombie villager" + 
								ChatColor.DARK_GRAY + ".");
						player.sendMessage(" ");
						player.sendMessage(ChatColor.GRAY + "  " + ChatColor.BOLD + 
								"Example" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + 
								ChatColor.WHITE + "/" + label + " set <entity> <lvl> <tier> <dropTableId>");
						player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD +"---------------------------------------------");
					}
				}	
			}



		} else { //The command sent was not by a player.
			sender.sendMessage("Please do not use the console to run this command.");
		}
		return false;
	}
}
