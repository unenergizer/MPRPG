package com.minepile.mprpg.listeners;

import java.util.UUID;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.entities.MonsterManager;
import com.minepile.mprpg.items.LoreManager;
import com.minepile.mprpg.player.PlayerHealthTagManager;
import com.minepile.mprpg.player.PlayerManager;

public class EntityDamageByEntityListener implements Listener{

	public static MPRPG plugin;

	public EntityDamageByEntityListener(MPRPG plugin) {
		EntityDamageByEntityListener.plugin = plugin;
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {

			//Do not cancel
			event.setCancelled(false);

			Player player = (Player) event.getEntity(); //Player who was attacked
			ItemStack weapon = player.getItemInHand();

			//Lets cancel fishing rod damage.
			if (weapon.getType().equals(Material.FISHING_ROD)) {
				event.setCancelled(true);
			}
		}

		//Calculate the damage caused by other players.
		if (event.getEntity() instanceof LivingEntity) {

			LivingEntity victim = (LivingEntity) event.getEntity();

			if (victim instanceof Player) {
				Player player = (Player) event.getEntity();
				String playerName = player.getName();

				double playerHealth = PlayerManager.getHealthPoints(playerName);
				double playerMaxHealth = PlayerManager.getMaxHealthPoints(playerName);

				//Damager is another player
				if (event.getDamager() instanceof Player) {

					Player damager = (Player) event.getDamager();
					String damagerName = damager.getName();

					double damage = Math.max(0, LoreManager.getDamageBonus(damager) - LoreManager.getArmorBonus((LivingEntity)event.getEntity()));
					double healthBarPercent = (20 * (playerHealth - damage) / playerMaxHealth);
					double newHealthTotal = (playerHealth - damage);

					LoreManager.addAttackCooldown(damagerName);

					//Show player damage message.
					playerHealthChangeMessage(player, (int) playerHealth, (int) damage, (int) playerMaxHealth);

					//If players healthBar is greater than 1 heart and the players healthMap is not 0.
					if (healthBarPercent <= 1 ) {
						player.setHealth(healthBarPercent + 1);
						PlayerManager.setHealthPoints(playerName, newHealthTotal);
					} else {
						player.setHealth(healthBarPercent);
						PlayerManager.setHealthPoints(playerName, newHealthTotal);
					}
					
					//Check if the player HP is too low. If it is, kill the player.
					if (playerHealth < 1) {
						//kill player
						PlayerManager.killPlayer(player);
					} else  {
						//Update the players health tag
						PlayerHealthTagManager.updateHealthTag(player);
					}

					//PLayer shot arrow.
				} else if (event.getDamager() instanceof Arrow){

					Arrow arrow = (Arrow)event.getDamager();

					if ((arrow.getShooter() != null) && ((arrow.getShooter() instanceof Player))) {
						Player damager = (Player) arrow.getShooter();
						String damagerName = damager.getName();

						double damage = event.getDamage();
						double healthBarPercent =(20 * (playerHealth - damage) / playerMaxHealth);
						double newHealthTotal = playerHealth - damage;

						LoreManager.addAttackCooldown(damagerName);

						//Show player damage message.
						damager.sendMessage(playerHealthChangeMessage(player, (int) playerHealth, (int) damage, (int) playerMaxHealth));

						//If players healthBar is greater than 1 heart and the players healthMap is not 0.
						if (healthBarPercent <= 1 ) {
							player.setHealth(healthBarPercent + 1);
							PlayerManager.setHealthPoints(playerName, newHealthTotal);
						} else {
							player.setHealth(healthBarPercent);
							PlayerManager.setHealthPoints(playerName, newHealthTotal);
						}
						
						//Check if the player HP is too low. If it is, kill the player.
						if (playerHealth < 1) {
							//kill player
							PlayerManager.killPlayer(player);
						} else  {
							//Update the players health tag
							PlayerHealthTagManager.updateHealthTag(player);
						}
					}
				}
			} else if (event.getEntity() instanceof LivingEntity && !event.getEntity().getType().equals(EntityType.ARMOR_STAND)) {
				//Living entity is not a player, so it must be some type of mob.				
				UUID victimID = victim.getUniqueId();
				Entity damager = event.getDamager();

				double victimHealth = MonsterManager.getMobHealthPoints(victimID);
				double victimMaxHealth = MonsterManager.getMobMaxHealthPoints(victimID);

				double damage = event.getDamage();


				//Set the entities health to 20.
				//We need this if the entity default 
				//health is less than 20. Example is 
				//chickens with 4 hit points.
				if (victim.getHealth() < 20) {
					victim.setMaxHealth(20);
				}

				//Show monster damage message.
				if (damager instanceof Player) {
					if (PlayerManager.getPlayerConfigBoolean((Player) damager, "setting.chat.monsterDebug") == true) {
						damager.sendMessage(mobHealthChangeMessage((Player)damager, victim, (int) victimHealth, (int) damage, (int) victimMaxHealth));
					}
				}

				if (victimHealth <= 1) {
					int x = victim.getLocation().getBlockX();
					int y = victim.getLocation().getBlockY();
					int z = victim.getLocation().getBlockZ();

					victim.remove();
					MonsterManager.toggleEntitieDeath(victimID, x, y, z);
				} else {
					victim.setHealth(15);
					MonsterManager.toggleDamage(victimID, damage);

				}
			}
		}

	}

	public static String playerHealthChangeMessage(Player player, int playerHealth, int damage, int playerMaxHealth) {

		int newHP = playerHealth - damage;
		int hpPercent = ((100 * newHP) / playerMaxHealth);

		String dmg = Integer.toString(damage);
		String hpPrc = Integer.toString(hpPercent);
		String hp = Integer.toString(newHP);
		String maxHP = Integer.toString(playerMaxHealth);
		String hpMessage = ChatColor.RED + "         -" + 
				ChatColor.GRAY + dmg + ChatColor.BOLD + " HP: " +
				ChatColor.GRAY + ChatColor.BOLD + hpPrc + "%" +
				ChatColor.GRAY + " [" + ChatColor.RED + hp +
				ChatColor.GRAY + " / " + ChatColor.GREEN + maxHP +
				ChatColor.GRAY + "]";
		return hpMessage;
	}

	public static String mobHealthChangeMessage(Player player, Entity victim, int playerHealth, int damage, int playerMaxHealth) {

		UUID id = victim.getUniqueId();
		int newHP = playerHealth - damage;
		int hpPercent = ((100 * newHP) / playerMaxHealth);

		String name = MonsterManager.getMobName(id);
		String dmg = Integer.toString(damage);
		String hpPrc = Integer.toString(hpPercent);
		String hp = Integer.toString(newHP);
		String maxHP = Integer.toString(playerMaxHealth);
		String hpMessage = ChatColor.GRAY + "          " + 
				ChatColor.BOLD + name + ChatColor.GRAY + ": " +
				ChatColor.GRAY + ChatColor.BOLD + hpPrc + "%" +
				ChatColor.GRAY + " [" + ChatColor.LIGHT_PURPLE + hp +
				ChatColor.GRAY + " / " + ChatColor.DARK_PURPLE + maxHP +
				ChatColor.GRAY + "]" + ChatColor.RED + " -" + 
				ChatColor.GRAY + dmg + " hp";
		return hpMessage;
	}
}