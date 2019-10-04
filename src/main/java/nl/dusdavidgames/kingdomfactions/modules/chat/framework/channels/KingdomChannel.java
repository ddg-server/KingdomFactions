package nl.dusdavidgames.kingdomfactions.modules.chat.framework.channels;

import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.chat.ChatModule;
import nl.dusdavidgames.kingdomfactions.modules.chat.events.ChannelMessageEvent;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatMode;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.KingdomChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.exception.chat.ChannelImmutableException;
import nl.dusdavidgames.kingdomfactions.modules.exception.chat.ChannelRankException;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomRank;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.Kingdom;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.ChannelType;

public final class KingdomChannel extends ChatChannel {
	//needs to be final because the methods in KingdomChannel should not be overloaded.
	//also, methods that take a KingdomChannel as an argument should expect an instance of this class, not a subclass.

	private final @Getter Kingdom kingdom; //I left out the setter on purpose. Kingdom channels' kingdom should never change.
	private final @Getter ChannelType type;
	private @Setter @Getter ChatMode mode;

	public KingdomChannel(String formattedName, Kingdom kingdom, ChannelType type) {
	
		super(formattedName, null, true);
	
		this.colouredName = formattedName;
		this.name = kingdom.getType().toString();
		this.id = name;
		this.kingdom = kingdom;
		this.type = type;
		this.mode = ChatMode.EVERYONE;
		this.label = name;

		deleteMotd();
	}

	@Override
	public void message(KingdomFactionsPlayer sender, String rawMessage) {

		if (mode  != ChatMode.EVERYONE && !sender.hasPermission(ChatModule.CHAT_ALWAYS_PERMISSION)){
			if (sender.getMembershipProfile().getKingdomRank() != KingdomRank.SPELER){
				KingdomRank kingdomRank = sender.getKingdomRank();

				if (mode == ChatMode.KONING){
					if (kingdomRank != KingdomRank.KONING){
						return;
					}
				} else if (mode == ChatMode.WACHTER){
					if (kingdomRank != KingdomRank.KONING && kingdomRank != KingdomRank.WACHTER){
						return;
					}

			} else {
				return;
			}
		}

		ChannelMessageEvent event = new ChannelMessageEvent(this, sender, rawMessage);
		StringBuilder builder = new StringBuilder();

		builder.append(colouredName);
		builder.append(" ");

		if (sender.getKingdom() != kingdom) {
			builder.append(sender.getKingdom().getType().getPrefix());
			builder.append(" ");
		}

		ChannelRank rank = getRank(sender);
		
		if (rank.showName()) {
			builder.append(rank.getName());
			builder.append(" ");
		}

		builder.append(rank.getMessageColour());
		builder.append(sender.getName());
		builder.append(": ");
		builder.append(rawMessage);

		String formattedMessage = builder.toString();
		event.setFormat(formattedMessage);
		Bukkit.getPluginManager().callEvent(event);

		if (!event.isCancelled() && rank.canTalk()) {
			event.getChannel().broadcast(event.getFormat());

			for (KingdomFactionsPlayer extraRecipient : event.getExtraRecipients()) {
				event.getChannel().whisperTo(extraRecipient, formattedMessage);
			}
		}

	}
	}

	/**
	 * modifies an AsyncPlayerChatEvent such that it is formatted correctly for this channel,
	 * and only members of this channel receive the message.
	 *
	 * @param event the AsyncPlayerChatEvent.
	 */
	@Override
	public void message(AsyncPlayerChatEvent event) {
		KingdomFactionsPlayer sender = PlayerModule.getInstance().getPlayer(event.getPlayer());
		ChannelRank rank = getRank(sender);


		if (mode  != ChatMode.EVERYONE && !sender.hasPermission(ChatModule.CHAT_ALWAYS_PERMISSION)){
			if (sender.getMembershipProfile().getKingdomRank() != KingdomRank.SPELER) {
				KingdomRank kingdomRank = sender.getKingdomRank();

				if (mode == ChatMode.KONING){
					if (kingdomRank != KingdomRank.KONING){
						event.setCancelled(true);
						return;
					}
				} else if (mode == ChatMode.WACHTER){
					if (kingdomRank != KingdomRank.KONING && kingdomRank != KingdomRank.WACHTER){
						event.setCancelled(true);
						return;
					}
				}
			} else {
				event.setCancelled(true);
				return;
			}
		}

		ChannelMessageEvent cmEvent = new ChannelMessageEvent(this, sender, event.getMessage());
		StringBuilder builder = new StringBuilder();
		builder.append(colouredName);

		if (sender.getKingdom() != kingdom) {
			builder.append(sender.getKingdom().getType().getPrefix());
		}

		if(rank != null) {
			if (rank.showName()) {
				builder.append(rank.getName());
			}
		}
	

		if(rank != null) {
		builder.append(rank.getMessageColour());
		} else {
			builder.append(ChatColor.GRAY);
		}
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

	@Override
	public void join(KingdomFactionsPlayer player) {
		join(player, false);
	}
    @Override
	public void join(KingdomFactionsPlayer player, boolean forced, boolean showConfirmMessage) {
		if (player.getKingdomRank() != KingdomRank.SPELER) {
			try {
				setRankFor(player, new KingdomChannelRank(player.getKingdomRank()));
			} catch (ChannelRankException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		join(player, forced, false, showConfirmMessage);
	}

	@Override
	public boolean leave(KingdomFactionsPlayer player) {
		return leave(player, true, false);
	}

	public boolean hasKingdomRankForThisChannel(KingdomFactionsPlayer player) {
		return player.getKingdomRank() != KingdomRank.SPELER && player.getKingdom().equals(kingdom);
	}

	public KingdomRank getRankIfOwnKingdom(KingdomFactionsPlayer player) {
		return hasKingdomRankForThisChannel(player) ? player.getKingdomRank() : null;
	}

	@Override
	public void setName(String name) throws ChannelImmutableException {
		throw new ChannelImmutableException(this.name);
	}

	@Override
	public void setColouredName(String colouredName) throws ChannelImmutableException {
		throw new ChannelImmutableException(this.colouredName);
	}

	@Override public String toString() {
		return "KingdomChannel{" +
				"kingdom=" + kingdom +
				", type=" + type +
				", mode=" + mode +
				'}';
	}

	@Override
	public ChannelType getChannelType() {
		// TODO Auto-generated method stub
		return ChannelType.KINGDOM;
	}
}

