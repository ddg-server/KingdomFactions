package nl.dusdavidgames.kingdomfactions.modules.nexus.command;

import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.SubCommand;
import nl.dusdavidgames.kingdomfactions.modules.data.types.ObjectData;
import nl.dusdavidgames.kingdomfactions.modules.exception.DataException;
import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;
import nl.dusdavidgames.kingdomfactions.modules.exception.value.ValueException;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.INexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.buildings.BuildAction;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.menu.BuildMenu;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildLevel;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildingType;
import nl.dusdavidgames.kingdomfactions.modules.nexus.transfer.NexusTransfer;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.utils.action.SmallAction;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.NexusType;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;
import nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.YesNoConfirmation;
import nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.YesNoListener;
import org.bukkit.ChatColor;

public class NexusCommand extends KingdomFactionsCommand {

	public NexusCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		this.registerSub(new SubCommand("create", "kingdomfactions.command.nexus.create", "Maak een nexus!") {

			@Override
			public void execute(String[] args) throws DataException {

				KingdomFactionsPlayer player = getPlayer();
				int i = 5;
				i = KingdomFactionsPlugin.getInstance().getDataManager().getInteger("faction.members.min");

				i = 0;// TODO remove this <----

				if (player.hasFaction()) {
					if (player.getMembershipProfile().isFactionLeader()) {
						if (!player.getFaction().hasNexus()) {
							if (!(player.getFaction().getMembers().size() > i)) {
								player.sendMessage(Messages.getInstance().getPrefix() + "Je moet minimaal " + i
										+ " Faction leden hebben voor je een Nexus kan bouwen!");
								return;
							}

							if (!(player.getFaction().getMembers().size() >= 0)) {// 5
																					// TODO
																					// set
																					// to
																					// 5
																					// for
																					// production
								player.sendMessage(Messages.getInstance().getPrefix()
										+ "Je moet minimaal 5 Faction leden hebben voor je een Nexus kan bouwen!");

								return;
							}

							if (player.getKingdomTerritory() == player.getKingdom().getType()) {
								BuildAction action = new BuildAction(getPlayer(), getPlayer().getKingdom().getType());
								player.setAction(action);
								int price = BuildingType.NEXUS.getPrice(player.getKingdomTerritory(), BuildLevel.LEVEL_1);
								getPlayer()
										.sendMessage(ChatColor.RED + "---------------------------------------------");
								getPlayer().sendMessage(
										"Je bent een Bouw-Actie gestart! Sla op de grond, om het gebouw te plaatsen. Let op! Hier komt het midden van het gebouw.");
								getPlayer().sendMessage("Het bouwen van dit gebouw gaat "+price+" coins kosten!");
								getPlayer().sendMessage("Je krijgt later de mogelijkheid om het te annuleren!");
								getPlayer().sendMessage("Gebruik /nexus cancel om de actie nu te annuleren!");
								getPlayer()
										.sendMessage(ChatColor.RED + "---------------------------------------------");
							} else {
								getPlayer().sendMessage(Messages.getInstance().getPrefix()
										+ "Je staat momenteel in een vreemd Kingdom! Je kan hier geen Nexus plaatsen!");
							}
						} else {
							player.sendMessage(Messages.getInstance().getPrefix() + "Jouw Faction heeft al een Nexus!");
						}
					} else {
						player.sendMessage(
								Messages.getInstance().getPrefix() + "Je moet Faction leider zijn voor deze actie!");
					}
				} else {
					player.sendMessage(Messages.getInstance().getPrefix() + "Je hebt geen Faction!");
				}
			}
		});

