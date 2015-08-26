package com.minepile.mprpg.listeners;

import java.util.UUID;

import org.bukkit.ChatColor;
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
import com.minepile.mprpg.items.ItemLoreFactory;
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

				//If the victim is a NPC. Lets cancel damage and send the player a message.
				if (victim.hasMetadata("NPC")) {
					event.setCancelled(true);

					//Check if the damager was a player.
					if (event.getDamager() instanceof Player) {
						//TODO: When punched we should toggle NPC interact.

						String npcName = victim.getCustomName();
						Player damager = (Player) event.getDamager();
						String damagerName = damager.getName();
						damager.sendMessage(ChatColor.GRAY + npcName + ChatColor.DARK_GRAY + ": "
								+ ChatColor.WHITE + damagerName + " why are you punching me? Use right click..");
					}
				} else {

					Player player = (Player) event.getEntity();
					String playerName = player.getName();

					double playerHealth = PlayerManager.getHealthPoints(playerName);
					double playerMaxHealth = PlayerManager.getMaxHealthPoints(playerName);

					//Damager is another player
					if (event.getDamager() instanceof Player) {
						Player damager = (Player) event.getDamager();

						//get the damagers damage values
						double damage = ItemLoreFactory.getInstance().getDamageBonus(damager);
						double coldDamage = ItemLoreFactory.getInstance().getColdDamage((LivingEntity) damager);
						double fireDamage = ItemLoreFactory.getInstance().getFireDamage((LivingEntity) damager);
						double poisonDamage = ItemLoreFactory.getInstance().getPoisonDamage((LivingEntity) damager);
						double thornDamage = ItemLoreFactory.getInstance().getThornDamage((LivingEntity) damager);

						//Get the victims magical resistances
						double armor = damage * (ItemLoreFactory.getInstance().getArmorBonus((LivingEntity) victim) / 100);
						double coldResistance = coldDamage * (ItemLoreFactory.getInstance().getColdResist((LivingEntity) victim) / 100);
						double fireResistance = fireDamage * (ItemLoreFactory.getInstance().getFireResist((LivingEntity) victim) / 100);
						double poisonResistance = poisonDamage * (ItemLoreFactory.getInstance().getPoisonResist((LivingEntity) victim) / 100);
						double thornResistance = thornDamage * (ItemLoreFactory.getInstance().getColdResist((LivingEntity) victim) / 100);

						//Get final damages based on both the damages damage values and the victims resistances.
						double damageFinal = damage - armor;
						double coldDamageFinal = coldDamage - coldResistance;
						double fireDamageFinal = fireDamage - fireResistance;
						double poisonDamageFinal = poisonDamage - poisonResistance;
						double thornDamageFinal = thornDamage - thornResistance;

						//Apply final math.
						double totalDamage = damageFinal + coldDamageFinal + fireDamageFinal + poisonDamageFinal + thornDamageFinal;
						double playerHitPointsFinal = playerHealth - totalDamage;
						double healthBarPercent = (20 * playerHitPointsFinal) / playerMaxHealth;

						//Debug message
						if (victim.isOp()) {
							victim.sendMessage(victim.getName() 
									+ ChatColor.GREEN + " D: " + ChatColor.RESET + damageFinal 
									+ ChatColor.GREEN +  " +C: " + ChatColor.RESET + coldDamageFinal
									+ ChatColor.GREEN +  " +F: " + ChatColor.RESET + fireDamageFinal 
									+ ChatColor.GREEN +  " +P: " + ChatColor.RESET + poisonDamageFinal
									+ ChatColor.GREEN +  " +T: " + ChatColor.RESET + thornDamageFinal 
									+ ChatColor.RED +  " = " + ChatColor.RESET + totalDamage
									+ ChatColor.YELLOW +  " > " + ChatColor.RESET + playerHealth 
									+ ChatColor.GREEN +  " >> " + ChatColor.RESET + playerHitPointsFinal);
						}
						if (damager.isOp()) {
							damager.sendMessage(damager.getName() 
									+ ChatColor.GREEN + "D: " + ChatColor.RESET + damage 
									+ ChatColor.GREEN + " +C: " + ChatColor.RESET + coldDamageFinal
									+ ChatColor.GREEN +  " +F: " + ChatColor.RESET + fireDamageFinal 
									+ ChatColor.GREEN + " +P: " + ChatColor.RESET + poisonDamageFinal
									+ ChatColor.GREEN +  " +T: " + ChatColor.RESET + thornDamageFinal 
									+ ChatColor.RED +  " = " + ChatColor.RESET + totalDamage
									+ ChatColor.YELLOW +  " > " + ChatColor.RESET + playerHealth 
									+ ChatColor.GREEN +  " >> " + ChatColor.RESET + playerHitPointsFinal);
						}

						//Send the player a hp change message.
						PlayerManager.displayActionBar(player);

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
							} else {
								player.setHealth(healthBarPercent);
							}
							PlayerManager.setPlayerHitPoints(player, playerHitPointsFinal);
						}

						//PLayer shot arrow.
					} else if (event.getDamager() instanceof Arrow) {

						/*
					Arrow arrow = (Arrow)event.getDamager();

					if ((arrow.getShooter() != null) && ((arrow.getShooter() instanceof Player))) {
						Player damager = (Player) arrow.getShooter();
						String damagerName = damager.getName();

						double damage = event.getDamage();
						double healthBarPercent =(20 * (playerHealth - damage) / playerMaxHealth);
						double newHealthTotal = playerHealth - damage;

						//ItemLoreFactory.getInstance().addAttackCooldown(damagerName);

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

							//Update the players armor.
							ItemLoreFactory.getInstance().applyHPBonus(player, false);
						}
					} 
						 */
					}
				}
			} else if (event.getEntity() instanceof LivingEntity && !event.getEntity().getType().equals(EntityType.ARMOR_STAND)) {
				//Living entity is not a player, so it must be some type of mob.				
				UUID victimID = victim.getUniqueId();
				Entity damager = event.getDamager();

				double victimHealth = MonsterManager.getMobHealthPoints(victimID);
				double victimMaxHealth = MonsterManager.getMobMaxHealthPoints(victimID);

				//Set the entities health to 20.
				//We need this if the entity default 
				//health is less than 20. Example is 
				//chickens with 4 hit points.
				if (victim.getHealth() < 20) {
					victim.setMaxHealth(20);
				}

				if (damager instanceof Player) {

					//get the damagers damage values
					double playerDamage = ItemLoreFactory.getInstance().getDamageBonus((LivingEntity) damager);
					double coldDamage = ItemLoreFactory.getInstance().getColdDamage((LivingEntity) damager);
					double fireDamage = ItemLoreFactory.getInstance().getFireDamage((LivingEntity) damager);
					double poisonDamage = ItemLoreFactory.getInstance().getPoisonDamage((LivingEntity) damager);
					double thornDamage = ItemLoreFactory.getInstance().getThornDamage((LivingEntity) damager);
					double totalDamage = playerDamage + coldDamage + fireDamage + poisonDamage + thornDamage;
					double finalHP = victimHealth - totalDamage;

					if (damager.isOp()) {
						damager.sendMessage(damager.getName() 
								+ ChatColor.GREEN + "D: " + ChatColor.RESET + playerDamage 
								+ ChatColor.GREEN + " +C: " + ChatColor.RESET + coldDamage
								+ ChatColor.GREEN +  " +F: " + ChatColor.RESET + fireDamage 
								+ ChatColor.GREEN + " +P: " + ChatColor.RESET + poisonDamage
								+ ChatColor.GREEN +  " +T: " + ChatColor.RESET + thornDamage 
								+ ChatColor.RED +  " = " + ChatColor.RESET + totalDamage
								+ ChatColor.YELLOW +  " > " + ChatColor.RESET + victimHealth 
								+ ChatColor.GREEN +  " >> " + ChatColor.RESET + finalHP);

						damager.sendMessage("Player damage: " + totalDamage);
					}

					//Show monster damage message.
					damager.sendMessage(mobHealthChangeMessage((Player)damager, victim, (int) victimHealth, (int) totalDamage, (int) victimMaxHealth));

					//Set victim health
					if (victimHealth <= 1) {
						int x = victim.getLocation().getBlockX();
						int y = victim.getLocation().getBlockY();
						int z = victim.getLocation().getBlockZ();

						victim.remove();
						MonsterManager.toggleEntityDeath(victimID, x, y, z);
					} else {
						victim.setHealth(15);
						MonsterManager.toggleDamage(victimID, totalDamage);

					}

				} else { //This damage type was not by a player.

					double damage = event.getDamage();

					if (damager.isOp()) {
						damager.sendMessage("eventDamage: " + damage);
					}

					if (victimHealth <= 1) {
						int x = victim.getLocation().getBlockX();
						int y = victim.getLocation().getBlockY();
						int z = victim.getLocation().getBlockZ();

						victim.remove();
						MonsterManager.toggleEntityDeath(victimID, x, y, z);
					} else {
						victim.setHealth(15);
						MonsterManager.toggleDamage(victimID, damage);

					}
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