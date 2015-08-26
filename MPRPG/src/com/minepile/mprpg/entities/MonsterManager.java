package com.minepile.mprpg.entities;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
	public static int entityRespawnTime = 60 * 5; 					//Time it takes for an entity to respawn. 120 = 2 Minutes (60*2)
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
	static HashMap<UUID, Integer> mobMoveRadius = new HashMap<UUID, Integer>();
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
		}
		
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

		//Teleport mobs if they move to far away from thier spawn point.
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin,  new Runnable() {
			public void run() {
				teleportEntity();
			}
		}, 0L, 5 * 20);

		//Start mob respawn thread.
		//This thread will attempt to respawn a mob if one has died.
		//If no mobs have died, then nothing will happen.
		//Remove this if you want to disable mob respawns.
		respawnMob();
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

		//Load the configuration file.
		File file = new File(mobTypeIdPath);
		FileConfiguration config =  YamlConfiguration.loadConfiguration(file);

		config.get("settings");
		int totalMonsters = config.getInt("settings.countTotal");

		for (int i = 1; i <= totalMonsters; i++) {
			setupEntity(i);
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

	private static void teleportEntity() {
		if (mobSpawnLocation.isEmpty() == false && mobMoveRadius.isEmpty() == false) {
			Iterator<Entry<UUID, Integer>> it = mobMoveRadius.entrySet().iterator();
			while (it.hasNext()) {

				Entry<UUID, Integer> pair = it.next();
				UUID uuid = pair.getKey();
				Integer radius = pair.getValue();
				
				//Find the specific mob in the world with the same UUID.
				for (Entity entity : world.getEntities()) {
					if (entity.getUniqueId().equals(uuid)) {
						
						//Original spawn location
						Location locSpawn = mobSpawnLocation.get(uuid);
						double x = locSpawn.getX();
						double y = locSpawn.getY();
						double z = locSpawn.getZ();

						//Current entity location
						Location locCurrent = entity.getLocation();
						double currentX = locCurrent.getX();
						double currentY = locCurrent.getY();
						double currentZ = locCurrent.getZ();

						//Test if mob is past the max radius for X.
						if ( x > 0) {
							if (currentX > x + radius) {
								entity.teleport(locSpawn);
							}
						} else {
							if (currentX < x - radius) {
								entity.teleport(locSpawn);
							}
						}
						//Test if mob is past the max radius for Y.
						if ( y > 0) {
							if (currentY > y + radius) {
								entity.teleport(locSpawn);
							}
						} else {
							if (currentY < y - radius) {
								entity.teleport(locSpawn);
							}
						}
						//Test if mob is past the max radius for Z.
						if ( z > 0) {
							if (currentZ > z + radius) {
								entity.teleport(locSpawn);
							}
						} else {
							if (currentZ < z - radius) {
								entity.teleport(locSpawn);
							}
						}
						
					}
				}
			}
		}
	}

	/**
	 * Spawns a an entite in the world.
	 * 
	 * @param id 
	 */
	public static void setupEntity(int id) {

		//Load the configuration file.
		File file = new File(mobTypeIdPath);
		FileConfiguration config =  YamlConfiguration.loadConfiguration(file);

		//Get id config values.
		String name = config.getString(Integer.toString(id) + ".mobType");
		int x = config.getInt(Integer.toString(id) + ".X");
		int y = config.getInt(Integer.toString(id) + ".Y");
		int z = config.getInt(Integer.toString(id) + ".Z");

		//Get mobType config values.
		String stringColor = MonsterCreatorManager.getMonsterConfig().getString(name + ".mobNameColor");
		EntityType entityType = stringToEntityType(MonsterCreatorManager.getMonsterConfig().getString(name + ".entity"));
		int lvl = MonsterCreatorManager.getMonsterConfig().getInt(name + ".mobLVL");
		int hp = MonsterCreatorManager.getMonsterConfig().getInt(name + ".mobHP");
		int moveRadius = MonsterCreatorManager.getMonsterConfig().getInt(name + ".mobRadius");
		String loot = MonsterCreatorManager.getMonsterConfig().getString(name + ".lootTable");

		//Get equipment
		boolean useSkull = MonsterCreatorManager.getMonsterConfig().getBoolean(name + ".useSkull");
		String skull = MonsterCreatorManager.getMonsterConfig().getString(name + ".skull");
		boolean useHelm = MonsterCreatorManager.getMonsterConfig().getBoolean(name + ".helm");
		boolean useChest = MonsterCreatorManager.getMonsterConfig().getBoolean(name + ".chest");
		boolean useLegs = MonsterCreatorManager.getMonsterConfig().getBoolean(name + ".legs");
		boolean useBoots = MonsterCreatorManager.getMonsterConfig().getBoolean(name + ".boots");
		boolean useWeapon = MonsterCreatorManager.getMonsterConfig().getBoolean(name + ".weapon");

		//misc vars
		Location loc = new Location(world, x + .5, y + .5, z + .5);

		//Spawn the mob
		//spawnEntity(world, loc, entity, stringColor, mobType, lvl, hp, runRadius, id, loot);
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
			ItemStack playerSkull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
			SkullMeta skullMeta = (SkullMeta)playerSkull.getItemMeta();
			if (skullMeta.setOwner(skull)) {
				playerSkull.setItemMeta(skullMeta);
			}
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
		
		//Boost mob running speed.
		PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 60*60*20, 1);
		speed.apply(entity);

		mobId.put(entityId, id);
		mobName.put(entityId, fixedName);
		mobLevel.put(entityId, lvl);
		mobHealthPoints.put(entityId, hp);
		mobMaxHealthPoints.put(entityId, hp);
		mobSpawnLocation.put(entityId, loc);
		mobMoveRadius.put(entityId, moveRadius);
		lootTable.put(entityId, loot);
	}

	/**
	 * This will respawn the Entity after a given amount of time.
	 * <p>
	 * This thread is started when this class is loaded at onEnable!
	 * 
	 * @param id The ID number of the mob to respawn contained in the monsterId.yml file.
	 */
	private static void respawnMob () {
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
							setupEntity(spawnID);

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
	 * @param id The UUID of the entity to receive HP reduction.
	 * @param damage The amount of HP to remove from the HashMap.
	 */
	public static void toggleDamage(UUID id, double damage) {
		int currentHP = mobHealthPoints.get(id);
		int newHP = (int) (currentHP - damage);
		mobHealthPoints.put(id, newHP);
		//The entity took damage, show HP bar instead of Entity name.
		renameEntity(id);
	}

	/**
	 * This will give the entity a custom name.
	 * 
	 * @param entity The entity that is going to be given a custom name.
	 */
	public static void setEntityName(Entity entity) {
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
	 * @param id The UUID of the entity to grab from the HashMap's.
	 */
	public static void renameEntity(UUID id) {
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
	 * This removes the dead Entity from all the HashMap's that are tracking it.
	 * 
	 * @param id The entities UUID to be removed from the HashMap's
	 * @param x The Entities X coordinate in the world.
	 * @param y The Entities Y coordinate in the world.
	 * @param z The Entities Z coordinate in the world.
	 */
	public static void toggleEntityDeath(final UUID id, int x, int y, int z) {
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
			mobId.remove(id);
			mobSpawnLocation.remove(id);
			mobName.remove(id);
			mobLevel.remove(id);
			mobHealthPoints.remove(id);
			mobMaxHealthPoints.remove(id);
			mobMoveRadius.remove(id);
			respawnTime.remove(id);
			lootTable.remove(id);

			//Setup mob for respawning.
			//Add spawnID to the HashMap.
			respawnTimeLeft.put(configId, entityRespawnTime);
		}
	}

	/**
	 * Converts a String value to an EntityType if a match can be made.
	 * 
	 * @param entity The String to be converted to an EntityType.
	 * @return A EntityType enumerator.
	 */
	private static EntityType stringToEntityType(String entity) {

		String entityType = entity.toUpperCase().replace(" ", "_");
		EntityType type = null;
		switch(entityType) {
		case "ARMOR_STAND": type = EntityType.ARMOR_STAND;
		break;
		case "ARROW": type = EntityType.ARROW;
		break;
		case "BAT": type = EntityType.BAT;
		break;
		case "BLAZE": type = EntityType.BLAZE;
		break;
		case "BOAT": type = EntityType.BOAT;
		break;
		case "CAVE_SPIDER": type = EntityType.CAVE_SPIDER;
		break;
		case "CHICKEN": type = EntityType.CHICKEN;
		break;
		case "COMPLEX_PART": type = EntityType.COMPLEX_PART;
		break;
		case "COW": type = EntityType.COW;
		break;
		case "CREEPER": type = EntityType.CREEPER;
		break;
		case "DROPPED_ITEM": type = EntityType.DROPPED_ITEM;
		break;
		case "EGG": type = EntityType.EGG;
		break;
		case "ENDERMAN": type = EntityType.ENDERMAN;
		break;
		case "ENDERMITE": type = EntityType.ENDERMITE;
		break;
		case "ENDER_CRYSTAL": type = EntityType.ENDER_CRYSTAL;
		break;
		case "ENDER_DRAGON": type = EntityType.ENDER_DRAGON;
		break;
		case "ENDER_PEARL": type = EntityType.ENDER_PEARL;
		break;
		case "ENDER_SIGNAL": type = EntityType.ENDER_SIGNAL;
		break;
		case "EXPERIENCE_ORB": type = EntityType.EXPERIENCE_ORB;
		break;
		case "FALLING_BLOCK": type = EntityType.FALLING_BLOCK;
		break;
		case "FIREBALL": type = EntityType.FIREBALL;
		break;
		case "FIREWORK": type = EntityType.FIREWORK;
		break;
		case "FISHING_HOOK": type = EntityType.FISHING_HOOK;
		break;
		case "GHAST": type = EntityType.GHAST;
		break;
		case "GIANT": type = EntityType.GIANT;
		break;
		case "GUARDIAN": type = EntityType.GUARDIAN;
		break;
		case "HORSE": type = EntityType.HORSE;
		break;
		case "IRON_GOLEM": type = EntityType.IRON_GOLEM;
		break;
		case "ITEM_FRAME": type = EntityType.ITEM_FRAME;
		break;
		case "LEASH_HITCH": type = EntityType.LEASH_HITCH;
		break;
		case "LIGHTNING": type = EntityType.LIGHTNING;
		break;
		case "MAGMA_CUBE": type = EntityType.MAGMA_CUBE;
		break;
		case "MINECART": type = EntityType.MINECART;
		break;
		case "MINECART_CHEST": type = EntityType.MINECART_CHEST;
		break;
		case "MINECART_COMMAND": type = EntityType.MINECART_COMMAND;
		break;
		case "MINECART_FURNACE": type = EntityType.MINECART_FURNACE;
		break;
		case "MINECART_HOPPER": type = EntityType.MINECART_HOPPER;
		break;
		case "MINECART_MOB_SPAWNER": type = EntityType.MINECART_MOB_SPAWNER;
		break;
		case "MINECART_TNT": type = EntityType.MINECART_TNT;
		break;
		case "MUSHROOM_COW": type = EntityType.MUSHROOM_COW;
		break;
		case "OCELOT": type = EntityType.OCELOT;
		break;
		case "PAINTING": type = EntityType.PAINTING;
		break;
		case "PIG": type = EntityType.PIG;
		break;
		case "PIG_ZOMBIE": type = EntityType.PIG_ZOMBIE;
		break;
		case "PLAYER": type = EntityType.PLAYER;
		break;
		case "PRIMED_TNT": type = EntityType.PRIMED_TNT;
		break;
		case "RABBIT": type = EntityType.RABBIT;
		break;
		case "SHEEP": type = EntityType.SHEEP;
		break;
		case "SILVERFISH": type = EntityType.SILVERFISH;
		break;
		case "SKELETON": type = EntityType.SKELETON;
		break;
		case "SLIME": type = EntityType.SLIME;
		break;
		case "SMALL_FIREBALL": type = EntityType.SMALL_FIREBALL;
		break;
		case "SNOWBALL": type = EntityType.SNOWBALL;
		break;
		case "SNOWMAN": type = EntityType.SNOWMAN;
		break;
		case "SPIDER": type = EntityType.SPIDER;
		break;
		case "SPLASH_POTION": type = EntityType.SPLASH_POTION;
		break;
		case "SQUID": type = EntityType.SQUID;
		break;
		case "THROWN_EXP_BOTTLE": type = EntityType.THROWN_EXP_BOTTLE;
		break;
		case "UNKNOWN": type = EntityType.UNKNOWN;
		break;
		case "VILLAGER": type = EntityType.VILLAGER;
		break;
		case "WEATHER": type = EntityType.WEATHER;
		break;
		case "WITCH": type = EntityType.WITCH;
		break;
		case "WITHER": type = EntityType.WITHER;
		break;
		case "WITHER_SKULL": type = EntityType.WITHER_SKULL;
		break;
		case "WOLF": type = EntityType.WOLF;
		break;
		case "ZOMBIE": type = EntityType.ZOMBIE;
		break;
		default:
			type = EntityType.CHICKEN;
			break;
		}
		return type;
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

		//Load the configuration file.
		File file = new File(mobTypeIdPath);
		FileConfiguration config =  YamlConfiguration.loadConfiguration(file);

		config.set("settings", "settings");
		config.set("settings.countTotal", 0);

		try {
			config.save(configFile);	//Save the file.
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public static FileConfiguration getMonsterIdConfig() {

		//Load the configuration file.
		File file = new File(mobTypeIdPath);
		FileConfiguration config =  YamlConfiguration.loadConfiguration(file);

		return config;
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
