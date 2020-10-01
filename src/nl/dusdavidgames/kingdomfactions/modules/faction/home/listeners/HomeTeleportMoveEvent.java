package nl.dusdavidgames.kingdomfactions.modules.faction.home.listeners;

import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.pvplog.LogoutAction;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.utils.TeleportationAction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class HomeTeleportMoveEvent implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(e.getPlayer());
		double xfrom = e.getFrom().getX();
		double yfrom = e.getFrom().getY();
		double zfrom = e.getFrom().getZ();
		double xto = e.getTo().getX();
		double yto = e.getTo().getY();
		double zto = e.getTo().getZ();
		if (!(xfrom == xto && yfrom == yto && zfrom == zto)) {
			if (p.hasAction()) {
				if (p.getAction() instanceof TeleportationAction) {
					((TeleportationAction) p.getAction()).notifyPlayerOnMovement();
				} else if(p.getAction() instanceof LogoutAction) {
					p.getAction().cancel();
					p.sendMessage(Messages.getInstance().getPrefix() + "Je bewoog! Je uitlog actie is geannuleerd!");
				}
			}
		}
	}
}
