package de.blablubbabc.commandbooks;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main instance;

	public static Main getInstance() {
		return instance;
	}
	
	public final String EDIT_PERMISSION = "commandbooks.edit";
	
	private Messages messages;
	
	public Messages getMessages() {
		return messages;
	}

	@Override
	public void onEnable() {
		instance = this;
		// load messages:
		this.messages = new Messages(this);
		
		// register listeners:
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new BookListener(this), this);
		pm.registerEvents(new InteractListener(), this);
	}

	@Override
	public void onDisable() {
		instance = null;
	}
}
