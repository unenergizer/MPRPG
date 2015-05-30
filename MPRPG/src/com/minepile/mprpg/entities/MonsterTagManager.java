package com.minepile.mprpg.entities;

import com.minepile.mprpg.MPRPG;

public class MonsterTagManager {
	
	//setup instance variables
	public static MPRPG plugin;
	static MonsterTagManager shopChestManagerInstance = new MonsterTagManager();
	
	//Create instance
	public static MonsterTagManager getInstance() {
		return shopChestManagerInstance;
	}
	
	//Setup PlayerManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
	}

	/*
	public static void NBTtags(org.bukkit.entity.Entity entity, String NBTtag, int value) {
        net.minecraft.server.v1_8_R1.Entity nmsEnt = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = nmsEnt.getNBTTag();
   
        if(tag == null) {
            tag = new NBTTagCompound();
        }
   
        nmsEnt.c(tag);
        tag.setInt(NBTtag, value);
        nmsEnt.f(tag);
    }
    */
}
