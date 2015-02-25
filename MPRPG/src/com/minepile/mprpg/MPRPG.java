package com.minepile.mprpg;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.minepile.mprpg.commands.CommandManager;
import com.minepile.mprpg.equipment.ArmorManager;
import com.minepile.mprpg.equipment.LoreManager;
import com.minepile.mprpg.equipment.WeaponManager;
import com.minepile.mprpg.inventory.BankChestManager;
import com.minepile.mprpg.inventory.ShopChestManager;
import com.minepile.mprpg.listeners.AsyncPlayerChatListener;
import com.minepile.mprpg.listeners.BlockBreakListener;
import com.minepile.mprpg.listeners.BlockPlaceListener;
import com.minepile.mprpg.listeners.CraftItemListener;
import com.minepile.mprpg.listeners.EntityDamageByEntityListener;
import com.minepile.mprpg.listeners.EntityDamageListener;
import com.minepile.mprpg.listeners.EntityDeathListener;
import com.minepile.mprpg.listeners.EntityRegainHealthListener;
import com.minepile.mprpg.listeners.EntityShootBowListener;
import com.minepile.mprpg.listeners.EntityTargetListener;
import com.minepile.mprpg.listeners.InventoryClickListener;
import com.minepile.mprpg.listeners.InventoryCloseListener;
import com.minepile.mprpg.listeners.InventoryOpenListener;
import com.minepile.mprpg.listeners.PlayerDropItemListener;
import com.minepile.mprpg.listeners.PlayerFishListener;
import com.minepile.mprpg.listeners.PlayerInteractListener;
import com.minepile.mprpg.listeners.PlayerJoinListener;
import com.minepile.mprpg.listeners.PlayerPickupItemListener;
import com.minepile.mprpg.listeners.PlayerQuitListener;
import com.minepile.mprpg.listeners.PlayerRespawnListener;
import com.minepile.mprpg.listeners.WeatherChangeListener;
import com.minepile.mprpg.managers.BlockRegenerationManager;
import com.minepile.mprpg.managers.ChatManager;
import com.minepile.mprpg.managers.LagManager;
import com.minepile.mprpg.managers.MessageManager;
import com.minepile.mprpg.player.PlayerManager;
import com.minepile.mprpg.player.PlayerMenuManager;
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
		Bukkit.getConsoleSender().sendMessage("§b§lStarting up MinePile:RPG now!");
		
        //Save config.yml if it doesn't exist. Reload it, if it does.
        if(!(new File("plugins/MPRPG/config.yml")).exists()){
            saveResource("config.yml", false);
        } else {
        	reloadConfig();
        }
        
        
        /////////////////////////////
        /// Setup Class Instances ///
        /////////////////////////////
        
        //setup manager instances
        BankChestManager.getInstance().setup(this);
        BlockRegenerationManager.getInstance().setup(this);
        ChatManager.getInstance().setup(this);
        LagManager.getInstance().setup(this);
        MessageManager.getInstance().setup(this);
        PlayerManager.getInstance().setup(this);
        PlayerMenuManager.getInstance().setup(this);
        ShopChestManager.getInstance().setup(this);
        
        //setup equipment instances
        ArmorManager.getInstance().setup(this);
        LoreManager.getInstance().setup(this);
        WeaponManager.getInstance().setup(this);
        
        //setup professions (game jobs) instances
        Blacksmithing.getInstance().setup(this);
        Cooking.getInstance().setup(this);
        Fishing.getInstance().setup(this);
        Herbalism.getInstance().setup(this);
        Mining.getInstance().setup(this);
        
        /////////////////////////////
        /// Setup Event Listeners ///
        /////////////////////////////
        
        pluginManager.registerEvents(new AsyncPlayerChatListener(this), this);
        
        pluginManager.registerEvents(new BlockBreakListener(this), this);
        pluginManager.registerEvents(new BlockPlaceListener(this), this);
        
        pluginManager.registerEvents(new CraftItemListener(this), this);
        
        pluginManager.registerEvents(new EntityDamageByEntityListener(this), this);
        pluginManager.registerEvents(new EntityDamageListener(this), this);
        pluginManager.registerEvents(new EntityDeathListener(this), this);
        pluginManager.registerEvents(new EntityRegainHealthListener(this), this);
        pluginManager.registerEvents(new EntityShootBowListener(this), this);
        pluginManager.registerEvents(new EntityTargetListener(this), this);
        
        pluginManager.registerEvents(new InventoryClickListener(this), this);
        pluginManager.registerEvents(new InventoryCloseListener(this), this);
        pluginManager.registerEvents(new InventoryOpenListener(this), this);
        
        pluginManager.registerEvents(new PlayerDropItemListener(this), this);
        pluginManager.registerEvents(new PlayerFishListener(this), this);
        pluginManager.registerEvents(new PlayerInteractListener(this), this);
        pluginManager.registerEvents(new PlayerJoinListener(this), this);
        pluginManager.registerEvents(new PlayerPickupItemListener(this), this);
        pluginManager.registerEvents(new PlayerQuitListener(this), this);
        pluginManager.registerEvents(new PlayerRespawnListener(this), this);
        
        pluginManager.registerEvents(new WeatherChangeListener(this), this);
        
        //////////////////////
        /// Setup Commands ///
        //////////////////////
        
        //setting player chat channel
        getCommand("cc").setExecutor(new CommandManager(this));
        getCommand("ch").setExecutor(new CommandManager(this));
        getCommand("chatchanel").setExecutor(new CommandManager(this));
        getCommand("chat").setExecutor(new CommandManager(this));
        
        //get the server lag
        getCommand("lag").setExecutor(new CommandManager(this));
        
        //get the server lag
        getCommand("armorstats").setExecutor(new CommandManager(this));
        getCommand("lorestats").setExecutor(new CommandManager(this));
        
        //private message other players
        getCommand("msg").setExecutor(new CommandManager(this));
        getCommand("message").setExecutor(new CommandManager(this));
        getCommand("pm").setExecutor(new CommandManager(this));
        getCommand("tell").setExecutor(new CommandManager(this));
        
        //send quick reply to the last private message a player had
        getCommand("r").setExecutor(new CommandManager(this));
        
        //Notify that plugin is fully finished loading.
        Bukkit.getConsoleSender().sendMessage("§b§lStart up has finished for MinePile:RPG!");
	}
	
	@Override
	public void onDisable() {
		//Show the administrator the plugin closing message.
		Bukkit.getConsoleSender().sendMessage("§c§lShutting down MinePile:RPG!");
		
		//Reset all plants and blocks that have been picked or mined.
		BlockRegenerationManager.resetAllBlocks();
		
		//Clear LoreManager Log
		LoreManager.staminaLog.clear();
		
		//Show the administrator that the plugin is finished closing.
		Bukkit.getConsoleSender().sendMessage("§c§lShut down of MinePile:RPG is complete!");
	}
	
}