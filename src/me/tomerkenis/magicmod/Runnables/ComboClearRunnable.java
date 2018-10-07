package me.tomerkenis.magicmod.Runnables;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.tomerkenis.magicmod.MagicMod;

public class ComboClearRunnable extends BukkitRunnable {

	@Override
	public void run() {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (!MagicMod.comboClear.containsKey(p)) {
				if (MagicMod.moves.containsKey(p)) {
					MagicMod.comboClear.put(p, 34);
				}
			} else {
				if (MagicMod.moves.containsKey(p)) {
					MagicMod.comboClear.put(p, MagicMod.comboClear.get(p) - 1);
					if (MagicMod.comboClear.get(p) == -1) {
						MagicMod.comboClear.remove(p);
						MagicMod.moves.remove(p);
					}
				}
			}
		}
	}

}
