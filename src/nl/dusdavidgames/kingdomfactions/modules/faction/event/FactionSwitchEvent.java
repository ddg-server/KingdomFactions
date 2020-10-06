package nl.dusdavidgames.kingdomfactions.modules.faction.event;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class FactionSwitchEvent extends Event {
    public static final HandlerList list = new HandlerList();
    private Faction newFaction;
    private KingdomFactionsPlayer player;

    public FactionSwitchEvent(KingdomFactionsPlayer kingdomPlayer, Faction newFaction) {
        this.player = kingdomPlayer;
        this.newFaction = newFaction;
    }


    @Override
    public HandlerList getHandlers() {
        return list;
    }

    public static HandlerList getHandlerList() {
        return list;
    }

}
