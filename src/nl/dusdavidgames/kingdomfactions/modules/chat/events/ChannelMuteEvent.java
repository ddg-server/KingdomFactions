package nl.dusdavidgames.kingdomfactions.modules.chat.events;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.event.HandlerList;

public class ChannelMuteEvent extends ChannelEvent {

	private static HandlerList handlers = new HandlerList();

	private @Getter @Setter KingdomFactionsPlayer muted;

	public ChannelMuteEvent(ChatChannel channel, KingdomFactionsPlayer muted, KingdomFactionsPlayer byWho) {
		super(channel, byWho);
		setMuted(muted);
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override public String toString() {
		return "ChannelMuteEvent{" +
				"muted=" + muted +
				'}';
	}
}
