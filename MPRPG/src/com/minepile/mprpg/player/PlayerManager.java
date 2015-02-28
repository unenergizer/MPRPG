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
	static int baseHealthPoints = 20;
	static int baseStaminaPoints = 20;
	static int baseManaPoints = 20;
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
	
	//Sets a players health points.  
	//If heal == true, then the player is being healed.
	//If heal == false, the player is being damaged.
	public static void setPlayerHealthPoints(Player player, double hpAmount, boolean heal) {
		String playerName = player.getName();
		int currentHP = healthPoints.get(playerName);
		
		//If heal == true, then we are healing the player and need to add to the players health.
		if (heal == true) { //Player is being healed.
			double newHP = currentHP + hpAmount;
			int maxHP = maxHealthPoints.get(playerName);
			int hpBarPercent = (int) ((200 * newHP) / maxHP);
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
					player.setHealth(200);
					
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
			int hpBarPercent = (int) ((200 * newHP) / maxHP);
			int hpPercent = (int) ((100 * newHP) / maxHP);
			
			//Play a sound for the player.  This is necessary
			//because we canceled the event, which cancels the noise
			//and damage sent to the player.
			player.playSound(player.getLocation(), Sound.HURT_FLESH, 1, 1);
			
			//Sets new HP value in the hashMap.
			healthPoints.put(playerName, (int) newHP);
			manaPoints.put(playerName, manaPoints.get(playerName) - 1);
			
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
        	
        	//Teleport new players to the spawn location.
        	teleportPlayerToSpawn(player);
        }
        
        //Read armor and set statistics.
        //update HashMap info
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
        
        //Set players health to max on the health bar.
        //player.setMaxHealth(200);
        
        //Give new players the MinePile game menu.
        PlayerMenuManager.givePlayerMenu(player);
        
        //Monster bar at the top of the screen.
      	updatePlayerBossbar(player);
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

	public static HashMap<String, Integer> getHealthPoints() {
		return healthPoints;
	}

	public static void setHealthPoints(HashMap<String, Integer> healthPoints) {
		PlayerManager.healthPoints = healthPoints;
	}

	public static HashMap<String, Integer> getMaxHealthPoints() {
		return maxHealthPoints;
	}

	public static void setMaxHealthPoints(HashMap<String, Integer> maxHealthPoints) {
		PlayerManager.maxHealthPoints = maxHealthPoints;
	}

	public static HashMap<String, Integer> getStaminaPoints() {
		return staminaPoints;
	}

	public static void setStaminaPoints(HashMap<String, Integer> staminaPoints) {
		PlayerManager.staminaPoints = staminaPoints;
	}

	public static HashMap<String, Integer> getMaxStaminaPoints() {
		return maxStaminaPoints;
	}

	public static void setMaxStaminaPoints(HashMap<String, Integer> maxStaminaPoints) {
		PlayerManager.maxStaminaPoints = maxStaminaPoints;
	}

	public static HashMap<String, Integer> getManaPoints() {
		return manaPoints;
	}

	public static void setManaPoints(HashMap<String, Integer> manaPoints) {
		PlayerManager.manaPoints = manaPoints;
	}

	public static HashMap<String, Integer> getMaxManaPoints() {
		return maxManaPoints;
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