package nl.dusdavidgames.kingdomfactions.modules.chat.framework.channels;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nl.dusdavidgames.kingdomfactions.modules.chat.events.ChannelMessageEvent;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomModule;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.ChannelType;
import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@EqualsAndHashCode(callSuper = false)
public @Data class FactionChannel extends ChatChannel {

	public FactionChannel(Faction owner, KingdomFactionsPlayer creator, boolean persistent) {
		super(owner.getStyle().getColor() + owner.getName(), creator,
				persistent);

		this.id = owner.getFactionId();
		this.name = owner.getName();
		this.owner = owner;
	}

	private Faction owner;
	public void updateName() {
    this.colouredName = owner.getStyle().getColor() + owner.getName();
	}

	@Override
	public ChannelType getChannelType() {
		// TODO Auto-generated method stub
		return ChannelType.FACTION;
	}
	
	
	@Override
	public void message(AsyncPlayerChatEvent event) {
		KingdomFactionsPlayer sender = PlayerModule.getInstance().getPlayer(event.getPlayer());
		ChannelRank rank = getRank(sender);

		ChannelMessageEvent cmEvent = new ChannelMessageEvent(this, sender, event.getMessage());
		StringBuilder builder = new StringBuilder();
		builder.append(colouredName);

		if (sender.getKingdom() != KingdomModule.getInstance().getKingdom(owner.getStyle())) {
			builder.append(" " +sender.getKingdom().getType().getPrefix());
		}

		if (rank.showName()) {
			builder.append(rank.getName());
		} else {
			builder.append(" ");
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
			event.getRecipients().retainAll(cmEvent.getChannel().getJoinedPlayers());
			for(KingdomFactionsPlayer player : cmEvent.getExtraRecipients()) {
				event.getRecipients().add(player.getPlayer());
			}
			event.getRecipients().add(sender.getPlayer());
			event.setFormat(cmEvent.getFormat());
		} else {
			event.setCancelled(true); //cancel the chat event if the channel message event was cancelled. makes sense, right?
		}
	}


}
