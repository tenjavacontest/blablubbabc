package de.blablubbabc.commandbooks;

public enum CommandBookData {
	PERMISSIONS_NODE("perm"),
	USES_LEFT("uses");
	
	private String key;
	
	private CommandBookData(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
