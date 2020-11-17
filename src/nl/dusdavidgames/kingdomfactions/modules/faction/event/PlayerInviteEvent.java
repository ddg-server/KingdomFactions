package nl.dusdavidgames.kingdomfactions.modules.faction.event;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.faction.invite.Invite;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class PlayerInviteEvent extends Event{

	private KingdomFactionsPlayer player;
	private KingdomFactionsPlayer inviter;
	private Invite invite;
	private Faction faction;

	public PlayerInviteEvent(KingdomFactionsPlayer kingdomPlayer, KingdomFactionsPlayer inviter, Invite invite, Faction faction) {
		this.player = kingdomPlayer;
		this.inviter = inviter;
		this.invite = invite;
		this.faction = faction;
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
