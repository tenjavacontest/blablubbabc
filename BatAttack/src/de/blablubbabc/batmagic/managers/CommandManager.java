package de.blablubbabc.batmagic.managers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.blablubbabc.batmagic.Main;
import de.blablubbabc.batmagic.Message;

public class CommandManager implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("This command can only be executed as player.");
			return true;
		}
		Player player = (Player) sender;
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("swarm")) {
				if (player.hasPermission(Main.SWARM_CREATE_PERMISSION)) {
					player.getInventory().addItem(Main.getInstance().getSettings().SWARM_WAND.clone());
				} else {
					player.sendMessage(Main.getInstance().getMessage(Message.WAND_CREATE_NO_PERMISSION));
				}
				return true;
			} else if (args[0].equalsIgnoreCase("shoot")) {
				if (player.hasPermission(Main.SHOOT_CREATE_PERMISSION)) {
					player.getInventory().addItem(Main.getInstance().getSettings().SHOOT_WAND.clone());
				} else {
					player.sendMessage(Main.getInstance().getMessage(Message.WAND_CREATE_NO_PERMISSION));
				}
				return true;
			}
		}
		return false;
	}

}
