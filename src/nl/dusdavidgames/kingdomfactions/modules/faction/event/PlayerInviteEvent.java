package nl.dusdavidgames.kingdomfactions.modules.faction.event;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.faction.invite.Invite;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerInviteEvent extends Event{

	
	private @Getter KingdomFactionsPlayer player;
	private @Getter KingdomFactionsPlayer inviter;
	private @Getter Invite invite;
	private @Getter Faction faction;
	public PlayerInviteEvent(KingdomFactionsPlayer p, KingdomFactionsPlayer inviter, Invite invite, Faction f) {
		this.player = p;
		this.inviter = inviter;
		this.invite = invite;
		this.faction = f;
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static final HandlerList list = new HandlerList();
	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return list;
	}
	public static HandlerList getHandlerList() {
		return list;
	}

}
