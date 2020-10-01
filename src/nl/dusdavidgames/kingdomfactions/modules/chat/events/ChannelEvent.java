package nl.dusdavidgames.kingdomfactions.modules.chat.events;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channels.KingdomChannel;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class ChannelEvent extends Event {

	private static HandlerList handlers = new HandlerList();

	protected @Getter ChatChannel channel;
	protected @Getter @Setter KingdomFactionsPlayer byWho;
	protected @Getter @Setter boolean isCancelled;

	public ChannelEvent(ChatChannel channel, KingdomFactionsPlayer byWho) {
		this.channel = channel;
		setByWho(byWho);
		setCancelled(false);
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public void setChannel(ChatChannel channel) throws IllegalAccessException {
		this.channel = channel;
	}

	public boolean hasKingdom() {
		return channel instanceof KingdomChannel;
	}

	public ChannelRank getByWhoRank() {
		return channel.getRank(byWho);
	}

	@Override public String toString() {
		return "ChannelEvent{" +
				"channel=" + channel +
				", byWho=" + byWho +
				", isCancelled=" + isCancelled +
				'}';
	}
}
