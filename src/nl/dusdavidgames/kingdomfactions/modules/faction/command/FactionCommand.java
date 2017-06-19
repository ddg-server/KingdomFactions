package nl.dusdavidgames.kingdomfactions.modules.faction.command;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.DDGStaffChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.FactionChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.SubCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;
import nl.dusdavidgames.kingdomfactions.modules.exception.faction.FactionException;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.UnkownPlayerException;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionMember;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionModule;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionRank;
import nl.dusdavidgames.kingdomfactions.modules.faction.home.Home;
import nl.dusdavidgames.kingdomfactions.modules.faction.invite.Invite;
import nl.dusdavidgames.kingdomfactions.modules.faction.invite.InviteModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.nexus.INexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildLevel;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.IPlayerBase;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.settings.Setting;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.utils.TeleportationAction;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;
import nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.YesNoConfirmation;
import nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.YesNoListener;
import nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.listeners.LeaderListener;

public class FactionCommand extends KingdomFactionsCommand {

	public FactionCommand(String name, String permission, String info, String usage, boolean sub,
			boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		this.registerSub(new SubCommand("create", "kingdomfactions.command.faction", "Maak een Faction!") {

			@Override
			public void execute(String[] args) {
				if (!getPlayer().hasFaction()) {

					String name = getArgs()[1];
					String[] s = new String[] { "Adamantium", "Malzan", "Tilifia", "Dok", "Hyvar", "Eredon", "Radius" };
					for (String st : s) {
						if (name.equalsIgnoreCase(st)) {
							getPlayer().sendMessage(Messages.getInstance().getPrefix()
									+ "Je mag deze Faction naam helaas niet gebruiken!");
							return;
						}
					}

					if (FactionModule.getInstance().getFactionByName(getArgs()[1]) != null) {

						getPlayer().sendMessage(
								Messages.getInstance().getPrefix() + "Deze Faction Naam is momenteel bezet!");
						return;
					}

					if (getPlayer().getKingdom().getType() == KingdomType.GEEN
							|| getPlayer().getKingdom().getType() == KingdomType.ERROR) {
						getPlayer().sendMessage(Messages.getInstance().getPrefix()
								+ "Je moet eerst een kingdom hebben gekozen voordat je een faction kan maken!");
						return;
					}

					if (name.length() > 10) {
						getPlayer().sendMessage(Messages.getInstance().getPrefix()
								+ "Je faction naam mag niet langer zijn 10 karakters!");
						return;
					}

					FactionModule.getInstance().createFaction(getArgs()[1], getPlayer());

				} else {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je hebt al een Faction!");
				}

			}
		});

		this.registerSub(
				new SubCommand(new String[] { "join", "j" }, "kingdomfactions.command.faction", "Join een Faction!") {

					@Override
					public void execute(String[] args) throws KingdomFactionsException {
						Faction f = FactionModule.getInstance().getFactionByName(getArgs()[1]);

						if (getPlayer().getMembershipProfile().hasPendingInviteForFaction(f)) {

							Invite i = getPlayer().getMembershipProfile().getPendingInviteForFaction(f);

							KingdomFactionsPlayer player = getPlayer();

							player.getMembershipProfile().setFaction(f);
							player.setFactionRank(FactionRank.SPELER);
							player.save();
							player.getChatProfile().addChannel(f.getChannel());
							if (player.isStaff()) {
								player.getChatProfile().setRank(
										new DDGStaffChannelRank(new FactionChannelRank(player.getFactionRank())),
										player.getFaction().getChannel());
							} else {
								player.getChatProfile().setRank(new FactionChannelRank(player.getFactionRank()),
										player.getFaction().getChannel());

							}

							getPlayer().setFaction(i.getTargetFaction());
							f.broadcast(Messages.getInstance().getPrefix(),
									"Verwelkom " + getPlayer().getName() + " in de Faction!");

							i.unregister();

						} else {
							getPlayer().sendMessage(
									Messages.getInstance().getPrefix() + "Je hebt geen uitnodiging voor deze Faction!");
						}
					}
				});

		this.registerSub(new SubCommand(new String[] { "nexuses", "nexus", "n" }, this.getPermission(),
				"Vraag een lijst op met alle nexuses die worden beheert door jouw faction!") {

			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				if (getPlayer().hasFaction()) {
                        sendNexusInfo(getPlayer());
				} else {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je hebt geen Faction!");
				}

			}
		});

