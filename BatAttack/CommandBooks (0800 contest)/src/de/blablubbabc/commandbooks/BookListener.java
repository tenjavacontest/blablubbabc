package de.blablubbabc.commandbooks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;

public class BookListener implements Listener {

	private Main plugin;
	
	public BookListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBookEdit(PlayerEditBookEvent event) {
		if (CommandBook.isCommandBook(event.getPreviousBookMeta()) || CommandBook.isCommandBook(event.getNewBookMeta())) {
			Player player = event.getPlayer();
			if (!player.hasPermission(plugin.EDIT_PERMISSION)) {
				event.setCancelled(true);
				player.sendMessage(plugin.getMessages().getMessage(Message.NO_EDIT_PERMISSION));
			}
		}
	}
}
