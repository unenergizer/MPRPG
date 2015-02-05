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
		player.sendMessage(MessageManager.selectMessagePrefix("debug") + "Adding experience.");
	}
	

}
