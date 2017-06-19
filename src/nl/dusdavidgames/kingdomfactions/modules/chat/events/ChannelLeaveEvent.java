package nl.dusdavidgames.kingdomfactions.modules.chat.events;

import org.bukkit.event.HandlerList;

import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class ChannelLeaveEvent extends ChannelEvent {

	private static HandlerList handlers = new HandlerList();

	public ChannelLeaveEvent(ChatChannel channel, KingdomFactionsPlayer byWho) {
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
