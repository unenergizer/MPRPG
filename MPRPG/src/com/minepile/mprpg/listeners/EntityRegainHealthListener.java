package com.minepile.mprpg.listeners;

import io.puharesource.mc.titlemanager.api.ActionbarTitleObject;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.chat.MessageManager;
import com.minepile.mprpg.items.LoreManager;
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
			
			double currentHP = PlayerManager.getHealthPoints(playerName);
			double maxHP = PlayerManager.getMaxHealthPoints(playerName);
			double healAmount = event.getAmount() + LoreManager.getRegenBonus((LivingEntity)event.getEntity());
			double newHP = currentHP + healAmount;
			double hpPercent = ((100 * newHP) / maxHP);
			double hpBarPercent = (20 * currentHP) / maxHP;		
			
			if (currentHP == maxHP) {
				PlayerManager.setHealthPoints(playerName, maxHP);
				player.setHealth(20);
			} else if (currentHP < maxHP){
				PlayerManager.setHealthPoints(playerName, newHP);
				player.setHealth(hpBarPercent);

				//Send the player a hp change message.
				new ActionbarTitleObject(ChatColor.GREEN + "+" + 
						ChatColor.GRAY + Integer.toString((int) healAmount) + ChatColor.BOLD + " HP: " +
						MessageManager.percentBar(hpPercent) + 
						ChatColor.GRAY + " [" + ChatColor.GREEN + Integer.toString((int) newHP) +
						ChatColor.GRAY + " / " + ChatColor.GREEN + Integer.toString((int) maxHP) +
						ChatColor.GRAY + "]").send(player);
				
				//Update the players health tag.
				PlayerHealthTagManager.updateHealthTag(player);
			} else if (hpBarPercent <= 0) {
				event.setCancelled(true);
			}
		}
	}
}
