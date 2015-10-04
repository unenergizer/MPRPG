package com.minepile.mprpg.entities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.items.ItemIdentifierManager;
import com.minepile.mprpg.items.MerchantManager;
import com.minepile.mprpg.player.PlayerCharacterManager;
import com.minepile.mprpg.player.PlayerHealthTagManager;
import com.minepile.mprpg.professions.Alchemy;
import com.minepile.mprpg.professions.Blacksmithing;
import com.minepile.mprpg.professions.Cooking;
import com.minepile.mprpg.professions.Fishing;
import com.minepile.mprpg.professions.Herbalism;
import com.minepile.mprpg.professions.Mining;

public class CitizensManager {

	static CitizensManager citizenManagerInstance = new CitizensManager();

	private static MPRPG plugin;

	private static File configFile;
	private static FileConfiguration npcConfig;
	private static String FILE_PATH = "plugins/MPRPG/npc/citizens.yml";
	
	//Holograms
	private static String namePrefix = ChatColor.YELLOW + "" + ChatColor.BOLD;
	private static ArrayList<Hologram> npcHolograms = new ArrayList<Hologram>();

	public static CitizensManager getInstance() {
		return citizenManagerInstance;
	}

	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		//If citizens configuration does not exist, create the file. Otherwise lets load the file.
		if(!(new File(FILE_PATH)).exists()){
			createCitizenConfig();
		} else {
			//lets load the configuration file.
			configFile = new File(FILE_PATH);
			npcConfig =  YamlConfiguration.loadConfiguration(configFile);
		}

