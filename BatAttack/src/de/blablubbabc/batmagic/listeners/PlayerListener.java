package de.blablubbabc.batmagic.listeners;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import de.blablubbabc.batmagic.Main;
import de.blablubbabc.batmagic.Message;
import de.blablubbabc.batmagic.Settings;
import de.blablubbabc.batmagic.batbehaviors.FlyStraightBehavior;
import de.blablubbabc.batmagic.batbehaviors.SimpleDamageBehavior;
import de.blablubbabc.batmagic.objects.Magician;

public class PlayerListener implements Listener {

	private Map<String, Integer> swarmCooldown = new HashMap<String, Integer>();
	
	public PlayerListener() {
		// start cooldown timer:
		Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				Iterator<Map.Entry<String, Integer>> iterator = swarmCooldown.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<String, Integer> entry = iterator.next();
					int secondsLeft = entry.getValue().intValue();
					secondsLeft--;
					if (secondsLeft <= 0) {
						iterator.remove();
					} else {
						entry.setValue(secondsLeft);
					}
				}
			}
		}, 20L, 20L);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Action action = event.getAction();
		if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			ItemStack item = event.getItem();
			if (item != null) {
				Player player = event.getPlayer();
				String playerName = player.getName();
				Settings settings = Main.getInstance().getSettings();
				// check for the different magic wands:
				if (item.isSimilar(settings.SHOOT_WAND)) {
					if (player.hasPermission(Main.SHOOT_USE_PERMISSION)) {
						Magician magician = Main.getInstance().getPlayerManager().getMagician(playerName);
						// spawn single bat:
						Location spawnLocation = player.getEyeLocation();
						magician.spawnBat(spawnLocation, SimpleDamageBehavior.singleton, new FlyStraightBehavior(spawnLocation.getDirection().normalize().multiply(settings.BAT_FLY_SPEED)));
					} else {
						player.sendMessage(Main.getInstance().getMessage(Message.WAND_USE_NO_PERMISSION));
					}
				} else if (item.isSimilar(settings.SWARM_WAND)) {
					if (player.hasPermission(Main.SWARM_USE_PERMISSION)) {
						Integer cooldown = swarmCooldown.get(playerName);
						if (cooldown != null) {
							player.sendMessage(Main.getInstance().getMessage(Message.SWARM_COOLDOWN, String.valueOf(cooldown.intValue())));
							return;
						}
						Magician magician = Main.getInstance().getPlayerManager().getMagician(playerName);
						Location spawnLocation = player.getEyeLocation();
						for (int i = 0; i < settings.SWARM_BAT_AMOUNT; i++) {
							magician.spawnBat(spawnLocation, SimpleDamageBehavior.singleton);
						}
						swarmCooldown.put(playerName, settings.SWARM_COOLDOWN_SECONDS);
					} else {
						player.sendMessage(Main.getInstance().getMessage(Message.WAND_USE_NO_PERMISSION));
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Main.getInstance().getPlayerManager().addMagician(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Main.getInstance().getPlayerManager().addMagician(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Main.getInstance().getPlayerManager().removeMagician(event.getEntity().getName());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Main.getInstance().getPlayerManager().removeMagician(event.getPlayer().getName());
	}
}
