package nl.dusdavidgames.kingdomfactions.modules.faction.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class FactionCreateEvent extends Event implements Cancellable{
    public static final HandlerList list = new HandlerList();
	public boolean cancel;
    
    
    public FactionCreateEvent(Faction faction, KingdomFactionsPlayer player) {
	 this.faction = faction;
	 this.player = player;
	}
    
    
    @Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
		
	}
	
	

    private @Getter Faction faction;
    private @Getter KingdomFactionsPlayer player;
    
	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return list;
	}
	public static HandlerList getHandlerList() {
		return list;
	}
}
