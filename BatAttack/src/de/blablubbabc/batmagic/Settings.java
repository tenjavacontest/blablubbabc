package de.blablubbabc.batmagic;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Settings {
	public int BAT_LIFETIME_TICKS = 400;
	public double BAT_ATTACK_RANGE = 2.5D;
	public double BAT_ATTACK_DAMAGE = 3.0D;
	public int TARGET_PRIORITY_BOOST_PER_ATTACK = 50;
	public double BAT_FLY_SPEED = 1.5;
	public int SWARM_BAT_AMOUNT = 10;
	public int SWARM_COOLDOWN_SECONDS = 7;
	
	// magic wands:
	public ItemStack SWARM_WAND = setItemMeta(new ItemStack(Material.BLAZE_ROD), colorize("&6Bat Swarm Wand"), colorize(Arrays.asList("&3Summons a swarm", "&3of aggressive bats", "&3around you!")));
	public ItemStack SHOOT_WAND = setItemMeta(new ItemStack(Material.STICK), colorize("&6Bat Shoot Wand"), colorize(Arrays.asList("&3Summons an aggressive", "&3bat, which flies", "&3in the direction", "&3you are looking!")));
	
	
	Settings() {
		// load config:
		FileConfiguration config = Main.getInstance().getConfig();
		for (Field field : this.getClass().getFields()) {
			String fieldName = field.getName();
			try {
				Object defaultValue = field.get(this);
				Object actual = config.get(fieldName, defaultValue);
				field.set(this, actual);
				// write value back to file (for defaults):
				config.set(fieldName, actual);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		// save config back to file:
		Main.getInstance().saveConfig();
	}
	
	private ItemStack setItemMeta(ItemStack item, String name, List<String> description) {
		if (item != null) {
			ItemMeta meta = item.getItemMeta();
			if (name != null) {
				meta.setDisplayName(name);
			}
			if (description != null) {
				meta.setLore(description);
			}
			item.setItemMeta(meta);
		}
		return item;
	}
	
	private String colorize(String string) {
		if (string == null) return null;
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	private List<String> colorize(List<String> stringList) {
		if (stringList == null) return null;
		List<String> coloredStrings = new ArrayList<String>();
		for (String string : stringList) {
			coloredStrings.add(ChatColor.translateAlternateColorCodes('&', string));
		}
		return coloredStrings;
	}
}
