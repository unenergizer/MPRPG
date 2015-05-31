package com.minepile.mprpg.items;

import org.bukkit.Material;

import com.minepile.mprpg.MPRPG;

public class ItemGeneratorManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static ItemGeneratorManager itemGeneratorManagerInstance = new ItemGeneratorManager();
	
	//Create instance
	public static ItemGeneratorManager getInstance() {
		return itemGeneratorManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}	
	
	public static Material convertItemIdToMaterial(int itemId) {
		Material item = Material.getMaterial(itemId);
		return item;
	}
	
	public static double convertMaterialToItemId(Material item) {
		double itemId = 0;
		
		switch(item) {
		case ACACIA_DOOR:
			itemId = 430;
			break;
		case ACACIA_DOOR_ITEM:
			itemId = 430;
			break;
		case ACACIA_FENCE:
			itemId = 192;
			break;
		case ACACIA_FENCE_GATE:
			itemId = 187;
			break;
		case ACACIA_STAIRS:
			itemId = 163;
			break;
		case ACTIVATOR_RAIL:
			itemId = 157;
			break;
		case AIR:
			itemId = 0;
			break;
		case ANVIL:
			itemId = 145;
			break;
		case APPLE:
			itemId = 260;
			break;
		case ARMOR_STAND:
			itemId = 260;
			break;
		case ARROW:
			itemId = 262;
			break;
		case BAKED_POTATO:
			itemId = 393;
			break;
		case BANNER:
			itemId = 425;
			break;
		case BARRIER:
			itemId= 166;
			break;
		case BEACON:
			itemId = 138;
			break;
		case BED:
			itemId = 355;
			break;
		case BEDROCK:
			itemId = 7;
			break;
		case BED_BLOCK:
			itemId = 26;
			break;
		case BIRCH_DOOR:
			itemId = 428;
			break;
		case BIRCH_DOOR_ITEM:
			itemId = 428;
			break;
		case BIRCH_FENCE:
			itemId = 189;
			break;
		case BIRCH_FENCE_GATE:
			itemId = 184;
			break;
		case BIRCH_WOOD_STAIRS:
			itemId = 135;
			break;
		case BLAZE_POWDER:
			itemId = 377;
			break;
		case BLAZE_ROD:
			itemId = 369;
			break;
		case BOAT:
			itemId = 333;
			break;
		case BONE:
			itemId = 352;
			break;
		case BOOK:
			itemId = 387;
			break;
		case BOOKSHELF:
			itemId = 47;
			break;
		case BOOK_AND_QUILL:
			itemId = 386;
			break;
		case BOW:
			itemId = 261;
			break;
		case BOWL:
			itemId = 281;
			break;
		case BREAD:
			itemId = 297;
			break;
		case BREWING_STAND:
			itemId = 379;
			break;
		case BREWING_STAND_ITEM:
			itemId = 379;
			break;
		case BRICK:
			itemId = 45;
			break;
		case BRICK_STAIRS:
			itemId = 108;
			break;
		case BROWN_MUSHROOM:
			itemId = 43;
			break;
		case BUCKET:
			itemId = 325;
			break;
		case BURNING_FURNACE:
			itemId = 62;
			break;
		case CACTUS:
			itemId = 81;
			break;
		case CAKE:
			itemId = 354;
			break;
		case CAKE_BLOCK:
			itemId = 354;
			break;
		case CARPET:
			itemId = 171;
			break;
		case CARROT:
			itemId = 391;
			break;
		case CARROT_ITEM:
			itemId = 391;
			break;
		case CARROT_STICK:
			itemId = 398;
			break;
		case CAULDRON:
			itemId = 380;
			break;
		case CAULDRON_ITEM:
			itemId = 380;
			break;
		case CHAINMAIL_BOOTS:
			itemId = 305;
			break;
		case CHAINMAIL_CHESTPLATE:
			itemId = 303;
			break;
		case CHAINMAIL_HELMET:
			itemId = 302;
			break;
		case CHAINMAIL_LEGGINGS:
			itemId = 304;
			break;
		case CHEST:
			itemId = 54;
			break;
		case CLAY:
			itemId = 82;
			break;
		case CLAY_BALL:
			itemId = 337;
			break;
		case CLAY_BRICK:
			itemId = 336;
			break;
		case COAL:
			itemId = 263;
			break;
		case COAL_BLOCK:
			itemId = 173;
			break;
		case COAL_ORE:
			itemId = 16;
			break;
		case COBBLESTONE:
			itemId = 4;
			break;
		case COBBLESTONE_STAIRS:
			itemId = 67;
			break;
		case COBBLE_WALL:
			itemId = 139;
			break;
		case COCOA:
			itemId = 351.3;
			break;
		case COMMAND:
			itemId = 137;
			break;
		case COMMAND_MINECART:
			itemId = 422;
			break;
		case COMPASS:
			itemId = 345;
			break;
		case COOKED_BEEF:
			itemId = 364;
			break;
		case COOKED_CHICKEN:
			itemId = 366;
			break;
		case COOKED_FISH:
			itemId = 350;
			break;
		case COOKED_MUTTON:
			itemId = 424;
			break;
		case COOKED_RABBIT:
			itemId = 412;
			break;
		case COOKIE:
			itemId = 357;
			break;
		case CROPS:
			itemId = 59;
			break;
		case DARK_OAK_DOOR:
			break;
		case DARK_OAK_DOOR_ITEM:
			break;
		case DARK_OAK_FENCE:
			break;
		case DARK_OAK_FENCE_GATE:
			break;
		case DARK_OAK_STAIRS:
			break;
		case DAYLIGHT_DETECTOR:
			break;
		case DAYLIGHT_DETECTOR_INVERTED:
			break;
		case DEAD_BUSH:
			break;
		case DETECTOR_RAIL:
			break;
		case DIAMOND:
			break;
		case DIAMOND_AXE:
			break;
		case DIAMOND_BARDING:
			break;
		case DIAMOND_BLOCK:
			break;
		case DIAMOND_BOOTS:
			break;
		case DIAMOND_CHESTPLATE:
			break;
		case DIAMOND_HELMET:
			break;
		case DIAMOND_HOE:
			break;
		case DIAMOND_LEGGINGS:
			break;
		case DIAMOND_ORE:
			break;
		case DIAMOND_PICKAXE:
			break;
		case DIAMOND_SPADE:
			break;
		case DIAMOND_SWORD:
			break;
		case DIODE:
			break;
		case DIODE_BLOCK_OFF:
			break;
		case DIODE_BLOCK_ON:
			break;
		case DIRT:
			break;
		case DISPENSER:
			break;
		case DOUBLE_PLANT:
			break;
		case DOUBLE_STEP:
			break;
		case DOUBLE_STONE_SLAB2:
			break;
		case DRAGON_EGG:
			break;
		case DROPPER:
			break;
		case EGG:
			break;
		case EMERALD:
			break;
		case EMERALD_BLOCK:
			break;
		case EMERALD_ORE:
			break;
		case EMPTY_MAP:
			break;
		case ENCHANTED_BOOK:
			break;
		case ENCHANTMENT_TABLE:
			break;
		case ENDER_CHEST:
			break;
		case ENDER_PEARL:
			break;
		case ENDER_PORTAL:
			break;
		case ENDER_PORTAL_FRAME:
			break;
		case ENDER_STONE:
			break;
		case EXPLOSIVE_MINECART:
			break;
		case EXP_BOTTLE:
			break;
		case EYE_OF_ENDER:
			break;
		case FEATHER:
			break;
		case FENCE:
			break;
		case FENCE_GATE:
			break;
		case FERMENTED_SPIDER_EYE:
			break;
		case FIRE:
			break;
		case FIREBALL:
			break;
		case FIREWORK:
			break;
		case FIREWORK_CHARGE:
			break;
		case FISHING_ROD:
			break;
		case FLINT:
			break;
		case FLINT_AND_STEEL:
			break;
		case FLOWER_POT:
			break;
		case FLOWER_POT_ITEM:
			break;
		case FURNACE:
			break;
		case GHAST_TEAR:
			break;
		case GLASS:
			break;
		case GLASS_BOTTLE:
			break;
		case GLOWING_REDSTONE_ORE:
			break;
		case GLOWSTONE:
			break;
		case GLOWSTONE_DUST:
			break;
		case GOLDEN_APPLE:
			break;
		case GOLDEN_CARROT:
			break;
		case GOLD_AXE:
			break;
		case GOLD_BARDING:
			break;
		case GOLD_BLOCK:
			break;
		case GOLD_BOOTS:
			break;
		case GOLD_CHESTPLATE:
			break;
		case GOLD_HELMET:
			break;
		case GOLD_HOE:
			break;
		case GOLD_INGOT:
			break;
		case GOLD_LEGGINGS:
			break;
		case GOLD_NUGGET:
			break;
		case GOLD_ORE:
			break;
		case GOLD_PICKAXE:
			break;
		case GOLD_PLATE:
			break;
		case GOLD_RECORD:
			break;
		case GOLD_SPADE:
			break;
		case GOLD_SWORD:
			break;
		case GRASS:
			break;
		case GRAVEL:
			break;
		case GREEN_RECORD:
			break;
		case GRILLED_PORK:
			break;
		case HARD_CLAY:
			break;
		case HAY_BLOCK:
			break;
		case HOPPER:
			break;
		case HOPPER_MINECART:
			break;
		case HUGE_MUSHROOM_1:
			break;
		case HUGE_MUSHROOM_2:
			break;
		case ICE:
			break;
		case INK_SACK:
			break;
		case IRON_AXE:
			break;
		case IRON_BARDING:
			break;
		case IRON_BLOCK:
			break;
		case IRON_BOOTS:
			break;
		case IRON_CHESTPLATE:
			break;
		case IRON_DOOR:
			break;
		case IRON_DOOR_BLOCK:
			break;
		case IRON_FENCE:
			break;
		case IRON_HELMET:
			break;
		case IRON_HOE:
			break;
		case IRON_INGOT:
			break;
		case IRON_LEGGINGS:
			break;
		case IRON_ORE:
			break;
		case IRON_PICKAXE:
			break;
		case IRON_PLATE:
			break;
		case IRON_SPADE:
			break;
		case IRON_SWORD:
			break;
		case IRON_TRAPDOOR:
			break;
		case ITEM_FRAME:
			break;
		case JACK_O_LANTERN:
			break;
		case JUKEBOX:
			break;
		case JUNGLE_DOOR:
			break;
		case JUNGLE_DOOR_ITEM:
			break;
		case JUNGLE_FENCE:
			break;
		case JUNGLE_FENCE_GATE:
			break;
		case JUNGLE_WOOD_STAIRS:
			break;
		case LADDER:
			break;
		case LAPIS_BLOCK:
			break;
		case LAPIS_ORE:
			break;
		case LAVA:
			break;
		case LAVA_BUCKET:
			break;
		case LEASH:
			break;
		case LEATHER:
			break;
		case LEATHER_BOOTS:
			break;
		case LEATHER_CHESTPLATE:
			break;
		case LEATHER_HELMET:
			break;
		case LEATHER_LEGGINGS:
			break;
		case LEAVES:
			break;
		case LEAVES_2:
			break;
		case LEVER:
			break;
		case LOCKED_CHEST:
			break;
		case LOG:
			break;
		case LOG_2:
			break;
		case LONG_GRASS:
			break;
		case MAGMA_CREAM:
			break;
		case MAP:
			break;
		case MELON:
			break;
		case MELON_BLOCK:
			break;
		case MELON_SEEDS:
			break;
		case MELON_STEM:
			break;
		case MILK_BUCKET:
			break;
		case MINECART:
			break;
		case MOB_SPAWNER:
			break;
		case MONSTER_EGG:
			break;
		case MONSTER_EGGS:
			break;
		case MOSSY_COBBLESTONE:
			break;
		case MUSHROOM_SOUP:
			break;
		case MUTTON:
			break;
		case MYCEL:
			break;
		case NAME_TAG:
			break;
		case NETHERRACK:
			break;
		case NETHER_BRICK:
			break;
		case NETHER_BRICK_ITEM:
			break;
		case NETHER_BRICK_STAIRS:
			break;
		case NETHER_FENCE:
			break;
		case NETHER_STALK:
			break;
		case NETHER_STAR:
			break;
		case NETHER_WARTS:
			break;
		case NOTE_BLOCK:
			break;
		case OBSIDIAN:
			break;
		case PACKED_ICE:
			break;
		case PAINTING:
			break;
		case PAPER:
			break;
		case PISTON_BASE:
			break;
		case PISTON_EXTENSION:
			break;
		case PISTON_MOVING_PIECE:
			break;
		case PISTON_STICKY_BASE:
			break;
		case POISONOUS_POTATO:
			break;
		case PORK:
			break;
		case PORTAL:
			break;
		case POTATO:
			break;
		case POTATO_ITEM:
			break;
		case POTION:
			break;
		case POWERED_MINECART:
			break;
		case POWERED_RAIL:
			break;
		case PRISMARINE:
			break;
		case PRISMARINE_CRYSTALS:
			break;
		case PRISMARINE_SHARD:
			break;
		case PUMPKIN:
			break;
		case PUMPKIN_PIE:
			break;
		case PUMPKIN_SEEDS:
			break;
		case PUMPKIN_STEM:
			break;
		case QUARTZ:
			break;
		case QUARTZ_BLOCK:
			break;
		case QUARTZ_ORE:
			break;
		case QUARTZ_STAIRS:
			break;
		case RABBIT:
			break;
		case RABBIT_FOOT:
			break;
		case RABBIT_HIDE:
			break;
		case RABBIT_STEW:
			break;
		case RAILS:
			break;
		case RAW_BEEF:
			break;
		case RAW_CHICKEN:
			break;
		case RAW_FISH:
			break;
		case RECORD_10:
			break;
		case RECORD_11:
			break;
		case RECORD_12:
			break;
		case RECORD_3:
			break;
		case RECORD_4:
			break;
		case RECORD_5:
			break;
		case RECORD_6:
			break;
		case RECORD_7:
			break;
		case RECORD_8:
			break;
		case RECORD_9:
			break;
		case REDSTONE:
			break;
		case REDSTONE_BLOCK:
			break;
		case REDSTONE_COMPARATOR:
			break;
		case REDSTONE_COMPARATOR_OFF:
			break;
		case REDSTONE_COMPARATOR_ON:
			break;
		case REDSTONE_LAMP_OFF:
			break;
		case REDSTONE_LAMP_ON:
			break;
		case REDSTONE_ORE:
			break;
		case REDSTONE_TORCH_OFF:
			break;
		case REDSTONE_TORCH_ON:
			break;
		case REDSTONE_WIRE:
			break;
		case RED_MUSHROOM:
			break;
		case RED_ROSE:
			break;
		case RED_SANDSTONE:
			break;
		case RED_SANDSTONE_STAIRS:
			break;
		case ROTTEN_FLESH:
			break;
		case SADDLE:
			break;
		case SAND:
			break;
		case SANDSTONE:
			break;
		case SANDSTONE_STAIRS:
			break;
		case SAPLING:
			break;
		case SEA_LANTERN:
			break;
		case SEEDS:
			break;
		case SHEARS:
			break;
		case SIGN:
			break;
		case SIGN_POST:
			break;
		case SKULL:
			break;
		case SKULL_ITEM:
			break;
		case SLIME_BALL:
			break;
		case SLIME_BLOCK:
			break;
		case SMOOTH_BRICK:
			break;
		case SMOOTH_STAIRS:
			break;
		case SNOW:
			break;
		case SNOW_BALL:
			break;
		case SNOW_BLOCK:
			break;
		case SOIL:
			break;
		case SOUL_SAND:
			break;
		case SPECKLED_MELON:
			break;
		case SPIDER_EYE:
			break;
		case SPONGE:
			break;
		case SPRUCE_DOOR:
			break;
		case SPRUCE_DOOR_ITEM:
			break;
		case SPRUCE_FENCE:
			break;
		case SPRUCE_FENCE_GATE:
			break;
		case SPRUCE_WOOD_STAIRS:
			break;
		case STAINED_CLAY:
			break;
		case STAINED_GLASS:
			break;
		case STAINED_GLASS_PANE:
			break;
		case STANDING_BANNER:
			break;
		case STATIONARY_LAVA:
			break;
		case STATIONARY_WATER:
			break;
		case STEP:
			break;
		case STICK:
			break;
		case STONE:
			break;
		case STONE_AXE:
			break;
		case STONE_BUTTON:
			break;
		case STONE_HOE:
			break;
		case STONE_PICKAXE:
			break;
		case STONE_PLATE:
			break;
		case STONE_SLAB2:
			break;
		case STONE_SPADE:
			break;
		case STONE_SWORD:
			break;
		case STORAGE_MINECART:
			break;
		case STRING:
			break;
		case SUGAR:
			break;
		case SUGAR_CANE:
			break;
		case SUGAR_CANE_BLOCK:
			break;
		case SULPHUR:
			break;
		case THIN_GLASS:
			break;
		case TNT:
			break;
		case TORCH:
			break;
		case TRAPPED_CHEST:
			break;
		case TRAP_DOOR:
			break;
		case TRIPWIRE:
			break;
		case TRIPWIRE_HOOK:
			break;
		case VINE:
			break;
		case WALL_BANNER:
			break;
		case WALL_SIGN:
			break;
		case WATCH:
			break;
		case WATER:
			break;
		case WATER_BUCKET:
			break;
		case WATER_LILY:
			break;
		case WEB:
			break;
		case WHEAT:
			break;
		case WOOD:
			break;
		case WOODEN_DOOR:
			break;
		case WOOD_AXE:
			break;
		case WOOD_BUTTON:
			break;
		case WOOD_DOOR:
			break;
		case WOOD_DOUBLE_STEP:
			break;
		case WOOD_HOE:
			break;
		case WOOD_PICKAXE:
			break;
		case WOOD_PLATE:
			break;
		case WOOD_SPADE:
			break;
		case WOOD_STAIRS:
			break;
		case WOOD_STEP:
			break;
		case WOOD_SWORD:
			break;
		case WOOL:
			break;
		case WORKBENCH:
			break;
		case WRITTEN_BOOK:
			break;
		case YELLOW_FLOWER:
			break;
		default:
			itemId = 0;
			break;
		}
		
		return itemId;
	}
}
