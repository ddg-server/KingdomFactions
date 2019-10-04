package nl.dusdavidgames.kingdomfactions.modules.chat.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import nl.dusdavidgames.kingdomfactions.modules.faction.FactionRank;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.event.RankChangeEvent;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
public class RankChangeEventListener implements Listener{

	
	
	@EventHandler
	public void onChange(RankChangeEvent e) {

		if(e.getPlayer().isOnline()) {
			KingdomFactionsPlayer player = (KingdomFactionsPlayer) e.getPlayer();
			
		player.getChatProfile().getHolders().forEach(holder -> holder.getRank().recalcPrefix(e.getRank()));
		}
		if(e.getPlayer().hasFaction()) {
		if(e.getRank() instanceof FactionRank) {
			e.getPlayer().getFaction().getFactionMember(e.getPlayer().getUuid()).setRank((FactionRank) e.getRank());
		}
		}
	}
}
