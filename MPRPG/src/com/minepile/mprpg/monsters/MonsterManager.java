package com.minepile.mprpg.monsters;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.managers.MessageManager;

public class MonsterManager {

	//setup instance variables
	public static MPRPG plugin;
	static MonsterManager monsterManagerInstance = new MonsterManager();

	private static World world = Bukkit.getWorld("world");
	
	static HashMap<UUID, Location> mobSpawnLocation = new HashMap<UUID, Location>();
	static HashMap<UUID, String> mobName = new HashMap<UUID, String>();
	static HashMap<UUID, Integer> mobLevel = new HashMap<UUID, Integer>();
	static HashMap<UUID, Integer> mobHealthPoints = new HashMap<UUID, Integer>();
	static HashMap<UUID, Integer> mobMaxHealthPoints = new HashMap<UUID, Integer>();
	
	//Configuration file that holds monster information.
	FileConfiguration monsterConfig;

	//Create instance
	public static MonsterManager getInstance() {
		return monsterManagerInstance;
	}

	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		//If monster configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File("plugins/MPRPG/mobs/monstersId.yml")).exists()){
			createMonsterConfig();
        } else {
        	//lets load the configuration file.
        	File configFile = new File("plugins/MPRPG/mobs/monsterId.yml");
            monsterConfig =  YamlConfiguration.loadConfiguration(configFile);

            //setup and spawn monsters
            
        }
	}	
	
	public static void setupMonster(UUID id) {
		
		//spawn monster from config at location
		//get monster uuid
		//set monster hp
		//set monster maxhp
		//set monster level
		//set monster name
		
        for (int i = 1; i < 100; i++) {
        	String z = Integer.toString(i);
        	//int totalEXPforLVL = (int) monsterConfig.get(z);
        	//configFishingLevel.put(i, totalEXPforLVL);
        }
        /*
		if (mobHealthPoints.get(id) == null) {
			//Bukkit.broadcastMessage(ChatColor.AQUA + "Added monster: " + id.toString());
			mobHealthPoints.put(id, baseMaxHP);
			mobMaxHealthPoints.put(id, baseMaxHP);
		}
		*/
	}

	public static void toggleDamage(UUID id, double damage) {
		
		if (mobHealthPoints.get(id) == null) {
			setupMonster(id);
		}

		int currentHP = mobHealthPoints.get(id);
		int newHP = (int) (currentHP - damage);
		mobHealthPoints.put(id, newHP);
		renameMob(id);
	}
	
	public static void setMobName(Entity entity) {
		UUID entityId = entity.getUniqueId();
		String monsterName = mobName.get(entityId);
		int monsterLevel = mobLevel.get(entityId);
		String name = ChatColor.GRAY + "[" + ChatColor.RED + monsterLevel + ChatColor.GRAY +"] " + 
				monsterName;
		entity.setCustomName(name);
		entity.setCustomNameVisible(true);
	}
	
	public static void renameMob(UUID id) {
		int maxHP = mobMaxHealthPoints.get(id);
		int hp = mobHealthPoints.get(id);
		int level = mobLevel.get(id);
		int percentHP = (100 * hp) / maxHP;
		String name = ChatColor.GRAY + "[" + ChatColor.RED + level + ChatColor.GRAY +"] " + 
				MessageManager.percentBar(percentHP);

		//Loop through entity list and rename correct entity.
		for (Entity mob : world.getEntities()) {
			if (mob.getUniqueId().equals(id)) {
				mob.setCustomName(name);
				mob.setCustomNameVisible(true);
			}
		}
	}



	public static void toggleDeath(final UUID id) {
		new BukkitRunnable() {
			@Override
			public void run() {
				//Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "Mob removed becahse of death.");
				mobSpawnLocation.remove(id);
				mobName.remove(id);
				mobLevel.remove(id);
				mobHealthPoints.remove(id);
				mobMaxHealthPoints.remove(id);
			}
		}.runTaskLater(plugin, 1); //run after 1 tick
	}

	public static int getMobHealthPoints(UUID id) {
		if (mobHealthPoints.get(id) == null) {
			setupMonster(id);
		}
		return mobHealthPoints.get(id);
	}

	public static void setMobHealthPoints(UUID id, int mobHealthPoints) {
		MonsterManager.mobHealthPoints.put(id, mobHealthPoints);
	}

	public static int getMobMaxHealthPoints(UUID id) {
		if (mobMaxHealthPoints.get(id) == null) {
			setupMonster(id);
		}
		return mobMaxHealthPoints.get(id);
	}

	public static void setMobMaxHealthPoints(UUID id, int mobMaxHealthPoints) {
		MonsterManager.mobMaxHealthPoints.put(id, mobMaxHealthPoints);
	}
	
	//This creates the configuration file that will hold data to save mob attributes.
    private static void createMonsterConfig() {
    	
        File configFile = new File("plugins/MPRPG/mobs/monsterId.yml");
        FileConfiguration monsterConfig =  YamlConfiguration.loadConfiguration(configFile);

        try {
        	monsterConfig.save(configFile);	//Save the file.
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
