package nl.dusdavidgames.kingdomfactions.modules.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;

public class Item {

	private static @Getter @Setter Item instance;

	public Item() {
		setInstance(this);
	}

	public ItemStack getItem(Material m, String name, int amount) {
		ItemStack i = new ItemStack(m, amount);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		i.setItemMeta(im);
		return i;
	}

	public ItemStack getItem(Material m, String name, int amount, ArrayList<String> lore) {
		ItemStack i = new ItemStack(m, amount);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		i.setItemMeta(im);
		return i;
	}

	public ItemStack getItem(Material m, String name, int amount, ArrayList<String> lore, Enchantment e,
			int enchLevel) {
		ItemStack i = new ItemStack(m, amount);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		i.setItemMeta(im);
		i.addEnchantment(e, enchLevel);
		return i;
	}

	public ItemStack getItem(Material m, String name, int amount, Enchantment e, int enchLevel,
			boolean showEnchantments) {
		ItemStack i = new ItemStack(m, amount);
		ItemMeta im = i.getItemMeta();
		im.addEnchant(e, enchLevel, true);
		if (!showEnchantments) {
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		im.setDisplayName(name);
		i.setItemMeta(im);
		return i;
	}

	public ItemStack getItem(Material m, String name, int amount, Enchantment e, int enchLevel,
			boolean showEnchantments, List<String> lore) {
		ItemStack i = new ItemStack(m, amount);
		ItemMeta im = i.getItemMeta();
		im.addEnchant(e, enchLevel, true);
		im.setLore(lore);
		if (!showEnchantments) {
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		im.setDisplayName(name);
		i.setItemMeta(im);
		return i;
	}

	public ItemStack getItem(Material m, String name, int amount, int in) {
		ItemStack i = new ItemStack(m, amount, (short) in);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		i.setItemMeta(im);
		return i;
	}

	public ItemStack getItem(Material m, String name, int amount, ArrayList<String> lore, int in) {
		ItemStack i = new ItemStack(m, amount, (short) in);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		i.setItemMeta(im);
		return i;
	}

	public ItemStack getItem(Material m, String name, int amount, ArrayList<String> lore, Enchantment e, int enchLevel,
			int in) {
		ItemStack i = new ItemStack(m, amount, (short) in);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		i.setItemMeta(im);
		i.addEnchantment(e, enchLevel);
		return i;
	}

	public ItemStack getItem(Material m, String name, int amount, Enchantment e, int enchLevel,
			boolean showEnchantments, int in) {
		ItemStack i = new ItemStack(m, amount, (short) in);
		ItemMeta im = i.getItemMeta();
		im.addEnchant(e, enchLevel, true);
		if (!showEnchantments) {
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		im.setDisplayName(name);
		i.setItemMeta(im);
		return i;
	}
	public ItemStack getItem(Material m, String name, int amount, HashMap<Enchantment, Integer> enchantments, boolean showEnchantments) {
		ItemStack i = new ItemStack(m, amount);
		ItemMeta im = i.getItemMeta();
		for(Enchantment e : enchantments.keySet()) {
			im.addEnchant(e, enchantments.get(e), true);
		}
		if (!showEnchantments) {
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		im.setDisplayName(name);
		i.setItemMeta(im);
		return i;
	}
   
	public void setPane(KingdomType type, int i, Inventory in) {
	 switch(type) {
	case ADAMANTIUM:
		in.setItem(i, getItem(Material.STAINED_GLASS_PANE, ChatColor.DARK_RED + "A" + ChatColor.DARK_GRAY + "damantium", 1, GlassColor.GRAY));
		break;
	case DOK:
		in.setItem(i, getItem(Material.STAINED_GLASS_PANE, ChatColor.DARK_PURPLE + "Dok", 1, GlassColor.PURPLE));
		break;
	case EREDON:
		in.setItem(i, getItem(Material.STAINED_GLASS_PANE, ChatColor.AQUA + "Eredon", 1, GlassColor.LIGHT_BLUE));
		break;
	case HYVAR:
		in.setItem(i, getItem(Material.STAINED_GLASS_PANE, ChatColor.GOLD + "Hyvar", 1, GlassColor.ORANGE));
		break;
	case MALZAN:
		in.setItem(i, getItem(Material.STAINED_GLASS_PANE, ChatColor.YELLOW + "Malzan", 1, GlassColor.YELLOW));
		break;
	case TILIFIA:
		in.setItem(i, getItem(Material.STAINED_GLASS_PANE, ChatColor.DARK_GREEN + "Tilifia", 1, GlassColor.GREEN));
		break;
	case GEEN:
		in.setItem(i, getItem(Material.STAINED_GLASS_PANE, ChatColor.RED + "Random", 1, GlassColor.RED));
	default:
		break;
	 
	 }
	}
}
