package nl.dusdavidgames.kingdomfactions.modules.chat.events;

import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class ChannelSwitchEvent extends ChannelEvent {

	private static HandlerList handlers = new HandlerList();

	private @Getter @Setter boolean showSwitchMessage;

	public ChannelSwitchEvent(ChatChannel newChannel, KingdomFactionsPlayer byWho, boolean showSwitchMessage) {
		super(newChannel, byWho);
		setShowSwitchMessage(showSwitchMessage);
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public ChatChannel getOldChannel() {
		return ChatChannel.getCurrent(byWho); //can return null if the player had no previous channel!
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override public String toString() {
		return "ChannelSwitchEvent{" +
				"showSwitchMessage=" + showSwitchMessage +
				'}';
	}
}
