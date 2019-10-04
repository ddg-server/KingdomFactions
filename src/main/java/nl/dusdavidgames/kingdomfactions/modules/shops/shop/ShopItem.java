package nl.dusdavidgames.kingdomfactions.modules.shops.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import lombok.Data;
import nl.dusdavidgames.kingdomfactions.modules.shops.ShopsModule;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;

@Data
public class ShopItem {

	private ItemStack item;
	private ItemStack displayItem;

	private int buyPrice;
	private int sellPrice;

	private int limit = -1;

	private String itemID;
	
	private int amount;

	public ShopItem(Material material, short value, int amount, int buyPrice, int sellPrice, String displayName,
			List<String> lore, boolean useDisplayName, String enchantments, String enchantmentLevels, int limit,
			String extraData) {
		this.amount = amount;
		ItemStack item = new ItemStack(material, amount, value);
		ItemMeta itemMeta = item.getItemMeta();

		if (useDisplayName) {
			itemMeta.setDisplayName(displayName);
		}

		if (!extraData.equalsIgnoreCase("") && !extraData.equalsIgnoreCase(" ") && material == Material.MOB_SPAWNER) {
			itemMeta.setLore(Arrays.asList("Spawner: " + extraData));
		}

		item.setItemMeta(itemMeta);
		addEnchantments(enchantments, enchantmentLevels, item);
		this.item = item;

		lore.addAll(Arrays.asList("Linker muis knop - kopen " + buyPrice + " coins"));
		if (sellPrice > 0)
			lore.addAll(Arrays.asList("Rechter muis knop - verkopen " + sellPrice + " coins"));

		if (!extraData.equalsIgnoreCase("") && !extraData.equalsIgnoreCase(" ") && material == Material.MOB_SPAWNER) {
			lore.addAll(Arrays.asList(" ", "Spawner: " + extraData));
		}

		this.limit = limit;

		if (isLimited()) {
			lore.addAll(Arrays.asList("Jouw faction kan dit item maximaal " + limit + "X kopen"));
		}

		ItemStack shopItem = new ItemStack(material, amount, value);
		ItemMeta shopItemMeta = shopItem.getItemMeta();
		shopItemMeta.setDisplayName(displayName);
		shopItemMeta.setLore(lore);
		shopItem.setItemMeta(shopItemMeta);
		addEnchantments(enchantments, enchantmentLevels, shopItem);
		this.displayItem = shopItem;

		this.sellPrice = sellPrice;
		this.buyPrice = buyPrice;

		this.itemID = ShopsModule.getInstance().getItemID(item);
	}

	private void addEnchantments(String enchantments, String level, ItemStack is) {
		try {
		if (enchantments == null || level == null)
			return;

		ArrayList<String> enchants = new ArrayList<String>(Arrays.asList(enchantments.split("!")));
		ArrayList<String> levels = new ArrayList<String>(Arrays.asList(level.split("!")));

		if (enchantments.isEmpty())
			return;

		for (int i = 0; i < enchants.size(); i++) {
			if (!enchants.get(i).equalsIgnoreCase(" ") && !enchants.get(i).equalsIgnoreCase("")) {
				if (is.getType() == Material.ENCHANTED_BOOK) {
					EnchantmentStorageMeta meta = (EnchantmentStorageMeta) is.getItemMeta();
					meta.addStoredEnchant(Enchantment.getByName(enchants.get(i)), Integer.parseInt(levels.get(i)), true);
				    
					is.setItemMeta(meta);
				} else
					is.addEnchantment(Enchantment.getByName(enchants.get(i)), Integer.parseInt(levels.get(i)));
			}
		}

		enchants.clear();
		levels.clear();
		} catch(Exception e) {
			if(e instanceof java.lang.IllegalArgumentException) {
			Logger.ERROR.log("couldn't add enchantment " + enchantments + " level " + level + " to " + is.getType().toString());
		    Logger.ERROR.log("Cause: java.lang.IllegalArgumentException");
			} else {
			e.printStackTrace();
			}
		}
	}

	public boolean isLimited() {
		return this.limit != -1;
	}
}