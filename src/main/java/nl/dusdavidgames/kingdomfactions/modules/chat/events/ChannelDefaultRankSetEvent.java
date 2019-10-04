package nl.dusdavidgames.kingdomfactions.modules.chat.events;

import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class ChannelDefaultRankSetEvent extends ChannelEvent {

	private static HandlerList handlers = new HandlerList();

	protected @Getter @Setter ChannelRank newRank;

	public ChannelDefaultRankSetEvent(ChatChannel channel, ChannelRank newDefaultRank, KingdomFactionsPlayer byWho) {
		super(channel, byWho);
		setNewRank(newDefaultRank);
	}

	public static HandlerList getHanlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override public String toString() {
		return "ChannelDefaultRankSetEvent{" +
				"newRank=" + newRank +
				'}';
	}
}
