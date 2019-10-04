package nl.dusdavidgames.kingdomfactions.modules.nexus.protection.protectionlisteners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import nl.dusdavidgames.kingdomfactions.modules.nexus.protection.ProtectionModule;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class PlayerBucketEmptyEventListener implements Listener {
	@EventHandler
	public void onBreak(PlayerBucketEmptyEvent e) {
		KingdomFactionsPlayer p =PlayerModule.getInstance().getPlayer(e.getPlayer());
		if(p.getSettingsProfile().hasAdminMode()) return;
		if(!p.canBuild(e.getBlockClicked().getLocation())) {
			e.setCancelled(!ProtectionModule.getInstance().tryInfluence(p, 50));
		} 
	}
}
