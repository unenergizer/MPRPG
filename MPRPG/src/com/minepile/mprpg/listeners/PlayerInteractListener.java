package com.minepile.mprpg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.gui.PlayerMailManager;
import com.minepile.mprpg.gui.PlayerMenuManager;
import com.minepile.mprpg.inventory.BankChestManager;
import com.minepile.mprpg.items.ItemLoreFactory;
import com.minepile.mprpg.items.LootTableChestManager;
import com.minepile.mprpg.player.PlayerCharacterManager;
import com.minepile.mprpg.professions.Alchemy;
import com.minepile.mprpg.professions.Blacksmithing;
import com.minepile.mprpg.professions.Cooking;

public class PlayerInteractListener implements Listener{

	public static MPRPG plugin;

	@SuppressWarnings("unused")
	private static int taskID; 

	@SuppressWarnings("static-access")
	public PlayerInteractListener(MPRPG plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		Block block = event.getClickedBlock();

		if ((event.getAction().equals(Action.RIGHT_CLICK_AIR)) || (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			if (event.getItem() != null) {

				switch(event.getItem().getType()) {

				///////////
				// Armor //
				///////////

				//Tier #1
				case LEATHER_BOOTS:
					delayedSlotUpdate(player);
					break;
				case LEATHER_CHESTPLATE:
					delayedSlotUpdate(player);
					break;
				case LEATHER_HELMET:
					delayedSlotUpdate(player);
					break;
				case LEATHER_LEGGINGS:
					delayedSlotUpdate(player);
					break;

					//Tier #2	
				case CHAINMAIL_BOOTS:
					delayedSlotUpdate(player);
					break;
				case CHAINMAIL_CHESTPLATE:
					delayedSlotUpdate(player);
					break;
				case CHAINMAIL_HELMET:
					delayedSlotUpdate(player);
					break;
				case CHAINMAIL_LEGGINGS:
					delayedSlotUpdate(player);
					break;

					//Tier #3	
				case IRON_BOOTS:
					delayedSlotUpdate(player);
					break;
				case IRON_CHESTPLATE:
					delayedSlotUpdate(player);
					break;
				case IRON_HELMET:
					delayedSlotUpdate(player);
					break;
				case IRON_LEGGINGS:
					delayedSlotUpdate(player);
					break;

					//Tier #4
				case GOLD_BOOTS:
					delayedSlotUpdate(player);
					break;
				case GOLD_CHESTPLATE:
					delayedSlotUpdate(player);
					break;
				case GOLD_HELMET:
					delayedSlotUpdate(player);
					break;
				case GOLD_LEGGINGS:
					delayedSlotUpdate(player);
					break;

					//Tier #5
				case DIAMOND_BOOTS:
					delayedSlotUpdate(player);
					break;
				case DIAMOND_CHESTPLATE:
					delayedSlotUpdate(player);
					break;
				case DIAMOND_HELMET:
					delayedSlotUpdate(player);
					break;
				case DIAMOND_LEGGINGS:
					delayedSlotUpdate(player);
					break;

					//////////
					// Axes //
					//////////
				case WOOD_AXE: //Tier #1
					break;
				case STONE_AXE: //Tier #2
					break;
				case IRON_AXE: //Tier #3
					break;	
				case GOLD_AXE: //Tier #4
					break;
				case DIAMOND_AXE: //Tier #5
					break;		

					///////////////////////
					// Polearms / Spears //
					///////////////////////
				case WOOD_SPADE: //Tier #1
					break;
				case STONE_SPADE: //Tier #2
					break;
				case IRON_SPADE: //Tier #3
					break;
				case GOLD_SPADE: //Tier #4
					break;		
				case DIAMOND_SPADE: //Tier #5
					break;

					////////////
					// Swords //
					////////////
				case WOOD_SWORD: //Tier #1
					break;
				case STONE_SWORD: //Tier #2
					break;
				case IRON_SWORD: //Tier #3
					break;
				case GOLD_SWORD: //Tier #4
					break;
				case DIAMOND_SWORD: //Tier #5
					break;

					///////////
					// Wands //
					///////////
				case WOOD_HOE: //Tier #1
					break;
				case STONE_HOE: //Tier #2
					break;
				case IRON_HOE: //Tier #3
					break;
				case GOLD_HOE: //Tier #4
					break;
				case DIAMOND_HOE: //Tier #5
					break;

					///////////////
					// Craftable //
					///////////////
				case ANVIL:
					//Disable item renaming.
					event.setCancelled(true);
					break;
				case COMPASS:
					//Open players main menu.
					PlayerMenuManager.openPlayerMenu(player);
					break;
				case WORKBENCH:
					//Disable item crafting.
					event.setCancelled(true);
					break;
				default:
					break;
				}
			}
		}
		/*
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && block.getType().equals(Material.GRASS)) {
			List<Entity> nearbyentities = event.getPlayer().getNearbyEntities(10.0, 10.0, 10.0);
			for(int i = 50; i >= 0; i--){
				for(Entity entity : nearbyentities){
					if(entity instanceof LivingEntity){
						if(!(entity.getLocation().equals(event.getPlayer().getLocation()))){
							Location entitystrike = new Location(entity.getWorld(), entity.getLocation().getX(), (entity.getLocation().getY() + i), entity.getLocation().getZ());
							double pi = Math.PI;
							double radian = 180/pi;
							for(double theta = 0; theta <= (2 * pi); theta += (pi/100)){
								double x = entitystrike.getX() + Math.cos(theta * radian) * 0.5;
								double y = entitystrike.getY();
								double z = entitystrike.getZ() + Math.sin(theta * radian) * 0.5;
								entitystrike.getWorld().playEffect(new Location(entitystrike.getWorld(), x, y, z), Effect.LAVADRIP, 1, 1000);
							}
							if(i == 0){
								if (!entity.hasMetadata("NPC")) {
									//((LivingEntity) entity).damage(4);
									//entity.getWorld().getBlockAt(entity.getLocation()).setType(Material.FIRE);
									entity.setFireTicks(60);
									if(entity instanceof Player){
										((Player)entity).sendMessage(ChatColor.DARK_RED + event.getPlayer().getName() + ChatColor.RED + "struck you with a bolt of fire!");
									}
								}
							}
						}
					}
				}
			}

		}*/

		/*
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && block.getType().equals(Material.GRASS)) {
			List<Entity> nearbyentities = event.getPlayer().getNearbyEntities(10.0, 10.0, 10.0);
			for(int i = 50; i >= 0; i--){
				for(Entity entity : nearbyentities){
					if(entity instanceof LivingEntity){
						double pi = Math.PI;
						double radian = 180/pi;
						double width = 1;
						for(double theta = 0; theta <= (2 * pi); theta += (pi/100)){
							double x = player.getLocation().getX() + Math.cos(theta * radian) * width;
							double y = player.getLocation().getY();
							double z = player.getLocation().getZ() + Math.sin(theta * radian) * width;
							//player.getLocation().getWorld().playEffect(new Location(player.getLocation().getWorld(), x, y, z), Effect.LAVADRIP, 1, 1000);
							player.getLocation().getWorld().playEffect(new Location(player.getLocation().getWorld(), x, y + 1, z), Effect.LAVA_POP, 1, 1000);
							//player.getLocation().getWorld().playEffect(new Location(player.getLocation().getWorld(), x, y + 2, z), Effect.LAVADRIP, 1, 1000);
							width = width + .01;
						}
						if(!(entity.getLocation().equals(event.getPlayer().getLocation()))){
							//Location entitystrike = new Location(entity.getWorld(), entity.getLocation().getX(), (entity.getLocation().getY() + i), entity.getLocation().getZ());
							if(i == 0){
								if (!entity.hasMetadata("NPC")) {
									//((LivingEntity) entity).damage(4);
									//entity.getWorld().getBlockAt(entity.getLocation()).setType(Material.FIRE);
									entity.setFireTicks(60);
									if(entity instanceof Player){
										((Player)entity).sendMessage(ChatColor.DARK_RED + event.getPlayer().getName() + ChatColor.RED + "struck you with a bolt of fire!");
									}
								}
							}
						}
					}
				}
			}

		}
		 */

		//If a player clicks their bank.
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && block.getType().equals(Material.BOOKSHELF) || event.getAction().equals(Action.LEFT_CLICK_BLOCK) && block.getType().equals(Material.BOOKSHELF)) {
			
			//World
			World world = Bukkit.getWorld("world");
			
			//Location of block being clicked.
			Location blockLoc = event.getClickedBlock().getLocation();
			
			//Locations of the character selection libraries.
			Location lib0 = new Location(world, 42, 80, -286);
			Location lib1 = new Location(world, 35, 80, -293);
			Location lib2 = new Location(world, 42, 80, -300);
			Location lib3 = new Location(world, 49, 80, -293);
			
			//Allow the character selection screen on certain blocks.
			if (blockLoc.equals(lib0) || blockLoc.equals(lib1) || blockLoc.equals(lib2) || blockLoc.equals(lib3)) {
				
				//Show custom chest.
				PlayerCharacterManager.toggleCharacterSelectionMenu(player);

				//Play a sound
				player.playSound(player.getLocation(), Sound.CHEST_OPEN, .6F, .9F);
			}
		}

		//If a player clicks their bank.
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && block.getType().equals(Material.ENDER_CHEST) || event.getAction().equals(Action.LEFT_CLICK_BLOCK) && block.getType().equals(Material.ENDER_CHEST)) {

			//Cancel opening the ender chest and show a custom chest.
			event.setCancelled(true);

			//Show custom chest.
			player.openInventory(BankChestManager.getBank(player));

			//Play a sound
			player.playSound(player.getLocation(), Sound.CHEST_OPEN, .5F, 1F);
		}

