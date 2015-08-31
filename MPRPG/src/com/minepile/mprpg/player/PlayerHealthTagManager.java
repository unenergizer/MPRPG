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
	
	//Variables to set up the scoreboard.
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
	
	/**
	 * This set's up a Scoreboard to show the players HP under their name.
	 */
	public void setupScoreboard() {
		
		sb = Bukkit.getScoreboardManager().getNewScoreboard();
		
		obj = sb.registerNewObjective("playerhealthtag", "dummy");
		obj.setDisplayName(ChatColor.RED + "❤ ");
		obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
		
		setupTeam();
	}
	
	/**
	 * This set's up a Team for the Scoreboard.
	 */
	public static void setupTeam() {
		team = sb.registerNewTeam("team0");
		team.setCanSeeFriendlyInvisibles(false);
		team.setAllowFriendlyFire(true);
		team.setPrefix("");
	}
	
	/**
	 * This adds a player to the Scoreboard.
	 * 
	 * @param player A player that will be added to the Scoreboard.
	 */
	@SuppressWarnings("deprecation")
	public static void addPlayer(Player player) {
		player.setScoreboard(sb);
		team.addPlayer(player);
		obj.getScore(player).setScore((int) PlayerManager.getHealthPoints(player.getName()));
	}
	
	/**
	 * This will update the players score on the scoreboard.  This will then show the players new HP under their name.
	 * 
	 * @param player The player who will have their HP updated under their name.
	 */
	@SuppressWarnings("deprecation")
	public static void updateHealthTag(final Player player) {
		//Lets start a  task
		taskID = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				
				obj.getScore(player).setScore((int) PlayerManager.getHealthPoints(player.getName()));
				
			} //END Run method.
		}, 5); //(20 ticks = 1 second)
	}
	
	/**
	 * This will update the NPC score on the scoreboard.  This will then show the NPC new HP under its name.
	 * 
	 * @param player The player who will have their HP updated under their name.
	 */
	@SuppressWarnings("deprecation")
	public static void updateNPCHealthTag(final Player npc, final double maxHP) {
		//Lets start a  task
		taskID = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				
				obj.getScore(npc).setScore((int) maxHP);
				
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
