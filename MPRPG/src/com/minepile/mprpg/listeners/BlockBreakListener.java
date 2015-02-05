package com.minepile.mprpg.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

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
		Material tool = player.getItemInHand().getType();
		Material block = event.getBlock().getType();
		
		//Cancel block break unless the block was broke by a pickaxe.
		if(tool.equals(Material.WOOD_PICKAXE) || tool.equals(Material.STONE_PICKAXE) ||
				tool.equals(Material.IRON_PICKAXE) || tool.equals(Material.GOLD_PICKAXE) || 
				tool.equals(Material.DIAMOND_PICKAXE)) {
			
			//Chest to see if the block broken was an ORE material.
			if (block.equals(Material.COAL_ORE) || block.equals(Material.IRON_ORE) || 
					block.equals(Material.EMERALD_ORE) || block.equals(Material.GOLD_ORE) ||
					block.equals(Material.DIAMOND_ORE)) {
				
				///////////////////////////////////////////
				//Define what pick can break which block.//
				///////////////////////////////////////////
				
				//Prevent wood pickaxe from mining Iron ore.
				if (block.equals(Material.IRON_ORE) && tool.equals(Material.WOOD_PICKAXE)) {
					event.setCancelled(true);
					
					//show debug message
					if (MessageManager.canShowDebugMessage() == true) {
						player.sendMessage(MessageManager.selectMessagePrefix("debug") 
								+ "You're pickaxe must be level 20 or greater.");
					}
				}
				
				//Prevent wood pickaxes and stone pickaxes from mining emerald ore.
				if (block.equals(Material.EMERALD_ORE)) {
					
					if (tool.equals(Material.WOOD_PICKAXE) || tool.equals(Material.STONE_PICKAXE)) {
						event.setCancelled(true);
						
						//show debug message
						if (MessageManager.canShowDebugMessage() == true) {
							player.sendMessage(MessageManager.selectMessagePrefix("debug") 
									+ "You're pickaxe must be level 40 or greater.");
						}
					}
				}
				
				//Prevent wood pickaxes and stone pickaxes from mining emerald ore.
				if (block.equals(Material.GOLD_ORE)) {
					
					if (tool.equals(Material.WOOD_PICKAXE) || tool.equals(Material.STONE_PICKAXE) ||
							tool.equals(Material.IRON_PICKAXE)) {
						event.setCancelled(true);
					
						//show debug message
						if (MessageManager.canShowDebugMessage() == true) {
							player.sendMessage(MessageManager.selectMessagePrefix("debug") 
									+ "You're pickaxe must be level 60 or greater.");
						}
					}
				}
				
				//Prevent wood pickaxes and stone pickaxes from mining emerald ore.
				if (block.equals(Material.DIAMOND_ORE)) {
					
					if (tool.equals(Material.WOOD_PICKAXE) || tool.equals(Material.STONE_PICKAXE) ||
							tool.equals(Material.IRON_PICKAXE) || tool.equals(Material.GOLD_PICKAXE)) {
						event.setCancelled(true);
						
						//show debug message
						if (MessageManager.canShowDebugMessage() == true) {
							player.sendMessage(MessageManager.selectMessagePrefix("debug") 
									+ "You're pickaxe must be level 80 or greater.");
						}
					}
				}
				
				///////////////////////////////
				//END mining and tool checks //
				///////////////////////////////
				
			} else { //The block that was broken was not a ORE material.
				event.setCancelled(true);
				
				//show debug message
				if (MessageManager.canShowDebugMessage() == true) {
					player.sendMessage(MessageManager.selectMessagePrefix("debug") 
							+ "You can not mine this block.");
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
