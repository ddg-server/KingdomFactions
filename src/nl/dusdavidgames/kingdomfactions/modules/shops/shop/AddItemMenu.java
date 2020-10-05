package nl.dusdavidgames.kingdomfactions.modules.shops.shop;

import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.ShopDatabase;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class AddItemMenu implements Listener {

	private String type;
	private String level;
	private int buyPrice;
	private int sellprice;
	private boolean useDisplayname;
	private String playerName;
	private String extraData;
	private int limit;

	public AddItemMenu(KingdomFactionsPlayer player, String type, String level, int buyPrice, int sellprice,
			boolean useDisplayname, String extraData, int limit) {
		this.type = type;
		this.level = level;
		this.buyPrice = buyPrice;
		this.sellprice = sellprice;
		this.useDisplayname = useDisplayname;
		this.playerName = player.getName();
		this.extraData = extraData;
		this.limit = limit;
		player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Zet het item op het EERSTE slot!");

		player.openInventory(Bukkit.createInventory(null, InventoryType.DISPENSER, "addItem " + player.getName()));

		Bukkit.getPluginManager().registerEvents(this, KingdomFactionsPlugin.getInstance());
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (!event.getInventory().getTitle().equalsIgnoreCase("addItem " + this.playerName))
			return;

		if (event.getInventory().getItem(0) == null) {
			event.getPlayer().sendMessage("Item NIET gemaakt!");
			HandlerList.unregisterAll(this);
			return;
		}

		ItemStack item = event.getInventory().getItem(0);

		String enchantment = "";
		String enchantmentLevels = "";

		for (Enchantment enchants : item.getEnchantments().keySet()) {
			enchantment = enchantment + "!" + enchants.getName().toUpperCase();
			enchantmentLevels = enchantmentLevels + "!" + item.getEnchantmentLevel(enchants);
		}

		String displayname = "";
		if (item.hasItemMeta())
			if (item.getItemMeta().hasDisplayName())
				displayname = item.getItemMeta().getDisplayName();

		if (item.getType() == Material.ENCHANTED_BOOK) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
			meta.getStoredEnchants().toString();

			for (Enchantment enchants : meta.getStoredEnchants().keySet()) {
				enchantment = enchantment + "!" + enchants.getName().toUpperCase();
				enchantmentLevels = enchantmentLevels + "!" + meta.getStoredEnchants().get(enchants);
			}
		}
		ShopDatabase.getInstance().save(type, level, item.getType(), item.getDurability(), item.getAmount(), buyPrice,
				sellprice, "", displayname, enchantment, enchantmentLevels, useDisplayname, extraData, limit);

		event.getPlayer().sendMessage("Item GEMAAKT :D");

		HandlerList.unregisterAll(this);
	}
}