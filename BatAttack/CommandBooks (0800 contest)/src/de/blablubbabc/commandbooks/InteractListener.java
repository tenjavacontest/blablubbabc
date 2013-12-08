package de.blablubbabc.commandbooks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class InteractListener implements Listener {
	
	public InteractListener() {
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Action action = event.getAction();
		if (action != Action.PHYSICAL) {
			final Player player = event.getPlayer();
			// allow opening while sneaking:
			if (player.isSneaking()) return;
			ItemStack itemInHand = event.getItem();
			if (itemInHand != null) {
				if (itemInHand.getType() == Material.WRITTEN_BOOK) {
					final CommandBook commandBook = CommandBook.getCommandBook((BookMeta) itemInHand.getItemMeta());
					if (commandBook != null) {
						event.setCancelled(true);
						Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
							
							@Override
							public void run() {
								player.closeInventory();
								commandBook.run(player);
								if (!commandBook.hasUsesLeft()) {
									// uses left is currently unused
								}
							}
						}, 1L);
					}
				}
			}
		}
	}
}
