package com.minepile.mprpg.entities;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerHealthTagManager;

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

	private void applyCitizensConfiguration() {
		//Get all online players.
		for (Entity npc : Bukkit.getWorld("world").getEntities()) {
			
			//Get all Citizen NPC's.
			if (npc instanceof Player && npc.hasMetadata("NPC")) {
				String npcName = ((Player) npc).getDisplayName().replace(" ", "_");
				double npcHP = getCitizenMaxHP(npcName);
				//Set citizen health tag under their name.
				PlayerHealthTagManager.updateNPCHealthTag((Player) npc, npcHP);
			}
		}
	}

	/**
	 * Creates a new configuration file on a players first visit to the server.
	 */
	private static void createCitizenConfig() {

		configFile = new File(FILE_PATH);
		npcConfig =  YamlConfiguration.loadConfiguration(configFile);
		npcConfig.set("Whizzig", "Whizzig");
		npcConfig.set("Whizzig.health", 1000);
		npcConfig.set("Whizzig.gamemode", 1);
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
		Bukkit.broadcastMessage(citizenName);
		return npcConfig.getDouble(citizenName + ".health");
	}

	public static int getCitizenGamemode(String citizenName) {
		return npcConfig.getInt(citizenName + ".gamemode");
	}

	public static Location getCitizenSpawn(String citizenName) {
		double x = npcConfig.getDouble(citizenName + ".spawn.x");
		double y = npcConfig.getDouble(citizenName + ".spawn.y");
		double z = npcConfig.getDouble(citizenName + ".spawn.z");

		return new Location(Bukkit.getWorld("World"), x, y, z);
	}
}
