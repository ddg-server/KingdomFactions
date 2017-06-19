package nl.dusdavidgames.kingdomfactions.modules.war.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WarStopEvent extends Event{
    public static final HandlerList list = new HandlerList();
    public WarStopEvent() {
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
