package nl.dusdavidgames.kingdomfactions.modules.chat.events;

import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class ChannelPasswordSetEvent extends ChannelEvent {

	private static HandlerList handlers = new HandlerList();

	private @Getter @Setter String newPassword;

	public ChannelPasswordSetEvent(ChatChannel channel, KingdomFactionsPlayer byWho, String newPassword) {
		super(channel, byWho);
		setNewPassword(newPassword);
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override public String toString() {
		return "ChannelPasswordSetEvent{" +
				"newPassword='" + newPassword + '\'' +
				'}';
	}
}
