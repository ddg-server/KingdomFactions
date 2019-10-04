package nl.dusdavidgames.kingdomfactions.modules.utils;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.action.IAction;
import org.bukkit.Location;

public class TeleportationAction implements IAction {

    private Location location;
    private int delay;
    private boolean cancelOnMove;
    private KingdomFactionsPlayer player;

    public TeleportationAction(KingdomFactionsPlayer player, Location location, boolean cancelOnMove, int delay) {
        this.delay = delay;
        this.cancelOnMove = cancelOnMove;
        this.location = location;
        this.player = player;
    }

    public Location getLocation() {
        return location;
    }

    public int getDelay() {
        return delay;
    }

    public boolean shouldCancelOnMove() {
        return cancelOnMove;
    }

    public KingdomFactionsPlayer getPlayer() {
        return player;
    }

    public void execute() throws KingdomFactionsException {
        getPlayer().sendActionbar(ChatColor.RED + "Teleporteren...");
        getPlayer().teleport(location);
        cancel();
    }

    public void notifyPlayerOnMovement() {
        if (!shouldCancelOnMove()) {
            return;
        }
        getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je bewoog! De actie is geannuleerd.");
        cancel();
    }

    public void notifyPlayerOnDelayChange() {
        getPlayer().sendActionbar(ChatColor.RED + "Je teleporteert over " + this.delay + " seconde(n)!");
    }

    public void handleDelayChange() {
        if (delay <= 1) {
            try {
                execute();
            } catch (KingdomFactionsException e) {
                e.printStackTrace();
            }
        } else {
            delay -= 1;
            notifyPlayerOnDelayChange();
        }
    }

}
