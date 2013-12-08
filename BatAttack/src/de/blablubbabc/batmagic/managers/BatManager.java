package de.blablubbabc.batmagic.managers;

import java.util.HashMap;
import java.util.Map;

import de.blablubbabc.batmagic.objects.AttackingBat;

public class BatManager {
	private Map<Integer, AttackingBat> bats = new HashMap<Integer, AttackingBat>();
	
	public BatManager() {
	}
	
	public void registerBat(AttackingBat bat) {
		if (bat == null) return;
		bats.put(bat.getEntity().getEntityId(), bat);
	}
	
	public void unregisterBat(int entityId) {
		bats.remove(entityId);
	}
	
	public AttackingBat getAttackingBat(int entityId) {
		return bats.get(entityId);
	}
	
	public boolean isAttackingBat(int entityId) {
		return bats.get(entityId) != null;
	}
}
