package com.minepile.mprpg.guild;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerCharacterManager;


public class GuildManager {

	//setup instance variables
	public static MPRPG plugin;
	private static GuildManager guildManagerInstance = new GuildManager();
	private static String filePath = "plugins/MPCore/guilds/";
	private static String fileExtension = ".yml";

	//Create instance
	public static GuildManager getInstance() {
		return guildManagerInstance;
	}

	//Setup GuildManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	
	
	/**
	 * Checks to see if the player owns a guild.
	 * <p>
	 * This information comes from the Players configuration file. MPCore/players/playerUUID
	 * 
	 * @param player The player who might own a guild.
	 * @return Return a boolean true if player owns a guild.
	  */
	public static boolean getGuildOwner(Player player) {
		if (PlayerCharacterManager.getPlayerConfigBoolean(player, "guild.owner") == true) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Sets the players guild.
	 * <p>
	 * This information comes from the Players configuration file. MPCore/players/playerUUID
	 * 
	 * @param player The player who might be in a guild.
	 * @param guildName Name The name of the guild.
	  */
	public static void setGuildOwner(Player player, boolean value) {
		PlayerCharacterManager.setPlayerConfigBoolean(player, "guild.owner", value);
	}
	
	/**
	 * Gets the guild the player is currently in.
	 * <p>
	 * This information comes from the Players configuration file. MPCore/players/playerUUID
	 * 
	 * @param player The player who might be in a guild.
	 * @return Name of the guild the player is in.
	  */
	public static String getPlayerGuild(Player player) {
		if (PlayerCharacterManager.getPlayerConfigString(player, "guild.name") != null) {
			return PlayerCharacterManager.getPlayerConfigString(player, "guild.name");
		} else {
			return null;
		}
	}
	
	/**
	 * Sets the players guild.
	 * <p>
	 * This information comes from the Players configuration file. MPCore/players/playerUUID
	 * @param player The player who might be in a guild.
	 * @param guildName Name The name of the guild.
	  */
	public static void setPlayerGuild(Player player, String guildName) {
		PlayerCharacterManager.setPlayerConfigString(player, "guild.name", guildName);
	}
	
	/**
	 * This will create a new guild, if a guild with the same name doesn't already exist.
	 * 
	 * @param player The player who is making the guild. This player will become the guild owner.
	 * @param guildName The name of the guild.
	 */
	public static void addGuild(Player player, String guildName) {

		File configFile = new File(filePath + guildName + fileExtension);

		//Check to make sure the new guild doesn't already exist.
		if (!configFile.exists() && !configFile.isDirectory()) { 
			String uuid = player.getUniqueId().toString();
			String playerName = player.getName();

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();

			FileConfiguration guildConfig =  YamlConfiguration.loadConfiguration(configFile);
			guildConfig.set("guildName", guildName);
			guildConfig.set("guildTag", null);
			guildConfig.set("guildMOTD", null);
			guildConfig.set("guildDisbanded", false);
			guildConfig.set("guildSize", 25);
			guildConfig.set("guildCreationDate", dateFormat.format(date));
			guildConfig.set("guildDisbandedDate", null);
			guildConfig.set("guildOwner", playerName);
			guildConfig.set("guildOwnerUUID", uuid);
			guildConfig.set("guildOfficer", null);
			guildConfig.set("guildMembers", null);

			try {
				guildConfig.save(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Update player file.
			//setPlayerGuild(player, guildName);
			//setGuildOwner(player, true);
			
			player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + 
					"Your have created a new guild called " + 
					ChatColor.WHITE + ChatColor.BOLD + ChatColor.UNDERLINE + guildName + 
					ChatColor.YELLOW + ChatColor.BOLD + ".");
			player.sendMessage(" ");
		} else {
			player.sendMessage(ChatColor.RED + "This guild already exists. Please choose a different guild name.");
		}
	}

	/**
	 * This will disband a guild, making it no longer usable.  Only guild owners can do this.
	 * 
	 * @param player The guild owner.
	 * @param guildName The name of the guild to disband.
	 */
	public static void disbandGuild(Player player, String guildName) {
		String playerName = player.getName();
		String playerUUID = player.getUniqueId().toString();
		String guildOwnerUUID = getGuildConfigString(guildName, "guildOwnerUUID");

		if (playerUUID.equals(guildOwnerUUID)) {

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();

			setGuildConfigBoolean(guildName, "guildDisbanded", true);
			setGuildConfigString(guildName, "guildDisbandedDate", dateFormat.format(date));
			
			//Save changes to player configuration file.
			//setGuildOwner(player, false);
			//setPlayerGuild(player, null);

			player.sendMessage(ChatColor.YELLOW + playerName + ", your guild " + guildName + " has been disbanded.");
			player.sendMessage(ChatColor.YELLOW + "The name of this can can not be used for another 30 days.");
		} else {
			player.sendMessage(ChatColor.RED + "You must be the owner of '" + guildName + "' to disband this guild.");
		}
	}

	/**
	 * This will delete the guild file from the file-system forever.
	 * 
	 * @param player The player who is requesting full file deletion.
	 * @param guildName The name of the guild to be deleted.
	 */
	private static void deleteGuild(Player player, String guildName) {
		File configFile = new File(filePath + guildName + fileExtension);
		configFile.delete();
	}

	//TODO: Let guilds upgrade their size and other attributes.
	private static void upgradeGuild(Player player, String guildName) {

	}

	/**
	 * Sets the guild tag for a given guild.
	 * <p>
	 * Example:  [TAG] playerName
	 * @param player The guild owner.
	 * @param guildName The name of the guild.
	 * @param tag The 3 digit long guild tag.
	 */
	public static void setGuildTag(Player player, String guildName, String tag) {
		String playerName = player.getName();
		String playerUUID = player.getUniqueId().toString();
		String guildOwnerUUID = getGuildConfigString(guildName, "guildOwnerUUID");
		
		//Check to make sure the guild name length isn't longer than 24 characters.
		if (guildName.length() > 24) {
			//Make sure the guild owner is setting the tag.
			if (playerUUID.equals(guildOwnerUUID)) {
				//Make sure the tag is 3 characters long.
				if (tag.length() == 3) {
					//Make sure tag only contains letters and numbers.
					if (Pattern.matches("[^a-zA-Z0-9]", tag) == true) {

						setGuildConfigString(guildName, "guildTag", tag);

						player.sendMessage(ChatColor.GREEN + playerName + ", your guild tag has been successfully added.");
						player.sendMessage(ChatColor.YELLOW + "Your guild tag will now appear next to your name in chat.");
					} else {
						player.sendMessage(ChatColor.RED + "Your guild tag can only contain letters and numbers Do not use special characters.");
					}
				} else if (tag.length() > 3) {
					player.sendMessage(ChatColor.RED + "Your guild tag is too long. It must be 3 characters long.");
				} else {
					player.sendMessage(ChatColor.RED + "Your guild tag is too short. It must be 3 characters long.");
				}
			} else {
				player.sendMessage(ChatColor.RED + "You must be the owner of '" + guildName + "' to add a guild tag.");
			}
		} else {
			player.sendMessage(ChatColor.RED + "Your guild name is too long.  It must be under 24 characters long.");
		}
	}

	/**
	 * Sets a MOTD or "message of the day" for the given can.
	 * @param player The player who wants to set the guild motd.
	 * @param guildName The name of the guild.
	 * @param motd The message of the day.
	 */
	public static void setMotd(Player player, String guildName, String motd) {
		String playerName = player.getName();
		String playerUUID = player.getUniqueId().toString();
		String guildOwnerUUID = getGuildConfigString(guildName, "guildOwnerUUID");

		//Check to make sure the guild owner is making the changes.
		if (playerUUID.equals(guildOwnerUUID)) {
			//Check to make sure the motd is not more than 64 characters long.
			if (motd.length() < 64) {
				setGuildConfigString(guildName, "guildMOTD", motd);

				player.sendMessage(ChatColor.GREEN + playerName + ", your motd has been successfully added.");
				player.sendMessage(ChatColor.YELLOW + "Your motd will now appear every time guild members login.");
			} else {
				player.sendMessage(ChatColor.RED + "You motd is too long. Max characters is 64 long.");
			}
		} else {
			player.sendMessage(ChatColor.RED + "You must be the owner of '" + guildName + "' to set the guild motd.");
		}
	}

	private static void addPlayer(Player player, String guildName, String playerName) {
		
		//Save changes to player configuration file.
		//setPlayerGuild(player, guildName);
	}

	public static void removePlayer(Player player, String guildName, String playerName) {
		
		//Save changes to player configuration file.
		//setPlayerGuild(player, null);
	}

	public static void promotePlayer(Player player, String guildName, String playerName) {

	}

	public static void demotePlayer(Player player, String guildName, String playerName) {

	}

	public static void getMemberList(Player player, String guildName) {

	}

	/**
	 * Gets a boolean value from the configuration.
	 * 
	 * @param guild The guild name
	 * @param value The entry to get.
	 * @return A string form the guilds config file.
	 */
	public static boolean getGuildConfigBoolean(String guild, String value) {
		File configFile = new File(filePath + guild + fileExtension);
		FileConfiguration guildConfig =  YamlConfiguration.loadConfiguration(configFile);
		return guildConfig.getBoolean(value);
	}	

	/**
	 * Gets a boolean value from the configuration.
	 * 
	 * @param guild The guild name
	 * @param value The entry to get.
	 * @return A string form the guilds config file.
	 */
	private static void setGuildConfigBoolean(String guild, String config, boolean value) {
		File configFile = new File(filePath + guild + fileExtension);
		FileConfiguration guildConfig =  YamlConfiguration.loadConfiguration(configFile);
		guildConfig.set(config, value);

		try {
			guildConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Gets an Integer value from the configuration.
	 * 
	 * @param guild The guild name
	 * @param value The entry to get.
	 * @return A string form the guild's configuration file.
	 */ 
	public static int getGuildConfigInt(String guild, String value) {
		File configFile = new File(filePath + guild + fileExtension);
		FileConfiguration guildConfig =  YamlConfiguration.loadConfiguration(configFile);
		return (int) guildConfig.get(value);
	}

	/**
	 * Sets an Integer value from the configuration.
	 * 
	 * @param guild The guild name
	 * @param config The setting to be changed in the config.
	 * @param value The new value for the setting.
	 */ 
	private static void setGuildConfigInt(String guild, String config, int value) {
		File configFile = new File(filePath + guild + fileExtension);
		FileConfiguration guildConfig =  YamlConfiguration.loadConfiguration(configFile);
		guildConfig.set(config, value);

		try {
			guildConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Sets a string value from the configuration.
	 * 
	 * @param guild The guild name
	 * @param value The entry to get.
	 * @return A string form the guild's configuration file.
	 */
	private static void setGuildConfigString(String guild, String config, String value) {
		File configFile = new File(filePath + guild + fileExtension);
		FileConfiguration guildConfig =  YamlConfiguration.loadConfiguration(configFile);
		guildConfig.set(config, value);

		try {
			guildConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Gets a string value from the configuration.
	 * 
	 * @param guild The guild name
	 * @param value The entry to get.
	 * @return A string form the guild's configuration file.
	 */
	private static String getGuildConfigString(String guild, String value) {
		File configFile = new File(filePath + guild + fileExtension);
		FileConfiguration guildConfig =  YamlConfiguration.loadConfiguration(configFile);
		return  (String) guildConfig.get(value);
	}

}
