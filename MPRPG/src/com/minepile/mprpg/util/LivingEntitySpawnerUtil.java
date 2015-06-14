package com.minepile.mprpg.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class LivingEntitySpawnerUtil {
		
	private UUID entityID;
	private EntityType entityType;
	
	/**
	 * Spawns a living entity.
	 * 
	 * @param world The world to spawn the entitie.
	 * @param location The location to spawn the entitie.
	 * @param entityType The type of entitie to spawn.
	 * @param entityName The name of the entitie.
	 */
	public void spawnEntity(String world, Location location, EntityType entityType, String entityName) {
		LivingEntity entity = (LivingEntity) Bukkit.getWorld(world).spawnEntity(location, entityType);
		entity.setCustomName(entityName);
		entity.setCustomNameVisible(true);
		entity.setRemoveWhenFarAway(false);
		entity.setCanPickupItems(false);
		
		setEntityID(entity.getUniqueId());
		setEntityType(entityType);
	}

	public UUID getEntityID() {
		return entityID;
	}

	public void setEntityID(UUID entityID) {
		this.entityID = entityID;
	}
	
	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entity) {
		this.entityType = entity;
	}
}
