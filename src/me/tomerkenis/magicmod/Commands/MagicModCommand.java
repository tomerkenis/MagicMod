package me.tomerkenis.magicmod.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MagicModCommand implements CommandExecutor {

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

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player p = (Player) sender;
		if (p.hasPermission("magic.command.use")) {
			p.getInventory().addItem(
					enchant(createItem(1, Material.STICK, "&6Magic Stick", (short) 0), Enchantment.ARROW_DAMAGE, 2));
		} else p.sendMessage(color("&cYou don't have the magic.command.use permission!"));
		return false;
	}


}
