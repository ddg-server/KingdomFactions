package nl.dusdavidgames.kingdomfactions.modules.shops.shop;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.empirewand.EmpireWandModule;

public class EmpireWandShopItem extends ShopItem {

	public EmpireWandShopItem() {
		super(Material.BLAZE_ROD, (short) 0, 1, 100000, 0, ChatColor.RED + "Empire Wand", new ArrayList<String>(), true, "", "", -1, "");
	}

	@Override
	public ItemStack getItem() {
		return EmpireWandModule.getInstance().getEmpireWand();
	}
}