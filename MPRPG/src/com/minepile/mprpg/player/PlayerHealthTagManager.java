package com.minepile.mprpg.player;

import java.util.UUID;

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
		
		//Resetup players on scoreboard.
		for (Player players : Bukkit.getOnlinePlayers()) {
			if (!players.hasMetadata("NPC")) {
				addPlayer(players);
				updateHealthTag(players);
			}
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
		UUID uuid = player.getUniqueId();
		player.setScoreboard(sb);
		team.addPlayer(player);
		if (PlayerCharacterManager.isPlayerLoaded(player)) {
			obj.getScore(player).setScore((int) PlayerManager.getHealthPoints(uuid));
		} else {
			obj.getScore(player).setScore(50);
		}
	}

	/**
	 * This will update the players HP under their name.
	 * 
	 * @param player The player who will have their HP updated under their name.
	 */
	@SuppressWarnings("deprecation")
	public static void updateHealthTag(final Player player) {
		//Lets start a  task
		Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {
				if (PlayerCharacterManager.isPlayerLoaded(player)) {
					UUID uuid = player.getUniqueId();
					obj.getScore(player).setScore((int) PlayerManager.getHealthPoints(uuid));
				}
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
		Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
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
