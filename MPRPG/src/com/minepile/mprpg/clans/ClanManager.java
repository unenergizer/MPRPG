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

public class ClanManager {

	//setup instance variables
	public static MPRPG plugin;
	static ClanManager clanManagerInstance = new ClanManager();
	static String clanFilePathStart = "plugins/MPRPG/clans/";
	static String clanFilePathEnd = ".yml";

	//Create instance
	public static ClanManager getInstance() {
		return clanManagerInstance;
	}

	//Setup ClanManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	

	/**
	 * This will create a new clan, if a clan with the same name doesn't already exist.
	 * 
	 * @param player The player who is making the clan. This player will become the clan owner.
	 * @param clanName The name of the clan.
	 */
	public static void addClan(Player player, String clanName) {

		File configFile = new File(clanFilePathStart + clanName + clanFilePathEnd);
		
		//Check to make sure the new clan doesn't already exist.
		if (!configFile.exists() && !configFile.isDirectory()) { 
			String uuid = player.getUniqueId().toString();
			String playerName = player.getName();

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();

			FileConfiguration clanConfig =  YamlConfiguration.loadConfiguration(configFile);
			clanConfig.set("clanName", clanName);
			clanConfig.set("clanTag", null);
			clanConfig.set("clanMOTD", null);
			clanConfig.set("clanDisbanded", false);
			clanConfig.set("clanSize", 25);
			clanConfig.set("clanCreationDate", dateFormat.format(date));
			clanConfig.set("clanDisbandedDate", null);
			clanConfig.set("clanOwner", playerName);
			clanConfig.set("clanOwnerUUID", uuid);
			clanConfig.set("clanOfficer", null);
			clanConfig.set("clanMembers", null);

			try {
				clanConfig.save(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			player.sendMessage(ChatColor.GREEN + playerName + ", your clan " + clanName + " has been successfully created.");
		} else {
			player.sendMessage(ChatColor.RED + "This clan already exists. Please choose a different clan name.");
		}
	}
	
	/**
	 * This will disband a clan, making it no longer usable.  Only clan owners can do this.
	 * 
	 * @param player The clan owner.
	 * @param clanName The name of the clan to disband.
	 */
	public static void disbandClan(Player player, String clanName) {
		String playerName = player.getName();
		String playerUUID = player.getUniqueId().toString();
		String clanOwnerUUID = getClanConfigString(clanName, "clanOwnerUUID");
		
		if (playerUUID.equals(clanOwnerUUID)) {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			
			setClanConfigBoolean(clanName, "clanDisbanded", true);
			setClanConfigString(clanName, "clanDisbandedDate", dateFormat.format(date));
			
			player.sendMessage(ChatColor.YELLOW + playerName + ", your clan " + clanName + " has been disbanded.");
			player.sendMessage(ChatColor.YELLOW + "The name of this can can not be used for another 30 days.");
		} else {
			player.sendMessage(ChatColor.RED + "You must be the owner of '" + clanName + "' to disband this clan.");
		}
	}

	/**
	 * This will delete the clan file from the file-system forever.
	 * 
	 * @param player The player who is requesting full file deletion.
	 * @param clanName The name of the clan to be deleted.
	 */
	public static void deleteClan(Player player, String clanName) {
		File configFile = new File(clanFilePathStart + clanName + clanFilePathEnd);
		configFile.delete();
	}
	
	//TODO: Let clans upgrade their size and other attributes.
	public static void upgradeClan(Player player, String clanName) {

	}
	
	/**
	 * Sets the clan tag for a given clan.
	 * <p>
	 * Example:  [TAG] playerName
	 * @param player The clan owner.
	 * @param clanName The name of the clan.
	 * @param tag The 3 digit long clan tag.
	 */
	public static void setClanTag(Player player, String clanName, String tag) {
		String playerName = player.getName();
		String playerUUID = player.getUniqueId().toString();
		String clanOwnerUUID = getClanConfigString(clanName, "clanOwnerUUID");
		
		//Make sure the clan owner is setting the tag.
		if (playerUUID.equals(clanOwnerUUID)) {
			//Make sure the tag is 3 characters long.
			if (tag.length() == 3) {
				//Make sure tag only contains letters and numbers.
				if (Pattern.matches("[^a-zA-Z0-9]", tag) == true) {
					
					setClanConfigString(clanName, "clanTag", tag);
			
					player.sendMessage(ChatColor.GREEN + playerName + ", your clan tag has been successfully added.");
					player.sendMessage(ChatColor.YELLOW + "Your clan tag will now appear next to your name in chat.");
				} else {
					player.sendMessage(ChatColor.RED + "Your clan tag can only contain letters and numbers Do not use special characters.");
				}
			} else if (tag.length() > 3) {
				player.sendMessage(ChatColor.RED + "Your clan tag is too long. It must be 3 characters long.");
			} else {
				player.sendMessage(ChatColor.RED + "Your clan tag is too short. It must be 3 characters long.");
			}
		} else {
			player.sendMessage(ChatColor.RED + "You must be the owner of '" + clanName + "' to add a clan tag.");
		}
	}
	
	/**
	 * Sets a MOTD or "message of the day" for the given can.
	 * @param player The player who wants to set the clan motd.
	 * @param clanName The name of the clan.
	 * @param motd The message of the day.
	 */
	public static void setMotd(Player player, String clanName, String motd) {
		String playerName = player.getName();
		String playerUUID = player.getUniqueId().toString();
		String clanOwnerUUID = getClanConfigString(clanName, "clanOwnerUUID");
		
		//Check to make sure the clan owner is making the changes.
		if (playerUUID.equals(clanOwnerUUID)) {
			//Check to make sure the motd is not more than 64 characters long.
			if (motd.length() < 64) {
				setClanConfigString(clanName, "clanMOTD", motd);
			
				player.sendMessage(ChatColor.GREEN + playerName + ", your motd has been successfully added.");
				player.sendMessage(ChatColor.YELLOW + "Your motd will now appear every time clan members login.");
			} else {
				player.sendMessage(ChatColor.RED + "You motd is too long. Max characters is 64 long.");
			}
		} else {
			player.sendMessage(ChatColor.RED + "You must be the owner of '" + clanName + "' to set the clan motd.");
		}
	}

	public static void addPlayer(Player player, String clanName, String playerName) {

	}

	public static void removePlayer(Player player, String clanName, String playerName) {

	}

	public static void promotePlayer(Player player, String clanName, String playerName) {

	}

	public static void demotePlayer(Player player, String clanName, String playerName) {

	}

	public static void getMemberList(Player player, String clanName) {

	}

	/**
	 * Gets a boolean value from the configuration.
	 * 
	 * @param clan The clan name
	 * @param value The entry to get.
	 * @return A string form the clans config file.
	 */
	public static boolean getClanConfigBoolean(String clan, String value) {
		File configFile = new File(clanFilePathStart + clan + clanFilePathEnd);
		FileConfiguration clanConfig =  YamlConfiguration.loadConfiguration(configFile);
		return clanConfig.getBoolean(value);
	}	

	/**
	 * Gets a boolean value from the configuration.
	 * 
	 * @param clan The clan name
	 * @param value The entry to get.
	 * @return A string form the clans config file.
	 */
	public static void setClanConfigBoolean(String clan, String config, boolean value) {
		File configFile = new File(clanFilePathStart + clan + clanFilePathEnd);
		FileConfiguration clanConfig =  YamlConfiguration.loadConfiguration(configFile);
		clanConfig.set(config, value);

		try {
			clanConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Gets an Integer value from the configuration.
	 * 
	 * @param clan The clan name
	 * @param value The entry to get.
	 * @return A string form the clan's configuration file.
	 */ 
	public static int getClanConfigInt(String clan, String value) {
		File configFile = new File(clanFilePathStart + clan + clanFilePathEnd);
		FileConfiguration clanConfig =  YamlConfiguration.loadConfiguration(configFile);
		return (int) clanConfig.get(value);
	}

	/**
	 * Sets an Integer value from the configuration.
	 * 
	 * @param clan The clan name
	 * @param config The setting to be changed in the config.
	 * @param value The new value for the setting.
	 */ 
	public static void setClanConfigInt(String clan, String config, int value) {
		File configFile = new File(clanFilePathStart + clan + clanFilePathEnd);
		FileConfiguration clanConfig =  YamlConfiguration.loadConfiguration(configFile);
		clanConfig.set(config, value);

		try {
			clanConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Sets a string value from the configuration.
	 * 
	 * @param clan The clan name
	 * @param value The entry to get.
	 * @return A string form the clan's configuration file.
	 */
	public static void setClanConfigString(String clan, String config, String value) {
		File configFile = new File(clanFilePathStart + clan + clanFilePathEnd);
		FileConfiguration clanConfig =  YamlConfiguration.loadConfiguration(configFile);
		clanConfig.set(config, value);

		try {
			clanConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Gets a string value from the configuration.
	 * 
	 * @param clan The clan name
	 * @param value The entry to get.
	 * @return A string form the clan's configuration file.
	 */
	public static String getClanConfigString(String clan, String value) {
		File configFile = new File(clanFilePathStart + clan + clanFilePathEnd);
		FileConfiguration clanConfig =  YamlConfiguration.loadConfiguration(configFile);
		return  (String) clanConfig.get(value);
	}

}
