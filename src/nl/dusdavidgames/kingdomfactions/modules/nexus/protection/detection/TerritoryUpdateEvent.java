package nl.dusdavidgames.kingdomfactions.modules.nexus.protection.detection;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TerritoryUpdateEvent extends Event{
    public static final HandlerList list = new HandlerList();
    
    private @Getter KingdomFactionsPlayer player;
    public TerritoryUpdateEvent(KingdomFactionsPlayer p) {
    	this.player = p;
	}
    
	@Override
	public HandlerList getHandlers() {
		return list;
	}
	public static HandlerList getHandlerList() {
		return list;
	}
   
}
