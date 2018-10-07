package me.tomerkenis.magicmod.Configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.tomerkenis.magicmod.MagicMod;

public class Data {
	private static File file = new File(MagicMod.getInstance().getDataFolder(), "data.yml");
	private static FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

	public static void save() {
		try {
			fileConfiguration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static FileConfiguration getConfig() {
		return fileConfiguration;
	}

}
