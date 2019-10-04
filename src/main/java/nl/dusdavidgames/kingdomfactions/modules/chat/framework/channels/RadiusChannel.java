package nl.dusdavidgames.kingdomfactions.modules.chat.framework.channels;

import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.chat.events.ChannelMessageEvent;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class RadiusChannel extends ChatChannel{

	public RadiusChannel(String colouredName, KingdomFactionsPlayer creator, boolean persistent) {
		super(colouredName, creator, persistent);
	
	}

	
	
	@Override
	public void message(AsyncPlayerChatEvent event) {
		KingdomFactionsPlayer sender = PlayerModule.getInstance().getPlayer(event.getPlayer());
		ChannelRank rank = getRank(sender);
		ChannelMessageEvent cmEvent = new ChannelMessageEvent(this, sender, event.getMessage());
		StringBuilder builder = new StringBuilder();

		    builder.append(ChatColor.BLACK + "(" + ChatColor.WHITE + "Radius" + ChatColor.BLACK + ")");
		    builder.append(" ");
			builder.append(sender.getKingdom().getType().getPrefix());
		if (rank.showName()) {
			builder.append(rank.getName());
		}
		builder.append(rank.getMessageColour());
		builder.append(sender.getName());
		builder.append(": ");
		builder.append(event.getMessage());

		String formattedMessage = builder.toString();
		cmEvent.setFormat(formattedMessage);

		Bukkit.getPluginManager().callEvent(cmEvent);
		sender = cmEvent.getByWho();
		ChannelRank channelRank = cmEvent.getChannel().getRank(sender);
		if (!cmEvent.isCancelled() && channelRank.canTalk()) {
			event.getRecipients().removeIf(receiver -> (!receiver.getWorld().equals(event.getPlayer().getWorld()) || receiver.getLocation().distanceSquared(event.getPlayer().getLocation()) > 300));  	    
			
			if(!event.getRecipients().contains(event.getPlayer())) {
				event.getRecipients().add(event.getPlayer());
			}

			event.setFormat(cmEvent.getFormat());

		} else {

			event.setCancelled(true); //cancel the chat event if the channel message event was cancelled. makes sense, right?
		}
	}

	@Override
	public boolean canLeave() {
		return false;
	}

}
