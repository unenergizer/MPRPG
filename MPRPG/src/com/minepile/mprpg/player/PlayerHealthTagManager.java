package com.minepile.mprpg.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.minepile.mprpg.MPRPG;

public class PlayerHealthTagManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static PlayerHealthTagManager playerHealthTagManagerInstance = new PlayerHealthTagManager();
	
	static Scoreboard sb;
	private Objective obj;
	
	//Create instance
	public static PlayerHealthTagManager getInstance() {
		return playerHealthTagManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		setupScoreboard("Lobby", "RPG");
	}	
	
	public void setupScoreboard(String objectiveName, String displayName) {
		
		sb = Bukkit.getScoreboardManager().getNewScoreboard();
		
		obj = sb.registerNewObjective(objectiveName, "dummy");
		obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
		obj.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + displayName);
	}
	
}