		//Apply Citizens Configuration after server startup.
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,  new Runnable() {
			public void run() {
				applyCitizensConfiguration();
				Bukkit.getLogger().info("[MPRPG] Applied Citizens HP and Holograms.");
			}
		}, 5 * 20L);
	}
	
	/**
	 * This will disable this class.
	 */
	public static void disable() {
		removeHolograms();
	}

	/**
	 * This ENUM defines what type of NPC's exist in game.
	 * 
	 * @author Andrew
	 */
	public static enum CitizenType {
		
		//Class NPC's
		ARCHER (ChatColor.GREEN + "" + ChatColor.BOLD + "Archer Class"),			//Archer class selection
		MAGE (ChatColor.BLUE + "" + ChatColor.BOLD + "Mage Class"),				//Mage class selection
		ROGUE (ChatColor.YELLOW + "" + ChatColor.BOLD + "Rogue Class"),			//Rogue class selection
		WARRIOR (ChatColor.RED + "" + ChatColor.BOLD + "Warrior Class"),			//Warrior class selection
		
		//Profession NPC's
		ALCHEMIST (namePrefix + "Alchemy Trainer"),			//Alchemist Trainer
		BLACKSMITH (namePrefix + "Blacksmith Trainer"),		//Blacksmith Trainer
		COOK (namePrefix + "Cooking Trainer"),				//Cooking Trainer
		FISHER (namePrefix + "Fishing Trainer"),			//Fishing Trainer
		HERBALIST (namePrefix + "Herbalisim Trainer"),		//Herbalism Trainer
		MINING (namePrefix + "Mining Trainer"),				//Mining Trainer
		
		//Other NPC's
		INN_KEEPER (namePrefix + "Inn Keeper"),				//Inn Keeper
		ITEM_IDENTIFIER (namePrefix + "Item Identifier"),	//Item Identifier (NPC identifies unidentified items)
		MERCHANT (namePrefix + "Item Merchant"),			//Merchant
		NONE (""),											//NONE.  This NPC is just filler.
		QUEST_GIVER (namePrefix + "Quest Giver");			//NPC that gives quests.
		
		private String name;

		CitizenType(String s) {
			this.name = s;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * This will apply HP to a NPC.
	 * The HP is displayed under the NPC's name.
	 */
	private void applyCitizensConfiguration() {
		//Get all online players.
		for (Entity npc : Bukkit.getWorld("world").getEntities()) {

			//Get all Citizen NPC's.
			if (npc instanceof Player && npc.hasMetadata("NPC")) {
				
				String npcName = ((Player) npc).getDisplayName();
				double npcHP = getCitizenMaxHP(npcName);
				String hologramText = getCitizenType(npcName).getName();
				String click = ChatColor.BOLD + "RIGHT-CLICK";
				
				Location loc = getCitizenLocation(npcName);
				World world = loc.getWorld();
				double x = loc.getX();
				double y = loc.getY() + 3.5;
				double z = loc.getZ();
				Location hologramLoc = new Location(world, x, y, z);
				
				//Set citizen health tag under their name.
				PlayerHealthTagManager.updateNPCHealthTag((Player) npc, npcHP);
				
				//Setup NPC Hologram
				Hologram hologram = HologramsAPI.createHologram(plugin, hologramLoc);
		    	hologram.appendTextLine(hologramText);
		    	
		    	if (!hologramText.isEmpty()) {
		    		hologram.appendTextLine(click);
		    	}
		    	
		    	//Add hologram to the array list.
		    	npcHolograms.add(hologram);
			}
		}
	}
    
    /**
     * This will delete the holograms on server reload or shut down.
     */
    private static void removeHolograms() {
    	for (int i = 0; i < npcHolograms.size(); i++) {
    		npcHolograms.get(i).delete();
    	}
    }

	/**
	 * This will trigger when an NPC is left clicked or right clicked.
	 * 
	 * @param player The player that interacted with the NPC.
	 * @param npc The Citizen (created by the Citizen plugin) that was clicked.
	 */
	public static void onCitizenInteract(Player player, Player npc) {
		String npcName = npc.getDisplayName();
		CitizenType type = getCitizenType(npcName);
		Location loc = npc.getLocation().add(0, 2, 0);
		int rand = randomInt(1, 100);
		
		//Trigger code in appropriate Java Class file.
		if (type == CitizenType.ALCHEMIST) {
			Alchemy.toggleCitizenInteract(player);
		} else if (type == CitizenType.BLACKSMITH) {
			Blacksmithing.toggleCitizenInteract(player);
		} else if (type == CitizenType.COOK) {
			Cooking.toggleCitizenInteract(player);
		} else if (type == CitizenType.FISHER) {
			Fishing.toggleCitizenInteract(player);
		} else if (type == CitizenType.HERBALIST) {
			Herbalism.toggleCitizenInteract(player);
		} else if (type == CitizenType.MINING) {
			Mining.toggleCitizenInteract(player);
		} else if (type == CitizenType.ITEM_IDENTIFIER) {
			ItemIdentifierManager.toggleCitizenInteract(player, npc);
		} else if (type == CitizenType.INN_KEEPER) {
			InnKeeperManager.toggleCitizenInteract(player, npc);
		} else if (type == CitizenType.MERCHANT) {
			MerchantManager.toggleCitizenInteract(player);
		} else if (type == CitizenType.NONE) {
			//Do nothing. This is a regular NPC.
		} else if (type == CitizenType.QUEST_GIVER) {
			//TODO
		} else if (type == CitizenType.ARCHER) {
			player.sendMessage(ChatColor.GREEN + "You have selected the " + ChatColor.DARK_GREEN + "Archer" + ChatColor.GREEN +" class.");
			PlayerCharacterManager.toggleClassSelectionInteract(player, "archer");
		} else if (type == CitizenType.MAGE) {
			player.sendMessage(ChatColor.GREEN + "You have selected the " + ChatColor.BLUE + "Mage" + ChatColor.GREEN +" class.");
			PlayerCharacterManager.toggleClassSelectionInteract(player, "mage");
		} else if (type == CitizenType.ROGUE) {
			player.sendMessage(ChatColor.GREEN + "You have selected the " + ChatColor.YELLOW + "Rogue" + ChatColor.GREEN +" class.");
			PlayerCharacterManager.toggleClassSelectionInteract(player, "rogue");
		} else if (type == CitizenType.WARRIOR) {
			player.sendMessage(ChatColor.GREEN + "You have selected the " + ChatColor.RED + "Warrior" + ChatColor.GREEN +" class.");
			PlayerCharacterManager.toggleClassSelectionInteract(player, "warrior");
		}
		
		//Show particle effect.
		for (int i = 0; i <= 5; i++) {
			Bukkit.getWorld("world").spigot().playEffect(loc, Effect.HAPPY_VILLAGER);
		}
		
		//Player sound.
		if (rand < 25) {
			player.playSound(loc, Sound.VILLAGER_HAGGLE, .8f, .8f);
		} else if (rand >= 25 && rand < 50) {
			player.playSound(loc, Sound.VILLAGER_IDLE, .8f, .8f);
		} else if (rand >= 50 && rand < 75) {
			player.playSound(loc, Sound.VILLAGER_NO, .8f, .8f);
		} else if (rand >= 75) {
			player.playSound(loc, Sound.VILLAGER_YES, .8f, .8f);
		}
	}

	/**
	 * Generates a random integer between the min value and the max value.
	 * 
	 * @param min the minimal value
	 * @param max the maximum value
	 * @return a random value between the min value and the max value
	 */
	public static int randomInt(int min, int max) {
		Random rand = new Random();

		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	/**
	 * Creates a new configuration file on a players first visit to the server.
	 */
	private static void createCitizenConfig() {

		configFile = new File(FILE_PATH);
		npcConfig =  YamlConfiguration.loadConfiguration(configFile);
		npcConfig.set("Whizzig", "Whizzig");

		//Types: ItemIdentifier, Merchant, Blacksmith, Alchemist, Quest, None
		npcConfig.set("Whizzig.type", "Merchant");

		//The HP to display under the NPC's name.
		npcConfig.set("Whizzig.health", 1000);

		//Spawn location (used for Hologram
		npcConfig.set("Whizzig.spawn.x", 28.5);
		npcConfig.set("Whizzig.spawn.y", 79);
		npcConfig.set("Whizzig.spawn.z", -13.5);

		try {
			npcConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public static double getCitizenMaxHP(String citizenName) {
		String npc = citizenName.replace(" ", "_");
		return npcConfig.getDouble(npc + ".health");
	}

	public static CitizenType getCitizenType(String citizenName) {
		String npc = citizenName.replace(" ", "_");
		String type = npcConfig.getString(npc + ".type");

		if (type == null) {
			//Will leave null to report any errors if necessary.
			Bukkit.getLogger().info("[MPRPG] NPC: " + npc + " does not exist in config.");
			return CitizenType.NONE;
		} else {
			if (type.equalsIgnoreCase("ALCHEMIST")) {
				return CitizenType.ALCHEMIST;
			} else if (type.equalsIgnoreCase("BLACKSMITH")) {
				return CitizenType.BLACKSMITH;
			} else if (type.equalsIgnoreCase("COOK")) {
				return CitizenType.COOK;
			} else if (type.equalsIgnoreCase("FISHER")) {
				return CitizenType.FISHER;
			} else if (type.equalsIgnoreCase("HERBALIST")) {
				return CitizenType.HERBALIST;
			} else if (type.equalsIgnoreCase("MINING")) {
				return CitizenType.MINING;
			} else if (type.equalsIgnoreCase("INN_KEEPER")) {
				return CitizenType.INN_KEEPER;
			} else if (type.equalsIgnoreCase("ITEM_IDENTIFIER")) {
				return CitizenType.ITEM_IDENTIFIER;
			} else if (type.equalsIgnoreCase("MERCHANT")) {
				return CitizenType.MERCHANT;
			} else if (type.equalsIgnoreCase("NONE")) {
				return CitizenType.NONE;
			} else if (type.equalsIgnoreCase("QUEST_GIVER")) {
				return CitizenType.QUEST_GIVER;
			} else if (type.equalsIgnoreCase("ARCHER")) {
				return CitizenType.ARCHER;
			} else if (type.equalsIgnoreCase("MAGE")) {
				return CitizenType.MAGE;
			} else if (type.equalsIgnoreCase("ROGUE")) {
				return CitizenType.ROGUE;
			} else if (type.equalsIgnoreCase("WARRIOR")) {
				return CitizenType.WARRIOR;
			} else {
				//This should never happen.
				return null;
			}
		}
	}

	public static Location getCitizenLocation(String citizenName) {
		String npc = citizenName.replace(" ", "_");
		double x = npcConfig.getDouble(npc + ".spawn.x");
		double y = npcConfig.getDouble(npc + ".spawn.y");
		double z = npcConfig.getDouble(npc + ".spawn.z");

		return new Location(Bukkit.getWorld("World"), x, y, z);
	}
}
