package com.minepile.mprpg.items;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.minepile.mprpg.MPRPG;

public class SoulboundManager {

	//setup instance variables
	public static MPRPG plugin;
	static SoulboundManager menuManagerInstance = new SoulboundManager();


	//Create instance
	public static SoulboundManager getInstance() {
		return menuManagerInstance;
	}

	//Setup Soulboud Item Manager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;

	}

	public static boolean isItemSoulbound(ItemStack item) {
		if (item != null && item.getItemMeta() != null && item.getItemMeta().getLore() != null) {
			List<String> lore = item.getItemMeta().getLore();
			String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

			Pattern regex = Pattern.compile("soulbound");
			Matcher matcher = regex.matcher(allLore);

			if (matcher.find()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static void showSoulboundMessage(Player player) {
		//Send player a sound notification.
		player.playSound(player.getLocation(), Sound.FIZZ, 1F, 1F);

		//Send item deletion message.
		player.sendMessage(ChatColor.RED + "That item was soulbound, so it was deleted!");
	}
}
