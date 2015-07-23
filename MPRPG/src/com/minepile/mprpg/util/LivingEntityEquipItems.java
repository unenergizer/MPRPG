package com.minepile.mprpg.util;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class LivingEntityEquipItems {
	
	public static void setWeapon(LivingEntity mob, ItemStack item){
		if (mob instanceof LivingEntity) {
			mob.getEquipment().setItemInHand(item);
		}
	}
	
	public static ItemStack getWeapon(LivingEntity mob){
		return mob.getEquipment().getItemInHand();
	}
	
	public static void setHelmet(LivingEntity mob, ItemStack item){
		if (mob instanceof LivingEntity) {
			mob.getEquipment().setHelmet(item);
		}
	}
	
	public static ItemStack getHelmet(LivingEntity mob){
		return mob.getEquipment().getHelmet();
	}
	
	public static void setChestplate(LivingEntity mob, ItemStack item){
		if (mob instanceof LivingEntity) {
			mob.getEquipment().setChestplate(item);
		}
	}
	
	public static ItemStack getChestplate(LivingEntity mob){
		return mob.getEquipment().getChestplate();
	}
	
	public static void setLeggings(LivingEntity mob, ItemStack item){
		if (mob instanceof LivingEntity) {
			mob.getEquipment().setLeggings(item);
		}
	}
	
	public static ItemStack getLeggings(LivingEntity mob){
		return mob.getEquipment().getLeggings();
	}
	
	public static void setBoots(LivingEntity mob, ItemStack item){
		if (mob instanceof LivingEntity) {
			mob.getEquipment().setBoots(item);
		}
	}
	
	public static ItemStack getBoots(LivingEntity mob){
		return mob.getEquipment().getBoots();
	}
}
