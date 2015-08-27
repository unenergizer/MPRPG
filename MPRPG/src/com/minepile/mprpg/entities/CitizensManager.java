package com.minepile.mprpg.entities;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.items.ItemIdentifierManager;
import com.minepile.mprpg.items.MerchantManager;
import com.minepile.mprpg.player.PlayerHealthTagManager;
import com.minepile.mprpg.professions.Alchemy;
import com.minepile.mprpg.professions.Blacksmithing;
import com.minepile.mprpg.professions.Cooking;
import com.minepile.mprpg.professions.Fishing;
import com.minepile.mprpg.professions.Herbalism;
import com.minepile.mprpg.professions.Mining;

public class CitizensManager {

	static CitizensManager citizenManagerInstance = new CitizensManager();

	@SuppressWarnings("unused")
	private static MPRPG plugin;

	private static File configFile;
	private static FileConfiguration npcConfig;
	private static String FILE_PATH = "plugins/MPRPG/npc/citizens.yml";

	public static CitizensManager getInstance() {
		return citizenManagerInstance;
	}

	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		//If monster configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(FILE_PATH)).exists()){
			createCitizenConfig();
		} else {
			//lets load the configuration file.
			configFile = new File(FILE_PATH);
			npcConfig =  YamlConfiguration.loadConfiguration(configFile);
		}

		//Apply Citizens Configuration after server startup.
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,  new Runnable() {
			public void run() {
				applyCitizensConfiguration();
				Bukkit.getLogger().info("[MPRPG] Applied Citizens HP.");
			}
		}, 5 * 20L);
	}

	/**
	 * This ENUM defines what type of NPC's exist in game.
	 * 
	 * @author Andrew
	 */
	public static enum CitizenType {

		ALCHEMIST (ChatColor.GOLD + ""),		//Alchemist Trainer
		BLACKSMITH (ChatColor.GOLD + ""),		//Blacksmith Trainer
		COOK (ChatColor.GOLD + ""),				//Cooking Trainer
		FISHER (ChatColor.GOLD + ""),			//Fishing Trainer
		HERBALIST (ChatColor.GOLD + ""),		//Herbalism Trainer
		MINING (ChatColor.GOLD + ""),			//Mining Trainer
		ITEM_IDENTIFIER (ChatColor.GOLD + ""),	//Item Identifier (NPC identifies unidentified items)
		MERCHANT (ChatColor.GOLD + ""),			//Merchant
		NONE (ChatColor.GOLD + ""),				//NONE.  This NPC is just filler.
		QUEST_GIVER (ChatColor.GOLD + "");		//NPC that gives quests.

		private String name;

		CitizenType(String s) {
			this.name = s;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * This will apply HP to a NPC.
	 * The HP is displayed under the NPC's name.
	 */
	private void applyCitizensConfiguration() {
		//Get all online players.
		for (Entity npc : Bukkit.getWorld("world").getEntities()) {

			//Get all Citizen NPC's.
			if (npc instanceof Player && npc.hasMetadata("NPC")) {
				String npcName = ((Player) npc).getDisplayName();
				double npcHP = getCitizenMaxHP(npcName);
				//Set citizen health tag under their name.
				PlayerHealthTagManager.updateNPCHealthTag((Player) npc, npcHP);
			}
		}
	}

	/**
	 * This will trigger when an NPC is left clicked or right clicked.
	 * 
	 * @param player The player that interacted with the NPC.
	 * @param npc The Citizen (plugin) that was clicked.
	 */
	public static void onCitizenInteract(Player player, Player npc) {
		String npcName = npc.getDisplayName();
		CitizenType type = getCitizenType(npcName);

		if (type != null) {
			//Trigger code in appropriate Java Class file.
			if (type == CitizenType.ALCHEMIST) {
				Alchemy.toggleCitizenInteract(player);
			} else if (type == CitizenType.BLACKSMITH) {
				Blacksmithing.toggleCitizenInteract(player);
			} else if (type == CitizenType.COOK) {
				Cooking.toggleCitizenInteract(player);
			} else if (type == CitizenType.FISHER) {
				Fishing.toggleCitizenInteract(player);
			} else if (type == CitizenType.HERBALIST) {
				Herbalism.toggleCitizenInteract(player);
			} else if (type == CitizenType.MINING) {
				Mining.toggleCitizenInteract(player);
			} else if (type == CitizenType.ITEM_IDENTIFIER) {
				ItemIdentifierManager.toggleCitizenInteract(player);
			} else if (type == CitizenType.MERCHANT) {
				MerchantManager.toggleCitizenInteract(player);
			} else if (type == CitizenType.NONE) {
				//TODO
			} else if (type == CitizenType.QUEST_GIVER) {
				//TODO
			}
		} else {
			Bukkit.getLogger().info("[MPRPG] NPC: " + npcName + " does not exist in config.");
		}
	}

	/**
	 * Creates a new configuration file on a players first visit to the server.
	 */
	private static void createCitizenConfig() {

		configFile = new File(FILE_PATH);
		npcConfig =  YamlConfiguration.loadConfiguration(configFile);
		npcConfig.set("Whizzig", "Whizzig");

		//Types: ItemIdentifier, Merchant, Blacksmith, Alchemist, Quest, None
		npcConfig.set("Whizzig.type", "Merchant");

		//The HP to display under the NPC's name.
		npcConfig.set("Whizzig.health", 1000);

		//Spawn location (used for Hologram
		npcConfig.set("Whizzig.spawn.x", 28.5);
		npcConfig.set("Whizzig.spawn.y", 79);
		npcConfig.set("Whizzig.spawn.z", -13.5);

		try {
			npcConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public static double getCitizenMaxHP(String citizenName) {
		String npc = citizenName.replace(" ", "_");
		return npcConfig.getDouble(npc + ".health");
	}

	public static CitizenType getCitizenType(String citizenName) {
		String npc = citizenName.replace(" ", "_");
		String type = npcConfig.getString(npc + ".type");

		if (type.equalsIgnoreCase("ALCHEMIST")) {
			return CitizenType.ALCHEMIST;
		} else if (type.equalsIgnoreCase("BLACKSMITH")) {
			return CitizenType.BLACKSMITH;
		} else if (type.equalsIgnoreCase("COOK")) {
			return CitizenType.COOK;
		} else if (type.equalsIgnoreCase("FISHER")) {
			return CitizenType.FISHER;
		} else if (type.equalsIgnoreCase("HERBALIST")) {
			return CitizenType.HERBALIST;
		} else if (type.equalsIgnoreCase("MINING")) {
			return CitizenType.MINING;
		} else if (type.equalsIgnoreCase("ITEM_IDENTIFIER")) {
			return CitizenType.ITEM_IDENTIFIER;
		} else if (type.equalsIgnoreCase("MERCHANT")) {
			return CitizenType.MERCHANT;
		} else if (type.equalsIgnoreCase("NONE")) {
			return CitizenType.NONE;
		} else if (type.equalsIgnoreCase("QUEST_GIVER")) {
			return CitizenType.QUEST_GIVER;
		} else {
			//Will leave null to report any errors if necessary. 
			return null;
		}
	}

	public static Location getCitizenSpawn(String citizenName) {
		String npc = citizenName.replace(" ", "_");
		double x = npcConfig.getDouble(npc + ".spawn.x");
		double y = npcConfig.getDouble(npc + ".spawn.y");
		double z = npcConfig.getDouble(npc + ".spawn.z");

		return new Location(Bukkit.getWorld("World"), x, y, z);
	}
}
