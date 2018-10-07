package me.tomerkenis.magicmod;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.tomerkenis.magicmod.Commands.MagicModCommand;
import me.tomerkenis.magicmod.Events.DeathEvent;
import me.tomerkenis.magicmod.Events.InteractEvent;
import me.tomerkenis.magicmod.Runnables.ComboClearRunnable;

public class MagicMod extends JavaPlugin {

	private static MagicMod instance;
	public static HashMap<Player, String[]> moves = new HashMap<>();
	public static HashMap<Player, Integer> comboClear = new HashMap<>();
	
	@Override
	public void onEnable() {
		getCommand("magic").setExecutor(new MagicModCommand());
		instance = this;
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new InteractEvent(), this);
		pm.registerEvents(new DeathEvent(), this);
		new ComboClearRunnable().runTaskTimer(this, 0, 1);
	}

	public static MagicMod getInstance() {
		return instance;
	}

	@Override
	public void onDisable() {

	}
	
}
