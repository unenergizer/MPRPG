package com.minepile.mprpg.player;

import io.puharesource.mc.titlemanager.api.TitleObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import me.mgone.bossbarapi.BossbarAPI;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.items.LoreManager;

public class PlayerManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static PlayerManager playerManagerInstance = new PlayerManager();
	static String playerFilePathStart = "plugins/MPRPG/players/";
	static String playerFilePathEnd = ".yml";
	
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
	
	/**
	 * This will disable this class.
	 */
	public static void disable() {
		for (Player players : Bukkit.getOnlinePlayers()) {
			int logoutHP = PlayerManager.getHealthPoints(players.getName());
			setPlayerConfigInt(players, "player.logoutHP", logoutHP);
		}
	}
	
	/**
	 * This will teleport the specified player to the main player Spawn location.
	 * 
	 * @param player The player that will be teleported.
	 */
	public static void teleportPlayerToSpawn(Player player) {
    	//Player must be new, lets teleport them to the new player starting point.
    	player.teleport(new Location(Bukkit.getWorld("world"), 43.5, 79, -35.5));
    	
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
	
	/**
	 * Things that should happen when a player levels up.
	 * 
	 * @param player The player who has leveled up.
	 * @param level The players new level.
	 */
	public static void levelUp(Player player, int playerLevel) {
		
		String playerName = player.getName();
		String level = Integer.toString(playerLevel);
		World world = player.getWorld();
		int x = player.getLocation().getBlockX();
		int y = player.getLocation().getBlockY();
		int z = player.getLocation().getBlockZ();
		int hp = maxHealthPoints.get(playerName);
		
		//Set the players level in their configuration.
		setPlayerConfigInt(player, "player.playerLVL", playerLevel);
		
		//Show level up message.
		new TitleObject(ChatColor.GREEN + "Leveled UP!", ChatColor.GOLD + "You are now level " + level).send(player);
		
		//Show level up effects.
		Location loc = new Location(world, x, y - 1, z); //Firework spawn location

		for (int i = 0; i < 2; i++) {
			Firework fw = (Firework) world.spawn(loc, Firework.class);
			FireworkMeta fm = fw.getFireworkMeta();
			fm.addEffect(FireworkEffect.builder()
					.flicker(false)
					.trail(false)
					.with(Type.STAR)
					.withColor(Color.YELLOW)
					.withFade(Color.YELLOW)
					.build());
			fw.setFireworkMeta(fm);
		}
		
		//Heal the player
		healthPoints.put(playerName, hp);	//Sets payer HP hashmap
		player.setHealth(20); 				//Sets player HP bar
		
		//Send the player a message
		player.sendMessage(ChatColor.GREEN + "You have leveled up!"); 
		player.sendMessage(ChatColor.GREEN + "You are now level " +  ChatColor.GOLD + level + ChatColor.GREEN + ".");
		player.sendMessage(ChatColor.GREEN + "You have been healed!");
	}
	
	/**
	 * This will update the players UI bar at the top of the screen.
	 * 
	 * @param player The player who's UI will be updated.
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
	
	/**
	 * This performs the necessary steps to load in a player.
	 * Creates a configuration file for new players and loads players statistics into the hashMaps.
	 * 
	 * @param player The player that will be setup.
	 */
	public static void setupPlayer(Player player) {
		
		String playerName = player.getName();
		String uuid = player.getUniqueId().toString();
		
		//Check to make sure the player configuration exists.
		//Player configurations are saved with the UUID (Mojang's Unique User Identifier).
        if(!new File(playerFilePathStart + uuid + playerFilePathEnd).exists()){
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
      	
      	//Give the player a Menu!
      	PlayerMenuManager.createMenu(player);
      	
	}
	
	/**
	 * This will update the players HashMap's to contain new important attribute information.
	 * 
	 * @param player The player to update.
	 * @param attribute The attribute to update.
	 * @param value The new attribute value.
	 */
	public static void updatePlayerHashMap(Player player, String attribute, int value) {
        String playerName = player.getName();
		
		healthPoints.put(playerName, baseHealthPoints);
		
        maxHealthPoints.put(playerName, maxHealthPoints.get(playerName) + value);
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
	
	/**
	 * This will reset the players important HashMap attributes.
	 * <p>
	 * This is mainly used for server reloads.
	 * 
	 * @param player The player to reset.
	 */
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
	
	/**
	 * Remove players from the game. Will remove players from hashMaps and saves any data if necessary.
	 * 
	 * @param player The player to remove from the server.
	 */
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
    	
    	//Remove players game menu
    	PlayerMenuManager.deleteMenu(player);
	}
	
	/**
	 * Creates a new configuration file on a players first visit to the server.
	 */
    public static void createPlayerConfig(Player player) {
 	
    	String uuid = player.getUniqueId().toString();
    	String playerName = player.getName();
    	
        File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
        FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
        playerConfig.set("playerName", playerName);
        playerConfig.set("player.playerLVL", 1);
        playerConfig.set("player.playerEXP", 0);
        playerConfig.set("player.logoutHP", baseHealthPoints);
        
        playerConfig.set("gang.owner", false);
        playerConfig.set("gang.name", null);
        
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

        playerConfig.set("setting.chat.healthDebug", true);
        playerConfig.set("setting.chat.monsterDebug", true);
        playerConfig.set("setting.chat.professionDebug", true);
        
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

        try {
            playerConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
	public static void setPlayerConfigInt(Player player, String config, int value) {
    	
		String uuid = player.getUniqueId().toString();
    	
        File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
        FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
        playerConfig.set(config, value);

        try {
            playerConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
	}
    
	public static boolean getPlayerConfigBoolean(Player player, String value) {
    	
		String uuid = player.getUniqueId().toString();
    	
        File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
        FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
        return playerConfig.getBoolean(value);
	}
	
	public static void setPlayerConfigBoolean(Player player, String config, boolean value) {
    	
		String uuid = player.getUniqueId().toString();
    	
        File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
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
    	
        File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
        FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
        return (int) playerConfig.get(value);
	}
	
	public static void setPlayerConfigString(Player player, String config, String value) {
    	
		String uuid = player.getUniqueId().toString();
    	
        File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
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
    	
        File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
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