package nl.dusdavidgames.kingdomfactions.modules.kingdom.event;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.player.player.IPlayerBase;
import nl.dusdavidgames.kingdomfactions.modules.utils.IRank;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RankChangeEvent extends Event {
	public static final HandlerList list = new HandlerList();
	private @Getter IPlayerBase player;

	
	private @Getter IRank rank;
	public RankChangeEvent(IRank rank, IPlayerBase base) {
		this.rank = rank;
		this.player = base;
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
