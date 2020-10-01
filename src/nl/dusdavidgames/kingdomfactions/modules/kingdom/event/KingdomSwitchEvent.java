package nl.dusdavidgames.kingdomfactions.modules.kingdom.event;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class KingdomSwitchEvent extends Event{
    public static final HandlerList list = new HandlerList();
    private @Getter KingdomFactionsPlayer player;
    public KingdomSwitchEvent(KingdomFactionsPlayer p) {
    	this.player = p;
	}
    
    
	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return list;
	}
	public static HandlerList getHandlerList() {
		return list;
	}
   
}
