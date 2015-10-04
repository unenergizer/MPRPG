package com.minepile.mprpg.player;

import io.puharesource.mc.titlemanager.api.ActionbarTitleObject;
import io.puharesource.mc.titlemanager.api.TitleObject;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
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

	//MAIN STATS
	private static ConcurrentHashMap<UUID, Double> healthPoints = new ConcurrentHashMap<UUID, Double>();
	private static ConcurrentHashMap<UUID, Double> maxHealthPoints = new ConcurrentHashMap<UUID, Double>();
	private static ConcurrentHashMap<UUID, Double> staminaPoints = new ConcurrentHashMap<UUID, Double>();
	private static ConcurrentHashMap<UUID, Double> maxStaminaPoints = new ConcurrentHashMap<UUID, Double>();
	private static ConcurrentHashMap<UUID, Double> manaPoints = new ConcurrentHashMap<UUID, Double>();
	private static ConcurrentHashMap<UUID, Double> maxManaPoints = new ConcurrentHashMap<UUID, Double>();
	private static ConcurrentHashMap<UUID, Boolean> playerDead = new ConcurrentHashMap<UUID, Boolean>();

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
			if (PlayerCharacterManager.isPlayerLoaded(players)) {
				double logoutHP = getHealthPoints(players.getUniqueId());
				PlayerCharacterManager.setPlayerConfigDouble(players, "player.logoutHP", logoutHP);
			}
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
		UUID uuid = player.getUniqueId();
		double playerMaxHealth = getMaxHealthPoints(uuid);
		double healthBarPercent = (20 * hp) / playerMaxHealth;

		//Set the players health map
		setHealthPoints(uuid, hp);

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
		UUID uuid = player.getUniqueId();

		if (PlayerCharacterManager.isPlayerLoaded(player)) {
			double playerRegen = ItemLoreFactory.getInstance().getHealthPointsRegenerate(player);
			double totalRegen = baseHealthRegenRate + playerRegen;
			double playerHP = getHealthPoints(uuid);
			double playerMaxHP = getMaxHealthPoints(uuid);
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
		UUID uuid = player.getUniqueId();

		//Player is dead.
		setPlayerDead(uuid, true);

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
		double maxHp = getMaxHealthPoints(uuid);
		setHealthPoints(uuid, maxHp);
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
		UUID uuid = player.getUniqueId();

		if (PlayerCharacterManager.isPlayerLoaded(player)) {

			//Show player important attributes!
			String hp = Integer.toString((int) getHealthPoints(uuid));
			String maxHP = Integer.toString((int) getMaxHealthPoints(uuid));
			String stamina = Integer.toString((int) getStaminaPoints(uuid));
			String maxStamina = Integer.toString((int) getMaxStaminaPoints(uuid));
			String mana = Integer.toString((int) getManaPoints(uuid));
			String maxMana = Integer.toString((int) getMaxManaPoints(uuid));

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

		} else if (PlayerCharacterManager.isCharacterSelected(player) == true && PlayerCharacterManager.isClassSelected(player) == false) {
			//Please select your class!
			new ActionbarTitleObject(ChatColor.GREEN + "" + ChatColor.BOLD + "Please select a class!").send(player);

		} else {
			//Please select you character!
			new ActionbarTitleObject(ChatColor.AQUA + "" + ChatColor.BOLD + "Please select a character!").send(player);
		}
	}

	private static void startPlayerRespawn(final Player player) {
		// Create the task anonymously and schedule to run it once, after 20 ticks
		new BukkitRunnable() {

			@Override
			public void run() {
				UUID uuid = player.getUniqueId();

				//Turn flying off back to one.
				player.setAllowFlight(false);
				player.setFlying(false);

				//Teleport the player to spawn
				teleportPlayerToSpawn(player);

				//Clear potion effect.
				player.removePotionEffect(PotionEffectType.INVISIBILITY);

				//Player is no longer dead.
				setPlayerDead(uuid, false);

			}

		}.runTaskLater(plugin, 20 * 10); //7 seconds
	}

	/**
	 * Things that should happen when a player levels up.
	 * 
	 * @param player The player who has leveled up.
	 * @param level The players new level.
	 */
	public static void levelUp(Player player, int playerLevel) {

		UUID uuid = player.getUniqueId();
		if (PlayerCharacterManager.isPlayerLoaded(player)) {
			String level = Integer.toString((int) playerLevel);
			World world = player.getWorld();
			double x = player.getLocation().getBlockX();
			double y = player.getLocation().getBlockY();
			double z = player.getLocation().getBlockZ();
			double hp = getMaxHealthPoints(uuid);

			//Set the players level in their configuration.
			PlayerCharacterManager.setPlayerConfigInt(player, "player.playerLVL", playerLevel);

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
			setHealthPoints(uuid, hp);	//Sets payer HP ConcurrentHashMap
			player.setHealth(20); 				//Sets player HP bar

			//Send the player a message
			player.sendMessage(ChatColor.GREEN + "You have leveled up!"); 
			player.sendMessage(ChatColor.GREEN + "You are now level " +  ChatColor.GOLD + level + ChatColor.GREEN + ".");
			player.sendMessage(ChatColor.GREEN + "You have been healed!");
		}
	}

	/**
	 * This performs the necessary steps to load in a player.
	 * Creates a configuration file for new players and loads players statistics doubleo the ConcurrentHashMaps.
	 * 
	 * @param player The player that will be setup.
	 */
	public static void setupPlayer(Player player) {

		UUID uuid = player.getUniqueId();

		//Set the players level, if less than 1.
		if (player.getLevel() < 1) {
			player.setLevel(1);
		}

		//Read armor and set statistics.
		//update ConcurrentHashMap info
		if (PlayerCharacterManager.getPlayerConfigDouble(player, "player.logout.hp") < ItemLoreFactory.getInstance().getHealthPointsBonus(player)) {
			setHealthPoints(uuid, baseHealthPoints);
		} else {
			setHealthPoints(uuid, PlayerCharacterManager.getPlayerConfigDouble(player, "player.logout.hp"));
		}
		setMaxHealthPoints(uuid, baseHealthPoints);
		setStaminaPoints(uuid, baseStaminaPoints);
		setMaxStaminaPoints(uuid, baseStaminaPoints);
		setManaPoints(uuid, baseManaPoints);
		setMaxManaPoints(uuid, baseManaPoints);
		setPlayerDead(uuid, false);

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
		}
		
		PlayerHealthTagManager.updateHealthTag(player);

		//Give the player a Menu!
		PlayerMenuManager.createMenu(player);

		//Update the players armor.
		ItemLoreFactory.getInstance().applyHPBonus(player, false);
	}

	/**
	 * Remove players from the game. Will remove players from ConcurrentHashMaps and saves any data if necessary.
	 * 
	 * @param player The player to remove from the server.
	 */
	public static void removePlayer(Player player) {
		UUID uuid = player.getUniqueId();

		//remove player from ConcurrentHashMaps.
		healthPoints.remove(uuid);
		maxHealthPoints.remove(uuid);
		staminaPoints.remove(uuid);
		maxStaminaPoints.remove(uuid);
		manaPoints.remove(uuid);
		maxManaPoints.remove(uuid);
		playerDead.remove(uuid);

		//Remove players game menu
		PlayerMenuManager.deleteMenu(player);
	}

	public static double getHealthPoints(UUID uuid) {
		return healthPoints.get(uuid);
	}

	private static void setHealthPoints(UUID uuid, double newHealthTotal) {
		healthPoints.put(uuid, newHealthTotal);
	}

	public static double getMaxHealthPoints(UUID uuid) {
		return maxHealthPoints.get(uuid);
	}

	public static void setMaxHealthPoints(UUID uuid, double maxhp) {
		maxHealthPoints.put(uuid, maxhp);
	}

	public static double getStaminaPoints(UUID uuid) {
		return staminaPoints.get(uuid);
	}

	public static void setStaminaPoints(UUID uuid, double stamina) {
		staminaPoints.put(uuid, stamina);
	}

	public static double getMaxStaminaPoints(UUID uuid) {
		return maxStaminaPoints.get(uuid);
	}

	public static void setMaxStaminaPoints(UUID uuid, double maxStamina) {
		maxStaminaPoints.put(uuid, maxStamina);
	}

	public static double getManaPoints(UUID uuid) {
		return manaPoints.get(uuid);
	}

	public static void setManaPoints(UUID uuid, double mana) {
		manaPoints.put(uuid, mana);
	}

	public static double getMaxManaPoints(UUID uuid) {
		return maxManaPoints.get(uuid);
	}

	public static void setMaxManaPoints(UUID uuid, double maxMana) {
		maxManaPoints.put(uuid, maxMana);
	}

	public static boolean isPlayerDead(UUID uuid) {
		return playerDead.get(uuid);
	}

	public static void setPlayerDead(UUID uuid, boolean dead) {
		playerDead.put(uuid, dead);
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