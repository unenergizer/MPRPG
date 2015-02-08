package com.minepile.mprpg;

import java.io.File;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.minepile.mprpg.equipment.ArmorManager;
import com.minepile.mprpg.equipment.WeaponManager;
import com.minepile.mprpg.listeners.BlockBreakListener;
import com.minepile.mprpg.listeners.BlockPlaceListener;
import com.minepile.mprpg.listeners.EntityDamageByEntityListener;
import com.minepile.mprpg.listeners.EntityDamageListener;
import com.minepile.mprpg.listeners.EntityRegainHealthListener;
import com.minepile.mprpg.listeners.PlayerJoinListener;
import com.minepile.mprpg.listeners.PlayerQuitListener;
import com.minepile.mprpg.managers.MessageManager;
import com.minepile.mprpg.managers.PlayerManager;
import com.minepile.mprpg.professions.Blacksmithing;
import com.minepile.mprpg.professions.Cooking;
import com.minepile.mprpg.professions.Fishing;
import com.minepile.mprpg.professions.Herbalism;
import com.minepile.mprpg.professions.Mining;

public class MPRPG extends JavaPlugin {
	
	public static MPRPG plugin;
	private PluginManager pluginManager;
	
	@Override
	public void onEnable() {
		this.pluginManager = getServer().getPluginManager();
		
		//Notify that plugin is starting to load all components
		getLogger().info("Starting up MinePile:RPG now!");
		
        //Save config.yml if it doesn't exist. Reload it, if it does.
        if(!(new File("plugins/MPRPG/config.yml")).exists()){
            saveResource("config.yml", false);
        } else {
        	reloadConfig();
        }
        
        //setup manager instances
        MessageManager.getInstance().setup(this);
        PlayerManager.getInstance().setup(this);
        
        //setup equipment instances
        ArmorManager.getInstance().setup(this);
        WeaponManager.getInstance().setup(this);
        
        //setup professions (game jobs) instances
        Blacksmithing.getInstance().setup(this);
        Cooking.getInstance().setup(this);
        Fishing.getInstance().setup(this);
        Herbalism.getInstance().setup(this);
        Mining.getInstance().setup(this);
        
        //setup event listeners
        pluginManager.registerEvents(new BlockBreakListener(this), this);
        pluginManager.registerEvents(new BlockPlaceListener(this), this);
        pluginManager.registerEvents(new EntityDamageByEntityListener(this), this);
        pluginManager.registerEvents(new EntityDamageListener(this), this);
        pluginManager.registerEvents(new EntityRegainHealthListener(this), this);
        pluginManager.registerEvents(new PlayerJoinListener(this), this);
        pluginManager.registerEvents(new PlayerQuitListener(this), this);
        
        //Notify that plugin is fully finished loading.
        getLogger().info("Start up has finished for MinePile:RPG!");
	}
	
	@Override
	public void onDisable() {
		//TODO: Use me.
		
	}
	
}
