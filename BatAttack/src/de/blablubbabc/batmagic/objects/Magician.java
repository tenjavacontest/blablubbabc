package de.blablubbabc.batmagic.objects;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import de.blablubbabc.batmagic.Main;
import de.blablubbabc.batmagic.batbehaviors.BatBehavior;

public class Magician {
	private final String playerName;
	private final int playerEntityId;
	private final Set<AttackingBat> attackingBats = new HashSet<AttackingBat>();
	
	public Magician(Player player) {
		this.playerName = player.getName();
		this.playerEntityId = player.getEntityId();
	}
	
	public void tick() {
		Player player = Bukkit.getPlayerExact(playerName);
		if (player == null) return;
		Location location = player.getEyeLocation();
		String worldName = location.getWorld().getName();
		
		Iterator<AttackingBat> iterator = attackingBats.iterator();
		while (iterator.hasNext()) {
			AttackingBat entity = iterator.next();
			LivingEntity livingEntity = entity.getEntity();
			if (!livingEntity.isValid() || livingEntity.isDead() || !livingEntity.getEyeLocation().getWorld().getName().equals(worldName)) {
				iterator.remove();
			} else {
				entity.tick();
			}
		}
	}
	
	public void spawnBat(Location spawnLocation, BatBehavior... behaviors) {
		if (spawnLocation == null) return;
		LivingEntity entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.BAT);
		AttackingBat bat = new AttackingBat(this, entity, behaviors);
		attackingBats.add(bat);
		Main.getInstance().getBatManager().registerBat(bat);
	}
	
	public int getEntityId() {
		return playerEntityId;
	}
}