		this.registerSub(
				new SubCommand("setowner", "kingdomfactions.command.nexus.setowner", "Verzet een Nexus eigenaar!") {

					@Override
					public void execute(String[] args) throws KingdomFactionsException {
						Faction f = FactionModule.getInstance().getFactionByName(getArgs()[1]);
						if (NexusModule.getInstance().transferManager.getTransfer(getPlayer()) != null) {
							getPlayer().sendMessage(Messages.getInstance().getPrefix()
									+ "Je bent al bezig met een Nexus Transfer-actie!");
							return;
						}
						if (f != null) {
							NexusTransfer transfer = NexusModule.getInstance().transferManager
									.createTransfer(getPlayer(), f);
							transfer.getPlayer()
									.sendMessage(Messages.getInstance().getPrefix()
											+ "Je bent een Nexus-Transfer-Actie begonnen! Sla op een Nexus om de Eigenaar te veranderen naar "
											+ transfer.getNewFaction().getName());
						} else {
							getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Onbekende Faction!");
						}

					}
				});
		this.registerSub(new SubCommand("build", "kingdomfactions.command.nexus.build", "Open het bouw menu!") {

			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				KingdomFactionsPlayer player = getPlayer();

				if (player.getCurrentNexus() == null) {
					player.sendMessage(
							Messages.getInstance().getPrefix() + "Je staat momenteel niet in een nexus stad!");
					return;
				}
				if (player.getCurrentNexus().getType() == NexusType.CAPITAL) {
					player.sendMessage(Messages.getInstance().getPrefix()
							+ "Je staat momenteel in een hoofdstad! Een hoofdstad kan geen gebouwen hebben!");
					return;
				}
				Nexus n = (Nexus) player.getCurrentNexus();
				if (!n.getOwner().hasPlayer(player)) {
					player.sendMessage(Messages.getInstance().getPrefix()
							+ "Je staat momenteel in het land van een andere faction!");
					return;
				}
				if (!player.getMembershipProfile().isFactionAdmin()) {
					player.sendMessage(
							Messages.getInstance().getPrefix() + "Je moet de Faction leider zijn voor deze actie!");
				}
				BuildMenu.getInstance().openBuildMenu(player);

			}

		});
		this.registerSub(
				new SubCommand("cancel", "kingdomfactions.command.nexus.create", "Annuleer een nexus bouw actie!") {

					@Override
					public void execute(String[] args) throws KingdomFactionsException {
						if (getPlayer().hasBuildAction()) {
							getPlayer().setAction(null);
							getPlayer()
									.sendMessage(Messages.getInstance().getPrefix() + "Je hebt de actie geannuleerd!");
						} else {
							getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je hebt geen lopende actie!");
						}

					}
				});

		this.registerSub(new SubCommand("inspect", "kingdomfactions.command.nexus.inspect", "Inspecteer een Nexus!") {

			@Override
			public void execute(String[] args) throws KingdomFactionsException {

				if (!getPlayer().getSettingsProfile().hasNexusInspect()) {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je hebt Nexus Inspectie aangezet!");
				} else {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je hebt Nexus Inspectie uitgezet!");
				}
				getPlayer().getSettingsProfile().setNexusIspect(!getPlayer().getSettingsProfile().hasNexusInspect());

			}
		});
		this.registerSub(new SubCommand("destroy", "kingdomfactions.command.nexus.destroy", "Vernietig een Nexus! Alleen gebruiken om plugin problemen op te lossen!") {
			
			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Sla op de Nexus die je wil verwijderen!");
		      getPlayer().setAction(new SmallAction("NexusDestroyAction", getPlayer()) {
             
				@Override
				public void execute() throws KingdomFactionsException {
					new YesNoConfirmation(getPlayer(), "Weet je zeker dat je deze nexus wil verwijderen?", "Ja. Let op! Dit is permanent!", "Nee!", new YesNoListener() {
						
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
							try {
							ObjectData<?> toBeChecked = getAdditionalData().getObjectData("nexus");
							
	                       if(toBeChecked.getValue() instanceof Nexus) {
								 @SuppressWarnings("unchecked")
								ObjectData<Nexus> data = (ObjectData<Nexus>) toBeChecked;
								 Logger.INFO.log("steenooo is deleting a Nexus!");
								data.getValue().delete();
								
								
							}
							
							} catch (DataException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
						}
					});
					
				}
			});
		  
				
			}
		});
		this.registerSub(new SubCommand("protect", "kingdomfactions.command.nexus", "Koop Nexus-Bescherming voor 1 oorlog!") {
			
			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				INexus n = getPlayer().getCurrentNexus();
				if(n == null) {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je staat momenteel niet in een stad!");
					return;
				}
				if(n instanceof Nexus) {
				Nexus ne = (Nexus) n;	
				if(ne.getOwner() == null) {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je staat momenteel in een onbekende stad!");
					return;
				}
				if(ne.getOwner() != getPlayer().getFaction()) {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je staat momenteel in een vreemde stad!");
				} else {
					new YesNoConfirmation(getPlayer(), ChatColor.RED + "Weet je zeker dat je Nexus Protectie wil kopen?", "Dit kost 100000 coins", "Annuleren te actie", new YesNoListener() {
						
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
							Nexus n = (Nexus) player.getCurrentNexus();
							try {
								player.removeCoins(100000,  false);
								n.setProtected(true);
								player.sendMessage(Messages.getInstance().getPrefix() + "Je hebt Nexus-Protectie gekocht voor 1 oorlog");
							} catch (ValueException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				}
	     	} else {
			getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je staat momenteel in een hoofdstad!");
		}
				
			}
		});
	}

	@Override
	public void execute() throws KingdomFactionsException {
		// TODO Auto-generated method stub

	}
}