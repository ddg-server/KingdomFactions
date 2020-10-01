package nl.dusdavidgames.kingdomfactions.modules.shops;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildLevel;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildingType;
import nl.dusdavidgames.kingdomfactions.modules.shops.commands.ShopCommand;
import nl.dusdavidgames.kingdomfactions.modules.shops.events.BlockPlace;
import nl.dusdavidgames.kingdomfactions.modules.shops.shop.Shop;
import nl.dusdavidgames.kingdomfactions.modules.shops.shop.ShopItem;
import nl.dusdavidgames.kingdomfactions.modules.shops.shoplogger.ShopLogger;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ShopsModule {

	private static @Getter @Setter ShopsModule instance;

	private @Getter ArrayList<Shop> shops = new ArrayList<>();

	public ShopsModule() {
		setInstance(this);

		new ShopLogger();

		for (BuildingType t : BuildingType.values()) {
			for (BuildLevel level : BuildLevel.values()) {
				this.shops.add(new Shop(t, level));
			}
		}

		new ShopCommand("shop", "kingdomfactions.command.shop", "Main command for shops", "", true, true)
				.registerCommand();

		Bukkit.getPluginManager().registerEvents(new BlockPlace(), KingdomFactionsPlugin.getInstance());
	}

	public Shop getShop(BuildingType type, BuildLevel level) {
		for (Shop s : shops) {
			if (type == s.getBuildingType() && level == s.getBuildLevel()) {
				return s;
			}
		}
		return null;
	}

	public Shop getShop(String name) {
		for (Shop s : this.shops) {
			if (s.getTITLE().equalsIgnoreCase(name)) {
				return s;
			}
		}
		return null;
	}

	public void reload(CommandSender sender) {
		for (Shop shop : this.shops) {
			sender.sendMessage(
					ChatColor.GOLD + "Herladen van shop " + shop.getBuildingType() + " " + shop.getBuildLevel() + ".");
			shop.reload();
			sender.sendMessage(
					ChatColor.GOLD + "Shop " + shop.getBuildingType() + " " + shop.getBuildLevel() + " herladen!");
		}

		sender.sendMessage(Messages.getInstance().getPrefix() + "Alle shops herladen!");
	}

	public String getItemID(ItemStack is) {
		return is.getDurability() + "" + is.getType() + "" + is.getAmount();
	}

	public boolean canBuy(ShopItem shopItem, Faction faction) {
		if (!faction.getShopLimits().containsKey(shopItem.getItemID())) {
			faction.getShopLimits().put(shopItem.getItemID(), 1);
			return true;
		}

		int current = faction.getShopLimits().get(shopItem.getItemID());
		int max = shopItem.getLimit();

		boolean canBuy = current < max;

		if (canBuy)
			faction.getShopLimits().replace(shopItem.getItemID(), current + 1);

		return canBuy;
	}

	/**
	 * You can check with the time from DB how many hours days minutes etc a
	 * purchase was private void forLater() throws ParseException {
	 * SimpleDateFormat format = new SimpleDateFormat("HH:mm/dd/MM/yyyy"); Date
	 * past = format.parse("time from DB"); Date now = new Date();
	 * 
	 * System.out.println(TimeUnit.MILLISECONDS.toMillis(now.getTime() -
	 * past.getTime()) + " milliseconds ago");
	 * System.out.println(TimeUnit.MILLISECONDS.toMinutes(now.getTime() -
	 * past.getTime()) + " minutes ago");
	 * System.out.println(TimeUnit.MILLISECONDS.toHours(now.getTime() -
	 * past.getTime()) + " hours ago");
	 * System.out.println(TimeUnit.MILLISECONDS.toDays(now.getTime() -
	 * past.getTime()) + " days ago"); }
	 */
}