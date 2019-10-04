package nl.dusdavidgames.kingdomfactions.modules.player.deathban;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class DeathBanLoginEventListener implements Listener{

	
	
	@EventHandler
	public void onJoin(PlayerLoginEvent e) {
		DeathBan ban = DeathBanModule.getInstance().getBan(e.getPlayer().getUniqueId());
		
		if(ban == null){
			return;
		}
		
		if(ban.handleBan()) {
			e.disallow(Result.KICK_OTHER, ban.getMessage());
		}
	}
}
