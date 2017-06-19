package nl.dusdavidgames.kingdomfactions.modules.chat.events;

import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class ChannelRankSetEvent extends ChannelDefaultRankSetEvent {

	private static HandlerList handlers = new HandlerList();

	private @Getter @Setter KingdomFactionsPlayer rankChangedPlayer;

	public ChannelRankSetEvent(ChatChannel channel, ChannelRank newRank, KingdomFactionsPlayer rankChangedPlayer, KingdomFactionsPlayer byWho) {
		super(channel, newRank, byWho);
		setRankChangedPlayer(rankChangedPlayer);
	}

	public static HandlerList getHanlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override public String toString() {
		return "ChannelRankSetEvent{" +
				"rankChangedKingdomFactionsPlayer=" + rankChangedPlayer +
				'}';
	}
}
