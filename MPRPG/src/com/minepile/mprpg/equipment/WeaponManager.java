package com.minepile.mprpg.equipment;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;

public class WeaponManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static WeaponManager weaponManagerInstance = new WeaponManager();
	
	//Create instance
	public static WeaponManager getInstance() {
		return weaponManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	
	
	public enum itemQuality {
		JUNK,		//Grey
		COMMON,		//White
		UNCOMMON,	//Green
		RARE,		//Blue
		EPIC,		//Purple
		LEGENDARY	//Gold
	}
	
	public void makeItem(Player player, Material weapon) {
		switch(weapon) {
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
		
		//Default (not used)	
		default:
			break;
		}
	}
}
