package nl.dusdavidgames.kingdomfactions.modules.influence.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles.StatisticsProfile;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.ValueEditAction;

public class InfluenceEditEvent extends Event{
    public static final HandlerList list = new HandlerList();
    
    private @Getter StatisticsProfile profile;
    private @Getter KingdomFactionsPlayer player;
    private @Getter ValueEditAction action;
    public InfluenceEditEvent(StatisticsProfile profile, ValueEditAction action) {
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
