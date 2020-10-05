package nl.dusdavidgames.kingdomfactions.modules.nexus.protection.protectionlisteners;

import nl.dusdavidgames.kingdomfactions.modules.nexus.protection.ProtectionModule;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class PlayerShearEventListener implements Listener{
	
   @EventHandler
   public void onShear(PlayerShearEntityEvent e) {

			KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(e.getPlayer());
			if(p.getSettingsProfile().hasAdminMode()) return;
			if(!p.canBuild(e.getEntity().getLocation())) {
				e.setCancelled(!ProtectionModule.getInstance().tryInfluence(p, 100));
			} 
   }
}
