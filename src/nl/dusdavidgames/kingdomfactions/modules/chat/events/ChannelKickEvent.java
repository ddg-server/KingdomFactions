package nl.dusdavidgames.kingdomfactions.modules.chat.events;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.event.HandlerList;

//Note: this will also call a ChannelLeaveEvent if this event is not cancelled.
public class ChannelKickEvent extends ChannelEvent {

	private static HandlerList handlers = new HandlerList();

	private @Getter @Setter KingdomFactionsPlayer kicked;

	public ChannelKickEvent(ChatChannel channel, KingdomFactionsPlayer kicked, KingdomFactionsPlayer byWho) {
		super(channel, byWho);
		setKicked(kicked);
	}

	public static HandlerList getHanlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override public String toString() {
		return "ChannelKickEvent{" +
				"kicked=" + kicked +
				'}';
	}
}
