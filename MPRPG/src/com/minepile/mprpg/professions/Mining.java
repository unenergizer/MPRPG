package com.minepile.mprpg.professions;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.managers.MessageManager;
import com.minepile.mprpg.managers.PlayerManager;

public class Mining {
	
	//setup variables
	public static MPRPG plugin;
	static Mining miningInstance = new Mining();
	
	//hashmap to hold levels in memory.
	static HashMap<Integer, Integer> configMiningLevel = new HashMap<Integer, Integer>();
	
	//Create instance
	public static Mining getInstance() {
		return miningInstance;
	}
	
	//Setup MessageManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		//If mining configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File("plugins/MPRPG/professions/mining.yml")).exists()){
			createMiningConfig();
        } else {
        	//lets load the configuration file.
        	File configFile = new File("plugins/MPRPG/professions/mining.yml");
            FileConfiguration miningConfig =  YamlConfiguration.loadConfiguration(configFile);
            for (int i = 1; i < 100; i++) {
            	String z = Integer.toString(i);
            	int totalEXPforLVL = (int) miningConfig.get(z);
            	configMiningLevel.put(i, totalEXPforLVL);
            }
        }
	}
	
	public static void addExperience(Player player, Material tool) {
		if (tool.equals(Material.WOOD_PICKAXE)) {
			
			int expGain = calculateExpGain(10);
			
			//If expGain is 0, let the user know the mine was not successful.
			//If the expGain is any other number, let them know it was successful.
			if (expGain != 0) {
				//add EXP to tool
				int currentPickEXP = PlayerManager.getPlayerConfig(player, "miningEXP");
				int newEXP = currentPickEXP + expGain;
				int currentPickLVL = PlayerManager.getPlayerConfig(player, "miningLVL");
				int expToNextLevel = configMiningLevel.get(currentPickLVL);
				
				if (newEXP < expToNextLevel) {
					player.sendMessage(showToolLevel(expGain, newEXP, configMiningLevel.get(currentPickLVL)));
				
					//Save the new EXP total to the user's configuration file.
					PlayerManager.updatePlayerConfig(player, "miningEXP", newEXP);
				} else {
					//level up the players pickaxe.
					player.sendMessage(showToolLevel(expGain, newEXP, configMiningLevel.get(currentPickLVL)));
					
					int newPickLVL = currentPickLVL + 1;
					int newPickEXP = newEXP - expToNextLevel;
					PlayerManager.updatePlayerConfig(player, "miningLVL", newPickLVL);
					PlayerManager.updatePlayerConfig(player, "miningEXP", newPickEXP);
					player.sendMessage(MessageManager.selectMessagePrefix("debug") +
							ChatColor.YELLOW + ChatColor.BOLD + "Your pick is now level " + newPickLVL + ".");
				}
			} else {
				//Let user know mining was not successful.
				player.sendMessage(MessageManager.selectMessagePrefix("debug") + "Mining was not successful.");
			}
			
		} else if (tool.equals(Material.STONE_PICKAXE)) {
			player.sendMessage(MessageManager.selectMessagePrefix("debug") + "Adding experience for stone pick.");
		} else if (tool.equals(Material.IRON_PICKAXE)) {
			player.sendMessage(MessageManager.selectMessagePrefix("debug") + "Adding experience for iron pick.");
		} else if (tool.equals(Material.GOLD_PICKAXE)) {
			player.sendMessage(MessageManager.selectMessagePrefix("debug") + "Adding experience for gold pick.");
		} else if (tool.equals(Material.DIAMOND_PICKAXE)) {
			player.sendMessage(MessageManager.selectMessagePrefix("debug") + "Adding experience for diamond pick.");
		} else {
			//This should never happen.
			player.sendMessage(MessageManager.selectMessagePrefix("debug") + "Can not add exp to your tool.");
		}
	}
	
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
	
	public static String showToolLevel(int exp, int currentExp, int expGoal){
		int expPercent = ((100 * currentExp) / expGoal);
	
		return ChatColor.GRAY + "" + ChatColor.BOLD + 
				"        EXP: " + 
				percentBar(expPercent) + 
				ChatColor.GRAY + ChatColor.BOLD + " " + expPercent + "%" + 
				ChatColor.RESET + ChatColor.GRAY + " [" + 
				ChatColor.BLUE + currentExp + " / " + expGoal +
				ChatColor.RESET + ChatColor.GRAY + "] "
				+ ChatColor.GREEN + "+" + ChatColor.GRAY+ exp + " EXP";
	}
	
	private static String percentBar(int expPercent) {
		if (expPercent <= 5) {
			return ChatColor.RED + "" + ChatColor.BOLD + "|" + ChatColor.GRAY + ChatColor.BOLD + "|||||||||||||||||||";
		} else if (expPercent > 5 && expPercent <= 10) {
			return ChatColor.RED + "" + ChatColor.BOLD + "||" + ChatColor.GRAY + ChatColor.BOLD + "||||||||||||||||||";
		} else if (expPercent > 10 && expPercent <= 15) {
			return ChatColor.RED + "" + ChatColor.BOLD + "|||" + ChatColor.GRAY + ChatColor.BOLD + "|||||||||||||||||";
		} else if (expPercent > 15 && expPercent <= 20) {
			return ChatColor.RED + "" + ChatColor.BOLD + "||||" + ChatColor.GRAY + ChatColor.BOLD + "||||||||||||||||";
		} else if (expPercent > 20 && expPercent <= 25) {
			return ChatColor.RED + "" + ChatColor.BOLD + "|||||" + ChatColor.GRAY + ChatColor.BOLD + "|||||||||||||||";
		} else if (expPercent > 25 && expPercent <= 30) {
			return ChatColor.RED + "" + ChatColor.BOLD + "||||||" + ChatColor.GRAY + ChatColor.BOLD + "||||||||||||||";
		} else if (expPercent > 30 && expPercent <= 35) {
			return ChatColor.YELLOW + "" + ChatColor.BOLD + "|||||||" + ChatColor.GRAY + ChatColor.BOLD + "|||||||||||||";
		} else if (expPercent > 35 && expPercent <= 40) {
			return ChatColor.YELLOW + "" + ChatColor.BOLD + "||||||||" + ChatColor.GRAY + ChatColor.BOLD + "||||||||||||";
		} else if (expPercent > 40 && expPercent <= 45) {
			return ChatColor.YELLOW + "" + ChatColor.BOLD + "|||||||||" + ChatColor.GRAY + ChatColor.BOLD + "|||||||||||";
		} else if (expPercent > 45 && expPercent <= 50) {
			return ChatColor.YELLOW + "" + ChatColor.BOLD + "||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "||||||||||";
		} else if (expPercent > 50 && expPercent <= 55) {
			return ChatColor.YELLOW + "" + ChatColor.BOLD + "|||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "|||||||||";
		} else if (expPercent > 55 && expPercent <= 60) {
			return ChatColor.YELLOW + "" + ChatColor.BOLD + "||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "||||||||";
		} else if (expPercent > 60 && expPercent <= 65) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "|||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "|||||||";
		} else if (expPercent > 65 && expPercent <= 70) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "||||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "||||||";
		} else if (expPercent > 70 && expPercent <= 75) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "|||||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "|||||";
		} else if (expPercent > 75 && expPercent <= 80) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "||||||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "||||";
		} else if (expPercent > 80 && expPercent <= 85) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "|||||||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "|||";
		} else if (expPercent > 85 && expPercent <= 90) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "||||||||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "||";
		} else if (expPercent > 90 && expPercent <= 95) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "||||||||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "||";
		} else if (expPercent > 95 && expPercent <= 100) {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "|||||||||||||||||||" + ChatColor.GRAY + ChatColor.BOLD + "|";
		} else {
			return ChatColor.GREEN + "" + ChatColor.BOLD + "||||||||||||||||||||";
		}
	}
	
	//This creates the configuration file that has the EXP leveling requirements.
    public static void createMiningConfig() {
    	
        File configFile = new File("plugins/MPRPG/professions/mining.yml");
        FileConfiguration miningConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        //Loop through and create each level for mining.
        for (int i = 1; i <= 100; i++ ) {
        	int y = 167;					//This is the first level.
        	String z = Integer.toString(i);	//Convert i to string for yml format.
        	miningConfig.set(z, i * y);		//For every level multiply the first level x the number in the loop.
        }

        try {
            miningConfig.save(configFile);	//Save the file.
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

}
