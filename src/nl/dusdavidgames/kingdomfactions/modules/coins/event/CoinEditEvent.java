package nl.dusdavidgames.kingdomfactions.modules.coins.event;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles.StatisticsProfile;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.ValueEditAction;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CoinEditEvent extends Event{
    public static final HandlerList list = new HandlerList();
    
    private @Getter StatisticsProfile profile;
    private @Getter KingdomFactionsPlayer player;
    private @Getter ValueEditAction action;
    public CoinEditEvent(StatisticsProfile profile, ValueEditAction action) {
    	this.profile = profile;
    	this.player = profile.getPlayer();
    	this.action = action;
    	
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
