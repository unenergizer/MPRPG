package com.minepile.mprpg.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.managers.MessageManager;
import com.minepile.mprpg.managers.OreRegenerationManager;
import com.minepile.mprpg.professions.Mining;

public class BlockBreakListener implements Listener{
	
	public static MPRPG plugin;
	public boolean allPlayersCanBreakBlocks = true;
	
	@SuppressWarnings("static-access")
	public BlockBreakListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		Player player = event.getPlayer();					//The player who triggered the event.
		Material tool = player.getItemInHand().getType();	//The tool the player may be holding.
		Block block = event.getBlock();						//Gets the actual block broken.
		Material blockType = event.getBlock().getType();	//The block type that is being broken.
		
		//Cancel experience drops
		event.setExpToDrop(0);
		
		switch(blockType) {
		case COAL_ORE: //Mining profession
			if(tool.equals(Material.WOOD_PICKAXE) || tool.equals(Material.STONE_PICKAXE) ||
					tool.equals(Material.IRON_PICKAXE) || tool.equals(Material.GOLD_PICKAXE) ||
					tool.equals(Material.DIAMOND_PICKAXE)) {
				//The player used the right tool, so lets try to add EXP.
				Mining.minedOre(player, tool, Material.COAL_ORE);
				//Lets add the block to the ore regeneration list to be replaced later.
				OreRegenerationManager.setBlock(player, blockType, block.getLocation());
				
				//Now cancel the event.
				event.setCancelled(true);
			} else {
				//Cancel the block being broken.
				event.setCancelled(true);	
				//show debug message
				if (MessageManager.canShowDebugMessage() == true) {
					player.sendMessage(MessageManager.selectMessagePrefix("debug") 
							+ "You can not break this block yet.");
				}
			}
			break;
		case DIAMOND_ORE: //Mining profession
			if(tool.equals(Material.DIAMOND_PICKAXE)) {
				//The player used the right tool, so lets try to add EXP.
				Mining.minedOre(player, tool, Material.DIAMOND_ORE);

				//Lets add the block to the ore regeneration list to be replaced later.
				OreRegenerationManager.setBlock(player, blockType, block.getLocation());
				
				//Now cancel the event.
				event.setCancelled(true);
			} else {
				//Cancel the block being broken.
				event.setCancelled(true);	
				//show debug message
				if (MessageManager.canShowDebugMessage() == true) {
					player.sendMessage(MessageManager.selectMessagePrefix("debug") 
							+ "You can not break this block yet.");
				}
			}
			break;
		case EMERALD_ORE: //Mining profession
			if(tool.equals(Material.IRON_PICKAXE) || tool.equals(Material.GOLD_PICKAXE) ||
					tool.equals(Material.DIAMOND_PICKAXE)) {
				//The player used the right tool, so lets try to add EXP.
				Mining.minedOre(player, tool,  Material.EMERALD_ORE);

				//Lets add the block to the ore regeneration list to be replaced later.
				OreRegenerationManager.setBlock(player, blockType, block.getLocation());
				
				//Now cancel the event.
				event.setCancelled(true);
			} else {
				//Cancel the block being broken.
				event.setCancelled(true);	
				//show debug message
				if (MessageManager.canShowDebugMessage() == true) {
					player.sendMessage(MessageManager.selectMessagePrefix("debug") 
							+ "You can not break this block yet.");
				}
			}
			break;
		case GOLD_ORE: //Mining profession
			if(tool.equals(Material.GOLD_PICKAXE) || tool.equals(Material.DIAMOND_PICKAXE)) {
				//The player used the right tool, so lets try to add EXP.
				Mining.minedOre(player, tool,  Material.GOLD_ORE);

				//Lets add the block to the ore regeneration list to be replaced later.
				OreRegenerationManager.setBlock(player, blockType, block.getLocation());
				
				//Now cancel the event.
				event.setCancelled(true);
			} else {
				//Cancel the block being broken.
				event.setCancelled(true);	
				//show debug message
				if (MessageManager.canShowDebugMessage() == true) {
					player.sendMessage(MessageManager.selectMessagePrefix("debug") 
							+ "You can not break this block yet.");
				}
			}
			break;
		case IRON_ORE: //Mining profession
			if(tool.equals(Material.STONE_PICKAXE) || tool.equals(Material.IRON_PICKAXE) || 
					tool.equals(Material.GOLD_PICKAXE) || tool.equals(Material.DIAMOND_PICKAXE)) {
				//The player used the right tool, so lets try to add EXP.
				Mining.minedOre(player, tool, Material.IRON_ORE);

				//Lets add the block to the ore regeneration list to be replaced later.
				OreRegenerationManager.setBlock(player, blockType, block.getLocation());
				
				//Now cancel the event.
				event.setCancelled(true);
			} else {
				//Cancel the block being broken.
				event.setCancelled(true);	
				//show debug message
				if (MessageManager.canShowDebugMessage() == true) {
					player.sendMessage(MessageManager.selectMessagePrefix("debug") 
							+ "You can not break this block yet.");
				}
			}
			break;
		default:
			if (allPlayersCanBreakBlocks == false) {
				//Cancel the block being broken.
				event.setCancelled(true);	
				//show debug message
				if (MessageManager.canShowAdminDebugMessage() == true) {
					player.sendMessage(MessageManager.selectMessagePrefix("debug") 
							+ "You can not break blocks.");
				}
			}
			break;
		}
	}
}