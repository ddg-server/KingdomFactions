package nl.dusdavidgames.kingdomfactions.modules.chat.events;

import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.event.HandlerList;

public class ChannelDeleteEvent extends ChannelEvent {

	private static HandlerList handlers = new HandlerList();

	//byWho can be null since channels get deleted when the last player leaves.
	public ChannelDeleteEvent(ChatChannel channel, KingdomFactionsPlayer byWho) {
		super(channel, byWho);
	}

	public static HandlerList getHanlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
