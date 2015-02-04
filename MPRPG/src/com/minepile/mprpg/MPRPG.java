package com.minepile.mprpg;

import java.io.File;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.minepile.mprpg.listeners.BlockBreakListener;
import com.minepile.mprpg.listeners.BlockPlaceListener;
import com.minepile.mprpg.listeners.PlayerJoinListener;
import com.minepile.mprpg.managers.MessageManager;

public class MPRPG extends JavaPlugin {
	
	public static MPRPG plugin;
	private PluginManager pluginManager;
	
	@Override
	public void onEnable() {
		this.pluginManager = getServer().getPluginManager();
		
		getLogger().info("Starting up MinePile:RPG now!");
		
        //Save config.yml if it doesn't exist. Reload it, if it does.
        if(!(new File("plugins/MPRPG/config.yml")).exists()){
            saveResource("config.yml", false);
        } else {
        	reloadConfig();
        }
        
        //setup manager instances
        MessageManager.getInstance().setup(this);
        
        //setup event listeners
        pluginManager.registerEvents(new BlockBreakListener(this), this);
        pluginManager.registerEvents(new BlockPlaceListener(this), this);
        pluginManager.registerEvents(new PlayerJoinListener(this), this);
        
        getLogger().info("Start up has finished for MinePile:RPG!");
	}
	
	@Override
	public void onDisable() {
		//TODO: Use me.
	}
	
}
