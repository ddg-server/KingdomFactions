package nl.dusdavidgames.kingdomfactions.modules.nexus.build;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.BuildingDatabase;
import nl.dusdavidgames.kingdomfactions.modules.exception.nexus.NexusException;
import nl.dusdavidgames.kingdomfactions.modules.exception.value.ValueException;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus.CapitalNexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.INexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.buildings.BuildAction;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.buildings.Building;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.listeners.BuildListener;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.listeners.BuildingHandleListener;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.menu.BuildMenu;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildLevel;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildingType;
import nl.dusdavidgames.kingdomfactions.modules.nexus.protection.ProtectionModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;
import nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.YesNoConfirmation;
import nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.YesNoListener;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BuildModule {
    private static @Getter
    @Setter
    BuildModule instance;

    public BuildModule() {
        setInstance(this);
        loadBuildings();
        KingdomFactionsPlugin.getInstance().registerListener(new BuildMenu());
        KingdomFactionsPlugin.getInstance().registerListener(new BuildListener());
        KingdomFactionsPlugin.getInstance().registerListener(new BuildingHandleListener());
    }

    public BuildAction addAction(KingdomFactionsPlayer player, Building b) {
        BuildAction action = new BuildAction(b, player);
        player.setAction(action);
        return action;
    }

    public BuildAction addAction(KingdomFactionsPlayer player, Nexus nexus, BuildingType type, KingdomType style) {
        BuildAction action = new BuildAction(nexus, type, style, player);
        player.setAction(action);
        return action;
    }

    public BuildAction getAction(KingdomFactionsPlayer p) {
        if (p.getAction() instanceof BuildAction) {
            return (BuildAction) p.getAction();
        }
        return null;
    }


    public void loadBuildings() {
        Logger.DEBUG.log("Attempting to load buildings!");
        Logger.DEBUG.log(NexusModule.getInstance().getNexuses().size() + " nexuses!");
        for (INexus n : NexusModule.getInstance().getNexuses()) {
            if (n instanceof CapitalNexus)
                continue;
            for (BuildingType t : BuildingType.values()) {
                Building b = BuildingDatabase.getInstance().loadBuilding((Nexus) n, t);
                if (b == null) {
                    Logger.DEBUG.log("Couldn't load building " + t);
                    continue;
                }
                Nexus ne = (Nexus) n;
                ne.getBuildings().add(b);
            }
        }
    }

    public boolean isBuilding(Block block) {
        if (block == null) {
            return false;
        }
        if (block.getType() != Material.COAL_BLOCK) {
            return false;
        }


        return getBuilding(block) != null;
    }

    public Building getBuilding(Block block) {
        INexus n = NexusModule.getInstance().getINexus(ProtectionModule.getInstance().getTerritoryId(block.getLocation()));
        if (n == null) {
            return null;
        }
        if (n instanceof Nexus) {
            for (Building b : ((Nexus) n).getBuildings()) {
                if (Utils.getInstance().equalsLocation(b.getLocation(), block.getLocation())) {
                    return b;
                }
            }
        }
        return null;
    }

    public void openBuildBuilderMenu(KingdomFactionsPlayer player, BuildAction action) {
        player.setAction(action);
        YesNoConfirmation confirm = new YesNoConfirmation(player, "Weet je zeker dat je dit gebouw wilt bouwen/upgraden? Dit kost "
                + action.getPrice() + " coins!", Utils.getInstance().getLore("Ja. Let wel op! Dit gebouw kan NIET worden verwijderd"), Utils.getInstance().getLore("Nee! Annuleer de actie!"), new YesNoListener() {

            @Override
            public void onDeny(KingdomFactionsPlayer player) {
				//not in use
            }

            @Override
            public void onClose(KingdomFactionsPlayer player) {
				//not in use
            }

            @Override
            public void onAgree(KingdomFactionsPlayer player) {
                try {
                    if (action.getType() == BuildingType.NEXUS) {
                        if (action.getLevel() == BuildLevel.LEVEL_1) {
                            Nexus nexus = player.getBuildAction().executeNexusAction();
                            nexus.getOwner().broadcast(Messages.getInstance().getPrefix(), "Er is een Nexus gebouwd door " + player.getName() + "!");
                            nexus.save();
                            player.getBuildAction().cancel();
                        } else {
                            Nexus nexus = player.getBuildAction().executeNexusAction();
                            nexus.save();
                            player.getBuildAction().cancel();
                        }
                    } else {
                        Building b = action.executeAction();

                        b.save();
                        action.cancel();
                    }

                } catch (ValueException | NexusException exception) {
                    if (exception instanceof ValueException) {
                        player.sendMessage(Messages.getInstance().getPrefix() + "Je hebt onvoldoende coins voor deze actie!");
                        player.getAction().cancel();
                    } else {
                        exception.printStackTrace();
                    }
                }

            }
        });

        player.setYesNoConfirmation(confirm);

    }

    /**
     *        @Override public void onAgree(KingdomFactionsPlayer player) {
    Messages.getInstance().debug("Agreed with the YesNo Building confirm");
    try {
    player.removeCoins(action.getPrice(), false);
    if(action.getType() == BuildingType.NEXUS) {
    if(action.getLevel() == BuildLevel.LEVEL_1) {
    Nexus nexus = player.getBuildAction().executeNexusAction(action.getLocation(), player);
    nexus.getOwner().broadcast(Messages.getInstance().getPrefix(), "Er is een Nexus gebouwd door " + player.getName() + "!");
    nexus.save();
    player.getBuildAction().cancel();
    } else {
    Nexus n = player.getBuildAction().executeNexusAction();
    n.save();
    player.getBuildAction().cancel();
    }
    } else {
    Building b = action.executeAction();
    b.save();
    }

    } catch (ValueException | NexusException e) {
    if(e instanceof ValueException) {
    player.sendMessage(Messages.getInstance().getPrefix() + "Je hebt onvoldoende coins voor deze actie!");
    } else {
    e.printStackTrace();
    }
    }

    });
     */
}
