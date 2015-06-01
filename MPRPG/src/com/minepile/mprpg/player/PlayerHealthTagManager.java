package com.minepile.mprpg.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.minepile.mprpg.MPRPG;

public class PlayerHealthTagManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static PlayerHealthTagManager playerHealthTagManagerInstance = new PlayerHealthTagManager();
	
	@SuppressWarnings("unused")
	private static int taskID; 
	
	static Scoreboard sb;
	static Team team;
	private static Objective obj;
	
	//Create instance
	public static PlayerHealthTagManager getInstance() {
		return playerHealthTagManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		setupScoreboard();
		
		for (Player players : Bukkit.getOnlinePlayers()) {
			addPlayer(players);
			updateHealthTag(players);
		}
	}	
	
	public void setupScoreboard() {
		
		sb = Bukkit.getScoreboardManager().getNewScoreboard();
		
		obj = sb.registerNewObjective("playerhealthtag", "dummy");
		obj.setDisplayName(ChatColor.RED + "❤ ");
		obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
		
		setupTeam();
	}
	
	public static void setupTeam() {
		team = sb.registerNewTeam("team0");
		team.setCanSeeFriendlyInvisibles(false);
		team.setAllowFriendlyFire(true);
		team.setPrefix("");
	}

	@SuppressWarnings("deprecation")
	public static void addPlayer(Player player) {
		player.setScoreboard(sb);
		team.addPlayer(player);
		obj.getScore(player).setScore(PlayerManager.getHealthPoints(player.getName()));
	}
	
	@SuppressWarnings("deprecation")
	public static void updateHealthTag(final Player player) {
		//Lets start a  task
		taskID = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				
				obj.getScore(player).setScore(PlayerManager.getHealthPoints(player.getName()));
				
			} //END Run method.
		}, 5); //(20 ticks = 1 second)
	}

	public static Scoreboard getSb() {
		return sb;
	}

	public static Team getTeam() {
		return team;
	}

	public static Objective getObj() {
		return obj;
	}
	
}
