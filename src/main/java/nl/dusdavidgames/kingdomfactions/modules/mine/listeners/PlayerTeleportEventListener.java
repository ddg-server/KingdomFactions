package nl.dusdavidgames.kingdomfactions.modules.mine.listeners;

import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.mine.DelayedPlayerTeleportEvent;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Created by Jan on 21-6-2017.
 */
public class PlayerTeleportEventListener implements Listener {

    @EventHandler
    public void onTeleport(final PlayerTeleportEvent e) {
        Bukkit.getScheduler().runTaskLater(KingdomFactionsPlugin.getInstance(), () -> {
                Logger.DEBUG.log("Calling TeleportEvent");
                Bukkit.getPluginManager().callEvent(new DelayedPlayerTeleportEvent(e));
        }, 40L);
    }

}
