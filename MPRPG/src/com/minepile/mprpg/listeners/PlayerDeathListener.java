package com.minepile.mprpg.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerHealthTagManager;

public class PlayerDeathListener implements Listener {
	
	public static MPRPG plugin;


	@SuppressWarnings("static-access")
	public PlayerDeathListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void death(PlayerDeathEvent event){
		Player player = (Player) event.getEntity();
		Player killer = player.getKiller();
		String playerName = player.getName();
		String killerName = "";
		String deathCause = "";
		
		//update the players health tag.
		PlayerHealthTagManager.updateHealthTag(player);
		
		if(!(killer instanceof Player) && player.getLastDamageCause().getCause() != null){
			//Get damage type so we can build a death message.
			switch(player.getLastDamageCause().getCause()){
			case BLOCK_EXPLOSION:
				deathCause = "from an explosion";
				break;
			case CONTACT:
				deathCause = "from huging a cactus";
				break;
			case CUSTOM:
				deathCause = "from something awesome";
				break;
			case DROWNING:
				deathCause = "from not taking a breath";
				break;
			case ENTITY_ATTACK:
				deathCause = "from being attacked";
				break;
			case ENTITY_EXPLOSION:
				deathCause = "by playing with a creeper";
				break;
			case FALL:
				deathCause = "by bungee jumping without a cord";
				break;
			case FALLING_BLOCK:
				deathCause = "by being smooshed";
				break;
			case FIRE:
				deathCause = "after jumping into a campfire";
				break;
			case FIRE_TICK:
				deathCause = "after jumping into a campfire";
				break;
			case LAVA:
				deathCause = "from swimming in lava";
				break;
			case LIGHTNING:
				deathCause = "by flying a kite in an electrical storm";
				break;
			case MAGIC:
				deathCause = "by playing with magic";
				break;
			case MELTING:
				deathCause = "from thawing";
				break;
			case POISON:
				deathCause = "by drinking poison";
				break;
			case PROJECTILE:
				deathCause = "from being was shot";
				break;
			case STARVATION:
				deathCause = "from forgetting to eat";
				break;
			case SUFFOCATION:
				deathCause = "from not taking a breath";
				break;
			case SUICIDE:
				deathCause = "by taking the easy way out";
				break;
			case THORNS:
				deathCause = "from being poked";
				break;
			case VOID:
				deathCause = "from falling into the void";
				break;
			case WITHER:
				deathCause = "because they danced with the wither";
				break;
			default:
				deathCause = "by something crazy";
				break;
			}
		} else {
			killerName = killer.getName();
			deathCause = ChatColor.GOLD + "by " + ChatColor.AQUA + killerName;
		}
		
		event.setDeathMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "MPRPG" + ChatColor.GOLD + "> " 
		+ ChatColor.RED + playerName + ChatColor.GOLD + " was killed " + ChatColor.GREEN + deathCause + ChatColor.GOLD + "!");
	}
}
