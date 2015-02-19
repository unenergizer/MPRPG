package com.minepile.mprpg.inventory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.minepile.mprpg.MPRPG;

public class BankChestManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static BankChestManager weaponManagerInstance = new BankChestManager();
	
	static int maxChestPages = 5;//Change Me
	
	static int slotsPerRow = 9; //Do Not change
	static int rowsPerChest = 6;//Do Not change
	static int maxBankSlots = slotsPerRow * rowsPerChest * maxChestPages;
	static int maxBankRows = maxChestPages * rowsPerChest;
	
	public static HashMap<Integer, Integer> configBankCosts = new HashMap<Integer, Integer>();
	
	//Create instance
	public static BankChestManager getInstance() {
		return weaponManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		//If bank costs configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File("plugins/MPRPG/economy/bankChestPrice.yml")).exists()){
			createBankCostsConfig();
        } else {
        	//lets load the configuration file.
        	File configFile = new File("plugins/MPRPG/economy/bankChestPrice.yml");
            FileConfiguration bankConfig =  YamlConfiguration.loadConfiguration(configFile);
            for (int i = 1; i < maxBankRows; i++) {
            	String z = Integer.toString(i);
            	int totalEXPforLVL = (int) bankConfig.get(z);
            	configBankCosts.put(i, totalEXPforLVL);
            }
        }
	}

	public static Inventory getBank(Player player) {
		// TODO Auto-generated method stub
		return null;
	}
	
    public static void createPlayerBankConfig(Player player) {
     	
    	String uuid = player.getUniqueId().toString();
    	
        File configFile = new File("plugins/MPRPG/players/bank/" + uuid + ".yml");
        FileConfiguration playerConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        for (int i = 0; i <= maxBankSlots; i++) {
        	
        	playerConfig.set(Integer.toString(i), "");
        }
       

        try {
            playerConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
	
	//This creates the configuration file that has the bank purchasing requirements.
    public static void createBankCostsConfig() {
    	
        File configFile = new File("plugins/MPRPG/economy/bankChestPrice.yml");
        FileConfiguration bankConfig =  YamlConfiguration.loadConfiguration(configFile);
        
        //Loop through and create each level for mining.
        for (int i = 1; i <= maxBankRows; i++ ) {
        	int y = 200;					//This is the first level.
        	String z = Integer.toString(i);	//Convert i to string for yml format.
        	bankConfig.set(z, i * y * 3);	//For every level multiply the first level x the number in the loop.
        }

        try {
            bankConfig.save(configFile);	//Save the file.
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
