package com.minepile.mprpg.listeners;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.entities.MonsterManager;
import com.minepile.mprpg.items.ItemLoreFactory;
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
				event.setCancelled(true);
			}
		}

		if (event.getEntity() instanceof LivingEntity) {

			LivingEntity victim = (LivingEntity) event.getEntity();

			if (victim instanceof Player) {

				/*
				Player player = (Player) event.getEntity();
				String playerName = player.getName();

				double playerHealth = PlayerManager.getHealthPoints(playerName);
				double playerMaxHealth = PlayerManager.getMaxHealthPoints(playerName);

				double damage = event.getDamage();
				double healthBarPercent = (20 * (playerHealth - damage) / playerMaxHealth);
				double newHealthTotal = (playerHealth - damage);

				//Cancel regular event damage
				event.setDamage(0);

				//Show player damage message.
				hpChangeMessage(player, (int) playerHealth, (int) damage, (int) playerMaxHealth);

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
					//Update the players health tag if player isnt dead.
					PlayerHealthTagManager.updateHealthTag(player);

					//Update the players armor.
					LoreManagerOLD.applyHpBonus(player, false);
				}
				 */

				/*
				Player player = (Player) event.getEntity();
				String playerName = player.getName();
				double playerHealth = PlayerManager.getHealthPoints(playerName);
				double playerMaxHealth = PlayerManager.getMaxHealthPoints(playerName);


				double coldDamage = ItemLoreFactory.getInstance().getColdDamage((LivingEntity) player);
				double fireDamage = ItemLoreFactory.getInstance().getFireDamage((LivingEntity) player);
				double poisonDamage = ItemLoreFactory.getInstance().getPoisonDamage((LivingEntity) player);
				double thornDamage = ItemLoreFactory.getInstance().getThornDamage((LivingEntity) player);

				player.sendMessage("coldDamage: " + coldDamage + " fireDamage: " + fireDamage
						+ " poisonDamage: " + poisonDamage + " thornDamage: " + thornDamage);

				double coldResistance = coldDamage * (ItemLoreFactory.getInstance().getColdResist((LivingEntity) victim) / 100);
				double fireResistance = fireDamage * (ItemLoreFactory.getInstance().getFireResist((LivingEntity) victim) / 100);
				double poisonResistance = poisonDamage * (ItemLoreFactory.getInstance().getPoisonResist((LivingEntity) victim) / 100);
				double thornResistance = thornDamage * (ItemLoreFactory.getInstance().getColdResist((LivingEntity) victim) / 100);

				player.sendMessage("coldResist: " + coldResistance + " fireResist: " + fireResistance
						+ " poisonResist: " + poisonResistance + " thornResist: " + thornResistance);

				//double damage = ItemLoreFactory.getInstance().getDamageBonus(player);
				double damage = event.getDamage();
				double coldDamageFinal = coldDamage - coldResistance;
				double fireDamageFinal = fireDamage - fireResistance;
				double poisonDamageFinal = poisonDamage - poisonResistance;
				double thornDamageFinal = thornDamage - thornResistance;

				double totalDamage = damage + coldDamageFinal + fireDamageFinal + poisonDamageFinal + thornDamageFinal;
				double playerHitPoints = PlayerManager.getBaseHealthPoints();
				double playerHitPointsFinal = playerHitPoints - totalDamage;
				double healthBarPercent = (20 * playerHitPointsFinal) / playerMaxHealth;

				//PlayerManager.setHealthPoints(victim.getName(), playerHitPointsFinal);
				victim.sendMessage(victim.getName() 
						+ ChatColor.GREEN + "D: " + ChatColor.RESET + damage 
						+ ChatColor.GREEN +  " +C: " + ChatColor.RESET + coldDamageFinal
						+ ChatColor.GREEN +  " +F: " + ChatColor.RESET + fireDamageFinal 
						+ ChatColor.GREEN +  " +P: " + ChatColor.RESET + poisonDamageFinal
						+ ChatColor.GREEN +  " +T: " + ChatColor.RESET + thornDamageFinal 
						+ ChatColor.RED +  " = " + ChatColor.RESET + totalDamage
						+ ChatColor.YELLOW +  " > " + ChatColor.RESET + playerHitPoints 
						+ ChatColor.GREEN +  " >> " + ChatColor.RESET + playerHitPointsFinal);

				//Cancel regular event damage
				event.setDamage(0);

				//Show player damage message.
				hpChangeMessage(player, (int) playerHealth, (int) damage, (int) playerMaxHealth);

				//ItemLoreFactory.getInstance().addAttackCooldown(damagerName);

				//If players healthBar is greater than 1 heart and the players healthMap is not 0.
				if (healthBarPercent <= 1 ) {
					player.setHealth(healthBarPercent + 1);
					//PlayerManager.setPlayerHitPoints(player, playerHitPointsFinal);
				} else {
					player.setHealth(healthBarPercent);
					//PlayerManager.setPlayerHitPoints(player, playerHitPointsFinal);
				}

				//Check if the player HP is too low. If it is, kill the player.
				if (playerHealth < 1) {
					//kill player
					PlayerManager.killPlayer(player);
				} else  {
					//Update the players health tag
					PlayerHealthTagManager.updateHealthTag(player);
				}
				 */
				if (victim.hasMetadata("NPC")) {
					event.setCancelled(true);
				} else {
					Player player = (Player) victim;
					String playerName = player.getName();

					double playerHealth = PlayerManager.getHealthPoints(playerName);
					double playerMaxHealth = PlayerManager.getMaxHealthPoints(playerName);

					double eventDamage = event.getDamage();

					//double coldResistance = coldDamage * (ItemLoreFactory.getInstance().getColdResist((LivingEntity) victim) / 100);
					//double fireResistance = fireDamage * (ItemLoreFactory.getInstance().getFireResist((LivingEntity) victim) / 100);
					//double poisonResistance = poisonDamage * (ItemLoreFactory.getInstance().getPoisonResist((LivingEntity) victim) / 100);
					//double thornResistance = thornDamage * (ItemLoreFactory.getInstance().getColdResist((LivingEntity) victim) / 100);


					switch (event.getCause()) {
					case BLOCK_EXPLOSION:
						break;
					case CONTACT:
						break;
					case CUSTOM:
						break;
					case DROWNING:
						break;
					case ENTITY_ATTACK:
						//Do nothing.  This action should be handled in EntityDamageByEntityListener.java
						eventDamage = 0; //Set dmg to 0 and handle this in the java file mentioned above.
						break;
					case ENTITY_EXPLOSION:
						break;
					case FALL:
						break;
					case FALLING_BLOCK:
						break;
					case FIRE:
						double fireResistance = eventDamage * (ItemLoreFactory.getInstance().getFireResist((LivingEntity) victim) / 100);
						eventDamage -= fireResistance;
						break;
					case FIRE_TICK:
						//using fireResistance here.
						double fireTickResistance = eventDamage * (ItemLoreFactory.getInstance().getFireResist((LivingEntity) victim) / 100);
						eventDamage -= fireTickResistance;
						break;
					case LAVA:
						//using fireResistance here.
						double lavaResistance = eventDamage * (ItemLoreFactory.getInstance().getFireResist((LivingEntity) victim) / 100);
						eventDamage -= lavaResistance;
						break;
					case LIGHTNING:
						break;
					case MAGIC:
						break;
					case MELTING:
						break;
					case POISON:
						double poisonResistance = eventDamage * (ItemLoreFactory.getInstance().getPoisonResist((LivingEntity) victim) / 100);
						eventDamage -= poisonResistance;
						break;
					case PROJECTILE:
						break;
					case STARVATION:
						break;
					case SUFFOCATION:
						break;
					case SUICIDE:
						break;
					case THORNS:
						double thornResistance = eventDamage * (ItemLoreFactory.getInstance().getColdResist((LivingEntity) victim) / 100);
						eventDamage -= thornResistance;
						break;
					case VOID:

						break;
					case WITHER:
						break;
					default:
						break;

					}

					//Apply final math.
					double playerHitPointsFinal = playerHealth - eventDamage;
					double healthBarPercent = (20 * playerHitPointsFinal) / playerMaxHealth;

					//Debug message
					if (player.isOp()) {
						player.sendMessage(playerName + "> "
								+ ChatColor.GREEN +  "HP: " + ChatColor.RESET + playerHealth 
								+ ChatColor.RED + " - D: " + ChatColor.RESET + eventDamage  
								+ ChatColor.GOLD +  " = " + ChatColor.RESET + playerHitPointsFinal);

						player.sendMessage("HP Percent: " + healthBarPercent);
					}

					//Display action bar message
					if (!event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
						//Send the player a hp change message.
						PlayerManager.displayActionBar(player);
					}

					//Check if the player HP is too low. If it is, kill the player.
					//Otherwise apply damage to the player
					if (playerHitPointsFinal < 1) {
						//kill player
						PlayerManager.killPlayer(player);
					} else {
						//Set damage
						if (healthBarPercent <= 3) {
							if (player.isOp()) {
								player.sendMessage("Adding 2% back to healthBarPercent");
							}
							player.setHealth(healthBarPercent + 2);
						} else if (healthBarPercent >= 19) {
							if (player.isOp()) {
								player.sendMessage("Adding 2% back to healthBarPercent");
							}
							player.setHealth(20);
						} else {
							player.setHealth(healthBarPercent + 1);
						}

						PlayerManager.setPlayerHitPoints(player, playerHitPointsFinal);
					}
				}

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
						MonsterManager.toggleEntityDeath(victimID, x, y, z);
					} else {
						victim.setHealth(15);
						MonsterManager.toggleDamage(victimID, damage);

					}
				}
			}
		}
	}
}
