package com.minepile.mprpg.listeners;

import org.bukkit.GameMode;
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
			event.setCancelled(true);
			break;
		case BROWN_MUSHROOM:
			event.setCancelled(true);
			break;
		case CACTUS:
			event.setCancelled(true);
			break;
		case CARROT:
			event.setCancelled(true);
			break;
		case CARROT_ITEM:
			event.setCancelled(true);
			break;
		case CARROT_STICK:
			event.setCancelled(true);
			break;
		case DEAD_BUSH:
			if(tool.equals(Material.SHEARS)) {
				//The player used the right tool, so lets try to add EXP.
				Herbalism.toggleHerbalism(player, blockType);

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
			event.setCancelled(true);
			break;
		case DRAGON_EGG:
			event.setCancelled(true);
			break;
		case FLOWER_POT:
			event.setCancelled(true);
			break;
		case FLOWER_POT_ITEM:
			event.setCancelled(true);
			break;
		case JACK_O_LANTERN:
			event.setCancelled(true);
			break;
		case LEAVES:
			event.setCancelled(true);
			break;
		case LEAVES_2:
			event.setCancelled(true);
			break;
		case LONG_GRASS:
			event.setCancelled(true);
			break;
		case MELON:
			event.setCancelled(true);
			break;
		case MELON_BLOCK:
			event.setCancelled(true);
			break;
		case MELON_SEEDS:
			event.setCancelled(true);
			break;
		case MELON_STEM:
			event.setCancelled(true);
			break;
		case POISONOUS_POTATO:
			event.setCancelled(true);
			break;
		case POTATO:
			event.setCancelled(true);
			break;
		case POTATO_ITEM:
			event.setCancelled(true);
			break;
		case PUMPKIN:
			event.setCancelled(true);
			break;
		case PUMPKIN_SEEDS:
			event.setCancelled(true);
			break;
		case PUMPKIN_STEM:
			event.setCancelled(true);
			break;
		case RED_MUSHROOM:
			event.setCancelled(true);
			break;
		case RED_ROSE:
			if(tool.equals(Material.SHEARS)) {
				//The player used the right tool, so lets try to add EXP.
				Herbalism.toggleHerbalism(player, blockType);

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
			event.setCancelled(true);
			break;
		case SEEDS:
			event.setCancelled(true);
			break;
		case SUGAR_CANE:
			event.setCancelled(true);
			break;
		case SUGAR_CANE_BLOCK:
			event.setCancelled(true);
			break;
		case VINE:
			event.setCancelled(true);
			break;
		case WATER_LILY:
			event.setCancelled(true);
			break;
		case WHEAT:
			event.setCancelled(true);
			break;
		case YELLOW_FLOWER:
			if(tool.equals(Material.SHEARS)) {
				//The player used the right tool, so lets try to add EXP.
				Herbalism.toggleHerbalism(player, blockType);

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

			//Cancel the block being broken if the player is not OP.
			if (!player.isOp()) {
				event.setCancelled(true);	
				//show debug message
				if (MessageManager.canShowAdminDebugMessage() == true) {
					player.sendMessage(MessageManager.selectMessagePrefix("debug") 
							+ "You can not break blocks.");
				}
			} else if (player.isOp() && player.getGameMode().equals(GameMode.CREATIVE)) {
				event.setCancelled(false);
			} else {
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