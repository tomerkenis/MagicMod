package me.tomerkenis.magicmod.Events;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.tomerkenis.magicmod.MagicMod;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class InteractEvent implements Listener {

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	HashMap<Player, String[]> moves = MagicMod.moves;

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack is = e.getItem();

		if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName()
				&& is.getItemMeta().getDisplayName().equalsIgnoreCase(color("&6Magic Stick"))
				&& is.getEnchantments().containsKey(Enchantment.ARROW_DAMAGE)
				&& is.getEnchantments().get(Enchantment.ARROW_DAMAGE) == 2 && is.getType() == Material.STICK) {
			if (!moves.containsKey(p)) {
				moves.put(p, new String[0]);
			}

			String action = null;
			if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
				action = "L";
			} else if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				action = "R";
			}

			String[] temp1 = moves.get(p);
			if (temp1.length == 0) {
				String[] temp = new String[1];
				temp[0] = action;
				moves.put(p, temp);

				String msg = null;
				if (temp[0].equalsIgnoreCase("L")) {
					msg = "&cL";
				} else if (temp[0].equalsIgnoreCase("R")) {
					msg = "&aR";
				}
				sendActionBarMessage(p, msg + "&7, &8?&7, &8?");
			} else if (temp1.length == 1) {
				String[] temp = new String[2];
				temp[0] = temp1[0];
				temp[1] = action;
				moves.put(p, temp);

				String msg = null;
				for (String s : temp) {
					if (msg == null) {
						msg = new String();
						if (s.equalsIgnoreCase("L")) {
							msg = "&cL";
						} else if (s.equalsIgnoreCase("R")) {
							msg = "&aR";
						}
					} else {
						if (s.equalsIgnoreCase("L")) {
							msg = msg + "&7, &cL";
						} else if (s.equalsIgnoreCase("R")) {
							msg = msg + "&7, &aR";
						}
					}
				}
				sendActionBarMessage(p, msg + "&7, &8?");
			} else if (temp1.length == 2) {
				String[] temp = new String[3];
				temp[0] = temp1[0];
				temp[1] = temp1[1];
				temp[2] = action;

				String msg = null;
				for (String s : temp) {
					if (msg == null) {
						msg = new String();
						if (s.equalsIgnoreCase("L")) {
							msg = "&cL";
						} else if (s.equalsIgnoreCase("R")) {
							msg = "&aR";
						}
					} else {
						if (s.equalsIgnoreCase("L")) {
							msg = msg + "&7, &cL";
						} else if (s.equalsIgnoreCase("R")) {
							msg = msg + "&7, &aR";
						}
					}
				}
				sendActionBarMessage(p, msg);
				// TODO: Execute the skill (use temp not temp1)
				moves.remove(p);
			} else {
			}

		}

	}

	public void sendActionBarMessage(Player p, String msg) {
		PacketPlayOutChat packet = new PacketPlayOutChat(
				ChatSerializer.a("{\"text\":\"" + msg.replace('&', '§') + "\"}"), (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

}
