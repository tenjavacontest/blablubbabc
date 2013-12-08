package de.blablubbabc.batmagic.batbehaviors;

import de.blablubbabc.batmagic.objects.AttackingBat;

public abstract class BatBehavior {
	/**
	 * Run each tick.
	 * 
	 * @param bat
	 *            the AttackingBat this behavior is assigned to
	 */
	public void onTick(AttackingBat bat) {
		// do nothing by default
	}

	/**
	 * Run every 5 ticks.
	 * 
	 * @param bat
	 *            the AttackingBat this behavior is assigned to
	 */
	public void onTick5(AttackingBat bat) {
		// do nothing by default
	}
	
	/**
	 * Run every 10 ticks.
	 * 
	 * @param bat
	 *            the AttackingBat this behavior is assigned to
	 */
	public void onTick10(AttackingBat bat) {
		// do nothing by default
	}

	/**
	 * Run when the AttackingBat is disposed.
	 * 
	 * @param bat
	 *            the AttackingBat this behavior is assigned to
	 */
	public void onDispose(AttackingBat bat) {
		// do nothing by default
	}
}
