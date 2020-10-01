package nl.dusdavidgames.kingdomfactions.modules.chat.listeners;

import nl.dusdavidgames.kingdomfactions.modules.chat.events.ChannelPasswordTryEvent;
import nl.dusdavidgames.kingdomfactions.modules.exception.chat.ChannelNotFoundException;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChannelPasswordTryEventListener implements Listener {

	@EventHandler
	public void onChat(ChannelPasswordTryEvent event) throws ChannelNotFoundException {
		if (event.getChannel().tryPassword(event.getAttempt())) {
			event.getChannel().allow(event.getByWho());
			event.getChannel().join(event.getByWho());
		} else {
			event.getByWho().sendMessage(Messages.getInstance().getPrefix() + "Dit wachtwoord was onjuist.");
		}
		event.getByWho().setAction(null);
	}

}
