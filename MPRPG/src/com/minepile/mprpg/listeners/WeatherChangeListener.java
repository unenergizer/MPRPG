package com.minepile.mprpg.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import com.minepile.mprpg.MPRPG;


public class WeatherChangeListener  implements Listener {
	
	@SuppressWarnings("unused")
	private MPRPG plugin;
	
	public WeatherChangeListener(MPRPG plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event) {
		//Cancel weather changes.
		event.setCancelled(true);
	}
}
