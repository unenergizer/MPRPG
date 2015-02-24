package com.minepile.mprpg.equipment;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;
import com.minepile.mprpg.player.PlayerManager;

public class LoreManager {

	//setup instance variables
	public static MPRPG plugin;
	static LoreManager loreManagerInstance = new LoreManager();

	private static Pattern healthRegex = Pattern.compile("[+](\\d+)[ ](health)");
	private static Pattern negHealthRegex = Pattern.compile("[-](\\d+)[ ]()");
	private static Pattern regenRegex = Pattern.compile("[+](\\d+)[ ](regen)");
	private static Pattern staminaSpeedRegex = Pattern.compile("[+](\\d+)[ ](stamina)");
	private static Pattern damageValueRegex = Pattern.compile("[+](\\d+)[ ](damage)");
	private static Pattern negitiveDamageValueRegex = Pattern.compile("[-](\\d+)[ ]()");
	private static Pattern damageRangeRegex = Pattern.compile("(\\d+)(-)(\\d+)[ ]()");
	private static Pattern dodgeRegex = Pattern.compile("[+](\\d+)[%][ ](dodge)");
	private static Pattern critChanceRegex = Pattern.compile("[+](\\d+)[%][ ](crit chance)");
	private static Pattern critDamageRegex = Pattern.compile("[+](\\d+)[ ](crit damage)");
	private static Pattern lifestealRegex = Pattern.compile("[+](\\d+)[ ](life steal)");
	private static Pattern armorRegex = Pattern.compile("[+](\\d+)[ ](armor)");
	private static Pattern restrictionRegex = Pattern.compile("(restriction:)[ ][0-9]+");
	private static Pattern levelRegex = Pattern.compile("level (\\d+)()");
	public static HashMap<String, Timestamp> staminaLog = new HashMap<String, Timestamp>();
	private static Random generator = new Random();

	//Create instance
	public static LoreManager getInstance() {
		return loreManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		for (Player players : Bukkit.getOnlinePlayers()) {
			applyHpBonus(players);
		}
	}

