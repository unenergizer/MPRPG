package com.minepile.mprpg.player;

import io.puharesource.mc.titlemanager.api.ActionbarTitleObject;
import io.puharesource.mc.titlemanager.api.TabTitleObject;
import io.puharesource.mc.titlemanager.api.TitleObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.items.ItemLoreFactory;

public class PlayerManager {

	//setup instance variables
	public static MPRPG plugin;
	public static PlayerManager playerManagerInstance = new PlayerManager();
	private static String playerFilePathStart = "plugins/MPRPG/players/";
	private static String playerFilePathEnd = ".yml";

	//MAIN STATS
	private static ConcurrentHashMap<String, Double> healthPoints = new ConcurrentHashMap<String, Double>();
	private static ConcurrentHashMap<String, Double> maxHealthPoints = new ConcurrentHashMap<String, Double>();
	private static ConcurrentHashMap<String, Double> staminaPoints = new ConcurrentHashMap<String, Double>();
	private static ConcurrentHashMap<String, Double> maxStaminaPoints = new ConcurrentHashMap<String, Double>();
	private static ConcurrentHashMap<String, Double> manaPoints = new ConcurrentHashMap<String, Double>();
	private static ConcurrentHashMap<String, Double> maxManaPoints = new ConcurrentHashMap<String, Double>();
	private static ConcurrentHashMap<String, Boolean> playerDead = new ConcurrentHashMap<String, Boolean>();

	//Base statistic rates
	private static double baseHealthPoints = 100;
	private static double baseHealthRegenRate = .5;
	private static double baseStaminaPoints = 100;
	private static double baseManaPoints = 100;
	private static double baseStaminaRegenRate = 1;
	private static double baseManaRegenRate = 1;

	//Create instance
	public static PlayerManager getInstance() {
		return playerManagerInstance;
	}

	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		//If the server reloads, lets remove all players from the ConcurrentHashMaps.
		//We will add them back after this step.
		for (Player players : Bukkit.getOnlinePlayers()) {
			if (!players.hasMetadata("NPC")) {
				removePlayer(players);
			}
		}

		//If the server reloads, then setup all the players again.
		for (Player players : Bukkit.getOnlinePlayers()) {
			if (!players.hasMetadata("NPC")) {
				setupPlayer(players);
			}
		}

