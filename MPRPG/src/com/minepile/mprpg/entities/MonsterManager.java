package com.minepile.mprpg.entities;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.chat.MessageManager;
import com.minepile.mprpg.items.LootTableMobManager;
import com.minepile.mprpg.util.LivingEntityEquipItems;

public class MonsterManager {

	//setup instance variables
	public static MPRPG plugin;
	static MonsterManager monsterManagerInstance = new MonsterManager();

	//@SuppressWarnings("unused")
	//private static int taskID01;
	@SuppressWarnings("unused")
	private static int taskID02;

	private static World world = Bukkit.getWorld("world");

	private static boolean cancelCreatureSpawn = true;
	static String mobTypeIdPath = "plugins/MPRPG/mobs/monsterId.yml";

	//Entity respawn
	public static int entityRespawnTime = 60 * 5; 						//Time it takes for an entity to respawn. 120 = 2 Minutes (60*2)
	public static int entityRespawnRate = 5; 						//Default between 90 and 200 seconds intervals.
	public static int entityRespawnTicks = entityRespawnRate * 20; 	//Time it takes for an block to regenerate.
	public static HashMap<Integer, Integer> respawnTimeLeft = new HashMap<Integer, Integer>(); //ID > TimeLeft

	//Entity attributes
	static HashMap<UUID, Integer> mobId = new HashMap<UUID, Integer>();
	static HashMap<UUID, Location> mobSpawnLocation = new HashMap<UUID, Location>();
	static HashMap<UUID, String> mobName = new HashMap<UUID, String>();
	static HashMap<UUID, Integer> mobLevel = new HashMap<UUID, Integer>();
	static HashMap<UUID, Integer> mobHealthPoints = new HashMap<UUID, Integer>();
	static HashMap<UUID, Integer> mobMaxHealthPoints = new HashMap<UUID, Integer>();
	static HashMap<UUID, Integer> respawnTime = new HashMap<UUID, Integer>();
	static HashMap<UUID, String> lootTable = new HashMap<UUID, String>();

	//Configuration file that holds monster information.
	static File configFile;
	static FileConfiguration monsterIdConfig;

	//Create instance
	public static MonsterManager getInstance() {
		return monsterManagerInstance;
	}

