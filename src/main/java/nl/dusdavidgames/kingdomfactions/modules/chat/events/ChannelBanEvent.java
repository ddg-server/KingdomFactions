package nl.dusdavidgames.kingdomfactions.modules.chat.events;

import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class ChannelBanEvent extends ChannelEvent {

	private static HandlerList handlers = new HandlerList();

	private @Getter @Setter KingdomFactionsPlayer banned;

	public ChannelBanEvent(ChatChannel channel, KingdomFactionsPlayer banned, KingdomFactionsPlayer byWho) {
		super(channel, byWho);
		setBanned(banned);
	}

	public static HandlerList getHanlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override public String toString() {
		return "ChannelBanEvent{" +
				"banned=" + banned +
				'}';
	}
}
