package com.minepile.mprpg.listeners;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.world.BlockRegenerationManager;

public class EntityExplodeListener implements Listener{

	public static MPRPG plugin;
	private static final Set<Material> toDestroy = new HashSet<Material>();
	
	@SuppressWarnings("static-access")
	public EntityExplodeListener(MPRPG plugin) {
		this.plugin = plugin;
		
		//Add items that TNT can destroy.
		toDestroy.add(Material.CLAY);
		toDestroy.add(Material.DEAD_BUSH);
		toDestroy.add(Material.DOUBLE_PLANT);
		toDestroy.add(Material.RED_MUSHROOM);
		toDestroy.add(Material.RED_ROSE);
		toDestroy.add(Material.SAPLING);
		toDestroy.add(Material.SUGAR_CANE);
		toDestroy.add(Material.SUGAR_CANE_BLOCK);
		toDestroy.add(Material.YELLOW_FLOWER);
		toDestroy.add(Material.WATER_LILY);
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		
		if (event.getEntity().getType() == EntityType.PRIMED_TNT) {
			List destroyed = event.blockList();
			Iterator it = destroyed.iterator();
			while (it.hasNext()) {
				Block block = (Block) it.next();
				Material mat = block.getType();
				if (!toDestroy.contains(block.getType())) {
					it.remove();
				} else {
					BlockRegenerationManager.setBlock(mat, Material.AIR, block.getLocation());
				}
				
			}
		}
	}
}