package com.minepile.mprpg.professions;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.managers.MessageManager;

public class Mining {
	
	//setup variables
	public static MPRPG plugin;
	static Mining miningInstance = new Mining();
	
	//Create instance
	public static Mining getInstance() {
		return miningInstance;
	}
	
	//Setup MessageManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	public static void addExperience(Player player, Material tool) {
		if (tool.equals(Material.WOOD_PICKAXE)) {
			player.sendMessage(MessageManager.selectMessagePrefix("debug") + "Adding experience for wood pick.");
		} else if (tool.equals(Material.STONE_PICKAXE)) {
			player.sendMessage(MessageManager.selectMessagePrefix("debug") + "Adding experience for stone pick.");
		} else if (tool.equals(Material.IRON_PICKAXE)) {
			player.sendMessage(MessageManager.selectMessagePrefix("debug") + "Adding experience for iron pick.");
		} else if (tool.equals(Material.GOLD_PICKAXE)) {
			player.sendMessage(MessageManager.selectMessagePrefix("debug") + "Adding experience for gold pick.");
		} else if (tool.equals(Material.DIAMOND_PICKAXE)) {
			player.sendMessage(MessageManager.selectMessagePrefix("debug") + "Adding experience for diamond pick.");
		} else {
			//This should never happen.
			player.sendMessage(MessageManager.selectMessagePrefix("debug") + "Can not add exp to your tool.");
		}
	}
	

}
