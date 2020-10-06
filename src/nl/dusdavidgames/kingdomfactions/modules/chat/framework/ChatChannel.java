package nl.dusdavidgames.kingdomfactions.modules.chat.framework;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.chat.ChatModule;
import nl.dusdavidgames.kingdomfactions.modules.chat.events.*;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.DDGStaffChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.ListenerChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.OwnerChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.SpeakerChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.exception.chat.*;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.*;

public class ChatChannel implements IChannelTypeHolder {

	public static final String CURRENTCHANNEL_METADATAKEY = "kingdom2.channel.talking.current"; // value
																								// is
																								// the
																								// channel.
	public static final String CHANNELJOINPERMISSIONPREFIX = "kingdom2.channel.joinpermission.";

	protected final @Getter boolean persistent;
	protected final @Getter String joinPermission;
	protected final @Getter String rankMetadataKey;

	protected @Getter String id;
	public String name; // this is also a unique identifier
	protected String colouredName;
	protected String label;

	protected @Getter @Setter Set<KingdomFactionsPlayer> joinedPlayers;
	protected @Getter @Setter ChannelRank defaultRank;
	protected @Getter @Setter List<String> motd;
	protected @Getter @Setter String password;

	private @Getter ArrayList<UUID> whitelist = new ArrayList<UUID>();

	public ChatChannel(String colouredName, KingdomFactionsPlayer creator, boolean persistent) {

		this.name = ChatColor.stripColor(colouredName).replace("[", "").replace("]", "").replace("{", "").replace("}",
				"");
		this.id = name;
		this.colouredName = ChatColor.GRAY + "[" + colouredName + ChatColor.GRAY + "]";
		if (name.length() > 16) {
			label = name.substring(0, 16);
		} else {
			label = name;
		}

		setJoinedPlayers(new HashSet<>());
		setMotd(new LinkedList<>());
		setDefaultRank(new SpeakerChannelRank());

		this.motd.add("Regels:");
		this.motd.add("Heb respect voor anderen.");
		this.motd.add("Heb plezier.");

		this.persistent = persistent;
		this.joinPermission = CHANNELJOINPERMISSIONPREFIX + name;
		this.rankMetadataKey = "kingdom2.channel." + name + ".rank";

		ChannelCreateEvent event = new ChannelCreateEvent(this, creator);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			if (creator != null) {
				allow(creator);
				join(creator);
				try {
					setRankFor(creator, new OwnerChannelRank());
				} catch (ChannelRankException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			Bukkit.getScheduler().runTask(KingdomFactionsPlugin.getInstance(), () -> {
				try {
					this.delete(null);
				} catch (ChannelPersistentException e) {
					e.printStackTrace();
				}
			});
		}

	}

	/**
	 * tries to get a ChatChannel given a channel name
	 *
	 * @param name
	 *            the channel name
	 * @return the channel, if it exists.
	 * @throws ChannelNotFoundException
	 *             if the channel does not exist.
	 */
	public static ChatChannel getByName(String name) throws ChannelNotFoundException {
		return ChatModule.getInstance().getChannelByName(name);
	}

	/**
	 * gets the channel for a given KingdomFactionsPlayer
	 *
	 * @param KingdomFactionsPlayer
	 *            the KingdomFactionsPlayer
	 * @return the channel, or null if the KingdomFactionsPlayer has no current
	 *         channel.
	 */
	public static ChatChannel getCurrent(KingdomFactionsPlayer KingdomFactionsPlayer) {
		return KingdomFactionsPlayer.getChatProfile().getCurrent();
	}

	/**
	 * removes this channel as the current for the KingdomFactionsPlayer.
	 *
	 * @param KingdomFactionsPlayer
	 *            the KingdomFactionsPlayer
	 */
	public static void removeCurrent(KingdomFactionsPlayer KingdomFactionsPlayer) {
		KingdomFactionsPlayer.getChatProfile().setCurrent(null);
	}

	/**
	 * gets all channels for a given KingdomFactionsPlayer
	 *
	 * @param KingdomFactionsPlayer
	 *            the KingdomFactionsPlayer
	 * @return a collection containing all channels the KingdomFactionsPlayer is
	 *         joined.
	 */
	public static List<ChatChannel> getAll(KingdomFactionsPlayer KingdomFactionsPlayer) {
		return KingdomFactionsPlayer.getChatProfile().getChannels();
	}

	/**
	 * Sends a message to all KingdomFactionsPlayers in the channel.
	 *
	 * @param formattedMessage
	 *            the message
	 */
	public void messageRaw(String formattedMessage) {
		joinedPlayers.forEach(player -> player.sendMessage(formattedMessage));
	}

	/**
	 * Broadcasts a message to this channel. The formatted channel name is
	 * concatenated in front of the message.
	 *
	 * @param anyMessage
	 *            self explanatory
	 */
	public void broadcast(String anyMessage) {
		messageRaw(colouredName + " " + ChatColor.RESET + anyMessage);
	}

	/**
	 * Broadcasts a message to this channel. The given KingdomFactionsPlayers
	 * are excluded from receiving this message. The formatted channel name is
	 * concatenated in front of the message.
	 *
	 * @param anyMessage
	 *            self explanatory
	 */
	public void broadcastExcept(String anyMessage, KingdomFactionsPlayer... excluded) {
		List<KingdomFactionsPlayer> excludedKingdomFactionsPlayers = Arrays.asList(excluded);
		joinedPlayers.stream()
				.filter(KingdomFactionsPlayer -> !excludedKingdomFactionsPlayers.contains(KingdomFactionsPlayer))
				.forEach(KingdomFactionsPlayer -> whisperTo(KingdomFactionsPlayer, anyMessage));
	}

	/**
	 * Make a KingdomFactionsPlayer send a message in this channel.
	 *
	 * @param sender
	 *            the sender
	 * @param colouredKingdomName
	 *            the coloured kingdom name
	 * @param rawMessage
	 *            the message typed by the KingdomFactionsPlayer.
	 */
	public void message(KingdomFactionsPlayer sender, String rawMessage) {
		ChannelMessageEvent event = new ChannelMessageEvent(this, sender, rawMessage);
		StringBuilder builder = new StringBuilder();

		builder.append(colouredName);
		builder.append(" ");

		builder.append(event.getByWho().getFormattedName());
		builder.append(" ");

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
			event.getChannel().broadcast(formattedMessage);
			for (KingdomFactionsPlayer extraRecipient : event.getExtraRecipients()) {
				extraRecipient.sendMessage(formattedMessage);
			}
			// ChatModule.getInstance().getLogger().info(event.getFormat());
		}
	}