		//Starts the thread that will refresh the action bar for the user, so they can see
		//various stats above their health and hunger bar.
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin,  new Runnable() {
			public void run() {
				for (Player players : Bukkit.getOnlinePlayers()) {
					if (!players.hasMetadata("NPC")) {
						displayActionBar(players);
					}
				}
			}
		}, 0L, 2 * 20);

		//Starts a thread that will regen a players health every few seconds.
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin,  new Runnable() {
			public void run() {
				for (Player players : Bukkit.getOnlinePlayers()) {
					if (!players.hasMetadata("NPC")) {
						regenerateHealthPoints(players);
					}
				}
			}
		}, 0L, 2 * 20);
	}

	/**
	 * This will disable this class.
	 */
	public static void disable() {
		for (Player players : Bukkit.getOnlinePlayers()) {
			double logoutHP = PlayerManager.getHealthPoints(players.getName());
			setPlayerConfigInt(players, "player.logoutHP", logoutHP);
		}
	}

	/**
	 * This will teleport the specified player to the main player Spawn location.
	 * 
	 * @param player The player that will be teleported.
	 */
	public static void teleportPlayerToSpawn(Player player) {
		//Player must be new, lets teleport them to the new player starting podouble.
		player.teleport(new Location(Bukkit.getWorld("world"), 43.5, 79, -35.5));

		//Play a sound effect for the player.
		player.playSound(player.getLocation(), Sound.AMBIENCE_CAVE, .8f, .8f);

		//Add temporary potion effects.
		PotionEffect blind = new PotionEffect(PotionEffectType.BLINDNESS, 6*20, 10);
		PotionEffect confuse = new PotionEffect(PotionEffectType.CONFUSION, 6*20, 20);
		blind.apply(player);
		confuse.apply(player);
	}

	/**
	 * Sets the players health.  This will update all things health related.
	 * 
	 * @param player
	 * @param playerHitPointsFinal
	 */
	public static void setPlayerHitPoints(Player player, double hp) {
		String playerName = player.getName();
		double playerMaxHealth = maxHealthPoints.get(playerName);
		double healthBarPercent = (20 * hp) / playerMaxHealth;

		//Set the players health map
		setHealthPoints(player.getName(), hp);

		//Set players health tag under their name
		PlayerHealthTagManager.updateHealthTag(player);

		//Set hearts
		if (healthBarPercent <= 3) {
			player.setHealth(healthBarPercent + 2);
		} else {
			player.setHealth(healthBarPercent);
		}
	}

	/**
	 * Regenerates a players HitPoints every few seconds.
	 * 
	 * @param player The player who will have their HP regenerated.
	 */
	public static void regenerateHealthPoints(Player player) {
		if (healthPoints.get(player.getName()) != null) {
			String playerName = player.getName();
			double playerRegen = ItemLoreFactory.getInstance().getHealthPointsRegenerate(player);
			double totalRegen = baseHealthRegenRate + playerRegen;
			double playerHP = healthPoints.get(playerName);
			double playerMaxHP = maxHealthPoints.get(playerName);
			double newHP = playerHP + totalRegen;

			//Set the players HP HashMap values.
			if (newHP >= playerMaxHP) {
				setPlayerHitPoints(player, playerMaxHP);
			} else {
				setPlayerHitPoints(player, newHP);
			}

			//Update the players health tag under their name.
			PlayerHealthTagManager.updateHealthTag(player);
		}
	}

	/**
	 * This is what happens when a player has lost all its HP.
	 * 
	 * @param player The player entity that needs to be killed.
	 */
	public static void killPlayer(final Player player) {
		String playerName = player.getName();

		//Player is dead.
		playerDead.put(playerName, true);
		
		//This will respawn the player after a certain amount of time.
		startPlayerRespawn(player);

		//Drop the players items.
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,  new Runnable() {
			public void run() {
				for (ItemStack itemStack : player.getInventory()) {
					if (itemStack != null) {
						player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
						player.getInventory().remove(itemStack);
					}
				}
			}
		}, 5L);

		//If the player dies on fire. Stop him from burning.	
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,  new Runnable() {
			public void run() {
				player.setFireTicks(0);
			}
		}, 1L);

		//Turn the player invisible.
		PotionEffect invisible = new PotionEffect(PotionEffectType.INVISIBILITY, 6*20, 10);
		invisible.apply(player);

		//Teleport player into the air.
		double x = player.getLocation().getX();
		double y = player.getLocation().getY();
		double z = player.getLocation().getZ();

		player.teleport(new Location(Bukkit.getWorld("world"), x, y + 5, z));

		//Temporarily allow the player to fly.
		player.setAllowFlight(true);
		player.setFlying(true);

		//Play death sound.
		player.playSound(player.getLocation(), Sound.VILLAGER_DEATH, .5F, 1F);

		//Show death message.
		new TitleObject(ChatColor.RED + "You have died!", ChatColor.YELLOW + "You are now being respawned.").send(player);

		//Update the players armor.
		ItemLoreFactory.getInstance().applyHPBonus(player, true);

		//Heal the player.
		double maxHp = maxHealthPoints.get(playerName);
		healthPoints.put(playerName, maxHp);
		player.setHealth(20);
		player.setFoodLevel(20);
		
		//Send the player a message
		player.sendMessage(ChatColor.RED + "You have died!");
		player.sendMessage(ChatColor.YELLOW + "Your armor has been damaged!");
		player.sendMessage(ChatColor.GREEN + "You have been healed!");
	}

	/**
	 * This will add an action bar above the players HP area to display various stats
	 * to the player.  Currently it displays hp, stamina, and mana.
	 * 
	 * @param player The player to add a stats action/display bar too.
	 */
	public static void displayActionBar(Player player) {
		if (healthPoints.get(player.getName()) != null) {
			String playerName = player.getName();
			String hp = Integer.toString(healthPoints.get(playerName).intValue());
			String maxHP = Integer.toString(maxHealthPoints.get(playerName).intValue());
			String stamina = Integer.toString(staminaPoints.get(playerName).intValue());
			String maxStamina = Integer.toString(maxStaminaPoints.get(playerName).intValue());
			String mana = Integer.toString(manaPoints.get(playerName).intValue());
			String maxMana = Integer.toString(maxManaPoints.get(playerName).intValue());

			new ActionbarTitleObject(ChatColor.GREEN + "" + ChatColor.BOLD + "HP" 
					+ ChatColor.GRAY + ChatColor.BOLD + ": " 
					+ ChatColor.WHITE + ChatColor.BOLD + hp 
					+ ChatColor.GREEN + ChatColor.BOLD + "/" 
					+ ChatColor.WHITE + ChatColor.BOLD + maxHP 
					+ ChatColor.AQUA + ChatColor.BOLD + "   Stamina" 
					+ ChatColor.GRAY + ChatColor.BOLD + ": "
					+ ChatColor.WHITE + ChatColor.BOLD + stamina 
					+ ChatColor.AQUA + ChatColor.BOLD + "/" 
					+ ChatColor.WHITE + ChatColor.BOLD + maxStamina
					+ ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "   Mana" 
					+ ChatColor.GRAY + ChatColor.BOLD + ": " 
					+ ChatColor.WHITE + ChatColor.BOLD + mana 
					+ ChatColor.LIGHT_PURPLE + ChatColor.BOLD +  "/" 
					+ ChatColor.WHITE + ChatColor.BOLD + maxMana).send(player);
		}
	}

	private static void startPlayerRespawn(final Player player) {
		// Create the task anonymously and schedule to run it once, after 20 ticks
		new BukkitRunnable() {

			@Override
			public void run() {
				//Turn flying off back to one.
				player.setAllowFlight(false);
				player.setFlying(false);

				//Teleport the player to spawn
				teleportPlayerToSpawn(player);
				
				//Clear potion effect.
				player.removePotionEffect(PotionEffectType.INVISIBILITY);
				
				//Player is no longer dead.
				playerDead.put(player.getName(), false);

			}

		}.runTaskLater(plugin, 20 * 10); //7 seconds
	}

	/**
	 * Things that should happen when a player levels up.
	 * 
	 * @param player The player who has leveled up.
	 * @param level The players new level.
	 */
	public static void levelUp(Player player, double playerLevel) {

		String playerName = player.getName();
		String level = Integer.toString((int) playerLevel);
		World world = player.getWorld();
		double x = player.getLocation().getBlockX();
		double y = player.getLocation().getBlockY();
		double z = player.getLocation().getBlockZ();
		double hp = maxHealthPoints.get(playerName);

		//Set the players level in their configuration.
		setPlayerConfigInt(player, "player.playerLVL", playerLevel);

		//Show level up message.
		new TitleObject(ChatColor.GREEN + "Leveled UP!", ChatColor.GOLD + "You are now level " + level).send(player);

		//Show level up effects.
		Location loc = new Location(world, x, y - 1, z); //Firework spawn location

		for (double i = 0; i < 2; i++) {
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
		healthPoints.put(playerName, hp);	//Sets payer HP ConcurrentHashMap
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
	/*
	public static void updatePlayerBossbar(Player player) {

		double playerlvlexp = player.getLevel();
		double playerMana = manaPoints.get(player.getName());
		double playerMaxMana = maxManaPoints.get(player.getName());

		String playerLevel = Double.toString(playerlvlexp);
		String playerMaxManaString = Double.toString(playerMaxMana);
		String playerStaminaString = Double.toString((staminaPoints.get(player.getName()) * 100 ) / maxStaminaPoints.get(player.getName()));


		BossbarAPI.setMessage(player, ChatColor.AQUA + "" + ChatColor.BOLD + "    " +
				"LVL " + ChatColor.AQUA + playerLevel + ChatColor.DARK_GRAY + ChatColor.BOLD + 
				"  -  " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Mana " + 
				ChatColor.LIGHT_PURPLE + playerMana +  " / " + playerMaxManaString + ChatColor.DARK_GRAY + ChatColor.BOLD + 
				"  -  " + ChatColor.GREEN + ChatColor.BOLD + "Stamina " + ChatColor.GREEN + playerStaminaString + "%");
	}*/

	/**
	 * This performs the necessary steps to load in a player.
	 * Creates a configuration file for new players and loads players statistics doubleo the ConcurrentHashMaps.
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
		//update ConcurrentHashMap info
		if (getPlayerConfigInt(player, "player.logoutHP") < ItemLoreFactory.getInstance().getHealthPointsBonus(player)) {
			healthPoints.put(playerName, baseHealthPoints);
		} else {
			healthPoints.put(playerName, getPlayerConfigInt(player, "player.logoutHP"));
		}
		maxHealthPoints.put(playerName, baseHealthPoints);
		staminaPoints.put(playerName, baseStaminaPoints);
		maxStaminaPoints.put(playerName, baseStaminaPoints);
		manaPoints.put(playerName, baseManaPoints);
		maxManaPoints.put(playerName, baseManaPoints);
		playerDead.put(playerName, false);

		//Set players health to max on the health bar.
		//player.setMaxHealth(200);

		//Give new players the MinePile game menu.
		PlayerMenuManager.givePlayerMenu(player);

		//Monster bar at the top of the screen.
		//updatePlayerBossbar(player);

		//Feed player.
		player.setFoodLevel(20);

		//update the players health tag
		if (PlayerHealthTagManager.getSb() != null && PlayerHealthTagManager.getObj() != null) {
			PlayerHealthTagManager.addPlayer(player);
			PlayerHealthTagManager.updateHealthTag(player);
		}

		//Give the player a Menu!
		PlayerMenuManager.createMenu(player);

		//Set player tab menu text
		String header = ChatColor.YELLOW + "" + ChatColor.BOLD + "MinePile" 
				+ ChatColor.WHITE + ChatColor.BOLD + ": RPGMMO " 
				+ plugin.getPluginVersion();
		String footer = ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "www.MinePile.com";
		new TabTitleObject(header, footer).send(player);

	}

	/**
	 * Remove players from the game. Will remove players from ConcurrentHashMaps and saves any data if necessary.
	 * 
	 * @param player The player to remove from the server.
	 */
	public static void removePlayer(Player player) {
		//Get Player's name.
		String playerName = player.getName();

		//remove player from ConcurrentHashMaps.
		healthPoints.remove(playerName);
		maxHealthPoints.remove(playerName);
		staminaPoints.remove(playerName);
		maxStaminaPoints.remove(playerName);
		manaPoints.remove(playerName);
		maxManaPoints.remove(playerName);
		playerDead.remove(playerName);

		//Remove players game menu
		PlayerMenuManager.deleteMenu(player);
	}

	/**
	 * Creates a new configuration file on a players first visit to the server.
	 */
	private static void createPlayerConfig(Player player) {

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

	public static void setPlayerConfigInt(Player player, String config, double value) {

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

	public static double getPlayerConfigInt(Player player, String value) {

		String uuid = player.getUniqueId().toString();

		File configFile = new File(playerFilePathStart + uuid + playerFilePathEnd);
		FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
		return playerConfig.getDouble(value);
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

	public static double getHealthPoints(String playerName) {
		return healthPoints.get(playerName);
	}

	private static void setHealthPoints(String playerName, double newHealthTotal) {
		healthPoints.put(playerName, newHealthTotal);
	}

	public static double getMaxHealthPoints(String playerName) {
		return maxHealthPoints.get(playerName);
	}

	public static void setMaxHealthPoints(String playerName, double maxhp) {
		maxHealthPoints.put(playerName, maxhp);
	}

	public static double getStaminaPoints(String playerName) {
		return staminaPoints.get(playerName);
	}

	public static void setStaminaPoints(ConcurrentHashMap<String, Double> stamina) {
		staminaPoints = stamina;
	}

	public static double getMaxStaminaPoints(String playerName) {
		return maxStaminaPoints.get(playerName);
	}

	public static void setMaxStaminaPoints(ConcurrentHashMap<String, Double> maxStamina) {
		maxStaminaPoints = maxStamina;
	}

	public static double getManaPoints(String playerName) {
		return manaPoints.get(playerName);
	}

	public static void setManaPoints(ConcurrentHashMap<String, Double> mana) {
		manaPoints = mana;
	}

	public static double getMaxManaPoints(String playerName) {
		return maxManaPoints.get(playerName);
	}

	public static void setMaxManaPoints(ConcurrentHashMap<String, Double> maxMana) {
		maxManaPoints = maxMana;
	}

	public static boolean isPlayerDead(String playerName) {
		return playerDead.get(playerName);
	}

	public static void setPlayerDead(ConcurrentHashMap<String, Boolean> dead) {
		playerDead = dead;
	}

	public static double getBaseHealthPoints() {
		return baseHealthPoints;
	}

	public static void setBaseHealthPoints(double baseHealth) {
		baseHealthPoints = baseHealth;
	}

	public static double getBaseStaminaPoints() {
		return baseStaminaPoints;
	}

	public static void setBaseStaminaPoints(double baseStamina) {
		baseStaminaPoints = baseStamina;
	}

	public static double getBaseManaPoints() {
		return baseManaPoints;
	}

	public static void setBaseManaPoints(double baseMana) {
		baseManaPoints = baseMana;
	}

	public static double getBaseHealthRegenRate() {
		return baseHealthRegenRate;
	}

	public static void setBaseHealthRegenRate(double baseHealthRegen) {
		baseHealthRegenRate = baseHealthRegen;
	}

	public static double getBaseStaminaRegenRate() {
		return baseStaminaRegenRate;
	}

	public static void setBaseStaminaRegenRate(double baseStaminaRegen) {
		baseStaminaRegenRate = baseStaminaRegen;
	}

	public static double getBaseManaRegenRate() {
		return baseManaRegenRate;
	}

	public static void setBaseManaRegenRate(double baseManaRegen) {
		baseManaRegenRate = baseManaRegen;
	}
}