package me.tomerkenis.magicmod.Events;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DeathEvent implements Listener {
	
	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	public ItemStack createItem(int amount, Material m, String cName, short dValue) {
		ItemStack is = new ItemStack(m, amount, dValue);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(color(cName));
		is.setItemMeta(im);
		return is;
	}

	public ItemStack enchant(ItemStack is, Enchantment enchantment, int level) {
		ItemMeta im = is.getItemMeta();
		im.addEnchant(enchantment, 2, true);
		is.setItemMeta(im);
		return is;
	}

	ArrayList<Player> stickInInv = new ArrayList<>();

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (e.getEntity() instanceof Player) {
			for (ItemStack is : e.getDrops()) {
				if (is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName()
						&& is.getItemMeta().getDisplayName().equalsIgnoreCase(color("&6Magic Stick"))
						&& is.getEnchantments().containsKey(Enchantment.ARROW_DAMAGE)
						&& is.getEnchantments().get(Enchantment.ARROW_DAMAGE) == 2 && is.getType() == Material.STICK) {
					e.getDrops().remove(is);
					stickInInv.add(((Player) e.getEntity()));
					break;
				}
			}
		}
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		if (stickInInv.contains(e.getPlayer())) {
			ItemStack is = createItem(1, Material.STICK, color("&6Magic Stick"), (short) 0);
			is = enchant(is, Enchantment.ARROW_DAMAGE, 2);
			e.getPlayer().getInventory().addItem(is);
			stickInInv.remove(e.getPlayer());
		}
	}

}
