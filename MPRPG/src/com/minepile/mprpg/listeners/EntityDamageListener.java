package com.minepile.mprpg.listeners;

import io.puharesource.mc.titlemanager.api.ActionbarTitleObject;
import io.puharesource.mc.titlemanager.api.TitleObject;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.chat.MessageManager;
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

				double playerHealth = PlayerManager.getHealthPoints(playerName);
				double playerMaxHealth = PlayerManager.getMaxHealthPoints(playerName);

				double damage = event.getDamage();
				double healthBarPercent = (20 * (playerHealth - damage) / playerMaxHealth);
				double newHealthTotal = (playerHealth - damage);

				//Cancel regular event damage
				event.setDamage(0);

				//Print out the type of damage done.
				//player.sendMessage(ChatColor.AQUA + "DmgCause: " + event.getCause().toString());

				//Show player damage message.
				hpChangeMessage(player, playerHealth, damage, playerMaxHealth);
				
				//Check to make sure the healthBarPercent isn't less than 1.
				//If it is, we should set it back to 1.
				if (healthBarPercent < 1) {
					healthBarPercent = 1;
				}
				
				//If players healthBar is greater than 1 heart and the players healthMap is not 0.
				if (player.getHealth() > 1 && playerHealth > 0) {
					
					//If the percent of HP is less than or equal to 1 continue.
					if (healthBarPercent <= 1) {
						//Set the players health based on the percentage.
						player.setHealth(healthBarPercent);
					} else {
						//Set the players health 
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
					
					new TitleObject(ChatColor.RED + "You have died!", "").send(player);
				}

				//Update the players health tag
				PlayerHealthTagManager.updateHealthTag(player);
			} else {
				if (!victim.getType().equals(EntityType.ARMOR_STAND)) {
					//Living entity is not a player, so it must be some type of mob.
					UUID victimID = victim.getUniqueId();
					double victimHealth = MonsterManager.getMobHealthPoints(victimID);
					double damage = event.getDamage();

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
						MonsterManager.toggleEntitieDeath(victimID, x, y, z);
					} else {
						victim.setHealth(15);
						MonsterManager.toggleDamage(victimID, damage);

					}
				}
			}
		}
	}

	private static void hpChangeMessage(Player player, double playerHealth, double damage, double playerMaxHealth) {

		double newHP = playerHealth - damage;
		double hpPercent = ((100 * newHP) / playerMaxHealth);

		String dmg = Double.toString(damage);
		String hp = Double.toString(newHP);
		String maxHP = Double.toString(playerMaxHealth);

		new ActionbarTitleObject(ChatColor.RED + "-" + 
				ChatColor.GRAY + dmg + ChatColor.BOLD + " HP: " +
				MessageManager.percentBar(hpPercent) + 
				ChatColor.GRAY + " [" + ChatColor.RED + hp +
				ChatColor.GRAY + " / " + ChatColor.GREEN + maxHP +
				ChatColor.GRAY + "]").send(player);
	}
}
