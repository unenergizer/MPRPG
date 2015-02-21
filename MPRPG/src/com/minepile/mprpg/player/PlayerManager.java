package com.minepile.mprpg.player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;

public class PlayerManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static PlayerManager playerManagerInstance = new PlayerManager();
	
	//MAIN STATS
	static HashMap<String, Integer> healthPoints = new HashMap<String, Integer>();
	static HashMap<String, Integer> maxHealthPoints = new HashMap<String, Integer>();
	static HashMap<String, Integer> energyPoints = new HashMap<String, Integer>();
	static HashMap<String, Integer> maxEnergyPoints = new HashMap<String, Integer>();
	
	static HashMap<String, Integer> dexterityMap = new HashMap<String, Integer>();
	static HashMap<String, Integer> intellectMap = new HashMap<String, Integer>();
	static HashMap<String, Integer> luckMap = new HashMap<String, Integer>();
	static HashMap<String, Integer> personalityMap = new HashMap<String, Integer>();
	static HashMap<String, Integer> strengthMap = new HashMap<String, Integer>();
	static HashMap<String, Integer> vitalityMap = new HashMap<String, Integer>();
	
	//Base statistic rates
	static int baseHealthPoints = 500;
	static int baseEnergyPoints = 500;
	static int baseHealthRegenRate = 1;
	static int baseEnergyRegenRate = 1;
	
	//Base attributes
	static int dexterity = 0;
	static int intellect = 0;
	static int luck = 0;
	static int personality = 0;
	static int strength = 0;
	static int vitality = 0;
	
	//Create instance
	public static PlayerManager getInstance() {
		return playerManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		//If the server reloads, lets remove all players from the hashMaps.
		//We will add them back after this step.
		for (Player players : Bukkit.getOnlinePlayers()) {
			removePlayer(players);
		}
		
		//If the server reloads, then setup all the players again.
		for (Player players : Bukkit.getOnlinePlayers()) {
			setupPlayer(players);
		}
	}	
	
	public static void setPlayerHealthPoints(Player player, double hpAmount, boolean heal) {
		String playerName = player.getName();
		int currentHP = healthPoints.get(playerName);
		
		//If heal == true, then we are healing the player and need to add to the players health.
		if (heal == true) { //Player is being healed.
			double newHP = currentHP + hpAmount;
			int maxHP = maxHealthPoints.get(playerName);
			int hpBarPercent = (int) ((20 * newHP) / maxHP);
			int hpPercent = (int) ((100 * newHP) / maxHP);
			
			
			//Set new value if player is being healed.
			//Do not let the heal value go over the maxHP.
			if (newHP <= maxHP) {
				//Sets new HP value in the hashMap.
				healthPoints.put(playerName, (int) newHP);
				
				//Sets the players hearts on the player bar.
				player.setHealth(hpBarPercent);
				
				//Send the player the debug message.
				player.sendMessage(ChatColor.GREEN + "         +" + 
						ChatColor.GRAY + hpAmount + ChatColor.BOLD + " HP: " +
						ChatColor.GRAY + ChatColor.BOLD + hpPercent + "%" + 
						ChatColor.GRAY + " [" + ChatColor.GREEN + newHP +
						ChatColor.GRAY + " / " + ChatColor.GREEN + maxHP +
						ChatColor.GRAY + "]");
				
				//If the health is 99%, go ahead and but it to 100% for healthbar sync reasons.
				if(newHP >= maxHP - 1) {
					//Sets new HP value in the hashMap.
					healthPoints.put(playerName, maxHP);
					
					//Sets the players hearts on the player bar.
					player.setHealth(20);
					
					//Send the player the debug message.
					player.sendMessage(ChatColor.GREEN + "         +" + 
							ChatColor.GRAY + hpAmount + ChatColor.BOLD + " HP: " +
							ChatColor.GRAY + ChatColor.BOLD + "100%" + 
							ChatColor.GRAY + " [" + ChatColor.GREEN + maxHP +
							ChatColor.GRAY + " / " + ChatColor.GREEN + maxHP +
							ChatColor.GRAY + "]");
				}
			}
		} else { //Player is being hurt.
			double newHP = currentHP - hpAmount;
			int maxHP = maxHealthPoints.get(playerName);
			int hpBarPercent = (int) ((20 * newHP) / maxHP);
			int hpPercent = (int) ((100 * newHP) / maxHP);
			
			//Play a sound for the player.  This is necessary
			//because we canceled the event, which cancels the noise
			//and damage sent to the player.
			player.playSound(player.getLocation(), Sound.HURT_FLESH, 1, 1);
			
			//Sets new HP value in the hashMap.
			healthPoints.put(playerName, (int) newHP);
			
			//Sets the players red hearts on the player bar.
			player.setHealth(hpBarPercent);
			
			//Send the player the debug message.
			player.sendMessage(ChatColor.RED + "         -" + 
					ChatColor.GRAY + hpAmount + ChatColor.BOLD + " HP: " +
					ChatColor.GRAY + ChatColor.BOLD + hpPercent + "%" +
					ChatColor.GRAY + " [" + ChatColor.RED + newHP +
					ChatColor.GRAY + " / " + ChatColor.GREEN + maxHP +
					ChatColor.GRAY + "]");
		}
	}
	
	//This performs the necessary steps to load in a player.
	//Creates a configuration file for new players and loads 
	//players statistics into the hashMaps.
	public static void setupPlayer(Player player) {
		
		String playerName = player.getName();
		String uuid = player.getUniqueId().toString();
		
		//Check to make sure the player configuration exists.
		//Player configurations are saved with the UUID (Mojang's Unique User Identifier).
        if(!new File("plugins/MPRPG/players/" + uuid + ".yml").exists()){
        	//The players file does not exist. Lets create the player file now.
        	createPlayerConfig(player);
        }
        
        //Read armor and set statistics.
        //update HashMap info
        healthPoints.put(playerName, baseHealthPoints);
        maxHealthPoints.put(playerName, baseHealthPoints);
        energyPoints.put(playerName, baseEnergyPoints);
        maxEnergyPoints.put(playerName, baseEnergyPoints);
        
        //Setup players attributes
    	dexterityMap.put(playerName, getPlayerConfigInt(player, "attribute.dexterity"));
    	intellectMap.put(playerName, getPlayerConfigInt(player, "attribute.intellect"));
    	luckMap.put(playerName, getPlayerConfigInt(player, "attribute.luck"));
    	personalityMap.put(playerName, getPlayerConfigInt(player, "attribute.personality"));
    	strengthMap.put(playerName, getPlayerConfigInt(player, "attribute.strength"));
    	vitalityMap.put(playerName, getPlayerConfigInt(player, "attribute.vitality"));
        
        //Set players health to max on the health bar.
        player.setHealth(20);
        
        //Give new players the MinePile game menu.
        PlayerMenuManager.givePlayerMenu(player);
	}
	
	//Remove players from the game. Will remove players
	//from hashMaps and saves any data if necessary.
	public static void removePlayer(Player player) {
		//Get Player's name.
		String playerName = player.getName();
		
		//remove player from HashMaps.
		healthPoints.remove(playerName);
		maxHealthPoints.remove(playerName);
		energyPoints.remove(playerName);
		maxEnergyPoints.remove(playerName);
		
		//remove player attributes from HashMaps.
		dexterityMap.remove(playerName);
    	intellectMap.remove(playerName);
    	luckMap.remove(playerName);
    	personalityMap.remove(playerName);
    	strengthMap.remove(playerName);
    	vitalityMap.remove(playerName);
	}

    public static void createPlayerConfig(Player player) {
 	
    	String uuid = player.getUniqueId().toString();
    	String playerName = player.getName();
    	
        File configFile = new File("plugins/MPRPG/players/" + uuid + ".yml");
        FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
        playerConfig.set("playerName", playerName);
        playerConfig.set("player.playerLVL", 1);
        playerConfig.set("player.playerEXP", 0);
        
        playerConfig.set("permissions.admin", 0);
        playerConfig.set("permissions.dev", 0);
        playerConfig.set("permissions.mod", 0);
        
        playerConfig.set("attribute.dexterity", 0);
        playerConfig.set("attribute.intellect", 0);
        playerConfig.set("attribute.luck", 0);
        playerConfig.set("attribute.personality", 0);
        playerConfig.set("attribute.strength", 0);
        playerConfig.set("attribute.vitality", 0);

        playerConfig.set("setting.chat.languagefilter", 1);
        playerConfig.set("setting.chat.focus", "local");
        playerConfig.set("setting.chat.lastpm", null);
        
        playerConfig.set("setting.chatchannel.admin", 1);
        playerConfig.set("setting.chatchannel.global", 1);
        playerConfig.set("setting.chatchannel.guild", 1);
        playerConfig.set("setting.chatchannel.help", 1);
        playerConfig.set("setting.chatchannel.local", 1);
        playerConfig.set("setting.chatchannel.mod", 1);
        playerConfig.set("setting.chatchannel.party", 1);
        playerConfig.set("setting.chatchannel.pm", 1);
        playerConfig.set("setting.chatchannel.trade", 1);
        
        playerConfig.set("economy.gold", 0);
        playerConfig.set("economy.silver", 0);
        playerConfig.set("economy.copper", 32);
        playerConfig.set("economy.portalCash", 0);
        playerConfig.set("economy.bankRows", 1);
        playerConfig.set("economy.shopRows", 1);

        playerConfig.set("clan.id", 0);
        playerConfig.set("clan.tag", null);
        
        playerConfig.set("profession.blackSmithingLVL", 1);
        playerConfig.set("profession.blackSmithingEXP", 0);
        playerConfig.set("profession.cookingLVL", 1);
        playerConfig.set("profession.cookingEXP", 0);

        try {
            playerConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
	public static void setPlayerConfigInt(Player player, String config, int value) {
    	
		String uuid = player.getUniqueId().toString();
    	
        File configFile = new File("plugins/MPRPG/players/" + uuid + ".yml");
        FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
        playerConfig.set(config, value);

        try {
            playerConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
	}
    
	public static int getPlayerConfigInt(Player player, String value) {
    	
		String uuid = player.getUniqueId().toString();
    	
        File configFile = new File("plugins/MPRPG/players/" + uuid + ".yml");
        FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
        return (int) playerConfig.get(value);
	}
	
	public static void setPlayerConfigString(Player player, String config, String value) {
    	
		String uuid = player.getUniqueId().toString();
    	
        File configFile = new File("plugins/MPRPG/players/" + uuid + ".yml");
        FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
        playerConfig.set(config, value);

        try {
            playerConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
	}
	
	public static String getPlayerConfigString(Player player, String value) {
    	
		String uuid = player.getUniqueId().toString();
    	
        File configFile = new File("plugins/MPRPG/players/" + uuid + ".yml");
        FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
        return  (String) playerConfig.get(value);
	}
}