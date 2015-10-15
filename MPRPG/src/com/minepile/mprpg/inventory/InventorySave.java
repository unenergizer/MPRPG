package com.minepile.mprpg.inventory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerCharacterManager;

public class InventorySave {

	//setup instance variables
	public static MPRPG plugin;
	static InventorySave inventorySaveInstance = new InventorySave();

	//File paths
	private static String filePathStart = "plugins/MPRPG/inventory/";
	private static String filePathEnd = ".yml";

	//Create instance
	public static InventorySave getInstance() {
		return inventorySaveInstance;
	}

	//Setup InventorySave
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * This will save the contents of an inventory slot to the configuration file.
	 * @param owner The owner of the inventory slot and item we are working with.
	 * @param invType The type of inventory slot. Examples: Armor slot, bank chest slot, player inventory slot, etc.
	 * @param invSlot The slot number we are reading.
	 * @param item The item we are saving. This can be null or air.
	 */
	public static void saveItemStack(Player owner, String invType, int invSlot, ItemStack item) {

		/**
		 * THE BASIC IDEA:
		 * 
		 * CharacterSlot0:  (used for more than one character inventory)
		 *   invType:  (armor, bank chest, player inv, etc)
		 *     invSlot0:
		 *        ItemType:
		 *        ItemName:
		 *        ItemLore:
		 *          -LoreLine0
		 *          -LoreLine1
		 *          -LoreLine2
		 *          -LoreLine3
		 *          -LoreLine4
		 *     invSlot1:
		 *       IntemName:
		 *          -LoreLine0
		 *   invType:
		 *     invSlot0
		 */

		String uuid = owner.getUniqueId().toString();
		String ownerName = owner.getName();
		String characterSlot = Integer.toString(PlayerCharacterManager.getCurrentCharacterSlot(owner));

		File configFile = new File(filePathStart + uuid + filePathEnd);
		FileConfiguration invConfig =  YamlConfiguration.loadConfiguration(configFile);
		invConfig.set("playerName", ownerName);
		
		//YML path inside of file.
		String path = "Character" + characterSlot + "." + invType + "." + "Slot" + invSlot;
		
		//Check to make sure were about to save an item that has meta data.
		if ((item != null) && (item.hasItemMeta())) {
			
			//Check to make sure the item we are saving is not the players compass menu.
			if (!item.getType().equals(Material.COMPASS) || !item.getType().equals(Material.WRITTEN_BOOK)) {
				
				//Get basic item info.
				String itemType = item.getType().name();
				String itemName = item.getItemMeta().getDisplayName();
				int itemData = item.getData().getData();
				int stackSize = item.getAmount();

				//Save basic item info.
				invConfig.set(path + ".type", itemType);
				invConfig.set(path + ".name", itemName);
				invConfig.set(path + ".data", itemData);
				invConfig.set(path + ".amount", stackSize);

				//If the item has lore, lets save it too.
				if (item.getItemMeta().hasLore()) {

					//Get the item's lore.
					List<String> lore = item.getItemMeta().getLore();
					
					invConfig.set(path + ".lore", lore);
				}
			}
		} else if (item != null) {
			//Item has no lore. Save it anyway.
			
			//Get item info
			String itemType = item.getType().name();
			String itemData = item.getData().toString();
			int stackSize = item.getAmount();
			
			//Save item
			invConfig.set(path + ".type", itemType);
			invConfig.set(path + ".data", itemData);
			invConfig.set(path + ".amount", stackSize);
			
		} else {
			//Slot must contain air or be null (empty).
			//Lets remove that slots contents if something was saved before it.
			//This should prevent duplication of items.
			invConfig.set(path, null);
		}

		try {
			invConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
