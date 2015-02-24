package com.minepile.mprpg.listeners;

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
				player.sendMessage("EntityDamageByEntityEvent");
				event.setCancelled(true);
			}
		}

		//Check Weapon Restriction
		if (!(event.getDamager() instanceof Player)) {
			return;
		}
		if (!LoreManager.canUse((Player)event.getDamager(), ((Player)event.getDamager()).getItemInHand()))
		{
			event.setCancelled(true);
			return;
		}

		//modifyEntityDamage
		if ((event.isCancelled()) || (!(event.getEntity() instanceof LivingEntity))) {
			return;
		}
		if (LoreManager.dodgedAttack((LivingEntity)event.getEntity()))
		{
			event.setDamage(0.0D);
			event.setCancelled(true);
			return;
		}
		if ((event.getDamager() instanceof LivingEntity))
		{
			LivingEntity damager = (LivingEntity)event.getDamager();
			if ((damager instanceof Player)) {
				if (LoreManager.canAttack(((Player)damager).getName()))
				{
					LoreManager.addAttackCooldown(((Player)damager).getName());
				}
				else
				{
					((Player)damager).sendMessage("lore.attack-speed.message");
					event.setCancelled(true);
					return;
				}
			}
			if (LoreManager.useRangeOfDamage(damager)) {
				event.setDamage(Math.max(0, LoreManager.getDamageBonus(damager) - LoreManager.getArmorBonus((LivingEntity)event.getEntity())));
			} else {
				event.setDamage(Math.max(0.0D, event.getDamage() + LoreManager.getDamageBonus(damager) - LoreManager.getArmorBonus((LivingEntity)event.getEntity())));
			}
			damager.setHealth(Math.min(damager.getMaxHealth(), damager.getHealth() + Math.min(LoreManager.getLifeSteal(damager), event.getDamage())));
		}
		else if ((event.getDamager() instanceof Arrow))
		{
			Arrow arrow = (Arrow)event.getDamager();
			if ((arrow.getShooter() != null) && ((arrow.getShooter() instanceof LivingEntity)))
			{
				LivingEntity damager = (LivingEntity) arrow.getShooter();
				if ((damager instanceof Player)) {
					if (LoreManager.canAttack(((Player)damager).getName()))
					{
						LoreManager.addAttackCooldown(((Player)damager).getName());
					}
					else
					{
				
						((Player)damager).sendMessage("lore.attack-speed.message");
						event.setCancelled(true);
						return;
					}
				}
				if (LoreManager.useRangeOfDamage(damager)) {
					event.setDamage(Math.max(0, LoreManager.getDamageBonus(damager) - LoreManager.getArmorBonus((LivingEntity)event.getEntity())));
				} else {
					event.setDamage(Math.max(0.0D, event.getDamage() + LoreManager.getDamageBonus(damager)) - LoreManager.getArmorBonus((LivingEntity)event.getEntity()));
				}
				damager.setHealth(Math.min(damager.getMaxHealth(), damager.getHealth() + Math.min(LoreManager.getLifeSteal(damager), event.getDamage())));
			}
		}
	}

}