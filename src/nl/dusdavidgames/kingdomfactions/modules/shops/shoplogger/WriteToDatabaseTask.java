package nl.dusdavidgames.kingdomfactions.modules.shops.shoplogger;

import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.ShopLogDatabase;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;
import org.bukkit.Bukkit;

public class WriteToDatabaseTask implements Runnable {

	@Override
	public void run() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomFactionsPlugin.getInstance(), new Runnable() {
			@Override
			public void run() {
				Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomFactionsPlugin.getInstance(), new Runnable() {
					@Override
					public void run() {
						if (ShopLogger.getInstance().getShopLogs().isEmpty())
							return;
						Logger.DEBUG.log("Attempting to save one shop log to database");
						ShopLogDatabase.getInstance().save(ShopLogger.getInstance().getShopLogs().get(0));
						ShopLogger.getInstance().getShopLogs().remove(0);
						Logger.DEBUG.log("saved one shop log to database. "
								+ ShopLogger.getInstance().getShopLogs().size() + " shop logs left.");
					}
				});
			}
		});
	}
}
