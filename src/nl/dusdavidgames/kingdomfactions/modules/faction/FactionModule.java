package nl.dusdavidgames.kingdomfactions.modules.faction;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.chat.ChatModule;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.DDGStaffChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channelranks.FactionChannelRank;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channels.FactionChannel;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.FactionDatabase;
import nl.dusdavidgames.kingdomfactions.modules.exception.chat.DuplicateChannelException;
import nl.dusdavidgames.kingdomfactions.modules.exception.faction.UnkownFactionException;
import nl.dusdavidgames.kingdomfactions.modules.faction.command.FactionCommand;
import nl.dusdavidgames.kingdomfactions.modules.faction.home.HomeModule;
import nl.dusdavidgames.kingdomfactions.modules.faction.invite.InviteModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;

import java.util.ArrayList;

@Getter
public class FactionModule {

    private ArrayList<Faction> factions = new ArrayList<>();

    @Setter
    private static FactionModule instance;

    public FactionModule() {
        setInstance(this);
        loadFactions();
        new FactionCommand("faction", "kingdomfactions.command.faction", "Main command for factions", "", true, false)
                .registerCommand();
        new InviteModule();
        new HomeModule();
    }

    public Faction getFaction(String factionId) {
        Faction faction = null;
        for (Faction faction1 : factions) {
            if (factionId.equalsIgnoreCase(faction1.getFactionId())) {
                return faction1;
            }
        }
        try {
            faction = FactionDatabase.getInstance().loadFaction(factionId);
        } catch (UnkownFactionException exception) {
        }
        return faction;
    }

    public Faction getFactionByName(String name) {
        for (Faction faction : factions) {
            if (faction.getName().equalsIgnoreCase(name)) {
                return faction;

            }
        }
        return null;
    }

    public void createFaction(String name, KingdomFactionsPlayer owner) {
        String factionId = Utils.getInstance().generateRandomString(Utils.FACTION);
        Faction faction = new Faction(factionId, name, owner.getKingdom().getType());
        try {
            ChatModule.getInstance().addChatChannel(new FactionChannel(faction, null, true));
        } catch (DuplicateChannelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        factions.add(faction);

        owner.getMembershipProfile().setFaction(faction);
        owner.getMembershipProfile().setFactionRank(FactionRank.LEADER);
        owner.save();
        owner.getChatProfile().addChannel(faction.getChannel());
        if (owner.isStaff()) {
            owner.getChatProfile().setRank(new DDGStaffChannelRank(new FactionChannelRank(owner.getFactionRank())), owner.getFaction().getChannel());
        } else {
            owner.getChatProfile().setRank(new FactionChannelRank(owner.getFactionRank()), owner.getFaction().getChannel());
        }
        FactionDatabase.getInstance().createFaction(factionId, name, owner.getKingdom().getType());

        owner.sendMessage(Messages.getInstance().getPrefix() + "Je hebt de Faction " + name + " gemaakt!");
    }

    public void loadFactions() {
        for (String id : FactionDatabase.getInstance().getFactions()) {
            Faction faction = null;
            try {
                faction = FactionDatabase.getInstance().loadFaction(id);
                factions.add(faction);
            } catch (UnkownFactionException e) {

            }
        }

    }

    public boolean compareFactions(Faction a, Faction b) {
        return a.getFactionId().equalsIgnoreCase(b.getFactionId());
    }

}
