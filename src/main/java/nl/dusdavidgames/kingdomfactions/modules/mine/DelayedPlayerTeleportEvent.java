package nl.dusdavidgames.kingdomfactions.modules.mine;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Created by Jan on 21-6-2017.
 */
public class DelayedPlayerTeleportEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private @Getter final KingdomFactionsPlayer player;
    private @Getter Location from;
    private @Getter Location to;
    private @Getter final PlayerTeleportEvent.TeleportCause cause;

    public DelayedPlayerTeleportEvent(Player player, Location from, Location to, PlayerTeleportEvent.TeleportCause cause) {
        this.player = PlayerModule.getInstance().getPlayer(player);
        this.from = from;
        this.to = to;
        this.cause = cause;
    }

    public DelayedPlayerTeleportEvent(PlayerTeleportEvent e) {
        this(e.getPlayer(), e.getFrom(), e.getTo(), e.getCause());
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