		//If a player clicks a loot chest.
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && block.getType().equals(Material.CHEST)) {
			//Toggle that a player has opened a loot chest.
			LootTableChestManager.playerOpenedLootChest(player, block.getLocation());
		}

		//If player clicks a mail box.
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && block.getType().equals(Material.DROPPER) || event.getAction().equals(Action.LEFT_CLICK_BLOCK) && block.getType().equals(Material.DROPPER)) {

			//Cancel opening the ender chest and show a custom chest.
			event.setCancelled(true);

			//Toggle potion stand interaction
			PlayerMailManager.toggleMailboxInteract(player);
		}

		//If player clicks a Potion Stand (Alchemy profession).
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && block.getType().equals(Material.BREWING_STAND) || event.getAction().equals(Action.LEFT_CLICK_BLOCK) && block.getType().equals(Material.BREWING_STAND)) {

			//Cancel opening the regular inventory.
			event.setCancelled(true);

			//Toggle potion stand interaction
			Alchemy.toggleBrewingStandInteract(player);
		}

		//If player clicks a Anvil (Blacksmithing profession).
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && block.getType().equals(Material.ANVIL) || event.getAction().equals(Action.LEFT_CLICK_BLOCK) && block.getType().equals(Material.ANVIL)) {

			//Cancel opening the regular inventory.
			event.setCancelled(true);

			//Toggle anvil interaction
			Blacksmithing.toggleAnvilInteract(player);
		}

		//If player clicks a Anvil (Blacksmithing profession).
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && block.getType().equals(Material.FURNACE) || event.getAction().equals(Action.LEFT_CLICK_BLOCK) && block.getType().equals(Material.FURNACE)) {

			//Cancel opening the regular inventory.
			event.setCancelled(true);

			//Toggle anvil interaction
			Cooking.toggleFurnaceInteract(player);
		}
	}

	//It seems that the client responds better if we give it time to
	//set the armor, and then read the contents of the armor slots.
	public void delayedSlotUpdate(final Player player) {
		//Lets start a repeating task
		taskID = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				ItemLoreFactory.getInstance().applyHPBonus(player, true);
			} //END Run method.
		}, 10); //(20 ticks = 1 second)
	}
}
