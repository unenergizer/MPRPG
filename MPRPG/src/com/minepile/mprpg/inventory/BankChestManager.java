package com.minepile.mprpg.inventory;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerCharacterManager;

public class BankChestManager {

	//setup instance variables
	public static MPRPG plugin;
	static BankChestManager weaponManagerInstance = new BankChestManager();

	//Holograms
	static Hologram bankHologram0;
	static Hologram bankHologram1;

	//Setup to save 5 bank chest pages.
	private static ConcurrentHashMap<UUID, Inventory> playerBankPage0 = new ConcurrentHashMap<UUID, Inventory>();
	private static ConcurrentHashMap<UUID, Inventory> playerBankPage1 = new ConcurrentHashMap<UUID, Inventory>();
	private static ConcurrentHashMap<UUID, Inventory> playerBankPage2 = new ConcurrentHashMap<UUID, Inventory>();
	private static ConcurrentHashMap<UUID, Inventory> playerBankPage3 = new ConcurrentHashMap<UUID, Inventory>();
	private static ConcurrentHashMap<UUID, Inventory> playerBankPage4 = new ConcurrentHashMap<UUID, Inventory>();

	//Create instance
	public static BankChestManager getInstance() {
		return weaponManagerInstance;
	}

	//Setup BankChestManager.
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

