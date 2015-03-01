package com.minepile.mprpg.listeners;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.equipment.LoreManager;
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

		//Check Weapon Restriction
		if (LoreManager.dodgedAttack((LivingEntity)event.getEntity())) {
			event.setDamage(0.0D);
			event.setCancelled(true);
			return;
		}
		if ((event.getEntity() instanceof Player) && (event.getDamager() instanceof LivingEntity)) {
			
			Player victim = (Player) event.getEntity();
			String victimName = victim.getName();
			LivingEntity damager = (LivingEntity) event.getDamager();
			String damagerName = damager.getName();
			
			int victimHP = PlayerManager.getHealthPoints(victimName);
			int victimMaxHP = PlayerManager.getMaxHealthPoints(victimName);
			int damage = Math.max(0, LoreManager.getDamageBonus(damager) - LoreManager.getArmorBonus((LivingEntity)event.getEntity()));
			int newHP = victimHP - damage;
			int hpBarPercent = (20 * (victimHP - damage) / victimMaxHP);
			int hpPercent = (int) ((100 * newHP) / victimMaxHP);

			LoreManager.addAttackCooldown(damagerName);

			victim.setHealth(hpBarPercent);
			PlayerManager.setHealthPoints(victimName, newHP);
			//victim.sendMessage(ChatColor.RED + Integer.toString(victimHP) + " - " + Integer.toString(damage) + " = " + PlayerManager.getHealthPoints(victimName));
			//Send the player the debug message.
			
			victim.sendMessage(ChatColor.RED + "         -" + 
					ChatColor.GRAY + damage + ChatColor.BOLD + " HP: " +
					ChatColor.GRAY + ChatColor.BOLD + hpPercent + "%" +
					ChatColor.GRAY + " [" + ChatColor.RED + newHP +
					ChatColor.GRAY + " / " + ChatColor.GREEN + victimMaxHP +
					ChatColor.GRAY + "]");
			
		} else if ((event.getDamager() instanceof Arrow)) {
			
			Arrow arrow = (Arrow)event.getDamager();
			
			if ((arrow.getShooter() != null) && ((arrow.getShooter() instanceof Player))) {
				
				Player victim = (Player) event.getEntity();
				String victimName = victim.getName();
				Player damager = (Player) arrow.getShooter();
				String damagerName = damager.getName();
				
				int damage = (int) (Math.min(PlayerManager.getMaxHealthPoints(damagerName), PlayerManager.getHealthPoints(damagerName) + Math.min(LoreManager.getLifeSteal(damager), event.getDamage())));
				int victimHP = PlayerManager.getHealthPoints(victimName);
				int victimMaxHP = PlayerManager.getMaxHealthPoints(victimName);
				int newHP = victimHP - damage;
				int hpBarPercent = (20 * (victimHP - damage) / victimMaxHP);
				int hpPercent = (int) ((100 * newHP) / victimMaxHP);
				
				LoreManager.addAttackCooldown(((Player)damager).getName());
				
				victim.setHealth(hpBarPercent);
				victim.sendMessage(ChatColor.RED + "         -" + 
						ChatColor.GRAY + damage + ChatColor.BOLD + " HP: " +
						ChatColor.GRAY + ChatColor.BOLD + hpPercent + "%" +
						ChatColor.GRAY + " [" + ChatColor.RED + newHP +
						ChatColor.GRAY + " / " + ChatColor.GREEN + victimMaxHP +
						ChatColor.GRAY + "]");
				
			}
		}
	}
}