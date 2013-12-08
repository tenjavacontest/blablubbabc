package de.blablubbabc.batmagic.listeners;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import de.blablubbabc.batmagic.Main;
import de.blablubbabc.batmagic.objects.AttackingBat;

public class BatListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntityType() == EntityType.BAT) {
			Entity entity = event.getEntity();
			AttackingBat attackingBat = Main.getInstance().getBatManager().getAttackingBat(entity.getEntityId());
			if (attackingBat != null) {
				attackingBat.dispose();
				// play some effect:
				Location location = entity.getLocation();
				location.getWorld().playEffect(location, Effect.MOBSPAWNER_FLAMES, 0, 80);
			}
		}
	}
}
