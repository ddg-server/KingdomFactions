package nl.dusdavidgames.kingdomfactions.modules.nexus.protection.protectionlisteners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerLeashEntityEvent;

import nl.dusdavidgames.kingdomfactions.modules.nexus.protection.ProtectionModule;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class PlayerLeashEventListener implements Listener{
	
	
	@EventHandler
	public void onLeash(PlayerLeashEntityEvent e) {
		KingdomFactionsPlayer p =PlayerModule.getInstance().getPlayer(e.getPlayer());
		if(p.getSettingsProfile().hasAdminMode()) return;
		if(!p.canBuild(e.getEntity().getLocation())) {
			e.setCancelled(!ProtectionModule.getInstance().tryInfluence(p, 200));
		} 
	}

}
