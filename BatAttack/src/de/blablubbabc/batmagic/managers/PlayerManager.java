package de.blablubbabc.batmagic.managers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.blablubbabc.batmagic.Main;
import de.blablubbabc.batmagic.objects.Magician;

public class PlayerManager {
	private Map<String, Magician> magicians = new HashMap<String, Magician>();
	
	public PlayerManager() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			addMagician(player);
		}
		
		// process magician every tick:
		Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				for (Magician magician : magicians.values()) {
					magician.tick();
				}
			}
		}, 5L, 1L);
	}
	
	public Magician getMagician(String playerName) {
		return magicians.get(playerName);
	}
	
	public void removeMagician(String playerName) {
		magicians.remove(playerName);
	}
	
	public void addMagician(Player player) {
		magicians.put(player.getName(), new Magician(player));
	}
}
