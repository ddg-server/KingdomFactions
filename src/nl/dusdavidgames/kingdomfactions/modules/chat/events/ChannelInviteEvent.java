package nl.dusdavidgames.kingdomfactions.modules.chat.events;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.event.HandlerList;

public class ChannelInviteEvent extends ChannelEvent {

	private static HandlerList handlers = new HandlerList();

	protected @Getter @Setter KingdomFactionsPlayer invited;

	public ChannelInviteEvent(ChatChannel channel, KingdomFactionsPlayer invited, KingdomFactionsPlayer byWho) {
		super(channel, byWho);
		setInvited(invited);
	}

	public static HandlerList getHanlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override public String toString() {
		return "ChannelInviteEvent{" +
				"invited=" + invited +
				'}';
	}
}