	/**
	 * modifies an AsyncKingdomFactionsPlayerChatEvent such that it is formatted
	 * correctly for this channel, and only members of this channel receive the
	 * message.
	 *
	 * @param event
	 *            the AsyncPlayerChatEvent.
	 */
	public void message(AsyncPlayerChatEvent event) {
		ChannelMessageEvent cmEvent = new ChannelMessageEvent(this,
				PlayerModule.getInstance().getPlayer(event.getPlayer()), event.getMessage());
		KingdomFactionsPlayer sender = PlayerModule.getInstance().getPlayer(event.getPlayer());
		StringBuilder builder = new StringBuilder();
        
		builder.append(colouredName);
		builder.append(" ");
		builder.append(PlayerModule.getInstance().getPlayer(event.getPlayer()).getFormattedName());
		builder.append(" ");

		ChannelRank rank = getRank(sender);
		if (rank.showName()) {
			builder.append(rank.getName());
			builder.append(" ");
		}
		builder.append(rank.getMessageColour());
		builder.append(sender.getName());
		builder.append(": ");
		builder.append(event.getMessage());

		cmEvent.setFormat(builder.toString());
		Bukkit.getServer().getPluginManager().callEvent(cmEvent);
		ChannelRank channelRank = cmEvent.getByWhoRank();
		if (!cmEvent.isCancelled() && channelRank.canTalk()) {
			event.getRecipients().retainAll(cmEvent.getChannel().getJoinedPlayers());
			for (KingdomFactionsPlayer player : cmEvent.getExtraRecipients()) {
				event.getRecipients().add(player.getPlayer());
			}
			event.getRecipients().add(sender.getPlayer());
			event.setFormat(cmEvent.getFormat());
		} else {
			event.setCancelled(true);
		}
	}

