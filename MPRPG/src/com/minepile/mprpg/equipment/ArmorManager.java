package com.minepile.mprpg.equipment;

import org.bukkit.entity.LivingEntity;

import com.minepile.mprpg.MPRPG;

public class ArmorManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static ArmorManager armorManagerInstance = new ArmorManager();
	
	//Create instance
	public static ArmorManager getInstance() {
		return armorManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	public void getHealth (LivingEntity entity) {
		
	}
	
	public void getDamage (LivingEntity entity) {
		
	}
	
	public void getStrength (LivingEntity entity) {
		
	}
	
	public void getIntellect (LivingEntity entity) {
		
	}
	
	public void getCrit (LivingEntity entity) {
		
	}
	
	public void getItemFind (LivingEntity entity) {
		
	}
	
	public void getGoldFind (LivingEntity entity) {
		
	}
	
	
}
