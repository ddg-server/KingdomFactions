package nl.dusdavidgames.kingdomfactions.modules.faction.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class FactionSwitchEvent extends Event{
    public static final HandlerList list = new HandlerList();
    private @Getter Faction newFaction;
    private @Getter KingdomFactionsPlayer player;
    public FactionSwitchEvent(KingdomFactionsPlayer p, Faction newFaction) {
    	this.player = p;
    	this.newFaction = newFaction;
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
