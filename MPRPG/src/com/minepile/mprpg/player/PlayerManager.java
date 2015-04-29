package com.minepile.mprpg.player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import me.mgone.bossbarapi.BossbarAPI;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.equipment.LoreManager;

public class PlayerManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static PlayerManager playerManagerInstance = new PlayerManager();
	
	//MAIN STATS
	static HashMap<String, Integer> healthPoints = new HashMap<String, Integer>();
	static HashMap<String, Integer> maxHealthPoints = new HashMap<String, Integer>();
	static HashMap<String, Integer> staminaPoints = new HashMap<String, Integer>();
	static HashMap<String, Integer> maxStaminaPoints = new HashMap<String, Integer>();
	static HashMap<String, Integer> manaPoints = new HashMap<String, Integer>();
	static HashMap<String, Integer> maxManaPoints = new HashMap<String, Integer>();
	
	static HashMap<String, Integer> dexterityMap = new HashMap<String, Integer>();
	static HashMap<String, Integer> intellectMap = new HashMap<String, Integer>();
	static HashMap<String, Integer> luckMap = new HashMap<String, Integer>();
	static HashMap<String, Integer> personalityMap = new HashMap<String, Integer>();
	static HashMap<String, Integer> strengthMap = new HashMap<String, Integer>();
	static HashMap<String, Integer> vitalityMap = new HashMap<String, Integer>();
	
	//Base statistic rates
	static int baseHealthPoints = 100;
	static int baseStaminaPoints = 100;
	static int baseManaPoints = 100;
	static int baseHealthRegenRate = 1;
	static int baseStaminaRegenRate = 1;
	static int baseManaRegenRate = 1;
	
	//Base attributes
	static int dexterity = 8;
	static int intellect = 8;
	static int luck = 5;
	static int personality = 5;
	static int strength = 10;
	static int vitality = 9;
	
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
	
	public static void teleportPlayerToSpawn(Player player) {
    	//Player must be new, lets teleport them to the new player starting point.
    	player.teleport(new Location(Bukkit.getWorld("world"), 43, 78, -35));
    	
    	//Play a sound effect for the player.
    	player.playSound(player.getLocation(), Sound.AMBIENCE_CAVE, .8f, .8f);
    	
    	//Add temporary potion effects.
    	PotionEffect blind = new PotionEffect(PotionEffectType.BLINDNESS, 6*20, 10);
		PotionEffect confuse = new PotionEffect(PotionEffectType.CONFUSION, 6*20, 20);
		blind.apply(player);
		confuse.apply(player);
	}
	
	/*
	public static void updateCustomName(Player player) {
		String name = player.getName();
		int level = player.getLevel();
		player.setCustomName(ChatColor.LIGHT_PURPLE + "[" + level + "]" +
				ChatColor.WHITE + name);
		player.setCustomNameVisible(true);
	}
	*/
	
	public static void updatePlayerBossbar(Player player) {
		
		int playerlvlexp = player.getLevel();
		int playerMana = manaPoints.get(player.getName());
		int playerMaxMana = maxManaPoints.get(player.getName());
		
		String playerLevel = Integer.toString(playerlvlexp);
		String playerMaxManaString = Integer.toString(playerMaxMana);
		String playerStaminaString = Integer.toString((staminaPoints.get(player.getName()) * 100 ) / maxStaminaPoints.get(player.getName()));
		
		
		BossbarAPI.setMessage(player, ChatColor.AQUA + "" + ChatColor.BOLD + "    " +
				"LVL " + ChatColor.AQUA + playerLevel + ChatColor.DARK_GRAY + ChatColor.BOLD + 
				"  -  " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Mana " + 
				ChatColor.LIGHT_PURPLE + playerMana +  " / " + playerMaxManaString + ChatColor.DARK_GRAY + ChatColor.BOLD + 
				"  -  " + ChatColor.GREEN + ChatColor.BOLD + "Stamina " + ChatColor.GREEN + playerStaminaString + "%");
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
        	
        	//Teleport new players to the spawn location.
        	teleportPlayerToSpawn(player);
        }
        
        //Set the players level, if less than 1.
        if (player.getLevel() < 1) {
        	player.setLevel(1);
        }
        
        //Read armor and set statistics.
        //update HashMap info
        if (getPlayerConfigInt(player, "player.logoutHP") < LoreManager.getHpBonus(player)) {
        	healthPoints.put(playerName, baseHealthPoints);
        } else {
        	healthPoints.put(playerName, getPlayerConfigInt(player, "player.logoutHP"));
        }
        maxHealthPoints.put(playerName, baseHealthPoints);
        staminaPoints.put(playerName, baseStaminaPoints);
        maxStaminaPoints.put(playerName, baseStaminaPoints);
        manaPoints.put(playerName, baseManaPoints);
        maxManaPoints.put(playerName, baseManaPoints);
        
        //Setup players attributes
    	dexterityMap.put(playerName, getPlayerConfigInt(player, "attribute.dexterity"));
    	intellectMap.put(playerName, getPlayerConfigInt(player, "attribute.intellect"));
    	luckMap.put(playerName, getPlayerConfigInt(player, "attribute.luck"));
    	personalityMap.put(playerName, getPlayerConfigInt(player, "attribute.personality"));
    	strengthMap.put(playerName, getPlayerConfigInt(player, "attribute.strength"));
    	vitalityMap.put(playerName, getPlayerConfigInt(player, "attribute.vitality"));
        
        //Set players health to max on the health bar.
        //player.setMaxHealth(200);
        
        //Give new players the MinePile game menu.
        PlayerMenuManager.givePlayerMenu(player);
        
        //Monster bar at the top of the screen.
      	updatePlayerBossbar(player);
      	
      	//Feed player.
      	player.setFoodLevel(20);
      	
      	//update the players health tag
      	if (PlayerHealthTagManager.getSb() != null && PlayerHealthTagManager.getObj() != null) {
      		PlayerHealthTagManager.addPlayer(player);
      		PlayerHealthTagManager.updateHealthTag(player);
      	}
      	
	}
	
	public static void updatePlayerHashMap(Player player, String attribute, int x) {
        String playerName = player.getName();
		
		healthPoints.put(playerName, baseHealthPoints);
		
        maxHealthPoints.put(playerName, maxHealthPoints.get(playerName) + x);
		player.sendMessage("new hp: " + maxHealthPoints.get(playerName).toString());
		
        staminaPoints.put(playerName, baseStaminaPoints);
        maxStaminaPoints.put(playerName, baseStaminaPoints);
        manaPoints.put(playerName, baseManaPoints);
        maxManaPoints.put(playerName, baseManaPoints);
        
        //Setup players attributes
    	dexterityMap.put(playerName, getPlayerConfigInt(player, "attribute.dexterity"));
    	intellectMap.put(playerName, getPlayerConfigInt(player, "attribute.intellect"));
    	luckMap.put(playerName, getPlayerConfigInt(player, "attribute.luck"));
    	personalityMap.put(playerName, getPlayerConfigInt(player, "attribute.personality"));
    	strengthMap.put(playerName, getPlayerConfigInt(player, "attribute.strength"));
    	vitalityMap.put(playerName, getPlayerConfigInt(player, "attribute.vitality"));
	}
	
	public static void resetPlayerHashMap(Player player) {
        String playerName = player.getName();
		
		healthPoints.put(playerName, baseHealthPoints);
		
        maxHealthPoints.put(playerName, baseHealthPoints);
        staminaPoints.put(playerName, baseStaminaPoints);
        maxStaminaPoints.put(playerName, baseStaminaPoints);
        manaPoints.put(playerName, baseManaPoints);
        maxManaPoints.put(playerName, baseManaPoints);
        
        //Setup players attributes
    	dexterityMap.put(playerName, getPlayerConfigInt(player, "attribute.dexterity"));
    	intellectMap.put(playerName, getPlayerConfigInt(player, "attribute.intellect"));
    	luckMap.put(playerName, getPlayerConfigInt(player, "attribute.luck"));
    	personalityMap.put(playerName, getPlayerConfigInt(player, "attribute.personality"));
    	strengthMap.put(playerName, getPlayerConfigInt(player, "attribute.strength"));
    	vitalityMap.put(playerName, getPlayerConfigInt(player, "attribute.vitality"));
	}
	
	//Remove players from the game. Will remove players
	//from hashMaps and saves any data if necessary.
	public static void removePlayer(Player player) {
		//Get Player's name.
		String playerName = player.getName();
		
		//remove player from HashMaps.
		healthPoints.remove(playerName);
		maxHealthPoints.remove(playerName);
		staminaPoints.remove(playerName);
		maxStaminaPoints.remove(playerName);
		manaPoints.remove(playerName);
		maxManaPoints.remove(playerName);
		
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
        playerConfig.set("player.logoutHP", 0);
        
        playerConfig.set("permissions.admin", 0);
        playerConfig.set("permissions.dev", 0);
        playerConfig.set("permissions.mod", 0);
        
        playerConfig.set("attribute.dexterity", dexterity);
        playerConfig.set("attribute.intellect", intellect);
        playerConfig.set("attribute.luck", luck);
        playerConfig.set("attribute.personality", personality);
        playerConfig.set("attribute.strength", strength);
        playerConfig.set("attribute.vitality", vitality);

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
        playerConfig.set("economy.copper", 15);
        playerConfig.set("economy.portalCash", 0);
        playerConfig.set("economy.bankRows", 1);
        playerConfig.set("economy.shopRows", 1);

        playerConfig.set("clan.id", 0);
        playerConfig.set("clan.tag", null);

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

	public static int getHealthPoints(String playerName) {
		return healthPoints.get(playerName);
	}

	public static void setHealthPoints(String playerName, Integer healthPoints) {
		PlayerManager.healthPoints.put(playerName, healthPoints);;
	}

	public static int getMaxHealthPoints(String playerName) {
		return PlayerManager.maxHealthPoints.get(playerName);
	}

	public static void setMaxHealthPoints(String playerName, Integer maxHealthPoints) {
		PlayerManager.maxHealthPoints.put(playerName, maxHealthPoints);
	}

	public static int getStaminaPoints(String playerName) {
		return staminaPoints.get(playerName);
	}

	public static void setStaminaPoints(HashMap<String, Integer> staminaPoints) {
		PlayerManager.staminaPoints = staminaPoints;
	}

	public static int getMaxStaminaPoints(String playerName) {
		return maxStaminaPoints.get(playerName);
	}

	public static void setMaxStaminaPoints(HashMap<String, Integer> maxStaminaPoints) {
		PlayerManager.maxStaminaPoints = maxStaminaPoints;
	}

	public static int getManaPoints(String playerName) {
		return manaPoints.get(playerName);
	}

	public static void setManaPoints(HashMap<String, Integer> manaPoints) {
		PlayerManager.manaPoints = manaPoints;
	}

	public static int getMaxManaPoints(String playerName) {
		return maxManaPoints.get(playerName);
	}

	public static void setMaxManaPoints(HashMap<String, Integer> maxManaPoints) {
		PlayerManager.maxManaPoints = maxManaPoints;
	}

	public static int getBaseHealthPoints() {
		return baseHealthPoints;
	}

	public static void setBaseHealthPoints(int baseHealthPoints) {
		PlayerManager.baseHealthPoints = baseHealthPoints;
	}

	public static int getBaseStaminaPoints() {
		return baseStaminaPoints;
	}

	public static void setBaseStaminaPoints(int baseStaminaPoints) {
		PlayerManager.baseStaminaPoints = baseStaminaPoints;
	}

	public static int getBaseManaPoints() {
		return baseManaPoints;
	}

	public static void setBaseManaPoints(int baseManaPoints) {
		PlayerManager.baseManaPoints = baseManaPoints;
	}

	public static int getBaseHealthRegenRate() {
		return baseHealthRegenRate;
	}

	public static void setBaseHealthRegenRate(int baseHealthRegenRate) {
		PlayerManager.baseHealthRegenRate = baseHealthRegenRate;
	}

	public static int getBaseStaminaRegenRate() {
		return baseStaminaRegenRate;
	}

	public static void setBaseStaminaRegenRate(int baseStaminaRegenRate) {
		PlayerManager.baseStaminaRegenRate = baseStaminaRegenRate;
	}

	public static int getBaseManaRegenRate() {
		return baseManaRegenRate;
	}

	public static void setBaseManaRegenRate(int baseManaRegenRate) {
		PlayerManager.baseManaRegenRate = baseManaRegenRate;
	}

	public static int getDexterity() {
		return dexterity;
	}

	public static void setDexterity(int dexterity) {
		PlayerManager.dexterity = dexterity;
	}

	public static int getIntellect() {
		return intellect;
	}

	public static void setIntellect(int intellect) {
		PlayerManager.intellect = intellect;
	}

	public static int getLuck() {
		return luck;
	}

	public static void setLuck(int luck) {
		PlayerManager.luck = luck;
	}

	public static int getPersonality() {
		return personality;
	}

	public static void setPersonality(int personality) {
		PlayerManager.personality = personality;
	}

	public static int getStrength() {
		return strength;
	}

	public static void setStrength(int strength) {
		PlayerManager.strength = strength;
	}

	public static int getVitality() {
		return vitality;
	}

	public static void setVitality(int vitality) {
		PlayerManager.vitality = vitality;
	}

}