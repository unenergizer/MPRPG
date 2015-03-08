package com.minepile.mprpg.listeners;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.equipment.LoreManager;
import com.minepile.mprpg.player.PlayerHealthTagManager;
import com.minepile.mprpg.player.PlayerManager;

public class EntityRegainHealthListener implements Listener{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public EntityRegainHealthListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityRegainHealth(EntityRegainHealthEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			String playerName = player.getName();
			
			int currentHP = PlayerManager.getHealthPoints(playerName);
			int maxHP = PlayerManager.getMaxHealthPoints(playerName);
			int healAmount = (int) (event.getAmount() + LoreManager.getRegenBonus((LivingEntity)event.getEntity()));
			int newHP = currentHP + healAmount;
			int hpPercent = (int) ((100 * newHP) / maxHP);
			int hpBarPercent = (20 * currentHP) / maxHP;
			
			
			if (currentHP == maxHP) {
				PlayerManager.setHealthPoints(playerName, maxHP);
				player.setHealth(20);
				
				/* Redundant message.
				player.sendMessage(ChatColor.GREEN + "            " + 
						ChatColor.GRAY + ChatColor.BOLD + " HP: " +
						ChatColor.GRAY + ChatColor.BOLD + "100%" +
						ChatColor.GRAY + " [" + ChatColor.GREEN + maxHP +
						ChatColor.GRAY + " / " + ChatColor.GREEN + maxHP +
						ChatColor.GRAY + "]");
				*/
			} else if (currentHP < maxHP){
				PlayerManager.setHealthPoints(playerName, newHP);
				
				if (hpBarPercent > 1) {
					player.setHealth(hpBarPercent - 1);
				} else {
					player.setHealth(hpBarPercent);
				}
				
				player.sendMessage(ChatColor.GREEN + "         +" + 
						ChatColor.GRAY + healAmount + ChatColor.BOLD + " HP: " +
						ChatColor.GRAY + ChatColor.BOLD + hpPercent + "%" +
						ChatColor.GRAY + " [" + ChatColor.GREEN + newHP +
						ChatColor.GRAY + " / " + ChatColor.GREEN + maxHP +
						ChatColor.GRAY + "]");
				
				//Update the players health tag.
				PlayerHealthTagManager.updateHealthTag(player);
			}
		}
	}
}
