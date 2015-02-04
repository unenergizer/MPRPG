package com.minepile.mprpg.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.managers.MessageManager;

public class BlockBreakListener implements Listener{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public BlockBreakListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		Player player = event.getPlayer();
		ItemStack tool = player.getItemInHand();
		Material block = event.getBlock().getType();
		
		//Cancel block break unless the block was broke by a pickaxe.
		if(tool.equals(Material.WOOD_PICKAXE) || tool.equals(Material.STONE_PICKAXE) ||
				tool.equals(Material.IRON_PICKAXE) || tool.equals(Material.DIAMOND_PICKAXE)) {
			
			//Prevent wood pickaxe from mining Iron ore.
			if (block.equals(Material.IRON_ORE) && tool.equals(Material.WOOD_PICKAXE)) {
				event.setCancelled(true);
				
				//show debug message
				if (MessageManager.canShowDebugMessage() == true) {
					player.sendMessage(MessageManager.selectMessagePrefix("debug") 
							+ "You can not mine iron ore with a wood pickaxe.");
				}
			}
			
			//Prevent wood pickaxes and stone pickaxes from mining emerald ore.
			if (block.equals(Material.EMERALD_ORE) && tool.equals(Material.WOOD_PICKAXE)
					&& tool.equals(Material.STONE_PICKAXE)) {
				event.setCancelled(true);
				
				//show debug message
				if (MessageManager.canShowDebugMessage() == true) {
					player.sendMessage(MessageManager.selectMessagePrefix("debug") 
							+ "You can not mine emerald ore with a wood pickaxe or a stone pickaxe.");
				}
			}
			
			//Prevent wood pickaxes, stone pickaxes, and iron pickaxes from mining diamond ore.
			if (block.equals(Material.DIAMOND_ORE) && tool.equals(Material.WOOD_PICKAXE)
					&& tool.equals(Material.STONE_PICKAXE) && tool.equals(Material.IRON_PICKAXE)) {
				event.setCancelled(true);
				
				//show debug message
				if (MessageManager.canShowDebugMessage() == true) {
					player.sendMessage(MessageManager.selectMessagePrefix("debug") 
							+ "You can not mine iron ore with a wood, stone, or iron pickaxe.");
				}
			}
			
			
		} else { //The user did not have a pickaxe. Cancel the block break.
			//Cancel block break
			event.setCancelled(true);
			
			//show debug message
			if (MessageManager.canShowDebugMessage() == true) {
				player.sendMessage(MessageManager.selectMessagePrefix("debug") 
						+ "You can not break blocks.");
			}
		} //End of pickaxe check.
	}

}
