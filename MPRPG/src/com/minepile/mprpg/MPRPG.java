package com.minepile.mprpg;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.minepile.mprpg.chat.ChatManager;
import com.minepile.mprpg.chat.DiceRollManager;
import com.minepile.mprpg.chat.GameTipManager;
import com.minepile.mprpg.chat.LagManager;
import com.minepile.mprpg.chat.MessageManager;
import com.minepile.mprpg.commands.CommandManager;
import com.minepile.mprpg.entities.CitizensManager;
import com.minepile.mprpg.entities.InnKeeperManager;
import com.minepile.mprpg.entities.MonsterCreatorManager;
import com.minepile.mprpg.entities.MonsterManager;
import com.minepile.mprpg.entities.NPCManager;
import com.minepile.mprpg.gui.ChestMenuManager;
import com.minepile.mprpg.gui.PlayerMailManager;
import com.minepile.mprpg.gui.PlayerMenuManager;
import com.minepile.mprpg.guild.GuildManager;
import com.minepile.mprpg.inventory.BankChestManager;
import com.minepile.mprpg.inventory.InventoryRestore;
import com.minepile.mprpg.inventory.InventorySave;
import com.minepile.mprpg.inventory.ShopChestManager;
import com.minepile.mprpg.items.ArmorItemManager;
import com.minepile.mprpg.items.ConsumableItemManager;
import com.minepile.mprpg.items.CurrencyItemManager;
import com.minepile.mprpg.items.ItemGeneratorManager;
import com.minepile.mprpg.items.ItemIdentifierManager;
import com.minepile.mprpg.items.ItemLoreFactory;
import com.minepile.mprpg.items.ItemQualityManager;
import com.minepile.mprpg.items.ItemTierManager;
import com.minepile.mprpg.items.LootTableChestManager;
import com.minepile.mprpg.items.LootTableMobManager;
import com.minepile.mprpg.items.MerchantManager;
import com.minepile.mprpg.items.MiscItemManager;
import com.minepile.mprpg.items.RandomItemFactory;
import com.minepile.mprpg.items.SoulboundManager;
import com.minepile.mprpg.items.WeaponItemManager;
import com.minepile.mprpg.listeners.AsyncPlayerChatListener;
import com.minepile.mprpg.listeners.BlockBreakListener;
import com.minepile.mprpg.listeners.BlockDamageListener;
import com.minepile.mprpg.listeners.BlockPlaceListener;
import com.minepile.mprpg.listeners.CreatureSpawnListener;
import com.minepile.mprpg.listeners.EntityChangeBlockListener;
import com.minepile.mprpg.listeners.EntityCombustListener;
import com.minepile.mprpg.listeners.EntityDamageByEntityListener;
import com.minepile.mprpg.listeners.EntityDamageListener;
import com.minepile.mprpg.listeners.EntityDeathListener;
import com.minepile.mprpg.listeners.EntityExplodeListener;
import com.minepile.mprpg.listeners.EntityRegainHealthListener;
import com.minepile.mprpg.listeners.EntityShootBowListener;
import com.minepile.mprpg.listeners.EntityTargetListener;
import com.minepile.mprpg.listeners.FoodLevelChangeListener;
import com.minepile.mprpg.listeners.InventoryClickListener;
import com.minepile.mprpg.listeners.InventoryCloseListener;
import com.minepile.mprpg.listeners.InventoryOpenListener;
import com.minepile.mprpg.listeners.PlayerDeathListener;
import com.minepile.mprpg.listeners.PlayerDropItemListener;
import com.minepile.mprpg.listeners.PlayerExpChangeListener;
import com.minepile.mprpg.listeners.PlayerFishListener;
import com.minepile.mprpg.listeners.PlayerInteractEntityListener;
import com.minepile.mprpg.listeners.PlayerInteractListener;
import com.minepile.mprpg.listeners.PlayerItemBreakListener;
import com.minepile.mprpg.listeners.PlayerJoinListener;
import com.minepile.mprpg.listeners.PlayerPickupItemListener;
import com.minepile.mprpg.listeners.PlayerQuitListener;
import com.minepile.mprpg.listeners.PlayerRespawnListener;
import com.minepile.mprpg.listeners.PlayerToggleSprintListener;
import com.minepile.mprpg.listeners.ProjectileHitListener;
import com.minepile.mprpg.listeners.WeatherChangeListener;
import com.minepile.mprpg.player.PlayerAttributesManager;
import com.minepile.mprpg.player.PlayerCharacterManager;
import com.minepile.mprpg.player.PlayerHealthTagManager;
import com.minepile.mprpg.player.PlayerHearthStoneManager;
import com.minepile.mprpg.player.PlayerManager;
import com.minepile.mprpg.professions.Alchemy;
import com.minepile.mprpg.professions.Blacksmithing;
import com.minepile.mprpg.professions.Cooking;
import com.minepile.mprpg.professions.Fishing;
import com.minepile.mprpg.professions.Herbalism;
import com.minepile.mprpg.professions.Mining;
import com.minepile.mprpg.world.BlockRegenerationManager;

