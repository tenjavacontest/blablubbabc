package de.blablubbabc.batmagic.objects;

import java.util.LinkedHashSet;
import java.util.Set;

import org.bukkit.entity.LivingEntity;

import de.blablubbabc.batmagic.Main;
import de.blablubbabc.batmagic.batbehaviors.BatBehavior;

public class AttackingBat {
	private final Magician owner;
	private final LivingEntity entity;
	private Set<BatBehavior> behaviors = new LinkedHashSet<BatBehavior>();
	
	private int tickCounter = 0;
	
	public AttackingBat(Magician owner, LivingEntity entity, BatBehavior... behaviors) {
		this.owner = owner;
		this.entity = entity;
		for (BatBehavior behavior : behaviors) {
			this.behaviors.add(behavior);
		}
	}
	
	public void tick() {
		if (entity.getTicksLived() > Main.getInstance().getSettings().BAT_LIFETIME_TICKS) {
			this.dispose();
			return;
		}

		tickCounter++;
		tickCounter %= 10;
		
		// run behaviors:
		// every tick:
		for (BatBehavior behavior : behaviors) {
			behavior.onTick(this);
		}
		// every 5 ticks:
		if (tickCounter % 5 == 0) {
			for (BatBehavior behavior : behaviors) {
				behavior.onTick5(this);
			}
		}
		// every 10 ticks:
		if (tickCounter == 0) {
			for (BatBehavior behavior : behaviors) {
				behavior.onTick10(this);
			}
		}
	}
	
	public void dispose() {
		// run dispose behaviors:
		for (BatBehavior behavior : behaviors) {
			behavior.onDispose(this);
		}
		this.entity.remove();
		Main.getInstance().getBatManager().unregisterBat(this.entity.getEntityId());
		
	}
	
	public Magician getOwner() {
		return owner;
	}
	
	public LivingEntity getEntity() {
		return entity;
	}
}
