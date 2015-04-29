package com.minepile.mprpg.managers;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.monsters.EntityTierManager.EntityTier;
import com.minepile.mprpg.util.LivingEntitySpawnerUtil;

public class NPCManager {

	static NPCManager kitInstance = new NPCManager();
	private static LivingEntitySpawnerUtil spawnerUtil = new LivingEntitySpawnerUtil();

	private static World world = Bukkit.getWorld("world");
	public static HashMap<UUID, Integer> entityHealth = new HashMap<UUID, Integer>();
	public static HashMap<UUID, EntityTier> entityTier = new HashMap<UUID, EntityTier>();
	public static HashMap<UUID, Integer> entityLevel = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Location> entityLocation = new HashMap<UUID, Location>();
	
	@SuppressWarnings("unused")
	private static int taskID;
	
	public static Location kit0Location = new Location(world, -8, 74, -18);
	public static Location kit1Location = new Location(world, -4, 74, -18);
	public static Location kit2Location = new Location(world, 0, 74, -18);
	public static Location kit3Location = new Location(world, 4, 74, -18);
	public static Location kit4Location = new Location(world, 8, 74, -18);
	public static Location kit5Location = new Location(world, 12, 74, -18);
	public static Location kit6Location = new Location(world, 16, 74, -18);
	
	public static Location npc0Location = new Location(world, 19.5, 79, 6.5);

	private static MPRPG plugin;

	public static NPCManager getInstance() {
		return kitInstance;
	}

	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;		
		
		
		spawnNPC();
		
		//TP Mobs
		teleportNPC();
	}

	public static void setupMob(Location location, EntityType entity, String entityName, EntityTier tier, int health, int level) {
		spawnerUtil.spawnEntity(world.getName(), location, entity, entityName);
		entityLocation.put(spawnerUtil.getEntityID(), location);
		entityTier.put(spawnerUtil.getEntityID(), tier);
		entityHealth.put(spawnerUtil.getEntityID(), health);
		entityLevel.put(spawnerUtil.getEntityID(), level);
	}
	
	public static void setupNPC(Location location, EntityType entity, ChatColor nameColor, String kitName) {
		spawnerUtil.spawnEntity(world.getName(), location, entity, nameColor + kitName);
		entityLocation.put(spawnerUtil.getEntityID(), location);
		//entityTeam.put(spawnerUtil.getEntityID(), team);
	}

	public static void spawnNPC() {	
		//ShopMaster.
		spawnerUtil.spawnEntity(world.getName(), npc0Location, EntityType.VILLAGER, ChatColor.LIGHT_PURPLE + "" + "Island Villager");
		entityLocation.put(spawnerUtil.getEntityID(), npc0Location);
	}

	public static void teleportNPC() {
		//Lets start a repeating task
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				//Loop through our entityKitLocation hashmap and teleport mobs back to their spawns.
				for (Entry<UUID, Location> entry : entityLocation.entrySet()) {
					UUID mobID = entry.getKey();
					Location spawnLocation = entry.getValue();

					//Loop through entity list and teleport the correct entity.
					for (Entity mob : world.getEntities()) {
						if (mob.getUniqueId().equals(mobID)) {
							mob.teleport(spawnLocation);
						}
					}
				}
			} //END Run method.
		}, 0, 5); //(20 ticks = 1 second)
	}
	/*
	public static void onPlayerInteract(Player player, UUID mobID) {
		//Set player kit if npc is a Kit NPC.
		if (entityKit.containsKey(mobID) == true) {
			setPlayerKit(player, mobID);
		}
		//Set player team if npc is a Team NPC.
		if (entityTeam.containsKey(mobID) == true) {
			setPlayerTeam(player, mobID);
		}
	}
 	*/
}
