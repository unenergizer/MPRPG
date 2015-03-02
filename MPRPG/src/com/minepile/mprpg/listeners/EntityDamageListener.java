package com.minepile.mprpg.listeners;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;
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
				
				//Print out the type of damage done.
				player.sendMessage(ChatColor.AQUA + "DmgCause: " + event.getCause().toString());
				
				//Show player damage message.
				hpChangeMessage(player, playerHealth, (int) damage, playerMaxHealth);

				if (playerHealth == 0) {
					//kill player
					player.setHealth(healthBarPercent);
				} else if (playerHealth >= 1) {
					
					if (player.getHealth() == 1) {
						//kill player
						player.setHealth(2);
						PlayerManager.setHealthPoints(playerName, newHealthTotal);
					} else if (playerHealth >=1) {
						player.setHealth(healthBarPercent);
						PlayerManager.setHealthPoints(playerName, newHealthTotal);
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
