package nl.dusdavidgames.kingdomfactions.modules.nexus.protection.detection;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import nl.dusdavidgames.kingdomfactions.modules.nexus.event.NexusCreateEvent;
import nl.dusdavidgames.kingdomfactions.modules.nexus.protection.ProtectionModule;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class MoveListener implements Listener{

	
 	
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
			ProtectionModule.getInstance().updateTerritory(p);
		}
	}
    
    @EventHandler
    public void onNexusCreate(NexusCreateEvent e) {
    	for(KingdomFactionsPlayer player : PlayerModule.getInstance().getPlayers()) {
    		ProtectionModule.getInstance().updateTerritory(player);
    	}
    }
}
