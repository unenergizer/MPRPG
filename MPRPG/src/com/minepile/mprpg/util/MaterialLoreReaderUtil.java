package com.minepile.mprpg.util;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MaterialLoreReaderUtil {

	//TODO: USE THIS CODE OR REMOVE IT.
	
	public void getLore(Player player, int loreLine) {
		ItemStack item = player.getItemInHand();
		
		// I'm not really sure if this could ever be null
		if (item != null) {
			ItemMeta meta = item.getItemMeta();
			
			// This can definitely be null, if the item has no metadata. (lore/enchantments/display name)
			if (meta != null) {
				// This part is probably never null...
				List<String> lore = meta.getLore();
				
				if (lore != null) {
					
					String loreLineText = lore.get(loreLine);
					String splitted = loreLineText.split("EXP: " + ChatColor.WHITE)[1];
					splitted        = splitted.split("/")[0];
				}
			}
		}
	}
}
