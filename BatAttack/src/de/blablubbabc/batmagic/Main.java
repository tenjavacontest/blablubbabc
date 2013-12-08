package de.blablubbabc.batmagic;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.blablubbabc.batmagic.listeners.BatListener;
import de.blablubbabc.batmagic.listeners.PlayerListener;
import de.blablubbabc.batmagic.managers.BatManager;
import de.blablubbabc.batmagic.managers.CommandManager;
import de.blablubbabc.batmagic.managers.PlayerManager;

public class Main extends JavaPlugin {

	public static final String SWARM_USE_PERMISSION = "batmagic.wand.swarm.use";
	public static final String SHOOT_USE_PERMISSION = "batmagic.wand.shoot.use";
	public static final String SWARM_CREATE_PERMISSION = "batmagic.wand.swarm.create";
	public static final String SHOOT_CREATE_PERMISSION = "batmagic.wand.shoot.create";
	
	private static Main instance;
	
	private Messages messages;
	private PlayerManager playerManager;
	private BatManager batManager;
	private Settings settings;
	
	public static Main getInstance() {
		return instance;
	}
	
	public String getMessage(Message messageID, String... args) {
		return messages.getMessage(messageID, args);
	}
	
	public PlayerManager getPlayerManager() {
		return playerManager;
	}
	
	public BatManager getBatManager() {
		return batManager;
	}
	
	public Settings getSettings() {
		return settings;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		// load messages:
		this.messages = new Messages(this);
		
		// load settings:
		this.settings = new Settings();
		
		// init PlayerManager:
		this.playerManager = new PlayerManager();
		
		//init BatManager:
		this.batManager = new BatManager();
		
		// register listeners:
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(), this);
		pm.registerEvents(new BatListener(), this);
		
		// register command executor:
		CommandManager commandManager = new CommandManager();
		getCommand("batwand").setExecutor(commandManager);
	}

	@Override
	public void onDisable() {
		instance = null;
	}
}
