package nl.dusdavidgames.kingdomfactions.modules.shops.shoplogger;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.ShopLogDatabase;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;

public class ShopLogger {

	private static @Getter @Setter ShopLogger instance;

	private @Getter ArrayList<ShopLog> shopLogs = new ArrayList<>();

	public ShopLogger() {
		setInstance(this);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(KingdomFactionsPlugin.getInstance(), new WriteToDatabaseTask(),
				20 * 3, 20 * 3);
	}

	public void disable() {
		Logger.DEBUG.log("Force saving...");
		while (!shopLogs.isEmpty()) {
			for (int i = 0; i < shopLogs.size(); i++) {
				ShopLogDatabase.getInstance().save(shopLogs.get(i));
				shopLogs.remove(i);
			}
		}

		Logger.DEBUG.log("Forced save done left items " + shopLogs.size());
	}
}