		this.registerSub(new SubCommand(new String[] { "invite", "inv" }, "kingdomfactions.command.faction",
				"Nodig een speler uit voor jouw faction!") {

			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				if (getPlayer().hasFaction()) {
					if (getPlayer().getMembershipProfile().isFactionMod()) {
						KingdomFactionsPlayer other = PlayerModule.getInstance().getPlayer(getArgs()[1]);

						if (other == null) {
							getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Deze speler is niet online!");
							return;
						}
						if (!getPlayer().getKingdom().getType().equals(other.getKingdom().getType())) {
							getPlayer().sendMessage(Messages.getInstance().getPrefix()
									+ "Je moet in het zelfde Kingdom zitten als deze speler!");
							return;
						}
						if (other.hasFaction()) {
							getPlayer().sendMessage(
									Messages.getInstance().getPrefix() + "Deze speler heeft al een Faction!");
							return;
						}

						if (other == getPlayer()) {
							getPlayer().sendMessage(Messages.getInstance().getPrefix()
									+ "Je kan jezelf niet uitnodigen voor een Faction.. grapjas.");
							return;
						}
						if (other.getMembershipProfile().hasPendingInviteForFaction(getPlayer().getFaction())) {
							Invite i = InviteModule.getInstance().getInvite(getPlayer().getFaction(), getPlayer());
							i.getTargetFaction().broadcast(Messages.getInstance().getPrefix(),
									i.getPlayer() + "'s uitnodiging is geannuleerd door " + getPlayer() + "!");
							getPlayer().getFaction().getInvites().remove(i);
						} else {
							Invite i = new Invite(other, getPlayer(), getPlayer().getFaction());
							getPlayer().getFaction().getInvites().add(i);
							i.getPlayer().sendMessage(Messages.getInstance().getPrefix()
									+ "Je bent uitgenodigd voor de faction " + i.getTargetFaction().getName() + "!");
							i.getTargetFaction().broadcast(Messages.getInstance().getPrefix(), i.getPlayer().getName()
									+ " is uitgenodigd voor de faction door " + i.getInviter().getName() + "!");
							i.sendClickableAcceptMessage();
						}
					} else {
						getPlayer().sendMessage(Messages.getInstance().getPrefix()
								+ "Je moet minimaal een officier zijn voor deze actie!");
					}
				} else {
					getPlayer().sendMessage(
							Messages.getInstance().getPrefix() + "Je moet een Faction hebben voor deze actie!");
				}
			}
		});
		this.registerSub(
				new SubCommand("kick", "kingdomfactions.command.faction", "Verwijder een speler uit de faction!") {

					@Override
					public void execute(String[] args) {
						if (getPlayer().getMembershipProfile().isFactionMod()) {
							IPlayerBase player = null;
							try {
								player = PlayerModule.getInstance().getPlayerBase(getArgs()[1]);
							} catch (UnkownPlayerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (!FactionModule.getInstance().compareFactions(getPlayer().getFaction(),
									player.getFaction())) {
								getPlayer().sendMessage(Messages.getInstance().getPrefix() + player.getName()
										+ " zit niet in jouw Faction!");
								return;
							}
							if (player.getFactionRank() == FactionRank.LEADER) {
								getPlayer().sendMessage(Messages.getInstance().getPrefix()
										+ "Je kan de Faction leider niet uit de faction verwijderen!");
								return;
							}
							if (player.getFactionRank() == FactionRank.OFFICER) {
								if (getPlayer().getMembershipProfile().isFactionLeader()) {
									player.setFaction(null);
									getPlayer().getFaction().broadcast(Messages.getInstance().getPrefix(),
											player.getName() + " is uit de faction verwijderd door "
													+ getPlayer().getName());
									player.save();
									if (player.isOnline()) {
										KingdomFactionsPlayer other = (KingdomFactionsPlayer) player;
										other.getChatProfile().wipeChannels();
									}
								} else {
									getPlayer().sendMessage(Messages.getInstance().getPrefix()
											+ "Je kan als officier geen andere officieren kicken!");
								}
							} else {
								getPlayer().getFaction().broadcast(Messages.getInstance().getPrefix(), player.getName()
										+ " is uit de faction verwijderd door " + getPlayer().getName());
								if (player.isOnline()) {
									KingdomFactionsPlayer other = (KingdomFactionsPlayer) player;
									other.getChatProfile().wipeChannels();
								}
								try {
									player.leaveFaction();
								} catch (FactionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								player.save();
							}
						}

					}
				});
		this.registerSub(new SubCommand("sethome", "kingdomfactions.command.faction", "Zet jouw Faction home!") {

			@Override
			public void execute(String[] args) {
				if (getPlayer().hasFaction()) {
					if (getPlayer().getMembershipProfile().isFactionMod()) {
						INexus n = getPlayer().getCurrentNexus();
						if (n == null) {
							getPlayer().sendMessage(Messages.getInstance().getPrefix()
									+ "Je kan alleen de home zetten in de nexus stad!");
							return;
						}
						if (n instanceof Nexus) {
							Nexus ne = (Nexus) n;
							if (ne.getOwner() == getPlayer().getFaction()) {
								getPlayer().getFaction()
										.setHome(new Home(ne, getPlayer().getFaction(), getPlayer().getLocation()));
								getPlayer().sendMessage(
										Messages.getInstance().getPrefix() + "Je hebt de Faction home gezet!");
							}
						} else {
							getPlayer().sendMessage(
									Messages.getInstance().getPrefix() + "Je mag geen home zetten in de hoofdstad!");
						}
					} else {
						getPlayer().sendMessage(
								Messages.getInstance().getPrefix() + "Je moet Faction Officier zijn voor deze actie!");
					}
				}

			}
		});
		this.registerSub(new SubCommand(new String[] { "home", "h" }, "kingdomfactions.command.faction",
				"Teleporteer naar jouw Faction home!") {

			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				if (getPlayer().hasFaction()) {
					Faction f = getPlayer().getFaction();
					if (f.hasHome()) {
						if(Setting.ALLOW_HOME.isEnabled()) {
						getPlayer().sendMessage(Messages.getInstance().getPrefix()
								+ "Je teleporteert naar jouw home in 10 seconden! Let op! Je mag niet bewegen!");
						getPlayer().setAction(new TeleportationAction(getPlayer(), f.getHome().getLocation(), true, 10));
						} else {
							getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Momenteel is het niet mogelijk om naar jouw Faction home te teleporteren.");
						}
					} else {
						getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Jouw Faction heeft geen home!");
					}
				} else {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je hebt geen Faction!");
				}

			}
		});
		this.registerSub(
				new SubCommand("promote", "kingdomfactions.command.faction", "Promoveer een member tot Officier!") {

					@Override
					public void execute(String[] args) throws KingdomFactionsException {
						IPlayerBase player = PlayerModule.getInstance().getPlayerBase(getArgs()[1]);
						if(getPlayer().getFaction() != player.getFaction()) {
							getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Deze speler zit niet in jouw Faction");
							return;
						}
						if (getPlayer().getMembershipProfile().isFactionAdmin()) {
							if (!(player.getFactionRank() == FactionRank.OFFICER)) {
								player.setFactionRank(FactionRank.OFFICER);
								getPlayer().getFaction().broadcast(Messages.getInstance().getPrefix(), player.getName()
										+ " is gepromoveerd tot Faction Officier door " + getPlayer().getName() + "!");
							} else {
								getPlayer().sendMessage(
										Messages.getInstance().getPrefix() + player.getName() + " is al een Officier!");
							}
						} else {
							getPlayer().sendMessage(Messages.getInstance().getPrefix()
									+ "Je moet minimaal Faction leader zijn voor deze actie!");
						}

					}
				});
		this.registerSub(new SubCommand("demote", "kingdomfactions.command.faction", "Degradeer een Officier!") {

			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				IPlayerBase player = PlayerModule.getInstance().getPlayerBase(getArgs()[1]);
				if(getPlayer().getFaction() != player.getFaction()) {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Deze speler zit niet in jouw Faction");
					return;
				}
				if (getPlayer().getMembershipProfile().isFactionAdmin()) {
					if (player.getFactionRank() == FactionRank.OFFICER) {
						player.setFactionRank(FactionRank.SPELER);
						getPlayer().getFaction().broadcast(Messages.getInstance().getPrefix(),
								player.getName() + " is gedegradeerd door " + getPlayer().getName() + "!");
					} else {
						getPlayer().sendMessage(
								Messages.getInstance().getPrefix() + player.getName() + " is geen officier!");
					}
				} else {
					getPlayer().sendMessage(Messages.getInstance().getPrefix()
							+ "Je moet minimaal Faction leader zijn voor deze actie!");
				}

			}
		});
		this.registerSub(new SubCommand(new String[] { "info", "f", "faction", "who", "informatie" },
				"kingdomfactions.command.faction", "Verkrijg informatie over een Faction!") {

			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				if (args.length == 1) {
					sendInfo(getSender(), getPlayer().getName());
				} else {
					sendInfo(getSender(), getArgs()[1]);
				}

			}
		});
		this.registerSub(new SubCommand("setleader", "kingdomfactions.command.faction",
				"Draag het leiderschap over aan iemand anders!") {

			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				IPlayerBase player = PlayerModule.getInstance().getPlayerBase(getArgs()[1]);
				if (player.hasFaction()) {
					if (getPlayer().getMembershipProfile().isFactionAdmin()) {
						new YesNoConfirmation(getPlayer(),
								ChatColor.RED + "Weet je zeker dat je jouw faction leiderschap wil overdragen aan "
										+ player.getName(),
								Utils.getInstance().getLore(ChatColor.GREEN + "Dit kan niet worden terug gedraait!"),
								Utils.getInstance().getLore(ChatColor.RED + "Annuleer de actie"),
								new LeaderListener(player));
					} else {
						getPlayer().sendMessage(Messages.getInstance().getPrefix()
								+ "Je moet minimaal Faction Leader zijn voor deze actie!");
					}
				} else {
					getPlayer()
							.sendMessage(Messages.getInstance().getPrefix() + "Deze speler zit niet in jouw Faction!");
				}

			}
		});

		this.registerSub(new SubCommand("forceleader", "kingdomfactions.command.faction.forceleader",
				"Forceer een speler naar de Leider rang - Alleen voor bug fixes") {

			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				IPlayerBase base = PlayerModule.getInstance().getPlayerBase(getArgs()[1]);
				base.setFactionRank(FactionRank.LEADER);
				base.save();
				getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je hebt " + base.getName()
						+ " naar de rang LEIDER gezet!");

			}
		});
		
		
		this.registerSub(new SubCommand("forcedemote", "kingdomfactions.command.faction.forcedemote",
				"Forceer een Leader naar de SPELER rang - Alleen voor bug fixes") {

			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				IPlayerBase base = PlayerModule.getInstance().getPlayerBase(getArgs()[1]);
				base.setFactionRank(FactionRank.SPELER);
				base.save();
				getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je hebt " + base.getName()
						+ " naar de rang SPELER gezet!");

			}
		});
		
		
		
		this.registerSub(new SubCommand("disband", "kingdomfactions.command.faction", "Verwijder de faction!") {

			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				if (getPlayer().hasFaction()) {
					if (getPlayer().getMembershipProfile().isFactionAdmin()) {
						new YesNoConfirmation(getPlayer(),
								ChatColor.RED + "Weet je zeker dat je jouw faction wil verwijderen?",
								Utils.getInstance()
										.getLore(ChatColor.GREEN
												+ "Je bent je Faction PERMANENT kwijt! (Dat is erg lang!)"),
								Utils.getInstance().getLore(ChatColor.RED + "Annuleer de actie"), new YesNoListener() {

									@Override
									public void onDeny(KingdomFactionsPlayer player) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onClose(KingdomFactionsPlayer player) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onAgree(KingdomFactionsPlayer player) {
										player.getFaction().broadcast(Messages.getInstance().getPrefix(),
												"De Faction is verwijderd door " + player.getName() + "!");
										player.getFaction().remove();
									}
								});
					}
				}
			}
		});
		this.registerSub(new SubCommand(new String[] { "leave", "verlaat" }, "kingdomfactions.command.faction",
				"Verlaat de faction!") {

			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				if (getPlayer().hasFaction()) {
					if (getPlayer().getMembershipProfile().isFactionLeader()) {
						getPlayer().sendMessage(Messages.getInstance().getPrefix()
								+ "Je kan de Faction niet zomaar verlaten! Gebruik '/faction disband' om de Faction te verwijderen!");
						getPlayer().sendMessage(Messages.getInstance().getPrefix()
								+ "Of, gebruik '/faction setleader' om het leiderschap over te dragen!");
						return;
					}
					getPlayer().getFaction().broadcast(Messages.getInstance().getPrefix(),
							getPlayer().getName() + " heeft de Faction verlaten!");
					getPlayer().leaveFaction();

				} else {
					getPlayer()
							.sendMessage(Messages.getInstance().getPrefix() + "Je hebt geen Faction om te verlaten!");
				}

			}
		});

	}

	private String getPlayer(CommandSender sender, FactionMember player) {
		if (player.isOnline()) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.canSee(player.toBukkitPlayer())) {
					return ChatColor.GREEN + player.getName();
				} else {
					return ChatColor.RED + player.getName();
				}
			}
			return ChatColor.GREEN + player.getName();
		} else {
			return ChatColor.RED + player.getName();
		}
	}

	private void sendMessage(CommandSender sender, Faction f) {
		sender.sendMessage(ChatColor.BLUE + "------------------------");
		sender.sendMessage(ChatColor.BLUE + "Naam: " + ChatColor.WHITE + f.getName());

		if (sender.hasPermission("kingdomfactions.command.faction.info.extra")) {
			sender.sendMessage(ChatColor.BLUE + "Faction ID: " + f.getFactionId());
		}

		try {
			sender.sendMessage(ChatColor.BLUE + "Leider: " + prepareOnlineList(sender, f.getLeaders()));
		} catch (FactionException e) {
			sender.sendMessage(ChatColor.BLUE + "Leider: " + ChatColor.RED + "Geen leider.");
			Logger.WARNING.log("Er ging iets fout! Er is een Faction zonder leider. Faction: " + f.getName() + "/" + f.getFactionId());
			
		}

		if (f.getOfficers().isEmpty()) {
			sender.sendMessage(ChatColor.BLUE + "Officieren: " + ChatColor.RED + "Geen");
		} else
			sender.sendMessage(ChatColor.BLUE + "Officieren: " + prepareOnlineList(sender, f.getOfficers()));

		if (f.getPlayers().isEmpty()) {
			sender.sendMessage(ChatColor.BLUE + "Leden: " + ChatColor.RED + "Geen");
		} else
			sender.sendMessage(ChatColor.BLUE + "Leden: " + prepareOnlineList(sender, f.getPlayers()));
		sender.sendMessage(ChatColor.BLUE + "Kingdom: " + f.getStyle().getPrefix());

		if (f.getNexuses().isEmpty()) {
			sender.sendMessage(ChatColor.BLUE + "Nexuses: " + ChatColor.RED + "Geen");
		} else
			sender.sendMessage(ChatColor.BLUE + "Nexuses: " + f.getNexuses().size());
		for (Nexus n : f.getNexuses()) {
			sender.sendMessage(ChatColor.BLUE + n.getNexusId());
		}

		sender.sendMessage(ChatColor.BLUE + "------------------------");

	}

	private String prepareOnlineList(CommandSender sender, ArrayList<FactionMember> list) {
		String members = "";

		for (int i = 0; i < list.size(); i++) {
			if (i == list.size()) {
				members = members + getPlayer(sender, list.get(i)) + ChatColor.BLUE;
			}

			if ((i + 1) == list.size()) {
				members = members + getPlayer(sender, list.get(i));
			} else
				members = members + getPlayer(sender, list.get(i)) + ChatColor.BLUE + ", ";
		}
		return members;
	}

	private void sendInfo(CommandSender player, String name) {
		if (getFactionMember(name) != null) {
			sendMessage(player, getFactionMember(name).getFaction());
		} else if (FactionModule.getInstance().getFactionByName(name) != null) {
			sendMessage(player, FactionModule.getInstance().getFactionByName(name));
		} else {
			player.sendMessage(Messages.getInstance().getPrefix() + "Onbekende Faction/speler!");
		}

	}

	private FactionMember getFactionMember(String name) {
		for (Faction f : FactionModule.getInstance().getFactions()) {
			for (FactionMember m : f.getMembers()) {
				if (m.getName().equalsIgnoreCase(name)) {
					return m;
				}
			}
		}
		return null;
	}

	private void sendNexusInfo(KingdomFactionsPlayer e) {
		Faction f = e.getFaction();
		
		if (f.getNexuses().isEmpty()) {
			e.sendMessage(Messages.getInstance().getPrefix() + "Jouw Faction heeft geen nexus!");
			return;
		}
		for (Nexus n : f.getNexuses()) {
			e.sendMessage(ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "-----------------------");
			e.sendMessage(ChatColor.BLUE + "Locatie: " + n.getKingdom().getPrefix() + ChatColor.BLUE + "/ "
					+ ChatColor.WHITE + Utils.getInstance().getLocationString(n.getNexusLocation()));
			e.sendMessage(
					ChatColor.BLUE + "Nexus Level: " + ChatColor.WHITE + BuildLevel.getLevel(n.getLevel()).getRoman());
			e.sendMessage(ChatColor.BLUE + "Afstand: " + (int) n.getDistance(e) + "M");
		}
		e.sendMessage(ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "-----------------------");
	}

	@Override
	public void execute() throws KingdomFactionsException {
		// TODO Auto-generated method stub

	}
}
