package com.minepile.mprpg.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.managers.BlockRegenerationManager;
import com.minepile.mprpg.managers.MessageManager;
import com.minepile.mprpg.professions.Herbalism;
import com.minepile.mprpg.professions.Mining;

public class BlockBreakListener implements Listener{
	
	public static MPRPG plugin;
	public boolean allPlayersCanBreakBlocks = false;
	
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
		
		/////////////////////////
		/// Mining Profession ///
		/////////////////////////
		
		case COAL_ORE: //Mining profession
			if(tool.equals(Material.WOOD_PICKAXE) || tool.equals(Material.STONE_PICKAXE) ||
					tool.equals(Material.IRON_PICKAXE) || tool.equals(Material.GOLD_PICKAXE) ||
					tool.equals(Material.DIAMOND_PICKAXE)) {
				//The player used the right tool, so lets try to add EXP.
				Mining.toggleOreMined(player, tool, Material.COAL_ORE);
				//Lets add the block to the ore regeneration list to be replaced later.
				BlockRegenerationManager.setBlock(player, blockType, Material.STONE, block.getLocation());
				
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
				Mining.toggleOreMined(player, tool, Material.DIAMOND_ORE);

				//Lets add the block to the ore regeneration list to be replaced later.
				BlockRegenerationManager.setBlock(player, blockType, Material.STONE, block.getLocation());
				
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
				Mining.toggleOreMined(player, tool,  Material.EMERALD_ORE);

				//Lets add the block to the ore regeneration list to be replaced later.
				BlockRegenerationManager.setBlock(player, blockType, Material.STONE, block.getLocation());
				
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
				Mining.toggleOreMined(player, tool,  Material.GOLD_ORE);

				//Lets add the block to the ore regeneration list to be replaced later.
				BlockRegenerationManager.setBlock(player, blockType, Material.STONE, block.getLocation());
				
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
				Mining.toggleOreMined(player, tool, Material.IRON_ORE);

				//Lets add the block to the ore regeneration list to be replaced later.
				BlockRegenerationManager.setBlock(player, blockType, Material.STONE, block.getLocation());
				
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
		////////////////////////////
		/// Herbalism Profession ///
		////////////////////////////
			
		case BAKED_POTATO:
			break;
		case BROWN_MUSHROOM:
			break;
		case CACTUS:
			break;
		case CARROT:
			break;
		case CARROT_ITEM:
			break;
		case CARROT_STICK:
			break;
		case DEAD_BUSH:
			if(tool.equals(Material.SHEARS)) {
				//The player used the right tool, so lets try to add EXP.
				Herbalism.toggleHerbalism(player);

				//Lets add the block to the ore regeneration list to be replaced later.
				BlockRegenerationManager.setBlock(player, blockType, Material.AIR, block.getLocation());
				
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
		case DOUBLE_PLANT:
			break;
		case DRAGON_EGG:
			break;
		case FLOWER_POT:
			break;
		case FLOWER_POT_ITEM:
			break;
		case GRASS:
			break;
		case JACK_O_LANTERN:
			break;
		case LEAVES:
			break;
		case LEAVES_2:
			break;
		case LONG_GRASS:
			break;
		case MELON:
			break;
		case MELON_BLOCK:
			break;
		case MELON_SEEDS:
			break;
		case MELON_STEM:
			break;
		case POISONOUS_POTATO:
			break;
		case POTATO:
			break;
		case POTATO_ITEM:
			break;
		case PUMPKIN:
			break;
		case PUMPKIN_SEEDS:
			break;
		case PUMPKIN_STEM:
			break;
		case RED_MUSHROOM:
			break;
		case RED_ROSE:
			if(tool.equals(Material.SHEARS)) {
				//The player used the right tool, so lets try to add EXP.
				Herbalism.toggleHerbalism(player);

				//Lets add the block to the ore regeneration list to be replaced later.
				BlockRegenerationManager.setBlock(player, blockType, Material.AIR, block.getLocation());
				
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
		case SAPLING:
			break;
		case SEEDS:
			break;
		case SUGAR_CANE:
			break;
		case SUGAR_CANE_BLOCK:
			break;
		case VINE:
			break;
		case WATER_LILY:
			break;
		case WHEAT:
			break;
		case YELLOW_FLOWER:
			if(tool.equals(Material.SHEARS)) {
				//The player used the right tool, so lets try to add EXP.
				Herbalism.toggleHerbalism(player);

				//Lets add the block to the ore regeneration list to be replaced later.
				BlockRegenerationManager.setBlock(player, blockType, Material.AIR, block.getLocation());
				
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
			
		//////////////////////
		/// Default Action ///
		//////////////////////
			
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