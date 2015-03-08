package com.minepile.mprpg.monsters;

import java.util.HashMap;
import java.util.UUID;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.managers.MessageManager;

public class MonsterManager {

	//setup instance variables
	public static MPRPG plugin;
	static MonsterManager monsterManagerInstance = new MonsterManager();

	private static World world = Bukkit.getWorld("world");
	//static HashMap<UUID, Location> mobSpawnLocation = new HashMap<UUID, Location>();
	static HashMap<UUID, Integer> mobHealthPoints = new HashMap<UUID, Integer>();
	static HashMap<UUID, Integer> mobMaxHealthPoints = new HashMap<UUID, Integer>();
	static HashMap<UUID, Hologram> mobHologramName = new HashMap<UUID, Hologram>();

	static int baseMaxHP = 100;

	//Create instance
	public static MonsterManager getInstance() {
		return monsterManagerInstance;
	}

	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		//updateNamePlateRepeat();
	}	

	public static void addMonster(UUID id) {
		if (mobHealthPoints.get(id) == null) {
			//Bukkit.broadcastMessage(ChatColor.AQUA + "Added monster: " + id.toString());
			mobHealthPoints.put(id, baseMaxHP);
			mobMaxHealthPoints.put(id, baseMaxHP);
		}
	}

	public static void toggleDamage(UUID id, double damage) {

		if (mobHealthPoints.get(id) == null) {
			addMonster(id);
		}

		int currentHP = mobHealthPoints.get(id);
		int newHP = (int) (currentHP - damage);
		mobHealthPoints.put(id, newHP);
		renameMob(id);
	}
	
	public static void setMobName(Entity entity) {
		String name = ChatColor.GRAY + "[" + ChatColor.RED + "LVL 1" + ChatColor.GRAY +"] " + 
				entity.getName();
		entity.setCustomName(name);
		entity.setCustomNameVisible(true);
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

	public void updateNamePlateRepeat() {

		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				//Lets loop through the hashMaps to find any name plates that need to be moved.

				for (Entity mob : Bukkit.getWorld("world").getEntities()) {
					if (!(mob instanceof Player) && (mob instanceof LivingEntity)) {
						
						boolean continueUpdate = false;
						
						switch(mob.getType()) {
						case BAT:
							continueUpdate = true;
							break;
						case BLAZE:
							continueUpdate = true;
							break;
						case CAVE_SPIDER:
							continueUpdate = true;
							break;
						case CHICKEN:
							continueUpdate = true;
							break;
						case COW:
							continueUpdate = true;
							break;
						case CREEPER:
							continueUpdate = true;
							break;
						case ENDERMAN:
							continueUpdate = true;
							break;
						case ENDERMITE:
							continueUpdate = true;
							break;
						case ENDER_DRAGON:
							continueUpdate = true;
							break;
						case GHAST:
							continueUpdate = true;
							break;
						case GIANT:
							continueUpdate = true;
							break;
						case GUARDIAN:
							continueUpdate = true;
							break;
						case HORSE:
							continueUpdate = true;
							break;
						case IRON_GOLEM:
							continueUpdate = true;
							break;
						case MAGMA_CUBE:
							continueUpdate = true;
							break;
						case MUSHROOM_COW:
							continueUpdate = true;
							break;
						case OCELOT:
							continueUpdate = true;
							break;
						case PIG:
							continueUpdate = true;
							break;
						case PIG_ZOMBIE:
							continueUpdate = true;
							break;
						case RABBIT:
							continueUpdate = true;
							break;
						case SHEEP:
							continueUpdate = true;
							break;
						case SILVERFISH:
							continueUpdate = true;
							break;
						case SKELETON:
							continueUpdate = true;
							break;
						case SLIME:
							continueUpdate = true;
							break;
						case SNOWMAN:
							continueUpdate = true;
							break;
						case SPIDER:
							continueUpdate = true;
							break;
						case SQUID:
							continueUpdate = true;
							break;
						case VILLAGER:
							continueUpdate = true;
							break;
						case WITCH:
							continueUpdate = true;
							break;
						case WITHER:
							continueUpdate = true;
							break;
						case WOLF:
							continueUpdate = true;
							break;
						case ZOMBIE:
							continueUpdate = true;
							break;
						default:
							continueUpdate = false;
							break;
							
						}
						
						if (continueUpdate == true) {
							String mobName = mob.getType().toString();
							UUID mobId = mob.getUniqueId();
							Location mobLocation = mob.getLocation();
							Double mobX = mobLocation.getX();
							Double mobY = mobLocation.getY() + 2.5;
							Double mobZ = mobLocation.getZ();
							
							
							if (mobHealthPoints.get(mobId) == null) {
								addMonster(mobId);
							}
							
							int maxHP = mobMaxHealthPoints.get(mobId);
							int hp = mobHealthPoints.get(mobId);
							int percentHP = (100 * hp) / maxHP;
	
							if(maxHP == hp) {
								String name = ChatColor.GRAY + "[" + ChatColor.RED + "LVL 1" + ChatColor.GRAY +"] " + 
										ChatColor.RED + mobName;
								if (mobHologramName.get(mobId) == null) {
									mobHologramName.put(mobId, HologramsAPI.createHologram(plugin, new Location(world, mobX, mobY, mobZ)));
									mobHologramName.get(mobId).appendTextLine(name);
								}
								mobHologramName.get(mobId).delete();
								mobHologramName.remove(mobId);
								mobHologramName.put(mobId, HologramsAPI.createHologram(plugin, new Location(world, mobX, mobY, mobZ)));
								mobHologramName.get(mobId).appendTextLine(name);
							} else {
								String name = ChatColor.GRAY + "[" + ChatColor.RED + "LVL 1" + ChatColor.GRAY +"] " + 
										ChatColor.RED + mobName;
								String hpBar = MessageManager.percentBar(percentHP);
								if (mobHologramName.get(mobId) == null) {
									mobHologramName.put(mobId, HologramsAPI.createHologram(plugin, new Location(world, mobX, mobY, mobZ)));
									mobHologramName.get(mobId).appendTextLine(name);
								}
								mobHologramName.get(mobId).delete();
								mobHologramName.remove(mobId);
								mobHologramName.put(mobId, HologramsAPI.createHologram(plugin, new Location(world, mobX, mobY, mobZ)));
								mobHologramName.get(mobId).appendTextLine(name);
								mobHologramName.get(mobId).appendTextLine(hpBar);
							}
						}
					}
				}
			}
		}, 0L, 4); //20 = 1 second
	}

	public void updateNamePlate() {

		for (Entity mob : Bukkit.getWorld("world").getEntities()) {
			if (!(mob instanceof Player)) {
				String mobName = mob.getType().toString();
				UUID mobId = mob.getUniqueId();
				Location mobLocation = mob.getLocation();
				int maxHP = mobMaxHealthPoints.get(mobId);
				int hp = mobHealthPoints.get(mobId);
				int percentHP = (100 * hp) / maxHP;

				if(maxHP == hp) {
					String name = ChatColor.GRAY + "[" + ChatColor.RED + "LVL 1" + ChatColor.GRAY +"] " + 
							ChatColor.RED + mobName;
					mobHologramName.remove(mobId);
					mobHologramName.put(mobId, HologramsAPI.createHologram(plugin, mobLocation));
					mobHologramName.get(mobId).appendTextLine(name);
				} else {
					String name = ChatColor.GRAY + "[" + ChatColor.RED + "LVL 1" + ChatColor.GRAY +"] " + 
							MessageManager.percentBar(percentHP);
					mobHologramName.remove(mobId);
					mobHologramName.put(mobId, HologramsAPI.createHologram(plugin, mobLocation));
					mobHologramName.get(mobId).appendTextLine(name);
				}
			}
		}
	}

	public static void toggleDeath(final UUID id) {
		new BukkitRunnable() {
			@Override
			public void run() {
				//Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "Mob removed becahse of death.");
				mobHealthPoints.remove(id);
				mobMaxHealthPoints.remove(id);
				//mobHologramName.get(id).clearLines();
				//mobHologramName.get(id).delete();
				//mobHologramName.remove(id);
			}
		}.runTaskLater(plugin, 1); //run after 1 tick
	}

	public static int getMobHealthPoints(UUID id) {
		if (mobHealthPoints.get(id) == null) {
			addMonster(id);
		}
		return mobHealthPoints.get(id);
	}

	public static void setMobHealthPoints(UUID id, int mobHealthPoints) {
		MonsterManager.mobHealthPoints.put(id, mobHealthPoints);
	}

	public static int getMobMaxHealthPoints(UUID id) {
		if (mobMaxHealthPoints.get(id) == null) {
			addMonster(id);
		}
		return mobMaxHealthPoints.get(id);
	}

	public static void setMobMaxHealthPoints(UUID id, int mobMaxHealthPoints) {
		MonsterManager.mobMaxHealthPoints.put(id, mobMaxHealthPoints);
	}
}
