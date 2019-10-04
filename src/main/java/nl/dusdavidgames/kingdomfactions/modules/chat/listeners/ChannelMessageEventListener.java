package nl.dusdavidgames.kingdomfactions.modules.chat.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import nl.dusdavidgames.kingdomfactions.modules.chat.ChatModule;
import nl.dusdavidgames.kingdomfactions.modules.chat.events.ChannelMessageEvent;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class ChannelMessageEventListener implements Listener {

	@EventHandler (priority = EventPriority.HIGH)
	public void onChannelMessage(ChannelMessageEvent event) {
		ChatChannel channel = event.getChannel();
		KingdomFactionsPlayer player = event.getByWho();
		
		int cooldownSeconds = channel.getRank(player).getCooldown();
		if (cooldownSeconds != 0  && !player.hasPermission(ChatModule.CHAT_ALWAYS_PERMISSION)) {
				return;
		}
	}

}
