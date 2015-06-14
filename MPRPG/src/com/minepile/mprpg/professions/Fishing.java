package com.minepile.mprpg.professions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.chat.MessageManager;
import com.minepile.mprpg.player.PlayerManager;

public class Fishing {
	
	//setup instance variables
	public static MPRPG plugin;
	static Fishing fishingManagerInstance = new Fishing();
    static String FILE_PATH = "plugins/MPRPG/professions/fishing.yml";
    
    //Config file.
    static File configFile;
    static FileConfiguration fishingConfig;
    
	//HashMap to hold levels in memory.
	static HashMap<Integer, Integer> configFishingLevel = new HashMap<Integer, Integer>();
	
	//Create instance
	public static Fishing getInstance() {
		return fishingManagerInstance;
	}
	
	//Setup FishingManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		//If fishing configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(FILE_PATH)).exists()){
			createFishingConfig();
        } else {
        	//lets load the configuration file.
        	configFile = new File(FILE_PATH);
            fishingConfig =  YamlConfiguration.loadConfiguration(configFile);
            for (int i = 1; i < 100; i++) {
            	String z = Integer.toString(i);
            	int totalEXPforLVL = (int) fishingConfig.get(z);
            	configFishingLevel.put(i, totalEXPforLVL);
            }
        }
		
	}
	
	/**
	 * Instructions for what to do when a player is fishing.
	 * 
	 * @param player The player who is fishing.
	 */
	public static void toggleFishing(Player player) {
		
		ItemStack is = player.getInventory().getItemInHand();
		ItemMeta im = is.getItemMeta();
		List<String> cointainsLore = im.getLore();
		
		//If the fishing rod does not have lore, lets create a new fishing rod.
		if (cointainsLore == null) {
			createFishingRod(player);
		}
		
		//Now that we know the fishing rod exists, lets get it's data.
		int currentRodLVL = getLoreLVL(player);
		int currentRodEXP = getLoreEXP(player);

		if (currentRodLVL <= 19) {
			
			//Lets calculate how much EXP we need to add.
			int expGain = calculateExpGain(10);

			if (expGain != 0) {
				player.getInventory().addItem(new ItemStack(Material.RAW_FISH, 1));
				toggleRodUpdate(player, expGain, currentRodEXP, currentRodLVL);
			} else {
				//Let user know fishing attempt was not successful.
				player.sendMessage(ChatColor.GRAY + "        " + ChatColor.ITALIC + "Fishing attempt was not successful.");
			}
		} else if (currentRodLVL > 19 && currentRodLVL <= 39) {
			
			//Lets calculate how much EXP we need to add.
			int expGain = calculateExpGain(11);

			if (expGain != 0) {
				player.getInventory().addItem(new ItemStack(Material.RAW_FISH, 1, (short) 1));
				toggleRodUpdate(player, expGain, currentRodEXP, currentRodLVL);
			} else {
				//Let user know fishing was not successful.
				player.sendMessage(ChatColor.GRAY + "        " + ChatColor.ITALIC + "Fishing attempt was not successful.");
			}
		} else if (currentRodLVL > 39 && currentRodLVL <= 59) {
			
			//Lets calculate how much EXP we need to add.
			int expGain = calculateExpGain(12);

			if (expGain != 0) {
				player.getInventory().addItem(new ItemStack(Material.RAW_FISH, 1, (short) 2));
				toggleRodUpdate(player, expGain, currentRodEXP, currentRodLVL);
			} else {
				//Let user know fishing was not successful.
				player.sendMessage(ChatColor.GRAY + "        " + ChatColor.ITALIC + "Fishing attempt was not successful.");
			}
		} else if (currentRodLVL > 59 && currentRodLVL <= 79) {
			
			//Lets calculate how much EXP we need to add.
			int expGain = calculateExpGain(13);

			if (expGain != 0) {
				player.getInventory().addItem(new ItemStack(Material.RAW_FISH, 1, (short) 3));
				toggleRodUpdate(player, expGain, currentRodEXP, currentRodLVL);
			} else {
				//Let user know fishing was not successful.
				player.sendMessage(ChatColor.GRAY + "        " + ChatColor.ITALIC + "Fishing attempt was not successful.");
			}
		}
	}
	
	/**
	 * Updates a players fishing rod with new statistical information.
	 * 
	 * @param player The player with the fishing rod that will receive updates.
	 * @param expGain The amount of experience the fishing rod has gained.
	 * @param currentRodEXP The current fishing rods experience before the update is applied.
	 * @param currentRodLVL The current level of the fishing rod before the update is applied.
	 */
	public static void toggleRodUpdate(Player player, int expGain, int currentRodEXP, int currentRodLVL) {
		
		//Additional variables.
		int totalEXP = currentRodEXP + expGain;
		int expGoal = configFishingLevel.get(currentRodLVL);
		
		//If the players fishing rod has leveled up, update the lore, and show a message.
		if (totalEXP > expGoal) {
			//The fishing rod has leveled. Lets add 1 level to it.
			int newRodkLVL = currentRodLVL + 1;
			int getLeftOverEXP = totalEXP - expGoal;
			
			if (newRodkLVL == 20 || newRodkLVL == 40 || newRodkLVL == 60 || newRodkLVL == 80) {

				//Update the items meta and add 1 level.
				setLore(player, getLeftOverEXP, newRodkLVL);
				
				//Send EXP up message.
				player.sendMessage(MessageManager.showEXPLevel(expGain, totalEXP, expGoal));
				
				//Send level up message.
				player.sendMessage(MessageManager.selectMessagePrefix("debug") +
						ChatColor.YELLOW + ChatColor.BOLD + "Your fishing rod is now level " + newRodkLVL + ".");
				
				//Play a level up sound.
				player.playSound(player.getLocation(), Sound.FIREWORK_TWINKLE2, 1F, 1F);
			} else {
				
				//Update the items meta and add 1 level.
				setLore(player, getLeftOverEXP, newRodkLVL);
				
				//Send EXP up message.
				player.sendMessage(MessageManager.showEXPLevel(expGain, totalEXP, expGoal));
				
				//Send level up message.
				player.sendMessage(MessageManager.selectMessagePrefix("debug") +
						ChatColor.YELLOW + ChatColor.BOLD + "Your fishing rod is now level " + newRodkLVL + ".");
				
				//Play a level up sound.
				player.playSound(player.getLocation(), Sound.FIREWORK_TWINKLE2, 1F, 1F);
			}
		} else { //The players fishing rod has not leveled up.  Just add exp to it.
			//Update the items meta and add 1 level.
			setLore(player, totalEXP, currentRodLVL);
			
			//Send EXP up message.
			if (PlayerManager.getPlayerConfigInt(player, "setting.chat.professionDebug") == 1) {
				player.sendMessage(MessageManager.showEXPLevel(expGain, totalEXP, expGoal));
			}
		}
	}
	
	/**
	 * Chat Message for the fishing rod.
	 * 
	 * @param player The player to show the fishing message.
	 * @param expGain The experience gain of the fishing rod.
	 */
	public static void chatfishingMessage(Player player, int expGain) {
		//If expGain is 0, let the user know the fishing attempt was not successful.
		//If the expGain is any other number, let them know it was successful.
		if (expGain != 0) {
			//add EXP to tool
			int currentRodEXP = getLoreEXP(player);
			int currentRodLVL = getLoreLVL(player);
			int expToNextLevel = configFishingLevel.get(currentRodLVL);
			
			if (currentRodEXP < expToNextLevel) {
				//Displays the leveling bar when user successfully catches a fish.
				player.sendMessage(MessageManager.showEXPLevel(expGain, currentRodEXP, expToNextLevel));
			} else {
				//level up the players fishing rod.
				player.sendMessage(MessageManager.showEXPLevel(expGain, currentRodEXP, expToNextLevel));
				
				int newRodLVL = currentRodLVL + 1;
				player.sendMessage(MessageManager.selectMessagePrefix("debug") +
						ChatColor.YELLOW + ChatColor.BOLD + "Your fishing rod is now level " + newRodLVL + ".");
			}
		} else {
			//Let user know fishing was not successful.
			player.sendMessage(ChatColor.GRAY + "        " + ChatColor.ITALIC + "Fishing attempt was not successful.");
		}
	}	
	
	/**
	 * Gets the Fishing rods experience from the ItemStack's Lore.
	 * 
	 * @param player The player with the fishing rod.
	 * @return The experience the fishing rod currently has.
	 */
	public static int getLoreEXP(Player player) {
		ItemStack item = player.getItemInHand();

		if (item != null) {
			ItemMeta meta = item.getItemMeta();
			if (meta != null) {
				List<String> lore = meta.getLore();
				
				if (lore != null) {
					String loreLineText = lore.get(1);
					
					String[] parts = loreLineText.split("EXP: " + ChatColor.RESET + ChatColor.BLUE);
					String part2 = parts[1];
					
					String[] getEXP = part2.split(" / ");
					String part3 = getEXP[0];

					int parseInt = Integer.parseInt(part3);
					return parseInt;
					
				}
			} else {
				return 0;
			}
		}
		return 0;
	}
	
	/**
	 * Gets the level from the fishing rods ItemStack lore.
	 * 
	 * @param player The player with the fishing rod.
	 * @return The current level of the fishing rod.
	 */
	public static int getLoreLVL(Player player) {
		ItemStack item = player.getItemInHand();

		if (item != null) {
			ItemMeta meta = item.getItemMeta();
			if (meta != null) {
				// This part is probably never null...
				List<String> lore = meta.getLore();
				
				if (lore != null) {
					String loreLineText = lore.get(0);
					
					String[] parts = loreLineText.split("LVL: " + ChatColor.RESET + ChatColor.LIGHT_PURPLE);
					String part2 = parts[1];
					
					int parseInt = Integer.parseInt(part2);
					return parseInt;
					
				}
			} else {
				return 1;
			}
		}
		return 1;
	}
	
	/**
	 * Sets the fishing rods ItemStack lore.
	 * <p>
	 * This is useful information for the player. It shows the items statics.
	 * 
	 * @param player The player that has a fishing rod waiting to have lore set to it.
	 * @param exp The experience that the fishing rod's lore will contain.
	 * @param lvl The level the fishing rod's lore will contain.
	 */
	public static void setLore(Player player, int exp, int lvl) {
		
		ItemStack is = player.getInventory().getItemInHand();
		ItemMeta im = is.getItemMeta();
		List<String> cointainsLore = im.getLore();
		
		if (cointainsLore == null) {
			createFishingRod(player);
		} else {
			int rodEXP = exp;
			int rodLVL = lvl;
			int expToNextLevel = configFishingLevel.get(rodLVL);
			int expPercent = ((100 * rodEXP) / expToNextLevel);
				
			//Set the item name
			if (lvl <= 19) {
				im.setDisplayName(ChatColor.WHITE + "Novice Fishing Rod");
			} else if (lvl > 19 && lvl <= 39) {
				im.setDisplayName(ChatColor.GREEN + "Apprentice Fishing Rod");
			} else if (lvl > 29 && lvl <= 59) {
				im.setDisplayName(ChatColor.BLUE + "Adept Fishing Rod");
			} else if (lvl > 59 && lvl <= 79) {
				im.setDisplayName(ChatColor.DARK_PURPLE + "Expert Fishing Rod");
			} else {
				im.setDisplayName(ChatColor.GOLD + "Master Fishing Rod");
			}
				
			//Set the item lore
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "LVL: " + ChatColor.RESET +
					ChatColor.LIGHT_PURPLE + rodLVL);
			lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "EXP: " + 
					ChatColor.RESET + ChatColor.BLUE + rodEXP + " / " + expToNextLevel);
			lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "EXP: " + 
					MessageManager.percentBar(expPercent) + ChatColor.GRAY + " " + expPercent + "%");
			lore.add(" ");//create blank space
			
			if (lvl <= 19) {
				lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "A fishing rod made of wood.");
			} else if (lvl > 19 && lvl <= 39) {
				lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "A fishing rod reinforced with stone.");
			} else if (lvl > 29 && lvl <= 59) {
				lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "A fishing rod reinforced with iron.");
			} else if (lvl > 59 && lvl <= 79) {
				lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "A fishing rod reinforced with gold.");
			} else {
				lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "A fishing rod reinforced with diamond.");
			}
			
			//Set the item lore
			im.setLore(lore);
			//Set the item meta
			is.setItemMeta(im);
		}
	}
	
	/**
	 * Creates a new fishing rod!
	 * 
	 * @param player The player with the new fishing rod.
	 */
	public static void createFishingRod(Player player) {
		ItemStack tool = player.getInventory().getItemInHand();
		ItemMeta meta = tool.getItemMeta();

		int expGoal = configFishingLevel.get(1);
		
		//Set the item name
		meta.setDisplayName(ChatColor.WHITE + "Novice Fishing Rod");
		
		//Set the item lore
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "LVL: " + ChatColor.RESET +
				ChatColor.LIGHT_PURPLE + "1");
		lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "EXP: " + 
				ChatColor.RESET + ChatColor.BLUE + "0" + " / " + expGoal);
		lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "EXP: " + 
				MessageManager.percentBar(0) + ChatColor.GRAY + " " + "0" + "%");
		lore.add(" ");//create blank space
		lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "A fishing rod made of wood.");
		
		//Set the item lore
		meta.setLore(lore);
		//Set the item meta
		tool.setItemMeta(meta);
	}
	
	/**
	 * Calculates how much EXP the player should get from fishing. 
	 * If the calculation is less than a certain percentage, then the 
	 * fishing attempt will not be successful and EXP will not be earned.  
	 * Default success rate is 70%.
	 * 
	 * @param multiplier The success rate of the fishing attempt.
	 * @return
	 */
	public static int calculateExpGain(int multiplier){
		double random = (Math.random() * 10);
		if(random > 3) {
			//return successful mine
			return (int) ((int)random * multiplier);
		} else {
			//mine was not successful
			return 0;
		}
	}
	
	/**
	 * This creates the configuration file that has the EXP leveling requirements.
	 */
    private static void createFishingConfig() {
    	
        configFile = new File(FILE_PATH);
        fishingConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        //Loop through and create each level for fishing.
        for (int i = 1; i <= 100; i++ ) {
        	int y = 167;					//This is the first level.
        	String z = Integer.toString(i);	//Convert i to string for yml format.
        	fishingConfig.set(z, i * y);		//For every level multiply the first level x the number in the loop.
        }

        try {
            fishingConfig.save(configFile);	//Save the file.
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
	
}
