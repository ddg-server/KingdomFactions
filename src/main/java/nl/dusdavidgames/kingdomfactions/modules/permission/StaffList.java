package nl.dusdavidgames.kingdomfactions.modules.permission;

import java.util.ArrayList;
import java.util.Iterator;

import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class StaffList extends ArrayList<KingdomFactionsPlayer> {

    public void broadcast(String message) {
        for (KingdomFactionsPlayer kingdomFactionsPlayer : this) {
            kingdomFactionsPlayer.sendMessage(message);
        }
    }

}
