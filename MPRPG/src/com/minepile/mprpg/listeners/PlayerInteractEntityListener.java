package com.minepile.mprpg.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.items.MerchantManager;

public class PlayerInteractEntityListener implements Listener{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public PlayerInteractEntityListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {

		if (event.getRightClicked() instanceof Player) {
			Player npc = (Player) event.getRightClicked();
			
			//Check to see if entity is a Citizens NPC.
			if (npc.hasMetadata("NPC")) {
				
				Player player = event.getPlayer();
				
				if (npc.getName().equalsIgnoreCase("Whizzig")) {
					MerchantManager.toggleNPC(player);
				}
				
				if (npc.getName().equalsIgnoreCase("Vargus")) {
					player.sendMessage(ChatColor.GRAY + "Vargus" + ChatColor.DARK_GRAY + ": "
							+ ChatColor.WHITE + player.getName() + ", I have nothing to teach you. Come back later.");
				}
				
				if (npc.getName().equalsIgnoreCase("Androl Oakhand")) {
					player.sendMessage(ChatColor.GRAY + "Androl Oakhand" + ChatColor.DARK_GRAY + ": "
							+ ChatColor.WHITE + player.getName() + ", I have nothing to teach you. Come back later.");
				}
				

				if (npc.getName().equalsIgnoreCase("Mr. Gribbles")) {
					player.sendMessage(ChatColor.GRAY + "Mr. Gribbles" + ChatColor.DARK_GRAY + ": "
							+ ChatColor.WHITE + player.getName() + ", I have nothing to teach you. Come back later.");
				}
				
			}
		}
	}
}