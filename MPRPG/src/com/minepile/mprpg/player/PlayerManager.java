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
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.gui.PlayerMenuManager;
import com.minepile.mprpg.inventory.BankChestManager;
import com.minepile.mprpg.inventory.InventoryRestore;
import com.minepile.mprpg.items.ItemQualityManager.ItemQuality;
import com.minepile.mprpg.items.ItemTierManager.ItemTier;
import com.minepile.mprpg.items.RandomItemFactory;

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

	//Flashing action bar message
	private static int classColor = 1;

	/**
	 * Base Stats:
	 * 	Strength
	 * 	Agility
	 * 	Stamina
	 * 	Intellect
	 * 	Spirit
	 * 	Armor
	 * 	Damage
	 * 
	 *  Reflect
	 *  Block
	 *  Dodge
	 * 	Critical Damage
	 *  Critical Chance
	 * 
	 * Magic Damage:
	 * 	Fire Damage
	 * 	Ice Damage
	 * 	Lightning Damage
	 * 	Poison Damage
	 * 	Paralyze Damage
	 * 	Blindness Damage
	 * 
	 * Resistances:
	 * 	Fire Resistance
	 * 	Ice Resistance
	 * 	Lightning Resistance
	 * 	Poison Resistance
	 * 	Paralyze Resistance
	 *  Blindness Resistance
	 *  
	 * Extras:
	 * 	Waterbreathing
	 * 	Personality
	 *  Gold Find
	 *  Magic Find
	 *  Knockback
	 *  Lifesteal
	 */

	//Create instance
	public static PlayerManager getInstance() {
		return playerManagerInstance;
	}

	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		//Starts the thread that will refresh the action bar for the user, so they can see
		//various stats above their health and hunger bar.
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin,  new Runnable() {
			public void run() {
				for (Player players : Bukkit.getOnlinePlayers()) {
					if (!players.hasMetadata("NPC")) {
						displayActionBar(players);
					}
				}
				
				//This will rotate colors for the action bar messages.
				if (getColor() == 1) {
					setColor(2);
				} else if (getColor() == 2) {
					setColor(3);
				} else if (getColor() == 3) {
					setColor(4);
				} else if (getColor() == 4) {
					setColor(1);
				}
			}
		}, 0L, 20);

		//Starts a thread that will regen a players health every few seconds.
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin,  new Runnable() {
			public void run() {
				for (Player players : Bukkit.getOnlinePlayers()) {
					if (!players.hasMetadata("NPC")) {
						regenerateStatPoints(players);
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
				if (!players.hasMetadata("NPC")) {
					//If the server reloads, lets remove all players from the ConcurrentHashMaps.
					removePlayer(players);
				}
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
	 * This will teleport the specified player to the last logout location.
	 * 
	 * @param player The player that will be teleported.
	 */
	public static void teleportPlayerToLocation(Player player, Location loc) {
		//Player must be new, lets teleport them to the new player starting podouble.
		player.teleport(loc);

		//Play a sound effect for the player.
		player.playSound(player.getLocation(), Sound.AMBIENCE_CAVE, .8f, .8f);

		//Add temporary potion effects.
		PotionEffect blind = new PotionEffect(PotionEffectType.BLINDNESS, 6*20, 10);
		PotionEffect confuse = new PotionEffect(PotionEffectType.CONFUSION, 6*20, 20);
		blind.apply(player);
		confuse.apply(player);
	}

	/**
	 * Regenerates a players stat points every few seconds.
	 * This includes hit points, mana points, and stamina.
	 * 
	 * @param player The player who will have their HP regenerated.
	 */
	public static void regenerateStatPoints(Player player) {
		if (PlayerCharacterManager.isPlayerLoaded(player)) {

			UUID uuid = player.getUniqueId();
			double currentHP = PlayerManager.getHealthPoints(uuid);
			double currentSP = PlayerManager.getStaminaPoints(uuid);
			double currentMP = PlayerManager.getManaPoints(uuid);

			double maxHealthPoints = PlayerManager.getMaxHealthPoints(uuid);
			double maxStaminaPoints = PlayerManager.getMaxStaminaPoints(uuid);
			double maxManaPoints = PlayerManager.getMaxManaPoints(uuid);

			double healthRegen = PlayerAttributesManager.getHealthPointRegeneration(player);
			double staminaRegen = PlayerAttributesManager.getStaminaPointRegeneration(player);
			double manaRegen = PlayerAttributesManager.getManaPointRegeneration(player);

			double totalHealthRegen = maxHealthPoints * healthRegen;
			double totalStaminaRegen = maxStaminaPoints * staminaRegen;
			double totalManaRegen = maxManaPoints * manaRegen;


			double healthPointsFinal = currentHP + totalHealthRegen;
			double staminaPointsFinal = currentSP + totalStaminaRegen;
			double manaPointsFinal = currentMP + totalManaRegen;

			//Set the players HP HashMap values.
			if (healthPointsFinal >= maxHealthPoints) {
				setHealthPoints(player, maxHealthPoints);
			} else {
				setHealthPoints(player, healthPointsFinal);
			}

			//Set the players SP HashMap values.
			if (staminaPointsFinal >= maxStaminaPoints) {
				setStaminaPoints(player, maxStaminaPoints);
			} else {
				setStaminaPoints(player, staminaPointsFinal);
			}

			//Set the players MP HashMap values.
			if (staminaPointsFinal >= maxStaminaPoints) {
				setManaPoints(uuid, maxManaPoints);
			} else {
				setManaPoints(uuid, manaPointsFinal);
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
		PlayerAttributesManager.applyNewAttributes(player, false);

		//Heal the player.
		double maxHp = getMaxHealthPoints(uuid);
		setHealthPoints(player, maxHp);
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
			double hp = getHealthPoints(uuid);
			double maxHP = PlayerAttributesManager.getMaxHealthPoints(player);
			double stamina = getStaminaPoints(uuid);
			double maxStamina = PlayerAttributesManager.getMaxStaminaPoints(player);
			double mana = getManaPoints(uuid);
			double maxMana = PlayerAttributesManager.getMaxManaPoints(player);
			
			String hpString = Integer.toString((int) hp);
			String maxHPString = Integer.toString((int) maxHP);
			String staminaString = Integer.toString((int) stamina);
			String maxStaminaString = Integer.toString((int) maxStamina);
			String manaString = Integer.toString((int) mana);
			String maxManaString = Integer.toString((int) maxMana);

			new ActionbarTitleObject(ChatColor.GREEN + "" + ChatColor.BOLD + "HP" 
					+ ChatColor.GRAY + ChatColor.BOLD + ": " 
					+ ChatColor.WHITE + ChatColor.BOLD + hpString 
					+ ChatColor.GREEN + ChatColor.BOLD + "/" 
					+ ChatColor.WHITE + ChatColor.BOLD + maxHPString 
					+ ChatColor.AQUA + ChatColor.BOLD + "   Stamina" 
					+ ChatColor.GRAY + ChatColor.BOLD + ": "
					+ ChatColor.WHITE + ChatColor.BOLD + staminaString 
					+ ChatColor.AQUA + ChatColor.BOLD + "/" 
					+ ChatColor.WHITE + ChatColor.BOLD + maxStaminaString
					+ ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "   Mana" 
					+ ChatColor.GRAY + ChatColor.BOLD + ": " 
					+ ChatColor.WHITE + ChatColor.BOLD + manaString 
					+ ChatColor.LIGHT_PURPLE + ChatColor.BOLD +  "/" 
					+ ChatColor.WHITE + ChatColor.BOLD + maxManaString).send(player);
			
			if (hp > maxHP || stamina > maxStamina || mana > maxMana) {
				PlayerAttributesManager.applyNewAttributes(player, false);
			}
			
		} else if (PlayerCharacterManager.isCharacterSelected(player) == true && PlayerCharacterManager.isClassSelected(player) == false) {
			//Please select your class!
			String message = ChatColor.BOLD + "Please select a class!";
			
			if (getColor() == 1) {
				new ActionbarTitleObject(ChatColor.RED + message).send(player);
			} else if (getColor() == 2) {
				new ActionbarTitleObject(ChatColor.YELLOW + message).send(player);
			} else if (getColor() == 3) {
				new ActionbarTitleObject(ChatColor.GREEN + message).send(player);
			} else if (getColor() == 4) {
				new ActionbarTitleObject(ChatColor.BLUE + message).send(player);
			}

		} else {
			//Please select you character!
			String message = ChatColor.BOLD + "Please select a character!";
			
			if (getColor() == 1) {
				new ActionbarTitleObject(ChatColor.RED + message).send(player);
			} else if (getColor() == 2) {
				new ActionbarTitleObject(ChatColor.YELLOW + message).send(player);
			} else if (getColor() == 3) {
				new ActionbarTitleObject(ChatColor.GREEN + message).send(player);
			} else if (getColor() == 4) {
				new ActionbarTitleObject(ChatColor.BLUE + message).send(player);
			}
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
			setHealthPoints(player, hp);	//Sets payer HP ConcurrentHashMap
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
		double maxHP = PlayerAttributesManager.getMaxHealthPoints(player);
		double logoutHP = PlayerCharacterManager.getPlayerConfigDouble(player, "player.logout.hp");
		double maxStamina = PlayerAttributesManager.getMaxStaminaPoints(player);
		double logoutStamina = PlayerCharacterManager.getPlayerConfigDouble(player, "player.logout.stamina");
		double maxMana = PlayerAttributesManager.getMaxManaPoints(player);
		double logoutMana = PlayerCharacterManager.getPlayerConfigDouble(player, "player.logout.mana");

		//If the character has 0 logoutHP, lets set their HP to max.
		//This is good if the character is new and has never had
		//an HP value assigned to them.
		if (logoutHP == 0) {
			setHealthPoints(player, maxHP);
		} else {
			//The player is not new, so give
			//them the HP value they logged out with.
			setHealthPoints(player, logoutHP);
		}

		//If the character has 0 logoutStamina, lets set their Stamina to max.
		if (logoutStamina == 0) {
			setStaminaPoints(player, maxStamina);
		} else {
			//The player is not new, so give
			//them the Stamina Point value they logged out with.
			setStaminaPoints(player, logoutStamina);
		}

		//If the character has 0 logoutMana, lets set their Mana to max.
		if (logoutMana == 0) {
			setManaPoints(uuid, maxMana);
		} else {
			//The player is not new, so give
			//them the Mana Point value they logged out with.
			setManaPoints(uuid, logoutMana);
		}

		//Setup HashMaps.
		setMaxHealthPoints(uuid, maxHP);
		setMaxStaminaPoints(uuid, maxStamina);
		setMaxManaPoints(uuid, maxMana);
		setPlayerDead(uuid, false);

		//Give new players the MinePile game menu.
		PlayerMenuManager.givePlayerMenu(player);

		//Set player level and experience
		player.setLevel(PlayerCharacterManager.getPlayerConfigInt(player, "player.playerLVL"));
		player.setExp(Float.parseFloat(PlayerCharacterManager.getPlayerConfigString(player, "player.playerEXP")));

		//Feed player.
		player.setFoodLevel(20);

		//Add player to the HealthTagManager if they are not already on it.
		if (PlayerHealthTagManager.getSb() != null && PlayerHealthTagManager.getObj() != null) {
			PlayerHealthTagManager.addPlayer(player);
		}
		//Update the player's HP tag above their head.
		PlayerHealthTagManager.updateHealthTag(player);

		//Give the player a Menu!
		PlayerMenuManager.createMenu(player);

		//Load player items
		Inventory playerInv = player.getInventory();
		for (int i = 0; i <= playerInv.getSize(); i++) {
			try {
				ItemStack item = InventoryRestore.restoreItemStack(player, "playerInv", i);
				playerInv.setItem(i, item);
			} catch (NullPointerException exc) {}
		}

		//Load player armor
		try {
			player.getEquipment().setBoots(InventoryRestore.restoreItemStack(player, "armorInv", 100));
		} catch (NullPointerException exc) {}

		try {
			player.getEquipment().setLeggings(InventoryRestore.restoreItemStack(player, "armorInv", 101));
		} catch (NullPointerException exc) {}

		try {
			player.getEquipment().setChestplate(InventoryRestore.restoreItemStack(player, "armorInv", 102));
		} catch (NullPointerException exc) {}

		try {
			player.getEquipment().setHelmet(InventoryRestore.restoreItemStack(player, "armorInv", 103));
		} catch (NullPointerException exc) {}

		//If the character is a new player, lets give them some items.
		if (PlayerCharacterManager.getPlayerConfigBoolean(player, "player.charNew")) {
			//Armor
			ItemStack boots = RandomItemFactory.createArmor(new ItemStack(Material.LEATHER_BOOTS), ItemTier.T1, ItemQuality.JUNK, true);
			ItemStack leggings = RandomItemFactory.createArmor(new ItemStack(Material.LEATHER_LEGGINGS), ItemTier.T1, ItemQuality.JUNK, true);
			ItemStack chestplate = RandomItemFactory.createArmor(new ItemStack(Material.LEATHER_CHESTPLATE), ItemTier.T1, ItemQuality.JUNK, true);
			ItemStack helmet = RandomItemFactory.createArmor(new ItemStack(Material.LEATHER_HELMET), ItemTier.T1, ItemQuality.JUNK, true);

			//Weapon
			ItemStack sword = RandomItemFactory.createWeapon(new ItemStack(Material.WOOD_SWORD), ItemTier.T1, ItemQuality.JUNK, true);

			//Give new player the items.
			player.getEquipment().setBoots(boots);
			player.getEquipment().setLeggings(leggings);
			player.getEquipment().setChestplate(chestplate);
			player.getEquipment().setHelmet(helmet);
			playerInv.setItem(0, sword);

			//Set the player as not a new player.
			//This will prevent them from getting new items on next join.
			PlayerCharacterManager.setPlayerConfigBoolean(player, "player.charNew", false);
		}

		//Restore the players items
		BankChestManager.restoreBank(player);

		//Update the players armor.
		PlayerAttributesManager.applyNewAttributes(player, false);

		//GIVE PLAYER TEST ITEM
		RandomItemFactory.makeTestItem(player);
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
		double health = healthPoints.get(uuid);
		double maxHealth = getMaxHealthPoints(uuid);
		if (health > maxHealth) {
			return maxHealth;
		} else {
			return health;
		}
	}

	public static void setHealthPoints(Player player, double newHealthTotal) {
		UUID uuid = player.getUniqueId();

		//Set the players health map
		healthPoints.put(uuid, newHealthTotal);

		if (maxHealthPoints.containsKey(uuid)) {
			double playerMaxHealth = getMaxHealthPoints(uuid);
			double healthPercent = newHealthTotal / playerMaxHealth;
			double hpDisplay = healthPercent * 20;

			//Set players health tag under their name
			PlayerHealthTagManager.updateHealthTag(player);

			//Set hearts
			if (hpDisplay > 20) {
				player.setHealth(20);
			} else if (hpDisplay <= 0) {
				player.setHealth(1);
			} else {
				player.setHealth(hpDisplay);
			}
		}
	}

	public static double getMaxHealthPoints(UUID uuid) {
		return maxHealthPoints.get(uuid);
	}

	public static void setMaxHealthPoints(UUID uuid, double maxhp) {
		maxHealthPoints.put(uuid, maxhp);
	}

	public static double getStaminaPoints(UUID uuid) {
		double stamina = staminaPoints.get(uuid);
		double maxStamina = getMaxStaminaPoints(uuid);
		if (stamina > maxStamina) {
			return maxStamina;
		} else {
			return stamina;
		}
	}

	public static void setStaminaPoints(Player player, double stamina) {
		UUID uuid = player.getUniqueId();
		
		//Apply stamina points to hashmap.
		staminaPoints.put(uuid, stamina);
		
		if (maxStaminaPoints.containsKey(uuid)) {
			double playerMaxStamina = getMaxStaminaPoints(uuid);
			double staminaPercent = stamina / playerMaxStamina;
			double spDisplay = staminaPercent * 20;
			
			//Set food level
			if (spDisplay > 20) {
				player.setFoodLevel(20);
			} else if (spDisplay < 1) {
				player.setFoodLevel(1);
			} else {
				player.setFoodLevel((int) spDisplay);
				
				if (spDisplay <= 7) {
					player.playSound(player.getLocation(), Sound.WOLF_PANT, 10F, 1.5F);
				}
			}
		}
	}

	public static double getMaxStaminaPoints(UUID uuid) {
		return maxStaminaPoints.get(uuid);
	}

	public static void setMaxStaminaPoints(UUID uuid, double maxStamina) {
		maxStaminaPoints.put(uuid, maxStamina);
	}

	public static double getManaPoints(UUID uuid) {
		double mana = manaPoints.get(uuid);
		double maxMana = getMaxManaPoints(uuid);
		if (mana > maxMana) {
			return maxMana;
		} else {
			return mana;
		}
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

	public static int getColor() {
		return classColor;
	}

	public static void setColor(int color) {
		PlayerManager.classColor = color;
	}
}