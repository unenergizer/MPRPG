package com.minepile.mprpg.professions;

import io.puharesource.mc.titlemanager.api.ActionbarTitleObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.chat.MessageManager;
import com.minepile.mprpg.gui.ChestMenuManager;
import com.minepile.mprpg.player.PlayerCharacterManager;

public class Mining {
	
	//setup instance variables
	public static MPRPG plugin;
	static Mining miningInstance = new Mining();
	static String FILE_PATH = "plugins/MPRPG/professions/mining.yml";
	
	static File configFile;
    static FileConfiguration miningConfig;
	
    public static Inventory menu;
	
	//hashmap to hold levels in memory.
	static HashMap<Integer, Integer> configMiningLevel = new HashMap<Integer, Integer>();
	
	//Create instance
	public static Mining getInstance() {
		return miningInstance;
	}
	
	//Setup MinigManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		//If mining configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(FILE_PATH)).exists()){
			createMiningConfig();
        } else {
        	//lets load the configuration file.
        	configFile = new File(FILE_PATH);
            miningConfig =  YamlConfiguration.loadConfiguration(configFile);
            for (int i = 1; i < 100; i++) {
            	String z = Integer.toString(i);
            	int totalEXPforLVL = (int) miningConfig.get(z);
            	configMiningLevel.put(i, totalEXPforLVL);
            }
        }

		//Create npc menu
		createMenu();
	}	
	
	/**
	 * This will build the menus for use with NPC's.
	 */
    private void createMenu() {
		String pageName = "Mining_Trainer";
    	menu = ChestMenuManager.buildMenuPage(null, pageName);
	}
    /**
     * This will be toggled when a player left-clicks or right clicks a player.
     * 
     * @param player The player who clicked the NPC.
     */
	public static void toggleCitizenInteract(Player player) {
		player.openInventory(menu);
	}
	
	/**
	 * This method is called by the BlockBreakEvent if the player has broken an ORE using a Pickaxe.
	 * 
	 * @param player The player who mined a block.
	 * @param tool The player's pickaxe.
	 * @param ore The ore the player mined with the pickaxe.
	 */
	public static void toggleOreMined(Player player, Material tool, Material ore) {

		ItemStack is = player.getInventory().getItemInHand();
		ItemMeta im = is.getItemMeta();
		List<String> cointainsLore = im.getLore();
		
		//If the pickaxe does not have lore, lets create a new pickaxe.
		if (cointainsLore == null) {
			createPickaxe(player);
		}
		
		//Now that we know the pickaxe exists, lets get it's data.
		int currentPickLVL = getLoreLVL(player);
		int currentPickEXP = getLoreEXP(player);
		
		
		if (tool.equals(Material.WOOD_PICKAXE)) {
			
			//Lets calculate how much EXP we need to add.
			int expGain = calculateExpGain(10);

			//If expGain is no 0, lets:
			//1) Add the ore mined to the players inventory.
			//2) Update the players pickaxe information.
			if (expGain != 0) {
				player.getInventory().addItem(new ItemStack(ore, 1));
				togglePickUpdate(player, tool, expGain, currentPickEXP, currentPickLVL);
			} else {
				//Let user know mining was not successful.
				new ActionbarTitleObject(ChatColor.GRAY + "" + ChatColor.ITALIC + "Mining was not successful.").send(player);
			}
		} else if (tool.equals(Material.STONE_PICKAXE)) {
			if (ore.equals(Material.COAL_ORE)) {
				//Lets calculate how much EXP we need to add.
				int expGain = calculateExpGain(9);

				//If expGain is no 0, lets:
				//1) Add the ore mined to the players inventory.
				//2) Update the players pickaxe information.
				if (expGain != 0) {
					player.getInventory().addItem(new ItemStack(ore, 1));
					togglePickUpdate(player, tool, expGain, currentPickEXP, currentPickLVL);
				} else {
					//Let user know mining was not successful.
					new ActionbarTitleObject(ChatColor.GRAY + "" + ChatColor.ITALIC + "Mining was not successful.").send(player);
				}
				
			} else { //Ore mined must be iron ore.
				//Lets calculate how much EXP we need to add.
				int expGain = calculateExpGain(10);

				//If expGain is no 0, lets:
				//1) Add the ore mined to the players inventory.
				//2) Update the players pickaxe information.
				if (expGain != 0) {
					player.getInventory().addItem(new ItemStack(ore, 1));
					togglePickUpdate(player, tool, expGain, currentPickEXP, currentPickLVL);
				} else {
					//Let user know mining was not successful.
					new ActionbarTitleObject(ChatColor.GRAY + "" + ChatColor.ITALIC + "Mining was not successful.").send(player);
				}
			}
		} else if (tool.equals(Material.IRON_PICKAXE)) {
			if (ore.equals(Material.COAL_ORE)) {
				//Lets calculate how much EXP we need to add.
				int expGain = calculateExpGain(8);

				//If expGain is no 0, lets:
				//1) Add the ore mined to the players inventory.
				//2) Update the players pickaxe information.
				if (expGain != 0) {
					player.getInventory().addItem(new ItemStack(ore, 1));
					togglePickUpdate(player, tool, expGain, currentPickEXP, currentPickLVL);
				} else {
					//Let user know mining was not successful.
					new ActionbarTitleObject(ChatColor.GRAY + "" + ChatColor.ITALIC + "Mining was not successful.").send(player);
				}
				
			} else if (ore.equals(Material.IRON_ORE)){ //Ore mined must be iron ore.
				//Lets calculate how much EXP we need to add.
				int expGain = calculateExpGain(9);

				//If expGain is no 0, lets:
				//1) Add the ore mined to the players inventory.
				//2) Update the players pickaxe information.
				if (expGain != 0) {
					player.getInventory().addItem(new ItemStack(ore, 1));
					togglePickUpdate(player, tool, expGain, currentPickEXP, currentPickLVL);
				} else {
					//Let user know mining was not successful.
					new ActionbarTitleObject(ChatColor.GRAY + "" + ChatColor.ITALIC + "Mining was not successful.").send(player);
				}
			} else { //Ore mined must be emerald ore.
				//Lets calculate how much EXP we need to add.
				int expGain = calculateExpGain(10);

				//If expGain is no 0, lets:
				//1) Add the ore mined to the players inventory.
				//2) Update the players pickaxe information.
				if (expGain != 0) {
					player.getInventory().addItem(new ItemStack(ore, 1));
					togglePickUpdate(player, tool, expGain, currentPickEXP, currentPickLVL);
				} else {
					//Let user know mining was not successful.
					new ActionbarTitleObject(ChatColor.GRAY + "" + ChatColor.ITALIC + "Mining was not successful.").send(player);
				}
			}
		} else if (tool.equals(Material.GOLD_PICKAXE)) {
			if (ore.equals(Material.COAL_ORE)) {
				//Lets calculate how much EXP we need to add.
				int expGain = calculateExpGain(9);

				//If expGain is no 0, lets:
				//1) Add the ore mined to the players inventory.
				//2) Update the players pickaxe information.
				if (expGain != 0) {
					player.getInventory().addItem(new ItemStack(ore, 1));
					togglePickUpdate(player, tool, expGain, currentPickEXP, currentPickLVL);
				} else {
					//Let user know mining was not successful.
					new ActionbarTitleObject(ChatColor.GRAY + "" + ChatColor.ITALIC + "Mining was not successful.").send(player);
				}
				
			} else if (ore.equals(Material.IRON_ORE)){ //Ore mined must be iron ore.
				//Lets calculate how much EXP we need to add.
				int expGain = calculateExpGain(10);

				//If expGain is no 0, lets:
				//1) Add the ore mined to the players inventory.
				//2) Update the players pickaxe information.
				if (expGain != 0) {
					player.getInventory().addItem(new ItemStack(ore, 1));
					togglePickUpdate(player, tool, expGain, currentPickEXP, currentPickLVL);
				} else {
					//Let user know mining was not successful.
					new ActionbarTitleObject(ChatColor.GRAY + "" + ChatColor.ITALIC + "Mining was not successful.").send(player);
				}
			} else if (ore.equals(Material.EMERALD_ORE)){ //Ore mined must be iron ore.
				//Lets calculate how much EXP we need to add.
				int expGain = calculateExpGain(11);

				//If expGain is no 0, lets:
				//1) Add the ore mined to the players inventory.
				//2) Update the players pickaxe information.
				if (expGain != 0) {
					player.getInventory().addItem(new ItemStack(ore, 1));
					togglePickUpdate(player, tool, expGain, currentPickEXP, currentPickLVL);
				} else {
					//Let user know mining was not successful.
					new ActionbarTitleObject(ChatColor.GRAY + "" + ChatColor.ITALIC + "Mining was not successful.").send(player);
				}
			} else { //Ore mined must be gold ore.
				//Lets calculate how much EXP we need to add.
				int expGain = calculateExpGain(12);

				//If expGain is no 0, lets:
				//1) Add the ore mined to the players inventory.
				//2) Update the players pickaxe information.
				if (expGain != 0) {
					player.getInventory().addItem(new ItemStack(ore, 1));
					togglePickUpdate(player, tool, expGain, currentPickEXP, currentPickLVL);
				} else {
					//Let user know mining was not successful.
					new ActionbarTitleObject(ChatColor.GRAY + "" + ChatColor.ITALIC + "Mining was not successful.").send(player);
				}
			}
		} else if (tool.equals(Material.DIAMOND_PICKAXE)) {
			if (ore.equals(Material.COAL_ORE)) {
				//Lets calculate how much EXP we need to add.
				int expGain = calculateExpGain(8);

				//If expGain is no 0, lets:
				//1) Add the ore mined to the players inventory.
				//2) Update the players pickaxe information.
				if (expGain != 0) {
					player.getInventory().addItem(new ItemStack(ore, 1));
					togglePickUpdate(player, tool, expGain, currentPickEXP, currentPickLVL);
				} else {
					//Let user know mining was not successful.
					new ActionbarTitleObject(ChatColor.GRAY + "" + ChatColor.ITALIC + "Mining was not successful.").send(player);
				}
				
			} else if (ore.equals(Material.IRON_ORE)){ //Ore mined must be iron ore.
				//Lets calculate how much EXP we need to add.
				int expGain = calculateExpGain(9);

				//If expGain is no 0, lets:
				//1) Add the ore mined to the players inventory.
				//2) Update the players pickaxe information.
				if (expGain != 0) {
					player.getInventory().addItem(new ItemStack(ore, 1));
					togglePickUpdate(player, tool, expGain, currentPickEXP, currentPickLVL);
				} else {
					//Let user know mining was not successful.
					new ActionbarTitleObject(ChatColor.GRAY + "" + ChatColor.ITALIC + "Mining was not successful.").send(player);
				}
			} else if (ore.equals(Material.EMERALD_ORE)){ //Ore mined must be iron ore.
				//Lets calculate how much EXP we need to add.
				int expGain = calculateExpGain(10);

				//If expGain is no 0, lets:
				//1) Add the ore mined to the players inventory.
				//2) Update the players pickaxe information.
				if (expGain != 0) {
					player.getInventory().addItem(new ItemStack(ore, 1));
					togglePickUpdate(player, tool, expGain, currentPickEXP, currentPickLVL);
				} else {
					//Let user know mining was not successful.
					new ActionbarTitleObject(ChatColor.GRAY + "" + ChatColor.ITALIC + "Mining was not successful.").send(player);
				}
			} else if (ore.equals(Material.GOLD_ORE)){ //Ore mined must be iron ore.
				//Lets calculate how much EXP we need to add.
				int expGain = calculateExpGain(11);

				//If expGain is no 0, lets:
				//1) Add the ore mined to the players inventory.
				//2) Update the players pickaxe information.
				if (expGain != 0) {
					player.getInventory().addItem(new ItemStack(ore, 1));
					togglePickUpdate(player, tool, expGain, currentPickEXP, currentPickLVL);
				} else {
					//Let user know mining was not successful.
					new ActionbarTitleObject(ChatColor.GRAY + "" + ChatColor.ITALIC + "Mining was not successful.").send(player);
				}
			} else { //Ore mined must be diamond ore.
				//Lets calculate how much EXP we need to add.
				int expGain = calculateExpGain(12);

				//If expGain is no 0, lets:
				//1) Add the ore mined to the players inventory.
				//2) Update the players pickaxe information.
				if (expGain != 0) {
					player.getInventory().addItem(new ItemStack(ore, 1));
					togglePickUpdate(player, tool, expGain, currentPickEXP, currentPickLVL);
				} else {
					//Let user know mining was not successful.
					new ActionbarTitleObject(ChatColor.GRAY + "" + ChatColor.ITALIC + "Mining was not successful.").send(player);
				}
			}
		} else {
			//This should never happen.
			player.sendMessage(MessageManager.selectMessagePrefix("debug") + "Can not add exp to your tool.");
		}
	}
	
	/**
	 * Updates a players pickaxe with new statistical information.
	 * 
	 * @param player The player with the tool to update.
	 * @param tool The tool that is going to be updated.
	 * @param expGain The experience the tool has earned.
	 * @param currentPickEXP The current experience the tool has before its updated.
	 * @param currentPickLVL The current level the tool has before its updated.
	 */
	public static void togglePickUpdate(Player player, Material tool, int expGain, int currentPickEXP, int currentPickLVL) {
		
		//Additional variables.
		int totalEXP = currentPickEXP + expGain;
		int expGoal = configMiningLevel.get(currentPickLVL);
		
		//If the players pickaxe has leveled up, update the lore, and show a message.
		if (totalEXP > expGoal) {
			//The pick has leveled. Lets add 1 level to it.
			int newPickLVL = currentPickLVL + 1;
			int getLeftOverEXP = totalEXP - expGoal;
			
			if (newPickLVL == 20 || newPickLVL == 40 || newPickLVL == 60 || newPickLVL == 80) {

				ItemStack currentTool = player.getItemInHand();
				
				if (newPickLVL == 20) {
					currentTool.setType(Material.STONE_PICKAXE);
					Material newTool = Material.STONE_PICKAXE;
					//Update the items meta and add 1 level.
					setLore(player, newTool, getLeftOverEXP, newPickLVL);
				} else if (newPickLVL == 40) {
					currentTool.setType(Material.IRON_PICKAXE);
					Material newTool = Material.IRON_PICKAXE;
					//Update the items meta and add 1 level.
					setLore(player, newTool, getLeftOverEXP, newPickLVL);
				} else if (newPickLVL == 60) {
					currentTool.setType(Material.GOLD_PICKAXE);
					Material newTool = Material.GOLD_PICKAXE;
					//Update the items meta and add 1 level.
					setLore(player, newTool, getLeftOverEXP, newPickLVL);
				} else if (newPickLVL == 80) {
					currentTool.setType(Material.DIAMOND_PICKAXE);
					Material newTool = Material.DIAMOND_PICKAXE;
					//Update the items meta and add 1 level.
					setLore(player, newTool, getLeftOverEXP, newPickLVL);
				}
				
				//Send EXP up message.
				new ActionbarTitleObject(MessageManager.showEXPLevel(expGain, totalEXP, expGoal)).send(player);
				
				//Send level up message.
				player.sendMessage(ChatColor.GREEN + "Your pick is now level " + ChatColor.GOLD + newPickLVL + ChatColor.GREEN + ".");
				
				//Play a level up sound.
				player.playSound(player.getLocation(), Sound.FIREWORK_TWINKLE2, 1F, 1F);
			} else {
				
				//Update the items meta and add 1 level.
				setLore(player, tool, getLeftOverEXP, newPickLVL);
				
				//Send EXP up message.
				new ActionbarTitleObject(MessageManager.showEXPLevel(expGain, totalEXP, expGoal)).send(player);
				
				//Send level up message.
				player.sendMessage(ChatColor.GREEN + "Your pick is now level " + ChatColor.GOLD + newPickLVL + ChatColor.GREEN + ".");
				
				//Play a level up sound.
				player.playSound(player.getLocation(), Sound.FIREWORK_TWINKLE2, 1F, 1F);
			}
		} else { //The players pickaxe has not leveled up.  Just add exp to it.
			//Update the items meta and add 1 level.
			setLore(player, tool, totalEXP, currentPickLVL);
			
			//Send EXP up message.
			if (PlayerCharacterManager.getPlayerConfigBoolean(player, "setting.chat.professionDebug") == true) {
				new ActionbarTitleObject(MessageManager.showEXPLevel(expGain, totalEXP, expGoal)).send(player);
			}
		}
	}
	
	/**
	 * Gets the tools current experience.
	 * 
	 * @param player The player with the tool to get the experience from.
	 * @return The current tools experience count.
	 */
	private static int getLoreEXP(Player player) {
		ItemStack item = player.getItemInHand();
		
		// I'm not really sure if this could ever be null
		if (item != null) {
			ItemMeta meta = item.getItemMeta();
			
			// This can definitely be null, if the item has no metadata. (lore/enchantments/display name)
			if (meta != null) {
				// This part is probably never null...
				List<String> lore = meta.getLore();
				
				if (lore != null) {
					String loreLineText = lore.get(1);
					
					String[] parts = loreLineText.split("EXP: " + ChatColor.RESET + ChatColor.BLUE);
					String part2 = parts[1];
					
					String[] getEXP = part2.split(" / ");
					String part3 = getEXP[0];

					int parseInt = Integer.parseInt(part3);
					return parseInt;
					
				}
			} else {
				return 0;
			}
		}
		return 0;
	}
	
	/**
	 * Gets the tools current level.
	 * 
	 * @param player The player with the tool to get the level from.
	 * @return The current tools level.
	 */
	private static int getLoreLVL(Player player) {
		ItemStack item = player.getItemInHand();
		
		// I'm not really sure if this could ever be null
		if (item != null) {
			ItemMeta meta = item.getItemMeta();
			
			// This can definitely be null, if the item has no metadata. (lore/enchantments/display name)
			if (meta != null) {
				// This part is probably never null...
				List<String> lore = meta.getLore();
				
				if (lore != null) {
					String loreLineText = lore.get(0);
					
					String[] parts = loreLineText.split("LVL: " + ChatColor.RESET + ChatColor.LIGHT_PURPLE);
					String part2 = parts[1];
					
					int parseInt = Integer.parseInt(part2);
					return parseInt;
					
				}
			} else {
				return 1;
			}
		}
		return 1;
	}
	
	/**
	 * Sets lore on the players tool.
	 * <p>
	 * This is very useful information for the player.
	 * 
	 * @param player The player with the tool.
	 * @param tool The tool to have lore set to it.
	 * @param exp The experience to put on the tool.
	 * @param lvl The level to put on the tool.
	 */
	private static void setLore(Player player, Material tool, int exp, int lvl) {
		
		ItemStack is = player.getInventory().getItemInHand();
		ItemMeta im = is.getItemMeta();
		List<String> cointainsLore = im.getLore();
		
		if (cointainsLore == null) {
			createPickaxe(player);
		} else {
			int pickEXP = exp;
			int pickLVL = lvl;
			int expToNextLevel = configMiningLevel.get(pickLVL);
			int expPercent = ((100 * pickEXP) / expToNextLevel);
				
			//Set the item name
			if (tool.equals(Material.WOOD_PICKAXE)) {
				im.setDisplayName(ChatColor.WHITE + "Novice Pickaxe");
			} else if (tool.equals(Material.STONE_PICKAXE)) {
				im.setDisplayName(ChatColor.GREEN + "Apprentice Pickaxe");
			} else if (tool.equals(Material.IRON_PICKAXE)) {
				im.setDisplayName(ChatColor.BLUE + "Adept Pickaxe");
			} else if (tool.equals(Material.GOLD_PICKAXE)) {
				im.setDisplayName(ChatColor.DARK_PURPLE + "Expert Pickaxe");
			} else {
				im.setDisplayName(ChatColor.GOLD + "Master Pickaxe");
			}
				
			//Set the item lore
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "LVL: " + ChatColor.RESET +
					ChatColor.LIGHT_PURPLE + pickLVL);
			lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "EXP: " + 
					ChatColor.RESET + ChatColor.BLUE + pickEXP + " / " + expToNextLevel);
			lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "EXP: " + 
					MessageManager.percentBar(expPercent) + ChatColor.GRAY + " " + expPercent + "%");
			lore.add(" ");//create blank space
			
			if (tool.equals(Material.WOOD_PICKAXE)) {
				lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "A pickaxe made of wood.");
			} else if (tool.equals(Material.STONE_PICKAXE)) {
				lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "A pickaxe made of stone.");
			} else if (tool.equals(Material.IRON_PICKAXE)) {
				lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "A pickaxe made of iron.");
			} else if (tool.equals(Material.GOLD_PICKAXE)) {
				lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "A pickaxe made of gold.");
			} else {
				lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "A pickaxe made of diamond.");
			}
			
			//Set the item lore
			im.setLore(lore);
			//Set the item meta
			is.setItemMeta(im);
		}
	}
	
	/**
	 * This creates a new pickaxe for the player!
	 * 
	 * @param player The player to create the tool for.
	 */
	private static void createPickaxe(Player player) {
		ItemStack tool = player.getInventory().getItemInHand();
		ItemMeta meta = tool.getItemMeta();

		int expGoal = configMiningLevel.get(1);
		
		//Set the item name
		meta.setDisplayName(ChatColor.WHITE + "Novice Pickaxe");
		
		//Set the item lore
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "LVL: " + ChatColor.RESET +
				ChatColor.LIGHT_PURPLE + "1");
		lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "EXP: " + 
				ChatColor.RESET + ChatColor.BLUE + "0" + " / " + expGoal);
		lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "EXP: " + 
				MessageManager.percentBar(0) + ChatColor.GRAY + " " + "0" + "%");
		lore.add(" ");//create blank space
		lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "A pickaxe made of wood.");
		
		//Set the item lore
		meta.setLore(lore);
		//Set the item meta
		tool.setItemMeta(meta);
	}
	
	/**
	 * Calculates how much EXP the player should get from mining. 
	 * If the calculation is less than a certain percentage, then the 
	 * mining attempt will not be successful and EXP will not be earned.  
	 * Default success rate is 70%.
	 * 
	 * @param multiplier The success rate of a mining attempt.
	 * @return The experience gained from a mining attempt.
	 */
	private static int calculateExpGain(int multiplier){
		double random = (Math.random() * 10);
		if(random > 3) {
			//return successful mine
			return (int) ((int)random * multiplier);
		} else {
			//mine was not successful
			return 0;
		}
	}
	
	/**
	 * This creates the configuration file that has the EXP leveling requirements.
	 */
    private static void createMiningConfig() {
    	
        configFile = new File(FILE_PATH);
        miningConfig =  YamlConfiguration.loadConfiguration(configFile);
        
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
