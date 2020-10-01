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

public class FactionModule {

	private @Getter ArrayList<Faction> factions = new ArrayList<Faction>();
	private static @Getter @Setter FactionModule instance;

	public FactionModule() {
		setInstance(this);
		loadFactions();
		new FactionCommand("faction", "kingdomfactions.command.faction", "Main command for factions", "", true, false)
				.registerCommand();
		new InviteModule();
		new HomeModule();
	}

	public Faction getFaction(String faction_id) {
		Faction faction = null;
		for (Faction f : factions) {
			if (faction_id.equalsIgnoreCase(f.getFactionId())) {
				return f;
			}
		}
		try {
			faction = FactionDatabase.getInstance().loadFaction(faction_id);
		} catch (UnkownFactionException e) {
		}
		return faction;
	}

	public Faction getFactionByName(String name) {
		for (Faction f : factions) {
			if (f.getName().equalsIgnoreCase(name)) {
				return f;

			}
		}
		return null;
	}

	public void createFaction(String name, KingdomFactionsPlayer owner) {		
		String faction_id = Utils.getInstance().generateRandomString(Utils.FACTION);
		Faction f = new Faction(faction_id, name, owner.getKingdom().getType());
		try {
			ChatModule.getInstance().addChatChannel(new FactionChannel(f, null, true));
		} catch (DuplicateChannelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		factions.add(f);
		
		owner.getMembershipProfile().setFaction(f);
		owner.getMembershipProfile().setFactionRank(FactionRank.LEADER);
		owner.save();
		owner.getChatProfile().addChannel(f.getChannel());
        if(owner.isStaff()) {
        	owner.getChatProfile().setRank(new DDGStaffChannelRank(new FactionChannelRank(owner.getFactionRank())), owner.getFaction().getChannel());
        } else {
          	owner.getChatProfile().setRank(new FactionChannelRank(owner.getFactionRank()), owner.getFaction().getChannel());
            	
        }
		FactionDatabase.getInstance().createFaction(faction_id, name, owner.getKingdom().getType());
	
		owner.sendMessage(Messages.getInstance().getPrefix() + "Je hebt de Faction " + name + " gemaakt!");
	}

	public void loadFactions() {
		for (String id : FactionDatabase.getInstance().getFactions()) {
			Faction f = null;
			try {
				f = FactionDatabase.getInstance().loadFaction(id);
				factions.add(f);
			} catch (UnkownFactionException e) {
			
			}
		}

	}

	public void deleteFaction() {

	}

	public boolean compareFactions(Faction a, Faction b) {
		return a.getFactionId().equalsIgnoreCase(b.getFactionId());
	}

}
