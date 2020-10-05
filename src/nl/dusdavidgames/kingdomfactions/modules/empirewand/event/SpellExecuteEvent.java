package nl.dusdavidgames.kingdomfactions.modules.empirewand.event;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.empirewand.Spell;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SpellExecuteEvent extends Event implements Cancellable{

	
	public static final HandlerList list = new HandlerList();
	
	private boolean cancel;
	private @Getter Location location;
	private @Getter Spell spell;
	private @Getter KingdomFactionsPlayer player;
	
	public SpellExecuteEvent(Location location, Spell spell, KingdomFactionsPlayer player) {
		this.location = location;
		this.spell = spell;
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
	

	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return list;
	}

	public static HandlerList getHandlerList() {
		return list;
	}
}