	/**
	 * whispers a message to a KingdomFactionsPlayer
	 *
	 * @param KingdomFactionsPlayer
	 *            the KingdomFactionsPlayer who receives the message
	 * @param message
	 *            the message
	 */
	public void whisperTo(KingdomFactionsPlayer player, String message) {
		player.sendMessage(String.format("%s %s%s", colouredName, ChatColor.RESET, message));
	}

	/**
	 * Makes a KingdomFactionsPlayer join this channel.
	 *
	 * @param KingdomFactionsPlayer
	 *            the KingdomFactionsPlayer that joins
	 */
	public void join(KingdomFactionsPlayer player) {
		join(player, false);
	}

	/**
	 * Makes a KingdomFactionsPlayer join this channel.
	 *
	 * @param KingdomFactionsPlayer
	 * @param showConfirmationMessage
	 *            whether the KingdomFactionsPlayer should be shown a switch
	 *            message
	 */
	public void join(KingdomFactionsPlayer player, boolean showConfirmationMessage) {
		join(player, false, showConfirmationMessage);
	}

	public void join(KingdomFactionsPlayer player, boolean forced, boolean showConfirmationMessage) {
		join(player, forced, true, showConfirmationMessage);
	}

	/**
	 * Makes a KingdomFactionsPlayer join this channel.
	 *
	 * @param KingdomFactionsPlayer
	 *            the KingdomFactionsPlayer that joins
	 * @param forced
	 *            whether the KingdomFactionsPlayer was forced to join this
	 *            channel
	 * @param showSwitchMessage
	 *            whether the KingdomFactionsPlayer should be shown a switch
	 *            message
	 * @return whether the KingdomFactionsPlayer successfully joined the channel
	 */
	public final boolean join(KingdomFactionsPlayer player, boolean forced, boolean showMotd,
			boolean showSwitchMessage) {
		ChannelJoinEvent event = new ChannelJoinEvent(this, player, forced);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled()) {

			player.getChatProfile().getChannels().add(this);

			if (!event.getChannel().hasRank(player)) {
				event.getChannel().setDefaultRankFor(player);
			}

			event.getChannel().addJoinedPlayer(player);
			event.getChannel().setAsCurrentFor(player, showSwitchMessage);

		}
		return !event.isCancelled();
	}

	/**
	 * Add motd line.
	 *
	 * @param line
	 *            the line
	 */
	public void addMotdLine(String line) {
		motd.add(line);
	}

	/**
	 * Sets motd line.
	 *
	 * @param index
	 *            the index
	 * @param line
	 *            the line
	 */
	public void setMotdLine(int index, String line) {
		motd.set(index, line);
	}

	/**
	 * Remove motd line.
	 *
	 * @param index
	 *            the index
	 */
	public void removeMotdLine(int index) {
		motd.remove(index);
	}

	/**
	 * Delete motd.
	 */
	public void deleteMotd() {
		motd.clear();
	}

	/**
	 * Set a password for this channel.
	 *
	 * @param newPass
	 *            the new password
	 * @param changer
	 *            the KingdomFactionsPlayer who sets the new password
	 */
	public void setNewPassword(String newPass, KingdomFactionsPlayer changer) {
		ChannelPasswordSetEvent event = new ChannelPasswordSetEvent(this, changer, newPass);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			event.getChannel().setPassword(event.getNewPassword());
			event.getByWho().sendMessage(Messages.getInstance().getPrefix() + "Het wachtwoord van het chatkaal is nu: "
					+ event.getNewPassword());
		}
	}

	/**
	 * Checks whether this channel has a password set.
	 *
	 * @return whether the password is set or not.
	 */
	public boolean isPasswordSet() {
		return password != null;
	}

	/**
	 * Remove password.
	 *
	 * @param remover
	 *            the KingdomFactionsPlayer who removes the password
	 */
	public void removePassword(KingdomFactionsPlayer remover) {
		ChannelPasswordRemoveEvent event = new ChannelPasswordRemoveEvent(this, remover);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			event.getChannel().setPassword(null);
			event.getByWho().sendMessage(
					event.getChannel().getColouredName() + ChatColor.WHITE + " heeft nu geen wachtwoord meer.");
		}
	}

	/**
	 * Try password.
	 *
	 * @param attempt
	 *            the attempt
	 * @return whether the attempt was correct
	 */
	public boolean tryPassword(String attempt) {
		return attempt.equalsIgnoreCase(password);
	}

	/**
	 * Makes a KingdomFactionsPlayer leave this channel
	 *
	 * @param KingdomFactionsPlayer
	 * @return
	 */
	public boolean leave(KingdomFactionsPlayer KingdomFactionsPlayer) {
		return leave(KingdomFactionsPlayer, true, true);
	}

	public boolean leave(KingdomFactionsPlayer player, boolean showChannelMessage) {
		return leave(player, false, showChannelMessage);
	}

	/**
	 * Makes a KingdomFactionsPlayer leave this channel.
	 *
	 * @param KingdomFactionsPlayer
	 *            the KingdomFactionsPlayer that leaves
	 * @return whether the KingdomFactionsPlayer has left successfully.
	 */
	public boolean leave(KingdomFactionsPlayer KingdomFactionsPlayer, boolean showConfirmation,
			boolean showChannelMessage) {
		ChannelLeaveEvent event = new ChannelLeaveEvent(this, KingdomFactionsPlayer);
		Bukkit.getPluginManager().callEvent(event);

		if (!event.isCancelled()) {
			boolean wasJoined = event.getChannel().getJoinedPlayers().remove(event.getByWho());

			if (wasJoined) {
				KingdomFactionsPlayer = event.getByWho();

				if (event.getChannel().isCurrentChannelFor(KingdomFactionsPlayer)) {
					removeCurrent(KingdomFactionsPlayer);
		
			//		KingdomFactionsPlayer.getChatProfile().setCurrent(new Randomizer<ChatChannel>(KingdomFactionsPlayer.getChatProfile().getChannels()).result());
				}
				event.getByWho().getChatProfile().getChannels().remove(event.getChannel());

				event.getChannel().removeRankFor(KingdomFactionsPlayer);

				if (showConfirmation) {
					whisperTo(KingdomFactionsPlayer, ChatColor.WHITE + "Je hebt het kanaal verlaten.");
				}

				if (showChannelMessage) {
					event.getChannel().broadcast(
							ChatColor.WHITE + KingdomFactionsPlayer.getName() + " heeft het kanaal verlaten.");
				}

				if (event.getChannel().getJoinedPlayers().isEmpty() && !isPersistent()) {
					try {
						event.getChannel().delete(null);
					} catch (ChannelPersistentException ignored) {
					}
				}
			}
			return wasJoined;
		}
		return false;
	}

	/**
	 * Kicks a KingdomFactionsPlayer from this channel.
	 *
	 * @param KingdomFactionsPlayer
	 *            the KingdomFactionsPlayer to be kicked
	 * @param byWho
	 *            the KingdomFactionsPlayer that kicked
	 * @return whether the KingdomFactionsPlayer was kicked successfully
	 */
	public boolean kick(KingdomFactionsPlayer kicked, KingdomFactionsPlayer byWho) {
		ChannelKickEvent event = new ChannelKickEvent(this, kicked, byWho);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			event.getKicked().sendMessage(Messages.getInstance().getPrefix() + event.getByWho()
					+ " heeft je uit het channel " + event.getChannel() + " geschopt!");
			return event.getChannel().leave(event.getKicked(), false, true);
		}
		return false; // if the event was cancelled.
	}

	/**
	 * Retrieves a rank for a given KingdomFactionsPlayer for this channel.
	 *
	 * @return the rank, or null if the KingdomFactionsPlayer had no rank
	 * @Param KingdomFactionsPlayer the KingdomFactionsPlayer
	 */
	public ChannelRank getRank(KingdomFactionsPlayer sender) {
		return sender.getChatProfile().getRank(this);
	}

	/**
	 * Sets a rank for a given KingdomFactionsPlayer for this channel.
	 *
	 * @param KingdomFactionsPlayer
	 *            the KingdomFactionsPlayer whose rank is set
	 * @param rank
	 *            the rank
	 * @throws ChannelRankException
	 */
	public void setRankFor(KingdomFactionsPlayer player, ChannelRank rank) throws ChannelRankException {
		if (player.isStaff()) {
			player.getChatProfile().setRank(new DDGStaffChannelRank(rank), this);
		} else {
			player.getChatProfile().setRank(rank, this);
		}
	}

	/**
	 * sets the default rank for a given KingdomFactionsPlayer for this channel
	 *
	 * @param KingdomFactionsPlayer
	 *            the player whose rank is set
	 */
	public void setDefaultRankFor(KingdomFactionsPlayer player) {
		player.getChatProfile().removeRank(this);
	}

	/**
	 * removes the rank for this KingdomFactionsPlayer for this channel
	 *
	 * @param KingdomFactionsPlayer
	 *            the KingdomFactionsPlayer whose rank is removed
	 */
	public void removeRankFor(KingdomFactionsPlayer player) {
		player.getChatProfile().removeRank(this);
	}

	/**
	 * checks whether the KingdomFactionsPlayer has a rank for this channel
	 *
	 * @param KingdomFactionsPlayer
	 *            the KingdomFactionsPlayer to be checked
	 * @return whether the KingdomFactionsPlayer has got a rank for this channel
	 */
	public boolean hasRank(KingdomFactionsPlayer player) {
		return player.getChatProfile().getRank(this) != null;
	}

	/**
	 * Checks whether a given KingdomFactionsPlayer has joined the channel.
	 *
	 * @param KingdomFactionsPlayer
	 *            the KingdomFactionsPlayer
	 * @return the boolean
	 */
	public boolean isJoined(KingdomFactionsPlayer player) {
		return joinedPlayers.contains(player);
	}

	/**
	 * forces a KingdomFactionsPlayer to join the channel 'unsafely' no checks
	 * for permissions are done and no messages are displayed.
	 *
	 * @param KingdomFactionsPlayer
	 *            the KingdomFactionsPlayer that secretly joins
	 */
	public void addJoinedPlayer(KingdomFactionsPlayer player) {
		joinedPlayers.add(player);
	}

	/**
	 * switches a given KingdomFactionsPlayer to this channel
	 *
	 * @param KingdomFactionsPlayer
	 *            - the KingdomFactionsPlayer who switches
	 * @param showMessage
	 *            - whether the KingdomFactionsPlayer should be shown a switch
	 *            message
	 * @return whether the KingdomFactionsPlayer was successfully switched
	 */
	public boolean setAsCurrentFor(KingdomFactionsPlayer player, boolean showMessage) {
		ChannelSwitchEvent event = new ChannelSwitchEvent(this, player, showMessage);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			player.getChatProfile().setCurrent(event.getChannel());
			if (event.isShowSwitchMessage()) {
				event.getByWho().sendMessage(Messages.getInstance().getPrefix() + "Je praat nu in chatkanaal: "
						+ event.getChannel().getColouredName());
			}
			return true;
		}
		return false;
	}

	/**
	 * checks whether the KingdomFactionsPlayer is currently in this channel
	 *
	 * @param KingdomFactionsPlayer
	 *            the KingdomFactionsPlayer to be checked
	 * @return whether the KingdomFactionsPlayer is currently talking in this
	 *         channel
	 */
	public boolean isCurrentChannelFor(KingdomFactionsPlayer player) {
		return player.getChatProfile().getCurrent() == this;// a check for
															// pointer equality
															// should be
															// sufficient.
	}

	/**
	 * invites a KingdomFactionsPlayer for this channel
	 *
	 * @param invited
	 *            the KingdomFactionsPlayer that receives an invite
	 * @param byWho
	 *            the KingdomFactionsPlayer that invited
	 */
	public void invite(KingdomFactionsPlayer invited, KingdomFactionsPlayer byWho) {
		ChannelInviteEvent event = new ChannelInviteEvent(this, invited, byWho);
		Bukkit.getPluginManager().callEvent(event);

		if (!event.isCancelled()) {
			event.getChannel().allow(event.getInvited());
			event.getInvited().sendMessage(Messages.getInstance().getPrefix()
					+ "Je hebt een uitnodiging ontvangen voor chatkanaal " + event.getChannel().getName() + ".");

			event.getChannel().broadcast(Messages.getInstance().getPrefix() + event.getByWho().getName() + " heeft "
					+ event.getInvited().getName() + " uitgenodigd!");
		}
	}

	/**
	 * invites a collection of KingdomFactionsPlayers for this channel
	 * 
	 * @param invited
	 */
	public void inviteAll(Collection<KingdomFactionsPlayer> invited, KingdomFactionsPlayer byWho) {
		invited.stream().filter(KingdomFactionsPlayer -> !isJoined(KingdomFactionsPlayer))
				.forEach(KingdomFactionsPlayer -> {
					allow(KingdomFactionsPlayer);
					KingdomFactionsPlayer.sendMessage(Messages.getInstance().getPrefix()
							+ "Je hebt een uitnodiging ontvangen voor chatkanaal " + name + ".");

					byWho.sendMessage(Messages.getInstance().getPrefix()
							+ "Er zijn meerdere spelers uitgenodigd voor dit chatkanaal.");
				});
	}

	/**
	 * allows a KingdomFactionsPlayer to join
	 *
	 * @param KingdomFactionsPlayer
	 *            the KingdomFactionsPlayer who will be allowed to join
	 */

	public void allow(KingdomFactionsPlayer player) {
		this.whitelist.add(player.getUuid());
	}

	/**
	 * disallows a KingdomFactionsPlayer to join the channel
	 *
	 * @param KingdomFactionsPlayer
	 *            the KingdomFactionsPlayer that is being 'un-whitelisted'
	 */
	public void disAllow(KingdomFactionsPlayer player) {
		this.whitelist.remove(player.getUuid());
	}

	/**
	 * checks whether the KingdomFactionsPlayer is allowed to join this channel
	 *
	 * @param KingdomFactionsPlayer
	 *            the KingdomFactionsPlayer to check for
	 * @return whether the KingdomFactionsPlayer is allowed to join
	 */
	public boolean isAllowed(KingdomFactionsPlayer player) {
		return this.whitelist.contains(player.getUuid()) || player.isStaff();
	}

	/**
	 * bans a KingdomFactionsPlayer from this channel
	 *
	 * @param banned
	 *            the banned KingdomFactionsPlayer
	 * @param byWho
	 *            the banner (no not the flag thingy -,-)
	 */
	public void ban(KingdomFactionsPlayer banned, KingdomFactionsPlayer byWho) {
		ChannelBanEvent event = new ChannelBanEvent(this, banned, byWho);
		Bukkit.getPluginManager().callEvent(event);

		if (!event.isCancelled()) {
			event.getChannel().disAllow(event.getBanned());
			event.getBanned().sendMessage(Messages.getInstance().getPrefix() + " Je bent verbannen van chatkanaal "
					+ event.getChannel().getName() + ".");
			event.getChannel().broadcast(Messages.getInstance().getPrefix() + event.getBanned().getName() + " is door "
					+ event.getByWho().getName() + " verbannen van dit chatkanaal!");

			event.getChannel().leave(event.getBanned(), false, false);
		}
	}

	/**
	 * mutes a KingdomFactionsPlayer
	 *
	 * @param muted
	 *            the KingdomFactionsPlayer who gets muted
	 * @param byWho
	 *            the muter
	 */
	public void mute(KingdomFactionsPlayer muted, KingdomFactionsPlayer byWho) {
		ChannelMuteEvent event = new ChannelMuteEvent(this, muted, byWho);
		Bukkit.getPluginManager().callEvent(event);

		if (!event.isCancelled()) {
			try {
				event.getChannel().setRankFor(event.getMuted(), new ListenerChannelRank());
			} catch (ChannelRankException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			event.getChannel().whisperTo(event.getMuted(),
					Messages.getInstance().getPrefix() + "Je kunt niet langer praten in dit chatkanaal.");
			event.getChannel().whisperTo(event.getByWho(),
					Messages.getInstance().getPrefix() + event.getMuted().getName() + " kan niet langer praten.");
		}
	}

	/**
	 * unmutes a KingdomFactionsPlayer
	 *
	 * @param muted
	 *            the muted KingdomFactionsPlayer
	 * @param byWho
	 *            the unmuter
	 */
	public void unmute(KingdomFactionsPlayer unmuted, KingdomFactionsPlayer byWho) {
		ChannelUnmuteEvent event = new ChannelUnmuteEvent(this, unmuted, byWho);
		Bukkit.getPluginManager().callEvent(event);

		if (!event.isCancelled()) {
			try {
				event.getChannel().setRankFor(event.getUnmuted(), defaultRank);
			} catch (ChannelRankException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			event.getChannel().whisperTo(event.getUnmuted(),
					Messages.getInstance().getPrefix() + "Je kunt weer praten!");
			event.getChannel().whisperTo(event.getByWho(),
					Messages.getInstance().getPrefix() + event.getUnmuted().getName() + " kan weer praten.");
		}
	}

	/**
	 * Deletes this channel
	 *
	 * @param byWho
	 *            the KingdomFactionsPlayer that deleted this channel
	 * @throws ChannelException
	 * @note byWho can be null if the channel is deleted when the last
	 *       KingdomFactionsPlayer left.
	 */
	public void delete(KingdomFactionsPlayer byWho) throws ChannelPersistentException {
		ChannelDeleteEvent event = new ChannelDeleteEvent(this, byWho);
		Bukkit.getPluginManager().callEvent(event);

		if (!event.isCancelled()) {

			if (event.getChannel().isPersistent()) {
				throw new ChannelPersistentException(event.getChannel().getName());
                }

			}
			List<KingdomFactionsPlayer> joinedKingdomFactionsPlayers = new LinkedList<>(
					event.getChannel().getJoinedPlayers());
			joinedKingdomFactionsPlayers.forEach(KingdomFactionsPlayer -> {
				event.getChannel().disAllow(KingdomFactionsPlayer);
				event.getChannel().leave(KingdomFactionsPlayer, false, false);
			});
			ChatModule.getInstance().removeChannel(event.getChannel());
	
	}
	
	
	public void deleteIgnorePersitent(KingdomFactionsPlayer byWho) {
			List<KingdomFactionsPlayer> joinedKingdomFactionsPlayers = new LinkedList<>(
				this.getJoinedPlayers());
			joinedKingdomFactionsPlayers.forEach(KingdomFactionsPlayer -> {
			    this.disAllow(KingdomFactionsPlayer);
			  this.leave(KingdomFactionsPlayer, false, false);
			});
			ChatModule.getInstance().removeChannel(this);
	
	}
	

	// Some identifiers
	public String getName() {
		if (this.name == null) {
			try {
				throw new ChannelException("No Name Provided for channel " + this.colouredName);
			} catch (ChannelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return name;
	}

	public void setName(String name) throws ChannelImmutableException {
		this.name = name;
	}

	public String getColouredName() {
		return colouredName;
	}

	public void setColouredName(String colouredName) throws ChannelImmutableException {
		this.colouredName = colouredName;
	}
	
	public boolean canLeave() {
		return true;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		if (label.length() > 16) {
			throw new IllegalArgumentException("Label cannot be longer than 16 characters!");
		}
		this.label = label;
	}

	public void recalcPrefix() {
		return;
	}
	// some convenience methods

	/**
	 * unique checks should be done purely on basis of the name. this is used
	 * when creating new channels (see ChannelCommand.create(...))
	 *
	 * @return whether the other object is an instance of ChatChannel and
	 *         whether the name of the other channel is equal to the current
	 *         channel's name, case ignored.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		return (obj instanceof ChatChannel && ((ChatChannel) obj).getName().equalsIgnoreCase(name));
	}

	/**
	 * To stick to the hashCode convention, this class also needs a custom
	 * hashCode() implementation since is has one for equals(Object obj).
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.toLowerCase().hashCode());
		return result;
	}

}