	//Setup MonsterManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		//If monster configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(mobTypeIdPath)).exists()){
			createMonsterConfig();
		} else {
			//Load the configuration file.
			configFile = new File(mobTypeIdPath);
			monsterIdConfig =  YamlConfiguration.loadConfiguration(configFile);

			//Setup and spawn monsters
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	            public void run() {
	                removeAllMobs();
	            }
	        }, 2 * 20L);
			
			//Spawn all the mobs.
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	            public void run() {
	            	spawnAllMobs();
	            }
	        }, 6 * 20L);
			
			//Start mob respawn thread.
			respawnMob();
		}
	}
	
	/**
	 * This will disable this class.
	 */
	public static void disable() {
		removeAllMobs();
	}

	/**
	 * Respawns all the mobs in the world after a Server Reload.
	 */
	private static void spawnAllMobs() {
		monsterIdConfig.get("settings");
		int totalMonsters = monsterIdConfig.getInt("settings.countTotal");

		for (int i = 1; i <= totalMonsters; i++) {
			setupEntitie(i);
		}
	}
	
	/**
	 * Removes all the mobs in the world.
	 */
	private static void removeAllMobs() {
		for (Entity mob : Bukkit.getWorld("world").getEntities()) {
			if (!(mob.getType().equals(EntityType.ENDER_DRAGON)) && 
					!(mob.getType().equals(EntityType.PLAYER)) && 
					!(mob.getType().equals(EntityType.ITEM_FRAME))) {
				mob.remove();
			}
		}
	}

	/**
	 * Spawns a an entite in the world.
	 * 
	 * @param id 
	 */
	public static void setupEntitie(int id) {

		//Get id config values.
		String name = monsterIdConfig.getString(Integer.toString(id) + ".mobType");
		int x = monsterIdConfig.getInt(Integer.toString(id) + ".X");
		int y = monsterIdConfig.getInt(Integer.toString(id) + ".Y");
		int z = monsterIdConfig.getInt(Integer.toString(id) + ".Z");

		//Get mobType config values.
		String stringColor = MonsterCreatorManager.getMonsterConfig().getString(name + ".mobNameColor");
		EntityType entityType = EntityType.fromName(MonsterCreatorManager.getMonsterConfig().getString(name + ".entity"));
		int lvl = MonsterCreatorManager.getMonsterConfig().getInt(name + ".mobLVL");
		int hp = MonsterCreatorManager.getMonsterConfig().getInt(name + ".mobHP");
		int runRadius = MonsterCreatorManager.getMonsterConfig().getInt(name + ".mobRadius");
		String loot = MonsterCreatorManager.getMonsterConfig().getString(name + ".lootTable");
		
		//Get equipment
		boolean useSkull = MonsterCreatorManager.getMonsterConfig().getBoolean(name + ".useSkull");
		String skull = MonsterCreatorManager.getMonsterConfig().getString(name + ".lootTable");
		boolean useHelm = MonsterCreatorManager.getMonsterConfig().getBoolean(name + ".helm");
		boolean useChest = MonsterCreatorManager.getMonsterConfig().getBoolean(name + ".chest");
		boolean useLegs = MonsterCreatorManager.getMonsterConfig().getBoolean(name + ".legs");
		boolean useBoots = MonsterCreatorManager.getMonsterConfig().getBoolean(name + ".boots");
		boolean useWeapon = MonsterCreatorManager.getMonsterConfig().getBoolean(name + ".weapon");
		
		//misc vars
		Location loc = new Location(world, x + .5, y + .5, z + .5);
		
		//Spawn the mob
		//spawnEntitie(world, loc, entity, stringColor, mobType, lvl, hp, runRadius, id, loot);
		String colorName = (stringToColor(stringColor) + name);
		String fixedName = colorName.replaceAll("_", " ");
		String mobNameBase = ChatColor.GRAY + "[" + ChatColor.RED + lvl + ChatColor.GRAY +"] " + fixedName;

		//Set evenStatus for mobs spawning to false to allow them.
		setEventStatus(false);

		//Spawn the mob
		LivingEntity entity = (LivingEntity) Bukkit.getWorld("world").spawnEntity(loc, entityType);
		entity.setCustomName(mobNameBase);
		entity.setCustomNameVisible(true);
		entity.setRemoveWhenFarAway(false);
		entity.setCanPickupItems(false);	
		
		//Set entity equipment (if possible).
		if (useSkull) {
			ItemStack playerSkull = new ItemStack(Material.SKULL_ITEM, 1);
			playerSkull.setDurability((short)3);
		    SkullMeta skullMeta = (SkullMeta)playerSkull.getItemMeta();
		    skullMeta.setOwner(skull);
		    playerSkull.setItemMeta(skullMeta);
		    
		    
			LivingEntityEquipItems.setHelmet(entity, playerSkull);
		} else if (useHelm) {
			ItemStack helm = new ItemStack(Material.CARPET);
			LivingEntityEquipItems.setHelmet(entity, helm);
		}
		
		if (useChest) {
			ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
			LivingEntityEquipItems.setChestplate(entity, chest);
		}
		
		if (useLegs) {
			ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
			LivingEntityEquipItems.setLeggings(entity, legs);
		}
		
		if (useBoots) {
			ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
			LivingEntityEquipItems.setBoots(entity, boots);
		}
		
		if (useWeapon) {
			ItemStack boots = new ItemStack(Material.IRON_SPADE);
			LivingEntityEquipItems.setWeapon(entity, boots);
		}

		//Set evenStatus for mobs spawning to true to cancel them.
		setEventStatus(true);

		//Setup various mob attributes.
		UUID entityId = entity.getUniqueId();
		
		mobId.put(entityId, id);
		mobName.put(entityId, fixedName);
		mobLevel.put(entityId, lvl);
		mobHealthPoints.put(entityId, hp);
		mobMaxHealthPoints.put(entityId, hp);
		lootTable.put(entityId, loot);
	}

	/**
	 * This will respawn the Entitie after a given amount of time.
	 * <p>
	 * This thread is started when this class is loaded at onEnable!
	 * 
	 * @param id The ID number of the mob to respawn contained in the monsterId.yml file.
	 */
	public static void respawnMob () {
		//Respawn mob
		taskID02 = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				//Lets loop through the hashMaps to find any entities that need to be spawned.

				if (respawnTimeLeft.isEmpty() == false) {
					Iterator<Entry<Integer, Integer>> it = respawnTimeLeft.entrySet().iterator();
				    while (it.hasNext()) {
				    	Entry<Integer, Integer> pair = it.next();
						int spawnID = pair.getKey();
						int timeLeft = pair.getValue();

						if(timeLeft <= 0) {

							//Spawn entity
							setupEntitie(spawnID);

							//Remove hashmap and update vars.
							it.remove();

						} else {
							respawnTimeLeft.put(spawnID, timeLeft - entityRespawnRate);
						}
					}
				}
			}
		}, 0L, entityRespawnTicks);
	}

	/**
	 * This will remove HP from the entities Health HashMap.
	 * 
	 * @param id The UUID of the entitie to receive HP reduction.
	 * @param damage The amount of HP to remove from the HashMap.
	 */
	public static void toggleDamage(UUID id, double damage) {
		int currentHP = mobHealthPoints.get(id);
		int newHP = (int) (currentHP - damage);
		mobHealthPoints.put(id, newHP);
		//The entitie took damage, show HP bar instead of Entitie name.
		renameEntitie(id);
	}

	/**
	 * This will give the entitie a custom name.
	 * 
	 * @param entity The entitie that is going to be given a custom name.
	 */
	public static void setEntitieName(Entity entity) {
		UUID entityId = entity.getUniqueId();
		String monsterName = mobName.get(entityId);
		int monsterLevel = mobLevel.get(entityId);
		String name = ChatColor.GRAY + "[" + ChatColor.RED + monsterLevel + ChatColor.GRAY +"] " + 
				monsterName;
		entity.setCustomName(name);
		entity.setCustomNameVisible(true);
	}

	/**
	 * This will put the entities health as their name.
	 * 
	 * @param id The UUID of the entitie to grab from the HashMap's.
	 */
	public static void renameEntitie(UUID id) {
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

	/**
	 * This removes the dead Entitie from all the HashMap's that are tracking it.
	 * 
	 * @param id The entities UUID to be removed from the HashMap's
	 * @param x The Entities X coordinate in the world.
	 * @param y The Entities Y coordinate in the world.
	 * @param z The Entities Z coordinate in the world.
	 */
	public static void toggleEntitieDeath(final UUID id, int x, int y, int z) {
		//Make sure the mob exists in the hashmap.
		//If the mob does not exist, do nothing.
		if (mobId.get(id) != null) {
			//Setup variables
			int configId = mobId.get(id);
			String lootTableName = lootTable.get(id);
			Location loc = new Location(world, x, y+1, z);

			//Drop Item(s)
			LootTableMobManager.toggleLootTableDrop(lootTableName, loc);

			//Remove mob
			mobSpawnLocation.remove(id);
			mobId.remove(id);
			mobName.remove(id);
			mobLevel.remove(id);
			mobHealthPoints.remove(id);
			mobMaxHealthPoints.remove(id);
			
			//Setup mob for respawning.
			//Add spawnID to the Hashmap.
			respawnTimeLeft.put(configId, entityRespawnTime);
		}
	}

	/**
	 * Converts a String color to a ChatColor enumerator.
	 * @param colorName A String that represents a ChatColor.
	 * @return A ChatColor enumerator.
	 */
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

	/**
	 * This creates the configuration file that will hold data to save mob attributes.
	 */
	private static void createMonsterConfig() {

		configFile = new File(mobTypeIdPath);
		monsterIdConfig =  YamlConfiguration.loadConfiguration(configFile);

		monsterIdConfig.set("settings", "settings");
		monsterIdConfig.set("settings.countTotal", 0);

		try {
			monsterIdConfig.save(configFile);	//Save the file.
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static FileConfiguration getMonsterIdConfig() {
		return monsterIdConfig;
	}

	public static String getMobName(UUID id) {
		return mobName.get(id);
	}

	public static int getMobHealthPoints(UUID id) {
		if (mobHealthPoints.get(id) == null) {
			return 0;
		} else {
			return mobHealthPoints.get(id);
		}
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
}
