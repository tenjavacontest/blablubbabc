package de.blablubbabc.commandbooks;

public class Log {

	public static void info(String message) {
		Main.getInstance().getLogger().info(message);
	}

	public static void warning(String message) {
		Main.getInstance().getLogger().warning(message);
	}

	public static void severe(String message) {
		Main.getInstance().getLogger().severe(message);
	}
}
