package de.blablubbabc.batmagic.batbehaviors;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import de.blablubbabc.batmagic.Main;
import de.blablubbabc.batmagic.Settings;
import de.blablubbabc.batmagic.objects.AttackingBat;

public class SimpleDamageBehavior extends BatBehavior {
	
	public static final SimpleDamageBehavior singleton = new SimpleDamageBehavior();
	
	public SimpleDamageBehavior() {
	}
	
	@Override
	public void onTick5(AttackingBat bat) {
		int ownerEntityId = bat.getOwner().getEntityId();
		double attackRange = Main.getInstance().getSettings().BAT_ATTACK_RANGE;
		for (Entity someEntity : bat.getEntity().getNearbyEntities(attackRange, attackRange, attackRange)) {
			if (someEntity instanceof LivingEntity) {
				LivingEntity someLivingEntity = (LivingEntity) someEntity;
				int someEntityId = someLivingEntity.getEntityId();
				if (ownerEntityId != someEntityId && !Main.getInstance().getBatManager().isAttackingBat(someEntityId)) {
					if (someLivingEntity.getNoDamageTicks() == 0) {
						someLivingEntity.damage(Main.getInstance().getSettings().BAT_ATTACK_DAMAGE);
						// some small efffect:
						Location location = someLivingEntity.getEyeLocation();
						location.getWorld().playEffect(location, Effect.STEP_SOUND, Material.REDSTONE_BLOCK.getId(), 80);
					}
				}
			}
		}
	}

}