	public static void handleArmorRestriction(Player player) {
		if (!canUse(player, player.getInventory().getBoots())) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(new ItemStack[] { player.getInventory().getBoots() });
			} else {
				player.getWorld().dropItem(player.getLocation(), player.getInventory().getBoots());
			}
			player.getInventory().setBoots(new ItemStack(0));
		}
		if (!canUse(player, player.getInventory().getChestplate())) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(new ItemStack[] { player.getInventory().getChestplate() });
			} else {
				player.getWorld().dropItem(player.getLocation(), player.getInventory().getChestplate());
			}
			player.getInventory().setChestplate(new ItemStack(0));
		}
		if (!canUse(player, player.getInventory().getHelmet())) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(new ItemStack[] { player.getInventory().getHelmet() });
			} else {
				player.getWorld().dropItem(player.getLocation(), player.getInventory().getHelmet());
			}
			player.getInventory().setHelmet(new ItemStack(0));
		}
		if (!canUse(player, player.getInventory().getLeggings())) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(new ItemStack[] { player.getInventory().getLeggings() });
			} else {
				player.getWorld().dropItem(player.getLocation(), player.getInventory().getLeggings());
			}
			player.getInventory().setLeggings(new ItemStack(0));
		}
		applyHpBonus(player);
	}

	public static boolean canUse(Player player, ItemStack item){
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			List<String> lore = item.getItemMeta().getLore();
			String allLore = lore.toString().toLowerCase();
			Matcher valueMatcher = levelRegex.matcher(allLore);
			if (valueMatcher.find()) {
				if (player.getLevel() < Integer.valueOf(valueMatcher.group(1)).intValue()) {
					player.sendMessage("Item was not able to be equipped.");
					return false;
				}
			}
			valueMatcher = restrictionRegex.matcher(allLore);
			if (valueMatcher.find()) {
				if (player.hasPermission("loreattributes." + valueMatcher.group(2))) {
					return true;
				}
				player.sendMessage(item.getType().toString());
				return false;
			}
		}
		return true;
	}

	public static int getDodgeBonus(LivingEntity entity) {
		Integer dodgeBonus = Integer.valueOf(0);
		for (ItemStack item : entity.getEquipment().getArmorContents()) {
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
				List<String> lore = item.getItemMeta().getLore();
				String allLore = lore.toString().toLowerCase();

				Matcher valueMatcher = dodgeRegex.matcher(allLore);
				if (valueMatcher.find()) {
					dodgeBonus = Integer.valueOf(dodgeBonus.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
				}
			}
		}
		ItemStack item = entity.getEquipment().getItemInHand();
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			Object lore = item.getItemMeta().getLore();
			String allLore = lore.toString().toLowerCase();

			Matcher valueMatcher = dodgeRegex.matcher(allLore);
			if (valueMatcher.find()) {
				dodgeBonus = Integer.valueOf(dodgeBonus.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
			}
		}
		return dodgeBonus.intValue();
	}

	public static boolean dodgedAttack(LivingEntity entity) {
		if (!entity.isValid()) {
			return false;
		}
		Integer chance = Integer.valueOf(getDodgeBonus(entity));


		Integer roll = Integer.valueOf(generator.nextInt(100) + 1);
		if (chance.intValue() >= roll.intValue()) {
			return true;
		}
		return false;
	}

	private static int getCritChance(LivingEntity entity) {
		Integer chance = Integer.valueOf(0);
		for (ItemStack item : entity.getEquipment().getArmorContents()) {
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
				List<String> lore = item.getItemMeta().getLore();
				String allLore = lore.toString().toLowerCase();

				Matcher valueMatcher = critChanceRegex.matcher(allLore);
				if (valueMatcher.find()) {
					chance = Integer.valueOf(chance.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
				}
			}
		}
		ItemStack item = entity.getEquipment().getItemInHand();
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			Object lore = item.getItemMeta().getLore();
			String allLore = lore.toString().toLowerCase();

			Matcher valueMatcher = critChanceRegex.matcher(allLore);
			if (valueMatcher.find()) {
				chance = Integer.valueOf(chance.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
			}
		}
		return chance.intValue();
	}

	private static boolean critStamina(LivingEntity entity) {
		if (!entity.isValid()) {
			return false;
		}
		Integer chance = Integer.valueOf(getCritChance(entity));

		Integer roll = Integer.valueOf(generator.nextInt(100) + 1);
		if (chance.intValue() >= roll.intValue()) {
			return true;
		}
		return false;
	}

	public static int getArmorBonus(LivingEntity entity) {
		Integer armor = Integer.valueOf(0);
		for (ItemStack item : entity.getEquipment().getArmorContents()) {
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
				List<String> lore = item.getItemMeta().getLore();
				String allLore = lore.toString().toLowerCase();

				Matcher valueMatcher = armorRegex.matcher(allLore);
				if (valueMatcher.find()) {
					armor = Integer.valueOf(armor.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
				}
			}
		}
		ItemStack item = entity.getEquipment().getItemInHand();
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			Object lore = item.getItemMeta().getLore();
			String allLore = lore.toString().toLowerCase();

			Matcher valueMatcher = armorRegex.matcher(allLore);
			if (valueMatcher.find()) {
				armor = Integer.valueOf(armor.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
			}
		}
		return armor.intValue();
	}

	public static int getLifeSteal(LivingEntity entity) {
		Integer steal = Integer.valueOf(0);
		for (ItemStack item : entity.getEquipment().getArmorContents()) {
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
				List<String> lore = item.getItemMeta().getLore();
				String allLore = lore.toString().toLowerCase();

				Matcher valueMatcher = lifestealRegex.matcher(allLore);
				if (valueMatcher.find()) {
					steal = Integer.valueOf(steal.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
				}
			}
		}
		ItemStack item = entity.getEquipment().getItemInHand();
		if ((item != null) && 
				(item.hasItemMeta()) && 
				(item.getItemMeta().hasLore()))
		{
			Object lore = item.getItemMeta().getLore();
			String allLore = lore.toString().toLowerCase();

			Matcher valueMatcher = lifestealRegex.matcher(allLore);
			if (valueMatcher.find()) {
				steal = Integer.valueOf(steal.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
			}
		}
		return steal.intValue();
	}

	public static int getCritDamage(LivingEntity entity) {
		if (!critStamina(entity)) {
			return 0;
		}
		Integer damage = Integer.valueOf(0);
		for (ItemStack item : entity.getEquipment().getArmorContents()) {
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
				List<String> lore = item.getItemMeta().getLore();
				String allLore = lore.toString().toLowerCase();

				Matcher valueMatcher = critDamageRegex.matcher(allLore);
				if (valueMatcher.find()) {
					damage = Integer.valueOf(damage.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
				}
			}
		}
		ItemStack item = entity.getEquipment().getItemInHand();
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			Object lore = item.getItemMeta().getLore();
			String allLore = lore.toString().toLowerCase();

			Matcher valueMatcher = critDamageRegex.matcher(allLore);
			if (valueMatcher.find()) {
				damage = Integer.valueOf(damage.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
			}
		}
		return damage.intValue();
	}

	private static double getStaminaCooldown(Player player) {
		return PlayerManager.getBaseStaminaRegenRate() * 0.1D - getStaminaSpeed(player) * 0.1D;
	}

	public static void addAttackCooldown(String playerName) {
		Timestamp able = new Timestamp((long) (new Date().getTime() + getStaminaCooldown(Bukkit.getPlayerExact(playerName)) * 1000.0D));

		staminaLog.put(playerName, able);
	}

	public static boolean canAttack(String playerName) {
		if (!staminaLog.containsKey(playerName)) {
			return true;
		}
		Date now = new Date();
		if (now.after(staminaLog.get(playerName))) {
			return true;
		}
		return false;
	}

	private static double getStaminaSpeed(Player player) {
		if (player == null) {
			return 1.0D;
		}
		double speed = 1.0D;
		for (ItemStack item : player.getEquipment().getArmorContents()) {
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
				List<String> lore = item.getItemMeta().getLore();
				String allLore = lore.toString().toLowerCase();

				Matcher valueMatcher = staminaSpeedRegex.matcher(allLore);
				if (valueMatcher.find()) {
					speed += Integer.valueOf(valueMatcher.group(1)).intValue();
				}
			}
		}
		ItemStack item = player.getEquipment().getItemInHand();
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			Object lore = item.getItemMeta().getLore();
			String allLore = lore.toString().toLowerCase();

			Matcher valueMatcher = staminaSpeedRegex.matcher(allLore);
			if (valueMatcher.find()) {
				speed += Integer.valueOf(valueMatcher.group(1)).intValue();
			}
		}
		return speed;
	}

	public static void applyHpBonus(LivingEntity entity) {
		if (!entity.isValid()) {
			return;
		}
		Integer hpToAdd = Integer.valueOf(getHpBonus(entity));
		if ((entity instanceof Player)) {
			if (entity.getHealth() > getBaseHealth((Player)entity) + hpToAdd.intValue()) {
				entity.setHealth(getBaseHealth((Player)entity) + hpToAdd.intValue());
			}
			entity.setMaxHealth(getBaseHealth((Player)entity) + hpToAdd.intValue());
		}
	}

	public static int getHpBonus(LivingEntity entity) {
		Integer hpToAdd = Integer.valueOf(0);
		for (ItemStack item : entity.getEquipment().getArmorContents()) {
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
				List<String> lore = item.getItemMeta().getLore();
				String allLore = lore.toString().toLowerCase();
				Matcher negmatcher = negHealthRegex.matcher(allLore);
				Matcher matcher = healthRegex.matcher(allLore);
				if (matcher.find()) {
					hpToAdd = Integer.valueOf(hpToAdd.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
				if (negmatcher.find()) {
					hpToAdd = Integer.valueOf(hpToAdd.intValue() - Integer.valueOf(negmatcher.group(1)).intValue());
				}
				if (hpToAdd.intValue() < 0) {
					hpToAdd = Integer.valueOf(0);
				}
			}
		}
		return hpToAdd.intValue();
	}

	public static int getBaseHealth(Player player) {
		int hp = PlayerManager.getBaseHealthPoints();
		return hp;
	}

	public static int getRegenBonus(LivingEntity entity) {
		if (!entity.isValid()) {
			return 0;
		}
		Integer regenBonus = Integer.valueOf(0);
		for (ItemStack item : entity.getEquipment().getArmorContents()) {
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
				List<String> lore = item.getItemMeta().getLore();
				String allLore = lore.toString().toLowerCase();

				Matcher matcher = regenRegex.matcher(allLore);
				if (matcher.find()) {
					regenBonus = Integer.valueOf(regenBonus.intValue() + Integer.valueOf(matcher.group(1)).intValue());
				}
			}
		}
		return regenBonus.intValue();
	}

	public static int getDamageBonus(LivingEntity entity) {
		if (!entity.isValid()) {
			return 0;
		}
		Integer damageMin = Integer.valueOf(0);
		Integer damageMax = Integer.valueOf(0);
		Integer damageBonus = Integer.valueOf(0);
		for (ItemStack item : entity.getEquipment().getArmorContents()) {
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
				List<String> lore = item.getItemMeta().getLore();
				String allLore = lore.toString().toLowerCase();

				Matcher rangeMatcher = damageRangeRegex.matcher(allLore);
				Matcher valueMatcher = damageValueRegex.matcher(allLore);
				if (rangeMatcher.find()) {
					damageMin = Integer.valueOf(damageMin.intValue() + Integer.valueOf(rangeMatcher.group(1)).intValue());
					damageMax = Integer.valueOf(damageMax.intValue() + Integer.valueOf(rangeMatcher.group(3)).intValue());
				}
				if (valueMatcher.find()) {
					damageBonus = Integer.valueOf(damageBonus.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
				}
			}
		}
		ItemStack item = entity.getEquipment().getItemInHand();
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			Object lore = item.getItemMeta().getLore();
			String allLore = lore.toString().toLowerCase();
			Matcher negValueMatcher = negitiveDamageValueRegex.matcher(allLore);
			Matcher rangeMatcher = damageRangeRegex.matcher(allLore);
			Matcher valueMatcher = damageValueRegex.matcher(allLore);
			if (rangeMatcher.find()) {
				damageMin = Integer.valueOf(damageMin.intValue() + Integer.valueOf(rangeMatcher.group(1)).intValue());
				damageMax = Integer.valueOf(damageMax.intValue() + Integer.valueOf(rangeMatcher.group(3)).intValue());
			}
			if (valueMatcher.find()) {
				damageBonus = Integer.valueOf(damageBonus.intValue() + Integer.valueOf(valueMatcher.group(1)).intValue());
				damageBonus = Integer.valueOf(damageBonus.intValue() - Integer.valueOf(negValueMatcher.group(1)).intValue());
			}
		}
		if (damageMax.intValue() < 1) {
			damageMax = Integer.valueOf(1);
		}
		if (damageMin.intValue() < 1) {
			damageMin = Integer.valueOf(1);
		}
		return (int)Math.round(Math.random() * (damageMax.intValue() - damageMin.intValue()) + damageMin.intValue() + damageBonus.intValue() + getCritDamage(entity));
	}

	public static boolean useRangeOfDamage(LivingEntity entity) {
		if (!entity.isValid()) {
			return false;
		}
		for (ItemStack item : entity.getEquipment().getArmorContents()) {
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
				List<String> lore = item.getItemMeta().getLore();
				String allLore = lore.toString().toLowerCase();

				Matcher rangeMatcher = damageRangeRegex.matcher(allLore);
				if (rangeMatcher.find()) {
					return true;
				}
			}
		}
		ItemStack item = entity.getEquipment().getItemInHand();
		if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {
			Object lore = item.getItemMeta().getLore();
			String allLore = lore.toString().toLowerCase();

			Matcher rangeMatcher = damageRangeRegex.matcher(allLore);
			if (rangeMatcher.find()) {
				return true;
			}
		}
		return false;
	}

	private static int getPermissionsHealth(Player player){
		int hp = PlayerManager.getBaseHealthPoints();
		try {
			hp = PlayerManager.getBaseHealthPoints();
		} catch (Exception e) {
			return hp;
		}
		return hp;
	}

	public static void displayLoreStats(Player sender)
	{
		HashSet<String> message = new HashSet<String>();
		if (getHpBonus(sender) != 0) {
			message.add(ChatColor.GRAY + "HitPoints" + ": " + ChatColor.WHITE + getHpBonus(sender));
		}
		if (getRegenBonus(sender) != 0) {
			message.add(ChatColor.GRAY + "Regeneration" + ": " + ChatColor.WHITE + getRegenBonus(sender));
		}
		if (getStaminaSpeed(sender) != 0) {
			message.add(ChatColor.GRAY + "Stamina Speed" + ": " + ChatColor.WHITE + getStaminaSpeed(sender));
		}
		if (getDamageBonus(sender) != 0) {
			message.add(ChatColor.GRAY + "Damage" + ": " + ChatColor.WHITE + getDamageBonus(sender));
		}
		if (getDodgeBonus(sender) != 0) {
			message.add(ChatColor.GRAY + "Dodge" + ": " + ChatColor.WHITE + getDodgeBonus(sender) + "%");
		}
		if (getCritChance(sender) != 0) {
			message.add(ChatColor.GRAY + "Critical Hit Chance" + ": " + ChatColor.WHITE + getCritChance(sender) + "%");
		}
		if (getCritDamage(sender) != 0) {
			message.add(ChatColor.GRAY + "Critical Damage" + ": " + ChatColor.WHITE + getCritDamage(sender));
		}
		if (getLifeSteal(sender) != 0) {
			message.add(ChatColor.GRAY + "Life Steal" + ": " + ChatColor.WHITE + getLifeSteal(sender));
		}
		if (getArmorBonus(sender) != 0) {
			message.add(ChatColor.GRAY + "Armor" + ": " + ChatColor.WHITE + getArmorBonus(sender));
		}
		String newMessage = "";
		for (String toSend : message)
		{
			newMessage = newMessage + "     " + toSend;
			if (newMessage.length() > 40)
			{
				sender.sendMessage(newMessage);
				newMessage = "";
			}
		}
		if (newMessage.length() > 0) {
			sender.sendMessage(newMessage);
		}
		message.clear();
	}
}
