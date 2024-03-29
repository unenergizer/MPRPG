package com.minepile.mprpg.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.chat.ChatManager;
import com.minepile.mprpg.chat.DiceRollManager;
import com.minepile.mprpg.chat.LagManager;
import com.minepile.mprpg.chat.MessageManager;
import com.minepile.mprpg.entities.MonsterCreatorManager;
import com.minepile.mprpg.gui.PlayerMenuManager;
import com.minepile.mprpg.guild.GuildManager;
import com.minepile.mprpg.items.ItemLoreFactory;
import com.minepile.mprpg.player.PlayerAttributesManager;
import com.minepile.mprpg.player.PlayerCharacterManager;
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
			UUID uuid = player.getUniqueId();
			
			//Make sure only players that have selected a character and class can run these commands.
			if (PlayerCharacterManager.isPlayerLoaded(player)) {

				//TODO: Remove debug messages.
				//player.sendMessage("DEBUG: args.length = " + Integer.toString(args.length));
				//player.sendMessage("DEBUG: args = " + Arrays.toString(args));

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

							String currentChannel = PlayerCharacterManager.getPlayerConfigString(player, "setting.chat.focus");

							if (arg.equalsIgnoreCase(currentChannel)) {
								//Tell the player their chat channel has NOT been changed.
								player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + 
										"       Your are already in the " + ChatColor.WHITE + 
										ChatColor.BOLD + ChatColor.UNDERLINE + arg + 
										ChatColor.RED + ChatColor.BOLD + " channel.");
							} else {

								//Make sure the player is a staff member or admin before they join staff channels.
								if(arg.equalsIgnoreCase("admin") && player.isOp() || arg.equalsIgnoreCase("mod") && player.isOp()) {
									//Set the players chat channel focus.
									PlayerCharacterManager.setPlayerConfigString(player, "setting.chat.focus", arg);

									//Tell the player their chat channel has been changed.
									player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + 
											"       Your are now chatting in the " + 
											ChatColor.WHITE + ChatColor.BOLD + ChatColor.UNDERLINE + arg + 
											ChatColor.YELLOW + ChatColor.BOLD + " channel.");

								} else if (arg.equalsIgnoreCase("admin") && !player.isOp() || arg.equalsIgnoreCase("mod") && !player.isOp()) {

									player.sendMessage(ChatColor.RED + "You are not allowed to join this channel.");

								} else { //The player is not staff and is joining a regular channel. So lets let them.
									//Set the players chat channel focus.
									PlayerCharacterManager.setPlayerConfigString(player, "setting.chat.focus", arg);

									//Tell the player their chat channel has been changed.
									player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + 
											"       Your are now chatting in the " + 
											ChatColor.WHITE + ChatColor.BOLD + ChatColor.UNDERLINE + arg + 
											ChatColor.YELLOW + ChatColor.BOLD + " channel.");
								}
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

							//Target player.
							Player target = Bukkit.getPlayer(targetName);

							//Start loop at location 1, to skip the username.
							for (int i = 1; i < args.length; i++) {
								str.append(args[i] + " ");
							}

							String msg = str.toString();

							//Lets set lastPM for both players.
							PlayerCharacterManager.setPlayerConfigString(player, "setting.chat.lastpm", targetName);
							PlayerCharacterManager.setPlayerConfigString(target, "setting.chat.lastpm", player.getName());

							//Get guild tag (if any).
							String guildTag = ChatManager.getGuildTag(player);

							//Append a staff tag (if any).
							String prefix = ChatManager.getStaffPrefix(player);

							//Send target player the message.
							target.sendMessage(ChatColor.LIGHT_PURPLE + "" + 
									ChatColor.BOLD + "PM " + prefix + guildTag +
									ChatColor.GRAY + senderName + ChatColor.LIGHT_PURPLE + " > " +
									ChatColor.GRAY + target.getName() + ChatColor.DARK_GRAY + ": " + 
									ChatColor.WHITE + msg);

							//Send message sound.
							target.playSound(target.getLocation(), Sound.CAT_MEOW, .8f, .8f);

							//Send the sender the same message.
							player.sendMessage(ChatColor.LIGHT_PURPLE + "" + 
									ChatColor.BOLD + "PM " + prefix + guildTag +
									ChatColor.GRAY + senderName + ChatColor.LIGHT_PURPLE + " > " +
									ChatColor.GRAY + target.getName() + ChatColor.DARK_GRAY + ": " + 
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

						String targetName = PlayerCharacterManager.getPlayerConfigString(player, "setting.chat.lastpm");
						String senderName = sender.getName();

						if (Bukkit.getServer().getPlayer(targetName) != null) {
							StringBuilder str = new StringBuilder();

							//Target player.
							Player target = Bukkit.getPlayer(targetName);

							//Start loop at location 0.
							for (int i = 0; i < args.length; i++) {
								str.append(args[i] + " ");
							}
							String msg = str.toString();

							//Lets set lastPM for both players.
							PlayerCharacterManager.setPlayerConfigString(player, "setting.chat.lastpm", targetName);
							PlayerCharacterManager.setPlayerConfigString(target, "setting.chat.lastpm", player.getName());

							//Get clan tag (if any).
							String clanTag = ChatManager.getGuildTag(player);

							//Append a staff tag (if any).
							String prefix = ChatManager.getStaffPrefix(player);

							//Send target player the message.
							target.sendMessage(ChatColor.LIGHT_PURPLE + "" + 
									ChatColor.BOLD + "PM " + prefix + clanTag +
									ChatColor.GRAY + senderName + ChatColor.LIGHT_PURPLE + " > " +
									ChatColor.GRAY + target.getName() + ChatColor.DARK_GRAY + ": " + 
									ChatColor.WHITE + msg);

							//Send message sound.
							target.playSound(target.getLocation(), Sound.CAT_MEOW, .8f, .8f);

							//Send the sender the same message.
							player.sendMessage(ChatColor.LIGHT_PURPLE + "" + 
									ChatColor.BOLD + "PM " + prefix + clanTag +
									ChatColor.GRAY + senderName + ChatColor.LIGHT_PURPLE + " > " +
									ChatColor.GRAY + target.getName() + ChatColor.DARK_GRAY + ": " + 
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
					//LoreManager.displayLoreStats((Player)sender);
					ItemLoreFactory.getInstance().displayAttributes(player);
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

				//Monster Creator and Manager commands.
				if (cmd.getLabel().equalsIgnoreCase("mm")) {
					if (player.isOp()) {

						//Show mm header text and borders.
						player.sendMessage(" ");
						player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------" +
								ChatColor.DARK_GRAY + "<[" +
								ChatColor.GOLD + " Monster Manager " + ChatColor.DARK_GRAY + "]>" +
								ChatColor.BOLD + "--------------");
						player.sendMessage(" ");


						if (args.length == 1 && args[0].toString().equalsIgnoreCase("entities")) {
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

							//TODO: Fix this to use new mob creating features!

							player.sendMessage(ChatColor.GRAY + "  " + ChatColor.BOLD + 
									"NOTE" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + 
									ChatColor.WHITE + "Create your own mobs with this command" + ChatColor.DARK_GRAY + "." + ChatColor.GREEN + " /mm manager");

						} else if (args.length == 1 && args[0].toString().equalsIgnoreCase("manager")) {

							player.sendMessage(ChatColor.RED + "  " + ChatColor.BOLD + "! " +
									ChatColor.GREEN + ChatColor.BOLD + "Please specify what you want to do.");
							player.sendMessage(" ");
							//Display Command prompt.
							player.sendMessage(ChatColor.BLUE + "  " + ChatColor.BOLD + 
									"Commands" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": ");
							//Add command
							player.sendMessage("     " + ChatColor.GREEN + "/mm manager add <name> <color> <entity> <LVL> <HP> <radius>");
							player.sendMessage("          " + ChatColor.GRAY + "This will add a new mobType to the database.");

							//Edit command
							player.sendMessage("");
							player.sendMessage("     " + ChatColor.YELLOW + "/mm manager edit <mobType>");
							player.sendMessage("          " + ChatColor.GRAY + "This will edit existing mobTypes.");

							//Delete command
							player.sendMessage("");
							player.sendMessage("     " + ChatColor.RED + "/mm manager delete <mobType>");
							player.sendMessage("          " + ChatColor.GRAY + "This will perminantly delete a mobType.");

							//Set mob
							player.sendMessage("");
							player.sendMessage("     " + ChatColor.LIGHT_PURPLE + "/mm manager set <mobType>");
							player.sendMessage("          " + ChatColor.GRAY + "Sets a monster at your current location.");

							//Spacer
							player.sendMessage("");

							player.sendMessage(ChatColor.GRAY + "  " + ChatColor.BOLD + 
									"NOTE" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + 
									ChatColor.WHITE + "To set a mob, create or use existing mobTypes" + 
									ChatColor.RED + "!");
							player.sendMessage(ChatColor.GRAY + "  " + ChatColor.BOLD + 
									"NOTE" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + 
									ChatColor.WHITE + "List of mobTypes" + ChatColor.DARK_GRAY + "." + ChatColor.LIGHT_PURPLE + " /mm mobTypes");
						} else if (args.length >= 1 && args[0].toString().equalsIgnoreCase("manager")) {

							if (args.length == 9 && args[1].toString().equalsIgnoreCase("add")) {

								player.sendMessage(ChatColor.RED + "  " + ChatColor.BOLD + "> " +
										ChatColor.GREEN + ChatColor.BOLD + "Please specify what you want to do.");
								player.sendMessage(" ");
								//Display Command prompt.
								player.sendMessage(ChatColor.BLUE + "  " + ChatColor.BOLD + 
										"Commands" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": ");
								//Add command
								player.sendMessage("     " + ChatColor.GREEN + "/mm manager add <name> <color> <entity> <LVL> <HP> <radius> <lootTable>");
								player.sendMessage("          " + ChatColor.GRAY + "This will add a new mobType to the database.");

								player.sendMessage("");

								player.sendMessage(ChatColor.GRAY + "  " + ChatColor.BOLD + 
										"NOTE" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + 
										ChatColor.WHITE + "To set a mob, create or use existing mobTypes" + 
										ChatColor.RED + "!");
								player.sendMessage(ChatColor.GRAY + "  " + ChatColor.BOLD + 
										"NOTE" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": " + 
										ChatColor.WHITE + "List of mobTypes" + ChatColor.DARK_GRAY + "." + ChatColor.LIGHT_PURPLE + " /mm mobTypes");

								String name = args[2].toString();
								String nameColor = args[3].toString();
								EntityType entity = EntityType.fromName(args[4].toString());
								int lvl = Integer.parseInt(args[5]);
								int hp = Integer.parseInt(args[6]);
								int radius = Integer.parseInt(args[7]);
								String lootTable = args[8].toString();

								MonsterCreatorManager.createNewMonster(player, name, nameColor, entity, lvl, hp, radius, lootTable);

							} else if (args.length == 2 && args[1].toString().equalsIgnoreCase("edit")) {
								player.sendMessage("This will let your edit a mobType that was created.");
							} else if (args.length == 2 && args[1].toString().equalsIgnoreCase("delete")) {
								player.sendMessage("This will let you delete a mobType that was created.");
							} else if (args.length == 3 && args[1].toString().equalsIgnoreCase("set")) {
								player.sendMessage("This will set a mob on the ground in your location.");

								String mobType =  args[2].toString();
								Location loc = player.getLocation();

								MonsterCreatorManager.setEntitie(player, mobType, loc);
							} else {
								player.sendMessage("Something went wrong. Please try again.");
							}
						} else if (args.length == 1 && args[0].toString().equalsIgnoreCase("mobs")) {
							player.sendMessage("This will contain the a list of info for mobs. Coming soon.");

						} else if (args.length == 1 && args[0].toString().equalsIgnoreCase("mobTypes")) {
							player.sendMessage("Displays a list of the custom mob types. Coming soon.");

						} else {
							//Default info text when "/mm" command is run.
							player.sendMessage(ChatColor.RED + "  " + ChatColor.BOLD + "! " +
									ChatColor.GREEN + ChatColor.BOLD + "Please specify what you want to do.");
							player.sendMessage(" ");
							player.sendMessage(ChatColor.BLUE + "  " + ChatColor.BOLD + 
									"Commands" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": ");
							player.sendMessage("     " + ChatColor.YELLOW + "/mm entities");
							player.sendMessage("          " + ChatColor.GRAY + "Display a reference of entity types.");
							player.sendMessage("     " + ChatColor.GREEN + "/mm manager");
							player.sendMessage("          " + ChatColor.GRAY + "Adding, editing, and deleting of mobs.");
							player.sendMessage("     " + ChatColor.BLUE + "/mm mobs");
							player.sendMessage("          " + ChatColor.GRAY + "Displays a list of info for mobs.");
							player.sendMessage("     " + ChatColor.LIGHT_PURPLE + "/mm mobTypes");
							player.sendMessage("          " + ChatColor.GRAY + "Displays a list of the custom mob types.");
						}

					}

					//Show footer message.
					player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD +"---------------------------------------------");
				}

				if (cmd.getLabel().equalsIgnoreCase("gethp")) {
					if (args.length == 0) {
						player.sendMessage(ChatColor.RED + "Your current HP" + ChatColor.DARK_GRAY + ": " + 
								ChatColor.RESET + Double.toString(PlayerManager.getHealthPoints(uuid)));
					}
				}

				/////////////////////////////////////////////////////////////////////////////////
				// GUILD COMMANDS : These commands do various actions with player clans /////////
				/////////////////////////////////////////////////////////////////////////////////

				//Roll command. Used to get a random number.
				if (cmd.getLabel().equalsIgnoreCase("guild")) {

					//Show mm header text and borders.
					player.sendMessage(" ");
					player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "----------------" +
							ChatColor.DARK_GRAY + "<[" +
							ChatColor.GOLD + " Guild Manager " + ChatColor.DARK_GRAY + "]>" +
							ChatColor.BOLD + "---------------");
					player.sendMessage(" ");

					if (GuildManager.getGuildOwner(player) == true) {

						String guildName = GuildManager.getPlayerGuild(player);

						if (args.length == 2 && args[0].toString().startsWith("addMember")) {
							GuildManager.addGuild(player, guildName);
							
						} else if (args.length == 2 && args[0].toString().startsWith("deleteMember")) {
							GuildManager.removePlayer(player, guildName, args[1].toString());
							
						} else if (args.length == 2 && args[0].toString().startsWith("promoteMember")) {
							GuildManager.promotePlayer(player, guildName, args[1].toString());
							
						} else if (args.length == 2 && args[0].toString().startsWith("demoteMember")) {
							GuildManager.demotePlayer(player, guildName, args[1].toString());
							
						} else if (args.length == 2 && args[0].toString().startsWith("setMotd")) {
							GuildManager.setMotd(player, guildName, "< WORK IN PROGRESS >");
							
						} else if (args.length == 2 && args[0].toString().startsWith("setTag")) {
							GuildManager.setGuildTag(player, guildName, args[1].toString());
							
						} else if (args.length == 2 && args[0].toString().startsWith("disband")) {
							GuildManager.disbandGuild(player, guildName);
							
						} else {
							//Default info text when "/guild" command is run.
							player.sendMessage(ChatColor.RED + "  " + ChatColor.BOLD + "! " +
									ChatColor.GREEN + ChatColor.BOLD + "Please specify what you want to do.");
							player.sendMessage(" ");
							player.sendMessage(ChatColor.BLUE + "  " + ChatColor.BOLD + 
									"Commands" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": ");
							player.sendMessage("     " + ChatColor.GREEN + "/guild addMember" + ChatColor.WHITE + " <playerName>");
							player.sendMessage("          " + ChatColor.GRAY + "This will invite a new player to the guild.");
							player.sendMessage("     " + ChatColor.RED + "/guild deleteMember" + ChatColor.WHITE + " <playerName>");
							player.sendMessage("          " + ChatColor.GRAY + "This will remove a member of the guild.");
							player.sendMessage("     " + ChatColor.GREEN + "/guild promoteMember" + ChatColor.WHITE + " <playerName>");
							player.sendMessage("          " + ChatColor.GRAY + "This will promote a member of the guild.");
							player.sendMessage("     " + ChatColor.RED + "/guild demoteMember" + ChatColor.WHITE + " <playerName>");
							player.sendMessage("          " + ChatColor.GRAY + "This will demote a member of the guild.");
							player.sendMessage("     " + ChatColor.LIGHT_PURPLE + "/guild setMotd" + ChatColor.WHITE + " <Message of the day>");
							player.sendMessage("          " + ChatColor.GRAY + "This will set the guild's message of the day.");
							player.sendMessage("     " + ChatColor.LIGHT_PURPLE + "/guild setTag" + ChatColor.WHITE + " <TAG>");
							player.sendMessage("          " + ChatColor.GRAY + "This will let you set your guild's Tag prefix.");
							player.sendMessage("     " + ChatColor.RED + "/guild disband");
							player.sendMessage("          " + ChatColor.GRAY + "This will close your guild and remove the members.");
						}
					} else {
						if (args.length == 2 && args[0].toString().startsWith("new")) {
							String guildName = args[1].toString();
							GuildManager.addGuild(player, guildName);
						} else {
							//Default info text when "/guild" command is run.
							player.sendMessage(ChatColor.RED + "  " + ChatColor.BOLD + "! " +
									ChatColor.GREEN + ChatColor.BOLD + "Please specify what you want to do.");
							player.sendMessage(" ");
							player.sendMessage(ChatColor.BLUE + "  " + ChatColor.BOLD + 
									"Commands" + ChatColor.DARK_GRAY + ChatColor.BOLD + ": ");
							player.sendMessage("     " + ChatColor.GREEN + "/guild new" + ChatColor.WHITE + " <guildName>");
							player.sendMessage("          " + ChatColor.GRAY + "This will create a new guild.");
						}
					}

					//Show footer message.
					player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD +"---------------------------------------------");

				}


				/////////////////////////////////////////////////////////////////////////////////
				// STATS: Opens a inventory menu so the player can assing attribute points //////
				/////////////////////////////////////////////////////////////////////////////////

				//Teleports the player back to the spawn location.
				if (cmd.getLabel().equalsIgnoreCase("stats")) {
					PlayerAttributesManager.createAttributeAssignMenu(player);
					PlayerAttributesManager.openAttributeAssingMenu(player);
				}


				/////////////////////////////////////////////////////////////////////////////////
				// HELP COMMAND REPLACE  ////////////////////////////////////////////////////////
				/////////////////////////////////////////////////////////////////////////////////

				//Teleports the player back to the spawn location.
				if (cmd.getLabel().equalsIgnoreCase("help")) {
					PlayerMenuManager.giveRuleBook(player);
				}
				
			} else {
				player.sendMessage(ChatColor.RED + "You can not use this command until you have selected or created a new character" + ChatColor.DARK_GRAY + ".");
			}
			return false;
		} else { //The command sent was not by a player.
			sender.sendMessage("Please do not use the console to run this command.");
		}
		return false;
	}
}
