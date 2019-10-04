package nl.dusdavidgames.kingdomfactions.modules.player.eventhandlers;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.chat.ChatModule;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class PlayerQuitEventListener implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(e.getPlayer());
		if(p.getCombatTracker().isInCombat()) {
	   p.getCombatTracker().handleDisconnect();
		Bukkit.getScheduler().runTaskLater(KingdomFactionsPlugin.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				ChatModule.getInstance().getChannels().forEach(channel -> channel.leave(p, false));
			    p.saveLogOut();
			}
		}, 20*2L);
		} else {
		ChatModule.getInstance().getChannels().forEach(channel -> channel.leave(p, false));
		
	    p.saveLogOut();
		}
		
	}
}
