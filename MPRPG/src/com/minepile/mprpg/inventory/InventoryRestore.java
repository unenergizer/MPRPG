package com.minepile.mprpg.inventory;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerCharacterManager;

public class InventoryRestore {

	//setup instance variables
	public static MPRPG plugin;
	static InventoryRestore inventoryRestoreInstance = new InventoryRestore();

	//File paths
	private static String filePathStart = "plugins/MPRPG/inventory/";
	private static String filePathEnd = ".yml";

	//Create instance
	public static InventoryRestore getInstance() {
		return inventoryRestoreInstance;
	}

	//Setup InventoryRestore
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}

	public static ItemStack restoreItemStack(Player owner, String invType, int invSlot) {

		//Get basic info.
		String uuid = owner.getUniqueId().toString();
		String characterSlot = Integer.toString(PlayerCharacterManager.getCurrentCharacterSlot(owner));

		//Load the file.
		File configFile = new File(filePathStart + uuid + filePathEnd);
		FileConfiguration itemConfig =  YamlConfiguration.loadConfiguration(configFile);

		//YML path inside of file.
		String path = "Character" + characterSlot + "." + invType + "." + "Slot" + invSlot;

		//Get values to make ItemStack
		String itemType = itemConfig.getString(path + ".type");
		String itemName = itemConfig.getString(path + ".name");
		//String itemData = itemConfig.getString(path + ".data").replaceAll("[^\\d.]", "");
		int itemAmount = itemConfig.getInt(path + ".amount");
		@SuppressWarnings("unchecked")
		List<String> itemLore = (List<String>) itemConfig.getList(path + ".lore");

		////////// BUILD ITEM /////////////
		Material itemMaterial = Material.getMaterial(itemType);
		//MaterialData data = new MaterialData(); //Integer.parseInt(itemData)
		ItemStack item = new ItemStack(itemMaterial, 1);
		ItemMeta im = item.getItemMeta();

		//Set the items Name
		im.setDisplayName(itemName);
		//Set the item lore
		im.setLore(itemLore);
		//Set the item meta
		item.setItemMeta(im);
		//item.setData(data);
		item.setAmount(itemAmount);

		return item;
	}

}
