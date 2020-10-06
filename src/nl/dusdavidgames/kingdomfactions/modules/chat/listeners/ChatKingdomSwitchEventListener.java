package nl.dusdavidgames.kingdomfactions.modules.chat.listeners;

import nl.dusdavidgames.kingdomfactions.modules.kingdom.event.KingdomSwitchEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatKingdomSwitchEventListener implements Listener {

	@EventHandler
	public void onKingdomSwitch(KingdomSwitchEvent event) {
		if (event.getPlayer() == null) {
			return;
		}
    	 event.getPlayer().getChatProfile().wipeChannels();
	}
}
