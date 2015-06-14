package com.minepile.mprpg.listeners;

import java.util.UUID;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.entities.MonsterManager;
import com.minepile.mprpg.player.PlayerHealthTagManager;
import com.minepile.mprpg.player.PlayerManager;

public class EntityDamageListener implements Listener{

	public static MPRPG plugin;

	@SuppressWarnings("static-access")
	public EntityDamageListener(MPRPG plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {

		if(event.getEntity() instanceof Player) {
			//Do not cancel, causes bugs.
			event.setCancelled(false);

			Player player = (Player) event.getEntity(); //Player who was attacked
			ItemStack weapon = player.getItemInHand();

			//Lets cancel fishing rod damage.
			if (weapon.getType().equals(Material.FISHING_ROD)) {
				player.sendMessage("EntityDamageEvent");
				event.setCancelled(true);
			}
		}

		if (event.getEntity() instanceof LivingEntity) {

			LivingEntity victim = (LivingEntity) event.getEntity();

			if (victim instanceof Player) {
				Player player = (Player) event.getEntity();
				String playerName = player.getName();

				int playerHealth = PlayerManager.getHealthPoints(playerName);
				int playerMaxHealth = PlayerManager.getMaxHealthPoints(playerName);

				int damage = (int) event.getDamage();
				int healthBarPercent = (int) (20 * (playerHealth - damage) / playerMaxHealth);
				int newHealthTotal = (int) (playerHealth - damage);

				//Cancel regular event damage
				event.setDamage(0);

				//Print out the type of damage done.
				//player.sendMessage(ChatColor.AQUA + "DmgCause: " + event.getCause().toString());

				//Show player damage message.
				hpChangeMessage(player, playerHealth, (int) damage, playerMaxHealth);

				if (player.getHealth() > 1 && playerHealth > 0) {
					if (healthBarPercent <= 1) {
						player.setHealth(healthBarPercent);
					} else {
						player.setHealth(healthBarPercent - 1);
					}
					PlayerManager.setHealthPoints(playerName, newHealthTotal);
				} else if (player.getHealth() == 1) {
					player.setHealth(player.getHealth() + 1);
					PlayerManager.setHealthPoints(playerName, newHealthTotal);
				} else if (playerHealth <= 0) {
					//Cancel any further damage.
					event.setCancelled(true);
					//Kill the player
					player.setHealth(0);
				}

				//Update the players health tag
				PlayerHealthTagManager.updateHealthTag(player);
			} else {
				if (!victim.getType().equals(EntityType.ARMOR_STAND)) {
					//Living entity is not a player, so it must be some type of mob.
					UUID victimID = victim.getUniqueId();
					int victimHealth = MonsterManager.getMobHealthPoints(victimID);
					int damage = (int) event.getDamage();

					//Set the entities health to 20.
					//We need this if the entity default 
					//health is less than 20. Example is 
					//chickens with 4 hit points.
					if (victim.getHealth() < 20) {
						victim.setMaxHealth(20);
					}

					if (victimHealth <= 1) {
						int x = victim.getLocation().getBlockX();
						int y = victim.getLocation().getBlockY();
						int z = victim.getLocation().getBlockZ();
						
						victim.remove(); //Removes the entity
						
						//Cleanup for MinePile plugin
						//Also gets location for loot drop
						MonsterManager.toggleDeath(victimID, x, y, z);
					} else {
						victim.setHealth(15);
						MonsterManager.toggleDamage(victimID, damage);

					}
				}
			}
		}
	}

	public static void hpChangeMessage(Player player, int playerHealth, int damage, int playerMaxHealth) {

		int newHP = playerHealth - damage;
		int hpPercent = (int) ((100 * newHP) / playerMaxHealth);

		String dmg = Integer.toString(damage);
		String hpPrc = Integer.toString(hpPercent);
		String hp = Integer.toString(newHP);
		String maxHP = Integer.toString(playerMaxHealth);
		player.sendMessage(ChatColor.RED + "         -" + 
				ChatColor.GRAY + dmg + ChatColor.BOLD + " HP: " +
				ChatColor.GRAY + ChatColor.BOLD + hpPrc + "%" +
				ChatColor.GRAY + " [" + ChatColor.RED + hp +
				ChatColor.GRAY + " / " + ChatColor.GREEN + maxHP +
				ChatColor.GRAY + "]");
	}
}
