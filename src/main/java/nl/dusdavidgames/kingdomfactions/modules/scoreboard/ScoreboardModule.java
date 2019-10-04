package nl.dusdavidgames.kingdomfactions.modules.scoreboard;

import org.bukkit.World;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.Kingdom;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.nexus.INexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.InhabitableType;
import nl.dusdavidgames.kingdomfactions.modules.war.WarModule;

public class ScoreboardModule {

    @Getter
    private static ScoreboardModule instance;

    public ScoreboardModule() {
        instance = this;
        KingdomFactionsPlugin.getInstance().registerListener(new ScoreboardListeners());
    }


    public void setupScoreboard(KingdomFactionsPlayer player) {
        ScoreBoard board = new ScoreBoard("The Kingdom Factions", player);
        board.addBlankLine(13);
        board.addLine(ChatColor.GRAY + "Oorlog: ", 12);
        board.addLine(getWarState(), 11);
        board.addBlankLine(10);
        board.addLine(ChatColor.GRAY + "Faction:", 9);
        board.addLine(getFaction(player.getKingdom().getType(), player.getFaction()), 8);
        board.addBlankLine(7);
        board.addLine(ChatColor.GRAY + "Locatie:", 6);
        board.addLine(ChatColor.GRAY + "Wereld: " + getWorld(player.getLocation().getWorld()), 5);
        board.addLine(ChatColor.GRAY + getTerritory(player.getTerritoryId(), player.getKingdomTerritory()), 4);
        board.addBlankLine(3);
        board.addLine(ChatColor.GRAY + "Coins: " + ChatColor.RED + player.getStatisticsProfile().getCoins(), 2);
        board.addLine(ChatColor.GRAY + "Influence: " + ChatColor.RED + player.getStatisticsProfile().getInfluence(), 1);
        player.setScoreBoard(board);
        player.getScoreboard().refreshTags();

    }


    private String getFaction(KingdomType k, Faction f) {
        if (f == null) {
            return k.getColor() + "Geen Faction!";
        } else {
            return ChatColor.GRAY + "[" + k.getColor() + f.getName() + ChatColor.GRAY + "]";
        }
    }

    public String getTerritory(String id, KingdomType k) {
        if (id.equalsIgnoreCase("~MINING~")) {
            return ChatColor.GRAY + "[" + ChatColor.DARK_GREEN + "Wildernis" + ChatColor.GRAY + "]";
        }
        if (k == KingdomType.GEEN || k == KingdomType.ERROR) {
            return ChatColor.GRAY + "[" + ChatColor.DARK_GREEN + "Wildernis" + ChatColor.GRAY + "]";
        }
        if (NexusModule.getInstance().getINexus(id) == null) {

            return k.getPrefix();
        }


        INexus n = NexusModule.getInstance().getINexus(id);
        if (n instanceof Nexus) {
            if (((Nexus) n).getOwnerId().equalsIgnoreCase("Wilderness")) {
                return k.getPrefix().replace("]", "") + ChatColor.GRAY + "/ Onbekend" + ChatColor.GRAY + "]";

            }
        }
        if (n.getOwner() == null) {

            return k.getPrefix();
        }
        if (n.getOwner().getInhabitableType() == InhabitableType.FACTION) {
            Faction f = (Faction) n.getOwner();
            return k.getPrefix().replace("]", "") + ChatColor.GRAY + "/ " + f.getStyle().getColor() + f.getName() + ChatColor.GRAY + "]";
        } else if (n.getOwner().getInhabitableType() == InhabitableType.KINGDOM) {
            Kingdom kingdom = (Kingdom) n.getOwner();
            return kingdom.getType().getPrefix().replace("]", "") + ChatColor.GRAY + "/" + ChatColor.RED + " Hoofdstad" + ChatColor.GRAY + "]";
        }
        return k.getPrefix();


    }

    public String getWarState() {
        if (WarModule.getInstance().isWarActive()) {
            return ChatColor.GRAY + "Resterende Minuten: " + WarModule.getInstance().getWar().getTime();
        }
        return ChatColor.GRAY + "Geen Oorlog";

    }


    public String getWorld(World w) {
        if (w == Utils.getInstance().getOverWorld()) {
            return ChatColor.GRAY + "[" + ChatColor.GREEN + "Ranos" + ChatColor.GRAY + "]";
        } else {
            return ChatColor.GRAY + "[" + ChatColor.RED + "Mithras" + ChatColor.GRAY + "]";
        }
    }
    //
    //


    public void updateScoreboard(KingdomFactionsPlayer p) {

        p.getScoreboard().editLine(2, ChatColor.GRAY + "Coins: " + ChatColor.RED + p.getStatisticsProfile().getCoins());

        p.getScoreboard().editLine(1,
                ChatColor.GRAY + "Influence: " + ChatColor.RED + p.getStatisticsProfile().getInfluence());

        p.getScoreboard().editLine(5, ChatColor.GRAY + "Wereld: " + ScoreboardModule.getInstance().getWorld(p.getLocation().getWorld()));
        p.getScoreboard().editLine(4, ScoreboardModule.getInstance()
                .getTerritory(p.getTerritoryId(), p.getKingdomTerritory()));


        Faction f = p.getFaction();
        if (f != null) {
            p.getScoreboard().editLine(8, ChatColor.GRAY + "[" + p.getKingdom().getType().getColor() + f.getName() + ChatColor.GRAY + "]");
        } else {
            p.getScoreboard().editLine(8, p.getKingdom().getType().getColor() + "Geen Faction!");
        }
    }


}