		//Display bank holograms
		setupBankHolograms();
	}

	/**
	 * This will disable this class.
	 */
	public static void disable() {
		removeHolograms();
	}

	/**
	 * This will setup Holograms to go over the players bank (ender-chest). 
	 */
	private static void setupBankHolograms() {
		Location bank01 = new Location(Bukkit.getWorld("world"), 16.5, 81, -5.5);
		Location bank02 = new Location(Bukkit.getWorld("world"), 18.5, 81, -7.5);

		bankHologram0 = HologramsAPI.createHologram(plugin, bank01);
		bankHologram0.appendTextLine(ChatColor.GREEN + "" + ChatColor.BOLD + "Player Stash");

		bankHologram1 = HologramsAPI.createHologram(plugin, bank02);
		bankHologram1.appendTextLine(ChatColor.GREEN + "" + ChatColor.BOLD + "Player Stash");
	}
	
	/**
	 * This will remove the holograms that are over the players bank.
	 */
	private static void removeHolograms() {
		bankHologram0.delete();
		bankHologram1.delete();
	}
	
	/**
	 * This will restore a players bank from a config file when they log-into a character.
	 * 
	 * @param player The player who owns the bank that will be restored.
	 */
	public static void restoreBank(Player player) {
		UUID uuid = player.getUniqueId();
		String playerName = player.getName();
		int bankRows = PlayerCharacterManager.getPlayerConfigInt(player, "economy.bankRows");

		//Make bank page 1 (default page).
		if (bankRows <= 5) {
			int size = bankRows * 9;
			Inventory bank0 = Bukkit.createInventory(player, size, playerName + " - Bank Page 1");

			//Load items
			for (int i = 0; i <= bank0.getSize() - 1; i++) {
				try {
					ItemStack item = InventoryRestore.restoreItemStack(player, "bankPage0", i);
					bank0.setItem(i, item);
				} catch (NullPointerException exc) {}
			}

			//Save inventory in HashMap.
			playerBankPage0.put(uuid, bank0);
		}

		//Make bank page 2.
		if (bankRows > 5 && bankRows <= 10) {
			int size = (bankRows * 9) - 5;
			Inventory bank1 = Bukkit.createInventory(player, size, playerName + " - Bank Page 2");

			//Load items
			for (int i = 0; i <= bank1.getSize() - 1; i++) {
				try {
					ItemStack item = InventoryRestore.restoreItemStack(player, "bankPage1", i);
					bank1.setItem(i, item);
				} catch (NullPointerException exc) {}
			}

			//Save inventory in HashMap.
			playerBankPage0.put(uuid, bank1);
		}

		//Make bank page 3.
		if (bankRows > 10 && bankRows <= 15) {
			int size = (bankRows * 9) - 10;
			Inventory bank2 = Bukkit.createInventory(player, size, playerName + " - Bank Page 3");

			//Load items
			for (int i = 0; i <= bank2.getSize() - 1; i++) {
				try {
					ItemStack item = InventoryRestore.restoreItemStack(player, "bankPage2", i);
					bank2.setItem(i, item);
				} catch (NullPointerException exc) {}
			}

			//Save inventory in HashMap.
			playerBankPage2.put(uuid, bank2);
		}

		//Make bank page 4.
		if (bankRows > 15 && bankRows <= 20) {
			int size = (bankRows * 9) - 15;
			Inventory bank3 = Bukkit.createInventory(player, size, playerName + " - Bank Page 4");

			//Load items
			for (int i = 0; i <= bank3.getSize() - 1; i++) {
				try {
					ItemStack item = InventoryRestore.restoreItemStack(player, "bankPage3", i);
					bank3.setItem(i, item);
				} catch (NullPointerException exc) {}
			}

			//Save inventory in HashMap.
			playerBankPage3.put(uuid, bank3);
		}

		//Make bank page 5.
		if (bankRows > 20 && bankRows <= 25) {
			int size = (bankRows * 9) - 20;
			Inventory bank4 = Bukkit.createInventory(player, size, playerName + " - Bank Page 5");

			//Load items
			for (int i = 0; i <= bank4.getSize() - 1; i++) {
				try {
					ItemStack item = InventoryRestore.restoreItemStack(player, "bankPage4", i);
					bank4.setItem(i, item);
				} catch (NullPointerException exc) {}
			}

			//Save inventory in HashMap.
			playerBankPage4.put(uuid, bank4);
		}
	}
	
	/**
	 * This will save a players bank when the log-out of a character.
	 * 
	 * @param player The player who's bank will be saved.
	 */
	public static void saveBank(Player player) {
		int bankRows = PlayerCharacterManager.getPlayerConfigInt(player, "economy.bankRows");

		//Save the default page.  Page 1.
		if (bankRows <= 5) {
			Inventory bank0 = getBank(player, 0);
			
			for (int i = 0; i <= bank0.getSize() - 1; i++) {
				ItemStack item = bank0.getItem(i);
				InventorySave.saveItemStack(player, "bankPage0", i, item);
			}
		}
		
		//Save page 2.
		if (bankRows > 5 && bankRows <= 10) {
			Inventory bank1 = getBank(player, 0);
			
			for (int i = 0; i <= bank1.getSize() - 1; i++) {
				ItemStack item = bank1.getItem(i);
				InventorySave.saveItemStack(player, "bankPage1", i, item);
			}
		}
		
		//Save page 3.
		if (bankRows > 10 && bankRows <= 15) {
			Inventory bank2 = getBank(player, 0);
			
			for (int i = 0; i <= bank2.getSize() - 1; i++) {
				ItemStack item = bank2.getItem(i);
				InventorySave.saveItemStack(player, "bankPage2", i, item);
			}
		}
		
		//Save page 4.
		if (bankRows > 15 && bankRows <= 20) {
			Inventory bank3 = getBank(player, 0);
			
			for (int i = 0; i <= bank3.getSize() - 1; i++) {
				ItemStack item = bank3.getItem(i);
				InventorySave.saveItemStack(player, "bankPage3", i, item);
			}
		}
		
		//Save page 5.
		if (bankRows > 20 && bankRows <= 25) {
			Inventory bank4 = getBank(player, 0);
			
			for (int i = 0; i <= bank4.getSize() - 1; i++) {
				ItemStack item = bank4.getItem(i);
				InventorySave.saveItemStack(player, "bankPage4", i, item);
			}
		}
	}
	
	/**
	 * This will open the players bank inventory when they click on an ender-chest.
	 * 
	 * @param player The player who is requesting that their inventory be opened.
	 * @param page The inventory page to be opened.
	 * @return Returns the inventory page of the player.
	 */
	public static Inventory getBank(Player player, int page) {
		UUID uuid = player.getUniqueId();

		//Default page 1
		if (page == 0) {
			return playerBankPage0.get(uuid);

		//Page 2	
		} else if (page == 1) {
			return playerBankPage1.get(uuid);

		//Page 3
		} else if (page == 2) {
			return playerBankPage2.get(uuid);

		//Page 4
		} else if (page == 3) {
			return playerBankPage3.get(uuid);

		//Page 5
		} else if (page == 4) {
			return playerBankPage4.get(uuid);

		} else {
			//Something went wrong.
			player.sendMessage(ChatColor.RED + "Could not open bank stash." 
					+ "Please report this error to a moderator and described what happened" + ChatColor.DARK_GRAY + ".");
			return null;
		}
	}
}
