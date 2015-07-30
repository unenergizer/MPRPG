package com.minepile.mprpg.listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Egg;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.items.CurrencyItemManager;

public class ProjectileHitListener implements Listener{

	public static MPRPG plugin;

	@SuppressWarnings("static-access")
	public ProjectileHitListener(MPRPG plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		if (event.getEntity() instanceof Egg) {
            Location loc = event.getEntity().getLocation();
            
            Random rand = new Random();
			int currencyMin = 1;
			int currencyMax = 5;
            int range = currencyMax - currencyMin + 1;
			int randomNum =  rand.nextInt(range) + currencyMin;

			if (randomNum != 0) {
				ItemStack money = CurrencyItemManager.makeItem("Copper");
				money.setAmount(randomNum);

				//Generate drops
				Bukkit.getWorld("world").dropItemNaturally(loc, money);

				Bukkit.getWorld("world").playSound(loc, Sound.ITEM_BREAK, .8f, .9f);
			}
        }
	}
}