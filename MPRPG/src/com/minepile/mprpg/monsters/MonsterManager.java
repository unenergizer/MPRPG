package com.minepile.mprpg.monsters;

import java.util.HashMap;
import java.util.UUID;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.managers.MessageManager;
import com.minepile.mprpg.util.LivingEntitySpawnerUtil;

public class MonsterManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static MonsterManager monsterManagerInstance = new MonsterManager();
	private static LivingEntitySpawnerUtil spawnerUtil = new LivingEntitySpawnerUtil();
	
	private static World world = Bukkit.getWorld("world");
	//static HashMap<UUID, Location> mobSpawnLocation = new HashMap<UUID, Location>();
	static HashMap<UUID, Integer> mobHealthPoints = new HashMap<UUID, Integer>();
	static HashMap<UUID, Integer> mobMaxHealthPoints = new HashMap<UUID, Integer>();
	
	static int baseMaxHP = 20;
	
	//Create instance
	public static MonsterManager getInstance() {
		return monsterManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		spawnTestMob();
	}	
	
	
	public static void spawnTestMob() {
		
		Location npc0Location = new Location(world, 17.5, 79, 3.5);
		String name = ChatColor.GRAY + "[" + ChatColor.RED + "LVL 1" + ChatColor.GRAY +"] " + 
				"Weak Chicken";
		
		spawnerUtil.spawnEntity(world.getName(), npc0Location, EntityType.CHICKEN, name );
		mobHealthPoints.put(spawnerUtil.getEntityID(), baseMaxHP);
		mobMaxHealthPoints.put(spawnerUtil.getEntityID(), baseMaxHP);
	}
	
	public static void addMonster(UUID id) {
		if (mobHealthPoints.get(id) == null) {
			Bukkit.broadcastMessage(ChatColor.AQUA + "Added monster: " + id.toString());
			mobHealthPoints.put(id, baseMaxHP);
			mobMaxHealthPoints.put(id, baseMaxHP);
		}
	}
	
	public static void toggleDamage(UUID id, double damage) {
		
		if (mobHealthPoints.get(id) == null) {
			addMonster(id);
		} else {
			int currentHP = mobHealthPoints.get(id);
			int newHP = (int) (currentHP - damage);
			mobHealthPoints.put(id, newHP);
			renameMob(id);
		}
	}
	
	public static void renameMob(UUID id) {
		int maxHP = mobMaxHealthPoints.get(id);
		int hp = mobHealthPoints.get(id);
		int percentHP = (100 * hp) / maxHP;
		String name = ChatColor.GRAY + "[" + ChatColor.RED + "LVL 1" + ChatColor.GRAY +"] " + 
				MessageManager.percentBar(percentHP);
		
		//Loop through entity list and teleport the correct entity.
		for (Entity mob : Bukkit.getWorld("world").getEntities()) {
			if (mob.getUniqueId().equals(id)) {
				mob.setCustomName(name);
				mob.setCustomNameVisible(true);
			}
		}
	}
}
