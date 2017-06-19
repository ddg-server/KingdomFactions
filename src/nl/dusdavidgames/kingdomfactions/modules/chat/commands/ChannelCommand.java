package nl.dusdavidgames.kingdomfactions.modules.chat.commands;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.chat.ChatModule;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.PasswordAttemptSession;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.AdminChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.ListenerChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.OwnerChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.SpeakerChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.SubCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;
import nl.dusdavidgames.kingdomfactions.modules.exception.chat.ChannelNotFoundException;
import nl.dusdavidgames.kingdomfactions.modules.exception.chat.ChannelPersistentException;
import nl.dusdavidgames.kingdomfactions.modules.exception.chat.DuplicateChannelException;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles.ChatProfile;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.MessagePrefix;
import nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.YesNoConfirmation;
import nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.YesNoListener;

public class ChannelCommand extends KingdomFactionsCommand {

	public ChannelCommand(String name, String permission, String info, String usage, boolean sub,
			boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		this.registerSub(
				new SubCommand("create", "kingdomfactions.command.channel.create", "Maak een nieuw chat kanaal aan") {

					@Override
					public void execute(String[] args) throws KingdomFactionsException {
						String name = getArgs()[1];
						if (name.length() > 20) {
							getPlayer().sendMessage(Messages.getInstance().getPrefix()
									+ "Een kanaal naam mag niet langer zijn dan 20 tekens!");
							return;
						}
						ChatChannel c = new ChatChannel(getPlayer().getKingdom().getType().getColor() + name,
								getPlayer(), false);
						try {
							ChatModule.getInstance().addChatChannel(c);
						} catch (DuplicateChannelException e) {
							getPlayer().sendMessage(
									Messages.getInstance().getPrefix() + "Het channel met deze naam bestaat al!");
						}
					}
				});
		this.registerSub(
				new SubCommand("delete", "kingdomfactions.command.channel.delete", "Verwijder een chat kanaal") {

					@Override
					public void execute(String[] args) throws KingdomFactionsException {
						ChatChannel c = ChatChannel.getByName(getArgs()[1]);
						ChannelRank rank = c.getRank(getPlayer());
						if (rank.canDelete() || getPlayer().isStaff()) {
							c.broadcast(Messages.getInstance().getPrefix() + "Chatkanaal wordt verwijderd!");
							try {
								c.delete(getPlayer());
							} catch (ChannelPersistentException e) {
								getPlayer().sendMessage(Messages.getInstance().getPrefix()
										+ "Je hebt geen toestemming om dit kanaal te verwijderen!");
							}
						} else {
							getPlayer().sendMessage(Messages.getInstance().getPrefix()
									+ "Je hebt geen toestemming om dit kanaal te verwijderen!");
						}

					}
				});
		this.registerSub(new SubCommand("join", "kingdomfactions.command.channel.join", "Join een kanaal") {

			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				try {
					ChatChannel c = ChatChannel.getByName(getArgs()[1]);
					if (c.isJoined(getPlayer())) {
						c.setAsCurrentFor(getPlayer(), true);
						return;
					}
					if (c.isAllowed(getPlayer())) {
						c.join(getPlayer());
						c.setAsCurrentFor(getPlayer(), true);
					} else {
						if (c.isPasswordSet()) {
							getPlayer().setAction(new PasswordAttemptSession(getPlayer(), c));
							getPlayer().sendMessage(
									Messages.getInstance().getPrefix() + "Dit kanaal is beveiligd met een wachtwoord!");
							getPlayer().sendMessage(
									Messages.getInstance().getPrefix() + "Typ het wachtwoord om het kanaal te joinen");
						} else {
							getPlayer().sendMessage(Messages.getInstance().getPrefix()
									+ "Je hebt geen toestemming om dit chatkanaal te joinen.");
						}

					}
				} catch (ChannelNotFoundException e) {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Chat kanaal niet gevonden!");
				}
			}
		});
		this.registerSub(new SubCommand("leave", "kingdomfactions.command.channel.leave", "Verlaat een chatkanaal") {

			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				ChatChannel c = ChatChannel.getByName(getArgs()[1]);
				if (c.isJoined(getPlayer())) {
					if (c.canLeave()) {
						c.leave(getPlayer());
						return;
					} else {
						getPlayer().sendMessage(
								Messages.getInstance().getPrefix() + "Je kan dit chatkanaal niet verlaten!");
						return;
					}

				}
				getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je zit niet in dit chatkanaal!");

			}
		});
		this.registerSub(new SubCommand("kick", "kingdomfactions.command.channel.kick", "Schop het chatkanaal") {

			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				KingdomFactionsPlayer player = PlayerModule.getInstance().getPlayer(getArgs()[1]);
				ChatChannel c = ChatChannel.getByName(getArgs()[2]);
				if (player == null || !c.isJoined(player)) {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Deze speler zit niet in dit kanaal");
					return;
				}
				ChannelRank rank = c.getRank(getPlayer());
				if (rank != null && rank.canKick()) {
					c.kick(player, getPlayer());
				} else {
					getPlayer().sendMessage(Messages.getInstance().getPrefix()
							+ "Je hebt geen toestemming om spelers uit dit kanaal te schoppen!");
				}

			}
		});
		this.registerSub(new SubCommand("invite", "kingdomfactions.command.channel.invite",
				"Nodig een speler uit voor het chatkanaal") {

			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				ChatChannel c = ChatChannel.getByName(getArgs()[2]);
				KingdomFactionsPlayer player = PlayerModule.getInstance().getPlayer(getArgs()[1]);
				ChannelRank rank = c.getRank(getPlayer());
				if (player == null) {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + getArgs()[1] + " is niet online");
					return;
				}
				if (c.isJoined(player)) {
					getPlayer().sendMessage(
							Messages.getInstance().getPrefix() + getArgs()[1] + " zit al in dit chatkanaal.");
					return;
				}

				if (rank != null && rank.canInvite() || getPlayer().isStaff()) {
					c.invite(player, getPlayer());
				} else {
					getPlayer().sendMessage(Messages.getInstance().getPrefix()
							+ "Je hebt geen toestemming om spelers uit te nodigen voor dit chatkanaal!");
				}
			}
		});
		this.registerSub(new SubCommand("setrank", "kingdomfactions.command.channel.setrank",
				"Zet een rang van een speler in een chatkanaal") {

			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				ChatChannel c = ChatChannel.getByName(getArgs()[3]);
				ChannelRank rankToGive = parseRank(getArgs()[2]);
				KingdomFactionsPlayer target = PlayerModule.getInstance().getPlayer(getArgs()[1]);
				ChannelRank rank = c.getRank(target);
				if (getPlayer().isStaff() || rank.canSetRank()) {
					getPlayer().sendMessage(Messages.getInstance().getPrefix()
							+ "Je hebt geen toegang om deze speler deze rank te geven!");
					return;
				}
				if (target == null || !c.isJoined(target)) {
					getPlayer()
							.sendMessage(Messages.getInstance().getPrefix() + "Deze speler zit niet in dit chatkanaal");
					return;
				}

				if (rankToGive instanceof OwnerChannelRank) {
					if (getPlayer().isStaff()) {
						c.broadcast(Messages.getInstance().getPrefix() + target.getName() + " is promoveert tot "
								+ rankToGive.getName());
						target.getChatProfile().setRank(rankToGive, c);

					} else if (rank instanceof OwnerChannelRank) {
						new YesNoConfirmation(getPlayer(),
								ChatColor.RED + "Weet je zeker dat je de speler " + target.getName()
										+ " Owner wil maken van dit channel? ",
								"Let op! Je kan de toegang permanent kwijt raken!", "", new YesNoListener() {
									@Override
									public void onDeny(KingdomFactionsPlayer player) {
									}

									@Override
									public void onClose(KingdomFactionsPlayer player) {
									}

									@Override
									public void onAgree(KingdomFactionsPlayer player) {

										target.getChatProfile().setRank(rankToGive, c);

										c.broadcast(Messages.getInstance().getPrefix() + target.getName()
												+ " is promoveert tot " + rankToGive.getName());
									}
								});
					} else {
						getPlayer().sendMessage(Messages.getInstance().getPrefix()
								+ "Je hebt geen toegang om deze speler deze rank te geven!");
					}
				} else {
					if (rank.canSetRank() || target.isStaff()) {
						target.getChatProfile().setRank(rankToGive, c);
						c.broadcast(Messages.getInstance().getPrefix() + target.getName() + " is promoveert tot "
								+ rankToGive.getName());
					}
				}

			}
		});
		this.registerSub(
				new SubCommand("ban", "kingdomfactions.command.channel.ban", "Verban een gebruiker uit het channel") {

					@Override
					public void execute(String[] args) throws KingdomFactionsException {
						ChatChannel channel;
						try {
							channel = ChatChannel.getByName(getArgs()[1]);
							ChannelRank rank = channel.getRank(getPlayer());
							KingdomFactionsPlayer other = PlayerModule.getInstance().getPlayer(getArgs()[2]);
							if (other != null || channel.isJoined(other)) {
								if (rank == null || !rank.canBan()) {
									getPlayer().sendMessage(Messages.getInstance().getPrefix()
											+ "Je hebt geen toegang om deze speler te verbannen!");
								} else {
									channel.ban(other, getPlayer());
								}
							} else {
								getPlayer().sendMessage(Messages.getInstance().getPrefix()
										+ "Deze speler zit niet in dit chat kanaal!");
							}

						} catch (ChannelNotFoundException e) {
							getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Onbekend chat kanaal");
						}

					}
				});
		this.registerSub(new SubCommand("setdefaultrank", "kingdomfactions.command.channel.setdefaultrank",
				"Zet de standaard rank van een chatkanaal!") {

			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				ChatChannel channel;

				try {
					channel = ChatChannel.getByName(getArgs()[1]);
					ChannelRank rank = channel.getRank(getPlayer());
					String target = getArgs()[2];
					ChannelRank targetRank = parseRank(target);
					if (rank != null && rank.canSetRank()) {
						if (targetRank != null) {
							channel.setDefaultRank(targetRank);
							getPlayer().sendMessage(Messages.getInstance().getPrefix()
									+ "Je hebt de standaard rank van dit channel naar " + targetRank.getName());
						} else {
							getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Onbekende Rank! Kies uit ");
						}
					} else {
						getPlayer().sendMessage(Messages.getInstance().getPrefix()
								+ "Je hebt geen toestemming om de standaard rang van dit chatkanaal te wijzigen.");
					}
				} catch (ChannelNotFoundException e) {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Onbekend chatkanaal!");
				}
			}
		});
		this.registerSub(new SubCommand("forcejoin", "kingdomfactions.command.channel.forcejoin",
				"Forceer een speler in een chatkanaal") {

			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				// TODO Auto-generated method stub

			}
		});
		this.registerSub(
				new SubCommand("switchto", "kingdomfactions.command.channel.switchto", "Verzet jouw channel!") {

					@Override
					public void execute(String[] args) throws KingdomFactionsException {
						ChatChannel c = ChatChannel.getByName(getArgs()[1]);
						if (c.isJoined(getPlayer())) {
							c.setAsCurrentFor(getPlayer(), true);
						} else {
							getPlayer().sendMessage(
									Messages.getInstance().getPrefix() + "Je zit momenteel niet in dit chat kanaal!");
						}

					}
				});
		this.registerSub(new SubCommand("list", "kingdomfactions.command.channel.list", "Verkrijg een lijst met jouw huidige channels") {
			
			@Override
			public void execute(String[] args) throws KingdomFactionsException {
			 ChatProfile c = getPlayer().getChatProfile();
			 getPlayer().sendMessage(MessagePrefix.KINGDOMFACTIONS, "Je bent lid van de volgende channels:");
			 getPlayer().sendMessage(c.getChannels().toChannelList());
				
			}
		});
	
	}

	@Override
	public void execute() throws KingdomFactionsException {
		return;

	}

	private ChannelRank parseRank(String rank) {
		switch (rank.toLowerCase()) {

		case "owner":
			return new OwnerChannelRank();
		case "admin":
			return new AdminChannelRank();
		case "speaker":
			return new SpeakerChannelRank();
		case "listener":
			return new ListenerChannelRank();
		}
		return null;
	}

}