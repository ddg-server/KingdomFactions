package nl.dusdavidgames.kingdomfactions.modules.chat.events;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class ChannelMessageEvent extends ChannelEvent implements Cancellable {

	private static HandlerList handlers = new HandlerList();

	private @Getter @Setter String rawMessage;
	private @Getter @Setter String format;
	private @Getter @Setter List<KingdomFactionsPlayer> extraRecipients;

	public ChannelMessageEvent(ChatChannel channel, KingdomFactionsPlayer byWho, String rawMessage) {
		super(channel, byWho);
		setRawMessage(rawMessage);
		setExtraRecipients(new LinkedList<>());
		this.getExtraRecipients().addAll(channel.getJoinedPlayers());
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override
	public String toString() {
		return "ChannelMessageEvent{" + "rawMessage='" + rawMessage + '\'' + ", format='" + format + '\''
				+ ", extraRecipients=" + extraRecipients + '}';
	}
}
