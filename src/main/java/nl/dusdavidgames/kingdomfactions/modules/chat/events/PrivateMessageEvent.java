package nl.dusdavidgames.kingdomfactions.modules.chat.events;

import org.bukkit.ChatColor;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class PrivateMessageEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	private boolean cancelled = false;
	private @Getter @Setter KingdomFactionsPlayer sender;
	private @Getter @Setter KingdomFactionsPlayer receiver;
	private @Getter @Setter String message;
	private @Getter @Setter String format;

	public PrivateMessageEvent(KingdomFactionsPlayer sender, KingdomFactionsPlayer receiver, String message) {
		setSender(sender);
		setReceiver(receiver);
		setMessage(message);
		setFormat(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "Privé" + ChatColor.GRAY + "] " + ChatColor.WHITE
				+ sender.getName() + "->" + receiver.getName() + ": " + message);
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}

	@Override
	public String toString() {
		return "PrivateMessageEvent{" + "cancelled=" + cancelled + ", sender=" + sender + ", receiver=" + receiver
				+ ", message='" + message + '\'' + '}';
	}
}
