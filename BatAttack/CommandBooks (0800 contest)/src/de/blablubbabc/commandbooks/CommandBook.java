package de.blablubbabc.commandbooks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class CommandBook {

	public static CommandBook getCommandBook(ItemStack item) {
		if (!isCommandBook(item)) return null;
		return new CommandBook((BookMeta) item.getItemMeta());
	}
	
	public static CommandBook getCommandBook(BookMeta bookMeta) {
		if (!isCommandBook(bookMeta)) return null;
		return new CommandBook(bookMeta);
	}

	public static boolean isCommandBook(ItemStack item) {
		if (item == null) return false;
		if (item.getType() != Material.WRITTEN_BOOK) return false;
		BookMeta bookMeta = (BookMeta) item.getItemMeta();
		return isCommandBook(bookMeta);
	}

	public static boolean isCommandBook(BookMeta bookMeta) {
		if (bookMeta == null) return false;
		if (!bookMeta.hasPages()) return false;
		String page1 = bookMeta.getPage(1);
		if (page1.startsWith("Command Book") || page1.startsWith("CommandBook")) return true;
		return false;
	}

	private String permissionsNode = null;
	private int usesLeft = -1;
	private List<String> commands = new ArrayList<String>();

	private CommandBook(BookMeta bookMeta) {
		String content = concatPages(bookMeta.getPages());
		String[] lines = content.split("\\n");
		int index = 1;
		// parse optional data:
		for (CommandBookData data : CommandBookData.values()) {
			if (lines.length > index) {
				String nextLine = lines[index];
				if (nextLine.toLowerCase().startsWith(data.getKey())) {
					index++;
					String[] lineSplit = nextLine.split(":\\s*", 2);
					if (lineSplit.length == 2) {
						String value = lineSplit[1];

						// data specific handling:
						switch (data) {
						case PERMISSIONS_NODE:
							this.permissionsNode = value;
							break;
						case USES_LEFT:
							Integer usesLeftInteger = parseInteger(value);
							if (usesLeftInteger != null) this.usesLeft = usesLeftInteger.intValue();
							break;

						default:
							break;
						}
					}
				}
			}
		}

		while (index < lines.length) {
			String line = lines[index];
			if (line.isEmpty() || line.startsWith("#")) continue;
			commands.add(line);
			index++;
		}
	}

	private Integer parseInteger(String string) {
		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public void run(Player executor) {
		if (executor == null) return;
		if (permissionsNode != null && !executor.hasPermission(permissionsNode)) {
			executor.sendMessage(Main.getInstance().getMessages().getMessage(Message.NO_EXECUTE_PERMISSION, permissionsNode));
			return;
		}

		String playerName = executor.getName();
		for (String command : commands) {
			command = command.replace("{player}", playerName);
			if (command.startsWith("@console")) {
				if (command.length() >= 9) {
					command = command.substring(9);
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
				}
			} else {
				executor.chat(command);
			}
		}

		if (usesLeft > 0) {
			usesLeft--;
			
		}
	}

	public void setUsesLeft(int newValue) {
		this.usesLeft = newValue;
	}

	public int getUsesLeft() {
		return usesLeft;
	}

	public boolean hasUsesLeft() {
		// -1 -> unlimited uses
		return usesLeft != 0;
	}

	private String concatPages(List<String> pages) {
		StringBuilder result = new StringBuilder();
		if (pages != null) {
			for (String page : pages) {
				result.append(page);
			}
		}
		return result.toString();
	}
}
