package nl.dusdavidgames.kingdomfactions.modules.chat.events;

import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;


public class ChannelUnmuteEvent extends ChannelEvent {

	private static HandlerList handlers = new HandlerList();

	private @Getter @Setter KingdomFactionsPlayer unmuted;

	public ChannelUnmuteEvent(ChatChannel channel, KingdomFactionsPlayer unmuted, KingdomFactionsPlayer byWho) {
		super(channel, byWho);
		setUnmuted(unmuted);
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override public String toString() {
		return "ChannelUnmuteEvent{" +
				"unmuted=" + unmuted +
				'}';
	}
}
