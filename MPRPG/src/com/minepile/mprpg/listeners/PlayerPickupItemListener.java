package com.minepile.mprpg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;

public class PlayerPickupItemListener implements Listener{
	
	public static MPRPG plugin;
	
	@SuppressWarnings("static-access")
	public PlayerPickupItemListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		
		Player player = event.getPlayer();
		ItemStack item = event.getItem().getItemStack();
		
		//Get the items name, to know what type of statistics to generate.
		//TODO: Saved this for when item have names.
		//String item = event.getItem().getName();
		
		switch(item.getType()) {
		/////////////
		//Currency //
		/////////////
		case CLAY_BRICK:
			player.sendMessage("picked up a clay brick");
			break;
		case IRON_INGOT:
			player.sendMessage("picked up a iron ingot");
			break;
		case GOLD_INGOT:
			player.sendMessage("picked up a gold ingot");
			break;
		case PORTAL:
			player.sendMessage("picked up a portal");
			break;
		
		//////////
		//Armor //
		//////////
		
		//Tier #1
		case LEATHER_BOOTS:
			break;
		case LEATHER_CHESTPLATE:
			player.sendMessage("picked up a lether chestplate");
			break;
		case LEATHER_HELMET:
			break;
		case LEATHER_LEGGINGS:
			break;
			
		//Tier #2	
		case CHAINMAIL_BOOTS:
			break;
		case CHAINMAIL_CHESTPLATE:
			break;
		case CHAINMAIL_HELMET:
			break;
		case CHAINMAIL_LEGGINGS:
			break;
			
		//Tier #3	
		case IRON_BOOTS:
			break;
		case IRON_CHESTPLATE:
			break;
		case IRON_HELMET:
			break;
		case IRON_LEGGINGS:
			break;
			
		//Tier #4
		case GOLD_BOOTS:
			break;
		case GOLD_CHESTPLATE:
			break;
		case GOLD_HELMET:
			break;
		case GOLD_LEGGINGS:
			break;
		
		//Tier #5
		case DIAMOND_BOOTS:
			break;
		case DIAMOND_CHESTPLATE:
			break;
		case DIAMOND_HELMET:
			break;
		case DIAMOND_LEGGINGS:
			break;
		
		
		/////////
		//Axes //
		/////////
		case WOOD_AXE: //Tier #1
			break;
		case STONE_AXE: //Tier #2
			break;
		case IRON_AXE: //Tier #3
			break;	
		case GOLD_AXE: //Tier #4
			break;
		case DIAMOND_AXE: //Tier #5
			break;		

		//////////////////////
		//Polearms / Spears //
		//////////////////////
		case WOOD_SPADE: //Tier #1
			break;
		case STONE_SPADE: //Tier #2
			break;
		case IRON_SPADE: //Tier #3
			break;
		case GOLD_SPADE: //Tier #4
			break;		
		case DIAMOND_SPADE: //Tier #5
			break;
			
		///////////
		//Swords //
		///////////
		case WOOD_SWORD: //Tier #1
			break;
		case STONE_SWORD: //Tier #2
			break;
		case IRON_SWORD: //Tier #3
			break;
		case GOLD_SWORD: //Tier #4
			break;
		case DIAMOND_SWORD: //Tier #5
			break;
			
		//////////
		//Wands //
		//////////
		case WOOD_HOE: //Tier #1
			break;
		case STONE_HOE: //Tier #2
			break;
		case IRON_HOE: //Tier #3
			break;
		case GOLD_HOE: //Tier #4
			break;
		case DIAMOND_HOE: //Tier #5
			break;	
			
		//Default case.  "Everything else"
		default:
			break;
		}
	}
}