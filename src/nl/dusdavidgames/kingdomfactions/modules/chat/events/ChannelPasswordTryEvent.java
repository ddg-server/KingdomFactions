package nl.dusdavidgames.kingdomfactions.modules.chat.events;

import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.PasswordAttemptSession;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class ChannelPasswordTryEvent extends ChannelEvent {

	private static HandlerList handlers = new HandlerList();

	private @Getter @Setter String attempt;

	public ChannelPasswordTryEvent(PasswordAttemptSession session, KingdomFactionsPlayer byWho) {
		super(session.getChannel(), byWho);
		setAttempt(session.getPassword());
		
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override public String toString() {
		return "ChannelPasswordTryEvent{" +
				"attempt='" + attempt + '\'' +
				'}';
	}
}
