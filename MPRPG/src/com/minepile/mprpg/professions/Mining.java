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
	
	//This method is called by the BlockBreakEvent if the player has
	//broken an ORE using a Pickaxe.
	public static void addExperience(Player player, Material tool) {
		if (tool.equals(Material.WOOD_PICKAXE)) {
			int expGain = calculateExpGain(10);
			chatMiningMessage(player, expGain);
		} else if (tool.equals(Material.STONE_PICKAXE)) {
			int expGain = calculateExpGain(11);
			chatMiningMessage(player, expGain);
		} else if (tool.equals(Material.IRON_PICKAXE)) {
			int expGain = calculateExpGain(12);
			chatMiningMessage(player, expGain);
		} else if (tool.equals(Material.GOLD_PICKAXE)) {
			int expGain = calculateExpGain(13);
			chatMiningMessage(player, expGain);
		} else if (tool.equals(Material.DIAMOND_PICKAXE)) {
			int expGain = calculateExpGain(14);
			chatMiningMessage(player, expGain);
		} else {
			//This should never happen.
			player.sendMessage(MessageManager.selectMessagePrefix("debug") + "Can not add exp to your tool.");
		}
	}
	
	//Calculates how much EXP the player should get from mining.
	//If the calculation is less than a certain percentage, then
	//the mining attempt will not be successful and EXP will not be
	//earned.  Default success rate is 70%.
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
	
	//Chat Message for mining
	public static void chatMiningMessage(Player player, int expGain) {
		//If expGain is 0, let the user know the mine was not successful.
		//If the expGain is any other number, let them know it was successful.
		if (expGain != 0) {
			//add EXP to tool
			int currentPickEXP = PlayerManager.getPlayerConfig(player, "miningEXP");
			int newEXP = currentPickEXP + expGain;
			int currentPickLVL = PlayerManager.getPlayerConfig(player, "miningLVL");
			int expToNextLevel = configMiningLevel.get(currentPickLVL);
			
			if (newEXP < expToNextLevel) {
				player.sendMessage(MessageManager.showEXPLevel(expGain, newEXP, configMiningLevel.get(currentPickLVL)));
			
				//Save the new EXP total to the user's configuration file.
				PlayerManager.updatePlayerConfig(player, "miningEXP", newEXP);
			} else {
				//level up the players pickaxe.
				player.sendMessage(MessageManager.showEXPLevel(expGain, newEXP, configMiningLevel.get(currentPickLVL)));
				
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
