package nl.dusdavidgames.kingdomfactions.modules.chat.events;

import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.event.HandlerList;

public class ChannelPasswordRemoveEvent extends ChannelEvent {

	private static HandlerList handlers = new HandlerList();

	public ChannelPasswordRemoveEvent(ChatChannel channel, KingdomFactionsPlayer byWho) {
		super(channel, byWho);
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