public class MPRPG extends JavaPlugin {

	public static MPRPG plugin;
	private PluginManager pluginManager;

	//A few setup variables.
	String pluginVersion = "v0.8.3";
	
	@Override
	public void onEnable() {
		this.pluginManager = getServer().getPluginManager();

		//Notify that plugin is starting to load all components
		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[MPRPG] Starting up MinePile:RPG now!");

		//Save config.yml if it doesn't exist. Reload it, if it does.
		if(!(new File("plugins/MPRPG/config.yml")).exists()){
			saveResource("config.yml", false);
		} else {
			reloadConfig();
		}

		/////////////////////////////
		/// Setup Class Instances ///
		/////////////////////////////
		
		//setup gui managers
		//Setting this up first so it will be ready for other classes.
		ChestMenuManager.getInstance().setup(this);

		//setup player manager instances
		PlayerAttributesManager.getInstance().setup(this);
		PlayerCharacterManager.getInstance().setup(this);
		PlayerMailManager.getInstance().setup(this);
		PlayerManager.getInstance().setup(this);
		PlayerHealthTagManager.getInstance().setup(this);
		PlayerHearthStoneManager.getInstance().setup(this);
		PlayerMenuManager.getInstance().setup(this);
		
		
		//setup chat manager instances
		ChatManager.getInstance().setup(this);
		DiceRollManager.getInstance().setup(this);
		GameTipManager.getInstance().setup(this);
		GuildManager.getInstance().setup(this);
		LagManager.getInstance().setup(this);
		MessageManager.getInstance().setup(this);

		//setup entities manager instances
		CitizensManager.getInstance().setup(this);
		InnKeeperManager.getInstance().setup(this);
		ItemIdentifierManager.getInstance().setup(this);
		MonsterCreatorManager.getInstance().setup(this);
		MonsterManager.getInstance().setup(this);
		NPCManager.getInstance().setup(this);
		
		//setup gang manager instances
		GuildManager.getInstance().setup(this);

		//setup inventory manager instances
		BankChestManager.getInstance().setup(this);
		InventoryRestore.getInstance().setup(this);
		InventorySave.getInstance().setup(this);
		ShopChestManager.getInstance().setup(this);

		//setup item manager instances
		ArmorItemManager.getInstance().setup(this);
		ConsumableItemManager.getInstance().setup(this);
		CurrencyItemManager.getInstance().setup(this);
		ItemGeneratorManager.getInstance().setup(this);
		ItemQualityManager.getInstance().setup(this);
		ItemTierManager.getInstance().setup(this);
		LootTableChestManager.getInstance().setup(this);
		LootTableMobManager.getInstance().setup(this);
		ItemLoreFactory.getInstance().setup(this);
		MerchantManager.getInstance().setup(this);
		MiscItemManager.getInstance().setup(this);
		RandomItemFactory.getInstance().setup(this);
		SoulboundManager.getInstance().setup(this);
		WeaponItemManager.getInstance().setup(this);

		//setup profession manager instances
		Alchemy.getInstance().setup(this);
		Blacksmithing.getInstance().setup(this);
		Cooking.getInstance().setup(this);
		Fishing.getInstance().setup(this);
		Herbalism.getInstance().setup(this);
		Mining.getInstance().setup(this);

		//setup world manager instances
		BlockRegenerationManager.getInstance().setup(this);

		
		/////////////////////////////
		/// Setup Event Listeners ///
		/////////////////////////////
		
		pluginManager.registerEvents(new AsyncPlayerChatListener(this), this);

		pluginManager.registerEvents(new BlockBreakListener(this), this);
		pluginManager.registerEvents(new BlockDamageListener(this), this);
		pluginManager.registerEvents(new BlockPlaceListener(this), this);

		pluginManager.registerEvents(new CreatureSpawnListener(this), this);

		pluginManager.registerEvents(new EntityChangeBlockListener(this), this);
		pluginManager.registerEvents(new EntityCombustListener(this), this);
		pluginManager.registerEvents(new EntityDamageByEntityListener(this), this);
		pluginManager.registerEvents(new EntityDamageListener(this), this);
		pluginManager.registerEvents(new EntityDeathListener(this), this);
		pluginManager.registerEvents(new EntityExplodeListener(this), this);
		pluginManager.registerEvents(new EntityRegainHealthListener(this), this);
		pluginManager.registerEvents(new EntityShootBowListener(this), this);
		pluginManager.registerEvents(new EntityTargetListener(this), this);
		
		pluginManager.registerEvents(new FoodLevelChangeListener(this), this);

		pluginManager.registerEvents(new InventoryClickListener(this), this);
		pluginManager.registerEvents(new InventoryCloseListener(this), this);
		pluginManager.registerEvents(new InventoryOpenListener(this), this);

		pluginManager.registerEvents(new PlayerDeathListener(this), this);
		pluginManager.registerEvents(new PlayerDropItemListener(this), this);
		pluginManager.registerEvents(new PlayerExpChangeListener(this), this);
		pluginManager.registerEvents(new PlayerFishListener(this), this);
		pluginManager.registerEvents(new PlayerInteractEntityListener(this), this);
		pluginManager.registerEvents(new PlayerInteractListener(this), this);
		pluginManager.registerEvents(new PlayerItemBreakListener(this), this);
		pluginManager.registerEvents(new PlayerJoinListener(this), this);
		pluginManager.registerEvents(new PlayerPickupItemListener(this), this);
		pluginManager.registerEvents(new PlayerQuitListener(this), this);
		pluginManager.registerEvents(new PlayerRespawnListener(this), this);
		pluginManager.registerEvents(new PlayerToggleSprintListener(this), this);
		
		pluginManager.registerEvents(new ProjectileHitListener(this), this);

		pluginManager.registerEvents(new WeatherChangeListener(this), this);

		//////////////////////
		/// Setup Commands ///
		//////////////////////

		//setting player chat channel
		getCommand("c").setExecutor(new CommandManager(this));

		//get the server lag
		getCommand("lag").setExecutor(new CommandManager(this));

		//get player stats
		getCommand("armorstats").setExecutor(new CommandManager(this));
		getCommand("gethp").setExecutor(new CommandManager(this));

		//commands to define monsters in the world
		getCommand("mm").setExecutor(new CommandManager(this));

		//private message other players
		getCommand("msg").setExecutor(new CommandManager(this));
		getCommand("pm").setExecutor(new CommandManager(this));
		getCommand("tell").setExecutor(new CommandManager(this));

		//send quick reply to the last private message a player had
		getCommand("r").setExecutor(new CommandManager(this));

		//roll a dice
		getCommand("roll").setExecutor(new CommandManager(this));

		//sends operator to the spawn location.
		getCommand("spawn").setExecutor(new CommandManager(this));

		//various commands to manage a guild.
		getCommand("guild").setExecutor(new CommandManager(this));

		//opens a menu so a player can assing attribute points.
		getCommand("stats").setExecutor(new CommandManager(this));

		//this gives the player the help book.
		getCommand("help").setExecutor(new CommandManager(this));

		///////////////////
		/// Startup End ///
		///////////////////

		//Notify that plugin is fully finished loading.
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[MPRPG] Start up has finished for MinePile:RPG!");
	}

	@Override
	public void onDisable() {
		//Show the administrator the plugin closing message.
		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[MPRPG] Shutting down MinePile:RPG!");

		//Reset all plants and blocks that have been picked or mined.
		BlockRegenerationManager.disable();
		
		//Remove any existing Holograms
		BankChestManager.disable();

		//Remove character select holograms
		PlayerCharacterManager.disable();

		//Remove mail holograms
		PlayerMailManager.disable();

		//Disable the citizen manager.
		//This also removes all holograms.
		CitizensManager.disable();
		
		//Remove Alchemy holograms
		Alchemy.disable();

		//Remove Blacksmithing holograms
		Blacksmithing.disable();

		//Save players last health
		PlayerManager.disable();
		
		//Loop through entity list and remove them.
		//This is mainly for clearing mobs on server reload.
		MonsterManager.disable();
		
		////////////////////
		/// Shutdown End ///
		////////////////////

		//Show the administrator that the plugin is finished closing.
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[MPRPG] Shut down of MinePile:RPG is complete!");
	}

	public String getPluginVersion() {
		return pluginVersion;
	}

}