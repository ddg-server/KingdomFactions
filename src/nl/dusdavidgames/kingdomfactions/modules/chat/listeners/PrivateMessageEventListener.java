package nl.dusdavidgames.kingdomfactions.modules.chat.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import nl.dusdavidgames.kingdomfactions.modules.chat.events.PrivateMessageEvent;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;

public class PrivateMessageEventListener implements Listener {

	@EventHandler
	public void onPrivateMessage(PrivateMessageEvent event) {
		if (event.getSender() instanceof Player && event.getReceiver() instanceof Player){
			if (!event.getSender().getKingdom().equals(event.getReceiver().getKingdom()) &&
					!event.getSender().isStaff()) {
				event.setCancelled(true);
				event.getSender().sendMessage(Messages.getInstance().getPrefix() + "Je kunt alleen private messages sturen naar spelers uit je eigen kingdom");
			}
		}
		if (event.getSender() == event.getReceiver()) {
			event.getSender().sendMessage(Messages.getInstance().getPrefix() + "Jij komen zo een persoonlijk bericht sturen tegen jezelf?\nWat denken jij? Whahahauw!");
			event.setCancelled(true);
			return;
		}
	}

}
