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
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.managers.MessageManager;
import com.minepile.mprpg.util.LivingEntitySpawnerUtil;

public class MonsterManager {

	//setup instance variables
	public static MPRPG plugin;
	private static LivingEntitySpawnerUtil spawnerUtil = new LivingEntitySpawnerUtil();
	static MonsterManager monsterManagerInstance = new MonsterManager();

	private static World world = Bukkit.getWorld("world");
	
	private static boolean cancelCreatureSpawn = true;
	static String mobTypeIdPath = "plugins/MPRPG/mobs/monsterId.yml";
	
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
		
		//Loop through entity list and remove them.
		//This is mainly for clearing mobs on server reload.
		for (Entity mob : world.getEntities()) {
			if (!(mob.getType().equals(EntityType.ENDER_DRAGON)) && 
					!(mob.getType().equals(EntityType.PLAYER))){
				mob.remove();
			}
		}
		
		//If monster configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(mobTypeIdPath)).exists()){
			createMonsterConfig();
        } else {
        	//lets load the configuration file.
        	File configFile = new File(mobTypeIdPath);
            monsterConfig =  YamlConfiguration.loadConfiguration(configFile);

            //setup and spawn monsters
            spawnMob();
        }
	}	
	
	public static ChatColor stringToColor(String colorName) {
		
		ChatColor color = null;
		
		if (colorName.equalsIgnoreCase("Aqua")) {
			color = ChatColor.AQUA;
		} else if (colorName.equalsIgnoreCase("Black")) {
			color = ChatColor.BLACK;
		} else if (colorName.equalsIgnoreCase("Blue")) {
			color = ChatColor.BLUE;
		} else if (colorName.equalsIgnoreCase("Dark_Aqua")) {
			color = ChatColor.DARK_AQUA;
		} else if (colorName.equalsIgnoreCase("Dark_Blue")) {
			color = ChatColor.DARK_BLUE;
		} else if (colorName.equalsIgnoreCase("Dark_Gray")) {
			color = ChatColor.DARK_GRAY;
		} else if (colorName.equalsIgnoreCase("Dark_Green")) {
			color = ChatColor.DARK_GREEN;
		} else if (colorName.equalsIgnoreCase("Dark_Blue")) {
			color = ChatColor.DARK_PURPLE;
		} else if (colorName.equalsIgnoreCase("Dark_Red")) {
			color = ChatColor.DARK_RED;
		} else if (colorName.equalsIgnoreCase("Gold")) {
			color = ChatColor.GOLD;
		} else if (colorName.equalsIgnoreCase("Gray")) {
			color = ChatColor.GRAY;
		} else if (colorName.equalsIgnoreCase("Green")) {
			color = ChatColor.GREEN;
		} else if (colorName.equalsIgnoreCase("Light_Purple")) {
			color = ChatColor.LIGHT_PURPLE;
		} else if (colorName.equalsIgnoreCase("Red")) {
			color = ChatColor.RED;
		} else if (colorName.equalsIgnoreCase("White")) {
			color = ChatColor.WHITE;
		} else if (colorName.equalsIgnoreCase("Yellow")) {
			color = ChatColor.YELLOW;
		} else {
			color = ChatColor.GRAY;
		}
		
		return color;
	}
	
	public static void setupMob(World world, Location loc, EntityType entity, String color, String name, int lvl, int hp, int runRadius) {	
		
		String colorName = stringToColor(color) + name;
		String mobNameBase = ChatColor.GRAY + "[" + ChatColor.RED + lvl + ChatColor.GRAY +"] " + colorName;
		
		//Set evenStatus for mobs spawning to false to allow them.
		setEventStatus(false);
		
		//Spawn the mob
		spawnerUtil.spawnEntity(world.getName(), loc, entity, mobNameBase);
		
		//Set evenStatus for mobs spawning to true to cancel them.
		setEventStatus(true);
		
		//Setup various mob attributes.
		UUID entityId = spawnerUtil.getEntityID();
		mobName.put(entityId, colorName);
		mobLevel.put(entityId, lvl);
		mobHealthPoints.put(entityId, hp);
		mobMaxHealthPoints.put(entityId, hp);
	}
	
	public static void spawnMob() {
		
		//set mobSpawning to true.
        setEventStatus(true);
		
		
		//Get id
        File configFile = new File(mobTypeIdPath);
        FileConfiguration monsterConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        monsterConfig.get("settings");
        int totalMonsters = monsterConfig.getInt("settings.countTotal");
		
        for (int i = 1; i < totalMonsters; i++) {
	        
	        //Get id config values.
	        File monsterIdConfigFile = new File(mobTypeIdPath);
	        FileConfiguration monsterIdConfig =  YamlConfiguration.loadConfiguration(monsterIdConfigFile);
	        String mobType = monsterIdConfig.getString(Integer.toString(i) + ".mobType");
	        int x = monsterIdConfig.getInt(Integer.toString(i) + ".X");
	        int y = monsterIdConfig.getInt(Integer.toString(i) + ".Y");
	        int z = monsterIdConfig.getInt(Integer.toString(i) + ".Z");
	        
	        //Get mobType config values.
	        File monsterTypeConfigFile = new File(MonsterCreatorManager.getMobTypeFilePath());
	        FileConfiguration monsterTypeConfig =  YamlConfiguration.loadConfiguration(monsterTypeConfigFile);
	        String stringColor = monsterTypeConfig.getString(mobType + ".mobNameColor");
	        EntityType entity = EntityType.fromName(monsterTypeConfig.getString(mobType + ".entity"));
	        int lvl = monsterTypeConfig.getInt(mobType + ".mobLVL");
	        int hp = monsterTypeConfig.getInt(mobType + ".mobHP");
	        int runRadius = monsterTypeConfig.getInt(mobType + ".mobRadius");
	        
	        //misc vars
	        Location loc = new Location(world, x + .5, y + .5, z + .5);
	        
			//Spawn the mob
			setupMob(world, loc, entity, stringColor, mobType, lvl, hp, runRadius);
        }
        
        //setMobSpawning status to false
        setEventStatus(false);
	}
	
	public static void respawnMob () {
		
	}
	
	public static void toggleDamage(UUID id, double damage) {
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

	public static String getMobName(UUID id) {
		return mobName.get(id);
	}

	public static int getMobHealthPoints(UUID id) {
		return mobHealthPoints.get(id);
	}

	public static void setMobHealthPoints(UUID id, int mobHealthPoints) {
		MonsterManager.mobHealthPoints.put(id, mobHealthPoints);
	}

	public static int getMobMaxHealthPoints(UUID id) {
		return mobMaxHealthPoints.get(id);
	}

	public static void setMobMaxHealthPoints(UUID id, int mobMaxHealthPoints) {
		MonsterManager.mobMaxHealthPoints.put(id, mobMaxHealthPoints);
	}
	
	public static boolean getEventStatus() {
		return cancelCreatureSpawn;
	}

	public static void setEventStatus(boolean status) {
		cancelCreatureSpawn = status;
	}
	
	public static String getMobTypeIdPath() {
		return mobTypeIdPath;
	}
	
	//This creates the configuration file that will hold data to save mob attributes.
    private static void createMonsterConfig() {
    	
        File configFile = new File(mobTypeIdPath);
        FileConfiguration monsterConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        monsterConfig.set("settings", "settings");
        monsterConfig.set("settings.countTotal", 0);
        
        try {
        	monsterConfig.save(configFile);	//Save the file.
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
