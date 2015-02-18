package com.minepile.mprpg.listeners;

import net.md_5.bungee.api.ChatColor;

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
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case BROWN_MUSHROOM:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case CACTUS:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case CARROT:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case CARROT_ITEM:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case CARROT_STICK:
			player.sendMessage("You broke: " + blockType.toString());
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
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case DRAGON_EGG:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case FLOWER_POT:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case FLOWER_POT_ITEM:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case JACK_O_LANTERN:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case LEAVES:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case LEAVES_2:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case LONG_GRASS:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case MELON:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case MELON_BLOCK:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case MELON_SEEDS:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case MELON_STEM:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case POISONOUS_POTATO:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case POTATO:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case POTATO_ITEM:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case PUMPKIN:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case PUMPKIN_SEEDS:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case PUMPKIN_STEM:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case RED_MUSHROOM:
			player.sendMessage("You broke: " + blockType.toString());
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
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case SEEDS:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case SUGAR_CANE:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case SUGAR_CANE_BLOCK:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case VINE:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case WATER_LILY:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case WHEAT:
			player.sendMessage("You broke: " + blockType.toString());
			break;
		case YELLOW_FLOWER:
			player.sendMessage("You broke: " + blockType.toString());
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
			player.sendMessage(ChatColor.RED + "defaultInvoked.");
			player.sendMessage("You broke: " + blockType.toString());
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