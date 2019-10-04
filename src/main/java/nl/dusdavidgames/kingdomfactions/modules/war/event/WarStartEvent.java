package nl.dusdavidgames.kingdomfactions.modules.war.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WarStartEvent extends Event{
    public static final HandlerList list = new HandlerList();
    public WarStartEvent() {
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
