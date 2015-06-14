package com.minepile.mprpg.chat;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import com.minepile.mprpg.MPRPG;

public class LagManager {
	
	//setup instance variables.
	public static MPRPG plugin;
	static LagManager lagManagerInstance = new LagManager();
	
	//Set up global variables.
	public static int TICK_COUNT= 0;
	public static long[] TICKS = new long[600];
	
	//Create instance
	public static LagManager getInstance() {
		return lagManagerInstance;
	}
	
	//Setup BlockRegenerationManager
	@SuppressWarnings("static-access")
	public void setup(MPRPG plugin) {
		this.plugin = plugin;
		
		//Start the timer used to get server lag.
		startClock();
	}
	
	/**
	 * Starts a clock that will calculate the lag in the server.
	 */
	private static void startClock() {
		
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
            	TICKS[(TICK_COUNT % TICKS.length)] = System.currentTimeMillis();
           	 
                TICK_COUNT += 1;
            }
        }, 100L, 1L);
	}
	
	public static double getTPS() {
		  return getTPS(100);
	}

	public static double getTPS(int ticks) {
		if (TICK_COUNT < ticks) {
			return 20.0D;
		}
		int target = (TICK_COUNT - 1 - ticks) % TICKS.length;
		long elapsed = System.currentTimeMillis() - TICKS[target];

		return ticks / (elapsed / 1000.0D);
	}
	
	public static long getElapsed(int tickID) {
		  if (TICK_COUNT - tickID >= TICKS.length){
		  }

		  long time = TICKS[(tickID % TICKS.length)];
		  return System.currentTimeMillis() - time;
	}
	
	public static double getLagPercent() {
		double tps = getTPS();
	    return Math.round((1.0D - tps / 20.0D) * 100.0D);
	}
}
