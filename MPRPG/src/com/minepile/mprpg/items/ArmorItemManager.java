package com.minepile.mprpg.items;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.minepile.mprpg.MPRPG;

public class ArmorItemManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static ArmorItemManager armorItemManagerInstance = new ArmorItemManager();
	static String armorItemsFilePath = "plugins/MPRPG/items/Armor.yml";
	
	//Configuration file that holds armor information.
	FileConfiguration armorItemConfig;
	
	//Create instance
	public static ArmorItemManager getInstance() {
		return armorItemManagerInstance;
	}
	
	//Setup ArmorItemManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		//If configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(armorItemsFilePath)).exists()){
			createConfig();
        } else {
        	//lets load the configuration file.
        	File configFile = new File(armorItemsFilePath);
            armorItemConfig =  YamlConfiguration.loadConfiguration(configFile);
        }
	}	
	
	//This creates the configuration file that will hold data to save armor information.
    private static void createConfig() {
    	
        File configFile = new File(armorItemsFilePath);
        FileConfiguration armorItemsConfig =  YamlConfiguration.loadConfiguration(configFile);

        try {
        	armorItemsConfig.save(configFile);	//Save the file.
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
