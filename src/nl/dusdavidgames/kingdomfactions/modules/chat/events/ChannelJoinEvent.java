package nl.dusdavidgames.kingdomfactions.modules.chat.events;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.event.HandlerList;

public class ChannelJoinEvent extends ChannelEvent {

	private static HandlerList handlers = new HandlerList();
	private @Getter @Setter boolean forced;

	public ChannelJoinEvent(ChatChannel channel, KingdomFactionsPlayer byWho, boolean forced) {
		super(channel, byWho);
		setForced(forced);
	}

	public static HandlerList getHanlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override public String toString() {
		return "ChannelJoinEvent{" +
				"forced=" + forced +
				'}';
	}
}
