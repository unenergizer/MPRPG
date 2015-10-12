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
import com.minepile.mprpg.player.PlayerCharacterManager;
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

				//Make sure the player taking damage is not an NPC.
				if (victim.hasMetadata("NPC")) {
					event.setCancelled(true);

				} else { //Player is not an NPC.

					Player player = (Player) victim;
					UUID uuid = player.getUniqueId();
					String playerName = player.getName();
					
					//Lets make sure the player actually has a player character loaded up and ready to play.
					if (PlayerCharacterManager.isPlayerLoaded(player)) {

						//Make sure the player is not dead.
						//This will prevent dead players from doing damage to players or monsters.
						if (PlayerManager.isPlayerDead(uuid) == true) {

							event.setCancelled(true);
						} else {

							double playerHealth = PlayerManager.getHealthPoints(uuid);
							double playerMaxHealth = PlayerManager.getMaxHealthPoints(uuid);
							//double eventDamage = event.getDamage() + (.03 * playerMaxHealth);
							double eventDamage = event.getDamage();

							/*
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
							event.setCancelled(true);
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
							event.setCancelled(true);
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
							PlayerManager.killPlayer(player);
							break;
						case WITHER:
							break;
						default:
							break;

						}
							 */

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
								//Set player HP.
								PlayerManager.setHealthPoints(player, playerHitPointsFinal);
							}
						}
					} else {
						//Lets prevent users from taking damage if they have not selected a character or class.
						event.setCancelled(true);
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
