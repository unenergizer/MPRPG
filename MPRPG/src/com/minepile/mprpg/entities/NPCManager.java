package com.minepile.mprpg.entities;

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
import com.minepile.mprpg.util.LivingEntitySpawnerUtil;

public class NPCManager {

	static NPCManager kitInstance = new NPCManager();
	private static LivingEntitySpawnerUtil spawnerUtil = new LivingEntitySpawnerUtil();

	private static World world = Bukkit.getWorld("world");
	public static HashMap<UUID, Integer> entityHealth = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Integer> entityLevel = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Location> entityLocation = new HashMap<UUID, Location>();
	
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
		//Spawns an NPC
		//spawnNPC();
		
		//TP NPC
		//teleportNPC();
	}
	
	
	
	
	/**
	 * 
	 * Load NPC from file
	 * Spawn NPC at location
	 * Give NPC a HP
	 * Assign actions to them (quests, menus, etc)
	 * Put hologram over a class trainers head and quest person head
	 *
	 * 
	 */
	
	
	
	/*///////////////////////////////////////////////////////////////////////////////////////////////////*/
	
	/**
	 * This will setup an NPC entitie.
	 * 
	 * @param location The XYZ location to spawn the entitie in a world.
	 * @param entity The type of entitie to spawn.
	 * @param entityName The name of the entitie.
	 * @param health The hitpoints a NPC will have.
	 * @param level The level of the NPC.
	 */
	public static void setupMob(Location location, EntityType entity, String entityName, int health, int level) {
		spawnerUtil.spawnEntity(world.getName(), location, entity, entityName);
		entityLocation.put(spawnerUtil.getEntityID(), location);
		entityHealth.put(spawnerUtil.getEntityID(), health);
		entityLevel.put(spawnerUtil.getEntityID(), level);
	}
	
	/**
	 * This will spawn an NPC.
	 */
	public static void spawnNPC() {	
		//ShopMaster.
		spawnerUtil.spawnEntity(world.getName(), npc0Location, EntityType.VILLAGER, ChatColor.LIGHT_PURPLE + "" + "Island Villager");
		entityLocation.put(spawnerUtil.getEntityID(), npc0Location);
	}
	
	/**
	 * This teleports an NPC back to their spawn location if they try to walk from it.
	 */
	public static void teleportNPC() {
		//Lets start a repeating task
		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
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
		}); //(20 ticks = 1 second)
	}
}
