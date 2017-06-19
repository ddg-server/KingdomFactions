package nl.dusdavidgames.kingdomfactions.modules.chat.events;

import org.bukkit.event.HandlerList;

import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class ChannelCreateEvent extends ChannelEvent {

	private static HandlerList handlers = new HandlerList();

	public ChannelCreateEvent(ChatChannel channel, KingdomFactionsPlayer player) {
		super(channel, player);
	}

	public static HandlerList getHanlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override
	public void setChannel(ChatChannel channel) throws IllegalAccessException {
		throw new IllegalAccessException("Cannot set the channel for a ChannelCreateEvent");
	}
}
