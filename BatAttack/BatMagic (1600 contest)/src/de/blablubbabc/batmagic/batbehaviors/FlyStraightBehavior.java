package de.blablubbabc.batmagic.batbehaviors;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import de.blablubbabc.batmagic.objects.AttackingBat;

public class FlyStraightBehavior extends BatBehavior {

	private final Vector velocity;
	private Location lastLocation = null;
	
	public FlyStraightBehavior(Vector velocity) {
		this.velocity = velocity;
	}
	
	@Override
	public void onTick(AttackingBat bat) {
		Location currentLocation = bat.getEntity().getLocation();
		if (lastLocation == null || !lastLocation.equals(currentLocation)) {
			lastLocation = currentLocation;
			LivingEntity entity = bat.getEntity();
			entity.setVelocity(velocity);
		} else {
			// bat didn't move much.. let's remove it
			bat.dispose();
		}
	}

}
