package de.blablubbabc.batmagic;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class Messages {

	private Map<Message, String> messages = new HashMap<Message, String>();

	public Messages(Main plugin) {
		// load messages:
		File messageFile = new File(plugin.getDataFolder(), "messages.yml");
		YamlConfiguration messageConfig = YamlConfiguration.loadConfiguration(messageFile);

		loadMessage(messageConfig, Message.WAND_USE_NO_PERMISSION, "&cYou don't have the permission to use this wand.");
		loadMessage(messageConfig, Message.WAND_CREATE_NO_PERMISSION, "&cYou don't have the permission to spawn this wand.");
		loadMessage(messageConfig, Message.SWARM_COOLDOWN, "&6You have to wait &c{0} &6seconds before you can use this spell again.");

		// save messages:
		try {
			messageConfig.save(messageFile);
		} catch (IOException e) {
			Log.severe("Wasn't able to save the message file at '" + messageFile.getPath() + "': " + e.getMessage());
		}
	}

	private void loadMessage(ConfigurationSection config, Message messageID, String defaultMessage) {
		String message = config.getString(messageID.name(), defaultMessage);
		messages.put(messageID, ChatColor.translateAlternateColorCodes('&', message));
		// write back (for default values):
		config.set(messageID.name(), message);
	}

	public String getMessage(Message messageID, String... args) {
		String message = messages.get(messageID);

		for (int i = 0; i < args.length; i++) {
			String param = args[i];
			message = message.replace("{" + i + "}", param);
		}
		return message;
	}
}
