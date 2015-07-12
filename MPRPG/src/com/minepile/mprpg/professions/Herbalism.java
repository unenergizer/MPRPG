package com.minepile.mprpg.professions;

import io.puharesource.mc.titlemanager.api.ActionbarTitleObject;

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

public class Herbalism {
	
	//setup instance variables
	public static MPRPG plugin;
	static Herbalism herbalismManagerInstance = new Herbalism();
	static String FILE_PATH = "plugins/MPRPG/professions/herbalism.yml";
	
	//Config file
	static File configFile;
    static FileConfiguration herbalismConfig;
	
	//HashMap to hold levels in memory.
	static HashMap<Integer, Integer> configHerbalismLevel = new HashMap<Integer, Integer>();
	
	//Create instance
	public static Herbalism getInstance() {
		return herbalismManagerInstance;
	}
	
	//Setup HerbalismManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		//If herbalism configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(FILE_PATH)).exists()){
			createHerbalismConfig();
        } else {
        	//lets load the configuration file.
        	configFile = new File(FILE_PATH);
            herbalismConfig =  YamlConfiguration.loadConfiguration(configFile);
            for (int i = 1; i < 100; i++) {
            	String z = Integer.toString(i);
            	int totalEXPforLVL = (int) herbalismConfig.get(z);
            	configHerbalismLevel.put(i, totalEXPforLVL);
            }
        }
		
	}
	
	/**
	 * Instructions for what to do when a player is cutting plants.
	 * 
	 * @param player The player who is cutting plants.
	 * @param plant The plant that was cut.
	 */
	public static void toggleHerbalism(Player player, Material plant) {
		
		ItemStack is = player.getInventory().getItemInHand();
		ItemMeta im = is.getItemMeta();
		List<String> cointainsLore = im.getLore();
		
		//If the herbalism tool does not have lore, lets create a new herbalism tool.
		if (cointainsLore == null) {
			createHerbalismTool(player);
		}
		
		//Now that we know the tool exists, lets get it's data.
		int currentToolLVL = getLoreLVL(player);
		int currentToolEXP = getLoreEXP(player);

		if (currentToolLVL <= 19) {
			
			//Lets calculate how much EXP we need to add.
			int expGain = calculateExpGain(10);

			if (expGain != 0) {
				player.getInventory().addItem(new ItemStack(plant, 1));
				toggleToolUpdate(player, expGain, currentToolEXP, currentToolLVL);
			} else {
				//Let user know herbalism attempt was not successful.
				new ActionbarTitleObject(ChatColor.GRAY + "" + ChatColor.ITALIC + "Harvest attempt was not successful.").send(player);
			}
		} else if (currentToolLVL > 19 && currentToolLVL <= 39) {
			
			//Lets calculate how much EXP we need to add.
			int expGain = calculateExpGain(11);

			if (expGain != 0) {
				player.getInventory().addItem(new ItemStack(plant, 1));
				toggleToolUpdate(player, expGain, currentToolEXP, currentToolLVL);
			} else {
				//Let user know herbalism attempt was not successful.
				new ActionbarTitleObject(ChatColor.GRAY + "" + ChatColor.ITALIC + "Harvest attempt was not successful.").send(player);
			}
		} else if (currentToolLVL > 39 && currentToolLVL <= 59) {
			
			//Lets calculate how much EXP we need to add.
			int expGain = calculateExpGain(12);

			if (expGain != 0) {
				player.getInventory().addItem(new ItemStack(plant, 1));
				toggleToolUpdate(player, expGain, currentToolEXP, currentToolLVL);
			} else {
				//Let user know herbalism attempt was not successful.
				new ActionbarTitleObject(ChatColor.GRAY + "" + ChatColor.ITALIC + "Harvest attempt was not successful.").send(player);
			}
		} else if (currentToolLVL > 59 && currentToolLVL <= 79) {
			
			//Lets calculate how much EXP we need to add.
			int expGain = calculateExpGain(13);

			if (expGain != 0) {
				player.getInventory().addItem(new ItemStack(plant, 1));
				toggleToolUpdate(player, expGain, currentToolEXP, currentToolLVL);
			} else {
				//Let user know herbalism attempt was not successful.
				new ActionbarTitleObject(ChatColor.GRAY + "" + ChatColor.ITALIC + "Harvest attempt was not successful.").send(player);
			}
		}
	}
	
	/**
	 * Updates a players herbalism tool with new statistical information.
	 * 
	 * @param player The player with the tool to update.
	 * @param expGain The experience that is going to be added to the tool.
	 * @param currentToolEXP The current experience of the tool before the change is made.
	 * @param currentToolLVL The current level of the tool before the change is made.
	 */
	public static void toggleToolUpdate(Player player, int expGain, int currentToolEXP, int currentToolLVL) {
		
		//Additional variables.
		int totalEXP = currentToolEXP + expGain;
		int expGoal = configHerbalismLevel.get(currentToolLVL);
		
		//If the players herbalism tool has leveled up, update the lore, and show a message.
		if (totalEXP > expGoal) {
			//The herbalism tool has leveled. Lets add 1 level to it.
			int newToolLVL = currentToolLVL + 1;
			int getLeftOverEXP = totalEXP - expGoal;
			
			if (newToolLVL == 20 || newToolLVL == 40 || newToolLVL == 60 || newToolLVL == 80) {

				//Update the items meta and add 1 level.
				setLore(player, getLeftOverEXP, newToolLVL);
				
				//Send EXP up message.
				new ActionbarTitleObject(MessageManager.showEXPLevel(expGain, totalEXP, expGoal)).send(player);
				
				//Send level up message.
				player.sendMessage(ChatColor.GREEN + "Your plant cutters are now level " + ChatColor.GOLD + newToolLVL + ChatColor.GREEN + ".");
				
				//Play a level up sound.
				player.playSound(player.getLocation(), Sound.FIREWORK_TWINKLE2, 1F, 1F);
			} else {
				
				//Update the items meta and add 1 level.
				setLore(player, getLeftOverEXP, newToolLVL);
				
				//Send EXP up message.
				new ActionbarTitleObject(MessageManager.showEXPLevel(expGain, totalEXP, expGoal)).send(player);
				
				//Send level up message.
				player.sendMessage(ChatColor.GREEN + "Your plant cutters are now level " + ChatColor.GOLD + newToolLVL + ChatColor.GREEN + ".");
				
				//Play a level up sound.
				player.playSound(player.getLocation(), Sound.FIREWORK_TWINKLE2, 1F, 1F);
			}
		} else { //The players herbalism tool has not leveled up.  Just add exp to it.
			//Update the items meta and add 1 level.
			setLore(player, totalEXP, currentToolLVL);
			
			//Send EXP up message.
			if (PlayerManager.getPlayerConfigBoolean(player, "setting.chat.professionDebug") == true) {
				new ActionbarTitleObject(MessageManager.showEXPLevel(expGain, totalEXP, expGoal)).send(player);
			}
		}
	}
	
	/**
	 * Gets the experience of the cutting tool from the ItemStack's lore.
	 * 
	 * @param player The player who has the item.
	 * @return The experience the item has.
	 */
	private static int getLoreEXP(Player player) {
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
	 * Gets the level of the cutting tool from the ItemStack's lore.
	 * 
	 * @param player The player who has the item.
	 * @return The level the item has.
	 */
	private static int getLoreLVL(Player player) {
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
	 * Sets lore on the players Shears.
	 * <p>
	 * This is useful information for the player. It shows the items statics.
	 * 
	 * @param player The player with the shears.
	 * @param exp The experience to place on the shears.
	 * @param lvl The level to place on the shears.
	 */
	private static void setLore(Player player, int exp, int lvl) {
		
		ItemStack is = player.getInventory().getItemInHand();
		ItemMeta im = is.getItemMeta();
		List<String> cointainsLore = im.getLore();
		
		if (cointainsLore == null) {
			createHerbalismTool(player);
		} else {
			int toolEXP = exp;
			int toolLVL = lvl;
			int expToNextLevel = configHerbalismLevel.get(toolLVL);
			int expPercent = ((100 * toolEXP) / expToNextLevel);
				
			//Set the item name
			if (lvl <= 19) {
				im.setDisplayName(ChatColor.WHITE + "Novice Shears");
			} else if (lvl > 19 && lvl <= 39) {
				im.setDisplayName(ChatColor.GREEN + "Apprentice Shears");
			} else if (lvl > 29 && lvl <= 59) {
				im.setDisplayName(ChatColor.BLUE + "Adept Shears");
			} else if (lvl > 59 && lvl <= 79) {
				im.setDisplayName(ChatColor.DARK_PURPLE + "Expert Shears");
			} else {
				im.setDisplayName(ChatColor.GOLD + "Master Shears");
			}
				
			//Set the item lore
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "LVL: " + ChatColor.RESET +
					ChatColor.LIGHT_PURPLE + toolLVL);
			lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "EXP: " + 
					ChatColor.RESET + ChatColor.BLUE + toolEXP + " / " + expToNextLevel);
			lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "EXP: " + 
					MessageManager.percentBar(expPercent) + ChatColor.GRAY + " " + expPercent + "%");
			lore.add(" ");//create blank space
			
			if (lvl <= 19) {
				lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Shears made of wood and dull stone.");
			} else if (lvl > 19 && lvl <= 39) {
				lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Shears made of wood and"); 
				lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "reinforced with sharp stone.");
			} else if (lvl > 29 && lvl <= 59) {
				lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Shears made of wood and"); 
				lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "reinforced with sharp iron.");
			} else if (lvl > 59 && lvl <= 79) {
				lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Shears made of wood and"); 
				lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "reinforced with sharp gold.");
			} else {
				lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Shears made of wood and"); 
				lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "reinforced with sharp diamond.");
			}
			
			//Set the item lore
			im.setLore(lore);
			//Set the item meta
			is.setItemMeta(im);
		}
	}
	
	/**
	 * This creates a new herbalism tool for the player!
	 * 
	 * @param player The player to recieve the tool.
	 */
	private static void createHerbalismTool(Player player) {
		ItemStack tool = player.getInventory().getItemInHand();
		ItemMeta meta = tool.getItemMeta();

		int expGoal = configHerbalismLevel.get(1);
		
		//Set the item name
		meta.setDisplayName(ChatColor.WHITE + "Novice Shears");
		
		//Set the item lore
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "LVL: " + ChatColor.RESET +
				ChatColor.LIGHT_PURPLE + "1");
		lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "EXP: " + 
				ChatColor.RESET + ChatColor.BLUE + "0" + " / " + expGoal);
		lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "EXP: " + 
				MessageManager.percentBar(0) + ChatColor.GRAY + " " + "0" + "%");
		lore.add(" ");//create blank space
		lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "A herbalism tool made of wood.");
		
		//Set the item lore
		meta.setLore(lore);
		//Set the item meta
		tool.setItemMeta(meta);
	}
	
	/**
	 * Calculates how much EXP the player should get from herbalism. 
	 * If the calculation is less than a certain percentage, then the 
	 * herbalism attempt will not be successful and EXP will not be earned.  
	 * Default success rate is 70%.
	 * 
	 * @param multiplier The success rate for the attempt.
	 * @return The experience earned by the player.
	 */
	private static int calculateExpGain(int multiplier){
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
    private static void createHerbalismConfig() {
    	
        configFile = new File(FILE_PATH);
        herbalismConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        //Loop through and create each level for Herbalism configuration.
        for (int i = 1; i <= 100; i++ ) {
        	int y = 167;					//This is the first level.
        	String z = Integer.toString(i);	//Convert i to string for yml format.
        	herbalismConfig.set(z, i * y);		//For every level multiply the first level x the number in the loop.
        }

        try {
        	herbalismConfig.save(configFile);	//Save the file.
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
	
}
