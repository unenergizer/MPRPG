package com.minepile.mprpg.clans;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerManager;

public class GangManager {

	//setup instance variables
	public static MPRPG plugin;
	static GangManager gangManagerInstance = new GangManager();
	static String gangFilePathStart = "plugins/MPRPG/gangs/";
	static String gangFilePathEnd = ".yml";

	//Create instance
	public static GangManager getInstance() {
		return gangManagerInstance;
	}

	//Setup GangManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	
	
	/**
	 * Checks to see if the player owns a gang.
	 * <p>
	 * This information comes from the Players configuration file. MPRPG/players/playerUUID
	 * 
	 * @param player The player who might own a gang.
	 * @return Return a boolean true if player owns a gang.
	 */
	public static boolean getGangOwner(Player player) {
		if (PlayerManager.getPlayerConfigBoolean(player, "gang.owner") == true) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Sets the players gang.
	 * <p>
	 * This information comes from the Players configuration file. MPRPG/players/playerUUID
	 * 
	 * @param player The player who might be in a gang.
	 * @param gangName Name The name of the gang.
	 */
	public static void setGangOwner(Player player, boolean value) {
		PlayerManager.setPlayerConfigBoolean(player, "gang.owner", value);
	}
	
	/**
	 * Gets the gang the player is currently in.
	 * <p>
	 * This information comes from the Players configuration file. MPRPG/players/playerUUID
	 * 
	 * @param player The player who might be in a gang.
	 * @return Name of the gang the player is in.
	 */
	public static String getPlayerGang(Player player) {
		if (PlayerManager.getPlayerConfigString(player, "gang.name") != null) {
			return PlayerManager.getPlayerConfigString(player, "gang.name");
		} else {
			return null;
		}
	}
	
	/**
	 * Sets the players gang.
	 * <p>
	 * This information comes from the Players configuration file. MPRPG/players/playerUUID
	 * @param player The player who might be in a gang.
	 * @param gangName Name The name of the gang.
	 */
	public static void setPlayerGang(Player player, String gangName) {
		PlayerManager.setPlayerConfigString(player, "gang.name", gangName);
	}
	
	/**
	 * This will create a new gang, if a gang with the same name doesn't already exist.
	 * 
	 * @param player The player who is making the gang. This player will become the gang owner.
	 * @param gangName The name of the gang.
	 */
	public static void addGang(Player player, String gangName) {

		File configFile = new File(gangFilePathStart + gangName + gangFilePathEnd);

		//Check to make sure the new gang doesn't already exist.
		if (!configFile.exists() && !configFile.isDirectory()) { 
			String uuid = player.getUniqueId().toString();
			String playerName = player.getName();

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();

			FileConfiguration gangConfig =  YamlConfiguration.loadConfiguration(configFile);
			gangConfig.set("gangName", gangName);
			gangConfig.set("gangTag", null);
			gangConfig.set("gangMOTD", null);
			gangConfig.set("gangDisbanded", false);
			gangConfig.set("gangSize", 25);
			gangConfig.set("gangCreationDate", dateFormat.format(date));
			gangConfig.set("gangDisbandedDate", null);
			gangConfig.set("gangOwner", playerName);
			gangConfig.set("gangOwnerUUID", uuid);
			gangConfig.set("gangOfficer", null);
			gangConfig.set("gangMembers", null);

			try {
				gangConfig.save(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Update player file.
			setPlayerGang(player, gangName);
			setGangOwner(player, true);
			
			player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + 
					"Your have created a new gang called " + 
					ChatColor.WHITE + ChatColor.BOLD + ChatColor.UNDERLINE + gangName + 
					ChatColor.YELLOW + ChatColor.BOLD + ".");
			player.sendMessage(" ");
		} else {
			player.sendMessage(ChatColor.RED + "This gang already exists. Please choose a different gang name.");
		}
	}

	/**
	 * This will disband a gang, making it no longer usable.  Only gang owners can do this.
	 * 
	 * @param player The gang owner.
	 * @param gangName The name of the gang to disband.
	 */
	public static void disbandGang(Player player, String gangName) {
		String playerName = player.getName();
		String playerUUID = player.getUniqueId().toString();
		String gangOwnerUUID = getGangConfigString(gangName, "gangOwnerUUID");

		if (playerUUID.equals(gangOwnerUUID)) {

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();

			setGangConfigBoolean(gangName, "gangDisbanded", true);
			setGangConfigString(gangName, "gangDisbandedDate", dateFormat.format(date));
			
			//Save changes to player configuration file.
			setGangOwner(player, false);
			setPlayerGang(player, null);

			player.sendMessage(ChatColor.YELLOW + playerName + ", your gang " + gangName + " has been disbanded.");
			player.sendMessage(ChatColor.YELLOW + "The name of this can can not be used for another 30 days.");
		} else {
			player.sendMessage(ChatColor.RED + "You must be the owner of '" + gangName + "' to disband this gang.");
		}
	}

	/**
	 * This will delete the gang file from the file-system forever.
	 * 
	 * @param player The player who is requesting full file deletion.
	 * @param gangName The name of the gang to be deleted.
	 */
	public static void deleteGang(Player player, String gangName) {
		File configFile = new File(gangFilePathStart + gangName + gangFilePathEnd);
		configFile.delete();
	}

	//TODO: Let gangs upgrade their size and other attributes.
	public static void upgradeGang(Player player, String gangName) {

	}

	/**
	 * Sets the gang tag for a given gang.
	 * <p>
	 * Example:  [TAG] playerName
	 * @param player The gang owner.
	 * @param gangName The name of the gang.
	 * @param tag The 3 digit long gang tag.
	 */
	public static void setGangTag(Player player, String gangName, String tag) {
		String playerName = player.getName();
		String playerUUID = player.getUniqueId().toString();
		String gangOwnerUUID = getGangConfigString(gangName, "gangOwnerUUID");
		
		//Check to make sure the gang name length isn't longer than 24 characters.
		if (gangName.length() > 24) {
			//Make sure the gang owner is setting the tag.
			if (playerUUID.equals(gangOwnerUUID)) {
				//Make sure the tag is 3 characters long.
				if (tag.length() == 3) {
					//Make sure tag only contains letters and numbers.
					if (Pattern.matches("[^a-zA-Z0-9]", tag) == true) {

						setGangConfigString(gangName, "gangTag", tag);

						player.sendMessage(ChatColor.GREEN + playerName + ", your gang tag has been successfully added.");
						player.sendMessage(ChatColor.YELLOW + "Your gang tag will now appear next to your name in chat.");
					} else {
						player.sendMessage(ChatColor.RED + "Your gang tag can only contain letters and numbers Do not use special characters.");
					}
				} else if (tag.length() > 3) {
					player.sendMessage(ChatColor.RED + "Your gang tag is too long. It must be 3 characters long.");
				} else {
					player.sendMessage(ChatColor.RED + "Your gang tag is too short. It must be 3 characters long.");
				}
			} else {
				player.sendMessage(ChatColor.RED + "You must be the owner of '" + gangName + "' to add a gang tag.");
			}
		} else {
			player.sendMessage(ChatColor.RED + "Your gang name is too long.  It must be under 24 characters long.");
		}
	}

	/**
	 * Sets a MOTD or "message of the day" for the given can.
	 * @param player The player who wants to set the gang motd.
	 * @param gangName The name of the gang.
	 * @param motd The message of the day.
	 */
	public static void setMotd(Player player, String gangName, String motd) {
		String playerName = player.getName();
		String playerUUID = player.getUniqueId().toString();
		String gangOwnerUUID = getGangConfigString(gangName, "gangOwnerUUID");

		//Check to make sure the gang owner is making the changes.
		if (playerUUID.equals(gangOwnerUUID)) {
			//Check to make sure the motd is not more than 64 characters long.
			if (motd.length() < 64) {
				setGangConfigString(gangName, "gangMOTD", motd);

				player.sendMessage(ChatColor.GREEN + playerName + ", your motd has been successfully added.");
				player.sendMessage(ChatColor.YELLOW + "Your motd will now appear every time gang members login.");
			} else {
				player.sendMessage(ChatColor.RED + "You motd is too long. Max characters is 64 long.");
			}
		} else {
			player.sendMessage(ChatColor.RED + "You must be the owner of '" + gangName + "' to set the gang motd.");
		}
	}

	public static void addPlayer(Player player, String gangName, String playerName) {
		
		//Save changes to player configuration file.
		setPlayerGang(player, gangName);
	}

	public static void removePlayer(Player player, String gangName, String playerName) {
		
		//Save changes to player configuration file.
		setPlayerGang(player, null);
	}

	public static void promotePlayer(Player player, String gangName, String playerName) {

	}

	public static void demotePlayer(Player player, String gangName, String playerName) {

	}

	public static void getMemberList(Player player, String gangName) {

	}

	/**
	 * Gets a boolean value from the configuration.
	 * 
	 * @param gang The gang name
	 * @param value The entry to get.
	 * @return A string form the gangs config file.
	 */
	public static boolean getGangConfigBoolean(String gang, String value) {
		File configFile = new File(gangFilePathStart + gang + gangFilePathEnd);
		FileConfiguration gangConfig =  YamlConfiguration.loadConfiguration(configFile);
		return gangConfig.getBoolean(value);
	}	

	/**
	 * Gets a boolean value from the configuration.
	 * 
	 * @param gang The gang name
	 * @param value The entry to get.
	 * @return A string form the gangs config file.
	 */
	public static void setGangConfigBoolean(String gang, String config, boolean value) {
		File configFile = new File(gangFilePathStart + gang + gangFilePathEnd);
		FileConfiguration gangConfig =  YamlConfiguration.loadConfiguration(configFile);
		gangConfig.set(config, value);

		try {
			gangConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Gets an Integer value from the configuration.
	 * 
	 * @param gang The gang name
	 * @param value The entry to get.
	 * @return A string form the gang's configuration file.
	 */ 
	public static int getGangConfigInt(String gang, String value) {
		File configFile = new File(gangFilePathStart + gang + gangFilePathEnd);
		FileConfiguration gangConfig =  YamlConfiguration.loadConfiguration(configFile);
		return (int) gangConfig.get(value);
	}

	/**
	 * Sets an Integer value from the configuration.
	 * 
	 * @param gang The gang name
	 * @param config The setting to be changed in the config.
	 * @param value The new value for the setting.
	 */ 
	public static void setGangConfigInt(String gang, String config, int value) {
		File configFile = new File(gangFilePathStart + gang + gangFilePathEnd);
		FileConfiguration gangConfig =  YamlConfiguration.loadConfiguration(configFile);
		gangConfig.set(config, value);

		try {
			gangConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Sets a string value from the configuration.
	 * 
	 * @param gang The gang name
	 * @param value The entry to get.
	 * @return A string form the gang's configuration file.
	 */
	public static void setGangConfigString(String gang, String config, String value) {
		File configFile = new File(gangFilePathStart + gang + gangFilePathEnd);
		FileConfiguration gangConfig =  YamlConfiguration.loadConfiguration(configFile);
		gangConfig.set(config, value);

		try {
			gangConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Gets a string value from the configuration.
	 * 
	 * @param gang The gang name
	 * @param value The entry to get.
	 * @return A string form the gang's configuration file.
	 */
	public static String getGangConfigString(String gang, String value) {
		File configFile = new File(gangFilePathStart + gang + gangFilePathEnd);
		FileConfiguration gangConfig =  YamlConfiguration.loadConfiguration(configFile);
		return  (String) gangConfig.get(value);
	}

}
