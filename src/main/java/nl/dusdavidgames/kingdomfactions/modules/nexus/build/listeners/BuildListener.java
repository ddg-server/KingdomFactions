package nl.dusdavidgames.kingdomfactions.modules.nexus.build.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.data.types.ObjectData;
import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;
import nl.dusdavidgames.kingdomfactions.modules.exception.nexus.NexusException;
import nl.dusdavidgames.kingdomfactions.modules.exception.value.ValueException;
import nl.dusdavidgames.kingdomfactions.modules.nexus.INexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.BuildModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.buildings.BuildAction;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildLevel;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildingType;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import nl.dusdavidgames.kingdomfactions.modules.utils.action.SmallAction;
import nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.YesNoConfirmation;
import nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.YesNoListener;

public class BuildListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		KingdomFactionsPlayer player = PlayerModule.getInstance().getPlayer(e.getPlayer());
		if (e.getClickedBlock() == null)
			return;
		
		
		if(player.hasAction()) {
			if(player.getAction() instanceof SmallAction) {
				SmallAction a = (SmallAction) player.getAction();
				if(a.getName().equalsIgnoreCase("NexusDestroyAction")) {
					try {
					 if(NexusModule.getInstance().isNexus(e.getClickedBlock())) {
						 INexus n = NexusModule.getInstance().getNexus(e.getClickedBlock());
						 if(n instanceof Nexus) {
						 a.getAdditionalData().addData(new ObjectData<Nexus>("nexus", (Nexus) n));
						 a.execute();
						 a.cancel();
						 player.sendMessage(Messages.getInstance().getPrefix() + "Bezig met het verwijderen van de stad..");
						 }
						 }
					} catch (KingdomFactionsException e1) {
						e1.printStackTrace();
					  player.sendMessage(Messages.getInstance().getPrefix() + "Er ging iets fout!");
					}
				}
			}
		}
		
		
		
		if (player.hasBuildAction()) {

			if (e.getClickedBlock().getLocation().getWorld() == Utils.getInstance().getMiningWorld()) {
				e.getPlayer().sendMessage(
						Messages.getInstance().getPrefix() + "Je kan geen gebouwen in de minewereld plaatsen!");
				return;
			}
			BuildAction action = player.getBuildAction();
			if (action.getType() == BuildingType.NEXUS && action.getLevel() == BuildLevel.LEVEL_1) {

				if (NexusModule.getInstance().canCreateNexus(e.getClickedBlock().getLocation(), player)) {
					new YesNoConfirmation(player, ChatColor.RED + "Weet je zeker dat je jouw Nexus wil plaatsen?",
							Utils.getInstance().getLore(ChatColor.GREEN + "Dit kost "  + BuildingType.NEXUS.getPrice(player.getKingdomTerritory(), BuildLevel.LEVEL_1) + " coins! "),
							Utils.getInstance().getLore(ChatColor.RED + "Annuleer de actie!"), new YesNoListener() {

								@Override
								public void onDeny(KingdomFactionsPlayer player2) {
									player.getBuildAction().cancel();
								}

								@Override
								public void onClose(KingdomFactionsPlayer player) {
									player.getBuildAction().cancel();
								}

								@Override
								public void onAgree(KingdomFactionsPlayer player) {
									try {
							
										BuildAction a = player.getBuildAction();
										Nexus nexus = a.executeNexusAction(Utils.getInstance()
															.getNewLocation(e.getClickedBlock().getLocation()).add(0, 1, 0),
															player);
									
										nexus.getOwner().broadcast(Messages.getInstance().getPrefix(),
												"Er is een Nexus gebouwd door " + player.getName() + "!");
										nexus.save();
										player.getBuildAction().cancel();
									} catch (NexusException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch(ValueException e) {
										
										player.sendMessage(Messages.getInstance().getPrefix() + "Je hebt onvoldoende coins voor deze actie!");
									}

								}
							});
				} else {
					player.sendMessage(
							Messages.getInstance().getPrefix() + "Je mag op deze plek helaas geen nexus bouwen!");
				}
			} else {
                    if(NexusModule.getInstance().canCreateBuilding(e.getClickedBlock().getLocation(), player)) {
                    	action.setPasteLocation(e.getClickedBlock().getLocation());
                    	BuildModule.getInstance().openBuildBuilerMenu(player, action);
                    } else {
                    	player.sendMessage(Messages.getInstance().getPrefix() + "Je mag hier geen gebouw plaatsen!");
                    }
			}
		} else {
		
		}

	}

}
