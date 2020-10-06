package nl.dusdavidgames.kingdomfactions.modules.faction;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.utils.IRank;

import java.util.Locale;

public enum FactionRank implements IRank {

    LEADER(ChatColor.GRAY + " [" + ChatColor.DARK_GREEN + "Leider" + ChatColor.GRAY + "] "),
    OFFICER(ChatColor.GRAY + " [" + ChatColor.GREEN + "Officier" + ChatColor.GRAY + "] "),
    SPELER("");

    private @Getter
    String prefix;

    FactionRank(String prefix) {
        this.prefix = prefix;
    }


    @Override
    public boolean isKingdomRank() {
        // TODO Auto-generated method stub
        return false;
    }

    public static FactionRank getRank(String rank) {
        switch (rank.toLowerCase()) {
            case "leader":
                return LEADER;
            case "officer":
                return OFFICER;
            case "speler":
                return SPELER;
            default:
                return SPELER;
        }
    }

    public static String getRank(FactionRank factionRank) {
        return factionRank.name().toLowerCase(Locale.ROOT);
    }


    public static FactionRank getForPromotion(String rank) {
        switch (rank.toLowerCase()) {
            case "officer":
                return OFFICER;
            case "speler":
                return SPELER;
        }
        return SPELER;
    }


    public int getPayment(int level) {
        switch (this) {
            case LEADER:
                if (level == 1) {
                } else if (level == 1) {
                    return 10;
                } else if (level == 2) {
                    return 25;
                } else if (level == 3) {
                    return 50;
                } else if (level == 4) {
                    return 100;
                } else if (level == 5) {
                    return 200;
                } else if (level == 6) {
                    return 400;
                } else if (level == 7) {
                    return 800;
                } else if (level == 8) {
                    return 1500;
                }
                break;
            case OFFICER:
                if (level == 1) {
                } else if (level == 1) {
                    return 8;
                } else if (level == 2) {
                    return 15;
                } else if (level == 3) {
                    return 25;
                } else if (level == 4) {
                    return 50;
                } else if (level == 5) {
                    return 100;
                } else if (level == 6) {
                    return 200;
                } else if (level == 7) {
                    return 400;
                } else if (level == 8) {
                    return 750;
                }
                break;
            case SPELER:
            default:
                if (level == 1) {
                } else if (level == 1) {
                    return 5;
                } else if (level == 2) {
                    return 10;
                } else if (level == 3) {
                    return 15;
                } else if (level == 4) {
                    return 25;
                } else if (level == 5) {
                    return 50;
                } else if (level == 6) {
                    return 100;
                } else if (level == 7) {
                    return 200;
                } else if (level == 8) {
                    return 500;
                }
                break;

        }
        return 5;
    }
}
