package nl.dusdavidgames.kingdomfactions.modules.server;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class PlayerLoginEventListener implements Listener{

	
	
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(e.getPlayer());
		if(!p.hasPermission(ServerModule.getInstance().getServerMode().getPermission())) {
			e.disallow(Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', ServerModule.getInstance().getServerMode().getMessage()));
		}
	}
}
