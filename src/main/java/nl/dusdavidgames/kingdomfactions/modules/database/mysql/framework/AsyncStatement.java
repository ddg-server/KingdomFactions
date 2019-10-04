package nl.dusdavidgames.kingdomfactions.modules.database.mysql.framework;

import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.MySQLModule;
import org.bukkit.Bukkit;

/**
 * Created by Jan on 21-6-2017.
 */
public class AsyncStatement {

    public AsyncStatement(String statement) {
        Bukkit.getScheduler().runTaskAsynchronously(KingdomFactionsPlugin.getInstance(), () -> MySQLModule.getInstance().insertQuery(statement));
    }

}
